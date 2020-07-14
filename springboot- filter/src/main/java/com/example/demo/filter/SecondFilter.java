package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * springboot 整合 Filter 方式二  基于代码注册的整合fileter  启动类需要添加注册代码 
 * filter 增加  @Component  注解方便启动类注入filter
 * 
 *  @Autowired
	private SecondFilter secondFilter;	
	
	
    @Bean
	public FilterRegistrationBean<SecondFilter> getFilterRegistrationBean(){
		FilterRegistrationBean<SecondFilter> bean =new FilterRegistrationBean<SecondFilter>();
		bean.setFilter(secondFilter);
		bean.addUrlPatterns("/*");
		return bean;
	}
	
 * setFilter 添加filter  addUrlPatterns 为要拦截的请求
 * @author zhangshuai
 *
 */
@Component
public class SecondFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("second filter into ");
		HttpServletRequest re=(HttpServletRequest)request;
		HttpServletResponse respon=(HttpServletResponse)response;
		String par=re.getParameter("par");
		System.out.println("par is "+par);
		String token=re.getHeader("token");
		if(StringUtils.isEmpty(token)) {
			System.out.println("second filter toeken is null ");
			respon.getWriter().append("second filter toeken is null,not pass");
		}else {
			System.out.println("toeken is "+token);
			System.out.println("second file enter ");
			chain.doFilter(request, response);
		}
		
	}

}
