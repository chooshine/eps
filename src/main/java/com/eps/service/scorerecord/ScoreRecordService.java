package com.eps.service.scorerecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.scorerecord.Layouter;
import com.eps.dao.scorerecord.ScoreRecordDao;
import com.eps.dao.scorerecord.Writer;
import com.eps.utils.FileUploader;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.utils.FileUploader.FileNameGenerator;
import com.eps.utils.FileUploader.Result;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class ScoreRecordService {

	@Autowired
	private ScoreRecordDao scoreRecordDao;
	
	
	public void createTest(){
		UStrMap<Object> map = UStrMap.newInstance();
		scoreRecordDao.createTest(map);
	}
	
	public void createExam(){
		UStrMap<Object> map = UStrMap.newInstance();
		scoreRecordDao.createExam(map);
	}
	
	public List<LStrMap<Object>> getClasses(long user_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", user_id);
		return scoreRecordDao.getClasses(map);
	}
	
	public List<LStrMap<Object>> getSorts(long user_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", user_id);
		return scoreRecordDao.getSorts(map);
	}
	
	public List<LStrMap<Object>> getClosedTest(String class_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("class_id", Integer.parseInt(class_id));
		return scoreRecordDao.getClosedTest(map);
	}
	
	public LStrMap<Object> getExam(String test_id, String subject_no){
		if(test_id==null || subject_no==null) {
			return null;
		}
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("test_id", Integer.parseInt(test_id));
		map.put("subject_no", Integer.parseInt(subject_no));
		List<LStrMap<Object>> list = scoreRecordDao.getExam(map);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public List<LStrMap<Object>> getAllGradeByUserId(long userId){
		return scoreRecordDao.getAllGradeByUserId(userId);
	}
	
	public Map<String, List<LStrMap<Object>>> getAllClass(long user_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("user_id", user_id);
		List<LStrMap<Object>> classes = scoreRecordDao.getAllClass(map);
		//得到该老师所在学校的id
		int schoolId = (Integer)classes.get(0).get("school_id");
		LStrMap<Object> schoolMap = LStrMap.newInstance();
		schoolMap.put("school_id",schoolId);
		List<LStrMap<Object>> schooList = new ArrayList<LStrMap<Object>>();
		schooList.add(schoolMap);
		
		Map<String,List<LStrMap<Object>>> result = new LinkedHashMap<String, List<LStrMap<Object>>>();
		result.put("schoolId", schooList);
		for (LStrMap<Object> lStrMap : classes) {
			String grade_name = String.valueOf(lStrMap.get("grade_name"));
			if (!result.containsKey(grade_name)) {
				result.put(grade_name, new ArrayList<LStrMap<Object>>());
			} 
			result.get(grade_name).add(lStrMap);
		}
		
		return result;
	}

	public LStrMap<Object> getGradeSchoolByClassId(String class_id) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("class_id", Integer.parseInt(class_id));
		List<LStrMap<Object>> list = scoreRecordDao.getGradeSchoolByClassId(map);
		return list.get(0);
	}
	
	public List<LStrMap<Object>> getAllStudentAndDownload(String classId, String testId, String subjectId){
		return scoreRecordDao.getAllStudentByClassId(Long.parseLong(classId), Long.parseLong(testId), Long.parseLong(subjectId));
	}
	
	/**
     * 使用传进的数据源填充表格
     * @param worksheet
     * @param startRowIndex
     * @param startColIndex
     * @param datasource
     */
    public static void fillReport(HSSFSheet worksheet,int startRowIndex, int startColIndex,List<LStrMap<Object>> datasource){ 
        
        startRowIndex += 2; 
        
        //设置单元格样式  
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();   
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);   
        bodyCellStyle.setWrapText(false); 							//是否自动换行
        bodyCellStyle.setBorderBottom(CellStyle.BORDER_THIN); 
        bodyCellStyle.setBorderLeft(CellStyle.BORDER_THIN); 
        bodyCellStyle.setBorderRight(CellStyle.BORDER_THIN); 
        bodyCellStyle.setBorderTop(CellStyle.BORDER_THIN); 
        
        //填充单元格
        for (int i=startRowIndex; i+startRowIndex-2< datasource.size()+2; i++) {   
            //创建行
            HSSFRow row = worksheet.createRow((short) i+1);   
  
            //检索学号的值  
            HSSFCell cell1 = row.createCell(startColIndex+0);   	//创建单元格
            cell1.setCellValue((String)datasource.get(i-2).get("student_no"));   
            cell1.setCellStyle(bodyCellStyle);   
  
            //检索姓名  
            HSSFCell cell2 = row.createCell(startColIndex+1);   
            cell2.setCellValue((String)datasource.get(i-2).get("student_name"));   
            cell2.setCellStyle(bodyCellStyle);   
            
            //得分
            HSSFCell cell3 = row.createCell(startColIndex+2);
            cell3.setCellValue((Double)datasource.get(i-2).get("score"));
            cell3.setCellStyle(bodyCellStyle);  
            
        }

    } 
	
	/**
	 * 读取数据库并导出报表 
	 * @param request
	 * @param response
	 */
    public void exportXLS(HttpServletRequest request, HttpServletResponse response) {

        // 1.创建一个 workbook 
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2.创建一个 worksheet 
        HSSFSheet worksheet = workbook.createSheet("Students");

        // 3.定义起始行和列 
        int startRowIndex = 0; 
        int startColIndex = 0;

        // 4.创建title,headers 
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

       // 5.填充数据 
        ScoreRecordService.fillReport(worksheet, startRowIndex, startColIndex, getDatasource(request));

        // 6.设置reponse参数 
        String fileName = "StudentsReport.xls"; 
        response.setHeader("Content-Disposition", "inline; filename=" + fileName); 
        // 确保发送的当前文本格式 
        response.setContentType("application/vnd.ms-excel");

        // 7. 输出流 
        Writer.write(response, worksheet); 
    }

	 /**
	  * 读取报表，存到list中
	  * @param inp 输入流
	  * @return
	  */
    public List<LStrMap<Object>> readReport(InputStream inp) {

        List<LStrMap<Object>> dataList = new ArrayList<LStrMap<Object>>();

        try { 
            Workbook wb = WorkbookFactory.create(inp); 
            Sheet sheet = wb.getSheetAt(0);
            String cellStr = null;
            for (int i = 3; i <= sheet.getLastRowNum(); i++) { 
                Row row = sheet.getRow(i); 
                if(row == null){ 
                    continue; 
                } 
                LStrMap<Object> map = LStrMap.newInstance();
                for(int j = 0; j < row.getLastCellNum(); j++){ 
                    Cell cell = row.getCell(j);
                    cellStr = ConvertCellStr(cell);
                    addingMap(map,cellStr,j);	//将数据存到map中
                } 
                //将所有学生记录有序存到list中
                dataList.add(map);
            } 
        } catch (InvalidFormatException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            if(inp != null){ 
                try { 
                    inp.close(); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
            else { 
                System.out.println("没有数据流"); 
            } 
        } 
        
        return dataList;

    }
    
    private void addingMap(LStrMap<Object> map, String cellStr, int columnNum) {
    	switch (columnNum) {
		case 0:
			map.put("student_no", cellStr);
			break;
		case 1:
			map.put("student_name", cellStr);
			break;
		case 2:
			map.put("score", cellStr);
			break;
		default:
			break;
		}
	}

    /**
     * 得到要导入到报表中的数据源
     * @param request
     * @return
     */
    private List<LStrMap<Object>> getDatasource(HttpServletRequest request) {
		return scoreRecordDao.getAllStudentByClassId(Long.parseLong(request.getParameter("classId")), Long.parseLong(request.getParameter("testId")), Long.parseLong(request.getParameter("subjectId")));
	}
    
    public LStrMap<Object> uploadXls(Long userId, HttpServletRequest request, HttpServletResponse response) {
    	FileUploader xlsUpload=new FileUploader();
		//设置保存路径
		xlsUpload.setSavePath("/images");
		//设置最大上传大小
		xlsUpload.setFileSizeMax(1024000);
		xlsUpload.setSizeMax(1024000);
		//设置文件格式
		Set<String> set=new HashSet<String>();
		set.add(".xls");
		xlsUpload.setAcceptTypes(set);
		//设置文件名
		FileNameGenerator xlsFileNameGenerator=new XlsFileNameGenerator(userId);
		xlsUpload.setFileNameGenerator(xlsFileNameGenerator);
		Result resut=xlsUpload.upload(request, response);
		Set<String> names=xlsUpload.getFileNames();
		LStrMap<Object> map = LStrMap.newInstance();
		String errorInfo="上传失败！";
		if("SUCCESS".equals(resut.toString())){
			errorInfo="success";
		}else{
			errorInfo="error";
		}
		Iterator iterator = names.iterator();
		String str = "";
		while (iterator.hasNext()) {
			str = iterator.next().toString();
		}
    	map.put("errorInfo", errorInfo);
    	map.put("xlsPath", "images/"+str);
    	return map;
    }
    
    /**  
     * 把单元格内的类型转换至String类型  
     */ 
    private String ConvertCellStr(Cell cell) { 
        String cellStr = "";
    	switch (cell.getCellType()) {   
          
        case Cell.CELL_TYPE_STRING:   
            // 读取String   
            cellStr = cell.getStringCellValue().toString();   
            break;   
        case Cell.CELL_TYPE_BOOLEAN:   
            // 得到Boolean对象的方法   
            cellStr = String.valueOf(cell.getBooleanCellValue());   
            break;   
        case Cell.CELL_TYPE_NUMERIC:   
            // 先看是否是日期格式   
            if (DateUtil.isCellDateFormatted(cell)) {   
                // 读取日期格式   
                cellStr = cell.getDateCellValue().toString();   
            } else {   
            	cell.setCellType(Cell.CELL_TYPE_STRING);
                // 读取数字   
                cellStr = cell.getStringCellValue();  
            }   
            break;   
        case Cell.CELL_TYPE_FORMULA:   
            // 读取公式   
            cellStr = cell.getCellFormula().toString();   
            break;   
        }   
        return cellStr;   
    } 
    
    public void savaReport(){
    	//将数据插入数据库
    	
    	//插入时检验出数据库已存在该记录，则更新数据库记录
    }
    
    public JsonArray stringToJson(String jsonString) {
		JsonParser parser = new JsonParser();								//得到一个json解析器，用以解析json字符串
		JsonElement jsonElement = parser.parse(jsonString);					//解析json字符串
		
		//将解析出来的信息存到jsonArray中
		JsonArray jsonArray = null;
		if (jsonElement.isJsonArray()) {
			jsonArray = jsonElement.getAsJsonArray();
			return jsonArray;
		}
		return null;
	}
    
    public int insertRecord(UStrMap<Object> map) {
    	int result = 0;
    	
    	//不抛异常则正常插入，抛出异常说明已有该记录，则更新
    	try {
    		result = scoreRecordDao.insertTestScore(map);
		} catch (Exception e) {
			result = scoreRecordDao.updateScore(map);
		}
    	return result;
    }
}
