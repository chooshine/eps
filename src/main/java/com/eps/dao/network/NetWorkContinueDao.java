package com.eps.dao.network;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.domain.EExam;
import com.eps.domain.EOption;
import com.eps.domain.EQuesType;
import com.eps.domain.EQuestion;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class NetWorkContinueDao extends BaseDao{
	
	@Value("${network.e_exam.releasestatus}")
	private String getUserFromExamByReleasestatus;
	@Value("${network.e_exam.queryByexamId}")
	private String getExamQuestionByQuesId;
	@Value("${network.e_exam.releasestatusIsOne}")
	private String getUserFromExamByReleasestatusIsOne;
	@Value("${network.e_exams.get}")
	private String getexams;
	@Value("${network.e_exam.updataByDel}")
	private String updataToDel;
	@Value("${network.e_exam.updateExamTest}")
	private String updateExamTest;
	
	/**
	 * 查询未发布的试卷
	 * @return
	 */
	public Page getUserFromExamByReleasestatus(int pageNo,long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return pageQuery(getUserFromExamByReleasestatus,pageNo, params);
		
	}
	
	
	public List<LStrMap<Object>> getExamQuestionByQuesId(long EXAM_ID){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("EXAM_ID", EXAM_ID);
		return find(getExamQuestionByQuesId, params);	
	}
//	public void delExamQuestionByExamId(EQuestion eq){
//		this.excute(delExamQuestionByExamId, eq.toMap());
//	}
//	public void delOptionByQuesId(EOption eo){
//		this.excute(delOptionByQuesId, eo.toMap());
//	}
//	public void delEQuesTypeByExamId(EQuesType eqt){
//		this.excute(delEQuesTypeByExamId, eqt.toMap());
//	}
//	public void delExamByExamId(EExam eex){
//		this.excute(delExamByExamId, eex.toMap());
//	}
	
	public List<LStrMap<Object>> getexams(long EXAM_ID){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("EXAM_ID", EXAM_ID);
		return find(getexams, params);
		
	}
	
	public int updateExamTest(EExam eex){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("test_id", eex.getTestId());
		paramMap.put("exam_id", eex.getExamId());
	    return this.excute(updateExamTest, paramMap);
	}
	
	public int updataToDel(EExam eex){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("exam_id", eex.getExamId());
	    return this.excute(updataToDel, paramMap);
	}
	
	/**
	 * 查询发布的试卷
	 * @return
	 */
	public Page getUserFromExamByReleasestatusIsOne(int pageNo,long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return pageQuery(getUserFromExamByReleasestatusIsOne, pageNo,params);
		
	}
	
	
	
	

	
//	@Value("${network.e_exam.delEquestionByExamId}")
//	private String delExamQuestionByExamId;
	
//	@Value("${network.e_exam.delByQuesId}")
//	private String delOptionByQuesId;
	
//	@Value("${network.e_exam.questype.delByexamId}")
//	private String delEQuesTypeByExamId;
	
//	@Value("${network.e_exam.delByexamId}")
//	private String delExamByExamId;
	
	
	
			

			

			

}
