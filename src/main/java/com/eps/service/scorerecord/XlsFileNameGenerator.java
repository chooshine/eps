package com.eps.service.scorerecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.fileupload.FileItem;

import com.eps.utils.FileUploader.FileNameGenerator;

public class XlsFileNameGenerator implements FileNameGenerator {

	private long userId;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public XlsFileNameGenerator(long userId) {
		this.userId = userId;
	}
	public String generate(FileItem item, String suffix) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return this.userId+"_"+formatter.format(calendar.getTime());
	}

}
