package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {
	
	@RequestMapping("/hello")
	@ResponseBody
	public Map<String,String> hello(){
		Map<String,String> re=new HashMap<String,String>();
		re.put("hello", "this is first spring boot porject");
		return re;
	}

}
