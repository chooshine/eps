package com.eps.web.homework;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.domain.EHomework;
import com.eps.domain.EHomeworkClass;
import com.eps.domain.EHomeworkQuestion;
import com.eps.domain.EOption;
import com.eps.domain.EQuestion;
import com.eps.domain.User;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.homework.TeacherHomeworkService;
import com.eps.service.network.ExamIntelligentlyChooseService;
import com.eps.service.network.NetWorkService;
import com.eps.service.network.NetworkLibraryService;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Controller
public class TeacherHomeworkController extends BaseController {

	@Autowired private TeacherHomeworkService ths;
	@Autowired private NetWorkService nws;
	@Autowired private ExamAfterService eas;
	@Autowired private NetworkLibraryService nls;
	@Autowired private ExamIntelligentlyChooseService eics;
	
	//进入教师课后作业模块的首页
	@RequestMapping(value="/homework/entrance.html")
	public String toIndex(HttpServletRequest request, ModelMap mm) {
		//判断是老师还是学生
		User user = getSessionUser(request);
		int userType = user.getUserType();
		if (userType==10 && ths.existHomeworkServiceOfTeacherSchool(user.getUserId())) {//如果是老师
			mm.put("hwStatistic", ths.getHwStatisticByUserId(user.getUserId()));
			//获得己有的所有科目
			mm.put("sortList", eas.getAllSortNoAndNameService());
			//查询老师所教的科目
			mm.put("ofterSort",nws.getTeachsOfTeacher(user.getUserId()));
			//得到所有年级
			mm.put("allGrades", nws.getAllGrades());
			//获得老师所教的年级
			mm.put("grades",nws.getGradeByTeacher(user.getUserId()));
			return "homework/teacher/index";
		} else if (userType==20 && ths.existHomeworkServiceOfStudentSchool(user.getUserId())) {//如果是学生
			return "redirect:/homework/student/showRecentlyHomeworks.html";
		}
		return "homework/unopened";
	}
	
	//进入编辑试卷的页面
	@RequestMapping(value="/homework/teacher/edithomework.html")
	public String toEditHomework(HttpServletRequest request, ModelMap mm) {
		return returnToEditHomework(mm, request);
	}
	private String returnToEditHomework(ModelMap mm, HttpServletRequest request) {
		User user = getSessionUser(request);
		//判断hwId是否存在，存在则说明作业存在，则从数据库查询相关信息，否则，创建作业
		Object hwId = request.getParameter("hwId");
		String returnUrl = "homework/teacher/edithomework";
		String way = request.getParameter("way");
		LStrMap<Object> hwInfo = null;
		if(hwId != null) {
			hwInfo = ths.getHwInfoByHwId(Integer.parseInt(hwId.toString()));
			mm.put("qiMap", ths.getFullHwInfoByHwId(Integer.parseInt(hwId.toString()), 0));
		} else {
			EHomework eHomework = new EHomework();
			eHomework.setHwName(request.getParameter("hwName"));
			eHomework.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
			eHomework.setGradeLevel(Integer.parseInt(request.getParameter("gradeLevel")));
			eHomework.setCreator(user.getUserId());
			eHomework.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
			eHomework = ths.saveHomework(eHomework);
			if (way!=null && way.equals("auto")) {//如果下一步是自动选题，则要重定向到自动选题
				returnUrl = "redirect:/homework/autoCreate.html?hwId="+eHomework.getHwId()+"&subjectId="+eHomework.getSubjectId();
			} else {
				hwInfo = ths.getHwInfoByHwId(eHomework.getHwId());
				mm.put("qiMap", ths.getFullHwInfoByHwId(eHomework.getHwId(), 0));
				returnUrl = "redirect:/homework/teacher/edithomework.html?hwId="+eHomework.getHwId()+"&oldHwId="+0;;
			}
		}
		if (way==null || !way.equals("auto")) {//如果下一步要进入自动选题页面，则不需要进行下面的操作
			String type = request.getParameter("type");
			if(type != null && type.equals("see")){
				mm.put("type", type);
			}
			
			mm.put("hwInfo", hwInfo);
			mm.put("referUrl", request.getHeader("referer"));
			mm.put("quesTypes", nws.getTypeSubjects(Integer.parseInt(hwInfo.get("subject_id").toString())));
		}
		return returnUrl;
	}
	
	//进入作业自动选题界面
	@RequestMapping(value="/homework/autoCreate.html", method=RequestMethod.GET)
	public String toAutoCreate(int hwId, int subjectId, ModelMap mm) {
		mm.put("hwId", hwId);
		mm.put("subjectId", subjectId);
		mm.put("typeSubjects", nws.getTypeSubjects(subjectId));
		mm.put("knowledgePoints", eics.formatKnowledgepoints(nws.getKnowledgePointsBySubjectId(subjectId)));
		return "homework/teacher/autoCreate";
	}
	@RequestMapping(value="/homework/autoCreate.html", method=RequestMethod.POST)
	public String autoCreateHw(int hwId, int subjectId, String tsIds, String quesNums, String kps, float diff, ModelMap mm) {
		//转换json数据成list
		Gson gson = new GsonBuilder().create();
		ArrayList<String> tsIdList = gson.fromJson(tsIds, ArrayList.class);
		ArrayList<String> quesNumList = gson.fromJson(quesNums, ArrayList.class);
		ArrayList<ArrayList<String>> kpList = gson.fromJson(kps, ArrayList.class);
		
		//存储已经用过小题
		List<Integer> usedQues = ths.getQuesIdsOfHw(hwId);//获取作业已有小题的列表
		if (usedQues==null || usedQues.size()==0) {
			usedQues = new ArrayList<Integer>();
			usedQues.add(0);//默认有一个为0的小题编号，用于在还没有需要排除的小题时使用
		}
		
		//声明一个EExamQuestion对象，用于传递信息
		EHomeworkQuestion ehq = new EHomeworkQuestion();
		ehq.setHwId(hwId);
		int lastMTopic = ths.getLastMTopicNumOfHw(hwId);//当前作业的最后一个小题
		ehq.setmTopic(lastMTopic);
		
		//遍历所有题型，将数据按题型拆分
		for (int i=0, size=tsIdList.size(); i<size; i++) {
			int tsId = Integer.parseInt(tsIdList.get(i));//题型
			int quesNum = Integer.parseInt(quesNumList.get(i));//小题数量
			List<String> quesKpList = kpList.get(i);//考点
			
			if (quesKpList!=null && quesKpList.size()>0) {//当当前大题有考点时才进行下面的操作
				//得到该题型的每个考点对应的题目数量以及余出的题目数量
				int avg = quesNum/quesKpList.size();
				int mod = quesNum%quesKpList.size();
				if (avg != 0) {
					for (int j=0; j<quesKpList.size(); j++) {//查询随机小题并对查出的题目进行处理
						ArrayList<String> tempList = new ArrayList<String>();
						tempList.add(quesKpList.get(j));
						List<LStrMap<Object>> quesList = nws.getRandomQues(tsId, tempList, avg, usedQues, diff);
						if (quesList.size() > 0) {
							dealNormalQues(usedQues, quesList, ehq, 1f);//对普通小题的处理（非材料下小题）
							dealArtAndQues(usedQues, quesList, ehq, 1);//对材料及材料下小题的处理
						}
					}
				}
				if (mod != 0) {//查询余下数量的随机题目
					List<LStrMap<Object>> quesList = nws.getRandomQues(tsId, quesKpList, mod, usedQues, diff);
					if (quesList.size() > 0) {
						dealNormalQues(usedQues, quesList, ehq, 1f);//对普通小题的处理（非材料下小题）
						dealArtAndQues(usedQues, quesList, ehq, 1f);//对材料及材料下小题的处理
					}
				}
			}
		}
		
		int addNum = ehq.getmTopic()-lastMTopic;
		if (addNum > 0) {//当小题号有更改的时候更新作业的小题数量
			ths.updateHomeworkTopicNumByHwId(hwId, addNum);
		}
		mm.put("hwId", hwId);
		return "redirect:/homework/teacher/edithomework.html";
	}
	/**
	 * 处理材料以及材料下小题
	 * @param usedQues 已用过题目
	 * @param paramList 参数列表
	 * @param quesList 小题列表
	 * @param ehq 小题对象
	 * @param defaultScore 大题默认分数，如果小题是填空题，则每个空格的选项分数为该参数值，否则，小题分数为该参数值
	 * @return
	 */
	private void dealArtAndQues(List<Integer> usedQues, List<LStrMap<Object>> quesList, 
			EHomeworkQuestion ehq, float defaultScore) {
		Iterator<LStrMap<Object>> iterator = quesList.iterator();
		while (iterator.hasNext()) {
			LStrMap<Object> tempQues = iterator.next();//当前小题
			if (tempQues.get("ques_type").toString().equals("6")) {//对材料的处理
				String remark = tempQues.get("remark").toString();
				ehq.setQuesId(Integer.parseInt(tempQues.get("ques_id").toString()));
				usedQues.add(ehq.getQuesId());
				ehq.setmTopic(ehq.getmTopic()+1);
				dealFitQues(iterator, tempQues, ehq, defaultScore);
				ehq.setmTopic(ehq.getmTopic()-1);//先将小题号减一，以保证第一个材料下小题的小题号与材料的小题号相同
				List<String> quesIdList = Arrays.asList(remark.split(","));
				dealQuesOfArt(iterator, quesIdList, ehq, defaultScore);
			}
		}
	}


	/**
	 * 处理材料下小题
	 * @param quesList 小题列表
	 * @param quesIdList 材料下的所有小题的小题列表
	 * @param ehq 小题对象
	 * @param defaultScore
	 * @return
	 */
	private void dealQuesOfArt(Iterator<LStrMap<Object>> iterator, List<String> quesIdList, 
			EHomeworkQuestion ehq, float defaultScore) {
		while (iterator.hasNext()) {
			LStrMap<Object> tempQues  = iterator.next();
			if (quesIdList.contains(tempQues.get("ques_id").toString())) {
				ehq.setQuesId(Integer.parseInt(tempQues.get("ques_id").toString()));
				ehq.setmTopic(ehq.getmTopic()+1);
				dealFitQues(iterator, tempQues, ehq, defaultScore);
			}
		}
	}
	/**
	 * 处理符合条件的小题，并从小题列表中删除当前小题
	 * @param iterator 小题列表的遍历器
	 * @param tempQues 需要进行处理的小题
	 * @param defaultScore 大题默认分数，如果小题是填空题，则每个空格的选项分数为该参数值，否则，小题分数为该参数值
	 * @return 当前小题号
	 */
	private void dealFitQues(Iterator<LStrMap<Object>> iterator, LStrMap<Object> tempQues, 
			EHomeworkQuestion ehq, float defaultScore) {
		int optionNum = Integer.parseInt(tempQues.get("option_num").toString());//选项数量
		String quesType = tempQues.get("ques_type").toString();
		if (!quesType.equals("4")) {
			ehq.setScore(defaultScore);
			ehq.setoScore(""+(int)defaultScore);
		} else {
			ehq.setScore((float)(optionNum*defaultScore));
			String oScore = "";
			for (int k=0; k<optionNum; k++)	oScore += ((int)defaultScore+",");
			ehq.setoScore(oScore.substring(0, oScore.length()-1));
		}
		
		ths.saveHomeworkQuestion(ehq);//保存一条小题记录
		toUpdateQuestionsMTopic(""+ehq.getmTopic(), ""+ehq.getQuesId(), "add", ""+ehq.getHwId());
		iterator.remove(); 			//从小题列表删除当前小题
	}
	/**
	 * @param usedQues 已用过小题列表
	 * @param typeId 大题编号
	 * @param quesList 小题列表 
	 * @param defaultScore 大题默认分数
	 * @return
	 */
	private void dealNormalQues(List<Integer> usedQues, List<LStrMap<Object>> quesList, 
			EHomeworkQuestion ehq, float defaultScore) {
		Iterator<LStrMap<Object>> iterator = quesList.iterator();
		while (iterator.hasNext()) {//遍历所有小题
			LStrMap<Object> tempQues = iterator.next();//当前小题
			String quesType = tempQues.get("ques_type").toString();
			Object remark = tempQues.get("remark");
			
			if (!quesType.equals("6") && remark==null) {//对普通小题的处理
				ehq.setQuesId(Integer.parseInt(tempQues.get("ques_id").toString()));
				ehq.setmTopic(ehq.getmTopic()+1);
				usedQues.add(ehq.getQuesId());//当前小题存为已用过小题
				dealFitQues(iterator, tempQues, ehq, defaultScore);
			}
		}
	}
	
	@RequestMapping(value="/homework/teacher/getKonwledgePointsBySubjectId.html", method=RequestMethod.POST)
	public String getKonwledgePointsBySubjectId(int subjectId, ModelMap mm) {
		//得到一级知识点
		List<LStrMap<Object>> oneLayerKnowledges = nws.getOneLayerKnowledgePointsBySubjectId(subjectId);
		//得到二级知识点
		List<LStrMap<Object>> twoLayerKnowledges = null;
		if (oneLayerKnowledges.size() > 0) {
			twoLayerKnowledges = nws.getKnowledgePointsByParentId(Integer.parseInt(oneLayerKnowledges.get(0).get("kp_id").toString()));
		}
		//得到三级知识点
		List<LStrMap<Object>> threeLayerKnowledges = null;
		if ((twoLayerKnowledges!=null) && (twoLayerKnowledges.size()>0)) {
			threeLayerKnowledges = nws.getKnowledgePointsByParentId(Integer.parseInt(twoLayerKnowledges.get(0).get("kp_id").toString()));
		}
		mm.put("oneLayerKnowledges", oneLayerKnowledges);
		mm.put("twoLayerKnowledges", twoLayerKnowledges);
		mm.put("threeLayerKnowledges", threeLayerKnowledges);
		
		return "homework/teacher/knowledgePoints/knowledgePointsSelect";
	}
	
	//上级知识点改变的时候，得到下级是知识点
	@RequestMapping(value="/homework/edithomework/getKnowledgePoints.json", method=RequestMethod.POST)
	public String getNextLayerKnowledgePoints(ModelMap mm, HttpServletRequest request) {
		int parentKpId = Integer.parseInt(request.getParameter("parentKpId").toString());
		mm.put("kps", nws.getKnowledgePointsByParentId(parentKpId));
		return "jsonView";
	}
		
	@RequestMapping(value="/homework/operateHomework.json", method=RequestMethod.POST)
	public String operateHomework(int hwId, String hwName, HttpServletRequest request, ModelMap mm) {
		ths.updateHomeworkName(hwId, hwName);
		return "jsonView";
	}
	
	//对小题的操作，包括新增、删除、更新等
	@RequestMapping(value="/homework/edithomework/operateQustion.json",method=RequestMethod.POST)
	public String saveOneQues(HttpServletRequest request,EQuestion eQuestion,EOption eOption, EHomeworkQuestion eHomeworkQuestion, ModelMap mm){
		User user = getSessionUser(request);
		eQuestion.setCreator(user.getUserId()+"");
		String subType = request.getParameter("subType");
		List<Integer> idList = new ArrayList<Integer>();
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		eQuestion.setInputTime(dateformat.format(date));
		
		int sQuesId = -1;						//用于记录新插入的小题的小题编号，用于在插入阅读理解下的小题时更新文章的remark字段
		int oldSquesId = eQuestion.getQuesId();	//用于记录小题原有的编号，如果是更新阅读理解的小题，则需要删除文章对应的remark中的原有小题编号
		
		int artId2 = 0;
		//artId不是null，说明是阅读理解，要将文章编号存到小题的remark字段中
		if(request.getParameter("artId") != null){
			artId2 = Integer.parseInt(request.getParameter("artId"));
			eQuestion.setRemark(String.valueOf(artId2));
		}
		
		if("add".equals(subType)){
			idList = ths.saveQuestion(eHomeworkQuestion,eQuestion,eOption);//保存一条小题记录和一条作业关系记录
			ths.updateHomeworkTopicNumByHwId(eHomeworkQuestion.getHwId(), 1);//更新作业名称、科目、小题数量
			mm.put("idList", idList);
			sQuesId = idList.get(0);
		} else if("articleAdd".equals(subType)){			 //如果是增加文章
			int artId = ths.saveArticle(eQuestion,eHomeworkQuestion);//新增小题和作业小题关系并返回小题号
			mm.put("artId", artId);
		} else if("articleUpdate".equals(subType)){			 //文章已经存在，本次是更新文章内容
			eas.updateArtlicle(eQuestion);
			//更新材料下各个小题的知识点
			eas.updateQuestionsKnowledgePointOfArticle(eQuestion.getQuesId(), eQuestion.getKnowledgePoint());
		} else if("update".equals(subType)){
			ths.deleteOneQuestion(eHomeworkQuestion.getHwId(), eHomeworkQuestion.getQuesId());
			idList = ths.saveQuestion(eHomeworkQuestion, eQuestion, eOption);
			mm.put("idList", idList);
			sQuesId = idList.get(0);
		} else if("delete".equals(subType)){
			ths.updateHomeworkTopicNumByHwId(eHomeworkQuestion.getHwId(), -1);
			ths.deleteOneQuestion(eHomeworkQuestion.getHwId(), eQuestion.getQuesId());
			sQuesId = eQuestion.getQuesId();
		}
		
		//如果对文章下的小题进行了增、删、改等动作，则要对相应的文章进行remark的更新
		//阅读理解时，判断属于哪一个文章
		if(artId2 != 0){
			//如果是更新，将新增材料的remak
			eas.updateArtRemark(artId2,sQuesId,subType,oldSquesId);
		}
		return "jsonView";
	}
	
	//当新增小题或者是删除已有小题时，更新该小题之后所有小题的题号，包括文章的题号
	@RequestMapping(value="/updateHwQuestionsMTopic.json", method=RequestMethod.POST)
	public void toUpdateQuestionsMTopic(String mTopic, String quesId, String operate, String hwId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("m_topic", Integer.parseInt(mTopic));
		map.put("ques_id", Long.parseLong(quesId));
		map.put("hw_id", Long.parseLong(hwId));
		if (operate.equals("add")) {
			map.put("add_num", 1);
			ths.updateMTopics(map);
		} else {
			map.put("add_num", -1);
			ths.updateMTopics(map);
		}
	}
	
	//删除一个文章的所有小题及文章本身
	@RequestMapping(value="/homework/deleteArticle.json", method=RequestMethod.POST)
	public String deleteHwArticleQuestions(HttpServletRequest request,int hwId, String quesIds, String mTopic, String mTopicNum) {
		//删除文章及小题
		ths.deleteArticleAndQuestions(hwId, quesIds);
		//如果小题号是none，说明当前文章下没有小题，则不用更新小题
		if(!mTopic.equals("none")) {
			//更新文章之后的小题的小题号
			ths.updateMTopicAfterArticle(hwId, Integer.parseInt(mTopic), Integer.parseInt(mTopicNum));
			//更新e_homework表的小题数
			ths.updateHomeworkTopicNumByHwId(hwId, -Integer.parseInt(mTopicNum));
		}
		
		return "jsonView";
	}

	@RequestMapping(value="/homework/moveQuestion.json", method=RequestMethod.POST)
	public String changeQuestionssMTopic(int hwId, int quesId, int moveNum) {
		//判断moveNum是正还是负，是正，说明是上移，是负，说明是下移
		if (moveNum > 0) {
			ths.updatePrevQuestionsMTopic(hwId, quesId, moveNum);
			ths.updateQuestionMTopic(hwId, quesId, moveNum);
		} else {
			ths.updateNextQuestionsMTopic(hwId, quesId, moveNum);
			ths.updateQuestionMTopic(hwId, quesId, moveNum);
		}
		return "jsonView";
	}
	
	//打开布置作业弹框时，查询班级信息
	@RequestMapping(value="/homework/edithw/getClasses.json", method=RequestMethod.POST)
	public String getClassesWhenSettleHw(int hwId, int subjectId, HttpServletRequest request, ModelMap mm) {
		//得到作业是否可自动批改的标志
		mm.put("autoCorrectFlag", ths.getHwAutoCorrectFlag(hwId, getSessionUser(request).getUserId()));
		//得到作业是否可布置的标志
		mm.put("usefulFlag", ths.getHwUsefulFlag(hwId, getSessionUser(request).getUserId()));
		//得到班级列表
		List<LStrMap<Object>> list = ths.getClassList(hwId, subjectId, getSessionUser(request).getUserId());
		if (list.size() > 0) {
			mm.put("chooseCorrect", list.get(0).get("correct"));//得到曾经所选的标记
		}
		mm.put("classList", list);
		return "jsonView";
	}
	
	@RequestMapping(value="/homework/teacher/saveSettle.json", method=RequestMethod.POST)
	public String saveSettle(int hwId, String classIds, String startTimes, String endTimes, String correct, HttpServletRequest request, ModelMap mm) {
		long userId = getSessionUser(request).getUserId();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		Map<String, String> classIdsMap = (Map<String, String>)gson.fromJson(classIds, Map.class);
		Map<String, String> startTimesMap = gson.fromJson(startTimes, Map.class);
		Map<String, String> endTimesMap = gson.fromJson(endTimes, Map.class);
		
		//清空原有还未到作业开始时间的作业安排
		ths.clearUnStartedHomeworkArranges(hwId);
		
		Iterator<String> iterator = classIdsMap.keySet().iterator();
		while (iterator.hasNext()) {
			String classId = classIdsMap.get(iterator.next()).toString();
			EHomeworkClass eHomeworkClass = new EHomeworkClass();
			eHomeworkClass.setHwId(hwId);
			eHomeworkClass.setClassId(Integer.parseInt(classId));
			eHomeworkClass.setStartTime(startTimesMap.get(classId));
			eHomeworkClass.setEndTime(endTimesMap.get(classId));
			eHomeworkClass.setCreator(userId);
			eHomeworkClass.setCorrect(Integer.parseInt(correct));
			
			//删除原有记录
			ths.deleteHwClass(eHomeworkClass);
			//保存新记录
			ths.saveHomeworkClass(eHomeworkClass);
		}
		
		//更改作业表的发布状态及发布时间
		ths.updateHomeworkToRelease(hwId);
		//更新作业小题的发布状态
		ths.updateQuestionsReleaseStatusOfHomework(hwId, 1);
		//得到作业相关信息
		if (classIdsMap.size() > 0) {
			List<LStrMap<Object>> classNameList = ths.getSettleClassNameList(hwId, userId);
			if (classNameList.size() > 0) {
				mm.put("hwName", classNameList.get(0).get("hw_name"));
				mm.put("correct", classNameList.get(0).get("correct"));
				mm.put("classNames", classNameList);
			}
		} else {
			LStrMap<Object> hwInfoMap = ths.getHwInfoByHwId(hwId);
			mm.put("hwName", hwInfoMap.get("hw_name"));
		}
		
		return "jsonView";
	}
	
	//显示还未布置的作业
	@RequestMapping(value="/homework/teacher/unSettleHomeworks.html")
	public String viewAllNetwork(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		mm.put("hwList", ths.getUnSettleHomeworks(user.getUserId()));
		return "homework/teacher/showUnSettleHomeworks";
	}
	
	@RequestMapping(value="homework/teacher/deleteHomework.json")
	public String deleteHomework(String hwId) {
		//删除作业只是设置作业状态为删除状态，实际上并没有从数据库中移除作业
		ths.updateHomeworkToDeletedStatus(Integer.parseInt(hwId));
		return "jsonView";
	}
	
	//进入作业库
	@RequestMapping(value="/homework/teacher/homeworkLibrary.html")
	public String toPaperLibrary(ModelMap mm,HttpServletRequest request){
		User user = getSessionUser(request);
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("近一周", 1);
		map.put("近一个月", 2);
		map.put("近三个月", 3);
		map.put("近半年", 4);
		map.put("全部", 5);
		
		mm.put("releaseTimeMap",map);
		mm.put("ofterSort",nws.getTeachsOfTeacher(user.getUserId()));//获得老师所教的科目
		mm.put("sortList",eas.getAllSortNoAndNameService());	   //获得所有科目
		return "homework/teacher/homeworkLibrary";
	}
	
	//查找老师出的作业
	@RequestMapping(value="/homework/teacher/getTeacherHomeworks.html", method=RequestMethod.POST)
	public String searchHomeworksOfTeacher(int releaseTime, int subjectId, HttpServletRequest request, ModelMap mm) {
		Calendar calendar = Calendar.getInstance();
		switch (releaseTime) {
		case 1:
			calendar.add(Calendar.DATE, -7);
			break;
		case 2:
			calendar.add(Calendar.MONTH, -1);
			break;
		case 3:
			calendar.add(Calendar.MONTH, -3);
			break;
		case 4:
			calendar.add(Calendar.MONTH, -6);
			break;
		case 5:
			calendar.add(Calendar.YEAR, -1);
			break;
		default:
			break;
		}
		mm.put("homeworkList", ths.getHomeworksOfTeacher(getSessionUser(request).getUserId(), calendar.getTime(), subjectId));
		
		return "homework/teacher/homeworkList";
	}
	
	//设置作业共享
	@RequestMapping(value="/homework/teacher/setHwShareFlag.json", method=RequestMethod.POST)
	public String setHwShareFlag(int hwId, String operateType) {
		if (operateType.equals("share")) {
			ths.setHwShareFlag(hwId, 1);
		} else {
			ths.setHwShareFlag(hwId, 0);
		}
		
		return "jsonView";
	}
	
	//进入作业共享页面
	@RequestMapping(value="/homework/teacher/sharedHomeworks.html")
	public String showSharedHomeworks(HttpServletRequest request, ModelMap mm) {
		//筛选条件需要的数据
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("近一周", 1);
		map.put("近一个月", 2);
		map.put("近三个月", 3);
		map.put("近半年", 4);
		map.put("全部", 5);
		
		mm.put("releaseTimeMap",map);
		mm.put("areaList", eas.getAreaFromCodeService("area"));
		mm.put("schoolList", nls.getSchoolsByArea("5"));
		mm.put("ofterSort",nws.getTeachsOfTeacher(getSessionUser(request).getUserId()));//获得老师所教的科目
		mm.put("sortList",eas.getAllSortNoAndNameService());	   //获得所有科目
		return "homework/teacher/sharedHomeworks";
	}
	
	//搜索共享的作业
	@RequestMapping(value="/homework/teacher/searchSharedHomeworks.html", method=RequestMethod.POST)
	public String searchSharedHomeworks(ModelMap mm,HttpServletRequest request){
		long userId = getSessionUser(request).getUserId();
		int releaseTime = Integer.parseInt(request.getParameter("releaseTime"));
		Calendar calendar = Calendar.getInstance();
		switch (releaseTime) {
		case 1:
			calendar.add(Calendar.DATE, -7);
			break;
		case 2:
			calendar.add(Calendar.MONTH, -1);
			break;
		case 3:
			calendar.add(Calendar.MONTH, -3);
			break;
		case 4:
			calendar.add(Calendar.MONTH, -6);
			break;
		case 5:
			calendar.add(Calendar.YEAR, -1);
			break;
		default:
			break;
		}
		int schoolId = Integer.parseInt(request.getParameter("schoolId"));
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		mm.put("homeworkList", ths.searchSharedHomeworks(calendar.getTime(), schoolId, subjectId, userId));
		return "homework/teacher/sharedHomeworksList";
	}
	
	//复制作业
	@RequestMapping(value="/homework/teacher/copyHomework.html")
	public String copyHomework(int hwId, String hwName, HttpServletRequest request) {
		//复制作业
		EHomework eHomework = new EHomework();
		LStrMap<Object> map = ths.getHwInfoByHwId(hwId);
		eHomework.setHwName(hwName);
		eHomework.setSubjectId((Integer)map.get("subject_id"));
		eHomework.setTopicNum((Integer)map.get("topic_num"));
		eHomework.setRemark(map.get("remark")==null?null:map.get("remark").toString());
		eHomework.setCreator(getSessionUser(request).getUserId());
		eHomework.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
		eHomework.setReferHwId(hwId);
		eHomework.setGradeLevel((Integer)map.get("grade_level"));
		eHomework = ths.saveHomework(eHomework);
		
		//复制作业试题关系
		//得到原有作业试题关系数据
		List<LStrMap<Object>> list = ths.getHwQuesRelationList(hwId);
		for (int i=0; i<list.size(); i++) {
			EHomeworkQuestion eHomeworkQuestion = new EHomeworkQuestion();
			eHomeworkQuestion.setHwId(eHomework.getHwId());
			eHomeworkQuestion.setQuesId((Integer)list.get(i).get("ques_id"));
			eHomeworkQuestion.setmTopic((Integer)list.get(i).get("m_topic"));
			eHomeworkQuestion.setScore((Double)list.get(i).get("score"));
			eHomeworkQuestion.setoScore(list.get(i).get("o_score")==null?null:list.get(i).get("o_score").toString());
			eHomeworkQuestion.setRemark(list.get(i).get("remark")==null?null:list.get(i).get("remark").toString());
			ths.saveHomeworkQuestion(eHomeworkQuestion);
		}
		return "redirect:/homework/teacher/edithomework.html?hwId="+eHomework.getHwId()+"&oldHwId="+hwId;
	}
	
	//显示已经结束的作业
	@RequestMapping(value="/homework/teacher/pastHomeworks.html")
	public String showPastHomeworks(HttpServletRequest request, ModelMap mm) {
		mm.put("unmarkedInfo", ths.getPastHomeworks(getSessionUser(request).getUserId()));
		return "homework/teacher/pastHomeworks";
	}
	
	//显示某个班级所有交了作业的学生的作业批改情况
	@RequestMapping(value="/homework/teacher/correctInfoOfOneClass.html", method=RequestMethod.POST)
	public String showEnterExamStudents(int hwId, int classId, ModelMap mm) {
		mm.put("title", "班级信息");
		mm.put("classBasicInfo", ths.getClassBasicCorrectInfo(hwId, classId));
		mm.put("students", ths.getCorrectInfoOfOneClass(hwId, classId));
		return "homework/teacher/correctInfoOfOneClass";
	}
	
	//教师批改作业
	@RequestMapping(value="/homework/teacher/correctHomework.html")
	public String gradePapers(HttpServletRequest request, ModelMap mm, int hwId, int hwRecId) {
		//得到本次查看的类型，查看全部或只查看主观题，如果是只查看主观题，就设置quesType为0，否则，quesType为-1
		int quesType = 0;
		mm.put("view", "查看全部");
		if (request.getParameter("questype") == null) {
			quesType = -1;
			mm.put("view", "批主观题");
		}
		mm.put("hwInfo", ths.getHwInfoByHwId(hwId));
		mm.put("hwRecId", hwRecId);
		mm.put("qiMap", ths.getStudentHomeworkInfo(hwId, hwRecId, quesType));
		mm.put("questionsInfo", ths.getQuestionsInfo(hwRecId));
		mm.put("questionNumber", request.getParameter("questionNumber"));
		mm.put("correctInfo", ths.getCorrectNum(hwRecId));
		mm.put("mtopicId", request.getParameter("mtopicId"));
		return "homework/teacher/correctHomework";
	}
	
	//老师批改作业之后选择保存或者提交批改结果的处理
	@RequestMapping(value="/homework/teacher/correctHomework.json", method=RequestMethod.POST)
	public String saveGradeResult(HttpServletRequest request, ModelMap mm) {
		int hwRecId = Integer.parseInt((request.getParameter("hwRecId")));
		long userId = this.getSessionUser(request).getUserId();
		String commentJson = request.getParameter("commentJson");
		
		Gson gson = new GsonBuilder().create();
		Map<String, String> quesScoreMap = gson.fromJson(request.getParameter("quesScoreJson"), Map.class);
		Map<String, String> oScorenMap = gson.fromJson(request.getParameter("oScoreJson"), Map.class);
		Map<String, String> commentMap = gson.fromJson(commentJson, Map.class);
		//Map<String, String> recordsMap = gson.fromJson(request.getParameter("records"), Map.class);
		
		//保存和提交的不同处理
		if(request.getParameter("operateType").equals("save")){
			ths.updateEHomeworkRecordDetailInfo(hwRecId, quesScoreMap, oScorenMap, commentMap);
			//更新考生的考试记录的评分字段，设为已阅部分
			//teacherHomeworkService.updateHomeworkRecordCorrectFlag(hwRecId, 1, userId, 0, null, null, null, null);
			ths.updateHomeworkRecordCorrectFlag(hwRecId, 1, userId, 0, null);
		} else {
			int star = Integer.parseInt(request.getParameter("star"));
			try {
				String comment = URLDecoder.decode(request.getParameter("comment"), "utf-8");
				ths.updateEHomeworkRecordDetailInfo(hwRecId, quesScoreMap, oScorenMap, commentMap);
				//更新考生的考试记录的评分字段，设为已批改完，并设置remark为作业的评语
				//teacherHomeworkService.updateHomeworkRecordCorrectFlag(hwRecId, 2, userId, star, comment, recordsMap.get("0"), recordsMap.get("1"), recordsMap.get("2"));
				ths.updateHomeworkRecordCorrectFlag(hwRecId, 2, userId, star, comment);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		
		return "jsonView";
	}
	
	//作业批改完成
	@RequestMapping(value="/homework/teacher/correctHomeworkSuccess.html", method=RequestMethod.POST)
	public String correctHwSuccess(int hwRecId, HttpServletRequest request ,ModelMap mm) {
		mm.put("title", "批阅完成");
		mm.put("hwRecId", hwRecId);
		//得到学生信息及得分
		mm.put("student", ths.getStudentInfo(hwRecId));
		//得到班级未评阅信息
		mm.put("classInfo", ths.getClassUnmarkedInfo(hwRecId));
		//得到下一张应批阅的作业
		mm.put("nextHomework", ths.getNextHomework(hwRecId));
		return "homework/teacher/correctHomeworkSuccess";
	}
	
	//查看作业报告
	@RequestMapping(value="/homework/teacher/homeworkStatistic.html")
	public String viewReportOfHomework(int hwId, int classId, ModelMap mm) {
		mm.put("title", "作业报告");
		//已交和未交学生饼图数据
		LStrMap<Object> map = ths.getClassBasicCorrectInfo(hwId, classId);
		int actualNum = Integer.parseInt(map.get("actual_num").toString());//交作业人数
		int classNum = Integer.parseInt(map.get("class_num").toString());//全班总人数
		mm.put("classNum", classNum);
		int uncommitNum = classNum - actualNum;//未交作业人数
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map1 = LStrMap.newInstance();
		map1.put("type", "已交学生");
		map1.put("stuNum", actualNum);
		LStrMap<Object> map2 = LStrMap.newInstance();
		map2.put("type", "未交学生");
		map2.put("stuNum", uncommitNum);
		list.add(map1);
		list.add(map2);
		Gson gson = new GsonBuilder().create();
		mm.put("commitPieData", gson.toJson(list));
		List<LStrMap<Object>> unCommitStudentsList = ths.getUnCommitStudents(hwId, classId);
		mm.put("uncommitStusJson", gson.toJson(unCommitStudentsList));
		//未交作业学生数据
		mm.put("uncommitStus", ths.getUnCommitStudents(hwId, classId));
		
		//星星对应的学生数量
		List<LStrMap<Object>> starStuNumList = ths.getStudentsNumOfStar(hwId, classId);
		mm.put("starStuNumList", starStuNumList);
		//星星对应的学生数量柱状图数据
		mm.put("starStusColumnData", gson.toJson(starStuNumList));
		
		//每个小题的错误人数
		List<LStrMap<Object>> erroStudentsNumList = ths.getStudentsNumOfQuestions(hwId, classId);
		mm.put("studentsNumOfQuestions", gson.toJson(erroStudentsNumList));
		mm.put("hwId", hwId);
		mm.put("classId", classId);
		mm.put("classBasicInfo", ths.getClassBasicCorrectInfo(hwId, classId));
		
		//折线图数据
		List<Map<String, Object>> numOfDurationList = ths.getStuNumInArea(hwId, classId);
		mm.put("durationStuLineChartData", gson.toJson(numOfDurationList));
		return "homework/teacher/homeworkStatistic";
	}
	
	//查看提交作业情况的详情
	@RequestMapping(value="/homework/teacher/statisticDialogModules/commitModules.html")
	public String viewCommitModules(int hwId, int classId, ModelMap mm) {
		//已交学生数据
		mm.put("commitStus", ths.getCorrectInfoOfOneClass(hwId, classId));
		//未交作业学生数据
		mm.put("uncommitStus", ths.getUnCommitStudents(hwId, classId));
		return "homework/teacher/statisticDialogModules/commitModules";
	}
	//查看星星分布情况的详情
	@RequestMapping(value="/homework/teacher/statisticDialogModules/starModules.html")
	public String viewStarModules(int hwId, int classId, ModelMap mm) {
		//班级总人数
		mm.put("classNum", ths.getClassNum(classId));
		//星星对应的学生数量
		mm.put("starStuNumList", ths.getStudentsNumOfStar(hwId, classId));
		//各个数量星星对应的学生
		mm.put("studentsOfStarMap", ths.getStudentsOfStar(hwId, classId));
		return "homework/teacher/statisticDialogModules/starModules";
	}
	//查看小题错误学生情况的详情
	@RequestMapping(value="/homework/teacher/statisticDialogModules/quesErrorModules.html")
	public String viewQuesErrorModules(int hwId, int classId, ModelMap mm) {
		//班级总人数
		mm.put("classNum", ths.getClassNum(classId));
		//每个小题的错误人数
		mm.put("erroStudentsNumList", ths.getStudentsNumOfQuestions(hwId, classId));
		//每个小题的错误学生的名字
		mm.put("errorStudentsInDialog", ths.getErrorStudentsOfQuestions(hwId, classId));
		return "homework/teacher/statisticDialogModules/quesErrorModules";
	}
	//查看小题学生做题时长情况的详情
	@RequestMapping(value="/homework/teacher/statisticDialogModules/timeModules.html")
	public String viewTimeModules(int hwId, int classId, ModelMap mm) {
		//班级总人数
		mm.put("classNum", ths.getClassNum(classId));
		//学生分布数量
		mm.put("numOfDurationList", ths.getStuNumInArea(hwId, classId));
		//在某段时长范围内完成作业的学生
		mm.put("studentsInDurationMap", ths.getStudentsInDuration(hwId, classId));
		return "homework/teacher/statisticDialogModules/timeModules";
	}
	
	//作业详情
	@RequestMapping(value="/homework/teacher/homeworkDetail.html")
	public String homeworkDetail(int hwId, int classId, HttpServletRequest request, ModelMap mm) {
		mm.put("hwInfo", ths.getHwInfoByHwId(hwId));
		mm.put("qiMap", ths.getHomeworkInfo(hwId));
		mm.put("hwId", hwId);
		mm.put("classId", classId);
		
		Gson gson = new GsonBuilder().create();
		//每个小题的错误人数和正确人数
		mm.put("errorInfoOfQuestions", gson.toJson(ths.getErrorInfoOfQuestions(hwId, classId)));
		//每个小题的错误学生的名字
		mm.put("errorStudentsOfQuestions", gson.toJson(ths.getErrorStudentsOfQuestions(hwId, classId)));
		//每个小题选择每个选项的人数
		mm.put("numOfOptions", gson.toJson(ths.getNumOfOptions(hwId, classId)));
		//每个小题选择每个选项的学生名字
		//mm.put("studentsOfOptions", teacherHomeworkService.getStudentsOfOptions(hwId, classId));
		return "homework/teacher/homeworkDetail";
	}
	
	//点击小题错误学生饼图或者选项选择人数饼图
	@RequestMapping(value="/homework/teacher/showDetailDialog.html")
	public String showDetailDialog(int hwId, int classId, int quesId, ModelMap mm) {
		//得到班级总人数
		int classNum = ths.getClassNum(classId);
		//得到小题正确人数
		int rightNum = ths.getRightNumOfQuestion(hwId, classId, quesId);
		//班级错误人数
		int faultNum = classNum - rightNum;
		mm.put("classNum", classNum);
		//得到小题正确的学生姓名
		mm.put("correctStudents", ths.getStudentsOfQuestionRight(hwId, classId, quesId));
		//得到小题错误学生的姓名
		mm.put("errorStudents", ths.getStudentsOfQuestionError(hwId, classId, quesId));
		
		//得到选项对应学生数
		mm.put("studentsNumOfOptions", ths.getStudentsNumOfOptions(hwId , classId, quesId));
		//得到选项对应学生姓名
		mm.put("studentsOfOptions", ths.getStusNameOfOptions(hwId, classId, quesId));
		return "homework/teacher/detailDialog";
	}
	
	@RequestMapping(value="/homework/edithomework/copyArticle.json", method=RequestMethod.POST)
	public String copyQuestions(int hwId, String subjectNo, String resource, int gradeLevel,
			String mTopics, String quesTypes, String quesContents, String quesRefers,String oScores, String scores,
			String knowledgePoints, String keywords, String optionNums, String optNos, String optContents, String optRefers, HttpServletRequest request, ModelMap mm) {
		Gson gson = new GsonBuilder().create();
		Map mtopicMap = gson.fromJson(mTopics, Map.class);
		Map quesTypeMap = gson.fromJson(quesTypes, Map.class);
		Map quesContentMap = gson.fromJson(quesContents, Map.class);
		Map quesReferMap = gson.fromJson(quesRefers, Map.class);
		Map oScoreMap = gson.fromJson(oScores, Map.class);
		Map scoreMap = gson.fromJson(scores, Map.class);
		Map knowledgePointMap = gson.fromJson(knowledgePoints, Map.class);
		Map keywordMap = gson.fromJson(keywords, Map.class);
		Map optionNumMap = gson.fromJson(optionNums, Map.class);
		Map optNoMap = gson.fromJson(optNos, Map.class);
		Map optContentMap = gson.fromJson(optContents, Map.class);
		Map optReferMap = gson.fromJson(optRefers, Map.class);
		
		int artId = 0;//记录材料编号
		String remark = "";//记录材料的remark
		List<Integer> idList = new ArrayList<Integer>();//记录材料和小题的编号
		for (int i=0,size=mtopicMap.size(); i<size; i++) {
			EHomeworkQuestion eHomeworkQuestion = new EHomeworkQuestion();
			EQuestion eQuestion = new EQuestion();
			EOption eOption = new EOption();
			
			//创建小题
			eQuestion.setQuesContent(quesContentMap.get(i+"")==null?"":quesContentMap.get(i+"").toString());
			eQuestion.setQuesType(quesTypeMap.get(i+"")==null?"":quesTypeMap.get(i+"").toString());
			eQuestion.setQuesRefer(quesReferMap.get(i+"")==null?null:quesReferMap.get(i+"").toString());
			eQuestion.setOptionNum(optionNumMap.get(i+"")==null?0:(int)Double.parseDouble(optionNumMap.get(i+"").toString()));
			eQuestion.setKnowledgePoint(knowledgePointMap.get(i+"")==null?"":knowledgePointMap.get(i+"").toString());
			eQuestion.setKeyword(keywordMap.get(i+"")==null?null:keywordMap.get(i+"").toString());
			eQuestion.setCreator(getSessionUser(request).getUserId()+"");
			eQuestion.setGradeLevel(gradeLevel);
			eQuestion.setSubjectNo(subjectNo);
			eQuestion.setResource(resource);
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
			eQuestion.setInputTime(dateformat.format(date));
			if (i != 0) {//如果不是材料，就设置remark
				eQuestion.setRemark(artId+"");
			}
			//创建选项
			
			eOption.setOptNo(optNoMap.get(i+"")==null?"":optNoMap.get(i+"").toString());
			eOption.setOptContent(optContentMap.get(i+"")==null?null:optContentMap.get(i+"").toString());
			eOption.setOptRefer(optReferMap.get(i+"")==null?"":optReferMap.get(i+"").toString());
			
			//创建小题作业关系
			eHomeworkQuestion.setHwId(hwId);
			eHomeworkQuestion.setmTopic(mtopicMap.get(i+"")==null?0:(int)Double.parseDouble(mtopicMap.get(i+"").toString()));
			eHomeworkQuestion.setoScore(oScoreMap.get(i+"")==null?null:oScoreMap.get(i+"").toString());
			eHomeworkQuestion.setScore(scoreMap.get(i+"")==null?0:Double.parseDouble(scoreMap.get(i+"").toString()));
			
			//保存小题、选项和关系
			if (i == 0) {//如果是材料，就记录材料的编号
				ths.saveArticle(eQuestion, eHomeworkQuestion);
				artId = eQuestion.getQuesId();
			} else {//如果不是材料，就记录材料的remark
				ths.saveQuestion(eHomeworkQuestion, eQuestion, eOption);
				remark = remark+eQuestion.getQuesId()+",";
			}
			
			idList.add(eQuestion.getQuesId());
		}
		
		eas.updateArtRemark(artId, remark);//更新材料的remark
		ths.updateHomeworkTopicNumByHwId(hwId, mtopicMap.size()-1);//更新作业小题数量
		
		mm.put("Ids", idList);//返回材料和小题编号
		return "jsonView";
	}
	
	//设置某次作业记录的某个小题的评语录音
	@RequestMapping(value="/setHomeworkCommentRecord.json")
	public String setHomeworkCommentRecord(int hwRecId, int quesId, String path) {
		ths.updateHomeworkRecordDetailCommentRec(hwRecId, quesId, path);
		return "jsonView";
	}
	
	//设置某次作业记录的评语录音
	@RequestMapping(value="/setHomeworkRecordWav.json")
	public String setHomeworkRecordWav(int hwRecId, String path) {
		ths.updateHomeworkRecordCommentRec(hwRecId, path);
		return "jsonView";
	}
	
	//设置小题的录音
	@RequestMapping(value="/setQuesRec.json")
	public String setQuesRec(int quesId, String path) {
		ths.updateQuesRec(quesId, path);
		return "jsonView";
	}
	
}