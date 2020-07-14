package com.ule.uhj.app.yzs.util;

import java.util.HashMap;
import java.util.Map;

import com.ule.uhj.app.yzs.constant.YzsConstants;

public class ResultUtil {

	/**
	 * 生成默认的成功 map
	 * @return
	 */
	public static Map<String, Object> successMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(YzsConstants.CODE, YzsConstants.SUCCESS_CODE);
		map.put(YzsConstants.MESSAGE, YzsConstants.SUCCESS_MSG);
		map.put(YzsConstants.DATA, null);
		
		return map;
	}
	
	/**
	 * 默认失败的 map
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> failureMap(String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(YzsConstants.CODE, YzsConstants.FAILURE_CODE);
		map.put(YzsConstants.MESSAGE, msg);
		map.put(YzsConstants.DATA, null);
		
		return map;
	}
}
