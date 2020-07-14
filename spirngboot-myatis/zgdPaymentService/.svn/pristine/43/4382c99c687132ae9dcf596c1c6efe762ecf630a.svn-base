package com.ule.uhj.app.zgd.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.service.ContactsService;
import com.ule.uhj.app.zgd.service.CreditReturnService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerAddressService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.CustomerStoreService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/review")
public class CreditReturnController {
	private static Logger log = LoggerFactory.getLogger(CreditReturnController.class);
	
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private CustomerStoreService customerStoreService;
	
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private CreditReturnService creditReturnService;
	
	
	
	@RequestMapping("/queryReviewReturn")
	@ResponseBody
	public JSONPObject queryReviewReturn(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("saveInterface begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
//			String userOnlyId = "10000025652";
			log.info("queryReviewReturn userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			Map<String,Object> ret=creditReturnService.queryReviewReturn(map);
			log.info("queryReviewReturn userOnlyId:"+userOnlyId+";ret="+ret);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(ret));
		} catch (Exception e) {
			log.error("queryReviewReturn error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
	/**
	 * 
	 * 保存审核退回的部分店铺信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/saveReviewReturn")
	@ResponseBody
	public JSONPObject saveReviewReturn(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		log.info("saveReviewReturn begin.");
		try {
			String userOnlyId=CommonHelper.getUserOnlyId(request);
			log.info("saveReviewReturn userOnlyId="+userOnlyId);
//			String businessLicense=request.getParameter("businessLicense");//营业执照   证件表
//			
			String owner=request.getParameter("owner");//owner   店铺所有人(地址的用户类型)   1 是本人  2是配偶       地址的用户类型和地址类型( 1-借款人   3-店铺地址)来判断是本人的
//			String addressType=UhjConstant.addressType.store_address;//地址类型
			String propertyType=request.getParameter("propertyType");//店铺房产类型  0是自有 1：租赁    地址表
			String storeAddress=request.getParameter("storeAddress");//店铺地址     地址表
//			
//			String storeInner=request.getParameter("storeInner");//店铺内部照片    已经保存早图片表了不用保存
//			String storeOutside=request.getParameter("storeOutside");//店铺外部照片   已经保存早图片表了不用保存
//			
//			String incomeType=request.getParameter("incomeType");//收入类型     收入表
//			String incomeSubType=request.getParameter("incomeSubType");//收入子类型    收入表
//			String description=request.getParameter("description");//备注        收入表
//			String incomeAmount=request.getParameter("incomeAmount");//金额      收入表
//			
			String storeInnerGPS=request.getParameter("storeInnerGPS");//店铺内部GPS定位信息    店铺表
//			String storeOutsideGPS=request.getParameter("storeOutsideGPS");//店铺外部GPS定位信息     店铺表
//			String storeName=request.getParameter("storeName");//店铺名称     店铺表     要和地址表关联地址ID
//			String storeArea=request.getParameter("storeArea");//店铺面积          店铺表
//			String storeTurnover=request.getParameter("storeTurnover");//店铺营业额（每日）   店铺表
//			String rent=request.getParameter("rent");//租赁金额      店铺表
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						map.put(paramName, paramValue);
					}
				}
			}
			map.put("userOnlyId", userOnlyId);
			Map<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("userOnlyId", userOnlyId);
			addressMap.put("addressType", UhjConstant.addressType.store_address);
			addressMap.put("address", storeAddress);
			addressMap.put("customerType", owner);
			addressMap.put("propertyType", propertyType);
			addressMap.put("storeInnerGPS", storeInnerGPS);
			log.info("saveReviewReturn addressMap:"+addressMap.toString());
			String addressId=customerAddressService.saveCustomerAddress(addressMap);
			map.put("addressId", addressId);
			log.info("saveReviewReturn userOnlyId:"+userOnlyId+";map:"+map);
			customerStoreService.saveCustomerStore(map);
			creditReturnService.saveReviewReturn(map);
			//保存店铺信息过程结束
			Map<String,Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("userOnlyId", userOnlyId);
			ruleMap.put("ruleRefId", UhjConstant.ruleRefId.shops_fill);
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
			creditRuleService.saveCreditRuleService(ruleMap);
			String result =JsonResult.getInstance().addOk().toString();
			log.info("saveReviewReturn userOnlyId:"+userOnlyId+";result："+result);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("saveReviewReturn error!",e);
			String result = JsonResult.getInstance().addError("网络异常，请稍后再试！").toJsonStr();
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}
	}
	
}
