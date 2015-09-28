package com.eps.android.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	/*
	 * 将图片转换为Base64编码字符串
	 */
	public static String GetImageStr(String imgFilePath) {
		byte[] data = null;
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		
		if (data != null) {
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		}
		return null;
	}

	public static boolean generateImage(String imgStr,String path){
		if (imgStr == null) // 图像数据为空  
            return false;  
        try {  
            // Base64解码  
            byte[] bytes = imageStr2Byte(imgStr);
            // 生成jpeg图片  
            OutputStream out = new FileOutputStream(path);  
            out.write(bytes);  
            out.flush();  
            out.close();  
            return true;  
        } catch (Exception e) {
            return false;  
        }  
	}
	public static byte[] imageStr2Byte(String imgStr){
		if (imgStr == null) return null;// 图像数据为空 
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码 
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据 
                    bytes[i] += 256;  
                }
            }
            return bytes;
        } catch (Exception e) {
            return null;
        }
	}
}
