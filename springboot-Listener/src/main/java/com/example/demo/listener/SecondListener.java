package com.example.demo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

/**
 * springboot z整合 listener 方式二  代码注入
 *  
 * 1 监听类添加注解  @Component 为了注入类
 * 2 在启动类里增加监听注册
 *  @Autowired
    private SecondListener secondListener;
    
 * 	@Bean
	public ServletListenerRegistrationBean<SecondListener> getServletListenerRegistrationBean() {
		ServletListenerRegistrationBean<SecondListener> bean =new ServletListenerRegistrationBean<SecondListener>(secondListener);
//		bean.setListener(secondListener);
		return bean;
	}
 * @author zhangshuai
 *
 */
@Component
public class SecondListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("SecondListener contextInitialized...");
		//获得application对象
		ServletContext application=sce.getServletContext();
		String name=application.getInitParameter("userName");
		System.out.println("SecondListener contextInitialized..."+name);
		ServletContextListener.super.contextInitialized(sce);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("SecondListener destroye...");
		
		ServletContextListener.super.contextDestroyed(sce);
	}

}
