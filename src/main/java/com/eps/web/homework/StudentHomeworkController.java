package com.eps.web.homework;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.android.service.homework.AHomeworkService;
import com.eps.android.service.login.LoginService;
import com.eps.dao.Page;
import com.eps.domain.EHomeworkRecord;
import com.eps.service.homework.StudentHomeworkService;
import com.eps.service.homework.TeacherHomeworkService;
import com.eps.service.network.NetWorkService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;

@Controller
public class StudentHomeworkController extends BaseController {

	@Autowired private StudentHomeworkService shs;
	@Autowired private TeacherHomeworkService ths;
	@Autowired private LoginService ls;
	@Autowired private AHomeworkService ahs;
	@Autowired private NetWorkService nws;
	
	//显示我的作业安排
	@RequestMapping(value="/homework/student/showRecentlyHomeworks.html")
	public String toIndex(HttpServletRequest request, ModelMap mm) {
		mm.put("homeworks", shs.getHomeworksOfStudents(getSessionUser(request).getUserId()));
		mm.put("sysdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return "homework/student/homeworksOfStudent";
	}
	
	//做作业
	@RequestMapping(value="/homework/student/doHomework.html")
	public String doHomework(int hwId, int hwRecId, HttpServletRequest request, ModelMap mm) {
		if(hwRecId == -1){
			long userId = getSessionUser(request).getUserId();
			List<LStrMap<Object>> recIdList = shs.getHwRecordId(hwId, userId);
			if (recIdList.size() > 0) {//如果学生已经做过这次作业，就找到学生已做这次作业的记录编号
				hwRecId = (Integer)recIdList.get(0).get("hw_rec_id");
				LStrMap<Object> hwInfoMap = shs.getHwFinishedNum(hwRecId);
				mm.put("finished_num", hwInfoMap.get("finished_num")==null?0:hwInfoMap.get("finished_num"));
			} else {//学生没做过这次作业，就新建一次作业记录，此时并不对记录详情表插入数据
				//获得相关信息
				EHomeworkRecord eHomeworkRecord = shs.getHwRecord(hwId, userId);
				hwRecId = eHomeworkRecord.getHwRecId();
				shs.saveEhomeworkRecord(eHomeworkRecord);//插入一次作业记录
				mm.put("finished_num", 0);
			}
		}else{
			LStrMap<Object> hwInfoMap = shs.getHwFinishedNum(hwRecId);
			mm.put("finished_num", hwInfoMap.get("finished_num")==null?0:hwInfoMap.get("finished_num"));
		}
		//作业信息
		mm.put("hwId",hwId);
		mm.put("hwRecId", hwRecId);
		mm.put("qiMap", ths.getFullHwInfoByHwId(hwId, hwRecId));
		mm.put("hwInfo",ths.getHwInfoByHwId(hwId));
		mm.put("hwClassInfo", shs.getHwClassInfo(hwRecId));
		mm.put("questionsInfo", shs.getQuestionsInfo(hwRecId));
		
		return "homework/student/doHomework";
	}
	
	//保存作业记录
	@RequestMapping(value="homework/student/saveRecord.json", method=RequestMethod.POST)
	public String saveHwRecord(ModelMap mm,String answers,String markInfo,HttpServletRequest request,
			int hwId,int hwRecId, String questionsTime, int finishedNum) {
		try {
			String studentAnswers = URLDecoder.decode(request.getParameter("studentAnswers"), "utf-8");
			answers = URLDecoder.decode(answers, "utf-8");
			//设置作业记录并更新作业记录
			EHomeworkRecord eHomeworkRecord = new EHomeworkRecord();
			eHomeworkRecord.setHwRecId(hwRecId);
			eHomeworkRecord.setHwId(hwId);
			eHomeworkRecord.setFinishedNum(finishedNum);
			
			//重新保存每一小题的做题详情
			shs.saveEhomework(answers, markInfo, hwRecId, studentAnswers, questionsTime);
			eHomeworkRecord.setCommitFlag(0);
			//更新作业记录
			shs.updateEhomeworkRecord(eHomeworkRecord);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "jsonView";
	}
	
	//交作业
	@RequestMapping(value="/homework/student/handInSuccess.html", method=RequestMethod.POST)
	public String handInHomework(ModelMap mm,String answers,String markInfo,HttpServletRequest request,
			int hwId,int hwRecId, String questionsTime, int finishedNum) {
		String studentAnswers = new String(request.getParameter("studentAnswers"));
		
		//设置作业记录并更新作业记录
		EHomeworkRecord eHomeworkRecord = new EHomeworkRecord();
		eHomeworkRecord.setHwRecId(hwRecId);
		eHomeworkRecord.setHwId(hwId);
		eHomeworkRecord.setFinishedNum(finishedNum);
		eHomeworkRecord.setCommitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		//重新保存每一小题的做题详情
		shs.saveEhomework(answers, markInfo, hwRecId, studentAnswers, questionsTime);
		eHomeworkRecord.setCommitFlag(1);
		//计算每个小题的得分，更新数据库中每个小题的做题详情的小题得分
		shs.updateEhomeworkRecordDetailScore(hwRecId);
		
		//更新作业记录
		shs.updateEhomeworkRecord(eHomeworkRecord);
		
		//判断是否是自动阅卷
		int autoMarkFlag = (Integer)shs.getAutoCorrectFlag(hwRecId).get("correct");
		if (autoMarkFlag == 1) {
			//更新考生的作业记录的批改字段，设为已批改
			shs.updateHwRecord(hwRecId, 2, 0);
			//更新学生成绩星星字段
			shs.updateEhomeworkRecordStar(hwRecId);
			//得到学生的星星
			LStrMap<Object> tempMap = shs.getHwRecordInfo(hwRecId);
			//作业时间到，则把查出来的结果返回，否则，返回null
			if (((Date)tempMap.get("end_time")).getTime() <= (new Date()).getTime()) {
				mm.put("hwRecordMap", tempMap);
			} else {
				mm.put("hwRecordMap", null);
			}
		}
		
		mm.put("title", "交作业成功");
		return "homework/student/handInSuccess";
	}
	
	//得到学生当前时间之前的已经提交了的作业
	@RequestMapping(value="/homework/student/viewAnaly.html")
	public String getStudentCommitedExam(HttpServletRequest request,ModelMap mm) {
		mm.put("commitedHomeworks", shs.getCommitedHomeworks(this.getSessionUser(request).getUserId()));
		return "homework/student/homeworksOfStudent";
	}
	
	//查看某次作业的做题详情，即展现作业信息及考生的答题信息
	@RequestMapping(value="/homework/student/viewDetail.html")
	public String toExamAnaly(HttpServletRequest request, ModelMap mm, int hwId, int hwRecId) {
		mm.put("hwId",hwId);
		mm.put("hwRecId",hwRecId);
		mm.put("hwRecord", shs.getHwRecordInfo(hwRecId));
		mm.put("qiMap", shs.getQuestions(hwRecId, hwId));
		mm.put("hwInfo",ths.getHwInfoByHwId(hwId));
		mm.put("questionsInfo", shs.getQuestionsFlag(hwRecId));
		return "homework/student/viewDetail";
	}
	
	//查看收藏的小题页面
	@RequestMapping(value="/homework/viewFavoriteQuestions.html")
	public String viewFavoriteQuestions(HttpServletRequest request, ModelMap mm) {
		long userId = getSessionUser(request).getUserId();
		
		//查询学生当前的所有科目
		List<LStrMap<Object>> sortList = ls.getSortListOfStudent(userId);
		mm.put("sortList", sortList);
		
		String sort = request.getParameter("subjectId");
		int subjectId = 0;
		if (sort == null) {
			subjectId = Integer.parseInt(sortList.get(0).get("sort_id").toString());
		} else {
			subjectId = Integer.parseInt(sort);
		}
		
		//得到选中的科目的名称
		for (int i=0; i<sortList.size(); i++) {
			if (Integer.parseInt(sortList.get(i).get("sort_id").toString()) == subjectId) {
				mm.put("sortName", sortList.get(i).get("sort_name"));
				mm.put("subjectId", subjectId);
				break;
			}
		}
		//根据科目查找对应的知识点
		mm.put("knowledgePoints", getStructedKnowledgePoints(shs.getFavoriteKnowledgePoints(userId, subjectId)));
		
		return "homework/viewFavoriteQuestions";
	}
	
	//得到封装好结构之后的知识点
	private List<LStrMap<Object>> getStructedKnowledgePoints(List<LStrMap<Object>> list) {
		List<LStrMap<Object>> resultList = new ArrayList<LStrMap<Object>>();//存放结果元素
		List<LStrMap<Object>> removeList = new ArrayList<LStrMap<Object>>();//记录需要删除的元素
		
		//将第一级知识点封装
		for (int i=0; i<list.size(); i++) {
			if (Integer.parseInt(list.get(i).get("p_kp_id").toString()) == 0) {
				list.get(i).put("subKps", new ArrayList<Object>());
				resultList.add(list.get(i));
				removeList.add(list.get(i));
			}
		}
		//删除list中已被封装过的一级知识点
		for (int i=0; i<removeList.size(); i++) {
			list.remove(removeList.get(i));
		}
		//清空indexList
		removeList.clear();
		
		//封装二级知识点
		for (int i=0; i<resultList.size(); i++) {
			//得到当前一级知识点
			LStrMap<Object> tempMap = (LStrMap<Object>)resultList.get(i);
			ArrayList<LStrMap<Object>> tempList = (ArrayList<LStrMap<Object>>)tempMap.get("subKps");
			//遍历剩余知识点，如果遍历到的知识点的p_kp_id==当前一级知识点的kp_id，则把其放到一级知识点的subKps中
			//并将符合条件的知识点的索引放到indexList中
			for (int j=0; j< list.size(); j++) {
				if (Integer.parseInt(list.get(j).get("p_kp_id").toString()) == Integer.parseInt(tempMap.get("kp_id").toString())) {
					list.get(j).put("subKps", new ArrayList<LStrMap<Object>>());
					tempList.add(list.get(j));
					removeList.add(list.get(j));
				}
			}
		}
		//删除list中已封装过的二级知识点
		for (int i=0; i<removeList.size(); i++) {
			list.remove(removeList.get(i));
		}
		
		//封装三级知识点
		for (int i=0; i<resultList.size(); i++) {
			ArrayList<LStrMap<Object>> tempList = (ArrayList<LStrMap<Object>>) ((LStrMap<Object>)resultList.get(i)).get("subKps");
			for (int j=0; j<tempList.size(); j++) {
				LStrMap<Object> tempMap = tempList.get(j);
				ArrayList<LStrMap<Object>> tempList2 = (ArrayList<LStrMap<Object>>)tempMap.get("subKps");
				for (int k=0; k<list.size(); k++) {
					if (Integer.parseInt(list.get(k).get("p_kp_id").toString()) == Integer.parseInt(tempMap.get("kp_id").toString())) {
						tempList2.add(list.get(k));
					}
				}
			}
		}
		
		return resultList;
	}
	
	//查询对应知识点收藏的小题
	@RequestMapping(value="/homework/showFavoriteQuestions.html")
	private String showFavoriteQuestions(int kpId, int subjectId, HttpServletRequest request, ModelMap mm) {
		mm.put("qiMap", shs.getFavoriteQuestions(getSessionUser(request).getUserId(), kpId, subjectId));
		
		return "homework/showFavoriteQuestions";
	}
	
	//收藏小题
	@RequestMapping(value="/collectQuestion.json")
	private String collectQuestion(HttpServletRequest request, ModelMap mm) {
		int quesId = Integer.parseInt(request.getParameter("quesId"));
		//获得以传入的小题编号作为remark的普通小题
		List<LStrMap<Object>> list = shs.getNormalQuestions(quesId);
		//如果收藏的小题是材料，则list的size>0，这时要收藏该材料下的所有小题
		for (int i=0; i<list.size(); i++) {
			ahs.collectQuestion(getSessionUser(request).getUserId(), Integer.parseInt(list.get(i).get("ques_id").toString()), null);
		}
		//收藏传入的小题
		ahs.collectQuestion(getSessionUser(request).getUserId(), quesId, null);
		
		return "jsonView";
	}
	
	//取消收藏小题
	@RequestMapping(value="/cancelCollectQuestion.json")
	private String cancelCollectQuestion(HttpServletRequest request, ModelMap mm) {
		int quesId = Integer.parseInt(request.getParameter("quesId"));
		long userId = getSessionUser(request).getUserId();
		//取消传入的小题的收藏记录
		ahs.cancelCollection(userId, quesId);
		//如果小题是材料，则要取消该材料下所有小题的收藏记录
		List<LStrMap<Object>> list = shs.getNormalQuestions(quesId);
		for (int i=0; i<list.size(); i++) {
			ahs.cancelCollection(userId, Integer.parseInt(list.get(i).get("ques_id").toString()));
		}
		return "jsonView";
	}
	
	//从其他入口或者选择科目后进入错题集页面
	@RequestMapping(value="/student/viewErrorQuestions.html")
	public String toErrorQuestions(HttpServletRequest request, ModelMap mm) {
		//获取科目，如果有科目参数，则说明是错题集页面重新选择科目后进行刷新页面，否则，说明是其他入口进入错题集页面
		String subjectId  = request.getParameter("subjectId"), sortName = null;
		long userId = getSessionUser(request).getUserId();
		int intSubject = 0;
		
		List<LStrMap<Object>> sortList = ls.getSortListOfStudent(userId);//获取学生所有的科目
		if (subjectId != null) {//刷新错题集页面
			intSubject = Integer.parseInt(subjectId);
			for (LStrMap<Object> tempMap : sortList) {
				String tempSubject = tempMap.get("sort_id").toString();
				if (tempSubject.equals(subjectId)) {
					sortName = tempMap.get("p_sort_name")+"·"+tempMap.get("sort_name");
					break;
				}
			}
		} else {//其他入口进入错题集页面
			LStrMap<Object> tempMap = sortList.get(0);
			intSubject = (Integer) tempMap.get("sort_id");
			sortName = tempMap.get("p_sort_name")+"·"+tempMap.get("sort_name");
		}
		
		List<LStrMap<Object>> quesTypes = nws.getTypeSubjects(intSubject);//根据科目查找大题类型
		int tsId = (Integer) quesTypes.get(0).get("ts_id");
		List<LStrMap<Object>> kps = shs.getErrorQuesKps(userId, intSubject, tsId, "");//根据科目及第一个大题类型查找考点
		kps = getStructedKnowledgePoints(kps);
		mm.put("sortList", sortList);
		mm.put("sortName", sortName);
		mm.put("subjectId", intSubject);
		mm.put("knowledgePoints", kps);
		mm.put("quesTypes", nws.getTypeSubjects(intSubject));
		return "homework/student/viewErrorQuestions";
	}
	
	//得到错题集的知识点
	@RequestMapping(value="/student/getErrorQuestionsKps.html", method=RequestMethod.POST)
	public String getKnowledgepoints(int subjectId, int tsId,String keyword, HttpServletRequest request, ModelMap mm) {
		long userId = getSessionUser(request).getUserId();

		List<LStrMap<Object>> kpList = shs.getErrorQuesKps(userId, subjectId, tsId, keyword);
		mm.put("knowledgePoints", getStructedKnowledgePoints(kpList));
		return "network/teacher/kpsTree";
	}
	
	//查找错题集中的小题
	@RequestMapping(value="/student/getErrorQuestions.html", method=RequestMethod.POST)
	public String getSuitableQuestions(int subjectId, int tsId, int kpId, String keyword, int pageNo, HttpServletRequest request, ModelMap mm) {
		try {
			keyword = URLDecoder.decode(keyword, "utf-8");
			List<Object> list = shs.getErrorQuestions(getSessionUser(request).getUserId(), subjectId, tsId, kpId, keyword, pageNo);
			mm.put("page", list.get(1));
			mm.put("qiMap", list.get(0));
			mm.put("start", Page.getStartOfPage(pageNo));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "homework/student/errorQuestions";
	}
	
	//查看某个小题的做题记录
	@RequestMapping(value="/student/getQuesRecDetail.html", method=RequestMethod.POST)
	public String getQuesRecord(int quesId, int quesType, HttpServletRequest request, ModelMap mm) {
		mm.put("details", ahs.getQuesRecord(getSessionUser(request).getUserId(), quesId));
		mm.put("quesType", quesType);
		return "homework/student/quesRecDetail";
	}
}