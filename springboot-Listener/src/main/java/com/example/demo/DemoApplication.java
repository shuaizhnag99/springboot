package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.demo.listener.SecondListener;

@SpringBootApplication
//@ServletComponentScan
public class DemoApplication {
    @Autowired
    private SecondListener secondListener;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public ServletListenerRegistrationBean<SecondListener> getServletListenerRegistrationBean() {
		ServletListenerRegistrationBean<SecondListener> bean =new ServletListenerRegistrationBean<SecondListener>(secondListener);
//		bean.setListener(secondListener);
		return bean;
	}

}
