package com.eps.service.esextends;

import org.apache.commons.fileupload.FileItem;

import com.eps.utils.FileUploader.FileNameGenerator;

public class CertificateNameGenerator implements FileNameGenerator{
	private static int k=0;
	private long userId;
	
	public CertificateNameGenerator(){
		
	}
	
	public CertificateNameGenerator(long userId){
		this.userId=userId;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String generate(FileItem item, String suffix) {
		k++;
		return userId+"_"+k;
	}

}
