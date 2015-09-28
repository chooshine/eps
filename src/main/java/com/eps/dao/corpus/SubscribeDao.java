package com.eps.dao.corpus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.FGoodModel;
import com.eps.domain.FTrade;
import com.eps.domain.LSubscribe;
import com.eps.utils.DateHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class SubscribeDao extends BaseDao{
	
	@Value("${good.model.corpus}")
	private String getSubscribeGoodModel;
	
	@Value("${trade.get.tradeno}")
	private String getTradeByid;
	
	@Value("${trade.update.status}")
	private String updateTradeStatus;
	
	@Value("${lsubscribe.insert}")
	private String insertSubscribe;
	
	@Value("${lsubscribe.update}")
	private String updateSubscribe;
	
	@Value("${lsubscribe.get.user}")
	private String getSubscribeByUser;
	@Value("${good.model.get.trade}")
	private String getGoodModelByTrade;
	
	@Value("${teacher.user.phone}")
	private String getPhone;
	
	public List<LStrMap<Object>> getSubscribeGood(String goodid){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("GOODS_ID", goodid);
		return this.find(getSubscribeGoodModel, params);
	}
	
	/**
	 * 根据交易编号查询商品模版信息
	 * @param tradeNo
	 * @return
	 */
	public FGoodModel getGoodModelByTrade(String tradeNo){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("trade_no", tradeNo);
		return getNameParameTemplate().queryForObject(getGoodModelByTrade, paramMap, new RowMapper<FGoodModel>() {
			public FGoodModel mapRow(ResultSet rs, int arg1)
					throws SQLException {
				FGoodModel goodModel = new FGoodModel();
				goodModel.setGoodsId(rs.getString("GOODS_ID"));
				goodModel.setModelId(rs.getInt("MODEL_ID"));
				goodModel.setModelName(rs.getString("MODEL_NAME"));
				goodModel.setUnitPrice(rs.getFloat("UNIT_PRICE"));
				goodModel.setStock(rs.getInt("STOCK"));
				goodModel.setRemark(rs.getString("REMARK"));
				return goodModel;
			}
		});
	}
	
	public FTrade getTradeByid(String tradeNo){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("TRADE_ID", tradeNo);
		return this.getNameParameTemplate().queryForObject(getTradeByid, paramMap, new RowMapper<FTrade>() {
			public FTrade mapRow(ResultSet rs, int arg1) throws SQLException {
				FTrade trade = new FTrade();
				trade.setTradeId(rs.getString("TRADE_ID"));
				trade.setUserId(rs.getLong("USER_ID"));
				trade.setGoodsId(rs.getString("GOODS_ID"));
				trade.setModelId(rs.getInt("MODEL_ID"));
				trade.setMoney(rs.getFloat("MONEY"));
				trade.setTradeStatus(rs.getInt("TRADE_STATUS"));
				trade.setTradeTime(rs.getString("TRADE_TIME"));
				trade.setTradeType(rs.getInt("TRADE_TYPE"));
				trade.setRemark(rs.getString("REMARK"));
				return trade;
			}
		});
	}
	
	public int updateTradeStatus(String tradeNo,int status){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("TRADE_ID", tradeNo);
		paramMap.put("STATUS", status);
		return this.excute(updateTradeStatus, paramMap);
	}
	
	public int saveSubscribe(LSubscribe subscribe){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("user_id", subscribe.getUserId());
		paramMap.put("start_datetime", subscribe.getStartDate());
		paramMap.put("end_datetime", subscribe.getEndDate());
		paramMap.put("remark", subscribe.getRemark());
		return this.excute(insertSubscribe, paramMap);
	}
	public int updateSubscribe(LSubscribe subscribe){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("user_id", subscribe.getUserId());
		paramMap.put("start_datetime", subscribe.getStartDate());
		paramMap.put("end_datetime", subscribe.getEndDate());
		paramMap.put("remark", subscribe.getRemark());
		return this.excute(updateSubscribe, paramMap);
	}
	
	public LSubscribe getSucscribeByUser(long userId){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("user_id", userId);
		List<LSubscribe> list = this.getNameParameTemplate().query(getSubscribeByUser, paramMap, new RowMapper<LSubscribe>() {
			public LSubscribe mapRow(ResultSet rs, int arg1)
					throws SQLException {
				LSubscribe subscribe = new LSubscribe();
				subscribe.setUserId(rs.getLong("USER_ID"));
				subscribe.setStartDate(DateHelper.parseDate(rs.getString("START_DATETIME"), "yyyy-MM-dd HH:mm:ss")); 
				subscribe.setEndDate(DateHelper.parseDate(rs.getString("END_DATETIME"), "yyyy-MM-dd HH:mm:ss"));
				subscribe.setRemark(rs.getString("REMARK"));
				return subscribe;
			}
		});
		if(list.size()==1) return list.get(0);
		return null;
	}
	
	/**
	 * 传入推荐码，查询该推荐码对应的老师是否已经认证身份及该推荐码是否存在
	 * @param phone
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherPhone(String phonenuber){
		UStrMap<Object> params = UStrMap.newInstance();
		params.put("phone", phonenuber);
		return this.find(getPhone, params);
	}
}
