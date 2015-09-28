package com.eps.domain.ques;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eps.utils.FileUploader;
import com.eps.utils.HttpHelper;
import com.eps.utils.StringHelper;
import com.eps.utils.UStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Content {
	
	public final static String TYPE_IMG = "IMG";
	public final static String TYPE_TEXT = "TEXT";
	
	private String type;
	private String data;
	public static final String IMGPATTERN = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
	public Content(){}
	public Content(String type,String data){
		this.type = type;
		this.data = data;
	}
	/**
	 * 解析内容，将内容里的图片和文字进行分片
	 * 将图片列表存入key=IMG的键中, 内容列表存入key=TEXT的键中
	 * @param content
	 * @return
	 */
	public static UStrMap<Object> parseContent(String content){
		content = content.replaceAll("data-latex=\"[^\"]*+\"", "");//去除图片中的latex表达式
		Pattern p = Pattern.compile(IMGPATTERN);
		Matcher m = p.matcher(content);
		while(m.find()){
			String img = m.group();
			//在图片的前后加上 | 
			content = content.replaceAll(img, "|"+img+"|");
		}
		//去除多余的  |
		content = content.replaceAll("\\|{2,}", "|");
		//去除开头和结尾的 |
		if(content.startsWith("|"))content = content.substring(1);
		if(content.endsWith("|"))content = content.substring(0, content.length()-1);
		//将内容分片，得到图片和非图片内容
		String[] contents = content.split("\\|");
		Map<String,Image> images =new HashMap<String, Image>();
		List<Content> list = new ArrayList<Content>();
		for (String string : contents) {
			Matcher mm = p.matcher(string);
			if(mm.find()){//是图片
				String src = mm.group(1);
				String key = "";
				Image image = new Image();
				image.setPath(src);
				if(images.containsValue(image)){
					List<Image> list1 = new ArrayList<Image>(images.values());
					Image old = list1.get(list1.indexOf(image));
					key = old.getKey();
				}else{
					key = FileUploader.DEFAULT_FILE_NAME_GENERATOR.generate(null, "");
					image.setKey(key);
					images.put(key, image);
				}
				list.add(new Content.Img(key));
			}else{//是文本
				list.add(new Content.Text(StringHelper.removeNPSB(StringHelper.removeHtml(string))));
			}
		}
		UStrMap<Object> result = UStrMap.newInstance();
		result.put(TYPE_IMG, images);
		result.put(TYPE_TEXT, list);
		return result;
	}
	
	public static class Img extends Content{
		public Img(String data){
			super(TYPE_IMG,data);
		}
	}
	
	public static class Text extends Content{
		public Text(String data){
			super(TYPE_TEXT,data);
		}
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public static void main(String[] args) {
		String reg = "\\*/health + /M00\\*";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher("http://192.168.11.80/health/M00/00/AA/wKgLUFU3hgCAAxThAAGwWyZygWk593.png");
		if(m.find()){
			System.out.println(m.group());
			System.out.println(m.group(1));
		}
	}
}
