package com.ule.uhj.sldProxy.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 返回的结果中至少包含 code , msg
 * 成功code 都是用0000
 * @author zhangyaou
 */
public class JsonResult implements Serializable{
	
	private static final long serialVersionUID = -8169363360709719300L;
	
	public static final String STR_SUCCESS = "{\"code\":\"0000\",\"msg\":\"success\"}";
	public static final String STR_ERROR = "{\"code\":\"1000\",\"msg\":\"error\"}";
	
	private Map<String, Object> _map = new HashMap<String, Object>();
	
	public static JsonResult getInstance(){
		return new JsonResult();
	}
	
	public static JsonResult error(){
		return getInstance().addError();
	}
	
	public static JsonResult success(){
		return getInstance().addOk();
	}
	
	public static JsonResult error(String msg){
		return getInstance().addError(msg);
	}
	
	public JsonResult add(String key, Object value){
		_map.put(key, value);
		return this;
	}
	
	public JsonResult add(Map<String, Object> map){
		_map.putAll(map);
		return this;
	}
	private Object[] emptyObjArr = new Object[]{};
	public JsonResult add(Object obj){
		if(obj != null){
			String mName = null;
			Object mValue = null;
			String key = null;
			for(Method m : obj.getClass().getMethods()){
				mName = m.getName();
				if(mName.startsWith("get") && !"getClass".equals(mName)){
					key = Character.toLowerCase(mName.charAt(3)) + mName.substring(4);
					try {
						if(!Check.isBlank(mValue = m.invoke(obj, emptyObjArr))){
							_map.put(key, Convert.toStr(mValue));
						}
					} catch (Exception e) {
						_map.put(key, "$error");
					}
				}
			}
		}
		return this;
	}
	
	public JsonResult addOk(){
		return addOk("success");
	}
	
	public JsonResult addOk(String okMsg){
		return add("code", "0000").add("msg", okMsg);
	}
	
	public JsonResult addError(){
		return addError("error");
	}
	
	public JsonResult addError(String errMsg){
		return add("code", "1000").add("msg", errMsg);
	}
	
	public String toJsonStr() throws Exception{
		return new ObjectMapper().writeValueAsString(_map);
	}
	
	public String toJsonStr(Map<String, Object> map) throws Exception{
		return new ObjectMapper().writeValueAsString(map);
	}
	
	public String toJsonp(Map<String, Object> map, String callBack) throws Exception{
		return callBack + "(" + toString() + ")";
	}
	
	public String toString(){
		try {
			return toJsonStr();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
