package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * spring boot 整合servlet 方式一
 * 注册
 * 需要在servlet 上标注 name 和 url  且在spring boot 启动器增加 注解@ServletComponentScan 启动器启动时，扫描本目录以及子目录带有的webservlet注解的
 * @author zhangshuai
 *
 */
@WebServlet(name="firstServlet",urlPatterns="/firstServlet")
public class FirstServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.print("----------servlet----into ---------------");
		resp.getWriter().append("first servlet into");
	}
	
	

}
