package com.eps.service.examsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.examsystem.ExamAfterDao;
import com.eps.domain.EExam;
import com.eps.domain.EExamQuestion;
import com.eps.domain.EOption;
import com.eps.domain.EQuesType;
import com.eps.domain.EQuestion;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class ExamAfterService {
	
	@Autowired
	private ExamAfterDao ead; 
	
	@Autowired
	private MaxValueINcrementer seq_typeId;
	
	@Autowired
	private MaxValueINcrementer seq_quesId;
	
	@Autowired
	private MaxValueINcrementer seq_optId;
	
	@Autowired
	private MaxValueINcrementer seq_examId;
	
	/**
	 * 获得code表的地区
	 * @param codeCate
	 * @return
	 */
	public List<LStrMap<Object>> getAreaFromCodeService(String codeCate){
		return ead.getAreaFromCodeDao(codeCate);
	}
	
	/**
	 * 获得code表的考试类型
	 * @param codeCate
	 * @return
	 */
	public List<LStrMap<Object>> getExamTypeFromCodeService(String codeCate){
		return ead.getExamTypeFromCodeDao(codeCate);
	}
	
	/**
	 * 获得code表的题目编号类型
	 * @param codeCate
	 * @return
	 */
	public List<LStrMap<Object>> getCodeTypeTypeFromCodeService(String codeCate){
		return ead.getCodeTypeTypeFromCodeDao(codeCate);
	}
	
	/**
	 * 保存并获得试卷所有信息
	 * @param eQuesType
	 * @return
	 */
	public void startReadyEditExamService(EQuesType eQuesType){
		int typeId=seq_typeId.nextIntValue();		//获得一个大题编号
		eQuesType.setTypeId(typeId);
		ead.startReadyEditExamDao(eQuesType);
	}
	
	public void updateQuesTypeService(EQuesType eQuesType){
		ead.updateQuesTypeDao(eQuesType);
	}
	
	public void deleteQuesTypeService(EQuesType eQuesType){
		//更新该大题后的所有大题的大题号
		int examId = eQuesType.getExamId();
		int orderNum = eQuesType.getOrderNum();
		ead.updateOrderNum(examId, orderNum);
		
		ead.deleteQuesTypeDao(eQuesType);		//从e_ques_type表中删除该大题对应的记录
		ead.deleteExamQuesByTypeid(eQuesType);	//从e_exam_question表中删除该大题对应的记录
	}
	
	/**
	 * 获得试卷所有信息，并转化成固定格式
	 * @param examId
	 * @return
	 */
	public List<Object> getFullExamInfoService(int examId, int testRecId){
		List<Object> list=new ArrayList<Object>();
		
		//从e_ques_type表根据order_num得到试卷上的所有大题信息
		List<LStrMap<Object>> qtlist=ead.getQuesTypeDao(examId);
		List<String> bctList = getTypeNames(examId);
		
		//得到这张试卷对应的科目的所有知识点，并存到map中，键名是kp_id,键值是知识点的map
		LStrMap<LStrMap<Object>> kpMap = getAllKnowledgepointsOfSubject(ead.getAllKnowledgePoints(examId));
		
		List<LStrMap<Object>> qiList=ead.getAllQuesInfoDao(examId, testRecId);//查出试卷的所有选项及对应的大题和小题信息
		List<LStrMap<Object>> qiCList=new ArrayList<LStrMap<Object>>();
		
		//将选项整合成小题
		for (int i=0; i<qiList.size();) {	//遍历所有选项
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			List<LStrMap<Object>> kpList = formatQuesKnowledgepoint(kpMap, qiList.get(i).get("knowledge_point").toString());
			
			int optNum=Integer.parseInt(qiList.get(i).get("OPTION_NUM").toString());//选项个数
			if(optNum==0){	//如果选项个数等于0说明是材料题的材料介绍 ，存储材料知识点、存储该该材料
				qiList.get(i).put("KNOWLEDGE_POINT", kpList);
				qiCList.add(qiList.get(i));
				i=i+1;
				continue;
			}
			
			//新建三个列表，用于存储相应的信息
			List<String> optList=new ArrayList<String>();
			List<String> refList=new ArrayList<String>();
			List<Integer> idList=new ArrayList<Integer>();
			List<String> optNoList = new ArrayList<String>();
			String[] oScoreArr = null;
			
			//将选项得分分割为数组
			if(qiList.get(i).get("O_SCORE")!=null){
				oScoreArr = qiList.get(i).get("O_SCORE").toString().split(",");
			}
			
			//遍历同一个小题的选项记录，封装选项内容、参考答案、编号、选项号
			for (int j = i; j <(i+optNum) && j<qiList.size(); j++) {
				if(qiList.get(j).get("OPT_CONTENT")!=null){//如果当前选项内容不是空，选项列表新增选项内容
					optList.add(qiList.get(j).get("OPT_CONTENT").toString());
				}
				refList.add(qiList.get(j).get("OPT_REFER").toString());	
				idList.add(Integer.parseInt(qiList.get(j).get("OPT_ID").toString()));
				if(qiList.get(j).get("OPT_NO")!=null){	//如果选项号不是空，选项号列表新增内容
					optNoList.add(qiList.get(j).get("OPT_NO").toString());
				}
			}
			
			// 把上面封装起来的相应的列表存到该小题第一个选项记录的里面
			qiList.get(i).put("OPT_CONTENT", optList);
			qiList.get(i).put("OPT_REFER", refList);
			qiList.get(i).put("OPT_ID", idList);
			qiList.get(i).put("OPT_NO", optNoList);
			qiList.get(i).put("O_SCORE", oScoreArr);
			qiList.get(i).put("KNOWLEDGE_POINT", kpList);
			
			qiCList.add(qiList.get(i));	//qiCList中存储当前小题
			i=i+optNum;
		}
		
		Map<String,LinkedHashMap<String, Object>> qiMap = new HashMap<String, LinkedHashMap<String,Object>>();	//用于存储所有小题信息
		List<LStrMap<Object>> tempList=new ArrayList<LStrMap<Object>>();
		//用于存储所有小题的map
		LinkedHashMap<String, Object> tempMap = null;
		for (int i = 0; i < qtlist.size(); i++) {	//遍历所有大题记录
			String qkey=(qtlist.get(i)).get("TYPE_ID").toString();	//得到对应的大题的编号
			qiMap.put(qkey, new LinkedHashMap<String, Object>());	//对应每个大题新建一个List
		}
		
		for (int i = 0; i < qiCList.size(); i++) {	//遍历所有小题信息
			LStrMap<Object> tempQues = qiCList.get(i);
			//获得其键值
			String quesKey = tempQues.get("TYPE_ID").toString();	//获得小题对应的大题编号
			int quesType = Integer.parseInt(tempQues.get("QUES_TYPE").toString());	//获得小题的类型
			Integer.parseInt(tempQues.get("TYPE").toString());
			
			int quesId = Integer.parseInt(tempQues.get("ques_id").toString());
			String remark = tempQues.get("remark")==null?null:tempQues.get("remark").toString();
			
			if(qiMap.containsKey(quesKey)){	//如果qiMap中存到对应的大题
				tempMap = qiMap.get(quesKey);	//得到对应的大题列表
			} else {	//如果qiMap中不存在对应的大题列表
				tempMap = new LinkedHashMap<String, Object>();	//新建一个列表
			}
			//判断小题类型是否是6
			if (quesType == 6) {
				//小题如果是材料，则把小题及其下的小题存入到一个ArrayList中，并将ArrayList存入对应大题的的LinkedHashMap
				tempList = new ArrayList<LStrMap<Object>>();	//新建一个临时列表
				tempList.add(qiCList.get(i));	//tempList中增加对应的小题
				if(remark == null){	//如果remark是null，说明该材料下没有小题
					tempMap.put("art"+quesId, tempList);
					qiMap.put(quesKey, tempMap);	//将tkList存到qiMap中
					continue;	//跳出当前一次循环
				}
				
				//材料下有小题
				String[] quesIdArr = remark.split(",");	//得到材料对应的小题号
				for (int j=0; j<qiCList.size(); j++) {	//遍历所有小题
					for (int x = 0; x < quesIdArr.length; x++) {	//遍历材料对应的所有小题号
						if(qiCList.get(j).get("QUES_ID").toString().equals(quesIdArr[x])){	//如果对应的小题号等于材料小题号
							tempList.add(qiCList.get(j));	//tempList中增加对应的小题
						}
					}
				}
				tempMap.put("art"+quesId, tempList);
				qiMap.put(quesKey, tempMap);	//将对应的大题放到qiMap中
			} else if (remark==null || remark.equals("")) {//判断小题remark是否为null或""
				//小题是普通小题，则把小题存入对应大题的LinkedHashMap中，键名为normal+小题编号
				tempMap.put("normal"+quesId, tempQues);
				qiMap.put(quesKey, tempMap);
			}
		}
		
		list.add(qtlist);
		list.add(bctList);
		list.add(qiMap);
		return list;
	}

	/**
	 * 得到某个科目的所有知识点，并存到map中，键名是kp_id,键值是知识点的map
	 * @param knowledgePointsList 该科目的所有知识点的数据
	 * @return
	 */
	private LStrMap<LStrMap<Object>> getAllKnowledgepointsOfSubject(List<LStrMap<Object>> knowledgePointsList) {
		LStrMap<LStrMap<Object>> kpMap = LStrMap.newInstance();
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap);
		}
		return kpMap;
	}

	/**
	 * 将id,id2,id3...形式的知识点转成存有知识点名称的List
	 * @param kpMap
	 * @param kpStr 知识点编号字符串，如 1,2,3,
	 * @return
	 */
	private List<LStrMap<Object>> formatQuesKnowledgepoint(LStrMap<LStrMap<Object>> kpMap, String kpStr) {
		List<LStrMap<Object>> kpList = new ArrayList<LStrMap<Object>>();
		String[] kpArr = kpStr.split(",");
		for(String tempKpIdStr : kpArr) {
			LStrMap<Object> tempKpMap = LStrMap.newInstance();
			tempKpMap.put("kpId", tempKpIdStr);
			String tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString();//得到知识点编号对应的知识点名称
			tempKpMap.put("kpName", tempKpName);
			while (!kpMap.get(tempKpIdStr).get("p_kp_id").toString().equals("0")) {//如果存在父级知识点
				tempKpIdStr = kpMap.get(tempKpIdStr).get("p_kp_id").toString();//将父级知识点作为本级知识点进行循环
				tempKpName = kpMap.get(tempKpIdStr).get("kp_name").toString()+" - "+tempKpName;//得到父级知识点和本知识点的组合字符串
			}
			tempKpMap.put("kpPath", tempKpName);
			kpList.add(tempKpMap);
		}
		return kpList;
	}

	/**
	 * 得到所有大题的名字
	 * @param examId 试卷编号
	 * @return
	 */
	private List<String> getTypeNames(int examId) {
		List<String> bctList=new ArrayList<String>();
		String bctStr = ead.getBigQtDao(examId).get("name").toString();
		String[] bct = bctStr.split(",");	//得到大题题号类型名，使用的是e_exam和s_code表
		
		for (int i=0; i<bct.length; i++) bctList.add(bct[i]);//将大题号放到bctList中
		bctList.add(bctStr);
		return bctList;
	}
	
	/**
	 * 获得己有的所有考试分类
	 * @return
	 */
	public Map<String,List<LStrMap<Object>>> getAllSortNoAndNameService(){
		List<LStrMap<Object>> getList=ead.getAllSortNoAndNameDao();
		Map<String,List<LStrMap<Object>>> map =new HashMap<String, List<LStrMap<Object>>>();
		List<LStrMap<Object>> tempList=null;
		for (int i = 0; i < getList.size(); i++) {
			String parentSortName=getList.get(i).get("p_sort_name").toString();
			if(map.containsKey(parentSortName)){
				tempList=map.get(parentSortName);
				tempList.add(getList.get(i));
				map.put(parentSortName, tempList);
			}else{
				tempList=new ArrayList<LStrMap<Object>>();
				tempList.add(getList.get(i));
				map.put(parentSortName, tempList);
			}
		}
		
		return map;
	}
	
	/**
	 * 保存一道小题
	 * @param eQuestion
	 * @return
	 */
	public List<Integer> saveQuestionService(EExamQuestion eExamQuestion,EQuestion eQuestion,EOption eOption){
		List<Integer> idList=new ArrayList<Integer>();
		
		int quesId=seq_quesId.nextIntValue();	//生成一个小题号
		idList.add(quesId);
		eQuestion.setQuesId(quesId);
		eExamQuestion.setQuesId(quesId);
		
		Gson gson = new  GsonBuilder().create();
		Map<?, ?> quesReferMap=gson.fromJson(eQuestion.getQuesRefer(),Map.class);	//得到小题选项内容
		Map<?, ?> mapContemt=gson.fromJson(eOption.getOptContent(),Map.class);	//得到选项内容
		Map<?, ?> mapContent2=gson.fromJson(eOption.getOptRefer(),Map.class);		//得到选项参考答案
		Map<?, ?> mapContent3=gson.fromJson(eOption.getOptNo(), Map.class);		//得到选项号，如ABCD或者对错或者1,2,3
		
		//设置小题正确答案，设置分隔符为@@
		String quesReferStr = "";
		for (int i = 1; i <= quesReferMap.size(); i++) {
			if(quesReferMap.containsKey(i+"")){//如果小题答案中含有当前索引
				quesReferStr=quesReferStr+quesReferMap.get(i+"").toString()+"@@";
			}
		}
		
		if (quesReferStr.length() >= 2) {
			quesReferStr = quesReferStr.substring(0, quesReferStr.length()-2);
		}
		if (quesReferStr.equals("")) eQuestion.setQuesRefer(null);
		else eQuestion.setQuesRefer(quesReferStr);
		ead.saveQuestionDao(eQuestion);	//保存小题
		
		//从索引1开始，遍历参考答案
		for (int i = 1; i <= mapContent2.size(); i++) {
			if(mapContemt.containsKey(i+"")){	//如果选项内容中包含该索引键值
				eOption.setOptContent(mapContemt.get(i+"").toString());	//设置选项内容
			}else{
				eOption.setOptContent("");
			}
			
			//设置选项号
			if (mapContent3.containsKey(i+"")) {
				eOption.setOptNo(mapContent3.get(i+"").toString());
			}else {
				eOption.setOptNo("");
			}
			
			eOption.setOptRefer(mapContent2.get(i+"").toString());	//设置选项的参考答案
			int optId=seq_optId.nextIntValue();						//获得一个选项的编号
			idList.add(optId);										//把选项编号放到idList中
			
			eOption.setQuesId(quesId);
			eOption.setOptId(optId);
			ead.saveOptionDao(eOption);	//保存一个选项
		}
		
		ead.saveExamQuesDao(eExamQuestion);	//保存一个试卷小题关系
		
		return idList;
	}
	
	/**
	 * 删除一个小题及该小题的所有选项
	 * @param quesId 小题编号
	 * @return
	 */
	public void deleteOneQuestion(int quesId){
		ead.deleteQuestionDao(quesId);
		ead.deleteOptionDao(quesId);
	}
	
	/**
	 * 删除一个试卷小题关系
	 * @param examId 试卷编号
	 * @param quesId 小题编号
	 * @return
	 */
	public boolean deleteOneExamQuesRelation(int examId, int quesId) {
		return ead.deleteOneExamQuesRelation(examId, quesId) == 1;
	}
	
	public int saveExamService(EExam eExam){
		int examId=seq_examId.nextIntValue();
		eExam.setExamId(examId);
		ead.saveExamDao(eExam);
		return examId;
	}
	
	/**
	 * 根据id获得试卷信息
	 * @param examId
	 * @return
	 */
	public LStrMap<Object> getExamInfoByExamidServices(int examId){
		return ead.getExamInfoByExamidDao(examId);
	}
	
	/**
	 * 保存材料
	 * @param eQuestion
	 * @param eExamQuestion
	 * @return
	 */
	public int saveArtlicle(EQuestion eQuestion,EExamQuestion eExamQuestion){
		int quesId=seq_quesId.nextIntValue();	//获得一个小题编号
		eQuestion.setQuesId(quesId);			//设置小题编号
		ead.saveQuestionDao(eQuestion);			//保存一个小题
		eExamQuestion.setQuesId(quesId);
		ead.saveExamQuesDao(eExamQuestion);		//保存一个试卷小题关系
		return quesId;
	}
	
	/**
	 * 更新材料
	 * @param eQuestion
	 * @return
	 */
	public boolean updateArtlicle(EQuestion eQuestion){
		return ead.updateArticle(eQuestion)==1;
	}
	
	/**
	 * 更改材料的remark
	 * @param artId 材料编号
	 * @param newQuesId 新的材料小题的编号
	 * @param type 操作类型，新增、删除、或更新
	 * @param oldQuesId 老的材料小题的编号
	 * @return
	 */
	public boolean updateArtRemark(int artId, int newQuesId,String type,int oldQuesId){
		LStrMap<Object> map=ead.getArtRemarkDao(artId);		//获得小题的remark
		String remark="";
		
		//remark不为空，说明当前材料下有小题
		if(map.get("remark")!=null){
			remark=map.get("remark").toString();
		}
		
		if("add".equals(type)){									//如果是增加小题，remark更新
			remark=remark+newQuesId+",";
		}else if("delete".equals(type)){						//如果是删除小题，remark替换掉原来的小题编号和逗号为空字符串
			remark=remark.replace(newQuesId+",", "");
		}else if("update".equals(type)){						//如果是更新小题，remark替换掉原来的小题编号为新的小题编号
			remark=remark.replace(oldQuesId+"", newQuesId+"");
		}
		return ead.updateArtRemarkDao(artId, remark)==1;		//更新remark
		
	}
	
	/**
	 * 删除小题
	 * @param examId 试卷编号
	 * @param quesId 小题编号
	 * @return
	 */
	public void deleteOneQues(int examId, int quesId){
		ead.deleteOneExamQuesRelation(examId, quesId);	//删除e_exam_question表中，当前小题对应的记录
		//如果小题未分享，则允许删除
		if (ead.getQuestionByQuesId(quesId).getReleaseFlag() == 0) {
			deleteOneQuestion(quesId);
		}
	}
	
	/**
	 * 发布试卷
	 * @param examId
	 * @return
	 */
	public boolean updateReleasestatus(int examId){
		return ead.updateReleasestatusDao(examId)==1;
	}
	
	/**
	 * 得到当前用户经常出的试卷科目
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getOfterSortNameService(long userId){
		return ead.getOfterSortNameDao(userId);
	}
	
	/**
	 *  更新大题小题数 
	 * @param map
	 * @return
	 */
	public boolean updateExamNumService(int bnum,int mnum,int examId){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("B_TOPIC_NUM", bnum);
		map.put("M_TOPIC_NUM", mnum);
		map.put("EXAM_ID", examId);
		return ead.updateExamNumDao(map)==1;
	}
	
	/**
	 * 当新增小题或者是删除已有小题时，更新该小题之后所有小题的题号
	 * @param map
	 * @return
	 */
	public boolean updateMTopicsAfterAddQues(UStrMap<Object> map){
		return ead.updateMTopicsAfterAddQues(map)==1;
	}
	
	/**
	 * 当交换两个小题的小题号时，更新每个小题的小题号
	 * @param examId
	 * @param quesId
	 * @param MTopic
	 * @return
	 */
	public int updateOneQuesMTopic(long examId, long quesId, int MTopic){
		return ead.updateOneQuesMTopic(examId, quesId, MTopic);
	}
	
	public Object getTestArrangedFlag(long examId) {
		List<LStrMap<Object>> list = ead.getTestArrangeFlag(examId);
		if (list.size() > 0) {
			return list.get(0).get("test_arrange_flag");
		}
		return null;
	}

	/**
	 * 更新材料下各个小题的知识点
	 * @param artId2
	 * @param knowledgePoint
	 */
	public void updateQuestionsKnowledgePointOfArticle(int articleId, String knowledgePoint) {
		ead.updateQuestionsKnowledgePointOfArticle(articleId, knowledgePoint);
	}

	/**
	 * 更新试卷小题的发布状态
	 * @param examId 试卷编号
	 * @param status 状态，0为未发布，1为已发布
	 * @return
	 */
	public boolean updateReleaseStatusOfQuestions(int examId, int status) {
		return ead.updateReleaseStatusOfQuestions(examId, status)==1;
	}

	/**
	 * 更新某个试卷的某个小题的分数和选项分数
	 * @param examId 试卷编号
	 * @param quesId 小题编号
	 * @param Score 小题分数
	 * @param oScore 选项分数
	 * @return
	 */
	public boolean updateQuesScoreOscore(int examId, int quesId, int score, String oScore) {
		return ead.updateQuesScoreOscore(examId, quesId, score, oScore)==1;
	}

	public boolean updateMTopicsAfterDeleteQues(UStrMap<Object> params) {
		return ead.updateMTopicsAfterDeleteQues(params)==1;
	}

	/**
	 * 更新材料的remark
	 * @param artId
	 * @param substring
	 */
	public void updateArtRemark(int artId, String remark) {
		ead.updateArtRemarkDao(artId, remark);
	}

}
