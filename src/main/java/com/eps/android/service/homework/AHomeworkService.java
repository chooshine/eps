package com.eps.android.service.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.android.dao.homework.AHomeworkDao;
import com.eps.android.domain.ENote;
import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.Page;
import com.eps.dao.homework.StudentHomeworkDao;
import com.eps.dao.homework.TeacherHomeworkDao;
import com.eps.domain.EHomeworkRecord;
import com.eps.domain.EHomeworkRecordDetail;
import com.eps.utils.FileUploader;
import com.eps.utils.LStrMap;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class AHomeworkService {
	@Autowired private AHomeworkDao aHomeworkDao;
	@Autowired private TeacherHomeworkDao teacherHomeworkDao;
	@Autowired private MaxValueINcrementer seq_noteId;
	@Autowired private StudentHomeworkDao studentHomeworkDao;
	
	
	public List<LStrMap<Object>> getHomeworks(int uid, int pageNo) {
		Page page = aHomeworkDao.getHomeworks(uid, pageNo);
		if (page != null) {
			return page.getData();
		}
		return null;
	}

	public LStrMap<Object> getStarStatistic(int userId, int subjectId) {
		List<LStrMap<Object>> list = aHomeworkDao.getStarStatistic(userId, subjectId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List getStarNumOfHomeworks(int pageNo, int userId, int subjectId) {
		Page page = aHomeworkDao.getStarNumOfHomeworks(pageNo, userId, subjectId);
		return page.getData();
	}

	public LStrMap<Object> getHwInfo(int userId, int hwId) {
		List<LStrMap<Object>> list = aHomeworkDao.getHwInfo(userId, hwId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<LStrMap<Object>> getAnswerCard(int userId, int hwId) {
		return aHomeworkDao.getAnswerCard(userId, hwId);
	}

	public List<LStrMap<Object>> getKnowledgePoints(int userId, int hwId) {
		return aHomeworkDao.getKnowledgePoints(userId, hwId);
	}

	public List<LStrMap<Object>> getAllQuestions(int hwid, int hwrecid) {
		List<LStrMap<Object>> questions = getQuestions(hwid, hwrecid);
		List<LStrMap<Object>> options = getOptions(hwid);
		
		//将同一小题的选项放到一个ArrayList中，并将该List以ques_id为键名放到Map中
		LStrMap<List<LStrMap<Object>>> optionsMap = LStrMap.newInstance();
		for (int i = 0; i < options.size(); i++) {
			LStrMap<Object> tempMap = options.get(i);
			if (optionsMap.containsKey(tempMap.get("ques_id").toString())) {
				optionsMap.get(tempMap.get("ques_id").toString()).add(tempMap);
			} else {
				List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(options.get(i));
				optionsMap.put(tempMap.get("ques_id").toString(), tempList);
			}
		}
		//将选项List与对应的小题放在一起
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).put("options", optionsMap.get(questions.get(i).get("ques_id").toString()));
		}
		
		//根据o_score判断空格是否正确
		for(int i=0,size=questions.size(); i<size; i++) {
			LStrMap<Object> quesLStrMap = questions.get(i);
			//只是对填空题操作
			if (quesLStrMap.get("ques_type").toString().equals("4")) {
				String oScore = quesLStrMap.get("o_score").toString();
				String[] oScoreArray = oScore.split(",");
				List<LStrMap<Object>> tempOptions = (List<LStrMap<Object>>) quesLStrMap.get("options");
				for (int j=0, tempSize=tempOptions.size(); j<tempSize; j++) {//遍历所有选项得分
					tempOptions.get(j).put("correct_flag", oScoreArray[j]);
				}
			}
		}
		return questions;
	}

	public List<LStrMap<Object>> getOptions(int hwId) {
		return aHomeworkDao.getOptions(hwId);
	}

	private List<LStrMap<Object>> getQuestions(int hwId, int hwRecId) {
		//得到这张作业对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<Object> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = teacherHomeworkDao.getKnowledgePointsByHwId(hwId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap.get("kp_name"));
		}
		
		List<LStrMap<Object>> qiList = aHomeworkDao.getAllQuestions(hwId, hwRecId);	//查出作业的所有选项及对应的大题和小题信息
		
		for (int i = 0; i < qiList.size(); i++) {
			LStrMap<Object> tempMap = qiList.get(i);
			
			//将笔记内容转成所有img标签都在最后的形式
			String noteContent = tempMap.get("content")==null?"":tempMap.get("content").toString();
			tempMap.put("content", formatTextAndImg(noteContent));
			
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			if (tempMap.get("knowledge_point") != null) {
				String kpStr = tempMap.get("knowledge_point").toString();
				List<Object> kpList = new ArrayList<Object>();
				String[] kpArr = kpStr.split(",");
				for(String tempKpStr : kpArr) {
					kpList.add(kpMap.get(tempKpStr));
				}
				tempMap.put("knowledge_points", kpList);
			} else {
				tempMap.put("knowledge_points", null);
			}
			tempMap.remove("knowledge_point");
			
			//曾选项
			if (tempMap.get("ques_type").toString().equals("1") || tempMap.get("ques_type").toString().equals("2")) {
				List<String> everDoOptionList = new ArrayList<String>();
				String everDoOptionStr = tempMap.get("answer")==null?"":tempMap.get("answer").toString();
				Map<String, Object> gsonMap = new GsonBuilder().create().fromJson(everDoOptionStr, LinkedHashMap.class);
				Iterator<String> iterator = gsonMap.keySet().iterator();
				int var = -1;
				while (iterator.hasNext()) {
					var++;
					String doubtOpt = gsonMap.get(iterator.next()).toString();
					if (doubtOpt.equals("1")) {
						everDoOptionList.add((char)(65+var)+"");
					}
				}
				tempMap.put("everdo_Options", everDoOptionList);
			}
			tempMap.remove("answer");
			
		}
		
		return qiList;
	}

	public Map<String, String> uploadNoteImg(String imgPath, HttpServletRequest request, HttpServletResponse response) {
		FileUploader photoUpload=new FileUploader();
		//设置保存路径
		photoUpload.setSavePath(imgPath);
		//设置最大上传大小
		photoUpload.setFileSizeMax(1024000);
		photoUpload.setSizeMax(3720000);
		//设置文件格式
		Set<String> set=new HashSet<String>();
		set.add(".jpg");
		set.add(".png");
		set.add(".gif");
		set.add(".tiff");
		set.add(".bmp");
		set.add(".jpeg");
		set.add(".tif");
		set.add(".swf");
		set.add(".dib");
		photoUpload.setAcceptTypes(set);
		//设置文件名
		FileNameGenerator fileNameGenerator=new NoteNameGenerator();
		photoUpload.setFileNameGenerator(fileNameGenerator);
		Result resut=photoUpload.upload(request, response);
		Set<String> names=photoUpload.getFileNames();
		Iterator iterator=names.iterator();
		StringBuffer sbf=new StringBuffer();
		while (iterator.hasNext()) {
			sbf.append(imgPath+iterator.next().toString()+",");
		}
		String errorInfo="上传失败";
		if("SUCCESS".equals(resut.toString())){
			errorInfo="上传成功";
		}else if("FILE_SIZE_EXCEEDED".equals(resut.toString())){
			errorInfo="上传失败，文件大小超过限制";
		}
		Map<String,String> map=new HashMap<String, String>();
		map.put("errorInfo", errorInfo);
		map.put("path", new String(sbf));
		return map;
	}

	public void saveNote(ENote eNote) {
		eNote.setNoteId(seq_noteId.nextIntValue());
		aHomeworkDao.saveNote(eNote);
	}

	public void updateNote(int noteId, String commitTime, String content) {
		aHomeworkDao.updateNote(noteId, commitTime, content);
	}

	public List<LStrMap<Object>> getFavoriteKnowledgePoints(long userId, int subjectId) {
		return aHomeworkDao.getFavoriteKnowledgePoints(userId, subjectId);
	}

	public List<LStrMap<Object>> getErrorKnowledgePoints(long userId, int subjectId) {
		return aHomeworkDao.getErrorKnowledgePoints(userId, subjectId);
	}

	public List<LStrMap<Object>> packageQuestions(long userId, int kpId, int subjectId, String operate) {
		List<LStrMap<Object>> questions = getQuestionsBasicInfo(userId, kpId, subjectId, operate);
		List<LStrMap<Object>> options = getOptionsOfSpecifiedQuestions(userId, kpId, operate);
		
		//将同一小题的选项放到一个ArrayList中，并将该List以ques_id为键名放到Map中
		LStrMap<List<LStrMap<Object>>> optionsMap = LStrMap.newInstance();
		for (int i = 0; i < options.size(); i++) {
			LStrMap<Object> tempMap = options.get(i);
			if (optionsMap.containsKey(tempMap.get("ques_id").toString())) {
				optionsMap.get(tempMap.get("ques_id").toString()).add(tempMap);
			} else {
				List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(options.get(i));
				optionsMap.put(tempMap.get("ques_id").toString(), tempList);
			}
		}
		//将选项List与对应的小题放在一起
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).put("options", optionsMap.get(questions.get(i).get("ques_id").toString()));
		}
		return questions;
	}

	private List<LStrMap<Object>> getOptionsOfSpecifiedQuestions(long userId, int kpId, String operate) {
		if (operate.equals("favorite")) {
			return aHomeworkDao.getOptionsOfFavoriteQuestions(userId, kpId);
		} else if(operate.equals("error")) {
			return aHomeworkDao.getOptionsOfErrorQuestions(userId, kpId);
		} else if(operate.equals("note")) {
			return aHomeworkDao.getOptionsOfNoteQuestions(userId, kpId);
		}
		return null;
	}

	private List<LStrMap<Object>> getQuestionsBasicInfo(long userId, int kpId, int subjectId, String operate) {
		List<LStrMap<Object>> knowledgePointsList = null;
		List<LStrMap<Object>> qiList = null;
		
		if (operate.equals("favorite")) {
			qiList = aHomeworkDao.getFavoriteQuestionsBasicInfo(userId, kpId);
		} else if(operate.equals("error")) {
			qiList = aHomeworkDao.getErrorQuestionsBasicInfo(userId, kpId);
		} else if(operate.equals("note")){
			qiList = aHomeworkDao.getNoteQuestionsBasicInfo(userId, kpId);
		}
		
		//得到该知识点的科目下的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<Object> kpMap = LStrMap.newInstance();
		knowledgePointsList = teacherHomeworkDao.getKnowledgePointsBySubject(subjectId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap.get("kp_name"));
		}
		
		for (int i = 0; i < qiList.size(); i++) {
			LStrMap<Object> tempMap = qiList.get(i);
			//将笔记内容转成所有img标签都在最后的形式
			String noteContent = tempMap.get("content")==null?"":tempMap.get("content").toString();
			tempMap.put("content", formatTextAndImg(noteContent));
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			if (tempMap.get("knowledge_point") != null) {
				String kpStr = tempMap.get("knowledge_point").toString();
				List<Object> kpList = new ArrayList<Object>();
				String[] kpArr = kpStr.split(",");
				for(String tempKpStr : kpArr) {
					kpList.add(kpMap.get(tempKpStr));
				}
				tempMap.put("knowledge_points", kpList);
			} else {
				tempMap.put("knowledge_points", null);
			}
			tempMap.remove("knowledge_point");
			
			//曾选项
			if (tempMap.get("answer") != null) {
				if (tempMap.get("ques_type").toString().equals("1") || tempMap.get("ques_type").toString().equals("2")) {
					List<String> everDoOptionList = new ArrayList<String>();
					String everDoOptionStr = tempMap.get("answer").toString();
					Map<String, Object> gsonMap = new GsonBuilder().create().fromJson(everDoOptionStr, LinkedHashMap.class);
					Iterator<String> iterator = gsonMap.keySet().iterator();
					int var = -1;
					while (iterator.hasNext()) {
						var++;
						String doubtOpt = gsonMap.get(iterator.next()).toString();
						if (doubtOpt.equals("1")) {
							everDoOptionList.add((char)(65+var)+"");
						}
					}
					tempMap.put("everdo_Options", everDoOptionList);
				}
			}
			
			tempMap.remove("answer");
		}
		
		return qiList;
	}

	public List<LStrMap<Object>> getNoteKnowledgePoints(int userId, int subjectId) {
		return aHomeworkDao.getNoteKnowledgePoints(userId, subjectId);
	}

	public void collectQuestion(long userId, int quesId, String remark) {
		//先删除原有的收藏记录，再收藏
		aHomeworkDao.cancelCollection(userId, quesId);
		aHomeworkDao.collectQuestion(userId, quesId, remark);
	}

	public void updateHomeworkRecordToCommit(int hwRecId) {
		aHomeworkDao.updateHomeworkRecordToCommit(hwRecId);
	}

	public List<LStrMap<Object>> getQuestionsInfo(int hwId, int hwRecId) {
		List<LStrMap<Object>> list = aHomeworkDao.getQuestionsInfo(hwId, hwRecId);
		for (int i=0,size=list.size(); i<size; i++) {
			LStrMap<Object> tempMap = list.get(i);
			if (tempMap.get("ques_type").toString().equals("4")) {
				//得到填空题学生答案
				String studentAnswer = tempMap.get("student_answer")==null?"":tempMap.get("student_answer").toString();
				//用@@分隔学生答案并遍历每个空格的答案
				String[] answerArr = studentAnswer.split("@@");
				for (int j=0,length=answerArr.length; j<length; j++) {
					//用##分隔每个空格的答案，并得到空格内容
					String[] tempArr = answerArr[j].split("##");
					String lineContent = "";
					if (tempArr.length > 1) {
						lineContent = tempArr[1];
					}
					
					lineContent = formatTextAndImg(lineContent);
					answerArr[j] = tempArr[0]+"##"+lineContent;//重新设置每个空格的信息
				}
				studentAnswer = "";
				for (int j=0,length=answerArr.length;j<length; j++) {
					studentAnswer = studentAnswer+answerArr[j]+"@@";
				}
				//将空格信息填充到小题
				tempMap.put("student_answer", studentAnswer.substring(0, studentAnswer.length()-2));
			} else if (tempMap.get("ques_type").toString().equals("5")) {
				//得到解答题学生答案
				String studentAnswer = tempMap.get("student_answer")==null?"":tempMap.get("student_answer").toString();
				tempMap.put("student_answer", formatTextAndImg(studentAnswer));
			}
		}
		return list;
	}

	/**
	 * 格式化文字和图片混排字符串，将所有的img标签提取到字符串的最后
	 * @param studentAnswer
	 * @return
	 */
	private String formatTextAndImg(String str) {
		String regex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		List<String> strList = new ArrayList<String>();
		
		//定义img字符串
		String imgsStr = "";
		while (matcher.find()) {
			String tempStr = matcher.group();
			strList.add(tempStr);
			imgsStr = imgsStr+tempStr;
		}
		return str.replaceAll(regex, "")+imgsStr;//得到格式化后的内容
	}

	public List<LStrMap<Object>> fixQuestionsAndOptions(List<LStrMap<Object>> questions, List<LStrMap<Object>> options) {
		//将同一小题的选项放到一个ArrayList中，并将该List以ques_id为键名放到Map中
		LStrMap<List<LStrMap<Object>>> optionsMap = LStrMap.newInstance();
		for (int i = 0; i < options.size(); i++) {
			LStrMap<Object> tempMap = options.get(i);
			if (optionsMap.containsKey(tempMap.get("ques_id").toString())) {
				optionsMap.get(tempMap.get("ques_id").toString()).add(tempMap);
			} else {
				List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(options.get(i));
				optionsMap.put(tempMap.get("ques_id").toString(), tempList);
			}
		}
		//将选项List与对应的小题放在一起
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).put("options", optionsMap.get(questions.get(i).get("ques_id").toString()));
		}
		return questions;
	}

	public List<LStrMap<Object>> getQuestion(int quesId) {
		List<LStrMap<Object>> qiList = aHomeworkDao.getQuestion(quesId);
		//得到该知识点的科目下的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<Object> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = teacherHomeworkDao.getKnowledgePointsBySubject(Integer.parseInt(qiList.get(0).get("subject_no").toString()));
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap.get("kp_name"));
		}
		
		for (int i = 0; i < qiList.size(); i++) {
			LStrMap<Object> tempMap = qiList.get(i);
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			if (tempMap.get("knowledge_point") != null) {
				String kpStr = tempMap.get("knowledge_point").toString();
				List<Object> kpList = new ArrayList<Object>();
				String[] kpArr = kpStr.split(",");
				for(String tempKpStr : kpArr) {
					kpList.add(kpMap.get(tempKpStr));
				}
				tempMap.put("knowledge_points", kpList);
			} else {
				tempMap.put("knowledge_points", null);
			}
			tempMap.remove("knowledge_point");
			
		}
		
		return qiList;
	}

	public List<LStrMap<Object>> getOption(int quesId) {
		return aHomeworkDao.getOption(quesId);
	}

	public String getStudentId(int userId) {
		List<LStrMap<Object>> list = aHomeworkDao.getStudentId(userId);
		if (list.size() > 0) {
			return list.get(0).get("student_id").toString();
		}
		return null;
	}

	public LStrMap<Object> getUnCommitHomeworkNum(int userId) {
		List<LStrMap<Object>> list = aHomeworkDao.getUnCommitHomeworkNum(userId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public LStrMap<Object> getHwRecordInfo(int hwId, int userId) {
		return aHomeworkDao.getHwRecordInfo(hwId, userId).get(0);
	}

	public List<LStrMap<Object>> getErrorQuestions(int hwId, int hwRecId) {
		List<LStrMap<Object>> questions = getQuestionsOfError(hwId, hwRecId);
		List<LStrMap<Object>> options = aHomeworkDao.getOptionsOfHomeworkErrorQuestions(hwId, hwRecId);
		
		//将同一小题的选项放到一个ArrayList中，并将该List以ques_id为键名放到Map中
		LStrMap<List<LStrMap<Object>>> optionsMap = LStrMap.newInstance();
		for (int i = 0; i < options.size(); i++) {
			LStrMap<Object> tempMap = options.get(i);
			if (optionsMap.containsKey(tempMap.get("ques_id").toString())) {
				optionsMap.get(tempMap.get("ques_id").toString()).add(tempMap);
			} else {
				List<LStrMap<Object>> tempList = new ArrayList<LStrMap<Object>>();
				tempList.add(options.get(i));
				optionsMap.put(tempMap.get("ques_id").toString(), tempList);
			}
		}
		//将选项List与对应的小题放在一起
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).put("options", optionsMap.get(questions.get(i).get("ques_id").toString()));
		}
		//根据o_score判断空格是否正确
		for(int i=0,size=questions.size(); i<size; i++) {
			LStrMap<Object> quesLStrMap = questions.get(i);
			//只是对填空题操作
			if (quesLStrMap.get("ques_type").toString().equals("4")) {
				String oScore = quesLStrMap.get("o_score")==null?"":quesLStrMap.get("o_score").toString();
				String[] oScoreArray = oScore.split(",");
				List<LStrMap<Object>> tempOptions = (List<LStrMap<Object>>) quesLStrMap.get("options");
				for (int j=0, tempSize=tempOptions.size(); j<tempSize; j++) {//遍历所有选项得分
					tempOptions.get(j).put("correct_flag", oScoreArray[j]);
				}
			}
		}
		return questions;
	}

	private List<LStrMap<Object>> getQuestionsOfError(int hwId, int hwRecId) {
		//得到这张作业对应的科目的所有知识点，并存到map中，键名是kp_id,键值是kp_name
		LStrMap<Object> kpMap = LStrMap.newInstance();
		List<LStrMap<Object>> knowledgePointsList = teacherHomeworkDao.getKnowledgePointsByHwId(hwId);
		for (int i=0; knowledgePointsList.size()>0 && i<knowledgePointsList.size(); i++) {
			LStrMap<Object> kpTempMap = knowledgePointsList.get(i);
			kpMap.put(kpTempMap.get("kp_id").toString(), kpTempMap.get("kp_name"));
		}
		
		List<LStrMap<Object>> qiList = aHomeworkDao.getErrorQuestions(hwId, hwRecId);	//查出作业的所有选项及对应的大题和小题信息
		
		for (int i = 0; i < qiList.size(); i++) {
			LStrMap<Object> tempMap = qiList.get(i);
			
			//将笔记内容转成所有img标签都在最后的形式
			String noteContent = tempMap.get("content")==null?"":tempMap.get("content").toString();
			tempMap.put("content", formatTextAndImg(noteContent));
			
			//将id,id2,id3...形式的知识点转成存有知识点名称的List
			if (tempMap.get("knowledge_point") != null) {
				String kpStr = tempMap.get("knowledge_point").toString();
				List<Object> kpList = new ArrayList<Object>();
				String[] kpArr = kpStr.split(",");
				for(String tempKpStr : kpArr) {
					kpList.add(kpMap.get(tempKpStr));
				}
				tempMap.put("knowledge_points", kpList);
			} else {
				tempMap.put("knowledge_points", null);
			}
			tempMap.remove("knowledge_point");
			
			//曾选项
			if (tempMap.get("ques_type").toString().equals("1") || tempMap.get("ques_type").toString().equals("2")) {
				List<String> everDoOptionList = new ArrayList<String>();
				String everDoOptionStr = tempMap.get("answer")==null?"":tempMap.get("answer").toString();
				Map<String, Object> gsonMap = new GsonBuilder().create().fromJson(everDoOptionStr, LinkedHashMap.class);
				Iterator<String> iterator = gsonMap.keySet().iterator();
				int var = -1;
				while (iterator.hasNext()) {
					var++;
					String doubtOpt = gsonMap.get(iterator.next()).toString();
					if (doubtOpt.equals("1")) {
						everDoOptionList.add((char)(65+var)+"");
					}
				}
				tempMap.put("everdo_Options", everDoOptionList);
			}
			tempMap.remove("answer");
			
		}
		
		return qiList;
	}

	public void cancelCollection(long userId, int quesId) {
		aHomeworkDao.cancelCollection(userId, quesId);
	}

	/**
	 * 自动插入做题记录详情
	 * @param eHomeworkRecord
	 */
	public void autoInsertHwRecordDetail(EHomeworkRecord eHomeworkRecord) {
		//获取所有小题的所有选项
		List<LStrMap<Object>> list = aHomeworkDao.getOptionsOfHomeworkQuestions(eHomeworkRecord.getHwId());
		//组装选项
		Map<String, Map<String, String>> optionMap = new LinkedHashMap<String, Map<String, String>>();
		Map<String, Map<String, Object>> scoreMap = new LinkedHashMap<String, Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			LStrMap<Object> tempMap = list.get(i);
			//将选项组装到选项map中
			if (optionMap.containsKey(tempMap.get("ques_id").toString())) {
				if (tempMap.get("ques_type").toString().equals("1") || tempMap.get("ques_type").toString().equals("2") ||tempMap.get("ques_type").toString().equals("3")) {
					optionMap.get(tempMap.get("ques_id").toString()).put(tempMap.get("opt_id").toString(), "0");
				} else {
					optionMap.get(tempMap.get("ques_id").toString()).put(tempMap.get("opt_id").toString(), "");
				}
			} else {
				Map<String, String> newMap = new LinkedHashMap<String, String>();
				if (tempMap.get("ques_type").toString().equals("1") || tempMap.get("ques_type").toString().equals("2") ||tempMap.get("ques_type").toString().equals("3")) {
					newMap.put(tempMap.get("opt_id").toString(), "0");
				} else {
					newMap.put(tempMap.get("opt_id").toString(), "");
				}
				optionMap.put(tempMap.get("ques_id").toString(), newMap);
			}
			//计算小题的得分，并存到分数map中
			if (!scoreMap.containsKey(tempMap.get("ques_id").toString())) {
				Map<String, Object> newMap = new LinkedHashMap<String, Object>();
				newMap.put("score", -1);
				if (tempMap.get("ques_type").toString().equals("4")) {
					int optNum = Integer.parseInt(tempMap.get("option_num").toString());
					String oScore = "";
					for (int j = 0; j < optNum; j++) {
						oScore = oScore+1+",";
					}
					oScore.substring(0, oScore.length()-1);
					newMap.put("oScore", oScore);
				}
				scoreMap.put(tempMap.get("ques_id").toString(), newMap);
			}
		}
		//使用组装的信息进行插入做题记录
		Iterator<String> iterator = optionMap.keySet().iterator();
		Gson gson = new GsonBuilder().create();
		while (iterator.hasNext()) {
			String key = iterator.next();
			EHomeworkRecordDetail eHomeworkRecordDetail = new EHomeworkRecordDetail();
			eHomeworkRecordDetail.setHwRecId(eHomeworkRecord.getHwRecId());
			eHomeworkRecordDetail.setQuesId(Integer.parseInt(key));
			eHomeworkRecordDetail.setAnswer(gson.toJson(optionMap.get(key)));
			eHomeworkRecordDetail.setScore(Integer.parseInt(scoreMap.get(key).get("score").toString()));
			eHomeworkRecordDetail.setoScore(scoreMap.get(key).get("o_score")==null?null:scoreMap.get(key).get("o_score").toString());
			studentHomeworkDao.saveEhomeworkRecordDetail(eHomeworkRecordDetail);
		}
	}

	public LStrMap<Object> getHwRecordDetailInfo(int hwRecId, int quesId) {
		List<LStrMap<Object>> list = aHomeworkDao.getHwRecordDetailInfo(hwRecId, quesId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<LStrMap<Object>> getOptionsOfOneQuestion(int quesId) {
		return aHomeworkDao.getOptionsOfOneQuestion(quesId);
	}

	public void updateHomeworkDetail(int hwRecId, int quesId, String answer,
			String studentAnswer, int score, String answerTime) {
		aHomeworkDao.updateHomeworkDetail(hwRecId, quesId, answer, studentAnswer, score, answerTime);
	}

	public void updateHomeworkDetail(int hwRecId, int quesId, String studentAnswer, String answerTime) {
		aHomeworkDao.updateHomeworkDetail(hwRecId, quesId,studentAnswer, answerTime);
	}

	public List<LStrMap<Object>> getQuesRecord(long userId, int quesId) {
		return aHomeworkDao.getQuesRecord(userId, quesId);
	}

	public List<LStrMap<Object>> getHwRecordId(int hwId, int userId) {
		return aHomeworkDao.getHwRecordId(hwId, userId);
	}

	public void updateHomeworkFinishedNum(int hwRecId, int num) {
		aHomeworkDao.updateHomeworkFinishedNum(hwRecId, num);
	}

}