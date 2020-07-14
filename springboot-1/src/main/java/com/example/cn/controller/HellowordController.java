package com.example.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HellowordController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		return "hello spring boot";
	}
	
}
