package com.example.cn.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.cn.entity.Person;

@Controller
public class HellowordController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		return "hello spring boot";
	}
	@RequestMapping("/toadd")
 
	public String toadd(Model model){
		Person p=new Person();
		
		model.addAttribute("person", p);
		return "add";
	}
	
	@RequestMapping("/add")
	public ModelAndView add(@Valid Person person,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ModelAndView mv=new ModelAndView("add");
			 
			mv.addObject("person", person);
			return mv;
		}
		
		return new ModelAndView("ok");
	}
	
}
