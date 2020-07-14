package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 整合springboot  servlet 方式二 代码注册bean
 * 需要注解@Component，但是需要在启动器里面注册该servlet 
 * 
 *  @Autowired
    private SecondServlet secondServlet;
 * 
 * @Bean
	public ServletRegistrationBean<SecondServlet>  getServletRegistrationBean () {
		ServletRegistrationBean<SecondServlet>  bean=new ServletRegistrationBean<SecondServlet> ();
		bean.setServlet(secondServlet);
		bean.addUrlMappings("/second");
		return bean;
		
	}
 * 
 * 
 * @author zhangshuai
 *
 */
@Component
public class SecondServlet extends HttpServlet {

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
		resp.getWriter().append("second servlet into");
	}
	
	

}
