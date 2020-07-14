package com.example.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.example.boot.entity.MgtUser;
import com.example.boot.service.MgtUserService;

@Controller
public class MgtUserController {
	@Autowired
	private MgtUserService mgtUserService;
	
	@RequestMapping("/all")
	public String queryAll(Model modle) {
		List<MgtUser> users=mgtUserService.selectAll();
		modle.addAttribute("users", users);
		return "userlist";
	}
	@RequestMapping("/toEdit")
    public String edit(HttpServletRequest req,Model modle) {
		 MgtUser user=new MgtUser();
		 String username=req.getParameter("username");
		 if(!StringUtils.isEmpty(username))
			 user.setUsername(username);
		 String id=req.getParameter("id");
		 if(!StringUtils.isEmpty(id))
			 user.setId(Integer.valueOf(id));
		 String password=req.getParameter("password");
		 if(!StringUtils.isEmpty(password))
			 user.setPassword(password);

		 String permissions=req.getParameter("permissions");
		 if(!StringUtils.isEmpty(permissions))
			 user.setPermissions(Integer.valueOf(permissions));

		 
		modle.addAttribute("user",user);
    	return "edit";
    }
}
