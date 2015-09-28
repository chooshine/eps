package com.eps.service.corpus;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.corpus.SubscribeDao;
import com.eps.dao.esextends.ExamOrderDao;
import com.eps.dao.user.UserDao;
import com.eps.domain.FGoodModel;
import com.eps.domain.FTrade;
import com.eps.domain.LSubscribe;
import com.eps.utils.DateHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.SequenceFactory;
import com.eps.utils.UStrMap;


@Service
public class SubscribeService {
	
	@Autowired
	private SubscribeDao sdao;
	
	@Autowired
	private UserDao uDao;
	
	@Autowired
	private ExamOrderDao eod;
	
//	public FTrade getTradeByid(String tradeNo){
//		return sdao.getTradeByid(tradeNo);
//	}
//	
	public List<LStrMap<Object>> getSubscribeGood(){
		return sdao.getSubscribeGood("20130827162334569127");
	}
	
//	public boolean updateTradeStatus(int status,String tradeNo){
//		return sdao.updateTradeStatus(tradeNo, status)==1;
//	}
//	
	public boolean saveSubscribe(LSubscribe subscribe){
		return sdao.saveSubscribe(subscribe) == 1;
	}
//	
//	public boolean updateSubscribe(LSubscribe subscribe){
//		return sdao.updateSubscribe(subscribe) == 1;
//	}
//	
	public List<LStrMap<Object>> getCashByUserId(long user_id){
		return eod.getCashByuserIdDao(user_id);
	}
	
	public int saveTradeLog(UStrMap<Object> logMap) {
		return eod.saveTradeLogDao(logMap);
	}
	
	public LSubscribe getSubscribeByUser(long userId){
		return sdao.getSucscribeByUser(userId);
	}
	public boolean hasSubscribe(long userId){
		LSubscribe subscribe = sdao.getSucscribeByUser(userId);
		if(subscribe == null) return false;
		if(new Date().compareTo(subscribe.getEndDate()) > 0) return false;
		return true;
	}
	public void buySuccess(String tradeNo){
		FTrade trade = sdao.getTradeByid(tradeNo);
		if(trade.getTradeStatus() == FTrade.TRADESUCCESS)return;
		String recommendUser = trade.getRemark();
		FGoodModel fgm = sdao.getGoodModelByTrade(tradeNo);
		LSubscribe subscribe = sdao.getSucscribeByUser(trade.getUserId());
		Date now = new Date();
		if(subscribe == null){ //如为初次购买
			subscribe = new LSubscribe();
			subscribe.setUserId(trade.getUserId());
			subscribe.setStartDate(now);
			subscribe.setEndDate(DateHelper.add(now, Calendar.MONTH, Integer.parseInt(fgm.getRemark())));
			subscribe.setRemark("初次购买");
			sdao.saveSubscribe(subscribe);
		}else{
			Date endDate = subscribe.getEndDate();
			if(endDate.compareTo(now)>0){
				endDate = DateHelper.add(endDate, Calendar.MONTH, Integer.parseInt(fgm.getRemark()));
			}else{
				endDate = DateHelper.add(now, Calendar.MONTH, Integer.parseInt(fgm.getRemark()));
			}
			subscribe.setStartDate(now);
			subscribe.setEndDate(endDate);
			subscribe.setRemark("续费");
			sdao.updateSubscribe(subscribe);
		}
		sdao.updateTradeStatus(tradeNo,FTrade.TRADESUCCESS);
		
		//插入交易日志表
		UStrMap<Object> logMap = UStrMap.newInstance();
		String tradeLogId = SequenceFactory.getTradeLogSequence();
		logMap.put("TRADE_LOG_ID", tradeLogId);
		logMap.put("TRADE_ID", tradeNo);
		logMap.put("CASH", trade.getMoney());
		logMap.put("COUPON", 0);
		logMap.put("USER_ID", trade.getUserId());
		logMap.put("REMARK", "");
		logMap.put("TRADE_TYPE", 1);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		logMap.put("TRADE_TIME",dateformat.format(calendar.getTime()));
		List<LStrMap<Object>> cashList = getCashByUserId(trade.getUserId());
		Object cash = cashList.get(0).get("CASH");
		logMap.put("CASH_BEFORE", cash);
		logMap.put("COUPON_BEFORE", 0);
		logMap.put("CASH_AFTER", cash);
		logMap.put("COUPON_AFTER", 0);
		saveTradeLog(logMap);
		
		
		// 当使用推荐码的交易成功，则插入推荐人的交易记录
		if (recommendUser!=null && !recommendUser.equals("")) {
			String recommendTradeId = SequenceFactory.getTradeSequence();
			trade.setTradeId(recommendTradeId);
			trade.setUserId(Long.parseLong(recommendUser));
			trade.setTradeType(FTrade.INCOME);
			trade.setTradeTime(DateHelper.formatYMDHMS(new Date()));
			String reward = String.format("%.2f", (Math.round((trade.getMoney() * (float)0.1)*100)/100.0));
			trade.setMoney(Float.parseFloat(reward));
			trade.setTradeStatus(FTrade.TRADESUCCESS);
			trade.setRemark(null);
			trade.setModelId(FTrade.RECHARGEID);
			trade.setGoodsId(String.valueOf(FTrade.RECHARGEID));
			trade.setTradeName("手机被用作推荐码的奖励");
			eod.saveTradeRecordNoDao(trade);
			long recommendUserId = Long.parseLong(recommendUser);
			BigDecimal recommendCash = (BigDecimal)eod.getCashByuserIdDao(recommendUserId).get(0).get("cash");
			float cashAfter = Float.parseFloat(reward)+recommendCash.floatValue();
			uDao.updateUserAccount(recommendUserId, cashAfter);
			
			//插入交易日志表
			String recommendTradeLogId = SequenceFactory.getTradeLogSequence();
			logMap.put("TRADE_LOG_ID", recommendTradeLogId);
			logMap.put("TRADE_ID", recommendTradeId);
			logMap.put("CASH", trade.getMoney());
			logMap.put("COUPON", 0);
			logMap.put("USER_ID", recommendUser);
			logMap.put("REMARK", "");
			logMap.put("TRADE_TYPE", 2);
			Calendar recommendCalendar = Calendar.getInstance();
			logMap.put("TRADE_TIME",dateformat.format(recommendCalendar.getTime()));
			logMap.put("CASH_BEFORE", recommendCash);
			logMap.put("COUPON_BEFORE", 0);
			logMap.put("CASH_AFTER", cashAfter);
			logMap.put("COUPON_AFTER", 0);
			saveTradeLog(logMap);
		}
	}
	
	public void buyError(String tradeNo){
		FTrade trade = sdao.getTradeByid(tradeNo);
		if(trade.getTradeStatus() == FTrade.TRADESUCCESS)return;
		
		//插入交易日志表
		UStrMap<Object> logMap = UStrMap.newInstance();
		String tradeLogId = SequenceFactory.getTradeLogSequence();
		logMap.put("TRADE_LOG_ID", tradeLogId);
		logMap.put("TRADE_ID", tradeNo);
		logMap.put("CASH", trade.getMoney());
		logMap.put("COUPON", 0);
		logMap.put("USER_ID", trade.getUserId());
		logMap.put("REMARK", "");
		logMap.put("TRADE_TYPE", 1);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		logMap.put("TRADE_TIME", dateformat.format(calendar.getTime()));
		List<LStrMap<Object>> cashList = getCashByUserId(trade.getUserId());
		Object cash = cashList.get(0).get("CASH");
		logMap.put("CASH_BEFORE", cash);
		logMap.put("COUPON_BEFORE", 0);
		logMap.put("CASH_AFTER", cash);
		logMap.put("COUPON_AFTER", 0);
		saveTradeLog(logMap);
		sdao.updateTradeStatus(tradeNo,FTrade.TRADEFAILD);
	}
	
	/**
	 * 判断推荐是否正确
	 * @param phonenumber
	 * @return
	 */
	public List<LStrMap<Object>> isPhoneNumTrue(String phonenumber) {
		List<LStrMap<Object>> list = sdao.getTeacherPhone(phonenumber);
		return list;
	}
	
	public int getTradeStatus(String tradeNo){
		return sdao.getTradeByid(tradeNo).getTradeStatus();
	}
}
