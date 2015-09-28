package com.eps.android.web.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Decoder;

import com.eps.android.service.user.AUserService;
import com.eps.android.web.Base64Util;
import com.eps.android.web.Files;
import com.eps.domain.Message;
import com.eps.service.message.LeaveMessageService;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
@Controller
public class AUserController extends BaseController {

	@Autowired private AUserService aUserService;
	@Autowired private LeaveMessageService lms;
	
	//查看我的信息
	@RequestMapping(value="/android/myInfo.json")
	public String myInfo(int uid, ModelMap mm) {
		try {
			mm.put("DATA", aUserService.getUserInfo(uid));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put("DATA", null);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//上传反馈图片
	@RequestMapping(value="/android/uploadAdviceImg.json")
	public String uploadAdviceImg(String imgData, String imgName, HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
		List<LStrMap<Object>> list = new ArrayList<LStrMap<Object>>();
		LStrMap<Object> map = LStrMap.newInstance();
		try {
			String fileName = new Date().getTime()+imgName+".jpg";
			if(!Base64Util.generateImage(imgData, request.getSession().getServletContext().getRealPath("")+"\\images\\advice\\"+fileName)) {
				map.put("msg", "系统出错");
				mm.put("DATA", map);
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
			} else {
				map.put("msg", "上传成功");
				map.put("path", "/images/advice/"+fileName);
				mm.put("DATA", map);
				mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "系统出错");
			mm.put("DATA", map);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//问题反馈
	@RequestMapping(value="/android/advice.json")
	public String advice(int uid, HttpServletRequest request, ModelMap mm) {
		try {
			String text = request.getParameter("text")==null?"":request.getParameter("text");
			text = URLDecoder.decode(text, "UTF-8");
			//base64解码笔记图片
			//String imgs = new String(new BASE64Decoder().decodeBuffer(request.getParameter("imgs")==null?"":request.getParameter("imgs")));
			String imgs = URLDecoder.decode(request.getParameter("imgs")==null?"":request.getParameter("imgs"), "UTF-8");
			String content = text+imgs;
			
			Message message = new Message();
			String message_ip = request.getRemoteAddr();
			
			//获得当前时间
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message_date = sdf.format(date);
			
			message.setMessageUserId(uid);
			message.setMessageContent(content);
			message.setMessageIp(message_ip);
			message.setMessageDate(message_date);
			
			lms.submitMessage(message);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		
		return "jsonView";
	}
	
	//apk版本检查
	@RequestMapping(value="/android/checkVersion.json")
	private String checkVersion(HttpServletRequest request, ModelMap mm) {
		try {
			String rootPath = request.getRealPath("");
			File file = new File(rootPath+"\\apk.properties");
			InputStream in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			mm.put("RESULT", properties.getProperty("version")+"&"+properties.getProperty("name")+"&"+properties.getProperty("path"));
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		return "jsonView";
	}
	
	//apk下载
	@RequestMapping(value="/android/downLoadApk.json")
	private String downLoadApk(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
		try {
			String rootPath = request.getRealPath("");
			File file = new File(rootPath+"\\apk.properties");
			InputStream in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			String location = rootPath+"/apks/"+properties.getProperty("filename");
			Files.downFile(response, request, location);
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), true);
		} catch (Exception e) {
			e.printStackTrace();
			mm.put(SUCCESS_MSG_KEY.toUpperCase(), false);
		}
		return "jsonView";
	}
}
