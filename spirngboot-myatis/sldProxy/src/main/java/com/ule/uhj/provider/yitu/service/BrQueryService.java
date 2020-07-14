package com.ule.uhj.provider.yitu.service;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.bfd.facade.MerchantServer;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.CodeMsg;
import com.ule.uhj.sldProxy.util.Constants;
import com.ule.uhj.sldProxy.util.CreditException;
import com.ule.uhj.sldProxy.util.ServicesProperty;

public class BrQueryService {
	protected static Log log = LogFactory.getLog(BrQueryService.class);

	public String queryBankFourInfo(Map<String, Object> param) throws Exception {
		log.info("queryBankFourInfo param:" + param);
		String BRresult = "";
		String cid = Convert.toStr(param.get("id"));
		String cell = Convert.toStr(param.get("cell"));
		String name = Convert.toStr(param.get("name"));
		String bankId = Convert.toStr(param.get("bankId"));
		Long start = System.currentTimeMillis();
		String applicationId = "";
		try {
			applicationId = UUID.randomUUID().toString().replaceAll("-", "");
		} catch (Exception e) {
			log.error("UUID Exception:", e);
			applicationId = start + "";
		}
		JSONObject reqData = new JSONObject();
		JSONObject jso = new JSONObject();
		try {
			reqData.put("id", cid);
			reqData.put("cell", cell);
			reqData.put("name", name);
			reqData.put("bank_id", bankId);
			reqData.put("meal", Constants.BANK_FOUR);
			jso.put("apiName", Constants.FRAUD_API_NAME);
			jso.put("reqData", reqData);

			BRresult = this.requestMethod(applicationId, jso);
			// BRresult = applyBrService.requestMethod(applicationId, jso);
			log.info("百融征信：银行卡四要素查询返回结果=" + BRresult);
		} catch (CreditException e) {
			BRresult = creditExceptionResult(e);
		}
		return BRresult;
	}

	public String creditExceptionResult(CreditException e) {
		JSONObject returnJso = new JSONObject();
		if ((CodeMsg.SUCCESS.getCode()).equals(e.getCode())) {
			return e.getMsg();
		}
		returnJso.put("code", e.getCode());
		returnJso.put("msg", e.getMsg());
		return returnJso.toString();
	}

	private String requestMethod(String applicationId, JSONObject jso)
			throws CreditException, Exception {
		log.info("start request applicationId:[" + applicationId + "],jso=["
				+ jso + "]");
		// 请求第三方登录获取tokenid
		String login_result = login(new MerchantServer());
		log.info("request applicationId:[" + applicationId+ "],query tokenid return result:[" + login_result + "]");
		if (StringUtils.isBlank(login_result)) {
			throw new CreditException(CodeMsg.ERROR.getCode(),
					CodeMsg.ERROR.getMsg());
		}
		JSONObject loginJson = JSONObject.parseObject(login_result);
		if (!((Constants.CODE_0).equals(loginJson.getString("code")))) {// 登录不成功
			throw new CreditException(CodeMsg.SUCCESS.getCode(), login_result);
		}
		String tokenid = loginJson.getString("tokenid");
		if (StringUtils.isBlank(tokenid)) {
			throw new CreditException(CodeMsg.ERROR.getCode(),CodeMsg.ERROR.getMsg());
		}
		MerchantServer ms = new MerchantServer();
		jso.put("tokenid", tokenid);
		// 请求第三方接口
		String portrait_result = ms.getApiData(jso.toString(),ServicesProperty.BR_API_CODE);// 调用报告接口，获取结果。
		// 处理返回结果
		if (StringUtils.isBlank(portrait_result)) {// 为空 返回无数据
			throw new CreditException(CodeMsg.RETURN_NULL.getCode(),
					CodeMsg.RETURN_NULL.getMsg());
		}
		JSONObject json = JSONObject.parseObject(portrait_result);
		String code = json.getString("code");
		log.info("request applicationId:[" + applicationId
				+ "],query credit report return code:[" + code
				+ "],return data:[" + json + "]");
		return portrait_result;
	}

	/**
	 * 登录获取tokenid
	 * 
	 * @param ms
	 * @return
	 * @throws CreditException
	 * @throws Exception
	 */
	private String login(MerchantServer ms) throws CreditException {
		// 登录获取tokenid；
		try {
			return ms.login(ServicesProperty.BR_USERNAME,
					ServicesProperty.BR_PASSWORD, ServicesProperty.BR_API_CODE);
		} catch (Exception e) {
			log.error("request login,Exception:", e);
			throw new CreditException(CodeMsg.ERROR.getCode(),
					CodeMsg.ERROR.getMsg());
		}
	}

}
