package com.ule.uhj.sldProxy.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHookUtil implements ApplicationContextAware {
    private Log log = LogFactory.getLog(SpringContextHookUtil.class);

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.info("————————————————————————————————————\n");
        log.info("************************************************************************\n");
        log.info("************************************************************************\n");
        log.info("              Spring applicationContext hook success.                   \n");
        log.info("************************************************************************\n");
        log.info("************************************************************************\n");
        log.info("————————————————————————————————————\n");
    }

    public Object getTargetBean(String resourceName){
        Object bean;
        try{
            bean = applicationContext.getBean(resourceName);
        }catch (BeansException e){
            log.error("找不到指定的Bean对象。",e);
            return null;
        }catch (Exception e){
        	log.error("Exception error.", e);
            return null;
        }
        return bean;
    }
}
