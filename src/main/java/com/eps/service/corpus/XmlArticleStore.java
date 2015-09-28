package com.eps.service.corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.eps.dao.Page;
import com.eps.domain.Article;
import com.eps.utils.UStrMap;
import com.eps.web.corpus.SearchParamComm;

@Service
public class XmlArticleStore {
	
	@Value("${search.cache.dir}")
	private String path;
	
	public void writeHeader(String fileName){
		File filePath = new File(path);
		if (!filePath.exists())
			filePath.mkdirs();
		FileOutputStream fo = null;
		OutputStreamWriter fw = null;
		try {
			File file = new File(path + File.separator + fileName);
			fo = new FileOutputStream(file);
			fw = new OutputStreamWriter(fo, "UTF-8");
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ROOT>");
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
				try {
					if(fw!=null)fw.close();
					if(fo!=null)fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public void writeBottom(String fileName){
		File filePath = new File(path);
		if (!filePath.exists())
			filePath.mkdirs();
		FileOutputStream fo = null;
		OutputStreamWriter fw = null;
		try {
			File file = new File(path + File.separator + fileName);
			fo = new FileOutputStream(file,true);
			fw = new OutputStreamWriter(fo, "UTF-8");
			fw.append("</ROOT>");
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
				try {
					if(fw!=null)fw.close();
					if(fo!=null)fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	/**
	 * 写入缓存文件
	 * @param data
	 * @param fileName
	 */
	public void writeBuffFile(List<Article> data, String fileName){
		File filePath = new File(path);
		if (!filePath.exists())
			filePath.mkdirs();
		FileOutputStream fo = null;
		OutputStreamWriter fw = null;
		try {
			File file = new File(path + File.separator + fileName);
			fo = new FileOutputStream(file,true);
			fw = new OutputStreamWriter(fo, "UTF-8");
			for(Article article:data){
				List<Map<String,String>> contents = article.getContent();
				for (Map<String,String> item:contents) {
					 //content = (String) iterator.next();
					Set<String> keys = item.keySet();
					String mainKey = "";
					for(String key:keys){
						mainKey = key;
					}
					fw.append("<ROW>");
					fw.append("<AUTHOR>").append(article.getAuthorName()).append("</AUTHOR>");
					fw.append("<AUTHOR_ID>").append(String.valueOf(article.getAuthorId())).append("</AUTHOR_ID>");
					fw.append("<CONTENT>").append(item.get(mainKey)).append("</CONTENT>");
					fw.append("<ARTICLE_NAME>").append(article.getArticleName()).append("</ARTICLE_NAME>");
					fw.append("<PUBLICATION_DATE>").append(article.getPublisherDate()).append("</PUBLICATION_DATE>");
					fw.append("<MAINKEY>").append(mainKey).append("</MAINKEY>");
					fw.append("</ROW>\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
				try {
					if(fw!=null)fw.close();
					if(fo!=null)fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 从缓存文件读取数据
	 * @param comm
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	public Page readBuffFile(SearchParamComm comm,String fileName,int pageNo,int pageSize) throws IOException{
		ParseArticleXmlHandler handler = new ParseArticleXmlHandler(Page.getStartOfPage(pageNo, pageSize), pageSize, comm);
		parseXML(handler,fileName);
		Page page = new Page(handler.getStart(), handler.getTotalCount(), pageSize, handler.getResult());
		return page;
	}
	
	public List<UStrMap<String>> getExcelData(SearchParamComm comm,String fileName,HSSFWorkbook book) throws IOException{
		ExportParseArticleXmlHandler handler = new ExportParseArticleXmlHandler(comm, book);
		parseXML(handler,fileName);
		return handler.getResult();
	}
	
	private void parseXML(DefaultHandler handler,String fileName) throws IOException{
		XMLReader reader = null;
		//FileInputStream fs = null;
		Reader br = null;
//		InputStreamReader fr = null;
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
//			fs = new FileInputStream(path + File.separator + fileName);
//			fr = new FileReader(new File(path + File.separator + fileName));
//			fr = new InputStreamReader(new FileInputStream(path + File.separator + fileName), "UTF-8");
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(path + File.separator + fileName),
						    "UTF-8"));
			
			reader.parse(new InputSource(br));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally{
				try {
					if(br!=null)br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
