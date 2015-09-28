package com.eps.service.sms;

import java.rmi.RemoteException;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.eps.service.sms.client.Service1Soap;
import com.eps.service.sms.client.Service1SoapProxy;

@Service
public class SMSService {
	private Service1Soap smsService;// = new Service1SoapProxy();
	private final static String USERID = "chooshine";
	private final static String PASSWORD = "chooshine999";
	
	private void init(){
		synchronized (Service1Soap.class) {
			if(smsService==null)smsService = new Service1SoapProxy();
		}
	}
	public boolean sendMessage(String[] nums, String content){
		if(nums.length <1){
			return false;
		}
		String phoneNums = ArrayUtils.toString(nums);
		phoneNums = phoneNums.substring(1, phoneNums.length()-1);
		try {
			init();
			String result = smsService.sendMessages(USERID, PASSWORD, phoneNums, content, "");
			System.out.println(result);
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean sendMessage(String phone, String content){
		try {
			init();
			String result = smsService.sendMessages(USERID, PASSWORD, phone, content, "");
			System.out.println(result);
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
//	public static void main(String[] args) {
//		SMSService service =new SMSService();
//		service.sendMessage(new String[]{"18767186581"}, "尊敬的hejunwei您好，感谢您注册畅先网，您的用户名：hejunwei。");
//	}
}
