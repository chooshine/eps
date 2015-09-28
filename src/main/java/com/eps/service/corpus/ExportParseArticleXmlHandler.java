package com.eps.service.corpus;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.xml.sax.SAXException;

import com.eps.cons.CommonConstant;
import com.eps.utils.UStrMap;
import com.eps.web.corpus.SearchParamComm;

public class ExportParseArticleXmlHandler extends ParseArticleXmlHandler {
	
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	int r=0;
	public ExportParseArticleXmlHandler(SearchParamComm params,HSSFWorkbook book){
		super(0,Integer.MAX_VALUE,params);
		workbook = book;
	}
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (SearchUtils.ROW.equals(localName) && item != null) {
			if (validItem()) {
				UStrMap<String> map = UStrMap.newInstance();
				String content = (item.get(SearchUtils.CONTENT).replaceAll("\\s{2,}|\t|\r|\n|[\u3000]", ""));
				String [] contents = content.split(CommonConstant.KEYCHAR);
				if(contents.length==2){
					map.put("left", contents[0]);
					map.put("right", contents[1]);
				}else {
					if(content.indexOf(CommonConstant.KEYCHAR)==0){
						map.put("left", "");
						map.put("right", contents[0]);
					}else{
						map.put("left", contents[0]);
						map.put("right", "");
					}
				}
				map.put("key", item.get(SearchUtils.MAINKEY));
				map.put("AUTHOR", item.get(SearchUtils.AUTHOR));
				map.put("ARTICLENAME", item.get(SearchUtils.ARTICLE_NAME));
				map.put("ddate", item.get(SearchUtils.PUBLICATION_DATE));
				result.add(map);
//				if(totalCount%60000==0){
//					r = 0;
//					sheet = workbook.createSheet();
//					sheet.setColumnWidth(0, 50*256);
//					sheet.setColumnWidth(1, 100*256);
//					createHeader(sheet, r);
//					r++;
//				}
//				createRow(sheet,r++);
				totalCount++;
			}
		}
	}
	private void createRow(HSSFSheet s,int r){
		HSSFRow row = s.createRow(r);
		String content = item.get(SearchUtils.CONTENT);
		content = content.replaceAll(CommonConstant.KEYCHAR, item.get(SearchUtils.MAINKEY));
		int c=0;
		HSSFCell c2 = row.createCell(c++);
		//c2.setCellStyle(getContentStyle());
		c2.setCellValue(new HSSFRichTextString(item.get(SearchUtils.ARTICLE_NAME)));
		HSSFCell c5 = row.createCell(c++);
		//c5.setCellStyle(getContentStyle());
		c5.setCellValue(new HSSFRichTextString(content));
		HSSFCell c1 = row.createCell(c++);
		//c1.setCellStyle(getContentStyle());
		c1.setCellValue(new HSSFRichTextString(item.get(SearchUtils.AUTHOR)));
		HSSFCell c3 = row.createCell(c++);
		//c3.setCellStyle(getContentStyle());
		c3.setCellValue(new HSSFRichTextString(item.get(SearchUtils.PUBLICATION_DATE)));
	}
	private void createHeader(HSSFSheet sheet,int r){
		HSSFRow row = sheet.createRow(r);
		int c = 0;
		row.setHeightInPoints(35f);
		HSSFCell c2 = row.createCell(c++);
		c2.setCellStyle(getColumnStyle());
		c2.setCellValue(new HSSFRichTextString("出处"));
		HSSFCell c5 = row.createCell(c++);
		c5.setCellStyle(getColumnStyle());
		c5.setCellValue(new HSSFRichTextString("内容"));
		HSSFCell c1 = row.createCell(c++);
		c1.setCellStyle(getColumnStyle());
		c1.setCellValue(new HSSFRichTextString("作者"));
		HSSFCell c3 = row.createCell(c++);
		c3.setCellStyle(getColumnStyle());
		c3.setCellValue(new HSSFRichTextString("发表时间"));
	}
	private HSSFCellStyle getColumnStyle(){
		HSSFCellStyle style = workbook.createCellStyle();
		//居中对齐
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER );   
	    
	    style.setFillForegroundColor((short) 55);// 设置背景色  
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	   
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框 
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框   
	    // 创建字体
	    HSSFFont ff = workbook.createFont();
	    // ff.setColor(HSSFFont.COLOR_RED);//  字体颜色
	    ff.setFontHeightInPoints((short) 12);// 字体大小
	    //ff.setFontName("Arial");// 设置字体
	    ff.setBoldweight((short) 700);
	    ff.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
	    style.setFont(ff);//放入样式中
		return style;
	}
	private HSSFCellStyle getContentStyle(){
		HSSFCellStyle style = workbook.createCellStyle();
		//contextCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("Arial");
        contentFont.setFontHeightInPoints((short)10);
        style.setFont(contentFont); 
		return style;
	}
//	public static void main(String[] args) {
//		String content ="　@#@維新の会幹事長の松井一郎大阪府知事は１０日、２０１４年度税制改正をめぐり、政府、与党が国家戦略特区の法人税率の引き下げを見送る方針を固めたことについて「『アベノミクス』３本目の矢（である成長戦略）は、よれよれになってしまう」と批判した。　同時に「あとは安倍晋三首相の決断に期待する」と述べ、首相が".replaceAll("\\s{2,}|\t|\r|\n|[\u3000]", "");
//		System.out.println(content.indexOf(CommonConstant.KEYCHAR));
//	}
}
