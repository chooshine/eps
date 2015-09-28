package com.eps.service.weixin;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eps.utils.HttpHelper;
import com.eps.utils.HttpRespons;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 微信参数
 * @author hejunwei
 *
 */
public class Contains {
	static Logger log = LoggerFactory.getLogger(Contains.class);
	public final static String APPID = "wx483c983bba115dd6";
	public final static String APPSECRET = "0a40b7ff04142d5cf7ea3f8a081c2600";
	/**
	 * 获取access_token接口地址，请求方式:GET
	 */
	public final static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
	
	/**
	 * Access_Token有效期7200秒
	 */
	public volatile static String ACCESS_TOKEN = "iZMmVwR-AZYx45Hiipt_Q8sfAd_NBZ4lzvn1m-ZQudNXsmOCPnk3E2S4Gp475nhm0nPXk3CVTGZeSkORxxHQAQ";
	/**
	 * 发送信息接口地址，请求方式:POST
	 */
	public final static String SENDURL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+ACCESS_TOKEN;
	/**
	 * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
	 */
	public final static String SIGNATURE = "signature";
	
	/**
	 * 时间戳
	 */
	public final static String TIMESTAMP = "timestamp";
	
	/**
	 * 随机数
	 */
	public final static String NONCE = "nonce";
	
	/**
	 * 随机字符串
	 */
	public final static String ECHOSTR = "echostr";
	/**
	 * 信息类型 事件
	 */
	public final static String MSGTYPE_EVENT="event";
	/**
	 * 信息类型 文本
	 */
	public final static String MSGTYPE_TEXT = "text";
	/**
	 * 信息类型 图片
	 */
	public final static String MSGTYPE_IMAGE = "image";
	/**
	 * 事件类型 菜单事件
	 */
	public final static String EVENT_TYPE_CLICK = "CLICK";
	
	/**
	 * 事件类型 订阅事件
	 */
	public final static String EVENT_TYPE_SUB = "subscribe";
	/**
	 * 事件类型 取消订阅
	 */
	public final static String EVENT_TYPE_UNSUB = "unsubscribe";
	
	
	public final static String EVENT_CLICK_HW_KEY = "BTN01_QUERY_HOMEWORK";
	/**
	 * 发起access_token请求，修改access_token
	 */
	public static void getAccessToken(){
		try {
			HttpRespons response = HttpHelper.sendGet(TOKENURL);
			String content = response.getContent();
			log.info("请求AccessToken成功，返回内容:[{}]",content);
			Gson gson = new GsonBuilder().create();
			AccessToken token = gson.fromJson(content, AccessToken.class);
			if(token.getToken()!=null)ACCESS_TOKEN = token.getToken();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private class AccessToken{
		String token;
		String expires;
		String errCode;
		String errMsg;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getExpires() {
			return expires;
		}
		public void setExpires(String expires) {
			this.expires = expires;
		}
		public String getErrCode() {
			return errCode;
		}
		public void setErrCode(String errCode) {
			this.errCode = errCode;
		}
		public String getErrMsg() {
			return errMsg;
		}
		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
		
	}
	public static void main(String[] args) {
//		getAccessToken();
//		{"button":[{"name":"查询作业","type":"click","key":"BTN01_QUERY_HOMEWORK"}]}
//		String createBtn = "";
	}
}
