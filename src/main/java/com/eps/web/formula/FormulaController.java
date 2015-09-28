package com.eps.web.formula;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.android.web.Base64Util;
import com.eps.utils.FileUploader;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;
import com.eps.utils.HttpHelper;
import com.eps.utils.ImageUtil;
import com.eps.domain.Formula;
import com.eps.service.formula.FormulaService;
import com.eps.web.BaseController;

@Controller
public class FormulaController extends BaseController {
	@Autowired private FormulaService fs;
	//private static Random NEXTSERIALNUM = new Random(47);
	private static FileNameGenerator NAMEGENERATOR = new FileUploader.CommonFileNameGenerator();
	
	Logger logger = LoggerFactory.getLogger(FormulaController.class);
	@RequestMapping(value="/kityformula.html")
	public String toKity() {
		
		return "kityformula";
	}
	
	@RequestMapping(value="/homework/teacher/test.html")
	public String toFormulaTest() {
		
		return "homework/teacher/test";
	}
	
	@RequestMapping(value="/ueditor/kityformula-plugin/kityFormulaDialog.html")
	public String kityFormula() {
		
		return "/kityFormulaDialog";
	}
	
	//上传公式图片
	@RequestMapping(value="/uploadFormula.json", method=RequestMethod.POST)
	public String uploadNoteImg(String imgData, String latex, HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
		long userId = getSessionUser(request).getUserId();
		
		//保存当前公式到服务器和数据库中
		Formula formula = new Formula();
		imgData = imgData.substring(22);
		//判断是否已有与当前日期对应的文件夹，如果有，就把录音存到该文件夹，如果没有，就新建一个文件夹
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		String foldPath = "/images/formula/"+year+(month>9?month:"0"+month)+(date>9?date:"0"+date);
		File fold = new File(HttpHelper.getRequestRealPath(request, foldPath));
		if (!fold.exists()) {
			fold.mkdir();
		}
		String fileName = foldPath+"/"+NAMEGENERATOR.generate(null, "")+".png";
		//对图片进行裁剪
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64Util.imageStr2Byte(imgData)));
			int w = image.getWidth();
			int h = image.getHeight();
			int woff = 20;// 公式图片左右留白为20像素
			int hoff = 10;// 公式图片上下留白为10你素
			//把图片周围留白去掉
			image = ImageUtil.crop(image,woff, hoff, w-2 * woff, h - 2 * hoff);
			//图片写入服务器
			ImageIO.write(image, "png", new File(HttpHelper.getRequestRealPath(request, "")+fileName));
			formula.setCount(1);
			formula.setCreateTime(new Date());
			formula.setHeight(h - 2 * hoff);
			formula.setLatex(latex);
			formula.setSize((int)new File(request.getSession().getServletContext().getRealPath("")+fileName).length()/1024);
			formula.setUrl(fileName.replace('\\', '/'));
			formula.setUserId(userId);
			formula.setWidth(w-2 * woff);
			fs.saveFormula(formula);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mm.put("path", fileName.replace('\\', '/'));
		return "jsonView";
	}
	@RequestMapping(value="/test/imageCrop.html")
	public String jCrop(){
		return "ImageCrop";
	}
	
	@RequestMapping(value="/test/upload.json")
	public String uploadImage(HttpServletRequest request, HttpServletResponse response,ModelMap mm){
		FileUploader uploader = new FileUploader("/temp");
		uploader.setAcceptTypes(new String[]{"jpg","png","jpeg","gif"});
		uploader.setFileSizeMax(1024*500);//最大500KB
		Result result = uploader.upload(request, response);
		if(result == Result.SUCCESS){
			String fileName = uploader.getFileNames().iterator().next();
			String path = uploader.getSavePath();
			mm.put("success", true);
			mm.put("img", path + "/" + fileName);
		}else{
			mm.put("success", false);
			mm.put("message",result.getMessage());
		}
		return "jsonView";
	}
	
	@RequestMapping(value="/test/uploadImg.html", method=RequestMethod.POST)
	public void uploadImg(HttpServletRequest request, HttpServletResponse response, ModelMap mm) {
		FileUploader uploader = new FileUploader("/temp");
		uploader.setAcceptTypes(new String[]{"jpg","png","jpeg","gif"});
		uploader.setFileSizeMax(1024*1024*2);//最大2M
		Result result = uploader.upload(request, response);
		if(result == Result.SUCCESS){
			String fileName = uploader.getFileNames().iterator().next();
			String path = uploader.getSavePath()+"/"+fileName;
			try {
				HttpHelper.writeString(response, "<script>parent.afterChangeImg(\""+path.substring(1)+"\");</script>", "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(result == Result.FILE_SIZE_EXCEEDED){
			String errorMsg = "文件大小不能大于2M！";
			try {
				HttpHelper.writeString(response, "<script>parent.showErrorMsg(\""+errorMsg+"\");</script>", "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("文件大于2M");
			}
		}
		
	}

	@RequestMapping(value="/test/rotating/{theta}.json")
	public String rotatingImage(@PathVariable("theta") int theta, String path,HttpServletRequest request,ModelMap mm){
		try {
			String readPath = HttpHelper.getRequestRealPath(request, path);
			String writePath  = "/temp/"+NAMEGENERATOR.generate(null, "")+".png";
			ImageUtil.rotate(readPath, 
							theta,
							HttpHelper.getRequestRealPath(request, writePath),"png");
			File file = new File(readPath);
			file.delete();
			mm.put("img", writePath.substring(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "jsonView";
	}
	
	/**
	 * 剪裁图片
	 * @param path 图片在服务器中的路径，该路径是相对路径，相对于应用程序根目录
	 * @param savePath 要将剪裁后的图片保存到服务器的文件夹的路径，该路径也是相对路径，相对于应用程序根目录
	 * @param x 剪裁的x坐标
	 * @param y 剪裁的y坐标
	 * @param w 剪裁的宽度
	 * @param h 剪裁的高度
	 * @param scaleRefer 比例的参照，为width或height
	 * @param scaleLength 比例参照的长度
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/test/crop.json")
	public String cropImage(String path, String savePath, int x,int y, double w, double h, String scaleRefer, int scaleLength, HttpServletRequest request, ModelMap mm){
		try {
			String writePath = savePath+"/"+path.substring(path.lastIndexOf("/")+1);
			
			//如果没有指定的文件夹，则要新建一个文件夹
			String foldPath = HttpHelper.getRequestRealPath(request, savePath);
			File file = new File(foldPath);
			if (!file.exists()) {
				file.mkdir();
			}
			
			path = HttpHelper.getRequestRealPath(request, path);
			String newWritePath = HttpHelper.getRequestRealPath(request, writePath);
			ImageUtil.crop(path, x,y,(int)w,(int)h, newWritePath,"png", scaleRefer, scaleLength);
			mm.put("path", writePath.substring(1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "jsonView";
	}
	
	//查询公式了历史
	@RequestMapping(value="/formulaHistory.html")
	public String getFormulaHistory(HttpServletRequest request, ModelMap mm) {
		mm.put("formulaList", fs.getFormulas(getSessionUser(request).getUserId()));
		return "formula/formulaList";
	}
	
	//更新公式使用次数
	@RequestMapping(value="/updateFormuluCount.json", method=RequestMethod.POST)
	public String updateFormuluCount(HttpServletRequest request, int id, ModelMap mm) {
		fs.updateFormulaCount(getSessionUser(request).getUserId(), id);
		return "jsonView";
	}
	

	//上传摄像头图片
	@RequestMapping(value="/uploadCameraImg.json", method=RequestMethod.POST)
	public String uploadCameraImg(String imgData/*, String imgPath*/,HttpServletRequest request, ModelMap mm) {
		/*String foldPath = HttpHelper.getRequestRealPath(request, imgPath);
		File file = new File(foldPath);
		if (!file.exists()) {
			file.mkdir();
		}*/
		String fileName = getSessionUser(request).getUserId()+"-"+System.currentTimeMillis()+".png";
		String path = "/temp/"+fileName;
		Base64Util.generateImage(imgData, HttpHelper.getRequestRealPath(request, path));
		mm.put("imgPath", path.substring(1));
//		String path = foldPath+"\\"+fileName;
//		Base64Util.generateImage(imgData, path);
//		mm.put("imgPath", (imgPath+"/"+fileName).substring(1));
		return "jsonView";
	}
}
