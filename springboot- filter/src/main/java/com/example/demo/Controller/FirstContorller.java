package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstContorller {

	@RequestMapping("/hello")
	public String hello() {
		return "hello this is RestController back";
	}
}
