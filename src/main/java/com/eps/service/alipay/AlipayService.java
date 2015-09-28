package com.eps.service.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.eps.service.alipay.config.AlipayConfig;
import com.eps.service.alipay.sign.MD5;
import com.eps.web.alipay.AlipayParams;

@Service
public class AlipayService {
		
		public String createPayUrl(HttpServletRequest request,AlipayParams params){
//			System.out.println(request.getServerName());
//			System.out.println(request.getServerPort());
//			System.out.println(request.getHeader("Host"));
			String server = request.getServerName();
			if("localhost".equals(server)){
				server = "127.0.0.1";//如为调试将服务器地址改为IP
			}
			int port = request.getServerPort();
			String applicationPath = "http://" + server + ":"+ port + request.getContextPath();
			if(80 == port){
				applicationPath = "http://" + server + request.getContextPath();
			}
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", AlipayConfig.service);
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", AlipayConfig.payment_type);
			sParaTemp.put("notify_url", applicationPath + AlipayConfig.notify_url);
			sParaTemp.put("return_url", applicationPath + AlipayConfig.return_url);
			sParaTemp.put("error_notify_url", applicationPath + AlipayConfig.error_notify_url);
			sParaTemp.put("seller_email", AlipayConfig.seller_email);
			sParaTemp.put("out_trade_no", params.getOut_trade_no());
			sParaTemp.put("subject", params.getSubject());
			sParaTemp.put("total_fee", String.valueOf(params.getTotal_fee()));
			sParaTemp.put("body", params.getBody());
			sParaTemp.put("show_url", params.getShow_url());
			sParaTemp.put("extra_common_param", params.getRedirect_success()+";"+params.getRedirect_error());
			//sParaTemp.put("anti_phishing_key",);
			sParaTemp.put("exter_invoke_ip", params.getExter_invoke_ip());
			//过滤掉空数据及签名参数
			sParaTemp = AlipayCore.paraFilter(sParaTemp);
			//添加签名参数
			sParaTemp.put("sign", buildRequestMysign(sParaTemp));
			sParaTemp.put("sign_type", AlipayConfig.sign_type);
			System.out.println(sParaTemp);
			String htmlText = buildRequestForm(sParaTemp, "GET");
			return htmlText;
		}
		
		/**
	     * 生成签名结果
	     * @param sPara 要签名的数组
	     * @return 签名结果字符串
	     */
		private String buildRequestMysign(Map<String, String> sPara) {
	    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	        String mysign = "";
	        if(AlipayConfig.sign_type.equals("MD5") ) {
	        	mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
	        }
	        return mysign;
	    }
		
		/**
		 * 构建提交支付宝的form表单
		 * @param sPara 参数列表
		 * @param strMethod  提交方式 GET POST
		 * @return
		 */
		public String buildRequestForm(Map<String, String> sPara,String strMethod){
			List<String> keys = new ArrayList<String>(sPara.keySet());

	        StringBuffer sbHtml = new StringBuffer();

	        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + AlipayConfig.ALIPAY_GATEWAY_NEW
	                      + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod
	                      + "\">");

	        for (int i = 0; i < keys.size(); i++) {
	            String name = (String) keys.get(i);
	            String value = (String) sPara.get(name);

	            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
	        }

	        //submit按钮控件请不要含有name属性
	        sbHtml.append("<input type=\"submit\" value=\"提交\" style=\"display:none;\"></form>");
	        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

	        return sbHtml.toString();
		}
}
