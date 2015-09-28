package com.eps.utils;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

public class WavUtil {
	
	public static void wavToMp3(String sourcePath,String targetPath){
		Encoder encoder = new Encoder();
		File source = new File(sourcePath);
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();  
		audio.setCodec("libmp3lame");  
		audio.setBitRate(new Integer(128000));  
		audio.setChannels(new Integer(2));  
		audio.setSamplingRate(new Integer(44100));  
		EncodingAttributes attrs = new EncodingAttributes();  
		attrs.setFormat("mp3");  
		attrs.setAudioAttributes(audio);
		try {
			encoder.encode(source, target, attrs);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String sourcePath = "C:/Users/adminPC/workspace/eps/target/eps-0.0.1-SNAPSHOT/wav/000001_1426135530321.wav";
		String targetPath = "C:/Users/adminPC/workspace/eps/target/eps-0.0.1-SNAPSHOT/wav/000001_1426135530321.mp3";
		WavUtil.wavToMp3(sourcePath, targetPath);
	}
}
