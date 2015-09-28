package com.eps.web.weixin;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.service.weixin.Contains;
import com.eps.service.weixin.IMessageProcess;
import com.eps.service.weixin.WeixinMessage;
import com.eps.service.weixin.WeixinService;
import com.eps.service.weixin.WeixinThread;
import com.eps.utils.HttpHelper;
import com.eps.web.BaseController;

@Controller
public class WeiXincontroller extends BaseController{
	private final static String TOKEN = "7CA8E96DA61A877E68F158B8726E9983";
	
	Logger log = LoggerFactory.getLogger(WeiXincontroller.class);
	
//	@Autowired
//	private WeixinService service;
	@RequestMapping("/weixin.html")
	public void weixinInterface(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("接收微信信息");
		List<String> names = HttpHelper.getParamNames(request);
		log.info("请求参数列表[{}]",names);
		Map<String,String> params = new HashMap<String,String>();
		for (String name : names) {
			String value = HttpHelper.getParam(request, name);
			params.put(name, value);
			log.info("[{}={}]",name,value);
		}
		if(checkRequest(params.get(Contains.SIGNATURE),params.get(Contains.TIMESTAMP),params.get(Contains.NONCE))){
			log.info("请求验证通过");
			String xmlStr = HttpHelper.readString(request, true, "UTF-8");
			log.debug("xml内容:[{}]",xmlStr);
			WeixinMessage message = WeixinService.parseXml(xmlStr);
			IMessageProcess process = WeixinService.createProcess(message.getMsgType());
			WeixinMessage res = process.createResponseMsg(message);
			String send = res.createTextXmlMessage();
			log.info("回复内容:[{}]",send);
			HttpHelper.writeString(response, send, "UTF-8");
			//带有此参数是验证请求
//			if(names.contains(Contains.ECHOSTR)){
//				try {
//					HttpHelper.writeString(response, params.get(Contains.ECHOSTR),"UTF-8");
//				} catch (IOException e) {
//					log.info("程序异常",e);
//				}
//			}else{
//				HttpHelper.writeString(response, "","UTF-8");
//			}
			
//			//对请求的内容进行异步处理
//			WeixinThread thread = new WeixinThread(xmlStr);
//			service.execute(thread);
		}
	}
	
	/**
	 * 将TOKEN与时间戳随机数进行字典排序后sha加密与signature一致则验证通过
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	private boolean checkRequest(String signature,String timestamp, String nonce){
		String[] strs = new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(strs);
		String str = strs[0]+strs[1]+strs[2];
		str = DigestUtils.sha1Hex(str);
		if(signature.equals(str))return true;
		return false;
	}
}
