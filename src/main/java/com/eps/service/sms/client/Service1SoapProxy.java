package com.eps.service.sms.client;

public class Service1SoapProxy implements Service1Soap {
  private String _endpoint = null;
  private Service1Soap service1Soap = null;
  
  public Service1SoapProxy() {
    _initService1SoapProxy();
  }
  
  public Service1SoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initService1SoapProxy();
  }
  
  private void _initService1SoapProxy() {
    try {
      service1Soap = (new Service1Locator()).getService1Soap();
      if (service1Soap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service1Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service1Soap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service1Soap != null)
      ((javax.xml.rpc.Stub)service1Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Service1Soap getService1Soap() {
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap;
  }
  
  public java.lang.String regineUser(java.lang.String uid, java.lang.String pwd, java.lang.String agent_code, java.lang.String co_name, java.lang.String link_man, java.lang.String mob) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.regineUser(uid, pwd, agent_code, co_name, link_man, mob);
  }
  
  public java.lang.String getUserInfo(java.lang.String uid, java.lang.String pwd) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.getUserInfo(uid, pwd);
  }
  
  public java.lang.String editUserPwd(java.lang.String uid, java.lang.String pwd, java.lang.String newpwd) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.editUserPwd(uid, pwd, newpwd);
  }
  
  public java.lang.String sendMessages(java.lang.String uid, java.lang.String pwd, java.lang.String tos, java.lang.String msg, java.lang.String otime) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.sendMessages(uid, pwd, tos, msg, otime);
  }
  
  public java.lang.String sendFax(java.lang.String uid, java.lang.String pwd, java.lang.String faxno, java.lang.String men, java.lang.String title, byte[] bytes, java.lang.String fileName) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.sendFax(uid, pwd, faxno, men, title, bytes, fileName);
  }
  
  public java.lang.String sendMMS(java.lang.String uid, java.lang.String pwd, java.lang.String mobno, java.lang.String title, java.lang.String content, byte[] bytes, java.lang.String mmsFileName) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.sendMMS(uid, pwd, mobno, title, content, bytes, mmsFileName);
  }
  
  public java.lang.String newSendMMS(java.lang.String uid, java.lang.String pwd, java.lang.String tos, java.lang.String title, MMSContent[] mmsContentList, java.lang.String otime) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.newSendMMS(uid, pwd, tos, title, mmsContentList, otime);
  }
  
  public java.lang.String sendMMS_Test(java.lang.String uid, java.lang.String pwd, java.lang.String mobno) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.sendMMS_Test(uid, pwd, mobno);
  }
  
  public java.lang.String sendMMsTest() throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.sendMMsTest();
  }
  
  public java.lang.String getMessageInfo(java.lang.String snum) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.getMessageInfo(snum);
  }
  
  public java.lang.String sendVoice(java.lang.String uid, java.lang.String pwd, java.lang.String vto, java.lang.String vtxt, java.lang.String mode, byte[] fileBytes, java.lang.String svrno, java.lang.String str_time, java.lang.String end_time) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.sendVoice(uid, pwd, vto, vtxt, mode, fileBytes, svrno, str_time, end_time);
  }
  
  public java.lang.String getFaxRecord(java.lang.String uid, java.lang.String pwd, java.lang.String num, java.lang.String startDate, java.lang.String endDate) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.getFaxRecord(uid, pwd, num, startDate, endDate);
  }
  
  public java.lang.String getVoiceRecord(java.lang.String uid, java.lang.String pwd, java.lang.String num, java.lang.String startDate, java.lang.String endDate) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.getVoiceRecord(uid, pwd, num, startDate, endDate);
  }
  
  public java.lang.String getMessageRecord(java.lang.String uid, java.lang.String pwd, java.lang.String num, java.lang.String startDate, java.lang.String endDate, java.lang.String isday) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.getMessageRecord(uid, pwd, num, startDate, endDate, isday);
  }
  
  public java.lang.String GET_SMS_MO(java.lang.String uid, java.lang.String pwd, java.lang.String IDtype) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.GET_SMS_MO(uid, pwd, IDtype);
  }
  
  public java.lang.String GET_MSP_MO(java.lang.String uid, java.lang.String pwd, java.lang.String IDtype) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.GET_MSP_MO(uid, pwd, IDtype);
  }
  
  public java.lang.String telCall(java.lang.String uid, java.lang.String pwd, java.lang.String host_call, java.lang.String caller) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.telCall(uid, pwd, host_call, caller);
  }
  
  
}