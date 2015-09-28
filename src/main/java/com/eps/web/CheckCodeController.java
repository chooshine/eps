package com.eps.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CheckCodeController extends BaseController{
	private final int TYPE_NUMBER = 0;    //纯数字

	private final int TYPE_LETTER = 1;    //纯字母
 
	private final int TYPE_MULTIPLE = 2;  //字母+数字

	private int width;                    //宽度

	private int height;                   //高度

	private int count;                    //字符数量

	private int type;                     //类型

	private String validate_code;         //验证码

	private Random random;                //随机

	private Font font;

	private int line;

	private String checkCode;             //用户输入需对比验证码

	private void init() {
		width = 64;
		height = 30;
		count = 4;
		type = TYPE_MULTIPLE;
		random = new Random();
		line = 200;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode.toLowerCase();
	}
	
	@RequestMapping(value="/getCheckCode.html")
	public void getImg(HttpServletRequest request,HttpServletResponse response) throws IOException {
		init();
//		HttpServletResponse response = ServletActionContext.getResponse();
//		HttpServletRequest request = ServletActionContext.getRequest();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image.jpeg");

		String reqCount = request.getParameter("count");
		String reqWidth = request.getParameter("width");
		String reqHeight = request.getParameter("height");
		String reqType = request.getParameter("type");

		if (reqCount != null && reqCount != "")
			this.count = Integer.parseInt(reqCount);
		if (reqWidth != null && reqWidth != "")
			this.width = Integer.parseInt(reqWidth);
		if (reqHeight != null && reqHeight != "")
			this.height = Integer.parseInt(reqHeight);
		if (reqType != null && reqType != "")
			this.type = Integer.parseInt(reqType);

		font = new Font("Courier New", Font.BOLD, width / count);

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < line; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		g.setFont(font);
		validate_code = getValidateCode(count, type).toLowerCase();
		request.getSession().setAttribute(CHECKCODE, validate_code);
		for (int i = 0; i < count; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			int x = (int) (width / count) * i;
			int y = (int) ((height + font.getSize()) / 2) - 5;
			g.drawString(String.valueOf(validate_code.charAt(i)), x, y);
		}

		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	private Color getRandColor(int from, int to) {
		Random random = new Random();
		if (to > 255)
			from = 255;
		if (to > 255)
			to = 255;
		int rang = Math.abs(to - from);
		int r = from + random.nextInt(rang);
		int g = from + random.nextInt(rang);
		int b = from + random.nextInt(rang);
		return new Color(r, g, b);
	}
	
	@RequestMapping(value="/ajax/validateCode.json")
	public String validateCode(HttpServletRequest request,String checkcode, ModelMap mm) {
		String code = (String) request.getSession().getAttribute(CHECKCODE);
		mm.put(SUCCESS_MSG_KEY, true);
		if(!(StringUtils.lowerCase(checkcode).equals(StringUtils.lowerCase(code)))){
			mm.put(SUCCESS_MSG_KEY, false);
		}
		return "jsonView";
	}

	private String getValidateCode(int size, int type) {
		StringBuffer validate_code = new StringBuffer();
		for (int i = 0; i < size; i++) {
			validate_code.append(getOneChar(type));
		}
		return validate_code.toString();
	}

	private String getOneChar(int type) {
		String result = null;
		switch (type) {
		case TYPE_NUMBER:
			result = String.valueOf(random.nextInt(10));
			break;

		case TYPE_LETTER:
			result = String.valueOf((char) (random.nextInt(26) + 65));
			break;

		case TYPE_MULTIPLE:
			if (random.nextBoolean()) {
				result = String.valueOf(random.nextInt(10));
			} else {
				result = String.valueOf((char) (random.nextInt(26) + 65));
			}
			break;
		default:
			result = null;
			break;
		}
		if (result == null)
			throw new NullPointerException("获取验证码出错");
		return result;
	}
	
}
