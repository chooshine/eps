/**
 * Service1Soap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.eps.service.sms.client;

public interface Service1Soap extends java.rmi.Remote {

    /**
     * 注册用户
     */
    public java.lang.String regineUser(java.lang.String uid, java.lang.String pwd, java.lang.String agent_code, java.lang.String co_name, java.lang.String link_man, java.lang.String mob) throws java.rmi.RemoteException;

    /**
     * 读取账户信息
     */
    public java.lang.String getUserInfo(java.lang.String uid, java.lang.String pwd) throws java.rmi.RemoteException;

    /**
     * 修改密码
     */
    public java.lang.String editUserPwd(java.lang.String uid, java.lang.String pwd, java.lang.String newpwd) throws java.rmi.RemoteException;

    /**
     * 发送短消息服务
     */
    public java.lang.String sendMessages(java.lang.String uid, java.lang.String pwd, java.lang.String tos, java.lang.String msg, java.lang.String otime) throws java.rmi.RemoteException;

    /**
     * 传真服务引擎
     */
    public java.lang.String sendFax(java.lang.String uid, java.lang.String pwd, java.lang.String faxno, java.lang.String men, java.lang.String title, byte[] bytes, java.lang.String fileName) throws java.rmi.RemoteException;

    /**
     * 彩信发送
     */
    public java.lang.String sendMMS(java.lang.String uid, java.lang.String pwd, java.lang.String mobno, java.lang.String title, java.lang.String content, byte[] bytes, java.lang.String mmsFileName) throws java.rmi.RemoteException;

    /**
     * 多帧彩信发送
     */
    public java.lang.String newSendMMS(java.lang.String uid, java.lang.String pwd, java.lang.String tos, java.lang.String title, MMSContent[] mmsContentList, java.lang.String otime) throws java.rmi.RemoteException;

    /**
     * 彩信扩展测试服务
     */
    public java.lang.String sendMMS_Test(java.lang.String uid, java.lang.String pwd, java.lang.String mobno) throws java.rmi.RemoteException;

    /**
     * 彩信发送测试
     */
    public java.lang.String sendMMsTest() throws java.rmi.RemoteException;

    /**
     * 短信发送状态
     */
    public java.lang.String getMessageInfo(java.lang.String snum) throws java.rmi.RemoteException;

    /**
     * 语音服务引擎
     */
    public java.lang.String sendVoice(java.lang.String uid, java.lang.String pwd, java.lang.String vto, java.lang.String vtxt, java.lang.String mode, byte[] fileBytes, java.lang.String svrno, java.lang.String str_time, java.lang.String end_time) throws java.rmi.RemoteException;

    /**
     * 传真发送记录
     */
    public java.lang.String getFaxRecord(java.lang.String uid, java.lang.String pwd, java.lang.String num, java.lang.String startDate, java.lang.String endDate) throws java.rmi.RemoteException;

    /**
     * 语音发送记录
     */
    public java.lang.String getVoiceRecord(java.lang.String uid, java.lang.String pwd, java.lang.String num, java.lang.String startDate, java.lang.String endDate) throws java.rmi.RemoteException;

    /**
     * 短信发送记录
     */
    public java.lang.String getMessageRecord(java.lang.String uid, java.lang.String pwd, java.lang.String num, java.lang.String startDate, java.lang.String endDate, java.lang.String isday) throws java.rmi.RemoteException;

    /**
     * 获取短信回复记录 (示例：手机号码/内容/端口/时间|手机号码/内容/端口/时间|手机号码/内容/端口/时间)
     */
    public java.lang.String GET_SMS_MO(java.lang.String uid, java.lang.String pwd, java.lang.String IDtype) throws java.rmi.RemoteException;

    /**
     * 获取MSP上行记录 (示例：手机号码/短信内容/地区/时间|手机号码/短信内容/地区/时间|手机号码/短信内容/地区/时间)
     */
    public java.lang.String GET_MSP_MO(java.lang.String uid, java.lang.String pwd, java.lang.String IDtype) throws java.rmi.RemoteException;

    /**
     * 网络电话
     */
    public java.lang.String telCall(java.lang.String uid, java.lang.String pwd, java.lang.String host_call, java.lang.String caller) throws java.rmi.RemoteException;
}
