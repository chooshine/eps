package com.eps.dao.crawl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.service.crawl.bean.Ques;
import com.eps.service.crawl.bean.Task;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class ZhujuanDao extends BaseDao{
	@Value("${zhujuan.category.insert}")
	private String insert;
	@Value("${zhujuan.categroy.select}")
	private String selectCategory;
	@Value("${zhujuan.imageurl.save}")
	private String saveImageUrl;
	@Value("${zhujuan.category.ques.save}")
	private String saveCategoryQues;
	@Value("${zhujuan.ques.save}")
	private String saveQues;
	@Value("${zhujuan.task.select}")
	private String selectTask;
	@Value("${zhujuan.task.update}")
	private String updateTask;
	@Value("${zhujuan.task.log.select}")
	private String getLog;
	@Value("${zhujuan.task.log.save}")
	private String saveLog;
	@Value("${zhujuan.image.select}")
	private String selectImage;
	@Value("${zhujuan.image.delete}")
	private String deleteImage;
	@Value("${zhujuan.ques.select}")
	private String selectQues;
	@Value("${zhujuan.ques.update}")
	private String updateQues;
	public void batchInsert(List<UStrMap<Object>> params){
		this.batchUpdate(insert, params);
	}
	
	public List<LStrMap<Object>> queryCategroy(int categoryId, String type, int bankId){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("categoryid", categoryId);
		params.put("type", type);
		params.put("bankid", bankId);
		return this.find(selectCategory, params);
	}
	public void insertImageUrl(List<UStrMap<Object>> params){
		this.batchUpdate(saveImageUrl, params);
	}
	
	public void insertCategoryQues(List<UStrMap<Object>> params){
		this.batchUpdate(saveCategoryQues, params);
	}
	
	public void insertQues(UStrMap<Object> params){
		//this.batchUpdate(saveQues, params);
		this.excute(saveQues, params);
	}
	
	public List<Task> selectTask(){
		return this.getNameParameTemplate().query(selectTask, UStrMap.newInstance(), new RowMapper<Task>() {
			@Override
			public Task mapRow(ResultSet rs, int index) throws SQLException {
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setCategoryId(rs.getInt("categoryId"));
				task.setType(rs.getString("type"));
				task.setBankId(rs.getInt("bankId"));
				return task;
			}
		});
	}
	public Task getTaskByLog(Task task){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("taskid", task.getId());
		LStrMap<Object> result = LStrMap.newInstance();
		try {
			result = this.readRecode(getLog, args);
		} catch (Exception e) {
			task.setPageNo(1);
			return task;
		}
		if(result != null && !result.isEmpty()){
			int pageNo = (Integer) result.get("pageno");
			task.setPageNo(pageNo);
		}
		return task;
	}
	public void saveLog(int taskId, int pageNo){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("taskid", taskId);
		args.put("pageno", pageNo);
		this.excute(saveLog, args);
	}
	
	public void updateTask(int taskId){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("taskid", taskId);
		this.excute(updateTask, args);
	}
	public List<String> selectImageUrl(int limit){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("limit", limit);
		return this.getNameParameTemplate().queryForList(selectImage, args, String.class);
	}
	public void deleteImage(String url){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("url", url);
		this.excute(deleteImage, args);
	}
	public List<Ques> getQues(int limit){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("limit", limit);
		return this.getNameParameTemplate().query(selectQues, args, new RowMapper<Ques>() {
			@Override
			public Ques mapRow(ResultSet rs, int index) throws SQLException {
				Ques q = new Ques();
				q.setId(rs.getInt("id"));
				q.setQuesAbility(rs.getString("QuesAbility"));
				q.setQuesAnswer(rs.getString("QuesAnswer"));
				q.setQuesBody(rs.getString("QuesBody"));
				q.setQuesDiff(rs.getString("QuesDiff"));
				q.setQuesParse(rs.getString("QuesParse"));
				q.setQuesType(rs.getString("QuesType"));
				q.setTime(rs.getDate("Time"));
				q.setTitle(rs.getString("Title"));
				return q;
			}
		});
	}
	public void updateQuesStatus(int id){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("id", id);
		this.excute(updateQues, args);
	}
}
