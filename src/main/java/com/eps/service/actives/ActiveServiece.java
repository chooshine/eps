package com.eps.service.actives;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.actives.ActiveDao;
import com.eps.dao.esextends.ExamOrderDao;
import com.eps.domain.FTrade;
import com.eps.utils.FileUploader;
import com.eps.utils.LStrMap;
import com.eps.utils.SequenceFactory;
import com.eps.utils.UStrMap;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;

@Service
public class ActiveServiece {

	@Autowired 
	private ActiveDao activeDao;
	
	@Autowired
	private ExamOrderDao eod;
	
	public LStrMap<Object> getBankName(String code){
		List<LStrMap<Object>> list = activeDao.getBankName(code);
		LStrMap<Object> map = list.get(0);
		return map;
	}
	
	/**
	 * 显示某用户所有提现记录，传入用户user_id，返回List
	 * @param user_id
	 * @return
	 */
	public Page getAllDrawRecord(long user_id,int pageNo){
		return activeDao.getAllDrawRecord(user_id,pageNo);
	}

	/**
	 * 查看某用户某条提现记录，传入用户user_id，传入提现记录id，返回List
	 * @param recordId
	 * @return
	 */
	public List<LStrMap<Object>> getOneDrawRecord(long recordId){
		return activeDao.getOneDrawRecord(recordId);
	}
	
	/**
	 * 显示某用户的账户余额及最高可提取额度，传入用户id，返回List
	 * @param user_id
	 * @return
	 */
	public List<LStrMap<Object>> getBalance(long user_id){
		return activeDao.getBalance(user_id);
	}
	
	/**
	 * 插入一条提现记录，传入用户的user_id、开户行、收款人、银行账号、提取金额、返回值为空
	 * @param user_id
	 * @param bankName
	 * @param receiver
	 * @param account
	 * @param amount
	 */
	public void createDrawRecord(long user_id,String bankName,String receiver,String account,float amount){
		//获取时间
		Calendar calendar = Calendar.getInstance();
		//插入提现记录
		activeDao.createDrawRecord(user_id, bankName, receiver, account, amount, calendar);
	}
	
	/**
	 * 修改某用户的某条提现记录的状态，传入提现记录id,汇款时间和汇款人用户编号，返回值为void
	 * @param recordId
	 * @param transfer_time
	 * @param transfer_user_id
	 */
	public void updateRecordState(long recordId, String unpassReason, Date transfer_time, Long transfer_user_id, String transfer_evidence, int state){
		activeDao.updateRecordState(recordId, unpassReason, transfer_time, transfer_user_id, transfer_evidence,state);
	}
	
	/**
	 * 根据状态得到提现记录
	 * @param state
	 * @return
	 */
	public List<LStrMap<Object>> getRecordsByState(String state){
		return activeDao.getRecordsByState(state);
	}
	
	/**
	 * 得到所有提现记录，包括已转账和未转账的
	 * @return
	 */
	public List<LStrMap<Object>> getAllRecords(){
		return activeDao.getAllRecords();
	}
	
	/**
	 * 审核通过
	 * @param drawMoneyId
	 * @param checkUser
	 * @param filePath
	 */
	public void checkPassed(long drawMoneyId,long checkUser,long recordUser,String filePath){
		//更新提现记录状态
		List<LStrMap<Object>> cashListBefore = getBalance(recordUser);
		Calendar calendar = Calendar.getInstance();
		updateRecordState(drawMoneyId, null, calendar.getTime(), checkUser, filePath, 1);
		
		//更新用户账户
		double amount = (Double)activeDao.getOneDrawRecord(drawMoneyId).get(0).get("amount");
		activeDao.updateAccountCash(recordUser, amount);

		//插入交易记录
		FTrade trade = new FTrade();
		String tradeId = SequenceFactory.getTradeSequence();
		trade.setTradeId(tradeId);
		trade.setUserId(recordUser);
		trade.setTradeType(FTrade.PAY);
		Date drawmoney_time = calendar.getTime();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		trade.setTradeTime(f.format(drawmoney_time));
		trade.setMoney((float)amount);
		trade.setTradeStatus(FTrade.TRADESUCCESS);
		trade.setModelId(FTrade.DRAWMONEYID);
		trade.setGoodsId(String.valueOf(FTrade.DRAWMONEYID));
		trade.setTradeName("提现");
		eod.saveTradeRecordNoDao(trade);
		
		//插入交易日志表
		UStrMap<Object> logMap = UStrMap.newInstance();
		String tradeLogId = SequenceFactory.getTradeLogSequence();
		logMap.put("TRADE_LOG_ID", tradeLogId);
		logMap.put("TRADE_ID", tradeId);
		logMap.put("CASH", amount);
		logMap.put("COUPON", 0);
		logMap.put("USER_ID", recordUser);
		logMap.put("REMARK", "");
		logMap.put("TRADE_TYPE", 1);
		logMap.put("TRADE_TIME", f.format(drawmoney_time));
		List<LStrMap<Object>> cashListAfter = getBalance(recordUser);
		logMap.put("CASH_BEFORE", cashListBefore.get(0).get("CASH"));
		logMap.put("CASH_AFTER", cashListAfter.get(0).get("CASH"));
		logMap.put("COUPON_BEFORE", 0);
		logMap.put("COUPON_AFTER", 0);
		eod.saveTradeLogDao(logMap);
	}
	
	/**
	 * 审核不通过
	 * @param drawMoneyId
	 * @param userId
	 * @param filePath
	 */
	public void checkUnpassed(long drawMoneyId,String unpassReason, List<LStrMap<Object>> recordList, long checkUser){
		//更新提现记录状态
		Calendar calendar = Calendar.getInstance();
		updateRecordState(drawMoneyId, unpassReason, calendar.getTime(), checkUser, null, 2);
	}
	
	/**
	 * 上传证书
	 * @param userId
	 * @return
	 */
	public Map<String,String> upEvidence(long checkUser, HttpServletRequest request,HttpServletResponse response){
		FileUploader photoUpload=new FileUploader();
		//设置保存路径
		photoUpload.setSavePath("/images/transferevidence");
		//设置最大上传大小
		photoUpload.setFileSizeMax(1024000);
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
		String filename = request.getParameter("drawmoney_id");
		FileNameGenerator fileNameGenerator=new DrawMoneyFileNameGenerator(filename);
		photoUpload.setFileNameGenerator(fileNameGenerator);
		Result resut=photoUpload.upload(request, response);
		Set<String> names=photoUpload.getFileNames();
		Iterator iterator=names.iterator();
		StringBuffer sbf=new StringBuffer();
		String filePath = "";
		while (iterator.hasNext()) {
			filePath = "images/transferevidence/"+iterator.next().toString();
			sbf.append(filePath+",");
		}
		String errorInfo="上传失败！";
		if("SUCCESS".equals(resut.toString())){
			errorInfo="上传成功！";
			int recordUser = (Integer)getOneDrawRecord(Long.parseLong(filename)).get(0).get("user_id");
			checkPassed(Long.parseLong(filename),checkUser, recordUser, filePath);
		}else if("FILE_SIZE_EXCEEDED".equals(resut.toString())){
			errorInfo="上传失败，文件大小超过限制";
		}
		Map<String,String> map=new HashMap<String, String>();
		map.put("errorInfo", errorInfo);
		map.put("path", new String(sbf));
		return map;
	}
	
	/**
	 * 得到所有的交易记录
	 * @param pageNo
	 * @return
	 */
	public Page getAllTrade(int pageNo){
		return activeDao.getAllTrade(pageNo);
	} 
	
	/**
	 * 得到某用户的所有的交易记录
	 * @param pageNo
	 * @return
	 */
	public Page getAllTradeByUserId(int pageNo, long userId){
		return activeDao.getAllTradeByUserId(pageNo,userId);
	}
	
	public double getFreezeMoneyAmount(long userId){
		Object object = activeDao.getFreezeMoneyAmount(userId).get(0).get("freezemoney");
		
		if (object != null) {
			return (Double)object;
		}
		return 0;
	}
	
	//得到账户的几个数值，包括账户余额，当前处于提现审核状态的金额额度，当前未处于审核状态的金额额度，当前可提取的金额额度（为100的整数倍）
	public LStrMap<Object> getSeveralValue(long userId){
		//得到正在审核中的提现总额度
		double freezeMoney = this.getFreezeMoneyAmount(userId);
		//得到用户账户余额
		List<LStrMap<Object>> balance = this.getBalance(userId);
		double cash = ((BigDecimal)balance.get(0).get("cash")).doubleValue();
		//得到未冻结的账户额度
		double unfreezeMoney = cash-freezeMoney;
		//得到最大可提取的100的整数倍的金额
		int permitMoney = ((int)(unfreezeMoney/100))*100;
		
		LStrMap<Object> map = LStrMap.newInstance();
		map.put("freezemoney", freezeMoney);
		map.put("cash", cash);
		map.put("unfreezemoney",unfreezeMoney);
		map.put("permitmoney", permitMoney);
		return map;
	}
}
