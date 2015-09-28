package com.eps.android.web.homework;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.android.domain.ENote;
import com.eps.android.service.homework.AHomeworkService;
import com.eps.android.web.Base64Util;
import com.eps.dao.MaxValueINcrementer;
import com.eps.domain.EHomeworkRecord;
import com.eps.service.homework.StudentHomeworkService;
import com.eps.service.studentgrade.StudentGradeService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class AHomeworkController extends BaseController {
	@Autowired private AHomeworkService aHomeworkService;
	@Autowired private StudentHomeworkService studentHomeworkService;
	@Autowired private StudentGradeService sgs;
	@Autowired private MaxValueINcrementer seq_hwRecId;
	
	
	//显示学生未提交作业数量及应提交作业总数量
	@RequestMapping(value="/android/unCommitHomeworkNum.json")
	public String getUnCommitHomework(int uid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.getUnCommitHomeworkNum(uid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//显示学生的所有未到交作业时间的作业
	@RequestMapping(value="/android/getHomeworks.json")
	public String toIndex(int uid, HttpServletRequest request,ModelMap mm) {
		try {
			int pageNo = request.getParameter("pageNo")==null?1:Integer.parseInt(request.getParameter("pageNo"));
			List<LStrMap<Object>> list = aHomeworkService.getHomeworks(uid, pageNo);
			mm.put("DATA", list);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//作业历史页面
	@RequestMapping(value="/android/homeworkHistory.json")
	public String toHwHistory(int uid, int subjectid, HttpServletRequest request, ModelMap mm) {
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map = LStrMap.newInstance();
		try {
			int pageNo = request.getParameter("pageNo")==null?1:Integer.parseInt(request.getParameter("pageNo"));
			map.put("starStatistic", aHomeworkService.getStarStatistic(uid, subjectid));
			map.put("starNumOfHomeworks", aHomeworkService.getStarNumOfHomeworks(pageNo, uid, subjectid));
			list.add(map);
			mm.put("DATA", list);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//作业报告页面
	@RequestMapping(value="/android/hwreport.json")
	public String toHwReport(int uid, int hwid, HttpServletRequest request, ModelMap mm) {
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map = LStrMap.newInstance();
		try {
			map.put("hwInfo", aHomeworkService.getHwInfo(uid, hwid));
			map.put("answerCard", aHomeworkService.getAnswerCard(uid, hwid));
			map.put("kps", aHomeworkService.getKnowledgePoints(uid, hwid));
			list.add(map);
			mm.put("DATA", list);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看历史作业试题页面
	@RequestMapping(value="/android/viewQuestions.json")
	public String viewQuestions(int hwid, int hwrecid, HttpServletRequest request, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.getAllQuestions(hwid, hwrecid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	//查看历史作业错误试题页面
	@RequestMapping(value="/android/viewErrorQuestions.json")
	public String viewErrorQuestions(int hwid, int hwrecid, HttpServletRequest request, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.getErrorQuestions(hwid, hwrecid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//上传笔记图片
	@RequestMapping(value="/android/uploadNoteImg.json")
	public String uploadNoteImg(String imgData, String imgName, HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map = LStrMap.newInstance();
		try {
			String fileName = new Date().getTime()+imgName+".jpg";
			if(!Base64Util.generateImage(imgData, request.getSession().getServletContext().getRealPath("")+"\\images\\note\\"+fileName)) {
				map.put("msg", "系统出错");
				mm.put("DATA", map);
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
			} else {
				map.put("msg", "上传成功");
				map.put("path", "/images/note/"+fileName);
				mm.put("DATA", map);
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "系统出错");
			mm.put("DATA", map);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//上传笔记内容
	@RequestMapping(value="/android/uploadNoteContent.json")
	public String uploadNoteContent(HttpServletRequest request, ModelMap mm) {
		try {
			String text = URLDecoder.decode(request.getParameter("text")==null?"":request.getParameter("text"), "UTF-8");
			String imgs = URLDecoder.decode(request.getParameter("imgs")==null?"":request.getParameter("imgs"), "UTF-8");
			String content = text+imgs;
			String noteId = request.getParameter("noteid");
			if (noteId==null || noteId.equals("")) {//没有noteid说明是新建笔记
				ENote eNote = new ENote();
				eNote.setQuesId(Integer.parseInt(request.getParameter("quesid").toString()));
				eNote.setUserId(Long.parseLong(request.getParameter("uid")));
				eNote.setCommitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
				eNote.setContent(content);
				
				aHomeworkService.saveNote(eNote);
			} else {//修改笔记
				aHomeworkService.updateNote(Integer.parseInt(noteId), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())), content);
			}
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//收藏题目
	@RequestMapping(value="/android/collectQuestion.json")
	public String collectQuestion(int uid, int quesid, HttpServletRequest request, ModelMap mm) {
		try {
			String remark = request.getParameter("remark");
			aHomeworkService.collectQuestion(uid, quesid, remark);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//取消收藏
	@RequestMapping(value="/android/cancelCollection.json")
	public String cancelCollection(int uid, int quesid, ModelMap mm) {
		try {
			aHomeworkService.cancelCollection(uid, quesid);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看收藏的题目数，按知识点分
	@RequestMapping(value="/android/showFavorites.json")
	private String showFavorites(int uid, int subjectid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.getFavoriteKnowledgePoints(uid, subjectid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看收藏的某个知识点对应的小题的信息
	@RequestMapping(value="/android/showFavQuestions.json")
	public String showFavoriteQuestions(int uid, int kpid, int subjectid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.packageQuestions(uid, kpid, subjectid, "favorite"));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看错题集的题目数，按知识点分
	@RequestMapping(value="/android/showErrorKps.json")
	private String showErrorQuestions(int uid, int subjectid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.getErrorKnowledgePoints(uid, subjectid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看错题集的某个知识点对应的小题的信息
	@RequestMapping(value="/android/showErrorQuestions.json")
	public String showErrorQuestions(int uid, int kpid, int subjectid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.packageQuestions(uid, kpid, subjectid, "error"));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看笔记中的题目数，按知识点分
	@RequestMapping(value="/android/showNoteKps.json")
	private String showNoteQuestions(int uid, int subjectid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.getNoteKnowledgePoints(uid, subjectid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看笔记的某个知识点对应的小题的信息
	@RequestMapping(value="/android/showNoteQuestions.json")
	public String showNoteQuestions(int uid, int kpid, int subjectid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.packageQuestions(uid, kpid, subjectid, "note"));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//上交作业
	@RequestMapping(value="/android/handInHomework.json")
	public String handInHomework(int hwrecid, HttpServletRequest request, ModelMap mm) {
		try {
			//更新e_hw_record表
			aHomeworkService.updateHomeworkRecordToCommit(hwrecid);
			
			//计算每个小题的得分，更新数据库中每个小题的做题详情的小题得分
			studentHomeworkService.updateEhomeworkRecordDetailScore(hwrecid);
			
			//判断是否是自动阅卷
			int autoMarkFlag = (Integer)studentHomeworkService.getAutoCorrectFlag(hwrecid).get("correct");
			if (autoMarkFlag == 1) {
				//更新考生的作业记录的批改字段，设为已批改
				studentHomeworkService.updateHwRecord(hwrecid, 2, 0);
				//更新学生成绩星星字段
				studentHomeworkService.updateEhomeworkRecordStar(hwrecid);
			}
			
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看课程表
	@RequestMapping(value="/android/showLessons.json")
	private String showLessons(int uid, HttpServletRequest request, ModelMap mm) {
		try {
			String stuId = aHomeworkService.getStudentId(uid);
			if (stuId != null) {
				mm.put("DATA", sgs.getLessonByStudent(Integer.parseInt(stuId)));
			} else {
				mm.put("DATA", null);
			}
			
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//显示作业题目信息
	@RequestMapping(value="/android/doHomework.json")
	public String doHomework(int hwid, HttpServletRequest request, ModelMap mm) {
		try {
			int hwRecId = Integer.parseInt(request.getParameter("hwrecid"));
			List<LStrMap<Object>> questions = null;
			//如果hwrecid为null，说明是第一次做这次作业，要新建一条作业记录
			if (hwRecId == 0) {
				int userId = Integer.parseInt(request.getParameter("uid"));
				
				//查询数据库中是否已经有该学生的做题记录
				List<LStrMap<Object>> hwRecIdList = aHomeworkService.getHwRecordId(hwid, userId);
				if (hwRecIdList.size() > 0) {
					hwRecId = (Integer)hwRecIdList.get(0).get("hw_rec_id");
					questions = aHomeworkService.getQuestionsInfo(hwid, hwRecId);
					mm.put("hw_rec_id", hwRecId);
				} else {
					LStrMap<Object> map = aHomeworkService.getHwRecordInfo(hwid, userId);
					EHomeworkRecord eHomeworkRecord = new EHomeworkRecord();
					eHomeworkRecord.setClassId(Integer.parseInt(map.get("class_id").toString()));
					eHomeworkRecord.setHwId(hwid);
					eHomeworkRecord.setUserId(userId);
					eHomeworkRecord.setStudentId(Integer.parseInt(map.get("student_id").toString()));
					eHomeworkRecord.setSubjectId(Integer.parseInt(map.get("subject_id").toString()));
					eHomeworkRecord.setHwRecId(seq_hwRecId.nextIntValue());
					studentHomeworkService.saveEhomeworkRecord(eHomeworkRecord);
					aHomeworkService.autoInsertHwRecordDetail(eHomeworkRecord);
					mm.put("hw_rec_id", eHomeworkRecord.getHwRecId());
					questions = aHomeworkService.getQuestionsInfo(hwid, eHomeworkRecord.getHwRecId());
				}
			} else {
				questions = aHomeworkService.getQuestionsInfo(hwid, hwRecId);
				mm.put("hw_rec_id", hwRecId);
			}
			
			List<LStrMap<Object>> options = aHomeworkService.getOptions(hwid);
			mm.put("DATA", aHomeworkService.fixQuestionsAndOptions(questions, options));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
			
	//分享试题
	@RequestMapping(value="/shareQuestion.html")
	public String shareQuestions(int quesid, ModelMap mm) {
		List<LStrMap<Object>> question = aHomeworkService.getQuestion(quesid);
		List<LStrMap<Object>> option = aHomeworkService.getOption(quesid);
		mm.put("qs", aHomeworkService.fixQuestionsAndOptions(question, option).get(0));
		mm.put("title", "试题分享");
		return "shareQuestion";
	}
	
	//上传做题情况，一道题的情况
	@RequestMapping(value="/android/uploadDoHomeworkInfo.json")
	public String uploadDoHomeworkInfo(int hwrecid, String answertime, String quesdata, ModelMap mm) {
		try {
			quesdata = URLDecoder.decode(quesdata, "UTF-8");
			Gson gson = new GsonBuilder().create();
			Map map = gson.fromJson(quesdata, Map.class);
			int quesId = Integer.parseInt(map.get("ques_id").toString());
			
			List<Map<String, Object>> quesData = (List<Map<String, Object>>) map.get("ques");
			LStrMap<Object> hwRecordDetailMap = aHomeworkService.getHwRecordDetailInfo(hwrecid, quesId);
			List<LStrMap<Object>> optionsList = aHomeworkService.getOptionsOfOneQuestion(quesId);
			LStrMap<LStrMap<Object>> optionMap = LStrMap.newInstance();
			for(int i=0; i<optionsList.size(); i++) {
				optionMap.put(optionsList.get(i).get("opt_id").toString(), optionsList.get(i));
			}
			String studentAnswer = "";//标示学生答案
			//数据库中的学生答案
			String oldStudentAnwser = hwRecordDetailMap.get("student_answer")==null?"":hwRecordDetailMap.get("student_answer").toString();
			
			//如果是单选题或多选题或判断题，就比较answer内容和传入的参数，记录曾作项，比较参考答案和传入的答案，计算得分
			int quesType = Integer.parseInt(hwRecordDetailMap.get("ques_type").toString());
			if (quesType==1 || quesType==2 || quesType==3) {
				Map answerMap = gson.fromJson(hwRecordDetailMap.get("answer").toString(), Map.class);
				
				boolean flag = true;	  //标示学生是否回答正确
				int score = 0;			  //标示得分
				//判断学生是否选择了某个选项，如果选了，就把这个答案置为曾选项，并且要记录学生答案
				for (int i = 0; i < quesData.size(); i++) {
					String tempOptId = quesData.get(i).get("opt_id").toString();
					answerMap.put(tempOptId, 1+"");
					studentAnswer = studentAnswer + optionMap.get(tempOptId).get("opt_no").toString()+"@@";
				}
				//去除答案最后的@@
				if (studentAnswer.length() > 2) {
					studentAnswer = studentAnswer.substring(0, studentAnswer.length()-2);
				}
				if (studentAnswer.equals(hwRecordDetailMap.get("ques_refer").toString())) {
					score = 1;
				}
				//更新做题记录详情表，需更新的字段有：answer、student_answer、score、answer_time
				aHomeworkService.updateHomeworkDetail(hwrecid, quesId, gson.toJson(answerMap), studentAnswer, score, answertime);
				
				//作业finished_num修改
				if(quesData.size() > 0) {//传入的有数据
					//如果原来没有做过该题，则finished_num要加1，如果原来做过，则不更改
					if (oldStudentAnwser.equals("")) {
						aHomeworkService.updateHomeworkFinishedNum(hwrecid, 1);
					}
				} else {
					Object tempAnswer = hwRecordDetailMap.get("student_answer");
					if (tempAnswer != null) {
						if (!tempAnswer.toString().equals("")) {
							aHomeworkService.updateHomeworkFinishedNum(hwrecid, -1);
						}
					}
				}
			} else if (quesType == 4) {//如果是填空题，则不需要更新answer字段和比较答案
				boolean flag = false;
				//如果有一个空格的内容不为空，就代表这个填空题做了
				for (int i = 0; i < quesData.size(); i++) {
					if (!quesData.get(i).get("answer_content").toString().equals("")) {
						flag = true;
					}
					studentAnswer = studentAnswer + optionMap.get(quesData.get(i).get("opt_id").toString()).get("opt_no").toString()+"##"+quesData.get(i).get("answer_content").toString()+"@@";
				}
				//去除答案最后的@@
				if (studentAnswer.length() > 2) {
					studentAnswer = studentAnswer.substring(0, studentAnswer.length()-2);
				}
				//更新做题记录，需要更新的字段只有student_answer,answer_time
				aHomeworkService.updateHomeworkDetail(hwrecid, quesId, studentAnswer,answertime);
				
				//修改作业finished_num
				boolean oldFlag = false;//表示数据库中小题做题情况，为false代表小题原来没做，为true为原来做了
				//以@@分隔填空题答案如①##4-1@@②##4-2为数组
				String[] answerArray = oldStudentAnwser.split("@@");
				for (int i=0, lengt=answerArray.length; i<lengt; i++) {
					//将分开后的数组内容再根据##分隔，如果##后面的内容为空，代表原来没有做过该题
					String[] tempArray = answerArray[i].split("##");
					if (tempArray.length > 1) {
						oldFlag = true;
						break;
					}
				}
				if (flag && !oldFlag) {//当前做了，以前未作，则finished_num增加
					aHomeworkService.updateHomeworkFinishedNum(hwrecid, 1);
				} else if (!flag && oldFlag) {//当前未作，以前做了，则finished_num减少
					aHomeworkService.updateHomeworkFinishedNum(hwrecid, -1);
				}
			} else {
				studentAnswer = quesData.get(0).get("answer_content").toString();
				//更新做题记录，需要更新的字段只有student_answer,answer_time
				aHomeworkService.updateHomeworkDetail(hwrecid, quesId, studentAnswer,answertime);
				
				//修改finished_num
				if (!studentAnswer.equals("") && oldStudentAnwser.equals("")) {//当前做了，以前未作
					aHomeworkService.updateHomeworkFinishedNum(hwrecid, 1);
				} else if (studentAnswer.equals("") && !oldStudentAnwser.equals("")) {//当前未作，以前做了
					aHomeworkService.updateHomeworkFinishedNum(hwrecid, -1);
				}
			}
			
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//查看某个小题的做题记录
	@RequestMapping(value="/android/getQuesRecord.json")
	public String getQuesRecord(int uid, int quesid, ModelMap mm) {
		try {
			mm.put("DATA", aHomeworkService.getQuesRecord(uid, quesid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
}