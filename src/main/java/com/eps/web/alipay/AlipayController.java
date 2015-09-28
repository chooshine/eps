package com.eps.web.alipay;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.service.alipay.AlipayNotify;
import com.eps.service.alipay.AlipayService;

@Controller
public class AlipayController {
	
	@Autowired
	private AlipayService service;
	
	@RequestMapping(value="/alipay.html")
	public String toAlipay(HttpServletRequest request, HttpServletResponse response, AlipayParams params, ModelMap mm){
		params.setExter_invoke_ip(request.getRemoteAddr());
		String htmlText = service.createPayUrl(request,params);
		mm.put("htmlText", htmlText);
		return "/alipay/submitForm";
	}
	
	@RequestMapping(value="/alipay/return.html")
	public String payResult(HttpServletRequest request,ModelMap mm){
		Enumeration<String> keys =  request.getParameterNames();
		Map<String,String> params = new HashMap<String, String>();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			params.put(key, request.getParameter(key));
		}
		//System.out.println(params);
		boolean isAliPayReturn = AlipayNotify.verify(params);
		if(isAliPayReturn){
			String trade_status = params.get("trade_status");
			if("TRADE_SUCCESS".equals(trade_status)){//付款成功
				mm.put("tradeStatus", true);
			}else{
				mm.put("tradeStatus", false);
			}
		}else{
			mm.put("tradeStatus", false);
		}
		mm.put("out_trade_no", params.get("out_trade_no"));//本系统对应的唯一订单编号
		mm.put("body", params.get("body"));          //购买商品描述
		mm.put("subject", params.get("subject"));    //购买商品名称
		mm.put("total_fee", params.get("total_fee")); //商品总价
		String redierctUrl = params.get("extra_common_param").split("\\;")[0];
		//mm.put("buyerEmail", params.get("buyer_email"));//购买者的支付宝帐号
		return "redirect:" + redierctUrl;
	}
	
	@RequestMapping(value="/alipay/notify.html")
	public String payNotify(HttpServletRequest request,ModelMap mm){
		Enumeration<String> keys =  request.getParameterNames();
		Map<String,String> params = new HashMap<String, String>();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			params.put(key, request.getParameter(key));
		}
		//System.out.println(params);
		boolean isAliPayReturn = AlipayNotify.verify(params);
		if(isAliPayReturn){
			String trade_status = params.get("trade_status");
			if("TRADE_SUCCESS".equals(trade_status)){//付款成功
				mm.put("tradeStatus", true);
			}else{
				mm.put("tradeStatus", false);
			}
		}else{
			mm.put("tradeStatus", false);
		}
		mm.put("out_trade_no", params.get("out_trade_no"));//本系统对应的唯一订单编号
		mm.put("body", params.get("body"));          //购买商品描述
		mm.put("subject", params.get("subject"));    //购买商品名称
		mm.put("total_fee", params.get("total_fee")); //商品总价
		//mm.put("buyerEmail", params.get("buyer_email"));//购买者的支付宝帐号
		String redierctUrl = params.get("extra_common_param").split("\\;")[0];
		return "redirect:" + redierctUrl;
	}
	
	@RequestMapping(value="/alipay/error.html")
	public String payError(HttpServletRequest request,ModelMap mm){
		Enumeration<String> keys =  request.getParameterNames();
		Map<String,String> params = new HashMap<String, String>();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			params.put(key, request.getParameter(key));
		}
		//System.out.println(params);
		boolean isAliPayReturn = AlipayNotify.verify(params);
		mm.put("tradeStatus", false);
		String redierctUrl = params.get("extra_common_param").split("\\;")[1];
		return "redirect:" + redierctUrl;
	}
}
