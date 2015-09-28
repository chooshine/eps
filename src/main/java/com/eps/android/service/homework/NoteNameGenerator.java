package com.eps.android.service.homework;

import java.util.Date;

import org.apache.commons.fileupload.FileItem;

import com.eps.utils.FileUploader.FileNameGenerator;

public class NoteNameGenerator implements FileNameGenerator {

	public NoteNameGenerator(){
		
	}
	
	public String generate(FileItem item, String suffix) {
		return new Date().getTime()+"";
	}

}
