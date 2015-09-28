package com.eps.exception;

public class MailSendException extends RuntimeException{
	public MailSendException(String msg){
		super(msg);
	}
}
