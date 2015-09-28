package com.eps.service.user;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eps.cons.MessageResourceKeys;
import com.eps.dao.MaxValueINcrementer;
import com.eps.dao.corpus.SubscribeDao;
import com.eps.dao.user.LinkDao;
import com.eps.dao.user.LoginLogDao;
import com.eps.dao.user.UserDao;
import com.eps.domain.LSubscribe;
import com.eps.domain.SLink;
import com.eps.domain.User;
import com.eps.encrypt.Encrypt_MD5;
import com.eps.exception.MailSendException;
import com.eps.mybatis.auto.UserMapper;
import com.eps.mybatis.auto.entity.UserExample;
import com.eps.service.MessageService;
import com.eps.service.sms.SMSService;
import com.eps.utils.DateHelper;
import com.eps.utils.MailSendHelper;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginLogDao logDao;	
	
	@Autowired
	private LinkDao linkDao;
	
	@Autowired
	private SMSService sms;
	
	@Autowired
	private SubscribeDao sDao;
	@Autowired
	private MaxValueINcrementer seq_userId;
	
	@Autowired
	private MailSendHelper mailSendHelper;
	
	@Value("${regist.success.mailcontent}")
	private String content;
	
	@Value("${reset.password.mailcontent}")
	private String resetPasswordContent;
	
	@Value("${authen.mailaddress.mailcontent}")
	private String authenMailContent;
	
	@Autowired
	private UserMapper userMapper;
	
	
	
	public boolean hasMatchCount(String userName,String password){
		UserExample example = new UserExample();
		UserExample.Criteria c = example.createCriteria();
		c.andUserNameEqualTo(userName);
		int count = userDao.getMatchCount(userName, password);
		return count == 1;
	}
	
	public User getUserByUserName(String userName){
		return userDao.getUserByUserNo(userName);
	}
	
	public User loadUser(int userId){
		return userDao.load(userId);
	}
	
	public void loginSuccess(User user){
		userDao.updateLoginInfo(user.getLastVisit(), user.getIp(),user.getUserId());
		logDao.saveLoginLog(user.getUserId(), user.getIp(), user.getLastVisit());
		
	}
	/**
	 * 验证注册用户名和邮箱地址是否在系统中已有
	 * @param nameOrmail
	 * @return
	 */
	public boolean hashMatchCount(String nameOrmail){
		return userDao.getMatchCount(nameOrmail) == 0;
	}
	
	/**
	 * 新用户注册
	 * @param user
	 * @return
	 */
	public boolean regist(final User user){
		try{
			user.setPassword(Encrypt_MD5.encrypt(user.getPassword()));
			int user_id = seq_userId.nextIntValue();
			user.setUserId(user_id);
			user.setRegistDate(new Date());
			user.setPhoto("images/headImage/default_head.jpg");
			userDao.saveUser(user);//插入用户基本信息
			userDao.saveUserDetail(user);        //插入用户详细信息
			userDao.initUserAccount(user.getUserId()); //创建用户金额帐户
			//20140805添加注册用户初始语料搜索权限
			LSubscribe subscribe = new LSubscribe();
			subscribe.setUserId(user.getUserId());
			subscribe.setStartDate(new Date());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, 100);
			subscribe.setEndDate(c.getTime());
			subscribe.setRemark("注册添加语料搜索时间");
			sDao.saveSubscribe(subscribe);
			//发送邮件
			try{
				Thread thread = new Thread(new Runnable() {
					public void run() {
						String htmlText = MessageFormat.format(content, user.getUserNo());
						mailSendHelper.send("注册成功", user.getMailAddress(), htmlText);
					}
				});
				thread.start();
			}catch(MailSendException e){
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 发送修改密码邮件
	 * @param nameOrEmail
	 * @param url
	 * @return
	 */
	public boolean sendResetPasswordMail(String nameOrEmail,String url){
		User user = userDao.getUserByUserNo(nameOrEmail);
		String linkNo = Encrypt_MD5.encrypt(user.getUserNo()+System.currentTimeMillis());
		SLink link = new SLink();
		link.setUserId((int) user.getUserId());
		link.setLinkNo(linkNo);
		link.setCreateDate(new Date());
		link.setLinkStatus(0);
		linkDao.createLink(link);
		String htmlText = MessageFormat.format(resetPasswordContent, user.getUserNo(),url+"/user/resetPassword.html?n="+linkNo);
		mailSendHelper.send("密码重置", user.getMailAddress(), htmlText);
		return true;
	}
	
	/**
	 * 发送邮箱认证邮件
	 * @param email
	 * @param url
	 * @return
	 */
	public boolean sendAuthenMailAddressMail(User user,String url){
		String linkNo = Encrypt_MD5.encrypt(user.getUserNo()+System.currentTimeMillis());
		SLink link = new SLink();
		link.setUserId((int) user.getUserId());
		link.setLinkNo(linkNo);
		link.setCreateDate(new Date());
		link.setLinkStatus(0);
		linkDao.createLink(link);
		String htmlText = MessageFormat.format(authenMailContent, user.getUserNo(),url+"person/center/authen-mail-last.html?n="+linkNo);
		mailSendHelper.send("邮箱认证",user.getMailAddress(),htmlText);
		return true;
	}
	public boolean updateAuthenMailStatus(User user,int status){
		return userDao.updateIsAuthenMailAddress(user.getUserId(), status) ==1;
	}
	public boolean linkHasEffective(String linkNo){
		SLink link = linkDao.getLink(linkNo);
		if(link.getLinkStatus() == 0 && DateHelper.getDateDiff(link.getCreateDate(), new Date())[0] <= 30){
			return true;
		}
		return false;
	}
	public void updateLink(String linkNo){
		SLink link = linkDao.getLink(linkNo);
		link.setUseDate(new Date());
		link.setLinkStatus(1);
		linkDao.updateLink(link);
	}
	public User getUserByLinkNo(String linkNo){
		SLink link = linkDao.getLink(linkNo);
		User user = userDao.load(link.getUserId());
		return user;
	}
	/**
	 * 修改用户密码
	 * @param password 新密码
	 * @param userId 用户Id
	 */
	public void resetPassword(String password,int userId){
		userDao.updatePassword(userId, Encrypt_MD5.encrypt(password));
	}
	
	/**
	 * 更新用户详细信息
	 */
	public void updateUserDetailInfo(User user){
		userDao.updateUserDetail(user);
	}
	
	/**
	 * 更新用户信息
	 */
	public void updateUserInfo(User user){
		userDao.updateUser(user);
	}
	
	/**
	 * 验证手机号码是否已被绑定
	 * @param phone 手机号码
	 * @return
	 */
	public boolean hasMatchPhoneCount(String phone){
		return userDao.checkPhone(phone)== 0;
	}
	
	/**
	 * 发送手机验证码
	 * @param user 用户
	 * @param phone 手机号码
	 * @return 验证码(6位随机数)
	 */
	public String sendPhoneCode(User user, String phone){
		Random random = new Random();
		String code = String.valueOf(random.nextInt(999999)%(999999-100000+1) + 100000);
		String msg = MessageService.getMessage(MessageResourceKeys.BINDPHONE_MAIL,new String[]{user.getUserNo(),code});
		boolean flag = sms.sendMessage(phone, msg);
		return code;
	}
	/**
	 * 发送邮箱验证码
	 * @param user 用户
	 * @param mailAddress 邮箱地址
	 * @return 验证码(6位随机数)
	 */
	public String sendMailCode(User user,String mailAddress){
		Random random = new Random();
		String code = String.valueOf(random.nextInt(999999)%(999999-100000+1) + 100000);
		String msg = MessageService.getMessage(MessageResourceKeys.BINDPHONE_MAIL,new String[]{user.getUserNo(),code});
		boolean flag = mailSendHelper.send("用户验证", mailAddress, msg);
		return code;
	}
	
	/**
	 * 发送教师认证结果
	 * @param user
	 * @param mailAddress
	 * @param isSuccess
	 */
	public void sendAuthenResult(User user, String mailAddress, String isSuccess){
		String msg = "";
		if (isSuccess.equals("success")) {
			msg = MessageService.getMessage(MessageResourceKeys.AUTENTEACHER_SUCCESS, new String[]{user.getUserNo()});
		} else {
			msg = MessageService.getMessage(MessageResourceKeys.AUTENTEACHER_FAILED, new String[]{user.getUserNo()});
		}
		boolean flag = mailSendHelper.send("教师认证审核结果", mailAddress, msg);
	}
}
