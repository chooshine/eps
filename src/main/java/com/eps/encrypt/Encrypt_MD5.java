package com.eps.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.Assert;

public class Encrypt_MD5 {
	public final static String encrypt(String str) {
//		if (str == null || str.length() == 0) {
//			throw new IllegalArgumentException("String to encript cannot be null or zero length");
//		}
		Assert.hasText(str);
		StringBuffer hexString = new StringBuffer();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();
			
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				}				
				else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}				
			}
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hexString.toString();
    }
	public static void main(String[] args) {
		System.out.println(Encrypt_MD5.encrypt("123456"));
	}
}
