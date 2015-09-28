package com.eps.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.eps.service.corpus.CorpusService;

public class ComplementArticle {
	
	@Autowired
	private CorpusService service;
	
	public void excute(){
		service.complementAllArticle();
	}
}
