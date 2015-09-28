package com.eps.service.actives;

import org.apache.commons.fileupload.FileItem;

import com.eps.utils.FileUploader.FileNameGenerator;

public class DrawMoneyFileNameGenerator implements FileNameGenerator {

	private String url;
	
	public DrawMoneyFileNameGenerator(){
		
	}
	
	public DrawMoneyFileNameGenerator(String url){
		this.url=url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url){
		this.url = url;
	}
	public String generate(FileItem item, String suffix) {
		return url;
	}

}
