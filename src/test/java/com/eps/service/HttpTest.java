package com.eps.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.eps.utils.HttpHelper;
import com.eps.utils.HttpRespons;

public class HttpTest {
	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("123456"));
//		try {
//			questype:0
//			quesdiff:0
//			curpage:2
//			pagesize:10
//			quesyear:0
//			Map<String,String> params = new HashMap<String,String>();
//			params.put("bankid", "1");
//			params.put("categoryid", "1");
//			params.put("questype", "0");
//			params.put("quesdiff", "0");
//			params.put("curpage", "1");
//			params.put("pagesize", "50");
//			params.put("quesyear", "0");
//			Map<String,String> properties = new HashMap<String, String>();
//			properties.put("Content-Type", "application/x-www-form-urlencoded");
//			properties.put("Cookie", "Hm_lvt_055abb981359f6563b8efed767b802e2=1421913203,1421914027; ASP.NET_SessionId=e3doy355xugj1c45j23s1jyw; UserFilter=8a114110-b8af-41b9-97af-109196cb6609; pro_bank=7%2425; SectionLastSet=; userId=9865920; userName=zjhz4z; allbankCount=2342778%2c3120639");
//			HttpRespons res = HttpHelper.sendPost("http://www.zujuan.com/Web/Handler1.ashx?action=queslistquery",params,properties);
//			System.out.println(res.getContent());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
