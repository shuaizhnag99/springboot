package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import com.example.demo.filter.SecondFilter;

@SpringBootApplication
//@ServletComponentScan //注意使用@WebServlet、@WebFilter、@WebListener等servlet注解时需要在springboot的启动类上添加@ServletComponentScan注解，否则不会生效。
public class DemoApplication {
	@Autowired
	private SecondFilter secondFilter;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<SecondFilter> getFilterRegistrationBean(){
		FilterRegistrationBean<SecondFilter> bean =new FilterRegistrationBean<SecondFilter>();
		bean.setFilter(secondFilter);
		bean.addUrlPatterns("/*");
		bean.setOrder(1);  //值越小，Filter越靠前。
		
		return bean;
	}
	 //如果有多个Filter，再写一个public FilterRegistrationBean registerOtherFilter(){...}即可。
}
