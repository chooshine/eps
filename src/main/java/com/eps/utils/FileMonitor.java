package com.eps.utils;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMonitor {
	Logger log = LoggerFactory.getLogger(FileMonitor.class);
	
	private static final FileMonitor instance = new FileMonitor();
//	private Timer timer;
	private ScheduledThreadPoolExecutor executor;
	private Map<String,Object> timerEntries;
	private FileMonitor(){
		this.timerEntries = new HashMap<String,Object>();
		executor = new ScheduledThreadPoolExecutor(1);
//		this.timer = new Timer(true);
	}
	
	public static FileMonitor getInstance() {
		return instance;
	}
	/**
	 * 添加文件监听
	 * @param listener 监听器
	 * @param filename 文件路径全名
	 * @param period 监听间隔时间(毫秒)
	 */
	public void addFileChangeListener(FileChangeListener listener, 
			String filename, long period) {
		File file = new File(filename);
		addFileChangeListener(listener, file, period);
	}
	/**
	 * 添加文件监听
	 * @param listener 监听器
	 * @param file 文件
	 * @param period 监听间隔时间(毫秒)
	 */
	public void addFileChangeListener(FileChangeListener listener, 
			File file, long period){
		String filename = file.getPath();
		this.removeFileChangeListener(filename);
		
		log.info("Watching " + filename);
//			File file = new File(filename);
		FileMonitorTask task = new FileMonitorTask(listener, file);
//		log.info(task.toString());
//		this.timerEntries.put(filename, task);
		this.timerEntries.put(filename, executor.scheduleAtFixedRate(task, period, period, TimeUnit.MILLISECONDS));
//		this.timer.schedule(task, period, period);
	}
	/**
	 * 移除文件监听
	 * @param filename 文件名
	 */
	public void removeFileChangeListener(String filename) {
		Object task = this.timerEntries.remove(filename);
		if (task != null) {
			boolean b = executor.remove((Runnable) task);
//			log.info(task.toString());
//			boolean b = executor.remove(task);
			log.info("remove [{}] listener[{}]",filename,b);
		}
	}
	private static class FileMonitorTask implements Runnable {
		private FileChangeListener listener;
		private String filename;
		private File monitoredFile;
		private long lastModified;
		public FileMonitorTask(FileChangeListener listener, File file) {
			this.listener = listener;
			this.filename = file.getPath();
//			URL url = FileMonitor.class.getClassLoader().getResource(filename);
			this.monitoredFile = file ;//FileUtils.toFile(url);;
			if (!this.monitoredFile.exists()) {
				return;
			}
			
			this.lastModified = this.monitoredFile.lastModified();
		}
		
		public void run() {
			long latestChange = this.monitoredFile.lastModified();
			if (this.lastModified != latestChange) {
				this.lastModified = latestChange;
				
				this.listener.fileChanged(this.filename);
			}
		}

	}
	
	public static void main(String[] args) {
		URL url = FileMonitor.class.getClassLoader().getResource("system.properties");
		
		File file = FileUtils.toFile(url);
		FileMonitor.getInstance().addFileChangeListener(new FileChangeListener() {
			
			public void fileChanged(String filename) {
				System.out.println("文件发生变化");
			}
		}, file, 50l);
		
		try {
			TimeUnit.MILLISECONDS.sleep(3000l);
			FileMonitor.getInstance().removeFileChangeListener(file.getPath());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
