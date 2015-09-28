package com.eps.encrypt;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

public class Encrypt_Des {
	private Key key;
	
	public final static String GENERATEKEYSTR = "abcdefghigklmnopqrstuvwxyz";
	
	private static String ENCRYPTALGORITHM = "DES";
	
	/**
	 * 生成密钥
	 * @param keyStr
	 */
	public void initKey(String keyStr){
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ENCRYPTALGORITHM);
			generator.init(new SecureRandom(keyStr.getBytes()));
			this.key = generator.generateKey();
			generator = null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 加密
	 * @param strMing 
	 *        String 明文
	 * @return
	 *        String 密文
	 */
	public String getEncString(String strMing){
		byte[] byteMi = null;  
        byte[] byteMing = null;  
        String strMi = "";  
//        BASE64Encoder base64en = new BASE64Encoder();  
        try {  
            byteMing = strMing.getBytes("UTF8");  
            byteMi = this.getEncCode(byteMing);  
            strMi = Base64.encodeBase64String(byteMi);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
//            base64en = null;  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMi;  
	}
	/**
	 * 解密
	 * @param strMi 
	 *        String 密文
	 * @return 
	 *        String 明文
	 */
	public String getDesString(String strMi){
//		BASE64Decoder base64De = new BASE64Decoder();  
        byte[] byteMing = null;  
        byte[] byteMi = null;  
        String strMing = "";  
        try {  
            byteMi = Base64.decodeBase64(strMi);  
            byteMing = this.getDesCode(byteMi);  
            strMing = new String(byteMing, "UTF8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
//            base64De = null;  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMing;  
	}
	
	/**  
     *  为getEncString方法提供服务  
     *    
     * 加密以byte[]明文输入,byte[]密文输出  
     *   
     * @param byteS  
     *            byte[]明文  
     * @return byte[]密文  
     */  
    private byte[] getEncCode(byte[] byteS) {  
        byte[] byteFina = null;  
        Cipher cipher;  
        try {  
            cipher = Cipher.getInstance(ENCRYPTALGORITHM);  
            cipher.init(Cipher.ENCRYPT_MODE, key);  
            byteFina = cipher.doFinal(byteS);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    } 
    
    /**  
     * 为getDesString方法提供服务  
     *   
     * 解密以byte[]密文输入,以byte[]明文输出  
     *   
     * @param byteD  
     *            byte[]密文  
     * @return byte[]明文  
     */  
    private byte[] getDesCode(byte[] byteD) {  
        Cipher cipher;  
        byte[] byteFina = null;  
        try {  
            cipher = Cipher.getInstance(ENCRYPTALGORITHM);  
            cipher.init(Cipher.DECRYPT_MODE, key);  
            byteFina = cipher.doFinal(byteD);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }
    public static void main(String[] args) {
		Encrypt_Des des = new Encrypt_Des();
		des.initKey(GENERATEKEYSTR);
		System.out.println(des.getEncString("123456"));
	}
}
