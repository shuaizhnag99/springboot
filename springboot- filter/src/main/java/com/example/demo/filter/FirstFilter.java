package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * springboot 整合 Filter 方式一  基于注解的整合fileter  启动类需要添加注解@ServletComponentScan
 * filter 需要添加注解  @WebFilter(filterName="FirstFilter",urlPatterns="/*")
 * filterName 为filter名字  urlPatterns 为要拦截的请求
 * @author zhangshuai
 *
 */
@WebFilter(filterName="FirstFilter",urlPatterns="/*" )
public class FirstFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("first filter into ");
		HttpServletRequest re=(HttpServletRequest)request;
		String par=re.getParameter("par");
		System.out.println("par is "+par);
		String token=re.getHeader("token");
		if(StringUtils.isEmpty(token)) {
			System.out.println("toeken is null");
			chain.doFilter(request, response);
		}else {
			System.out.println("toeken is "+token);
			System.out.println("file enter ");
			chain.doFilter(request, response);
		}
		
	}

}
