package com.eps.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ExceptionController extends BaseController {

	@RequestMapping(value="/error/404.html")
	public String unfindedResource(HttpServletResponse response) {
		response.setHeader("refresh", "3;url=/eps/index.html");
		return "error/400";
	}
	
	@RequestMapping(value="/error/500.html")
	public String accessError(HttpServletResponse response) {
		response.setHeader("refresh", "3;url=/eps/index.html");
		return "error/500";
	}
}