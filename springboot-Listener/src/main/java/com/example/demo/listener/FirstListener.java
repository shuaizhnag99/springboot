package com.example.demo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * springboot z整合 listener 方式一 注解
 * 1 springboot 启动类添加注解@ServletComponentScan
 * 2 监听类添加注解  @WebListener
 * @author zhangshuai
 * 
 * 	按照被监听的内容划分
	监听域对象的创建或销毁
	监听域对象中的属性变化
	主要包括:	
	ServletContextListener
	ServletContextAttributeListener
	HttpSessionListener
	HttpSessionAttributeListener
	ServletRequestListener
	ServletRequestAttributeListener
	此外还有两个与session中的绑定的对象相关的监听器（对象感知监听器）
	HttpSessionBindingListener(绑定，解绑)
	HttpSeesionActivationListener（钝化，活化）（序列化，反序列化）
 *
 */
@WebListener
public class FirstListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("FirstListener contextInitialized...");
		

		ServletContextListener.super.contextInitialized(sce);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("FirstListener destroye...");
		ServletContextListener.super.contextDestroyed(sce);
	}

}
