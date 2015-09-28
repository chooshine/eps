package com.eps.dao.network;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.ETestRecord;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class NetWorkStudentDao  extends BaseDao{
	@Value("${network.student.getTest}")
	private String getStudentTest;
	@Value("${network.student.save.etestrecord}")
	private String saveTestRecord;
	@Value("${network.student.get.testrecid.by.testid.subjectid.examid.userid}")
	private String getTestRecIdByTestIdSubjectIdExamIdUserId;
	
	
	/**
	 * 得到试卷的额外信息
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<LStrMap<Object>> getNowExamInfoDao(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return find(getStudentTest, params);
	}
	
	/**
	 * 保存考试记录
	 * @param eTestRecord
	 * @return
	 */
	public int saveEtestRecord(ETestRecord eTestRecord){
		return this.excute(saveTestRecord, eTestRecord.toMap());
	}

	public List<LStrMap<Object>> getTestRecId(ETestRecord eTestRecord) {
		return this.find(getTestRecIdByTestIdSubjectIdExamIdUserId, eTestRecord.toMap());
	}
	
	
	
	

}
