package com.example.spring_boot_security_demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

	@GetMapping("login")
	public String getLoginView(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}
	
	@GetMapping("homepage")
	public String getHomepageView(HttpServletRequest request, HttpServletResponse response) {
		return "homepage";
	}

	@GetMapping("students")
	public String getStudentsView() {
		return "student/students";
	}
	
	@GetMapping("registerNewStudent")
	public String getRegisterNewStudentView() {
		return "student/registerNewStudent";
	}

}
