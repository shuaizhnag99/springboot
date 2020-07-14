package com.ule.uhj.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.app.zgd.model.ApplyDetail;

public class MapAndObject {
	
	  public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
	        if (map == null)   
	            return null;    
	   
	        Object obj = beanClass.newInstance();  
	   
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            Method setter = property.getWriteMethod();    
	            if (setter != null) {  
	                setter.invoke(obj, map.get(property.getName()));   
	            }  
	        }  
	   
	        return obj;  
	    }    
	       
	    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
	        if(obj == null)  
	            return null;      
	   
	        Map<String, Object> map = new HashMap<String, Object>();   
	   
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
	        for (PropertyDescriptor property : propertyDescriptors) {    
	            String key = property.getName();  
	            //默认PropertyDescriptor会有一个class对象，剔除之
	          if (key.compareToIgnoreCase("class") == 0) {   
	                continue;  
	            }  
	            Method getter = property.getReadMethod();  
	            Object value = getter!=null ? getter.invoke(obj) : null;  
	            map.put(key, value);  
	        }    
	   
	        return map;  
	    }
	    
	    public static void main(String args[]) throws Exception{
	    	ApplyDetail ad=new ApplyDetail();
	    	ad.setId("100001");
	    	ad.setAge((short) 20);
	    	ad.setAveDgGmv(new BigDecimal(1000));
	    	ad.setZgTelNo("136666666");
	    	 System.out.println("ad....."+JSONObject.toJSONString(ad));
	    	 Map<String, Object> map=objectToMap(ad);
	    	 System.out.println("map===="+JSONObject.toJSONString(map));
	    	 System.out.println("map===="+map.get("applyTime"));
	    	 ApplyDetail ad1=(ApplyDetail) mapToObject(map, ApplyDetail.class);
	    	 System.out.println("ad1===="+JSONObject.toJSONString(ad1));
	    	 System.out.println("ad1===="+ad1.getApplyTime());
	    	 
	    	 
	    }

}
