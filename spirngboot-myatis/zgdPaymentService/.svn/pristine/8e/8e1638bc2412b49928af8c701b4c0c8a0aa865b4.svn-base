package com.ule.uhj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @title json公共处理类,以后json处理都调用此类
 * @version 1.0
 */
public class UhjWebJsonUtil {

	/**
	 * WriteDateUseDateFormat
	 * 日期类型数据的格式化，包括java.util.Date、java.sql.Date、java.sql.
	 * Timestamp、java.sql.Time。 defaultformat: yyyy-MM-dd HH:mm:ss
	 * WriteClassName 输出Class JSON.toJSONString(color,
	 * SerializerFeature.WriteClassName);
	 * {"@type":"java.awt.Color","r":255,"g":0,"b":0,"alpha":255}
	 * BrowserCompatible 浏览器和设备兼容 比如支持 iphone上的emoji（绘文字）
	 */
	private static String DATE_FORMART = "yyyy-MM-dd HH:mm:ss";
	private static final SerializerFeature[] SERIALIZER_FEATURE_DEFAULT = {SerializerFeature.WriteDateUseDateFormat,SerializerFeature.BrowserCompatible};
	
	public static String toJSONString(Object data) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(DATE_FORMART);
		Gson gson = builder.create();
		return gson.toJson(data);
	}

//	public static String toJSONStringWithSerializerFeature(Object data) {
//		GsonBuilder builder = new GsonBuilder();
//		builder.setDateFormat(DATE_FORMART);
//		Gson gson = builder.create();
//		return gson.toJson(data);
//	}
	
//	public static String toJSONStringWithSerializerFeatureWithNull(Object data) {
//	    GsonBuilder builder = new GsonBuilder();
//	    builder.setDateFormat(DATE_FORMART);
//	    Gson gson = builder.serializeNulls().create();
//	    return gson.toJson(data);
//	}
	
	/**
	 * 判断请求参数是否为JSON
	 * @param str
	 * @return
	 */
	public static boolean isJson(String str){
		try{
			JSONObject.parse(str);
			return true;
		}catch(Exception e){
		}
		return false;
	}
	
	/**
	 * BigDecimal 转为字符串处理
	 * @param data
	 * @return
	 */
//	public static String toJSONStringWithBigDecimal(Object data) {
//		GsonBuilder builder = new GsonBuilder();
//		builder.setDateFormat(DATE_FORMART);
//		builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
//		builder.registerTypeAdapter(BigDecimal.class, new JsonSerializer<BigDecimal>(){  
//			@Override  
//			public JsonElement serialize(BigDecimal b, Type type,  
//					JsonSerializationContext arg2) {  
//				return new JsonPrimitive(b.toString());
//			}});  
//		Gson gson = builder.create();
//		return gson.toJson(data);
//	}

//	public static String toJSONStringWithSerializerFeature2(Object data){				
//		return JSON.toJSONString(data, SERIALIZER_FEATURE_DEFAULT);		
//	}
	
//	public static String toJSONStringWithSerializerFeature(Object data,
//			SerializerFeature[] features) {
//		GsonBuilder builder = new GsonBuilder();
//		builder.setDateFormat(DATE_FORMART);
//		Gson gson = builder.create();
//		return gson.toJson(data);
//	}

	public static Object parseObject(String text) {
		return JSON.parseObject(text);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getForm(String jsonString, Class<?> cls) {
		T t = null;
		try {
			t = (T) JSON.parseObject(jsonString, cls);
		} catch (Exception e) {

		}
		return t;
	}
	/**
	 * 获取泛型的Collection Type
	 * @param jsonStr json字符串
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses 元素类型
	 */
//	public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
//
//	       ObjectMapper mapper = new ObjectMapper();
//	       mapper.setDateFormat(new SimpleDateFormat(DATE_FORMART));
//	       JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
//
//	       return mapper.readValue(jsonStr, javaType);
//	}

//    public static Object jsonToBean(String json, Class<?> cls) throws Exception {   
//    	ObjectMapper mapper = new ObjectMapper();
//    	 mapper.setDateFormat(new SimpleDateFormat(DATE_FORMART));
//    	Object vo = mapper.readValue(json, cls);   
//        return vo;   
//    }  
    
    public static Object parseObjToJson(Object obj) {
    	if (isJson(obj.toString().trim())) {
    		return parseObject(obj.toString().trim());
		}else{
			return parseObject(toJSONString(obj));
		}
	}
}
