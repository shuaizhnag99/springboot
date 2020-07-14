package com.ule.uhj.sldProxy.service.impl;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bfd.facade.MerchantServer;
import com.ule.uhj.sldProxy.service.IApplyBrService;
import com.ule.uhj.sldProxy.util.CodeMsg;
import com.ule.uhj.sldProxy.util.Constants;
import com.ule.uhj.sldProxy.util.CreditException;
import com.ule.uhj.sldProxy.util.ServicesProperty;


@Service
public class IApplyBrServiceImpl implements IApplyBrService {

	private static final Logger LOG = Logger.getLogger("brLog");
	
	
	private final static String KEY = "BR_LOGIN_TOKEN";
	
	@Override
	public String requestMethod(String applicationId, JSONObject jso) throws CreditException,Exception {
		LOG.info("start request applicationId:[" + applicationId + "],jso=[" + jso+ "]");
		// 请求第三方登录获取tokenid
		String login_result = login(new MerchantServer());
		LOG.info("request applicationId:[" + applicationId + "],query tokenid return result:[" + login_result + "]");
		if(StringUtils.isBlank(login_result)){
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
		JSONObject loginJson = JSONObject.parseObject(login_result);
		if(!((Constants.CODE_0).equals(loginJson.getString("code")))) {// 登录不成功
			throw new CreditException(CodeMsg.SUCCESS.getCode(), login_result);
		}
		String tokenid = loginJson.getString("tokenid");
		if(StringUtils.isBlank(tokenid)) {
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
		MerchantServer ms = new MerchantServer();
		jso.put("tokenid", tokenid);
		// 请求第三方接口
		String portrait_result = ms.getApiData(jso.toString(), ServicesProperty.BR_API_CODE);//调用报告接口，获取结果。
		// 处理返回结果
		if(StringUtils.isBlank(portrait_result)) {// 为空  返回无数据
			throw new CreditException(CodeMsg.RETURN_NULL.getCode(), CodeMsg.RETURN_NULL.getMsg());
		}
		JSONObject json = JSONObject.parseObject(portrait_result);
		String code = json.getString("code");
		LOG.info("request applicationId:[" + applicationId + "],query credit report return code:[" + code + "],return data:[" + json + "]");
		return portrait_result;
	}
	
	/**
	 * 登录获取tokenid
	 * @param ms
	 * @return
	 * @throws CreditException 
	 * @throws Exception
	 */
	private String login(MerchantServer ms) throws CreditException {
		// 登录获取tokenid；
		try {
			return ms.login(ServicesProperty.BR_USERNAME,ServicesProperty.BR_PASSWORD, ServicesProperty.BR_API_CODE);
		} catch (Exception e) {
			LOG.error("request login,Exception:",e);
			throw new CreditException(CodeMsg.ERROR.getCode(), CodeMsg.ERROR.getMsg());
		}
	}
}
