package com.eps.web.examsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.eps.service.examsystem.ExamAfterService;
import com.eps.web.BaseController;

@Controller
public class ExamAfterController extends BaseController{

	
	@Autowired
	private ExamAfterService eas;
	
}
