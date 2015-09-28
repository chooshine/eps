package com.eps.service.esextends;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.esextends.ExamOrderDao;
import com.eps.domain.FGoodModel;
import com.eps.domain.FGoods;
import com.eps.domain.FTrade;
import com.eps.utils.LStrMap;
import com.eps.utils.SequenceFactory;
import com.eps.utils.UStrMap;

@Service
public class ExamOrderService {

	@Autowired
	private ExamOrderDao eod;
	
	/**
	 * 获得订购左边的数据
	 * @param userId
	 * @return
	 */
	public List<List<LStrMap<Object>>> getOrderLeftInfoService(long userId){
		List<List<LStrMap<Object>>> list=new ArrayList<List<LStrMap<Object>>>();
		list.add(eod.getExamQuentityDao(userId));
		list.add(eod.getTeacherQuentityDao(userId));
		List<LStrMap<Object>> list2 =eod.getReleaseQuentityDao(userId);
		if(list2.size()==1){
			LStrMap<Object> map=LStrMap.newInstance();
			map.put("COUNT_EXAM", 0);
			if("1".equals(list2.get(0).get("RELEASE_STATUS").toString())){
				map.put("RELEASE_STATUS", 0);
			}else{
				map.put("RELEASE_STATUS", 1);
			}
			list2.add(map);
		}else if(list2.size()==0){
			for (int i = 0; i < 2; i++) {
				LStrMap<Object> map=LStrMap.newInstance();
				map.put("COUNT_EXAM", 0);
				map.put("RELEASE_STATUS", i);
				list2.add(map);
			}
		}
		list.add(list2);
		list.add(eod.getTeacherOrderbyDao(userId));
		return list;
	}
	
	/**
	 * 得到当前用户完成与未完成的试卷数量
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getCommitQuService(long userId){
		return eod.getCommitQuentityDao(userId);
	}
	
	/**
	 * 得到试卷的额外信息
	 * @param userId,sortNo,commitFlag
	 * @return
	 */
	public Page getNowExamInfoService(long userId,String sortNo,String commitFlag,int pageNo, int pageSize){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("SUBJECT_NO", sortNo);
		map.put("COMMIT_FLAG", commitFlag);
		String str=" COMMIT_FLAG IS NOT NULL ) GROUP BY e.EXAM_ID";
		if(Integer.parseInt(commitFlag)==0){
			str=" COMMIT_FLAG IS NULL ) GROUP BY e.EXAM_ID";
		}
		return eod.getNowExamInfoDao(map,pageNo,pageSize,str);
	}
	
	/**
	 * 得到当前用户订阅试卷的做题情况
	 * @param userId
	 * @return
	 */
	public Map<String, LStrMap<Object>> getTestRecordService(long userId){
		Map<String, LStrMap<Object>> map=new HashMap<String, LStrMap<Object>>();
		List<List<LStrMap<Object>>> list=new ArrayList<List<LStrMap<Object>>>();
		List<LStrMap<Object>> getList=eod.getTestRecordDao(userId);
		for (int i = 0; i < getList.size(); i++) {
			
			if(getList.get(i).get("EXAM_USE_TIME")!=null){
				String time=getList.get(i).get("EXAM_USE_TIME").toString();
				String[] tg=time.split(":");
				String str=String.format("%d小时%d分钟%d秒", Integer.parseInt(tg[0]),Integer.parseInt(tg[1]),Integer.parseInt(tg[2]));
				getList.get(i).put("EXAM_USE_TIME", str);
			}
			
			if(map.containsKey(getList.get(i).get("EXAM_ID").toString())){
				getList.get(i).put("COUNT_TEST", Integer.parseInt(map.get(getList.get(i).get("EXAM_ID").toString()).get("COUNT_TEST").toString())+1);
				map.put(getList.get(i).get("EXAM_ID").toString(), getList.get(i));
			}else{
				getList.get(i).put("COUNT_TEST", 1);
				map.put(getList.get(i).get("EXAM_ID").toString(), getList.get(i));
			}
		}
		Iterator iterator=map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry=(Entry<String, LStrMap<Object>>) iterator.next();
			entry.getValue();
		}
		
		return map;
	}
	
	/**
	 * 得到当前用户订阅试卷的是否过期数量
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherLoseService(long userId){
		return eod.getTeacherIsloseDao(userId);
	}
	
	
	/**
	 * 得到教师的额外信息
	 * @param userId,sortNo,commitFlag
	 * @return
	 */
	public Page getTeacherInfoService(long userId,String isLose,String subjectNo,int pageNo, int pageSize){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("IS_LOSE", isLose);
		map.put("SUBJECT_NO", subjectNo);
		
		return eod.getTeacherInfoDao(map,pageNo,pageSize);
	}
	
	/**
	 * 得到一个教师的信息
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getOneTeacherInfoService(int teacherId){
		return eod.getOneTeacherInfoDao(teacherId);
	}
	
	/**
	 * 插入评价记录
	 * @param map
	 * @return
	 */
	public boolean saveGoodsAssessService(UStrMap<Object> map){
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		 SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		 map.put("ASS_TIME", dateformat.format(date));
		 map.put("REMARK", "");
		return eod.saveGoodsAssessDao(map)==1;
	}
	
	/**
	 * 获得评价信息
	 * @param goodsId
	 * @param pageNo
	 * @param pageSize
	 * @param assType
	 * @return
	 */
	public Page getTeacherAssessService(String goodsId,int pageNo, int pageSize,int assType){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("GOODS_ID", goodsId);
		map.put("ASS_TYPE", assType);
		map.put("ALL_ASSESS", assType);

		return eod.getTeacherAssessDao(map,pageNo,pageSize);
	}
	
	/**
	 * 各级评论数
	 * @param goodsId
	 * @return
	 */
	public List<LStrMap<Object>> getAssessQuentityService(String goodsId){
		return eod.getAssessQuentityDao(goodsId);
	}
	
	/**
	 * 获得评价信息
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param release
	 * @return
	 */
	public Page getExamReleaseService(long userId,int pageNo,int pageSize,int release){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("CAREAOR", userId);
		map.put("RELEASE_STATUS", release);
		map.put("ALL_RELEASE", release);

		return eod.getExamReleaseDao(map, pageNo, pageSize);
	}
	
	/**
	 * 教师出试卷所有科目
	 * @param userId
	 * @param sortParentId
	 * @return
	 */
	public List<LStrMap<Object>> getSubjectnoQuenService(long userId,int sortParentId){
		return eod.getSubjectnoQuenDao(userId, sortParentId);
	}
	
	/**
	 * 教师出试卷所有地区
	 * @param userId
	 * @param sortParentId
	 * @return
	 */
	public List<LStrMap<Object>> getExamAreaQuenService(long userId,int sortParentId){
		return eod.getExamAreaQuenDao(userId, sortParentId);
	}
	
	/**
	 * 得到教师的额外信息
	 * @param userId
	 * @param parentSubjectno
	 * @param examarea
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getAllOrderTeacherService(long userId,String parentSubjectno,String examarea,int pageNo, int pageSize,String sortName,String sortType,int sortParentId){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("SUBJECT_NO", parentSubjectno);
		map.put("EXAM_AREA", examarea);
		map.put("SORT_PARENT_ID", sortParentId);
		return eod.getAllOrderTeacherDao(map,pageNo,pageSize,sortName,sortType);
	}
	
	/**
	 * 获得教师信息
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getTeacherPerInfoService(int teacherId){
		 List<LStrMap<Object>> list=eod.getTeacherPerInfoDao(teacherId);
		 String teach=list.get(0).get("TEACH").toString();
		 String teachArr[]=teach.split(",");
		 StringBuffer str=new StringBuffer();
		 str.append("(");
		 for (int i = 0; i < teachArr.length; i++) {
			str.append(teachArr[i]);
			if(i!=teachArr.length-1){
				str.append(",");
			}
		 }
		 str.append(")");
		 List<LStrMap<Object>> listName= eod.getTeacherTeachDao(str);
		 
		 StringBuffer strName=new StringBuffer();
		 for (int i = 0; i < listName.size(); i++) {
			strName.append(listName.get(i).get("SORT_NAME"));
			if(i!=listName.size()-1){
				strName.append(" ");
			}
		}
		 
		 list.get(0).put("TEACH", strName);
		return list;
	}
	
	/**
	 * 交易记录
	 * @param teacherId
	 * @param pageNo
	 * @param pageSize
	 * @param release
	 * @return
	 */
	public Page getTradeByTeacheridService(int teacherId,int pageNo,int pageSize){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("PRODUCT_ID", teacherId);
		return eod.getTradeByTeacheridDao(map, pageNo, pageSize);
	}
	
	/**
	 * 发布记录
	 * @param teacherId
	 * @param pageNo
	 * @param pageSize
	 * @param release
	 * @return
	 */
	public Page getReleaseByTeacheridService(int teacherId,int pageNo,int pageSize){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("TEACHER_ID", teacherId);
		return eod.getReleaseByTeacheridDao(map, pageNo, pageSize);
	}
	
	/**
	 * 教师的订阅版本
	 * @param teacherId
	 * @return
	 */
	public List<LStrMap<Object>> getModelByTeacheridService(int teacherId){
		return eod.getModelByTeacheridDao(teacherId);
	}
	
	/**
	 * 插入交易表，未完成交易
	 * @param fTrade
	 * @return
	 */
	public boolean saveTradeRecordService(FTrade fTrade){
		return eod.saveTradeRecordNoDao(fTrade)==1;
	}
	
	public boolean payMoneyService(double total_fee,String out_trade_no,long userId){
		//total_fee=Math.
		DecimalFormat df = new DecimalFormat("#.00");
		total_fee=Double.parseDouble(df.format(total_fee));
		//根据订单号获得用户id
		List<LStrMap<Object>> ugList=eod.getUgIdByTradeIdDao(out_trade_no);
		int teacherId=Integer.parseInt(ugList.get(0).get("TEACHER_ID").toString());
		int subLength=Integer.parseInt(ugList.get(0).get("MODEL_NAME").toString());
		//int subLength=Integer.parseInt(ugList.get(0).get("SUB_LENGTH").toString());
		String tradeLogId=SequenceFactory.getTradeLogSequence();
		UStrMap<Object> logMap=UStrMap.newInstance();
		logMap.put("TRADE_LOG_ID", tradeLogId);
		logMap.put("TRADE_ID", out_trade_no);
		logMap.put("CASH", total_fee);
		logMap.put("COUPON", 0);
		logMap.put("USER_ID", userId);
		logMap.put("REMARK", "");
		logMap.put("TRADE_TYPE", 1);
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		logMap.put("TRADE_TIME",dateformat.format(date));
		
		
		//eod.payMoneyDao(ugList.get(0).get("GOODS_ID").toString(), out_trade_no, userId);
		long otherUserId=Long.parseLong(ugList.get(0).get("USER_ID").toString());
		
		List<LStrMap<Object>> cashList=eod.getCashByuserIdDao(otherUserId);
		logMap.put("CASH_BEFORE", cashList.get(0).get("CASH"));
		List<LStrMap<Object>> couponList=eod.getCouponByuserIdDao(otherUserId);
		if(couponList.size()==1){
			logMap.put("COUPON_BEFORE", couponList.get(0).get("COUNT_COUPON"));
		}
		
		eod.updateAccountByuserIdDao(otherUserId, total_fee);
		
		List<LStrMap<Object>> cashList2=eod.getCashByuserIdDao(otherUserId);
		logMap.put("CASH_AFTER", cashList2.get(0).get("CASH"));
		List<LStrMap<Object>> couponList2=eod.getCouponByuserIdDao(otherUserId);
		if(couponList2.size()==1){
			logMap.put("COUPON_AFTER", couponList2.get(0).get("COUNT_COUPON"));
		}
		
		UStrMap<Object> traMap=UStrMap.newInstance();
		traMap.put("SNAP_ID", tradeLogId);
		traMap.put("TRADE_TIME", dateformat.format(date));
		traMap.put("MONEY", total_fee);
		traMap.put("TRADE_STATUS", 2);
		traMap.put("REMARK", "");
		traMap.put("TRADE_ID", out_trade_no);
		eod.updateTradeBytradeIdDao(traMap);
		eod.saveTradeLogDao(logMap);
		
		//f_subscribe表的相关操作
		UStrMap<Object> gSubMap=UStrMap.newInstance();
		gSubMap.put("USER_ID", userId);
		gSubMap.put("TEACHER_ID", teacherId);
		List<LStrMap<Object>> gSubList=eod.getSubscribeBytuIdDao(gSubMap);
		if(gSubList.size()!=0){
			int oldSubLength=Integer.parseInt(gSubList.get(0).get("SUB_LENGTH").toString());
			UStrMap<Object> gUpdateMap=UStrMap.newInstance();
			gUpdateMap.put("IS_LOSE",0 );
			gUpdateMap.put("TEACHER_ID", teacherId);
			gUpdateMap.put("USER_ID", userId);
			if(Integer.parseInt(gSubList.get(0).get("IS_LOSE").toString())==0){
				gUpdateMap.put("SUB_LENGTH",(subLength+oldSubLength));
				gUpdateMap.put("SUB_TIME",gSubList.get(0).get("SUB_TIME").toString());
			}else{
				gUpdateMap.put("SUB_LENGTH",subLength);
				gUpdateMap.put("SUB_TIME",dateformat.format(date));
			}
			eod.updateSubscribeBytuIdDao(gUpdateMap);
		}else{
			UStrMap<Object> gSaveMap=UStrMap.newInstance();
			gSaveMap.put("USER_ID", userId);
			gSaveMap.put("TEACHER_ID", teacherId);
			gSaveMap.put("SUB_TIME", dateformat.format(date));
			gSaveMap.put("SUB_LENGTH", subLength);
			gSaveMap.put("IS_LOSE", 0);
			gSaveMap.put("REMARK", "");
			eod.saveSubscribeDao(gSaveMap);
		}
		return true;
	}
	
	/**
	 * 更新教师上架信息
	 * @param userId
	 * @return
	 */
	public boolean updateForsaleService(long userId,int saleType){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("FOR_SALE", saleType);
		return eod.updateForsaleDao(map)==1;
		
	}
	
	/**
	 * 得到发布试卷总数及上架情况
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getcfInforService(long userId){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("USER_ID", userId);
		return eod.getcfInforDao(map);
		
	}
	
	/**
	 * 得到当前用户的商品编号
	 * @param userId
	 * @return
	 */
	public List<LStrMap<Object>> getGoodsIdService(long userId){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("USER_ID", userId);
		return eod.getGoodsIdDao(map);
		
	}
	
	/**
	 * 插入商品模板表
	 * @param fGoodModel
	 * @return
	 */
	public boolean saveGoodModelService(FGoodModel fGoodModel){
		return eod.saveGoodModelDao(fGoodModel)==1;
	}
	
	/**
	 * 得到当前用户的所有试卷价格商品编号
	 * @param userId
	 * @return
	 */
	public LStrMap<Object> getGoodModelService(long userId){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("USER_ID", userId);
		List<LStrMap<Object>> list=eod.getGoodModelDao(map);
		LStrMap<Object> lMap=LStrMap.newInstance();
		for (int i = 0; i < list.size(); i++) {
			lMap.put(list.get(i).get("MODEL_NAME").toString(), list.get(i));
		}
		return lMap;
		
	}
	
	/**
	 * 更新当前用户的价格模板
	 * @param fGoodModel
	 * @return
	 */
	public boolean updateGoodModelService(FGoodModel fGoodModel){
		return eod.updateGoodModelDao(fGoodModel)==1;
	}
	
	/**
	 * 删除当前用户的价格模板
	 * @param fGoodModel
	 * @return
	 */
	public boolean deleteGoodModelService(FGoodModel fGoodModel){
		return eod.deleteGoodModelDao(fGoodModel)==1;
	}
	
	/**
	 * 删除试卷
	 * @param userId
	 * @return
	 */
	public boolean deleteExamService(long userId,int examId){
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("USER_ID", userId);
		map.put("EXAM_ID",examId);
		return eod.deleteExamDao(map)==1;
	}
	
	public boolean saveGoodsService(long userId,int teacherId){
		
		FGoods fGoods=new FGoods();
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		fGoods.setStartTime(dateformat.format(date));
		fGoods.setUserId(userId);
		fGoods.setForSale(1);
		fGoods.setProductType(7);
		fGoods.setProductId(teacherId);
		fGoods.setGoodsId(SequenceFactory.getGoodsSequence());
		return eod.saveGoodsDao(fGoods)==1;
	}
}
