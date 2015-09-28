package com.eps.web.network;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.dao.MaxValueINcrementer;
import com.eps.domain.EExam;
import com.eps.domain.EExamQuestion;
import com.eps.domain.EOption;
import com.eps.domain.EQuesType;
import com.eps.domain.EQuestion;
import com.eps.domain.ETest;
import com.eps.domain.ETestEexam;
import com.eps.domain.ExamPreviewSets;
import com.eps.domain.User;
import com.eps.domain.ques.Exam;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.network.ExamIntelligentlyChooseService;
import com.eps.service.network.NetWorkService;
import com.eps.service.network.NetWorkStudentService;
import com.eps.utils.HttpHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

import com.eps.web.BaseController;
import com.eps.web.corpus.CorpusController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
public class NetworkController extends BaseController{
	
	@Autowired private NetWorkService nws;
	@Autowired private ExamAfterService eas;
	@Autowired private MaxValueINcrementer seq_testId;
	@Autowired private NetWorkStudentService nwts;
	@Autowired private ExamIntelligentlyChooseService eics;
	
	private User user;
	
	//判断教师或者学生是否已授权
	@RequestMapping(value="/network/paperFactory.html")
	public String viewAllNetwork(ModelMap mm,HttpServletRequest request){
        user=getSessionUser(request);
		mm.put("title", "网络考场");
		List<LStrMap<Object>> teacher=nws.getUserFromTeaAndStu(user.getUserId());
		List<LStrMap<Object>> student=nws.getstudentByuserId(user.getUserId());
		if(teacher.size()>0){//如果是教师
			//未发布的试卷数目
			mm.put("examlist", nws.getTeacherAllNewWork(user.getUserId()));
			return "network/paperFactory";
		}else if(student.size()>0){
			mm.put("EXAMINFO", nwts.getNowExamInfoDao(getSessionUser(request).getUserId()));
			mm.put("sysdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return "network/student/net_student";
		}else{
			mm.put("errorInfo", "抱歉！您还未开通网络考场，请联系客服开通，欢迎您来电咨询。");
			return "network/NotFindUser";
		}
	}
	
	
	//创建试卷
	@RequestMapping(value="/network/exam_createExam.html")
	public String getExamCode(ModelMap mm,HttpServletRequest request){
		user=getSessionUser(request);
		List<LStrMap<Object>> tests=nws.getTestsByUser(user.getUserId());
		mm.put("testList",tests);//角色:老师.老师所在学校的所在年级的考卷
		mm.put("areaList",eas.getAreaFromCodeService("AREA"));//地区
		mm.put("examTypeList",eas.getAreaFromCodeService("EXAMTYPE"));//获得试卷类型
		mm.put("testTypeList",nws.getTestTypes("TESTTYPE"));//获得考试类型
		mm.put("codeTypeList",eas.getCodeTypeTypeFromCodeService("CODETYPE"));//获得code表的题目编号类型
		mm.put("sortList", eas.getAllSortNoAndNameService());//获得己有的所有科目
		List<LStrMap<Object>> sortOfTeacher = nws.getTeachsOfTeacher(user.getUserId());//查询老师所教的科目
		mm.put("ofterSort",sortOfTeacher);
		mm.put("allGrades", nws.getAllGrades());//得到所有年级
		List<Integer> dateList=new ArrayList<Integer>();//试卷年份
		Calendar cal = Calendar.getInstance();
		for (int i = 2000; i <= cal.get(Calendar.YEAR); i++) {
			dateList.add(i);
		}
		mm.put("dateList",dateList);
		List<LStrMap<Object>> grades=nws.getGradeByTeacher(user.getUserId());//获得老师所教的年级
		mm.put("grades",grades);
		return "/network/exam_createExam";
	}
	
	//保存考试信息和考卷信息
	@RequestMapping(value="/network/createExam.html", method=RequestMethod.POST)
	public String createExam(ETest eTest,EExam eExam,ETestEexam ete,String way, ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		eExam.setTestId(eTest.getTestId());
		eExam.setReleaseStatus(0);
		eExam.setCareaor(user.getUserId()+"");
		eExam.setmTopicNum(0);
		eExam.setbTopicNum(0);
		eExam.setsCodeType(2);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String time= df.format(new Date());
		eExam.setRelease_time(time);
		eExam.setE_status(0);
		int examId=nws.saveExamService(eExam);
		
		if(eTest.getTestId()>0){
			//记录关联表,拿到考试编号,试卷编号,科目编号
			ete.setTestId(eTest.getTestId());
			ete.setExamId(examId);
			ete.setSubjectId(Integer.parseInt(eExam.getSubjectNo()));
			nws.saveTestExamDao(ete);
		}
		
		mm.put("examId", examId);
		if(way.equals("auto")) {
			mm.put("subjectId", eExam.getSubjectNo());
			return "redirect:/network/autoCreate.html";
		}
		return "redirect:/network/getExamInfo.html";
	}
	
	//返回编辑试卷页面
	private String returnToEditExam(int examId, ModelMap mm, HttpServletRequest request) {
		int testRecId = 0;
		LStrMap<Object> examInfoMap = eas.getExamInfoByExamidServices(examId);
		mm.put("examInfoMap", examInfoMap);
		List<Object> list = eas.getFullExamInfoService(examId, testRecId);
		mm.put("qtList", list.get(0));
		mm.put("bctList", list.get(1));
		mm.put("qiMap", list.get(2));
		mm.put("referUrl", request.getHeader("referer"));
		String type = request.getParameter("type");
		
		if(type != null && type.equals("see")){
			mm.put("type", type);
		} else {	//如果不是查看试卷，则返回试卷的信息，用于修改试卷
			mm.put("defaultType", request.getParameter("defaultType"));
			mm.put("areaList",eas.getAreaFromCodeService("AREA"));
			mm.put("examTypeList",eas.getAreaFromCodeService("EXAMTYPE"));
			
			//试卷年份
			List<Integer> dateList=new ArrayList<Integer>();
			Calendar cal = Calendar.getInstance();
			for (int i = 2000; i <= cal.get(Calendar.YEAR); i++) {
				dateList.add(i);
			}
			mm.put("dateList",dateList);
		}
		
		mm.put("quesTypes", nws.getTypeSubjects(Integer.parseInt(examInfoMap.get("subject_no").toString())));
		return "network/exam_addOrUpdate";
	}

	//上级知识点改变的时候，得到下级是知识点
	@RequestMapping(value="/network/exam_addOrUpdate/getKnowledgePoints.json", method=RequestMethod.POST)
	public String getNextLayerKnowledgePoints(ModelMap mm, HttpServletRequest request) {
		int parentKpId = Integer.parseInt(request.getParameter("parentKpId").toString());
		mm.put("kps", nws.getKnowledgePointsByParentId(parentKpId));
		return "jsonView";
	}
	
	//得到试卷所有题目信息
	@RequestMapping(value="/network/getExamInfo.html",method=RequestMethod.GET)
	public String startReadyEditExam(int examId,ModelMap mm,HttpServletRequest request){
		return returnToEditExam(examId, mm, request);
	}
	
	//新增、更新或删除大题
	@RequestMapping(value="/network/network_addOrUpdate.html", method=RequestMethod.POST)
	public String startReadyEditExam(int examId, EQuesType eQuesType, ModelMap mm, HttpServletRequest request){
		if("delete".equals(eQuesType.getTypeDetail())){//删除大题
			eas.deleteQuesTypeService(eQuesType);
			eas.updateExamNumService(-1, 0, eQuesType.getExamId());
		}else if(eQuesType.getTypeId() != -1){//编辑大题
			eas.updateQuesTypeService(eQuesType);
		}else{//新增大题
			eas.startReadyEditExamService(eQuesType);
			eas.updateExamNumService(1, 0, eQuesType.getExamId());
		}
		return "redirect:/network/getExamInfo.html?examId="+examId;
	}
	
	
	//对小题的操作，包括新增、删除、更新等
	@RequestMapping(value="/netWorksaveOneQues/exam_addOrUpdate.json",method=RequestMethod.POST)
	public String saveOneQues(HttpServletRequest request,EQuestion eQuestion,EOption eOption,EExamQuestion eExamQuestion,ModelMap mm){
		String subType=request.getParameter("subType");
		int sQuesId=-1;
		int oldSquesId=eQuestion.getQuesId();
		List<Integer> idList=new ArrayList<Integer>();
		
		//设置当前时间为小题的录入时间
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		eQuestion.setInputTime(dateformat.format(date));
		
		//如果是材料题增加小题，就设置小题的remark字段为材料题的编号
		int artId2=0;
		if(request.getParameter("artId") != null){
			artId2=Integer.parseInt(request.getParameter("artId"));
			eQuestion.setRemark(String.valueOf(artId2));
		}
		
		if("add".equals(subType)){
			idList=eas.saveQuestionService(eExamQuestion,eQuestion,eOption);
			eas.updateExamNumService(0, 1, eExamQuestion.getExamId());
			mm.put("idList", idList);
			sQuesId=idList.get(0);
		}else if("arlicleAdd".equals(subType)){
			int artId=eas.saveArtlicle(eQuestion,eExamQuestion);
			mm.put("artId", artId);
		}else if("arlicleUpdate".equals(subType)){	//更新文章内容
			eas.updateArtlicle(eQuestion);
			//更新文章下小题的知识点
			eas.updateQuestionsKnowledgePointOfArticle(eQuestion.getQuesId(), eQuestion.getKnowledgePoint());
		}else if("update".equals(subType)){
			eas.deleteOneQues(eExamQuestion.getExamId(), eExamQuestion.getQuesId());
			idList = eas.saveQuestionService(eExamQuestion,eQuestion,eOption);
			mm.put("idList", idList);
			sQuesId=idList.get(0);
		}else if("delete".equals(subType)){
			eas.updateExamNumService(0, -1, eExamQuestion.getExamId());
			//更新要删除的小题之后的所有小题（包括材料）的小题号
			UStrMap<Object> params = UStrMap.newInstance();
			params.put("m_topic", eExamQuestion.getmTopic());
			params.put("ques_id", eExamQuestion.getQuesId());
			params.put("exam_id", eExamQuestion.getExamId());
			params.put("addnum", -1);
			eas.updateMTopicsAfterDeleteQues(params);
			
			eas.deleteOneQues(eExamQuestion.getExamId(), eQuestion.getQuesId());
			sQuesId=eQuestion.getQuesId();
		}
		
		//如果对文章下的小题进行了增、删、改等动作，则要对相应的文章进行remark的更新
		//阅读理解时，判断属于哪一题
		if(artId2!=0){
			eas.updateArtRemark(artId2,sQuesId,subType,oldSquesId);
		}
		//如果是增加小题，则需要更新小题之后的所有小题的小题号
		if (subType.equals("add")) {
			updateMTopicsAfterAddQues(eExamQuestion.getmTopic(), eExamQuestion.getQuesId(), eExamQuestion.getExamId());
		}
		mm.put("title", "编辑试卷");
		mm.put("createor", eQuestion.getCreator());
		return "jsonView";
	}


	/**
	 * 新增一个小题之后，更新该小题之后小题的小题号
	 * @param mTopic 新增的小题的小题号
	 * @param quesId 新增小题的编号
	 * @param examId 试卷编号
	 */
	private void updateMTopicsAfterAddQues(int mTopic, int quesId, int examId) {
		UStrMap<Object> param = UStrMap.newInstance();
		param.put("m_topic", mTopic);
		param.put("ques_id", quesId);
		param.put("exam_id", examId);
		param.put("addnum", 1);
		eas.updateMTopicsAfterAddQues(param);
	}
	
	//试卷发布
	@RequestMapping(value="/netWorkrelease/exam_addOrUpdate.html")
	public String releaseExam(ModelMap mm,int examId){
		eas.updateReleasestatus(examId);//更新试卷的发布状态
		eas.updateReleaseStatusOfQuestions(examId, 1);
		mm.put("title", "发布成功");
		mm.put("testarragedflag", eas.getTestArrangedFlag(examId));
		return "netWork/exam_rleFinish";
	}
	
	//跳转到新建考试页面
	@RequestMapping(value="/netWork/newTest.html")
	public String getNewTest(ModelMap mm){
		mm.put("title", "新建考试");			
		return "/network/exam_createExam";
	}
	
	//新建考试
	@RequestMapping(value="/netWork/newTest.json")
	public String insertNewTest(ModelMap mm,ETest etest,HttpServletRequest request) throws ParseException{
		etest.setTestName(request.getParameter("testName"));
		etest.setYear(request.getParameter("year"));
		etest.setSemester(Integer.parseInt(request.getParameter("semester")));
		etest.setSchoolId(Integer.parseInt(request.getParameter("schoolId")));
		etest.setGradeId(Integer.parseInt(request.getParameter("gradeId")));
		etest.setTestType(Integer.parseInt(request.getParameter("testType")));
		String startTime=request.getParameter("testTime");		
		String endTime=request.getParameter("testEndTime");
		etest.setTestTime(startTime);
		etest.setTestEndTime(endTime);
		nws.insertTest(etest);
		mm.put("testMap",etest);
		return "jsonView";
	}
	
	//小题上移或下移
	@RequestMapping(value="/network/moveQuestion.json", method=RequestMethod.POST)
	public String changeQuestionssMTopic(int examId, int quesId, int moveNum) {
		//判断moveNum是正还是负，是正，说明是上移，是负，说明是下移
		if (moveNum > 0) {
			nws.updatePrevQuestionsMTopic(examId, quesId, moveNum);
			nws.updateQuestionMTopic(examId, quesId, moveNum);
		} else {
			nws.updateNextQuestionsMTopic(examId, quesId, moveNum);
			nws.updateQuestionMTopic(examId, quesId, moveNum);
		}
		return "jsonView";
	}
	
	@RequestMapping(value="/netWork/downPhoto.json",method=RequestMethod.POST)
	public String dpwnPhoto(ModelMap mm,HttpServletRequest request,HttpServletResponse response,String fileUrl){
		User user=this.getSessionUser(request);
		long userId=user.getUserId();
		String contextRootPath = HttpHelper.getRequestRealPath(request, "");
		Date date=new Date();
		long fileName=date.getTime();
		//上传图片到服务器
		StringBuffer sbPhoto = new StringBuffer();
		sbPhoto.append(contextRootPath).append("\\images\\formula\\").append(userId).append("_").append(fileName).append(".gif");
		try {
			download(fileUrl, sbPhoto.toString());
			StringBuffer sbSrc=new StringBuffer();
			//如果使用"\\"则在火狐中无法载入图片
			sbSrc.append("/images/formula/").append(userId).append("_").append(fileName).append(".gif");
			mm.put("fileSrc", sbSrc);
		} catch (Exception e) {
			e.printStackTrace();
			return "/network/error";
		}
		return "jsonView";
	}
	
	@RequestMapping(value="/File.html",method=RequestMethod.POST)
	public void getReadReport(HttpServletRequest request,HttpServletResponse response, ModelMap mm){
		//上传图片到服务器
		long userId = this.getSessionUser(request).getUserId();
		//上传圖片到服务器
		LStrMap<Object> upResult = nws.uploadMath(userId, request, response);
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			HttpHelper.writeString(response, "<script>parent.showPhoto("+gson.toJson(upResult)+");</script>", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//ueditor获取配置文件
	@RequestMapping(value="/ueditor/uploadImg.json")
	public String getUEditorConf(HttpServletRequest request, ModelMap mm) {
		mm.put("imageUrl", HttpHelper.getRequestBasePath(request)+"/ueditor/uploadImg.html");
		mm.put("imagePath", "/ueditor/php/");
		mm.put("imageMaxSize", 2048);
		mm.put("imageAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"});
		
		return "jsonView";
	}
	
	//ueditor图片上传
	@RequestMapping(value="/ueditor/uploadImg.html", method=RequestMethod.POST)
	public String uploadImg(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
		//上传图片到服务器
		long userId = this.getSessionUser(request).getUserId();
		//上传圖片到服务器
		LStrMap<Object> upResult = nws.uploadMath(userId, request, response);
		
		mm.put("state", upResult.get("errorInfo"));
		mm.put("url", upResult.get("mathPath"));
		mm.put("title", upResult.get("mathPath"));
		mm.put("original", "");
		mm.put("type", "jpg");
		mm.put("size", 2);
		return "jsonView";
	}
	
	@RequestMapping(value="/showPhoto.json")
	public String parseXls(String path,HttpServletRequest request,ModelMap mm){
		return "jsonView";
	}
	
	//远程图片URL,绝对路径+文件名
	public static void download(String urlString, String filename) throws Exception {
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    // 输入流
	    InputStream is = con.getInputStream();
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	    OutputStream os = new FileOutputStream(filename);   
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);  
	    }
	    // 完毕，关闭所有链接
	    os.close();
	    is.close();
	}  
	
	//删除一个文章的所有小题及文章本身
	@RequestMapping(value="/network/deleteArticle.json", method=RequestMethod.POST)
	public String deleteArticleQuestions(HttpServletRequest request,String examId, String quesIds, String mTopic, String mTopicNum) {
		//删除文章及小题
		nws.deleteArticleQuestions(Long.parseLong(examId), quesIds);
		
		//如果小题号是none，说明当前文章下没有小题，则不用更新小题
		if(!mTopic.equals("none")) {
			//更新文章之后的小题的小题号
			nws.updateArticleAfterMTopic(Long.parseLong(examId), Integer.parseInt(mTopic), Integer.parseInt(mTopicNum));
			//更新e_exam_question表的小题数
			eas.updateExamNumService(0, -Integer.parseInt(mTopicNum), Integer.parseInt(examId));
		}
		
		return "jsonView";
	}

	//得到学生当前时间之前的已经提交了的考试
	@RequestMapping(value="/student/viewAnaly.html")
	public String getStudentCommitedExam(HttpServletRequest request,ModelMap mm) {
		mm.put("commitedExams", nws.getCommitedExam(this.getSessionUser(request).getUserId()));
		return "/network/student/net_student";
	}
	
	//交换大题号
	@RequestMapping(value="/network/swapQuesType.html", method=RequestMethod.POST)
	public String swapTypeOrderNum(String types, long examId, ModelMap mm) {
		//更新每个大题的大题号
		Map map = new GsonBuilder().create().fromJson(types, Map.class);
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String quesId = iterator.next().toString();
			nws.updateTypeOrderNum(Long.parseLong(quesId), Integer.parseInt(map.get(quesId).toString()));
		}
		//更新每个小题的小题号
		nws.updateAllQuesMTopic(examId);
		//刷新页面
		mm.put("examId", examId);
		return "redirect:/network/getExamInfo.html";
	}
	
	//得到试卷基本信息
	@RequestMapping(value="/network/getExamInfo.json", method=RequestMethod.POST)
	public String getExamInfo(HttpServletRequest request, ModelMap mm) {
		LStrMap<Object> map = nws.getExamInfoByExamId(Long.parseLong(request.getParameter("examId")));
		mm.put("examInfo", map);
		return "jsonView";
	}
	
	//更新试卷信息
	@RequestMapping(value="/network/changeExamInfo.html", method=RequestMethod.POST)
	public void updateExamInfo(EExam exam, HttpServletResponse response) {
		nws.upExamInfoByExamId(exam);
		try {
			HttpHelper.writeString(response, "", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//更新试卷某个小题分数及选项分数
	@RequestMapping(value="/network/updateQuesScore.json", method=RequestMethod.POST)
	public String updateQuesScore(int examId, int quesId, int score, String oScore) {
		eas.updateQuesScoreOscore(examId, quesId, score, oScore);
		return "jsonView";
	}
	
	@RequestMapping(value="/network/editExam/copyArticle.json", method=RequestMethod.POST)
	public String copyQuestions(int examId, int typeId, String subjectNo, String resource, int gradeLevel,
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
			EExamQuestion eExamQuestion = new EExamQuestion();
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
			
			//创建小题试卷关系
			eExamQuestion.setExamId(examId);
			eExamQuestion.setTypeId(typeId);
			eExamQuestion.setmTopic(mtopicMap.get(i+"")==null?0:(int)Double.parseDouble(mtopicMap.get(i+"").toString()));
			eExamQuestion.setoScore(oScoreMap.get(i+"")==null?null:oScoreMap.get(i+"").toString());
			eExamQuestion.setScore(scoreMap.get(i+"")==null?0:Float.parseFloat(scoreMap.get(i+"").toString()));
			
			//保存小题、选项和关系
			if (i == 0) {//如果是材料，就记录材料的编号
				//更新材料题之后的所有小题的小题号
				nws.updateArticleAfterMTopic(examId, eExamQuestion.getmTopic()-1, -(mtopicMap.size()-1));
				eas.saveArtlicle(eQuestion, eExamQuestion);
				artId = eQuestion.getQuesId();
			} else {//如果不是材料，就记录材料的remark
				eas.saveQuestionService(eExamQuestion, eQuestion, eOption);
				remark = remark+eQuestion.getQuesId()+",";
			}
			
			idList.add(eQuestion.getQuesId());
		}
		
		//更新材料的remark
		eas.updateArtRemark(artId, remark);
		//更新作业小题数量
		eas.updateExamNumService(0, mtopicMap.size()-1, examId);
		
		mm.put("Ids", idList);
		//返回材料和小题编号
		return "jsonView";
	}
	
	//进入试卷预览页面
	@RequestMapping(value="/previewExam.html")
	public String previewExam(int examId, String page, ModelMap mm, HttpServletRequest request) {
		int testRecId = 0;
		mm.put("examInfoMap", eas.getExamInfoByExamidServices(examId));
		List<Object> list = eas.getFullExamInfoService(examId, testRecId);
		mm.put("qtList", list.get(0));
		mm.put("bctList", list.get(1));
		mm.put("qiMap", list.get(2));
		mm.put("page", page);
		
		LStrMap<Object> previewSets = nws.getExamPreviewSets(examId);
		if (previewSets == null) {
			nws.saveExamPreviewSets(examId);
			previewSets = nws.getExamPreviewSets(examId);
		}
		mm.put("previewInfo", previewSets);
		
		return "network/teacher/previewExam";
	}
	
	//保存试卷预览的设置
	@RequestMapping(value="/saveExamPreviewSets.json", method=RequestMethod.POST)
	public String saveExamPreviewSets(ExamPreviewSets eps, ModelMap mm, HttpServletRequest request) {
		try {
			nws.updateExamPreviewSets(eps);
			mm.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("success", false);
		}
		
		return "network/teacher/previewExam";
	}
	
	@RequestMapping(value="/network/autoCreate", method=RequestMethod.GET)
	public String toAutoCreate(int examId, int subjectId, ModelMap mm) {
		mm.put("examId", examId);
		mm.put("subjectId", subjectId);
		mm.put("typeSubjects", nws.getTypeSubjects(subjectId));
		mm.put("quesTypes", nws.getQuesTypesOfExam(examId));//得到试卷大题
		mm.put("knowledgePoints", eics.formatKnowledgepoints(nws.getKnowledgePointsBySubjectId(subjectId)));
		return "network/autoCreate";
	}
	
	@RequestMapping(value="/network/autoCreate.html", method=RequestMethod.POST)
	public String autoCreateExam(int examId, int subjectId, String orderNums, String tsIds, String typeIds, String quesNums, 
			String kps, float diff, ModelMap mm) {
		//转换json数据成list
		Gson gson = new GsonBuilder().create();
		ArrayList<String> orderNumList = gson.fromJson(orderNums, ArrayList.class);
		ArrayList<String> tsIdList = gson.fromJson(tsIds, ArrayList.class);
		ArrayList<String> typeIdList = gson.fromJson(typeIds, ArrayList.class);
		ArrayList<String> quesNumList = gson.fromJson(quesNums, ArrayList.class);
		ArrayList<ArrayList<String>> kpList = gson.fromJson(kps, ArrayList.class);
		
		//存储已经用过小题
		List<Integer> usedQues = nws.getQuesIdsOfExam(examId);//获取试卷已有小题的列表
		if (usedQues==null || usedQues.size()==0) {
			usedQues = new ArrayList<Integer>();
			usedQues.add(0);//默认有一个为0的小题编号，用于在还没有需要排除的小题时使用
		}
		//得到试卷已有大题信息并将信息以大题Id为键名存到Map中
		List<LStrMap<Object>> typeList = nws.getQuesTypesOfExam(examId);
		LStrMap<EQuesType> typeMap = LStrMap.newInstance();
		for (Iterator<LStrMap<Object>> iterator = typeList.iterator(); iterator.hasNext();) {
			LStrMap<Object> tempMap = (LStrMap<Object>) iterator.next();
			typeMap.put(tempMap.get("type_id").toString(), EQuesType.create(tempMap));
		}
		
		//声明一个EExamQuestion对象，用于传递信息
		EExamQuestion eeq = new EExamQuestion();
		eeq.setExamId(examId);
		
		//遍历orderNums，拆分数据成各个大题
		for (int i=0, size=orderNumList.size(); i<size; i++) {
			String typeIdStr = (typeIdList.get(i)==null)?"0":typeIdList.get(i);
			int typeId = Integer.parseInt(typeIdStr);
			float defaultScore = 1;//大题的默认分数
			int orderNum = Integer.parseInt(orderNumList.get(i));//大题号
			int tsId = Integer.parseInt(tsIdList.get(i));//题型
			int quesNum = Integer.parseInt(quesNumList.get(i));//小题数量
			List<String> quesKpList = kpList.get(i);//考点
			
			if (typeMap.containsKey(typeIdStr)) {//大题已经存在
				defaultScore = typeMap.get(typeIdStr).getDefaultScore();
			} else {
				typeId = nws.saveQuesType(examId, subjectId, tsId, orderNum);//插入大题
			}
			eeq.setTypeId(typeId);//小题设置大题编号
			
			if (quesKpList!=null && quesKpList.size()>0) {//当当前大题有考点时才进行下面的操作
				int lastMTopic = nws.getLastMTopic(examId, orderNum);//小于或等于当前大题范围内的最后一个小题号
				eeq.setmTopic(lastMTopic);
				//得到大题中每个考点对应的题目数量以及余出的题目数量
				int avg = quesNum/quesKpList.size();
				int mod = quesNum%quesKpList.size();
				if (avg != 0) {
					for (int j=0; j<quesKpList.size(); j++) {//查询随机小题并对查出的题目进行处理
						ArrayList<String> tempList = new ArrayList<String>();
						tempList.add(quesKpList.get(j));
						List<LStrMap<Object>> quesList = nws.getRandomQues(tsId, tempList, avg, usedQues, diff);
						if (quesList.size() > 0) {
							dealNormalQues(usedQues, quesList, eeq, defaultScore);//对普通小题的处理（非材料下小题）
							dealArtAndQues(usedQues, quesList, eeq, defaultScore);//对材料及材料下小题的处理
						}
					}
				}
				if (mod != 0) {//查询余下数量的随机题目
					List<LStrMap<Object>> quesList = nws.getRandomQues(tsId, quesKpList, mod, usedQues, diff);
					if (quesList.size() > 0) {
						dealNormalQues(usedQues, quesList, eeq, defaultScore);//对普通小题的处理（非材料下小题）
						dealArtAndQues(usedQues, quesList, eeq, defaultScore);//对材料及材料下小题的处理
					}
				}
			}
		}
		
		eas.updateExamNumService(orderNumList.size(), 0, examId);//更试卷大题数量，此处暂时设置小题数量为0
		nws.updateExamMTopicNum(examId);//更新试卷的小题数量
		mm.put("examId", examId);
		return "redirect:/network/getExamInfo.html";
	}


	/**
	 * 处理材料以及材料下小题
	 * @param usedQues 已用过题目
	 * @param paramList 参数列表
	 * @param quesList 小题列表
	 * @param eeq 小题对象
	 * @param defaultScore 大题默认分数，如果小题是填空题，则每个空格的选项分数为该参数值，否则，小题分数为该参数值
	 * @return
	 */
	private void dealArtAndQues(List<Integer> usedQues, List<LStrMap<Object>> quesList, 
			EExamQuestion eeq, float defaultScore) {
		Iterator<LStrMap<Object>> iterator = quesList.iterator();
		while (iterator.hasNext()) {
			LStrMap<Object> tempQues = iterator.next();//当前小题
			if (tempQues.get("ques_type").toString().equals("6")) {//对材料的处理
				String remark = tempQues.get("remark").toString();
				eeq.setQuesId(Integer.parseInt(tempQues.get("ques_id").toString()));
				usedQues.add(eeq.getQuesId());
				eeq.setmTopic(eeq.getmTopic()+1);
				dealFitQues(iterator, tempQues, eeq, defaultScore);
				eeq.setmTopic(eeq.getmTopic()-1);//先将小题号减一，以保证第一个材料下小题的小题号与材料的小题号相同
				List<String> quesIdList = Arrays.asList(remark.split(","));
				dealQuesOfArt(iterator, quesIdList, eeq, defaultScore);
			}
		}
	}


	/**
	 * 处理材料下小题
	 * @param quesList 小题列表
	 * @param quesIdList 材料下的所有小题的小题列表
	 * @param eeq 小题对象
	 * @param defaultScore
	 * @return
	 */
	private void dealQuesOfArt(Iterator<LStrMap<Object>> iterator, List<String> quesIdList, 
			EExamQuestion eeq, float defaultScore) {
		while (iterator.hasNext()) {
			LStrMap<Object> tempQues  = iterator.next();
			if (quesIdList.contains(tempQues.get("ques_id").toString())) {
				eeq.setQuesId(Integer.parseInt(tempQues.get("ques_id").toString()));
				eeq.setmTopic(eeq.getmTopic()+1);
				dealFitQues(iterator, tempQues, eeq, defaultScore);
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
			EExamQuestion eeq, float defaultScore) {
		int optionNum = Integer.parseInt(tempQues.get("option_num").toString());//选项数量
		String quesType = tempQues.get("ques_type").toString();
		if (!quesType.equals("4")) {
			eeq.setScore(defaultScore);
			eeq.setoScore(""+(int)defaultScore);
		} else {
			eeq.setScore((float)(optionNum*defaultScore));
			String oScore = "";
			for (int k=0; k<optionNum; k++)	oScore += ((int)defaultScore+",");
			eeq.setoScore(oScore.substring(0, oScore.length()-1));
		}
		
		nws.saveEExamQuestion(eeq);//保存一条小题记录
		updateMTopicsAfterAddQues(eeq.getmTopic(), eeq.getQuesId(), eeq.getExamId());
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
			EExamQuestion eeq, float defaultScore) {
		Iterator<LStrMap<Object>> iterator = quesList.iterator();
		while (iterator.hasNext()) {//遍历所有小题
			LStrMap<Object> tempQues = iterator.next();//当前小题
			String quesType = tempQues.get("ques_type").toString();
			Object remark = tempQues.get("remark");
			
			if (!quesType.equals("6") && remark==null) {//对普通小题的处理
				eeq.setQuesId(Integer.parseInt(tempQues.get("ques_id").toString()));
				eeq.setmTopic(eeq.getmTopic()+1);
				usedQues.add(eeq.getQuesId());//当前小题存为已用过小题
				dealFitQues(iterator, tempQues, eeq, defaultScore);
			}
		}
	}
	
	//导出试卷
	@RequestMapping(value="/network/exportExamWord.html")
	public String exportExamWord(int examId, HttpServletRequest request, HttpServletResponse response) {
		Exam exam = nws.getExam(examId);
		LStrMap<Object> data = LStrMap.newInstance();
		data.put("exam", exam);
		String filename = exam.getName();
		try {
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				filename = URLEncoder.encode(filename, "UTF-8");
			} else {
				filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
			}
			response.reset();
			response.setHeader("Content-Type", "application/msword");
			response.setHeader("Content-disposition", "attachment; filename=\""+filename+".doc\"");
			response.setContentType("application/octet-stream;charset=UTF-8;");// 定义输出类型
			Template template = getXmlTemplate();
			template.setEncoding("UTF-8");
			template.process(data, new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),"UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Template getXmlTemplate() throws IOException {
		Configuration config = new Configuration();
		config.setClassForTemplateLoading(CorpusController.class, "/template");
		config.setDefaultEncoding("UTF-8");
		return config.getTemplate("试卷导出模板.xml");
	}
}