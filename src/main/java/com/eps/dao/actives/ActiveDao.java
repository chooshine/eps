package com.eps.dao.actives;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class ActiveDao extends BaseDao {
	
	@Value("${drawmoney.create}")
	private String createRecord;
	
	@Value("${drawmoney.get.allrecords.byuserid}")
	private String getAllRecordsByUser_id;
	
	@Value("${drawmoney.update.drawstate}")
	private String updateDrawState;
	
	@Value("${drawmoney.get.currentaccount}")
	private String getCurrentAccount;
	
	@Value("${drawmoney.update.accountcash}")
	private String updateAccountCash;
	
	@Value("${drawmoney.get.onerecord}")
	private String getOneRecord;
	
	@Value("${drawmoney.get.bystate}")
	private String getByState;
	
	@Value("${drawmoney.get.allrecords}")
	private String getAllRecords;
	
	@Value("${drawmoney.get.bank.code}")
	private String getBankByCode;
	
	@Value("${trade.get.all}")
	private String getAllTrade;

	@Value("${trade.get.all.byuserid}")
	private String getAllTradeByUserId;
	
	@Value("${drawmoney.get.freezemoney.amount}")
	private String getFreezeMoneyAmount;
	
	/**
	 * 插入一条提现记录，传入用户的user_id、开户行、收款人、银行账号、提取金额、返回值为空
	 * @param user_id
	 * @param bankName
	 * @param receiver
	 * @param account
	 * @param amount
	 */
	public void createDrawRecord(long user_id,String bankName,String receiver,String account,double amount,Calendar calendar){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("drawmoney_time", calendar.getTime());
		params.put("user_id", user_id);
		params.put("bankname", bankName);
		params.put("receiver", receiver);
		params.put("account", account);
		params.put("amount", amount);
		this.excute(createRecord, params);
	}

	/**
	 * 查看某用户所有提现，传入用户user_id，返回值为List
	 * @param user_id
	 * @return
	 */
	public Page getAllDrawRecord(long user_id,int pageNo){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return pageQuery(getAllRecordsByUser_id, pageNo, params);
	}

	/**
	 * 查看某条提现记录，传入提现记录id，返回值为List
	 * @param recordId
	 * @return
	 */
	public List<LStrMap<Object>> getOneDrawRecord(long recordId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("drawmoney_id", recordId);
		return this.find(getOneRecord, params);
	}

	/**
	 * 修改某用户的某条提现记录的状态，传入提现记录id，返回值为void
	 * @param recordId
	 */
	public void updateRecordState(long drawmoney_id, String unpassReason, Date transfer_time, long transfer_user_id,String transfer_evidence,int state){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("drawmoney_id", drawmoney_id);
		params.put("transfer_time", transfer_time);
		params.put("transfer_user_id", transfer_user_id);
		params.put("transfer_evidence", transfer_evidence);
		params.put("state", state);
		params.put("remark",unpassReason);
		this.excute(updateDrawState, params);
	}

	/**
	 * 得到某用户的账户余额，传入用户id，返回值为List
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getBalance(long user_id){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		return this.find(getCurrentAccount,params);
	}

	/**
	 * 修改某用户的账户余额，传入用户id,传入提取现金额度，返回值为void
	 * @param user_id
	 * @param drawAmount
	 */
	public void updateAccountCash(long user_id,double drawAmount){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", user_id);
		params.put("drawAmount", drawAmount);
		this.excute(updateAccountCash, params);
	}
	
	/**
	 * 根据状态得到提现记录
	 * @param state
	 * @return
	 */
	public List<LStrMap<Object>> getRecordsByState(String state){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("state", state);
		return this.find(getByState, params);
	}
	
	/**
	 * 得到所有提现记录，包括已转账和未转账的
	 * @return
	 */
	public List<LStrMap<Object>> getAllRecords(){
		return this.find(getAllRecords);
	}
	
	/**
	 * 根据银行码得到银行名称
	 * @param code
	 * @return
	 */
	public List<LStrMap<Object>> getBankName(String code){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("code", code);
		return this.find(getBankByCode,params);
	}
	
	/**
	 * 得到所有交易记录
	 * @param trade_id
	 * @return
	 */
	public Page getAllTrade(int pageNo){
		return pageQuery(getAllTrade, pageNo, null);
	}
	
	/**
	 * 得到某用户的所有交易记录
	 * @param trade_id
	 * @return
	 */
	public Page getAllTradeByUserId(int pageNo,long userId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		return pageQuery(getAllTradeByUserId, pageNo, params);
	}
	
	/**
	 * 得到用户当前的正在审核中的额度
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getFreezeMoneyAmount(long userId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("user_id", userId);
		return this.find(getFreezeMoneyAmount, params);
	}
}