package com.eps.web.alipay;

public class AlipayParams {

	
	//业务参数
	private String out_trade_no;                  //订单编号 唯一
	
	private String subject;                       //商品名称
	
	private double price;                         //商品单价
	
	private int quantity;                         //购买数量
	
	private double total_fee;                     //交易金额 (如有该参数)
	
	private String body;                          //商品描述
	
	private String show_url;                      //商品展示链接
	
	private String exter_invoke_ip;               //客户请求IP
	
	private String redirect_success;              //付款成功跳转地址
	
	private String redirect_error;                //付款失败跳转地址
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public String getExter_invoke_ip() {
		return exter_invoke_ip;
	}

	public void setExter_invoke_ip(String exter_invoke_ip) {
		this.exter_invoke_ip = exter_invoke_ip;
	}

	public String getRedirect_success() {
		return redirect_success;
	}

	public void setRedirect_success(String redirect_success) {
		this.redirect_success = redirect_success;
	}

	public String getRedirect_error() {
		return redirect_error;
	}

	public void setRedirect_error(String redirect_error) {
		this.redirect_error = redirect_error;
	}
	
}
