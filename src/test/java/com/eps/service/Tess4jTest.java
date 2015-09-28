package com.eps.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.LoadLibs;

public class Tess4jTest {
	public static void main(String[] args) {
		//System.setProperty("jna.encoding", "UTF8");
		
		File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build only; only English data bundled
        // instance.setDatapath(tessDataFolder.getAbsolutePath());
		//System.setProperty("TESSDATA_PREFIX", Tess4jTest.class.getClassLoader().getResource("tessdata").getPath());
		String imgPath = "C:/zujuan/answer/1976359.gif";
		File file = new File(imgPath);
		ITesseract tess = new Tesseract();
		tess.setDatapath(tessDataFolder.getAbsolutePath());
		tess.setLanguage("zhujuan");
		try {
			BufferedImage bi = ImageIO.read(file);
			
			//bi = ImageHelper.getScaledInstance(bi, bi.getWidth()*5, bi.getHeight()*5);
			//bi = ImageHelper.convertImageToBinary(bi);
			String result = tess.doOCR(bi);
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
