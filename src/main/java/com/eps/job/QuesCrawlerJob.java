package com.eps.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.eps.service.crawl.ZhujuanCrawler;
import com.eps.service.crawl.bean.Task;

public class QuesCrawlerJob {
	
	@Autowired
	private ZhujuanCrawler service;
	
	public void process(){
		service.zhujuanLogin();//登陆
		List<Task> tasks = service.getTask(); //查询抓取任务
		for (Task task : tasks) {
			service.searchQues(task);
		}
	}
	public void downloadImage(){
		service.download();
	}
	public void downloadAnswerAndParse(){
		service.zhujuanLogin();//登陆
		service.downloadAnswerAndParseImg();
	}
}
