package com.eps.job;


import org.springframework.beans.factory.annotation.Autowired;

import com.eps.dao.crawl.CrawlDao;
import com.eps.service.crawl.Crawler;

public class CrawlerJob {
	
	@Autowired
	private CrawlDao dao;
	
	@Autowired
	private Crawler crawler;
	
	public static boolean isRun = false;
	public void crawlerNews(){
		if(!isRun){
			CrawlerJob.isRun = true;
			try {
				crawler.crawl();
			} catch (Exception e) {
				// TODO: handle exception
			} finally{
				CrawlerJob.isRun = false;
			}
			CrawlerJob.isRun = false;
		}
	}
}
