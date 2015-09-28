package com.eps.web.corpus;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.domain.FTrade;
import com.eps.domain.LSubscribe;
import com.eps.service.corpus.SubscribeService;
import com.eps.service.esextends.ExamOrderService;
import com.eps.utils.DateHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.SequenceFactory;
import com.eps.utils.UStrMap;
import com.eps.web.BaseController;

@Controller
public class SubscribeController extends BaseController{
	
	@Autowired
	private SubscribeService service;
	@Autowired
	private ExamOrderService oservice;
	
	@RequestMapping(value="/corpus/subscribe.html")
	public String toSubscribe(HttpServletRequest request,ModelMap mm){
		mm.put("title", "语料订购");
		mm.put("goods", service.getSubscribeGood());
		LSubscribe subscribe = service.getSubscribeByUser(getSessionUser(request).getUserId());
		if(subscribe == null){
			mm.put("flag",true);
		}else if(subscribe.getEndDate().compareTo(new Date())> 0){
			mm.put("expired", "true");
		}
		return "corpus/subscribe/subscribe";
	}
	@RequestMapping(value="/corpus/receive.html")
	public String receiveSubscribe(ModelMap mm,HttpServletRequest request){
		mm.put("title", "语料订购");
		LSubscribe subscribe = service.getSubscribeByUser(getSessionUser(request).getUserId());
		mm.put("goods", service.getSubscribeGood());
		if(subscribe != null){
			mm.put("expired", "true");
			mm.put(ERROR_MSG_KEY, "您已经领取过，不能再次领取！");
			return "corpus/subscribe/subscribe";
		}
		subscribe = new LSubscribe();
		Date now = new Date();
		subscribe.setUserId(getSessionUser(request).getUserId());
		subscribe.setStartDate(now);
		subscribe.setEndDate(DateHelper.add(now, Calendar.DAY_OF_MONTH, 7));
		subscribe.setRemark("免费使用7天");
		service.saveSubscribe(subscribe);
		mm.put("subs", true);
		return "corpus/subscribe/subscribe";
	}
	
	/**
	 * 判断输入的推荐码是否正确,0代表推荐码不正确，1代表用户用的自己的手机号，2代表推荐码正确
	 * @param recommendcode
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/corpus/confimOrder.json")
	public String isPhoneTrue(HttpServletRequest requst,ModelMap mm){
		String recommendcode = requst.getParameter("recommendcode");
		String userPhone = this.getSessionUser(requst).getPhone();
		if (recommendcode.equals(userPhone)) {
			mm.put("differnum", 1);
		} else {
			List<LStrMap<Object>> list = service.isPhoneNumTrue(recommendcode);
			if (list.size() == 0) {
				mm.put("differnum", 0);
			} else {
				long recommendUser = (Integer)list.get(0).get("user_id");
				mm.put("differnum", 2);
				mm.put("recommendUser", recommendUser);
			}
		}
		
		return "jsonView";
	}
	
	
	@RequestMapping(value="/corpus/confimOrder.html")
	public String enterOrder(String total,String date,String modelId,String goodsId,String recommendUser,ModelMap mm,HttpServletRequest request){
		mm.put("title", "语料订购");
		String total_fee = String.format("%.2f", Math.round(Double.parseDouble(total)*100)/100.0);
		mm.put("total_fee", total_fee);
		mm.put("body", date);
		mm.put("subject", date + "个月的语料搜索服务");
		String tradeId = SequenceFactory.getTradeSequence();
		mm.put("tradeNo", tradeId);
		
		long userId = getSessionUser(request).getUserId();
		String tradeTime = DateHelper.formatYMDHMS(new Date());
		
		FTrade trade = new FTrade();
		trade.setTradeId(tradeId);
		trade.setUserId(userId);
		trade.setGoodsId(goodsId);
		trade.setModelId(Integer.parseInt(modelId));
		trade.setMoney(Float.parseFloat(total_fee));
		trade.setTradeStatus(FTrade.TRADEDING);
		trade.setTradeType(FTrade.PAY);
		trade.setRemark(recommendUser);
		trade.setTradeTime(tradeTime);
		trade.setTradeName(date + "个月的语料搜索服务");
		oservice.saveTradeRecordService(trade);
		
		//插入交易日志表
		UStrMap<Object> logMap=UStrMap.newInstance();
		String tradeLogId = SequenceFactory.getTradeLogSequence();
		logMap.put("TRADE_LOG_ID", tradeLogId);
		logMap.put("TRADE_ID", tradeId);
		logMap.put("CASH", Float.parseFloat(total_fee));
		logMap.put("COUPON", 0);
		logMap.put("USER_ID", userId);
		logMap.put("REMARK", "");
		logMap.put("TRADE_TYPE", 1);
		logMap.put("TRADE_TIME", tradeTime);
		
		List<LStrMap<Object>> cashList = service.getCashByUserId(userId);
		Object cash = cashList.get(0).get("CASH");
		logMap.put("CASH_BEFORE", cash);
		logMap.put("COUPON_BEFORE", 0);
		logMap.put("CASH_AFTER", cash);
		logMap.put("COUPON_AFTER", 0);
		service.saveTradeLog(logMap);
		return "corpus/subscribe/confimOrder";
	}
	
	@RequestMapping(value="/corpus/payOrder.html")
	public String payOrder(String total_fee,String subject,String body,String tradeNo,ModelMap mm){
		mm.put("title", "语料订购");
		mm.put("total_fee", total_fee);
		mm.put("subject", subject);
		mm.put("tradeNo", tradeNo);
		mm.put("body", body);
		return "corpus/subscribe/confimOrder";
	}
	
	@RequestMapping(value="/corpus/paying.html")
	public String payWait(String total_fee,String subject,String body,String tradeNo,String recommendTradeNo,ModelMap mm){
		mm.put("title", "正在付款");
		mm.put("total_fee", total_fee);
		mm.put("subject", subject);//new String(subject.getBytes("iso-8859-1"),"utf-8"));
		mm.put("body", body);
		mm.put("tradeNo", tradeNo);
		mm.put("recommendTradeNo", recommendTradeNo);
		return "corpus/subscribe/payWait";
	}
	@RequestMapping(value="/corpus/pay.html")
	public String pay(HttpServletRequest request,String total_fee,String subject,String body,String tradeNo,ModelMap mm){
		mm.put("total_fee", total_fee);
		mm.put("body", body);
		mm.put("subject", subject);
		mm.put("redirect_success", "/corpus/paySuccess.html");
		mm.put("redirect_error", "/corpus/payError.html");
		mm.put("out_trade_no", tradeNo);
		return "redirect:/alipay.html";
	}
	
	@RequestMapping(value="/corpus/paySuccess.html")
	public String paySuccess(String out_trade_no,String body,String subject,String total_fee, ModelMap mm){
		mm.put("title", "付款成功");
		mm.put("tradeNo", out_trade_no);
		mm.put("body",body);
		mm.put("subject", subject);
		mm.put("total_fee", total_fee);
		service.buySuccess(out_trade_no);
		return "corpus/subscribe/paySuccess";
	}
	
	@RequestMapping(value="/corpus/payError.html")
	public String payError(String out_trade_no,String body,String subject,String total_fee,ModelMap mm){
		mm.put("title", "付款失败");
		mm.put("tradeNo", out_trade_no);
		mm.put("body",body);
		mm.put("subject", subject);
		mm.put("total_fee", total_fee);
		service.buyError(out_trade_no);
		return "corpus/subscribe/payError";
	}
	
	@RequestMapping(value="/corpus/isError.html")
	public String toChooseErrorPage(String tradeNo,String body,String subject,String total_fee,ModelMap mm){
		mm.put("tradeNo", tradeNo);
		mm.put("body",body);
		mm.put("subject", subject);
		mm.put("total_fee", total_fee);
		//得到该笔交易的交易状态，判断是否交易成功
		int status = service.getTradeStatus(tradeNo);
		if (status == 2) {
			mm.put("title", "付款成功");
			return "corpus/subscribe/paySuccess";
		} 
		mm.put("title", "付款失败");
		return "corpus/subscribe/payError";
	}
}
