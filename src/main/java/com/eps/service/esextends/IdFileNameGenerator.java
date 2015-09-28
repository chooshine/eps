package com.eps.service.esextends;

import org.apache.commons.fileupload.FileItem;

import com.eps.utils.FileUploader.FileNameGenerator;

public class IdFileNameGenerator implements FileNameGenerator {
	
	//用户编号 
	private long userId;
	
	
	public IdFileNameGenerator(){
		
	}
	
	public IdFileNameGenerator(long userId){
		this.userId=userId;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}



	public String generate(FileItem item, String suffix) {
		return userId+"";
	}

}
