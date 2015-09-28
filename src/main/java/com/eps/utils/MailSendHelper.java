package com.eps.utils;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSendHelper {
	
	private JavaMailSenderImpl mailSend;
	
	private String from;
	
	private String personal;
	
	public boolean send(String subject,String to, String content){
		MimeMessage msg = mailSend.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, false,"UTF-8");
			helper.setFrom(new InternetAddress(from,personal));
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			mailSend.send(msg);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
			//return false;
		}
		return false;
	}

	public JavaMailSenderImpl getMailSend() {
		return mailSend;
	}

	public void setMailSend(JavaMailSenderImpl mailSend) {
		this.mailSend = mailSend;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}
	
}
