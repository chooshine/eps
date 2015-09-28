package com.eps.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver {
	Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		exception.printStackTrace();
		logger.error(exception.getLocalizedMessage());
		response.setHeader("refresh", "3;url=/eps/index.html");
		return new ModelAndView("error/500");
	}

}
