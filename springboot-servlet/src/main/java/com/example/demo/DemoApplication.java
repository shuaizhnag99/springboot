package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.demo.controller.SecondServlet;

@SpringBootApplication
@ServletComponentScan //注意使用@WebServlet、@WebFilter、@WebListener等servlet注解时需要在springboot的启动类上添加@ServletComponentScan注解，否则不会生效。
public class DemoApplication {
    @Autowired
    private SecondServlet secondServlet;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public ServletRegistrationBean<SecondServlet>  getServletRegistrationBean () {
		ServletRegistrationBean<SecondServlet>  bean=new ServletRegistrationBean<SecondServlet> ();
		bean.setServlet(secondServlet);
		bean.addUrlMappings("/second");
		return bean;
		
	}

}
