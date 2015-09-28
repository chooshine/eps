package com.eps.service.network;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.network.NetWorkStudentDao;
import com.eps.domain.ETestRecord;
import com.eps.utils.LStrMap;


@Service
public class NetWorkStudentService {
	@Autowired
	NetWorkStudentDao nwsd;
	@Autowired
	private MaxValueINcrementer seq_testRecId;
	
	public List<LStrMap<Object>> getNowExamInfoDao(long userId){
		return nwsd.getNowExamInfoDao(userId);
	}
	
	
	/**
	 * 保存记录表
	 * @param eTestRecord
	 * @return
	 */
	public int saveEtestRecordService(ETestRecord eTestRecord){
		int testRecId=seq_testRecId.nextIntValue();
		eTestRecord.setTestRecId(testRecId);
		eTestRecord.setScore(0);
		eTestRecord.setExamStatus(0);
		eTestRecord.setCommitFlag(0);
		eTestRecord.setExamUseTime("00:00:00");
		
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		eTestRecord.setExamTime(dateformat.format(date));
		nwsd.saveEtestRecord(eTestRecord);
		return testRecId;
	}


	public List<LStrMap<Object>> getTestRecId(ETestRecord eTestRecord) {
		return nwsd.getTestRecId(eTestRecord);
	}
	
}
