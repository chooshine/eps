package com.eps.web.network;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.dao.Page;
import com.eps.domain.ETestRecord;
import com.eps.service.esextends.ExamOrderService;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.examsystem.ExamBeforeService;
import com.eps.service.examsystem.ViewExamService;
import com.eps.service.network.NetWorkStudentService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;

@Controller
public class NetWorkStudent  extends BaseController{

	@Autowired private ExamOrderService eos;
	@Autowired private NetWorkStudentService nwts;
	@Autowired private ExamBeforeService ebserivce;
	@Autowired private ExamAfterService eas;
	@Autowired private ViewExamService ves;
	Page page;

	//学生进入做作业页面
	@RequestMapping(value="/net_examView.html")
	public String doExam(ETestRecord eTestRecord, HttpServletRequest request, ModelMap mm) {
		//如果testRecId不是-1，则直接查找曾经的做题记录，如果是-1，则查询数据库中是否有做题记录，如果有，则使用查出的testRecId
		eTestRecord.setUserId(getSessionUser(request).getUserId());
		if (eTestRecord.getTestRecId() == -1) {
			List<LStrMap<Object>> recIdList = nwts.getTestRecId(eTestRecord);
			if (recIdList.size() > 0) {
				eTestRecord.setTestRecId((Integer)recIdList.get(0).get("test_rec_id"));
				LStrMap<Object> exaMap = ebserivce.getTestRecordCountService(eTestRecord.getTestRecId()).get(0);
				mm.put("examTime",exaMap.get("exam_use_time"));
				mm.put("finished_num", exaMap.get("finished_num")==null?0:exaMap.get("finished_num"));
			} else {
				eTestRecord.setTestRecId(nwts.saveEtestRecordService(eTestRecord));
				mm.put("finished_num", 0);
			}
		} else {
			LStrMap<Object> exaMap = ebserivce.getTestRecordCountService(eTestRecord.getTestRecId()).get(0);
			mm.put("examTime",exaMap.get("exam_use_time"));
			mm.put("finished_num", exaMap.get("finished_num")==null?0:exaMap.get("finished_num"));
		}
		
		//试卷信息
	    List list = eas.getFullExamInfoService(eTestRecord.getExamId(), eTestRecord.getTestRecId());
		mm.put("title", "正在考试");
		mm.put("examId",eTestRecord.getExamId());
		mm.put("testRecId", eTestRecord.getTestRecId());
		mm.put("qtList", list.get(0));
		mm.put("bctList", list.get(1));
		mm.put("qiMap", list.get(2));
		mm.put("examInfo",ebserivce.getExamInfoAndEndTime(eTestRecord.getExamId(), eTestRecord.getTestId(), eTestRecord.getClassId(), eTestRecord.getSubjectId()));
		mm.put("typesAndQuestionsInfo", ebserivce.getNetWorkStudentTypesAndQuestions(eTestRecord.getTestRecId()));
		return "network/student/net_examView";
	}
	

	//学生保存做题情况
	@RequestMapping(value="/answer/exam_examView.json",method=RequestMethod.POST)
	public String getScore(ModelMap mm,String answers,String markInfo,HttpServletRequest request,
			String type,String useTime,int examId,int testRecId, String questionsTime, int finishedNum){
		
		String studentAnswers = new String(request.getParameter("studentAnswers"));
		
		//设置考试记录并更新考试记录
		ETestRecord eTestRecord=new ETestRecord();
		eTestRecord.setTestRecId(testRecId);
		eTestRecord.setExamId(examId);
		eTestRecord.setExamUseTime(useTime);
		eTestRecord.setFinishedNum(finishedNum);
		
		//重新保存每一小题的做题详情
		ebserivce.saveExam(answers, markInfo, testRecId, studentAnswers, questionsTime);
		eTestRecord.setCommitFlag(0);
		//更新考试记录
		ebserivce.updateEtestRecordService(eTestRecord);
		return "jsonView";
	}
	
	//交卷，跳转到交卷成功页面
	@RequestMapping(value="/network/student/submitExamSuccess.html", method=RequestMethod.POST)
	public String toSubmitExamSuccess(ModelMap mm,String answers,String markInfo,HttpServletRequest request,
			String type,String useTime,int examId,int testRecId, String questionsTime, int finishedNum) {
		
		String studentAnswers = new String(request.getParameter("studentAnswers"));
		
		//设置考试记录并更新考试记录
		ETestRecord eTestRecord=new ETestRecord();
		eTestRecord.setTestRecId(testRecId);
		eTestRecord.setExamId(examId);
		eTestRecord.setExamUseTime(useTime);
		eTestRecord.setFinishedNum(finishedNum);
		
		//重新保存每一小题的做题详情
		ebserivce.saveExam(answers, markInfo, testRecId, studentAnswers, questionsTime);
		eTestRecord.setCommitFlag(1);
		//计算每个小题的得分，更新数据库中每个小题的做题详情的小题得分
		ebserivce.updateTestRecordDetailScore(testRecId);
		
		//更新考试记录
		ebserivce.updateEtestRecordService(eTestRecord);
		
		//判断是否是自动阅卷
		int autoMarkFlag = ebserivce.getAutoMarkFlagByTestRecId(testRecId);
		LStrMap<Object> scoreMap = null;
		if (autoMarkFlag == 1) {
			//更新考生的考试记录的评分字段，设为已阅卷
			ves.updateRecordMarkFlagAndScore(testRecId, 2, 0);
			
			scoreMap = ebserivce.getStudentScoreByTestRecId(testRecId);
			//考试时间到，则把查出来的结果返回，否则，返回null
			/*if (Integer.parseInt(scoreMap.get("end_flag").toString()) == 1) {
				mm.put("scoreMap", scoreMap);
			} else {
				mm.put("scoreMap", null);
			}*/
			mm.put("scoreMap", scoreMap);
		}
		
		mm.put("title", "交卷成功");
		return "network/student/submitExamSuccess";
	}
}
