package com.example.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HellowordController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		String a=null;
		a.length();
		return "hello spring boot";
	}
	
	@RequestMapping("/hello1")
	@ResponseBody
	public String hello1(){
		int a=2/0;
		return "hello spring boot";
	}
	
	@RequestMapping("/hello2")
	@ResponseBody
	public String hello2(){
		int [] a= {1,2,3};
		int b =a[3];
		return "hello spring boot";
	}
	
}
