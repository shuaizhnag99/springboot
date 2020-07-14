package com.ule.uhj.sldProxy.service;

import com.ule.uhj.sld.model.UserGeolocation;

public interface UserGeolocationService {

	/**
	 * 新增APP用户设备定位信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public abstract UserGeolocation save(UserGeolocation info) throws Exception;


}
