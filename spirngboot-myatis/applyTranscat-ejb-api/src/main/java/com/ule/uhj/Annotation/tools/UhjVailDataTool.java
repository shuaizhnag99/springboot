package com.ule.uhj.Annotation.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

//import com.ule.jboss.util.StringUtils;
import com.ule.uhj.Annotation.Vaildata;
import com.ule.uhj.util.Convert;

public class UhjVailDataTool{
	public static <T> boolean vailData(T obj) throws Exception{
		for(Field field : obj.getClass().getDeclaredFields()){
			field.setAccessible(true);
			Annotation annotation = field.getAnnotation(Vaildata.class);
			if(annotation==null) continue;
			Method errorMsg = annotation.getClass().getMethod("errorMsg");
			Method nullAble = annotation.getClass().getMethod("nullable");
			Boolean nullable = (Boolean)nullAble.invoke(annotation);
			if(!nullable && (field.get(obj)==null)){
				throw new Exception(obj.getClass().getName()+" "+ obj.toString() +" "+field.getName()+":"+errorMsg.invoke(annotation).toString());
			}else if(nullable && field.get(obj)==null){
				Method external = annotation.getClass().getMethod("external");
				Method externalValue = annotation.getClass().getMethod("externalValue");
				String externalColumn = Convert.toStr(external.invoke(annotation),"");
				String externalColumnValue = Convert.toStr(externalValue.invoke(annotation),"");
				if(StringUtils.isNotBlank(externalColumnValue) && StringUtils.isNotBlank(externalColumn)){
					Field externalField = null;
					try {
						externalField = obj.getClass().getDeclaredField(externalColumn);
					}catch (Exception e) {
						throw new Exception(obj.getClass().getName()+" "+ obj.toString() +" "+field.getName()+":"+"依赖的字段"+externalColumn+"不存在！");
					}

					if(externalField==null){
						throw new Exception(obj.getClass().getName()+" "+ obj.toString() +" "+field.getName()+":"+"依赖的字段"+externalColumn+"不存在！");
					}
					externalField.setAccessible(true);

					if(externalColumnValue.equals(Vaildata.NOT_NULL) && StringUtils.isNotBlank(Convert.toStr(externalField.get(obj), ""))){
						throw new Exception(obj.getClass().getName()+" "+ obj.toString() +" "+field.getName()+":"+errorMsg.invoke(annotation).toString());
					}

					if(Convert.toStr(externalField.get(obj),"").equals(externalColumnValue)){
						throw new Exception(obj.getClass().getName()+" "+ obj.toString() +" "+field.getName()+":"+errorMsg.invoke(annotation).toString());
					}
				}
				continue;
			}
//			if(field.get(obj).getClass().getSimpleName().equals(String.class.getSimpleName())){
			if(String.class.isAssignableFrom(field.get(obj).getClass())){	
				Method method = annotation.getClass().getMethod("maxLength");
				Integer maxLength = (Integer) method.invoke(annotation);
				if(maxLength!=null && maxLength>0){
					String fieldValue = (String)field.get(obj);
					if(fieldValue.length()>maxLength) {
						throw new Exception(obj.getClass().getName()+" "+ maxLength +" "+field.getName()+":"+errorMsg.invoke(annotation).toString());
					}
				}
				method = annotation.getClass().getMethod("minLength");
				Integer minLength = (Integer) method.invoke(annotation);
				if(minLength!=null && minLength>0){
					String fieldValue = (String)field.get(obj);
					if(fieldValue.length()<minLength) {
						throw new Exception(obj.getClass().getName()+" "+minLength+" "+field.getName()+":"+errorMsg.invoke(annotation).toString());
					}
				}
				
			}
		}
		return true;
	}
	
	/*
	public static void main(String[] args){
		ApplyDetailDto dto = new ApplyDetailDto();
		dto.setUserName("我就是要写个超过10位的用户名！");
		try {
			boolean b = UhjVailDataTool.vailData(dto);
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
