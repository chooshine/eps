package com.eps.web.esextends;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.domain.CTeacher;
import com.eps.domain.User;
import com.eps.service.esextends.TeacherAuthenService;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.service.user.UserService;
import com.eps.utils.CheckIdentityAndPhone;
import com.eps.utils.HttpHelper;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class TeacherAuthenController extends BaseController{

	@Autowired
	private ExamAfterService eas;
	
	@Autowired
	private TeacherAuthenService tas;
	
	@Autowired
	private UserService us;
	
	private final static String PHONECODE = "phonecode";
	
	/**
	 * 认证说明页面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/authenExplain.html")
	public String initAuthenExplain(HttpServletRequest request, ModelMap mm){
		mm.put("title","教师认证");
		return "examsystem/teacherauthen/authenExplain";
	}
	
	/**
	 * 谁资料填写页面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/inputAuthenInfo.html")
	public String initAutherInfo(ModelMap mm,HttpServletRequest request){
		User user=getSessionUser(request);
		mm.put("user_no", user.getUserNo());
		mm.put("sortList",eas.getAllSortNoAndNameService());
		mm.put("title","教师认证");
		List<LStrMap<Object>> list = tas.getAuthenInfo(user.getUserId());
		Gson gson = new GsonBuilder().create();
		if (!list.isEmpty()) {
			LStrMap<Object> map = list.get(0);
			mm.put("authenInfo", map);
			String teach = (String)map.get("teach");
			String[] teaches = teach.split(",");
			mm.put("teach", gson.toJson(teaches));
		} else {
			mm.put("teach", gson.toJson("false"));
		}
		
		return "examsystem/teacherauthen/inputAuthenInfo";
	}
	
	/**
	 * 认证等待页面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/authenAudit.html")
	public String initAuthenAudit(ModelMap mm,HttpServletRequest request,CTeacher cTeacher){
		User user=getSessionUser(request);
		cTeacher.setUserId(user.getUserId());
		int k=0;
		StringBuffer sbf=new StringBuffer();
		while (request.getParameter("teach"+k)!=null) {
			sbf.append(request.getParameter("teach"+k)+",");
			k++;
		}
		cTeacher.setTeach(new String(sbf));
		tas.deleteTeacher(user.getUserId());
		tas.saveTeacherService(cTeacher);
		
		String contactPhone = cTeacher.getContactPhone();
		if (contactPhone != null) {
			user.setPhone(contactPhone);
		}
		us.updateUserDetailInfo(user);

		mm.put("title","教师认证");
		return "examsystem/teacherauthen/authenAudit";
	}
	
	/**
	 * 上传头像
	 * @param mm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/upPhoto/inputAuthenInfo.html",method=RequestMethod.POST)
	public void upImage(ModelMap mm,HttpServletRequest request,HttpServletResponse response){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		String photoInfo=tas.upPhotoService(userId, request, response).toString();
		try {
			HttpHelper.writeString(response, "<script>parent.setPhotoTt(\""+URLEncoder.encode(photoInfo,"UTF-8")+"\");</script>", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传证书
	 * @param mm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/upCer/inputAuthenInfo.html",method=RequestMethod.POST)
	public void upCertificate(ModelMap mm,HttpServletRequest request,HttpServletResponse response){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		Map<String,String> map=tas.upCertificateService(userId, request, response);
		try {
			HttpHelper.writeString(response, "<script>parent.setCerTt(\""+URLEncoder.encode(map.get("errorInfo"),"UTF-8")+"\",\""+map.get("path")+"\");</script>", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 认证时，相关信息的验证
	 * @param mm
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/check/autheninfo.json")
	public String cheakAuthenInfo(ModelMap mm,HttpServletRequest request){
		String type=request.getParameter("type");
		if("identityid".equals(type)){
			String IDStr=request.getParameter("ckValue");
			try {
				String resultStr=CheckIdentityAndPhone.IDCardValidate(IDStr);
				mm.put("result", resultStr);
			} catch (Exception e) {
				mm.put("result", "身份号码不正确！");
				e.printStackTrace();
			}
		}
		return "jsonview";
	}
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/test.html")
	public String fileUpload(HttpServletRequest request,ModelMap mm){
		int x=0,y=0,width=10,height=10;
		if(request.getParameter("x")!=null){
			 x=Integer.parseInt(request.getParameter("x"));
			 y=Integer.parseInt(request.getParameter("y"));
			 width=Integer.parseInt(request.getParameter("width"));
			 height=Integer.parseInt(request.getParameter("height"));
		}
		try {
			/*
			 * 从文件中读取 图片
			 * 创建ImageInputStream对象，图片输入流
			 * 返回一个ImageReader对象，用于从文件中读取流
			 * 设置输入源
			 * 定义一个区域(相对于源图片)
			 * 得到一个ImageReadParam对象，调用setSourceRegion方法，按照rect的区域获得图像
			 * 通过ImageRader对象的read方法，像图像读取到BufferedImage中
			 * 写入到指定文件中
			 */
			FileInputStream fis=new FileInputStream(request.getSession().getServletContext().getRealPath("images/headImage/default_head.jpg"));
			ImageInputStream iis=ImageIO.createImageInputStream(fis);
			
			Iterator<ImageReader> iterator=ImageIO.getImageReaders(iis);
			while (!iterator.hasNext()) {
				return null;
			}
			ImageReader reader=iterator.next();
			
			reader.setInput(iis, true,true);
			
			Rectangle rect=new Rectangle(x, y, width, height);
			
			ImageReadParam param=reader.getDefaultReadParam();
			
			param.setSourceRegion(rect);
			
			BufferedImage bi=reader.read(0, param);
			
			ImageIO.write(bi, "jpg",new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("images/certificate/3.jpg").toString())));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "examsystem/teacherauthen/test";	
	}
	
	@RequestMapping(value="/inputAuthenInfo/bindPhone.json")
	public String toBindPhone(HttpServletRequest request, ModelMap mm, String phone){
		User user = this.getSessionUser(request);
		String code = us.sendPhoneCode(user, phone);
		request.getSession().setAttribute(PHONECODE, code);
		return "jsonView";
	}
	
	@RequestMapping(value="/inputAuthenInfo/checkCheckCode.json")
	public String checkCheckCode(HttpServletRequest request, ModelMap mm, String checkcode){
		String realCode = (String) request.getSession().getAttribute(PHONECODE);
		if (checkcode.equals(realCode)) {
			mm.put("success", 1);
		} else {
			mm.put("success", 0);
		}		
		
		return "jsonView";
	}
	
	
	@RequestMapping(value="/authenBack.html")
	public String authenBack(HttpServletRequest request, ModelMap mm, String status){
		if (status == null) {
			status = "-1";
		}
		List<LStrMap<Object>> list = tas.getAuthenInfoByStatus(Integer.parseInt(status));
		mm.put("authenInfos", list);
		return "examsystem/teacherauthen/teacherAuthenBack";
	}
	
	@RequestMapping(value="/investigate.json")
	public String investigageAuthen(String teacher_id, String status, HttpServletRequest request, ModelMap mm){
		User user = this.getSessionUser(request);
		String mailAddress = user.getMailAddress();
		if (status.equals("1")) {
			us.sendAuthenResult(user, mailAddress, "success");
			tas.updateAuthenStatus(Long.parseLong(teacher_id),status);
			mm.put("result", "已认证");
			return "jsonView";
		} else {
			us.sendAuthenResult(user, mailAddress, "failed");
			tas.updateAuthenStatus(Long.parseLong(teacher_id),status);
			mm.put("result", "认证失败");
			return "jsonView";
		}
		
		
	}
	
}
