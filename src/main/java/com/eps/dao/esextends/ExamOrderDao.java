package com.eps.dao.esextends;

import java.sql.Types;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.dao.Page;
import com.eps.dao.ProcedureParameter;
import com.eps.domain.FGoodModel;
import com.eps.domain.FGoods;
import com.eps.domain.FTrade;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;

@Repository
public class ExamOrderDao extends BaseDao{

	@Value("${esextends.examorder.get.quentity}")
	private String getExamQuentity;
	
	@Value("${esextends.teacherorder.get.quentity}")
	private String getTeacherQuentity;
	
	@Value("${esextends.teacher.get.orderby}")
	private String getTeacherOrderby;
	
	@Value("${esextends.exam.releaseorder.quentity}")
	private String getReleaseQuentity;
	
	@Value("${esextends.get.commit.quentity}")
	private String getCommitQuentity;
	
	@Value("${esextends.get.examinfo.now}")
	private String getNowExamInfo;
	
	@Value("${esextends.get.testrecord.now}")
	private String getTestRecord;
	
	@Value("${esextends.get.teacher.islose}")
	private String getTeacherIslose; 
	
	@Value("${esextends.get.teacher.info}")
	private String getTeacherInfo; 
	
	@Value("${esextends.get.teacher.info.one}")
	private String getOneTeacherInfo; 
	
	@Value("${esextends.save.assess.goods}")
	private String saveGoodsAssess;
	
	@Value("${esextends.get.teacher.assess}")
	private String getTeacherAssess;
	
	@Value("${esextends.get.assess.quentity}")
	private String getAssessQuentity;
	
	@Value("${esextends.get.release.exam}")
	private String getExamReleaseInfo;
	
	@Value("${esextends.get.exam.subjectNo.quen}")
	private String getSubjectNoQuen;

	
	@Value("${esextends.get.exam.examarea.quen}")
	private String getExamAreaQuen;

	
	@Value("${esextends.get.order.teacher.all}")
	private String getAllOrderTeacher;
	
	@Value("${esextends.get.teacher.pinfo}")
	private String getTeacherPerInfo;
	
	@Value("${esextends.get.teacher.teach}")
	private String getTeacherTeach;
	
	@Value("${esextends.get.trade.teacher}")
	private String getTradeByTeacherid;

	@Value("${esextends.get.release.teacher}")
	private String getReleaseByTeacherid;
	
	@Value("${esextends.get.exam.model}")
	private String getModelByTeacherid;
	
	@Value("${esextends.save.trade.no}")
	private String saveTradeRecordNo;
	
	@Value("${esextends.get.ugid.tradeid}")
	private String getUgIdByTradeId;
	
	@Value("${esextends.update.faccount.userid}")
	private String updateAccountByuserId;

	@Value("${esextends.update.trade.tradeid}")
	private String updateTradeBytradeId;
	
	@Value("${esextends.get.cash.userid}")
	private String getCashByuserId;
	
	@Value("${esextends.get.coupon.userid}")
	private String getCouponByuserId;
	
	@Value("${esextends.save.tradelog}")
	private String saveTradeLog;
	
	@Value("${esextends.get.subscribe.tuid}")
	private String getSubscribeBytuId;
	
	@Value("${esextends.update.subscribe.tuid}")
	private String updateSubscribeBytuId;
	
	@Value("${esextends.save.subscribe}")
	private String saveSubscribe;
	
	@Value("${esextends.update.forsale.teacher}")
	private String updateForsaleByUserid;
	
	@Value("${esextends.get.cfinfor.teacher}")
	private String getcfInfoByUserid;
	
	@Value("${esextends.save.goodmodel.tacher}")
	private String saveGoodModelBygoodsid;
	
	@Value("${esextends.get.gooodsId.now}")
	private String getGoodsIdByUserid;
	
	@Value("${esextends.get.goodmodel.now}")
	private String getGoodModelbyUserid;
	
	@Value("${esextends.update.goodmodel.now}")
	private String updateGoodModelbyUserid;
	
	@Value("${esextends.delete.goodmodel.now}")
	private String deleteGoodModelbyUserid;
	
	@Value("${esextends.save.goods.userid}")
	private String saveGoodsByUserid;
	
	@Value("${esextends.delete.exam.userid}")
	private String deleteExamByUserId;
	/**
	 * 获得定购的试卷数量
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getExamQuentityDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getExamQuentity, map);
	}
	
	/**
	 * 获得定购的老师数量
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherQuentityDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getTeacherQuentity, map);
	}
	
	/**
	 * 获得当前用户发布与未发布的试卷的数量
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getReleaseQuentityDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getReleaseQuentity, map);
	}
	
	/**
	 * 推荐老师排序
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherOrderbyDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getTeacherOrderby,map);
	}
	
	/**
	 * 得到当前用户完成与未完成的试卷数量
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getCommitQuentityDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getCommitQuentity, map);
	}
	
	/**
	 * 得到试卷的额外信息
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getNowExamInfoDao(UStrMap<Object> map,int pageNo, int pageSize,String str){
		Page page=this.pageQuery(getNowExamInfo+str,pageNo,pageSize, map);
		return page;
	}
	
	/**
	 * 得到当前用户订阅试卷的做题情况
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getTestRecordDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getTestRecord, map);
	}
	
	/**
	 * 得到当前用户订阅试卷的是否过期数量
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherIsloseDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getTeacherIslose, map);
	}
	
	/**
	 * 得到教师的额外信息
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getTeacherInfoDao(UStrMap<Object> map,int pageNo, int pageSize){
		Page page=this.pageQuery(getTeacherInfo,pageNo,pageSize, map);
		return page;
	}
	
	/**
	 * 得一个教师的信息
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getOneTeacherInfoDao(int teacherId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("TEACHER_ID", teacherId);
		return this.find(getOneTeacherInfo, map);
	}
	
	/**
	 * 保存商品评论
	 * @param map
	 * @return
	 */
	public int saveGoodsAssessDao(UStrMap<Object> map){
		return this.excute(saveGoodsAssess, map);
	}
	
	/**
	 * 得到教师评论信息
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getTeacherAssessDao(UStrMap<Object> map,int pageNo, int pageSize){
		return this.pageQuery(getTeacherAssess, pageNo, pageSize, map);
	}
	
	/**
	 * 各评论数
	 * @param goodsId
	 * @return
	 */
	public List<LStrMap<Object>> getAssessQuentityDao(String goodsId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("GOODS_ID", goodsId);
		return this.find(getAssessQuentity, map);
	}
	
	/**
	 * 试卷的发布信息
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getExamReleaseDao(UStrMap<Object> map,int pageNo, int pageSize){
		return this.pageQuery(getExamReleaseInfo, pageNo, pageSize, map);
	}
	
	/**
	 * 得到所有教师的信息以供订阅
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getAllOrderTeacherDao(UStrMap<Object> map,int pageNo, int pageSize,String sortName,String sortType){
		return this.pageQuery(getAllOrderTeacher+" ORDER BY "+sortName+" "+""+sortType, pageNo, pageSize, map);
	}
	
	/**
	 * 教师出试卷所有科目
	 * @param userId
	 * @param sortParentId
	 * @return
	 */
	public List<LStrMap<Object>> getSubjectnoQuenDao(long userId,int sortParentId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("SORT_PARENT_ID", sortParentId);
		return this.find(getSubjectNoQuen, map);
	}
	
	/**
	 * 教师出试卷所有地区
	 * @param userId
	 * @param sortParentId
	 * @return
	 */
	public List<LStrMap<Object>> getExamAreaQuenDao(long userId,int sortParentId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("SORT_PARENT_ID", sortParentId);
		return this.find(getExamAreaQuen, map);
	}
	
	/**
	 * 获得教师个人信息
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherPerInfoDao(int teacherId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("TEACHER_ID", teacherId);
		return this.find(getTeacherPerInfo, map);
	}
	
	/**
	 * 获得老师所教科目
	 * @param str
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherTeachDao(StringBuffer str){
		return this.find(getTeacherTeach+" "+str);
	}
	
	/**
	 * 交易记录
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getTradeByTeacheridDao(UStrMap<Object> map,int pageNo, int pageSize){
		return this.pageQuery(getTradeByTeacherid, pageNo, pageSize, map);
	}
	
	/**
	 * 发布记录
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getReleaseByTeacheridDao(UStrMap<Object> map,int pageNo, int pageSize){
		return this.pageQuery(getReleaseByTeacherid, pageNo, pageSize, map);
	}
	
	/**
	 * 教师的订阅版本
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getModelByTeacheridDao(int teacherId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("PRODUCT_ID", teacherId);
		return this.find(getModelByTeacherid,map);
	}
	
	/**
	 * 插入交易表，未完成交易
	 * @param fTrade
	 * @return
	 */
	public int saveTradeRecordNoDao(FTrade fTrade){
		return this.excute(saveTradeRecordNo, fTrade.toMap());
	}
	
	/**
	 * 根据订单号获得用户id和商品id
	 * @param tradeId
	 * @return
	 */
	public List<LStrMap<Object>> getUgIdByTradeIdDao(String tradeId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("TRADE_ID", tradeId);
		return this.find(getUgIdByTradeId,map);
	}
	
	/**
	 * 更新帐户表
	 * @param userId
	 * @param cash
	 * @return
	 */
	public int updateAccountByuserIdDao(long userId,double cash){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("CASH", cash);
		return this.excute(updateAccountByuserId,map);
	}
	
	/**
	 * 更新交易表
	 * @param map
	 * @return
	 */
	public int updateTradeBytradeIdDao(UStrMap<Object> map){
		return this.excute(updateTradeBytradeId,map);
	}
	
	/**
	 * 获得用户帐户金额
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getCashByuserIdDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getCashByuserId,map);
	}
	
	/**
	 * 获得用户消费券
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getCouponByuserIdDao(long userId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		return this.find(getCouponByuserId,map);
	}
	
	/**
	 * 插入交易日志表
	 * @param map
	 * @return
	 */
	public int saveTradeLogDao(UStrMap<Object> map){
		return this.excute(saveTradeLog, map);
	}
	
	/**
	 * 得到已有订阅信息
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getSubscribeBytuIdDao(UStrMap<Object> map){
		return this.find(getSubscribeBytuId,map);
	}
	
	/**
	 * 更新已有订阅信息
	 * @param map
	 * @return
	 */
	public int updateSubscribeBytuIdDao(UStrMap<Object> map){
		return this.excute(updateSubscribeBytuId,map);
	}
	
	/**
	 * 插入订阅信息
	 * @param map
	 * @return
	 */
	public int saveSubscribeDao(UStrMap<Object> map){
		return this.excute(saveSubscribe,map);
	}
	
	/**
	 * 更新教师上架信息
	 * @param map
	 * @return
	 */
	public int updateForsaleDao(UStrMap<Object> map){
		return this.excute(updateForsaleByUserid, map);
	}
	
	/**
	 * 得到发布试卷总数及上架情况
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getcfInforDao(UStrMap<Object> map){
		return this.find(getcfInfoByUserid,map);
	}
	
	/**
	 * 保存商品模板信息
	 * @param fGoodModel
	 * @return
	 */
	public int saveGoodModelDao(FGoodModel fGoodModel){
		return this.excute(saveGoodModelBygoodsid, fGoodModel.toMap());
	}
	
	/**
	 * 得到当前用户的商品编号
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getGoodsIdDao(UStrMap<Object> map){
		return this.find(getGoodsIdByUserid,map);
	}

	/**
	 * 得到当前用户的所有试卷价格模板
	 * @param map
	 * @return
	 */
	public List<LStrMap<Object>> getGoodModelDao(UStrMap<Object> map){
		return this.find(getGoodModelbyUserid,map);
	}
	
	/**
	 * 更新当前用户的价格模板
	 * @param fGoodModel
	 * @return
	 */
	public int updateGoodModelDao(FGoodModel fGoodModel){
		return this.excute(updateGoodModelbyUserid, fGoodModel.toMap());
	}
	
	/**
	 * 删除当前用户的价格模板
	 * @param fGoodModel
	 * @return
	 */
	public int deleteGoodModelDao(FGoodModel fGoodModel){
		return this.excute(deleteGoodModelbyUserid, fGoodModel.toMap());
	}
	
	/**
	 * 保存商品表
	 * @param fGoods
	 * @return
	 */
	public int saveGoodsDao(FGoods fGoods){
		return this.excute(saveGoodsByUserid,fGoods.toMap());
	}
	
	/**
	 * 删除试卷
	 * @param map
	 * @return
	 */
	public int deleteExamDao(UStrMap<Object> map){
		return this.excute(deleteExamByUserId, map);
	}
	
	/**
	 * 调用存储过程，完成交易
	 * @param total_fee
	 * @param out_trade_no
	 * @param userId
	 */
	public void payMoneyDao(String goodsId,String out_trade_no,long userId){
		Set<ProcedureParameter> set=new HashSet<ProcedureParameter>();
		set.add(ProcedureParameter.createInParameter(1, goodsId));
		set.add(ProcedureParameter.createInParameter(2, userId));
		set.add(ProcedureParameter.createInParameter(3, out_trade_no));
		set.add(ProcedureParameter.createOutParameter(4,Types.INTEGER));
		set.add(ProcedureParameter.createOutParameter(5,Types.VARCHAR));
		set.add(ProcedureParameter.createOutParameter(6,Types.VARCHAR));
		this.call("call PRC_PAYMENT(?,?,?,?,?,?)", set);
	}
	
}

