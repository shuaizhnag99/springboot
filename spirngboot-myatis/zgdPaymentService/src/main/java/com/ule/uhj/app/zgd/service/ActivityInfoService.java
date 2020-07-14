package com.ule.uhj.app.zgd.service;

import com.ule.uhj.app.zgd.model.ActivityInfo;

/**
 * 浙江活动信息service
 * @author weisihua
 * @date 2018年2月1日
 */
public interface ActivityInfoService {

	/**
	 * 保存活动信息
	 * @param activity
	 * @throws Exception
	 */
	void saveActivityInfo(ActivityInfo activity) throws Exception;
}
