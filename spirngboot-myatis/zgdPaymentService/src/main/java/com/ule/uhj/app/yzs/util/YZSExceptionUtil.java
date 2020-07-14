package com.ule.uhj.app.yzs.util;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.ule.uhj.app.yzs.constant.YzsConstants;
import com.ule.uhj.app.yzs.exception.YZSException;

public class YZSExceptionUtil {

	public static Map<String, Object> handleException(Exception e){
		String msg = YzsConstants.FAILURE_MSG;
		if(e instanceof YZSException) {
			msg = e.getMessage();
		}
		else if(!StringUtils.isEmpty(e.getMessage())) {
			msg = e.getMessage();
		}
		Map<String, Object> map = ResultUtil.failureMap(msg);
		return map;
	}
}
