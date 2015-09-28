package com.eps.domain.ques;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.eps.android.web.Base64Util;

public class Image {
	private String key;
	private int width;
	private int height;
	private String base64 = "";
	private String path = "";
	private String suffix ="";
	public Image(){}
	
	public Image(String key,String path){
		this.key = key;
		this.path = path;
		
	}
	
	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof Image){
			Image other = (Image) obj;
			if(!StringUtils.isBlank(this.path) && !StringUtils.isBlank(other.path))
				return this.path.equals(other.path);
			return false;
		}
		return false;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
		//setBase64(getImageBase64(path));
	}

	private String getImageBase64(String path) {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = Object.class.getResourceAsStream("/system.properties");
			properties.load(in);//获取图片文件的存放路径根目录
			String rootPath = properties.getProperty("image.rootpath");
			return Base64Util.GetImageStr(rootPath+path.replaceFirst(".*/images/", "images/"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		Image image = new Image();
		image.setPath("/images/formula/20150410/000001_1428658216208.jpg");
		System.out.println(image.getBase64());
		Base64Util.generateImage(image.getBase64(), "D:/test.jpg");
	}
	
}
