package com.ule.uhj.zgd.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.ule.uhj.Dcoffee.core.application.DcoffeeApplicationContext;
import com.ule.uhj.Dcoffee.core.power.PowerSupply;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {

	private Log log = LogFactory.getLog(SpringContextHolder.class);
	private static ApplicationContext beans;
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringContextHolder.beans = arg0;
		PowerSupply powerSupply = beans.getBean("powerSupplyService", PowerSupply.class);
		DcoffeeApplicationContext.getDcoffeeApplicationContext().setPowerSupply(powerSupply);
		log.info("\n"+
				"------------------------------------------------------------------------\n" +
				"|**********************************************************************|\n" +
				"|**********************************************************************|\n" +
				"|**********************************************************************|\n" +
				"              DCOFFEE START SUCCESS !\n" +
				"              VERSION FOR Web  \n"+
				"              By.ZhengXin 2018.03\n"+
				"|**********************************************************************|\n" +
				"|**********************************************************************|\n" +
				"|**********************************************************************|\n" +
				"------------------------------------------------------------------------\n");
	}
}
