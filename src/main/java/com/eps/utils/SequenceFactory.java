package com.eps.utils;

public class SequenceFactory {
	/**
	 * 商品序列号
	 */
	private static Long GOODS_SEQUENCE = 0l;
	
	/**
	 * 交易序列号
	 */
	private static Long TRADE_SEQUENCE = 0l;
	
	/**
	 * 交易日志序列号
	 */
	private static Long TRADE_LOG_SEQUENCE = 0l;
	
	/**
	 * 获取商品编号
	 * @param goodsType 商品类型
	 * @return
	 */
	public static String getGoodsSequence(){
		long seq = 1l;
		synchronized (GOODS_SEQUENCE) {
			if(GOODS_SEQUENCE++ == 9999l) GOODS_SEQUENCE = 1l;
			seq = GOODS_SEQUENCE;
		}
		String s = StringHelper.zeroFillTop(String.valueOf(seq), 4);
		String now = DateHelper.getNowYMDHMSS();
		return now + s;
	}
	
	/**
	 * 交易编号
	 * @return
	 */
	public static String getTradeSequence(){
		long seq = 1l;
		synchronized (TRADE_SEQUENCE) {
			if(TRADE_SEQUENCE++ == 9999l) TRADE_SEQUENCE = 1l;
			seq = TRADE_SEQUENCE;
		}
		String s = StringHelper.zeroFillTop(String.valueOf(seq), 4);
		String now = DateHelper.getNowYMDHMSS();
		return now + s;
	}
	
	/**
	 * 交易日志编号号
	 * @return
	 */
	public static String getTradeLogSequence(){
		long seq = 1l;
		synchronized (TRADE_LOG_SEQUENCE) {
			if(TRADE_LOG_SEQUENCE++ == 9999l) TRADE_LOG_SEQUENCE = 1l;
			seq = TRADE_LOG_SEQUENCE;
		}
		String s = StringHelper.zeroFillTop(String.valueOf(seq), 4);
		String now = DateHelper.getNowYMDHMSS();
		return now + s;
	}
}
