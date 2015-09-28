package com.eps.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	@RequestMapping(value="/cameraer.html")
	public String showCamera(){
		
		return "camtest";
	}
}
