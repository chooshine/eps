package com.eps.service.weixin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eps.dao.achievement.StudentDao;
import com.eps.dao.homework.StudentHomeworkDao;
import com.eps.dao.user.UserDao;
import com.eps.domain.Student;
import com.eps.domain.User;
import com.eps.service.MessageService;
import com.eps.utils.CheckIdentityAndPhone;
import com.eps.utils.LStrMap;

public class SimpleMessageProcess implements IMessageProcess {
	private static Logger log = LoggerFactory.getLogger(SimpleMessageProcess.class);
	public void process(WeixinMessage message) {

	}
	
	public WeixinMessage createResponseMsg(WeixinMessage message) {
		String msg = "";
		try{
			StudentDao dao = MessageService.getBean(StudentDao.class);
			String phone = message.getContent();
			
			//验证发送的内容是否为正常的手机号吗
			if(!CheckIdentityAndPhone.idPhone(phone)){
				msg = "您发送的内容不是合法的手机号码!";
			//验证该手机号码是否在系统中存在
			}else{
//				User user = dao.getUserByPhone(phone);
				List<Student> students = dao.queryStudentByGuardianPhone(phone);
				if(students.size()<1)
					msg = "对不起, 您发送的手机号码未在系统中登记过，请确认您在学校中登记的监护人手机号码!";
				else{
					StudentHomeworkDao hdao = MessageService.getBean(StudentHomeworkDao.class);
					StringBuffer sb = new StringBuffer("");
					for(Student s:students){
						List<LStrMap<Object>> hw = hdao.getHomeworksOfStudents(s.getUserId());
						if(hw.size()<1) sb.append("未查询到").append(s.getStudentName()).append("作业信息。\n");
						else{
							sb.append(s.getStudentName()).append("作业信息如下:\n");
							for(LStrMap<Object> item:hw){
								sb.append("[").append(item.get("sort_name")).append("]");
								if(item.get("COMMIT_FLAG")!=null && (Integer)item.get("COMMIT_FLAG") == 1)
									sb.append("已上交\n");
								else
									sb.append("未上交\n").append("最迟上交时间:[").append(item.get("END_TIME")).append("]\n");
							}
							
						}
					}
					msg = sb.toString();
				}
			}
			
		}catch(Exception e){
			log.error("查找作业信息出错",e);
			msg = "未查询到作业信息。";
		}
		WeixinMessage res = new WeixinMessage(message.getFromUserName(),
				message.getToUserName(), Contains.MSGTYPE_TEXT, msg);
		return res;
	}
}
