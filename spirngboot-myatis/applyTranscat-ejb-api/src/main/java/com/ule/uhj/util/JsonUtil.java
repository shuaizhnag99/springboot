package com.ule.uhj.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 */
public class JsonUtil {

	/**
	 * @Title: getObjectFromJsonString
	 * @param jsonString 
	 * @param pojoCalss
	 * @param pojo对象
	 * @return Object 
	 */
	public static Object getObjectFromJsonString(String jsonString,	Class<?> pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 对象转换为json字符串
	 * @param javaObj
	 * @return json String 
	 */
	public static String getJsonStringFromObject(Object javaObj) {
		JSONObject json;
		if(javaObj instanceof List){
			return JSONArray.fromObject(javaObj).toString();
		}
		json = JSONObject.fromObject(javaObj);
		return json.toString();
	}

	/**
	 * json 字符串转换为map
	 * @param jsonString
	 * @return Map<String, Object> 
	 * @throws
	 */
	public static Map getMapFromJsonString(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * map 对象转换为json 字符串
	 * @param map
	 * @return String json
	 */
	public static String getJsonStringFromMap(Map map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	/***    
     * 校验用户是否有指定的权限
     * @param usrRole 权限集合以","隔开
     * @param target 指定的权限
     * @return boolean 结果
     */
    public static boolean checkUsrRole(String usrRole,String target) {
        boolean checkUsrRole=false;
        if(usrRole!=null&&target!=null){
            String usrRoleArray[]=usrRole.split(",");
            for(String str:usrRoleArray){
                if(str.trim().equals(target.trim())){
                    checkUsrRole=true;
                    break;
                }
            }
        }
        return checkUsrRole;
    }
}
