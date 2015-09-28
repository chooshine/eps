package com.eps.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	/**
	 * 图片旋转
	 * @param readPath 源图片地址
	 * @param theta 旋转角度
	 * @param writePath 写入图片地址
	 * @param writeType 写入图片类型 png jpeg
	 * @throws IOException
	 */
	public static void rotate(String readPath, int theta, String writePath, String writeType) throws IOException{
		FileInputStream fs = new FileInputStream(readPath);
		BufferedImage image = ImageIO.read(fs);
		int width = image.getWidth();
		int height = image.getHeight();
		int type = image.getColorModel().getTransparency();
		int w,h,x,y;
		theta = theta%360;
		if( theta<0){
			theta = 360 + theta;  //角度转为 0-360度之间
		}
		double ang  = Math.toRadians(theta); //弧度
		if(theta == 180 || theta ==0 || theta == 360){
			w = width;
			h = height;
		}else if(theta == 90 || theta == 270){
			w = height;
			h = width;
		}else{
			 double cosVal = Math.abs(Math.cos(ang));
             double sinVal = Math.abs(Math.sin(ang));
             w = (int) (sinVal*height) + (int) (cosVal*width);
             h = (int) (sinVal*width) + (int) (cosVal*height);
		}
		 x = (w / 2) - (width / 2);//确定原点坐标 
         y = (h / 2) - (height / 2); 
		BufferedImage temp = new BufferedImage(w,h,type);
		Graphics2D gs = temp.createGraphics();
		gs.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		gs.rotate(Math.toRadians(theta), w/2, h/2);
		gs.translate(x, y);
		gs.drawImage(image, 0, 0, null);
		gs.dispose();
		FileOutputStream fo = new FileOutputStream(writePath);
		ImageIO.write(temp, writeType, fo);
		if(fs!=null) fs.close();
		if(fo!=null) fo.close();
	}
	/**
	 * 图片裁剪
	 * @param image 源图片
	 * @param x  裁剪的 x坐标
	 * @param y  裁剪的 x坐标
	 * @param width 裁剪宽度
	 * @param height 裁剪宽度
	 * @return 新图片
	 */
	public static BufferedImage crop(BufferedImage image, int x, int y, double width, double height){
		BufferedImage temp = image.getSubimage(x, y, (int)(width+0.5d), (int)(height+0.5d));
		return temp;
	}
	/**
	 * 图片裁剪
	 * @param readPath 源图片地址
	 * @param x 裁剪的x坐标
	 * @param y 裁剪的 x坐标
	 * @param w 裁剪宽度
	 * @param h 裁剪宽度
	 * @param writePath 写入图片地址
	 * @param writeType 写入图片类型
	 * @param scaleRefer 比例的参照，为width或height
	 * @param scaleLength 比例参照的长度
	 * @throws IOException
	 */
	public static void crop(String readPath, int x, int y,int w, int h, String writePath, String writeType, String scaleRefer, int scaleLength) throws IOException{
		FileInputStream fs = new FileInputStream(readPath);
		BufferedImage image = ImageIO.read(fs);
		int imgWidth = image.getWidth();
		int imgHeight = image.getHeight();
		FileOutputStream fo = new FileOutputStream(writePath);
		
		if(w==0 || h==0) {
			ImageIO.write(image, writeType, fo);
		} else {
			//按比例计算出实际图片上的x、y、width、height
			if ("width".equals(scaleRefer)) {
				x = x*imgWidth/scaleLength;
				y = y*imgWidth/scaleLength;
				w = w*imgWidth/scaleLength;
				h = h*imgWidth/scaleLength;
			} else {
				x = x*imgHeight/scaleLength;
				y = y*imgHeight/scaleLength;
				w = w*imgHeight/scaleLength;
				h = h*imgHeight/scaleLength;
			}
			
			//如果x>=原图宽度，则x=原图宽度减1，width=1；如果y>=原图高度，则y=原图高度减1，height=1
			if (x >= imgWidth) {
				x = imgWidth - 1;
				w = 1;
			}
			if (y >= imgHeight) {
				y = imgHeight - 1;
				h = 1;
			}

			BufferedImage temp = image.getSubimage(x==0?1:x, y==0?1:y, w==0?1:w, h==0?1:h);
			ImageIO.write(temp, writeType, fo);
		}
		
		if(fs!=null) fs.close();
		if(fo!=null) fo.close();
	}
	/**
	 * 给图片打上水印
	 * @param image 源图片
	 * @param x 水印的x坐标
	 * @param y 水印的y坐标
	 * @param alpha 透明度 
	 * @param w 水印宽度
	 * @param h 水印高度
	 * @param alphaImage 水印图片
	 * @return 新图片
	 */
	public static BufferedImage alphalImage(BufferedImage image, int x, int y, float alpha, int w, int h, BufferedImage alphaImage){
		//创建画笔
		Graphics2D g2d = image.createGraphics();
		//用原图填充背景
		g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null,null);
		//设置透明度
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2d.setComposite(ac);
		g2d.drawImage(alphaImage, x, y, w, h, null, null);
		g2d.dispose();
		return image;
	}
	public static void main(String[] args) {
		
	}
}
