package com.eps.web.wav;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.android.web.Base64Util;
import com.eps.utils.FileUploader;
import com.eps.utils.WavUtil;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;
import com.eps.utils.HttpHelper;
import com.eps.web.BaseController;

@Controller
public class WavController extends BaseController{
	private Logger log = LoggerFactory.getLogger(WavController.class);
	private static FileNameGenerator NAMEGENERATOR = new FileUploader.CommonFileNameGenerator();
	@RequestMapping(value="/wav/upload.json", method=RequestMethod.POST)
	public String upload(HttpServletRequest request,HttpServletResponse response,ModelMap mm){
		//判断是否已有与当前日期对应的文件夹，如果有，就把录音存到该文件夹，如果没有，就新建一个文件夹
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		String foldPath = "/wav/"+year+(month>9?month:"0"+month)+(date>9?date:"0"+date);
		File fold = new File(HttpHelper.getRequestRealPath(request, foldPath));
		if (!fold.exists()) {
			fold.mkdir();
		}
		FileUploader uploader = new FileUploader(foldPath,new String[]{".wav"});
		
		Result result = uploader.upload(request, response);
		Map<String,String[]> param = uploader.getParamFields();
		if(Result.SUCCESS == result){
			mm.put("saved", 1);
			String fileName = uploader.getFileNames().iterator().next();
			log.info("上传音频文件成功,文件名[{}]",fileName);
			mm.put("fileName", fileName);
			
		}else{
			log.error("上传音频失败!",uploader.getCause());
			mm.put("saved", 0);
		}
		mm.put("authenticity_token", param.get("authenticity_token")[0]);
//		try {
//			//HttpHelper.writeString(response, "<script>parent.saveAfterHandler('"+mm.get("saved")+"','"+mm.get("authenticity_token")+"','"+mm.get("fileName")+"');</script>", "UTF-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return "jsonView";
	}
	@RequestMapping(value="/wav/uploadBase64.json", method=RequestMethod.POST)
	public String uploadBase64(HttpServletRequest request,HttpServletResponse response,ModelMap mm){
		String fileName = NAMEGENERATOR.generate(null, "");
		String mp3 = fileName + ".mp3";
		String wav = fileName + ".wav";
		String wavData = "";
		try {
			wavData = HttpHelper.readString(request, true, "UTF-8");
		//	System.out.println(wavData);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileOutputStream fo = null;
		
		try {
		//判断是否已有与当前日期对应的文件夹，如果有，就把录音存到该文件夹，如果没有，就新建一个文件夹
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			int date = calendar.get(Calendar.DAY_OF_MONTH);
			String foldPath = "/wav/"+year+(month>9?month:"0"+month)+(date>9?date:"0"+date);
			String wavPath = HttpHelper.getRequestRealPath(request, foldPath) + "/"+wav;
			String mp3Path = HttpHelper.getRequestRealPath(request, foldPath) + "/"+mp3;
			File fold = new File(HttpHelper.getRequestRealPath(request, foldPath));
			if (!fold.exists()) {
				fold.mkdirs();
			}
			fo = new FileOutputStream(wavPath);
			fo.write(Base64Util.imageStr2Byte(wavData));
			fo.close();
			//wav转成mp3
			WavUtil.wavToMp3(wavPath, mp3Path);
			new File(wavPath).delete();
//			fo = new FileOutputStream(HttpHelper.getRequestRealPath(request, "/wav") + "/"+mp3);
//			fo.write(Base64Util.imageStr2Byte(wavData));
			mm.put("path", foldPath + "/" + fileName+".mp3");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fo!=null)
				try {
					fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		//mm.put("path", "/wav/" + fileName);
		return "jsonView";
	}
	@RequestMapping(value="/wav/test.html")
	public String test(){
		return "wavtest";
	}
	
	//删除录音
	@RequestMapping(value="/wav/deleteWav.json")
	public String deleteWav(String path, HttpServletRequest request){
		//String[] arr = path.split("/");
		String mp3Ppath = HttpHelper.getRequestRealPath(request, path);
	    File mp3 = new File(mp3Ppath);
	    // 路径为文件且不为空则进行删除
	    if(mp3.isFile() && mp3.exists()) {
	    	mp3.delete();
	    }
		return "jsonView";
	}
}
