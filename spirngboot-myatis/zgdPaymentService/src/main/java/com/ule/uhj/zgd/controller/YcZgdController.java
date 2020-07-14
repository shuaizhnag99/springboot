package com.ule.uhj.zgd.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.service.CreditApplyService;
import com.ule.uhj.dic.ImageStatusDic;
import com.ule.uhj.dto.zgd.ApplyDetailDto;
import com.ule.uhj.dto.zgd.ApplyImageDto;
import com.ule.uhj.dto.zgd.UserInfoDto;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.acc.AccountClient;
import com.ule.uhj.ejb.client.app.SldAppClient;
import com.ule.uhj.ejb.client.cs.ZgdCSWebClient;
import com.ule.uhj.ejb.client.pixiao.LoanInterfaceClient;
import com.ule.uhj.ejb.client.ycWelab.ZgdWelabClient;
import com.ule.uhj.ejb.client.ycZgd.SendMessageClient;
import com.ule.uhj.ejb.client.ycZgd.YCZgdClient;
import com.ule.uhj.ejb.client.ycZgd.YCZgdQueryClient;
import com.ule.uhj.ejb.client.ycZgd.YzgVpsInfoClient;
import com.ule.uhj.ejb.client.ycZgd.ZgdWhiteClient;
import com.ule.uhj.ejb.client.zgd.ZgdClient;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.SldService;
import com.ule.uhj.sld.biz.service.impl.DefaultSldService;
import com.ule.uhj.sld.constant.BRTransCodeEnum;
import com.ule.uhj.sld.model.SldOperateLog;
import com.ule.uhj.util.ApplyDto;
import com.ule.uhj.util.BeanDto;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.UserTools;
import com.ule.web.util.Tools;

@Controller
@RequestMapping("/uhj")
public class YcZgdController{
	private static Logger log = LoggerFactory.getLogger(YcZgdController.class);
	
	@Autowired
	private CreditApplyService creditApplyService;
	
	
	
	
	
	
	String noQualify = "common/noQualify";
	String ycunApply = "yc/yc_accountUnOpen";
	String freeze = "yc/yc_freeze";
	String ycaccOverView = "yc/yc_accountOverView";
	String ERROR = "common/error";
	String upLimit = "yc/yc_upLimit";
	String ycbindCard = "yc/yc_bindCard";
	String personInfo = "yc/yc_personInfo";
	//String productIntroduction = "yc/yc_productIntroduction";
	String qualification = "yc/yc_qualification";
	String yclendPage = "yc/yc_zgdLend";
	String yclendSuccess = "yc/yc_lendSuccess";
	String waitReview = "yc/yc_waitReview";
	String freeze_PC = "yc/yc_freezePC";
	String hdepage="yc/yc_hodepage";
	String guidepage="yc/yc_guidepage";
	String sysAuditResult="yc/yc_sysAuditResult";
	
	//用于页面回显，0:未逾期 1:已逾期
	String overDues = "0";
	
	/**主入口 
	 * 跳转显示账户概览以及借据信息
	 * 逻辑改动后需要注意yczgdAction里的toLendPage方法
	 * @return
	 * 
	 * applyStatus   
	 *  0：引导页面
	 * 	2：运营商授权失败页面
	 *  3：等待审核
	 *  4：审核被打回重新申请(显示打回原因) reason
	 *  5 ：审核拒绝  显示重新申请日期   reactivatedDate
	 *  6:发送邮储申请接口阻塞中
	 *  7:邮储建额失败
	 *  100：中断页面
	 *  88：支用提示页面 
	 *  77  支用页面
	 *  9999 邮掌柜用户信息提示页面
	 *  
	 *  20  地址完善提示页面
	 *  10  批销额度激活页面
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@RequestMapping("/zgd_toZgdPage")
	public String toZgdPage(HttpServletRequest request) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("springmvc toZgdPage userOnlyId:" +userOnlyId);
			//2018-04-09增加pc端批销快速建额
//			String env = PropertiesHelper.getDfs("env");
			String re=getConstantValue("pcCreateLimite");
//			log.info("toZgdPage...env"+env);(StringUtils.isNotBlank(env)&&"beta".equals(env))||
			if("true".equals(re)){
				log.info("toZgdPage into new thread......begin.....");
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("userOnlyId", userOnlyId);
				String result =creditApplyService.queryApplyStatus(map);
				log.info(String.format("useronlyid..%s,tozgdpage,reuslt..%s", userOnlyId,result));
				JSONObject js =JSONObject.fromObject(result);
				String applyStatus=Convert.toStr(js.get("applyStatus"));
				log.info(String.format("applyStatus%s", applyStatus));
				
				if("10".equals(applyStatus)){
					return guidepage;// 进入首页面
				}else if("6".equals(applyStatus)){//进入审核等待页面
					return hdepage;
				}else if("7".equals(applyStatus)){//进入审核拒绝页面
					return sysAuditResult;
				}else if("77".equals(applyStatus)){//进入支用页面
					return ycaccOverView;
				}else{//暂时不符合条件，怎不能开通进货版
					return freeze_PC;
				}
			}else{
				
				//先判断用户应该走邮乐还是邮储
//			String source=getSysSource();
				
//			if("200".equals(source)){//邮储
				String status = getYCUserStatus(userOnlyId);
				
				//101为老用户重新申请中-2017.1.11郑鑫
				if ("21".equals(status) || "11".equals(status) || "101".equals(status)) {// 已经申请成功的跳转账户概览页面（11没绑卡的状态已无用因为已经申请的都绑卡了）
//					String provice = WildflyBeanFactory.getYCZgdQueryClient().queryUserProvince(userOnlyId);
//					request.setAttribute("provice", provice);
					return ycaccOverView;
				}
				
				//申请中的判断是去手机上申请还是在电脑上申请
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("userOnlyId", userOnlyId);
				String ret = WildflyBeanFactory.getYzgVpsInfoClient().queryShowInfo(map);
				log.info("springmvc toZgdPage ret:" +ret);
				JSONObject object = JSONObject.fromObject(ret);
				if ("0000".equals(Convert.toStr(object.get("returnCode")))){
					String pcShow=Convert.toStr(object.get("pcShow"));
					if("0".equals(pcShow)){
						return freeze_PC;
					}
				}
				if("0".equals(status)){//无资质
					log.info("");
					return noQualify;
				}
				if("01".equals(status) || "011".equals(status)){
					//开通申请 01
					//开通申请 ,但已实名认证（AccountInfo的绑定账号不为空） 011   防止已经实名的再次去绑卡
//					String provice = WildflyBeanFactory.getYCZgdQueryClient().queryUserProvince(userOnlyId);
//					request.setAttribute("provice", provice);
					String reslut = WildflyBeanFactory.getYCZgdQueryClient().queryZgdWhiteLimit(userOnlyId);
					JSONObject js = JSONObject.fromObject(reslut);
					if ("0000".equals(js.get("code").toString())){
						request.setAttribute("creditLimit", js.get("creditLimit").toString());
					}
					request.setAttribute("desc", Convert.toStr(js.get("desc"), ""));
					request.setAttribute("applyStatus", status);
					return ycunApply;//邮储的未开通账户概览
				}
				if("02".equals(status)){//因为身份证的年龄问题审核被冻结开通(历史数据有这个状态，后面拒绝统一状态是5)
					return freeze;
				}
				if("03".equals(status) ){//等待审核
					return waitReview;
				}
				if("04".equals(status) ){//审核被打回（如果因为姓名问题需要被打回实名之前，点击重新申请才会打回实名之前）
					String msg="";
					String applyStatus="04";
					ApplyDetailDto param=new ApplyDetailDto();
					param.setUserOnlyId(userOnlyId);
					List<ApplyDetailDto> list=WildflyBeanFactory.getZgdQueryClient().findApplyDetail(param);
					if(null!=list&&list.size()>0){
						ApplyDetailDto applyDetailDto=list.get(0);
						if(!Check.isBlank(applyDetailDto.getStatus()) && applyDetailDto.getStatus().equals(10L)){
							applyStatus="10";
						}
						msg=applyDetailDto.getRefusalReason();//审核被打回的原因
					}
					log.info("msg:"+msg);
					request.setAttribute("applyStatus", applyStatus);
					if(Check.isBlank(msg)){
						request.setAttribute("msg", "请确认您填写的资料是否正确，上传的图片是否清晰，邮掌柜的掌柜姓名是否是您本人的。");
					}else{
						request.setAttribute("msg", msg.replaceAll(";", ";</br>"));
					}
					return freeze;
				}
				if("05".equals(status)){//审核拒绝
					ApplyDetailDto param=new ApplyDetailDto();
					param.setUserOnlyId(userOnlyId);
					String res=WildflyBeanFactory.getYCZgdQueryClient().queryUserMessage(userOnlyId);
					JSONObject js=JSONObject.fromObject(res);
					String reactivatedDate=Convert.toStr(js.get("reactivatedDate"));
					String reason=Convert.toStr(js.get("reason"));//审核被打回的原因
					if(!Check.isBlank(reason) && reason.indexOf("营业执照注册未满一年")>=0){
//						reason="营业执照注册未满一年";
					}else if (!Check.isBlank(reason) &&  reason.indexOf("Z013")>=0){
						reason="尊敬的掌柜，非常遗憾的告知您，您的掌柜贷额度低于10000元，未能通过本次审核。您可以努力提升店铺经营业绩，并保持个人良好的信用记录";
					}else{
						status="02";
					}
					if(!Check.isBlank(reactivatedDate)){
						reactivatedDate="，请于"+reactivatedDate+"再次申请掌柜贷。";
					}
					request.setAttribute("applyStatus", status);
					request.setAttribute("reason", reason);
					request.setAttribute("reactivatedDate", reactivatedDate);
					return freeze;
				}
				if("06".equals(status) ){//审核被打回后已经点击了重新申请，下次再次点击掌柜贷直接进入提交资料页面
					request.setAttribute("applyDetailDto", prePersonInfo(userOnlyId));
					return personInfo;
				}
				return noQualify;
			}
//			}else if("100".equals(source)){//邮乐
//				String status = getUserStatus();
//				if ("21".equals(status) || "11".equals(status)) {// 21 已绑卡 或者用户有卡 , 11 有资质未绑卡且用户无卡 跳转绑卡页面
//					return accOverView;
//				} else {// 无资质
//					return noQualify;
//				}
//			}else if("000".equals(source)){
//				return noQualify;
//			}
		} catch (Exception e) {
			log.error("toZgdPage  error!", e);
		}
		return ERROR;
	}
	private String getConstantValue(String key){
		String re="";
		
		try {
			Map<String, Class<?>> constantMap = new HashMap<String, Class<?>>();
			constantMap.put(key, String.class);
			ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
			Map<String, Object> conMap = zgdQueryClient.queryZgdConstantValue2(constantMap);
			List<String> provinceCodeList = (List<String>) conMap.get(key);
			log.info("showTown provinceCodeList:" + provinceCodeList.toString());
			if (null != provinceCodeList && provinceCodeList.size() > 0) {
				re = Convert.toStr(provinceCodeList.get(0));
			}
		} catch (Exception e) {
			re="";
			log.error("getConstantValue error", e);
		}
		log.info(key+"..key-value.."+re);
		return re;
	}
    /**
     * 校验掌柜贷实名认证姓名与邮掌柜姓名是否一致
     * @param request
     * @param res
     */
 
	private String checkUserName(HttpServletRequest request,ApplyDto applyDto){
		JSONObject obj = new JSONObject();
		try {
			String userOnlyId = getUserOnlyId(request);
			if(StringUtils.isNotBlank(userOnlyId)){
				//调用VPS 接口获取邮掌柜姓名
				ZgdCSWebClient csClient = WildflyBeanFactory.getZgdCSWebClient();
				Map<String, Object> map = csClient.queryUserOrg(userOnlyId);
				String yzgUserName = Convert.toStr(map.get("userName"));
				//获取 实名认证姓名
				String zgdName=applyDto.getApplyDetailDto().getUserName();
				if(StringUtils.isBlank(zgdName)){
					ZgdQueryClient client=WildflyBeanFactory.getZgdQueryClient();
					String result=client.queryUserInfoByuserOnlyId(userOnlyId);
					JSONObject js=JSONObject.fromObject(result);
					if("0000".equals(js.get("code"))){
						zgdName=js.getString("userName");
					}
				}
				
				if(!zgdName.equals(yzgUserName)){
					obj.put("code", "1100");
					obj.put("msg", "对不起，您实名认证的姓名与邮掌柜姓名不一致，请重新绑卡申请！");
				}else{
					obj.put("code", "0000");
					obj.put("msg", "验证通过");
				}				
				
			}else{
				obj.put("code", "1000");
				obj.put("msg", "登陆超时，请重新登录");
			}
		} catch (Exception e) {
			obj.clear();
			obj.put("code", "0000");
			obj.put("msg", "服务异常，请刷新页面或者重新登录");
			log.error("checkUserName 校验姓名 error",e);
		}
	 return obj.toString();
	}
	
	/**
	 * 校验掌柜贷实名认证姓名与邮掌柜姓名不一致，重新申请需要先退回实名之前，重新实名认证，绑卡
	 * @param request
	 * @param res
	 */
	@RequestMapping("/zgd_backBeforRealName")
	public void backBeforRealName(HttpServletRequest request,HttpServletResponse res){
		JSONObject obj = new JSONObject();
		try {
			String userOnlyId = getUserOnlyId(request);
			if(StringUtils.isNotBlank(userOnlyId)){
				log.info(String.format("%s申请掌柜贷时掌柜实名认证时姓名与邮掌柜姓名不一致，退回实名认证之前", userOnlyId));
				 Map<String, Object> map=new HashMap<String, Object>();
				 map.put("userOnlyId", userOnlyId);
				 map.put("loginName", userOnlyId);
				 map.put("refusalReason", "您实名认证的姓名与邮掌柜姓名不一致，已经退回实名认证之前，请重新绑卡申请！");
				 map.put("namecheck", "notpass");//申请掌柜贷时掌柜实名认证时姓名与邮掌柜姓名不一致.
				ZgdWelabClient client = WildflyBeanFactory.getZgdWelabClient();
				client.backBeforeRealName(map);
				 obj.put("code", "0000");
				 obj.put("msg", "sucess");
				 		
				
			}else{
				obj.put("code", "1000");
				obj.put("msg", "登陆超时，请重新登录");
			}
		} catch (Exception e) {
			obj.clear();
			obj.put("code", "1000");
			obj.put("msg", "服务异常，请刷新页面或者重新登录");
		}
		try {
			res.setContentType("text/plain;charset=UTF-8");
			res.getWriter().write(obj.toString()); //返回jsonp数据
			log.info("backBeforRealName end:"+obj.toString());
		} catch (IOException e) {
			log.error("backBeforRealName error",e);
		}
	}
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
		return usronlyId;
	}

	/**
	 * 查询用户状态
	 * @return
	 * 用户无掌柜贷资质 0
	 * 用户已冻结 10
	 * 用户有掌柜贷资质 11
	 * 用户已绑卡 21 
	 * @throws Exception
	 */
	private String getYCUserStatus(String userOnlyId) throws Exception{
		String flag = WildflyBeanFactory.getYCZgdQueryClient().queryZgdUserUseStatus(userOnlyId);
		JSONObject js = JSONObject.fromObject(flag);
		if ("0000".equals(js.get("code").toString())){
			log.info("user " + userOnlyId + ", queryStatus " + flag);
			return js.get("status").toString();
		}else{
			throw new Exception("query user status error!" + flag);
		}
	}
	
	/**
	 * applyDetailDto 处理图片和省市地址
	 * @param dto
	 * @return
	 */
	private ApplyDetailDto handleDetailDto(ApplyDetailDto dto,List<ApplyImageDto> list){
		if(dto!=null){
			if(!Tools.isEmpty(dto.getKeeperProvince()))
					dto.setKeeperProvince(UserTools.subStrBeforeSymbol(dto.getKeeperProvince(), "-"));
			if(!Tools.isEmpty(dto.getKeeperCity()))
				dto.setKeeperCity(UserTools.subStrBeforeSymbol(dto.getKeeperCity(), "-"));
			if(!Tools.isEmpty(dto.getKeeperArea()))
				dto.setKeeperArea(UserTools.subStrBeforeSymbol(dto.getKeeperArea(), "-"));
			if(!Tools.isEmpty(dto.getStoreProvince()))
				dto.setStoreProvince(UserTools.subStrBeforeSymbol(dto.getStoreProvince(), "-"));
			if(!Tools.isEmpty(dto.getStoreCity()))
				dto.setStoreCity(UserTools.subStrBeforeSymbol(dto.getStoreCity(), "-"));
			if(!Tools.isEmpty(dto.getStoreArea()))
				dto.setStoreArea(UserTools.subStrBeforeSymbol(dto.getStoreArea(), "-"));
			if(!Tools.isEmpty(dto.getHouseholdRegisterProvice()))
				dto.setHouseholdRegisterProvice(UserTools.subStrBeforeSymbol(dto.getHouseholdRegisterProvice(), "-"));
		    if(!Tools.isEmpty(dto.getHouseholdRegisterCity()))
			  dto.setHouseholdRegisterCity(UserTools.subStrBeforeSymbol(dto.getHouseholdRegisterCity(), "-"));
		    if(!Tools.isEmpty(dto.getHouseholdRegisterArea()))
			  dto.setHouseholdRegisterArea(UserTools.subStrBeforeSymbol(dto.getHouseholdRegisterArea(), "-"));
		    String repflag="_78x78";
		    dto=getImage(dto, list);
			if (!Tools.isEmpty(dto.getBusLiecnceImg()))
				dto.setBusLiecnceImg(UserTools.insterStrBeforeSymbol(dto.getBusLiecnceImg(),".",repflag));
			if (!Tools.isEmpty(dto.getStoreImg()))
				dto.setStoreImg(UserTools.insterStrBeforeSymbol(dto.getStoreImg(),".",repflag));
			if (!Tools.isEmpty(dto.getStoreInnerImg()))
				dto.setStoreInnerImg(UserTools.insterStrBeforeSymbol(dto.getStoreInnerImg(),".",repflag));
			if (!Tools.isEmpty(dto.getCertHoldImg()))
				dto.setCertHoldImg(UserTools.insterStrBeforeSymbol(dto.getCertHoldImg(),".",repflag));
			if (!Tools.isEmpty(dto.getCertPositiveImg()))
				dto.setCertPositiveImg(UserTools.insterStrBeforeSymbol(dto.getCertPositiveImg(),".",repflag));
			if (!Tools.isEmpty(dto.getCertNegativeImg()))
				dto.setCertNegativeImg(UserTools.insterStrBeforeSymbol(dto.getCertNegativeImg(),".",repflag));
			if (!Tools.isEmpty(dto.getStorePropertyUrl()))
				dto.setStorePropertyUrl(UserTools.insterStrBeforeSymbol(dto.getStorePropertyUrl(),".",repflag));
		}
		
		return dto;
		
	}
	/**
	 * 从掌柜图片信息表中获取图片信息
	 * @param dto
	 * @param list
	 * @return
	 */
	private ApplyDetailDto getImage(ApplyDetailDto dto,List<ApplyImageDto> list){
		   if(null!=list&&list.size()>0){
			   String [] imagGroupIds={ImageStatusDic.BUSLIECNCE_ERROR.getGroupId(),
					   ImageStatusDic.STORE_ERROR.getGroupId(),
					   ImageStatusDic.STORE_INNER_ERROR.getGroupId(),
					   ImageStatusDic.CARD_HOLD_ERROR.getGroupId(),
					   ImageStatusDic.CARD_POS_ERROR.getGroupId(),
					   ImageStatusDic.CARD_NEG_ERROR.getGroupId(),
					   ImageStatusDic.STORE_PROPERTY_ERROR.getGroupId()};
			   
		    	for(ApplyImageDto dt: list){
		    		 if(imagGroupIds[0].equals(dt.getImageType())){
		    			 dto.setBusLiecnceImg(dt.getImageUrl());
		    		 }else if(imagGroupIds[1].equals(dt.getImageType())){
		    			 dto.setStoreImg(dt.getImageUrl());
		    		 }else if(imagGroupIds[2].equals(dt.getImageType())){
		    			 dto.setStoreInnerImg(dt.getImageUrl());
		    		 }else if(imagGroupIds[3].equals(dt.getImageType())){
		    			 dto.setCertHoldImg(dt.getImageUrl());
		    		 }else if(imagGroupIds[4].equals(dt.getImageType())){
		    			 dto.setCertPositiveImg(dt.getImageUrl());
		    		 }else if(imagGroupIds[5].equals(dt.getImageType())){
		    			 dto.setCertNegativeImg(dt.getImageUrl());
		    		 }else if(imagGroupIds[6].equals(dt.getImageType())){
		    			 dto.setStorePropertyUrl(dt.getImageUrl());
		    		 }
		    		 
		    	}
		    }
		return dto;
	}
	
	/**产品介绍
	 * @return
	 */
	@RequestMapping("/yczgd_upLimit")
	public String upLimit(HttpServletRequest request,Model model) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("upLimit userOnlyId=" + userOnlyId);
			String reslut = WildflyBeanFactory.getYCZgdQueryClient().queryZgdWhiteLimit(userOnlyId);
			JSONObject js = JSONObject.fromObject(reslut);
			if ("0000".equals(js.get("code").toString())){
				request.setAttribute("creditLimit", js.get("creditLimit").toString());
				request.setAttribute("jxc", js.get("jxc").toString());
				request.setAttribute("dg", js.get("dg").toString());
				request.setAttribute("pf", js.get("pf").toString());
				request.setAttribute("czjf", js.get("czjf").toString());
				request.setAttribute("huiYuan", js.get("huiYuan").toString());
				request.setAttribute("wenDingXing", js.get("wenDingXing").toString());
				request.setAttribute("other", js.get("other").toString());
				request.setAttribute("maxjxc", js.get("maxjxc").toString());
				request.setAttribute("maxdg", js.get("maxdg").toString());
				request.setAttribute("maxczjf", js.get("maxczjf").toString());
				request.setAttribute("maxpf", js.get("maxpf").toString());
			}
			return upLimit;
		} catch (Exception e) {
			log.error("upLimit error",e);
			return ERROR;
		}
	}
	
	/***
	 * 掌柜贷用户激活时间检验
	 * @return
	 */
	@RequestMapping("/zgd_zgdActiveTimeCheck")
	@ResponseBody
	public JSONPObject zgdActiveTimeCheck(HttpServletRequest request,@RequestParam String jsoncallback){
		log.info("zgdActiveTimeCheck begin.");
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			ZgdWhiteClient client = (ZgdWhiteClient)WildflyBeanFactory.getZgdWhiteClient();
			String result = client.userActiveTimeCheck(getUserOnlyId(request));
//			res.setContentType("text/plain;charset=UTF-8");
			//res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据 
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("zgdActiveTimeCheck error!",e);
			return new JSONPObject(jsoncallback, e);
//			return ERROR;
		}
	}
	
	/**
	 * 显示用户的基本信息（VPS）
	 * @return
	 */
	@RequestMapping("/yczgd_auditUserInfo")
	@ResponseBody
	public JSONPObject auditUserInfo(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("auditUserInfo  userOnlyId="+userOnlyId);
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryVPSUserInfo(userOnlyId);
			//			String result =JsonResult.getInstance().addOk().add("flag", "0000").add("certNo", "320602195011111111").add("name", "吴元之").add("userOnlyId", "10000000391").toJsonStr();
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("auditUserInfo Error", e);
			return new JSONPObject(jsoncallback, e);
		}
	}
	
	/**用户确认信息有误
	 * @return
	 */
//	public String auditUserInfoError(HttpServletRequest request) {
//		try {
//			String userOnlyId=getUserOnlyId(request);
//			log.info("auditUserInfoError  userOnlyId="+userOnlyId);
//			String result = WildflyBeanFactory.getYCZgdQueryClient().auditUserInfoError(userOnlyId);
//			String returnJson = request.getParameter("jsoncallback") + "(" + result + ")";
//			log.info("auditUserInfoError result:"+returnJson);
//			return "userInfoError";
//		} catch (Exception e) {
//			log.error("auditUserInfoError Error", e);
//			return ERROR;
//		}
//	}
	
	/**
	 * 用户确认基本信息后
	 * 判断掌柜是否绑定过银行卡
	 * 		N,进入绑卡页面0002  preBindCard()
	 * 		Y, 进入后续页面,弹出弹窗选择贷款卡号0003   selectCardNo()
	 * @return
	 */
	@RequestMapping("/yczgd_checkUserInfo")
	@ResponseBody
	public JSONPObject checkUserInfo(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId=getUserOnlyId(request);
			String certNo = request.getParameter("certNo");
			log.info("checkUserInfo  userOnlyId="+userOnlyId);
			String result = WildflyBeanFactory.getYCZgdQueryClient().checkUserInfo(userOnlyId,certNo);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			//			String result=JsonResult.getInstance().add("code", "0002").toString();
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("checkUserInfo Error", e);
			return new JSONPObject(jsoncallback, e);
		}
	}
	
	/**
	 * 将因年龄问题白名单冻结后跳转
	 * @return
	 */
	@RequestMapping("/yczgd_removeYcZgdWhite")
	public String removeYcZgdWhite() {
		try {
			return freeze;
		} catch (Exception e) {
			log.error("removeYcZgdWhite Error", e);
			return ERROR;
		}
	}
	
	/**
	 *  用户确认基本信息后，掌柜没有绑定过银行卡进入绑卡页面的前置方法
	 * @return
	 */
	@RequestMapping("/yczgd_preBindCard")
	public String preBindCard(HttpServletRequest request) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("preBindCard  userOnlyId="+userOnlyId);
			//取本系统的身份证号和姓名，没有取VPS的身份证号和姓名。
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryUserInfo(userOnlyId);
					//		String result =JsonResult.getInstance().addOk().add("flag", "0000").add("certNo", "320602195011111111").add("userName", "王小强").add("userOnlyId", "10000000391").toJsonStr();
			log.info("preBindCard result:"+result);
			JSONObject js=JSONObject.fromObject(result);
			if("0000".equals(js.get("code"))){//返回成功标志
				request.setAttribute("js", js);
				request.setAttribute("applyFlag", 1);//标志
				return ycbindCard;// 跳转绑卡页面
			}else 
				return ERROR;
		} catch (Exception e) {
			log.error("preBindCard Error", e);
			return ERROR;
		}
	}
	
	/**
	 *  用户确认基本信息后，掌柜绑定过银行卡,选择贷款卡号
	 * @return
	 */
	@RequestMapping("/yczgd_selectCardNo")
	@ResponseBody
	public JSONPObject selectCardNo(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("preBindCard  userOnlyId="+userOnlyId);
			//取本系统的身份证号和姓名，没有取VPS的身份证号和姓名。
			String result = WildflyBeanFactory.getYCZgdQueryClient().selectCardNo(userOnlyId);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("selectCardNo Error", e);
			return new JSONPObject(jsoncallback, e);
		}
	}
	
	/**
	 * 1、绑卡页面完成后（卡号、身份证号都有）、
	 * 或者 2、用户选择卡号确认后（卡号有、身份证号无）
	 * 先校验用户年龄，再更新账户表，用户没有身份证号的也要更新
	 * @return  code 0001 弹出弹窗获取验证码
	 * 			code 0002 进入申请页面
	 * 			code 0003 移除白名单说明页面
	 * 			code 1000 系统异常
	 */
	@RequestMapping("/yczgd_bindCardNo")
	@ResponseBody
	public JSONPObject bindCardNo(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId = getUserOnlyId(request);
			String cardNo = request.getParameter("cardNo");//卡号
			String certNo = request.getParameter("certNo");//身份证号码
			log.info("bindCardNo  userOnlyId="+userOnlyId+";cardNo="+cardNo);
			//取本系统的身份证号和姓名，没有取VPS的身份证号和姓名。
			String result = WildflyBeanFactory.getYCZgdClient().bindCardNo(userOnlyId,cardNo,certNo);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("bindCardNo Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	/**
	 * 向掌柜手机发送短信
	 * @param phone
	 * @return none
	 * response json
	 * returnCode 0000(成功)
	 * randomCode 校验码
	 */
	@RequestMapping("/sendmsg_smsSendRandomCode")
	@ResponseBody
	public JSONPObject smsSendRandomCode(HttpServletRequest request,@RequestParam String jsoncallback) {
		String result= "";
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String phone = request.getParameter("phone");
			log.info("smsSendRandomCode -->" + phone);
			String ret = WildflyBeanFactory.getSendMessageClient().smsSendRandomCode(phone);
			log.info("smsSendRandomCode ret:"+ret);
			JSONObject js=JSONObject.fromObject(ret);
			result=JsonResult.getInstance().add("returnCode", js.get("returnCode")).add("returnMessage", js.get("returnMessage")).toJsonStr();
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				result = JsonResult.getInstance().addError(e.getMessage()).toJsonStr();
//				res.setContentType("text/plain;charset=UTF-8");
//				res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			} catch (Exception e1) {
				log.error(e1.getMessage(), e1);
				return new JSONPObject(jsoncallback,e1);
			}
		}
	}
	
	/**
	 * 验证手机校验码是否正确
	 * @param phone   手机号
	 * @param validCode 验证码
	 * @return none
	 * response json
	 * returnCode  0000 成功
	 * returnMessage 
	 * verifyResult  true or false
	 *  {"returnCode":"0000","returnMessage":"操作成功","verifyResult":"true"}
	 */
	@RequestMapping("/sendmsg_verifyRandomCode")
	@ResponseBody
	public JSONPObject verifyRandomCode(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String phone = request.getParameter("phone");
			String validCode = request.getParameter("validatecode");
			log.info("verifyRandomCode -->" + phone + ":" + validCode);
			String result = WildflyBeanFactory.getSendMessageClient().verifyRandomCode(phone, validCode);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				String result = JsonResult.getInstance().addError(e.getMessage()).toJsonStr();
//				res.setContentType("text/plain;charset=UTF-8");
//				res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
				return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			} catch (Exception e1) {
				log.error(e1.getMessage(), e1);
				return new JSONPObject(jsoncallback,e1);
			}
		}
	}
	private ApplyDetailDto prePersonInfo(String userOnlyId){
		ApplyDetailDto applyDetailDto = null;
		try {
//			log.info("prePersonInfo begin>>>>>>>userOnlyId"+userOnlyId);
			ApplyDetailDto param=new ApplyDetailDto();
			param.setUserOnlyId(userOnlyId);
			List<ApplyDetailDto> list=WildflyBeanFactory.getZgdQueryClient().findApplyDetail(param);
			if(null!=list&&list.size()>0){
				applyDetailDto=list.get(0);
				applyDetailDto=handleDetailDto(applyDetailDto,null);
			}else if(applyDetailDto==null){
				applyDetailDto=new ApplyDetailDto();
			}
			ZgdCSWebClient  csClient=WildflyBeanFactory.getZgdCSWebClient();
			Map<String,Object> map=csClient.queryUserOrg(userOnlyId);
			String stationAddress=Convert.toStr(map.get("stationAddress"));
			if(StringUtils.isBlank(applyDetailDto.getStoreAddress())&&StringUtils.isNotBlank(stationAddress)){
				applyDetailDto.setStoreAddress(stationAddress);
			}
			applyDetailDto.setUserOnlyId(userOnlyId);
			///根据身份证号获取掌柜性别
			String certNo=applyDetailDto.getCertNo();
			String sex="";
			if(StringUtils.isNotBlank(certNo)){
				certNo=certNo.substring(certNo.length()-2, certNo.length()-1);
				int se=Convert.toInt(certNo);
				sex=se%2!=0?"男":"女";
				applyDetailDto.setSex(sex);
			}
			///根据身份证号获取掌柜性别
		
		} catch (Exception e) {
		log.error("prePersonInfo error",e);
		}
		return applyDetailDto;
	}
	/**
	 * 此处与zgdAction的prePersonInfo()方法同时维护
	 * 获取客户基本信息并跳转基本资料展示页面
	 * @author zhangshuai
	 * @return
	 */
	@RequestMapping("/yczgd_prePersonInfo")
	public String prePersonInfo(HttpServletRequest request,ApplyDetailDto applyDetailDto,Model model){
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("prePersonInfo begin>>>>>>>userOnlyId"+userOnlyId);
			ApplyDetailDto 	applyDetailDto1=prePersonInfo(userOnlyId);
			model.addAttribute("applyDetailDto", applyDetailDto1);
		} catch (Exception e) {
			log.error("prePersonInfo error",e);
			return "error";
		}
		log.info("prePersonInfo end>>>>>>>userOnlyId");
		return personInfo;
	}
	/**
	 * 
	 * 跳转上传图片页面
	 * @author zhangshuai
	 * @return
	 */
	@RequestMapping("/yczgd_preQualification")
	public String preQualification(HttpServletRequest request ,Model model){
		try {
			ApplyDetailDto applyDetailDto=null;
			String userOnlyId=getUserOnlyId(request);
			log.info("preQualification begin>>>>>>>userOnlyId"+userOnlyId);
			ApplyDetailDto param=new ApplyDetailDto();
			param.setUserOnlyId(userOnlyId);
			List<ApplyDetailDto> list=WildflyBeanFactory.getZgdQueryClient().findApplyDetail(param);
			if(null!=list&&list.size()>0){
				applyDetailDto=list.get(0);
				ZgdClient	client = WildflyBeanFactory.getZgdClient();
				List<ApplyImageDto> imagelist=client.queryByUserOnlyId(applyDetailDto.getUserOnlyId());
				applyDetailDto=handleDetailDto(applyDetailDto,imagelist);
			}else if(applyDetailDto==null){
				applyDetailDto=new ApplyDetailDto();
			}
			model.addAttribute("applyDetailDto", applyDetailDto);
		} catch (Exception e) {
			log.error("preQualification errror:", e);
		}
		log.info("preQualification end>>>>>>>userOnlyId");
		return qualification;
	}
	/**
	 * 校验掌柜姓名（配偶姓名）与营业执照经营者姓名是否一致、营业执照营业时间是否满一年
	 * @param request
	 * @param applyDto
	 * @return
	 */
	private String checkBusiness(HttpServletRequest request,ApplyDto applyDto){
		JSONObject obj = new JSONObject(); 
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info(String.format("校验工商信息%s", userOnlyId));
			Map<String, Object> map = new HashMap<String, Object>();
			String code="0000";
			String msg="sucess";
			
			if(StringUtils.isNotBlank(userOnlyId)){
				map.put("userOnlyId", userOnlyId);
			}else{
				obj.put("code", "1000");
				obj.put("msg", "无法获取登录信息，请重新登录");
				log.info(String.format("无法获取登录信息，请重新登录再试%s", "useronlyId  is null"));
				return obj.toString();
			}
		
			
			String busLiecnceNo=applyDto.getApplyDetailDto().getBusLiecnceNo();
			if(StringUtils.isBlank(busLiecnceNo)){
				obj.put("code", "1000");
				obj.put("msg", "营业执照注册号为空，请输入！");
				log.info(String.format("%s营业执照注册号为空", userOnlyId)); 
				return obj.toString();
			}
			Map<String, Object> retmap =getCompanyInfo(busLiecnceNo, userOnlyId);
			if(null!=retmap&&!retmap.isEmpty()){
				String esdate=Convert.toStr(retmap.get("esDate"));
				String frname=Convert.toStr(retmap.get("frName"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				log.info(String.format("已经获取工商信息%s", userOnlyId));
				//获取开业日期 
					log.info(String.format("开始比对工商信息%s", userOnlyId));
					if(StringUtils.isNotBlank(frname)){
						frname=frname.trim();
						String blm=applyDto.getApplyDetailDto().getBusLicManager();//营业执照经营者"1" 本人 2 配偶
//					  //防止页面缓存
						String username=applyDto.getApplyDetailDto().getUserName();
						//防止用户第一次提交，applyddetail 中没有该掌柜信息。
						if(StringUtils.isBlank(username)||"null".equals(username)||"undefined".equals(username)){
							log.info(String.format("%s后台无申请记录信息", userOnlyId));
							ZgdQueryClient client=WildflyBeanFactory.getZgdQueryClient();
							String result=client.queryUserInfoByuserOnlyId(userOnlyId);
							JSONObject js=JSONObject.fromObject(result);
							if("0000".equals(js.get("code"))){
								username=js.getString("userName");
							}
						}
						String conName=applyDto.getApplyDetailDto().getOtherContact().trim(); 
						if(blm.equals("1")){//营业执照经营者是本人
							
							log.info(String.format("%s工商信息姓名1：%s,配偶姓名：%s,掌柜姓名%s", userOnlyId,frname,conName,username));
							if(StringUtils.isNotBlank(username)&&!frname.equals(username.trim())){
								code="1000";
								msg="营业执照经营者与邮掌柜需为同一人，请重新填写，否则无法申请掌柜贷;";
								log.info(String.format("营业执照经营者与邮掌柜需为同一人，请重新填写，否则无法申请掌柜贷%s", userOnlyId));
							}
						}else if(blm.equals("2")){//营业执照经营者是配偶
							log.info(String.format("%s工商信息姓名2：%s,配偶姓名：%s,掌柜姓名%s", userOnlyId,frname,conName,username));
							if(StringUtils.isNotBlank(conName)&&!frname.equals(conName)){
								code="1000";
								msg="营业执照经营者与您配偶姓名不一致,请重新填写，否则无法申请掌柜贷;";
								log.info(String.format("营业执照经营者与您配偶姓名不一致,请重新填写，否则无法申请掌柜贷%s", userOnlyId));
							}
						}else{
							log.info(String.format("%s没有选择营业执照经营人与本人关系", userOnlyId));
						}
						
						
					}else{
						log.info(String.format("%s工商信息法人姓名为空，无法校验", userOnlyId));
					}
					if(StringUtils.isNotBlank(esdate)){
						
						long day=(sdf.parse(sdf.format(new Date())).getTime()-sdf.parse(esdate).getTime())/(24*60*60*1000);  
						if(day<365){
							code="1000";
							msg +="您的营业执照未满一年，暂不能申请";
							log.info(String.format("%s的营业执照未满一年，暂不能申请", userOnlyId));
						}
					}else{
						log.info(String.format("%s工商信息开业日期为空，无法校验", userOnlyId));
					}
			}else{
				log.info(String.format("%s工商信息为空，无法校验", userOnlyId));
			}
			 
			
			obj.put("code", code);
			obj.put("msg", msg);
			
			
		} catch (Exception e) {
			log.error("校验工商信息服务器异常，不影响正常流程; error:", e);
			obj.clear();
			obj.put("code", "0000");
			obj.put("msg", "");
		}
		
		log.info(String.format("校验结果%s", obj.toString()));
		return obj.toString();
	}
	/**
	 * 亿微征信接口 企业工商注册信息
	 * @param busincessCode
	 * @param userOnlyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> getCompanyInfo(String busincessCode,String userOnlyId){
		Map<String,Object>  reMap=new HashMap<String,Object>();
		log.info("百融征信接口 企业工商注册信息  调用：userOnlyId：" + userOnlyId+"busincessCode："+busincessCode);
		  try {
			if (StringUtils.isNotBlank(busincessCode)) {
				Map<String,Object>  dataMap=new HashMap<String,Object>();
				dataMap.put("bizRegnum", busincessCode);
				String bizWorkfor="";
				//企业工商注册基本信息查询,原亿微数据改成百融对接
				if(StringUtils.isNotBlank(userOnlyId)){
					ZgdCSWebClient zgdCsWebClient=WildflyBeanFactory.getZgdCSWebClient();
					ApplyDetailDto applyDetailDto =	zgdCsWebClient.findApplyDetailDtoByUserOnlyId(userOnlyId);
					bizWorkfor = applyDetailDto.getStoreName();
				}
				if("null".equals(bizWorkfor)){
					bizWorkfor="";
				}
				dataMap.put("bizWorkfor", bizWorkfor);
				SldService sldService = new DefaultSldService();
				Map<String,Object>  responseMap =new HashMap<String, Object>();
				Request request = sldService.getRequest(DefaultSldService.REQUEST_TYPE_BR, BRTransCodeEnum.BR_COMP_QUERY.getTransCode(),dataMap);
			    SldOperateLog sldOperateLog = new SldOperateLog("掌柜贷MGT", "调用者的MGT账户名", "BR", "", "MGT审核后台查询企业工商信息百融征信产品数据。", "");
			    request.setOpeartor(sldOperateLog);
			    Response response = sldService.doService(request);
			    if("000000".equals(response.getResponseCode())){  //000000,处理成功！ "000000".equals(response.getResponseCode())
			 	     responseMap = response.getResponseMap();//我是处理后的数据,Map格式
			 	    log.info("getCompanyInfo responseMap==========" + responseMap);
			 	     if(responseMap!=null && responseMap.size()>=1){
			 	    	 Map<String,Object> productMap = (Map<String,Object>)responseMap.get("product");
						 if(productMap!=null && productMap.size()>=1 && productMap.containsKey("data")){
							 reMap = (Map<String,Object>)productMap.get("data");
					 	     log.info("getCompanyInfo resultMap==========" + reMap);
						 }
			 	     }
			    }
			}
		} catch (Exception e) {
			log.error("百融征信接口查询企业工商注册信息接口 error", e);
		}
		return reMap;
	}
	/**
	 * 保存基本资料
	 * @author zhangshuai
	 * @return
	 */
	@RequestMapping(value="/yczgd_createPersonInfo",produces=MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	public void createPersonInfo(HttpServletRequest request,HttpServletResponse res,ApplyDto applyDto){
		JSONObject obj = new JSONObject();
		try {
			String checkName=checkUserName(request,applyDto);
			if(!checkName.contains("0000")){
				try {
					String userOnlyId = getUserOnlyId(request);
					log.info("createPersonInfo.....userOnlyId:" + userOnlyId);
					applyDto.getApplyDetailDto().setUserOnlyId(userOnlyId);
					//处理 推荐人 手机号 
					if (applyDto.getApplyDetailDto().getReferenceName()
							.contains("undefined"))
						applyDto.getApplyDetailDto().setReferenceName("");
					if (applyDto.getApplyDetailDto().getReferenceMobile()
							.contains("undefined"))
						applyDto.getApplyDetailDto().setReferenceMobile("");
					ZgdClient client = WildflyBeanFactory.getZgdClient();
					client.saOrUpDetailInfoBeRealName(applyDto
							.getApplyDetailDto());
				} catch (Exception e) {
					log.error("保存申请资料 error",e);
				}
				obj=JSONObject.fromObject(checkName);
				
			}else {
				String result=checkBusiness(request,applyDto);
				if(result.contains("1000")){
					obj=JSONObject.fromObject(result);
				}else{
					
					String userOnlyId=getUserOnlyId(request);
					log.info("createPersonInfo.....userOnlyId:"+userOnlyId);
					applyDto.getApplyDetailDto().setUserOnlyId(userOnlyId);
				
					//处理 推荐人 手机号 
					if(applyDto.getApplyDetailDto().getReferenceName().contains("undefined"))
						applyDto.getApplyDetailDto().setReferenceName("");
					if(applyDto.getApplyDetailDto().getReferenceMobile().contains("undefined"))
						applyDto.getApplyDetailDto().setReferenceMobile("");
					
					ZgdClient client=WildflyBeanFactory.getZgdClient();
					Map<String,Object> remap=client.saveOrUpdateApplyDetailInfo(applyDto.getApplyDetailDto());
					String code=Convert.toStr(remap.get("code"), "1000");
					String msg=Convert.toStr(remap.get("msg"), "注册失败，请稍后重试");		
					obj.put("code", code);
					obj.put("msg", msg);
					log.info("createPersonInfo.....code:"+code+">>>>>msg:"+msg);
				}
			}
			
		} catch (Exception e) {
		log.error("createPersonInfo error",e);
		obj.clear();
		obj.put("code", "1000");
		obj.put("msg", "数据提交失败，请稍后再试！");
		}
		try {
			res.setContentType("text/plain;charset=UTF-8");
			res.getWriter().write(obj.toString()); //返回jsonp数据
		} catch (IOException e) {
			log.error("createPersonInfo error",e);
		}
	}
	/**
	 * 处理dto中的图片防止传进来的是小图片
	 * @param dto
	 * @return
	 */
	private ApplyDetailDto handleDetailDtoImage(ApplyDetailDto dto){
		if(dto!=null){
			String repflag="_78x78";
			if (!Tools.isEmpty(dto.getBusLiecnceImg())&&dto.getBusLiecnceImg().contains(repflag))
				dto.setBusLiecnceImg(dto.getBusLiecnceImg().replace(repflag, ""));
			if (!Tools.isEmpty(dto.getStoreImg())&&dto.getStoreImg().contains(repflag))
				dto.setStoreImg(dto.getStoreImg().replace(repflag, ""));
			if (!Tools.isEmpty(dto.getStoreInnerImg())&&dto.getStoreInnerImg().contains(repflag))
				dto.setStoreInnerImg(dto.getStoreInnerImg().replace(repflag, ""));
			if (!Tools.isEmpty(dto.getCertHoldImg())&&dto.getCertHoldImg().contains(repflag))
				dto.setCertHoldImg(dto.getCertHoldImg().replace(repflag, ""));
			if (!Tools.isEmpty(dto.getCertPositiveImg())&&dto.getCertPositiveImg().contains(repflag))
				dto.setCertPositiveImg(dto.getCertPositiveImg().replace(repflag, ""));
			if (!Tools.isEmpty(dto.getCertNegativeImg())&&dto.getCertNegativeImg().contains(repflag))
				dto.setCertNegativeImg(dto.getCertNegativeImg().replace(repflag, ""));
			if (!Tools.isEmpty(dto.getStorePropertyUrl())&&dto.getStorePropertyUrl().contains(repflag))
				dto.setStorePropertyUrl(dto.getStorePropertyUrl().replace(repflag, ""));
			
		}
		return dto;
	}
	/**
	 * 保存客户图片信息
	 * 
	 * @author zhangshuai
	 * @return
	 */
	@RequestMapping(value="/yczgd_createQualification",produces=MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	public void createQualification(HttpServletRequest request,HttpServletResponse res,ApplyDto applyDto){
		log.info("createQualification begin");
		ApplyDetailDto applyDetailDto=applyDto.getApplyDetailDto();
		if(null!=applyDetailDto){
			JSONObject obj = new JSONObject();
			try {
				String userOnlyId=getUserOnlyId(request);
				log.info("createQualification userOnlyId:"+userOnlyId);
				applyDetailDto.setUserOnlyId(userOnlyId);
//				applyDetailDto.setStatus(UserTools.toLong(Constants.ApplyDetail.apply_apply));
				ZgdClient client=WildflyBeanFactory.getZgdClient();
				handleDetailDtoImage(applyDetailDto);
				applyDetailDto.setApplyIp(Tools.getIpAddr(request));
				applyDetailDto.setPhoneType("unknow");
				applyDetailDto.setIsMobileUpload(0);
				Map<String,Object> result=client.UpdateApplyDetailImage(applyDetailDto);
				String code=Convert.toStr(result.get("code"),"1000");
				String msg=Convert.toStr(result.get("msg"),"服务器异常，请刷新后重新提交。");
				obj.put("code",code);
				obj.put("msg", msg);
				 
				
			} catch (Exception e) {
				log.error("createQualification error",e);
				obj.clear();
				obj.put("code", "1000");
				obj.put("msg", "数据提交失败，请稍后再试！");
			}
			try {
				res.setContentType("text/plain;charset=UTF-8");
				res.getWriter().write(obj.toString()); //返回jsonp数据
				log.info("createQualification end:"+obj.toString());
			} catch (IOException e) {
				log.error("createQualification error",e);
			}
		}
	}
	
	
	
	/**
	 * 查询闪电提额上下限
	 * @return
	 */
	@RequestMapping("/yczgd_fastAmountLowAndTop")
	@ResponseBody
	public JSONPObject fastAmountLowAndTop(HttpServletRequest request,@RequestParam String callback){
		log.info("fastAmountLowAndTop begin.");
		Map<String,Object> resultMap =new HashMap<String, Object>();
//		String callback = request.getParameter("jsoncallback");
		try {
			log.info("fastAmountLowAndTop : userOnlyId = "+getUserOnlyId(request));
			String userOnlyId = getUserOnlyId(request);
			
			if(StringUtils.isBlank(userOnlyId)){
				resultMap.put("code", "000001");
				resultMap.put("message", "需查询的账户id不可为空。");
//				res.setContentType("text/plain;charset=UTF-8");
//				res.getWriter().write(callback+"("+JsonUtil.getJsonStringFromMap(resultMap)+")"); //返回jsonp数据  
				return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(resultMap));
			}
			
			String result = WildflyBeanFactory.getAccountClient().fastAmountLowAndTop(userOnlyId);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(callback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("fastAmountLowAndTop has error!",e);
			return new JSONPObject(callback,e);
		}
	}
	
	/**
	 * 验证闪电提额使用资质
	 * @return
	 */
	@RequestMapping("/yczgd_fastAmountCheck")
	@ResponseBody
	public JSONPObject fastAmountCheck(HttpServletRequest request,@RequestParam String callback){
		log.info("fastAmountCheck begin.");
		Map<String,Object> resultMap =new HashMap<String, Object>();
//		String callback = request.getParameter("jsoncallback");
		try {
			log.info("fastAmountCheck : userOnlyId = "+getUserOnlyId(request));
			String userOnlyId = getUserOnlyId(request);
			
			if(StringUtils.isBlank(userOnlyId)){
				resultMap.put("code", "000001");
				resultMap.put("message", "需校验的账户id不可为空。");
//				res.setContentType("text/plain;charset=UTF-8");
//				res.getWriter().write(callback+"("+JsonUtil.getJsonStringFromMap(resultMap)+")"); //返回jsonp数据  
				return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(resultMap));
			}
			
			boolean flag = WildflyBeanFactory.getAccountClient().checkFastAmount(userOnlyId);
			if(flag){
				resultMap.put("code", "000000");
				resultMap.put("message", "可使用掌柜贷闪电提额。");
			}else{
				resultMap.put("code", "999999");
				resultMap.put("message", "无使用掌柜贷闪电提额资质。");
			}
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(callback+"("+JsonUtil.getJsonStringFromMap(resultMap)+")"); //返回jsonp数据  
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(resultMap));
		} catch (Exception e) {
			log.error("fastAmountCheck has error!",e);
			return new JSONPObject(callback,e);
		}
	}
	
	/**
	 * 闪电提额
	 * @return
	 */
	@RequestMapping("/yczgd_fastAmount")
	@ResponseBody
	public JSONPObject fastAmount(HttpServletRequest request,@RequestParam String callback){
		log.info("fastAmount begin.");
		Map<String,Object> resultMap =new HashMap<String, Object>();
//		String callback = request.getParameter("jsoncallback");
		try {
			log.info("fastAmount : userOnlyId = "+getUserOnlyId(request));
			String userOnlyId = getUserOnlyId(request);
			if(StringUtils.isBlank(userOnlyId) || StringUtils.isBlank(request.getParameter("amount"))){
				resultMap.put("code", "000001");
				resultMap.put("message", "账户id与提额金额均不可为空。");
//				res.setContentType("text/plain;charset=UTF-8");
//				res.getWriter().write(callback+"("+JsonUtil.getJsonStringFromMap(resultMap)+")"); //返回jsonp数据 
				return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(resultMap));
			}
			Map<String,Object> paramers = new HashMap<String, Object>();
			paramers.put("userOnlyId", userOnlyId);
			paramers.put("amount", request.getParameter("amount"));
			paramers.put("transType", "200");
			resultMap = WildflyBeanFactory.getAccountClient().fastAmount(paramers);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(callback+"("+JsonUtil.getJsonStringFromMap(resultMap)+")"); //返回jsonp数据  
			return new JSONPObject(callback,UhjWebJsonUtil.parseObjToJson(resultMap));
		} catch (Exception e) {
			log.error("fastAmountCheck has error!",e);
			return new JSONPObject(callback,e);
		}
	}
	
	/**
	 * 跳转到账户概览页面
	 * @return
	 */
	@RequestMapping("yczgd_toAccOverView")
	public String toAccOverView(HttpServletRequest request){
		try {
			log.info("toAccOverView..");
			String userOnlyId = getUserOnlyId(request);
//			String provice = WildflyBeanFactory.getYCZgdQueryClient().queryUserProvince(userOnlyId);
//			request.setAttribute("provice", provice);
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("userOnlyId", userOnlyId);
			boolean result=WildflyBeanFactory.getYCZgdQueryClient().queryOverDues(params);
			this.overDues = result ? "1" : "0";
			return ycaccOverView;
		} catch (Exception e) {
			log.error("didn't get userOnlyId!", e);
			return ERROR;
		}
		
	}
	
	/**
	 * 查询账户概览信息  json
	 * 原来 queryAccountOverView
	 * @return
	 */
	@RequestMapping("yczgd_accOverView")
	@ResponseBody
	public JSONPObject accOverView(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			log.info("queryAccountOverView>>>>start" + "userOnlyId=" + getUserOnlyId(request));
			UserInfoDto dto = new UserInfoDto();
			dto.setUserOnlyId(getUserOnlyId(request));
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryAccOverview(dto);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryAccountOverView Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	/**
	 * 账户概览页 查询借据
	 * @return
	 */
	@RequestMapping("yczgd_queryDues")
	@ResponseBody
	public JSONPObject queryDues(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			log.info("queryDues>>>>start" + "userOnlyId=" + getUserOnlyId(request));
			Map<String,Object> paras = new HashMap<String, Object>();
			paras.put("userOnlyId", getUserOnlyId(request));
			paras.put("currPage", request.getParameter("currPage"));
			paras.put("pageSize", request.getParameter("pageSize"));
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryDues(paras);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryDues Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	/**
	 * 根据借据id查询还款计划
	 * @return
	 * code:0000
	 * msg:success
		list : [
		{index,  分期数
		currInter,  当期利息
		planRepayDate, 计划还款时间
		currPrincipal} 当期本金
		]
	 */
	@RequestMapping("yczgd_queryPlan")
	@ResponseBody
	public JSONPObject queryPlan(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			log.info("queryPlans>>>>start userOnlyId=" + getUserOnlyId(request) + "dueId=" + request.getParameter("dueId"));
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryPlans(request.getParameter("dueId"), getUserOnlyId(request));
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryPlans Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	/**产品介绍
	 * @return
	 */
	@RequestMapping("yczgd_prdIntroduce")
	public String prdIntroduce(HttpServletRequest request) {
		try {
			String productIntroduction  = "yc/yc_banner03";
			String banner=request.getParameter("banner");
			if("1".equals(banner))
				productIntroduction = "yc/yc_banner01";
			if("2".equals(banner))
				productIntroduction = "yc/yc_banner02";
			if("3".equals(banner))
				productIntroduction = "yc/yc_banner03";
			log.info("prdIntroduce userOnlyId=" + getUserOnlyId(request));
			return productIntroduction;
		} catch (Exception e) {
			log.error("prdIntroduce error",e);
			return ERROR;
		}
	}
	/**
	 * 去借款前置判断  jsonp
	 * @return
	 */
	@RequestMapping("yczgd_checkLendInfo")
	@ResponseBody
	public JSONPObject checkLendInfo(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			log.info("checkLendInfo userOnlyId=" + getUserOnlyId(request));
			String result=WildflyBeanFactory.getYCZgdQueryClient().checkLendInfo(getUserOnlyId(request));
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("checkLendInfo error",e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	/**
	 * 打回重新申请跳转
	 * @return
	 */
	@RequestMapping("yczgd_returnApply")
	public String returnApply(HttpServletRequest request) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("returnApply userOnlyId=" + userOnlyId);
			String result=WildflyBeanFactory.getYCZgdClient().returnApply(userOnlyId);
			log.info("returnApply " + result);
			JSONObject js = JSONObject.fromObject(result);
			if ("0000".equals(js.get("code").toString())){
				if("apply".equals(js.get("url"))){//重新提交资料
					prePersonInfo(userOnlyId);
					return personInfo;
				}else if("realName".equals(js.get("url"))){//打回实名之前，重新申请
//					String provice = WildflyBeanFactory.getYCZgdQueryClient().queryUserProvince(userOnlyId);
//					request.setAttribute("provice", provice);
					String reslut = WildflyBeanFactory.getYCZgdQueryClient().queryZgdWhiteLimit(userOnlyId);
					JSONObject object = JSONObject.fromObject(reslut);
					if ("0000".equals(object.get("code").toString())){
						request.setAttribute("creditLimit", object.get("creditLimit").toString());
					}
					request.setAttribute("desc", Convert.toStr(js.get("desc"), ""));
					request.setAttribute("applyStatus", "01");
					return ycunApply;
				}
			}
		} catch (Exception e) {
			log.error("returnApply error",e);
		}
		return ERROR;
	}
	/**
	 * 去借款
	 * 判断用户有资质且已绑卡 跳转 借款页
	 * @return
	 */
	@RequestMapping("yczgd_toLendPage")
	public String toLendPage(HttpServletRequest request,ApplyDetailDto applyDetailDto) {
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("toLendPage userOnlyId=" + userOnlyId);
			String status = getYCUserStatus(userOnlyId);
			/*****一下begin到end的逻辑与zgdAction的 toZgdPage 方法保持一致 （这里02至06的状态不会用到）***/
			/****************begin***************/
			if("0".equals(status)|| "01".equals(status) || "011".equals(status)){
				//开通申请 01
				//开通申请 ,但已实名认证（AccountInfo的绑定账号不为空） 011   防止已经实名的再次去绑卡
//				String provice = WildflyBeanFactory.getYCZgdQueryClient().queryUserProvince(userOnlyId);
//				request.setAttribute("provice", provice);
				String reslut = WildflyBeanFactory.getYCZgdQueryClient().queryZgdWhiteLimit(userOnlyId);
				JSONObject js = JSONObject.fromObject(reslut);
				if ("0000".equals(js.get("code").toString())){
					request.setAttribute("creditLimit", js.get("creditLimit").toString());
				}
				request.setAttribute("applyStatus", status);
				request.setAttribute("desc", Convert.toStr(js.get("desc"), ""));
				return ycunApply;//邮储的未开通账户概览
			}
			if("02".equals(status) || "05".equals(status)){//审核拒绝或被冻结开通
				return freeze;
			}
			if("03".equals(status) ){//等待审核
				return waitReview;
			}
			if("04".equals(status) ){//审核被打回（如果因为姓名问题需要被打回实名之前，点击重新申请才会打回实名之前）
				String msg="";
				String applyStatus="04";
				ApplyDetailDto param=new ApplyDetailDto();
				param.setUserOnlyId(userOnlyId);
				List<ApplyDetailDto> list=WildflyBeanFactory.getZgdQueryClient().findApplyDetail(param);
				if(null!=list&&list.size()>0){
					ApplyDetailDto	applyDetailDto1=list.get(0);
					if(applyDetailDto1.getStatus().equals(10L)){
						applyStatus="10";
					}
					msg=applyDetailDto1.getRefusalReason();//审核被打回的原因
				}
				log.info("msg:"+msg);
				request.setAttribute("applyStatus", applyStatus);
				if(Check.isBlank(msg)){
					request.setAttribute("msg", "请确认您填写的资料是否正确，上传的图片是否清晰");
				}else{
					request.setAttribute("msg", msg.replaceAll(";", ";</br>"));
				}
				return freeze;
			}
			if("06".equals(status) ){//审核被打回后已经点击了重新申请，下次再次点击掌柜贷直接进入提交资料页面
				prePersonInfo(userOnlyId);
				return personInfo;
			}
			/****************end***************/
			
			if ("11".equals(status)) {// 有资质无绑卡 跳转绑卡页面（无用：因为已申请的肯定绑卡了）
				//取本系统的身份证号和姓名，没有取VPS的身份证号和姓名，如果也没有不允许绑卡提示用户去省管后台录入身份证信息
				String result = WildflyBeanFactory.getYCZgdQueryClient().queryUserInfo(userOnlyId);
				log.info("toLendPage result:"+result);
				JSONObject js=JSONObject.fromObject(result);
				if("0000".equals(js.get("code"))){//返回成功标志
					request.setAttribute("js", js);
					return ycbindCard;// 跳转绑卡页面
				}
			}
			if ("21".equals(status)) {//有资质且绑卡
				return yclendPage;
			}
			return noQualify;
		} catch (Exception e) {
			log.error("toLendPage error",e);
			return ERROR;
		}
	}
	
	/**
	 * 借款页 查询用户基本信息  jsonp
	 * @return
	 */
	@RequestMapping("yczgd_queryLendInfo")
	@ResponseBody
	public JSONPObject queryLendInfo(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			log.info("queryLendInfo userOnlyId=" + getUserOnlyId(request));
			Map<String, Object> map=WildflyBeanFactory.getYCZgdQueryClient().queryLendInfo(getUserOnlyId(request));
			log.info("queryLendInfo " + map);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+JSONObject.fromObject(map).toString()+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(map));
		} catch (Exception e) {
			log.error("queryLendInfo error",e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	/**
	 * 根据用的借款本金显示用户的还款计划，以及预估费用等
	 * @param userOnlyId 用户Id
	 * @param lendAmount 借款金额
	 * @param lendAmount 固定还款日
	 * @return
		还款计划  list
		费用预估  forecast
		服务费  serviceCharge
		借款到期日 lastRepayDate
	 */
	@RequestMapping("yczgd_queryPlanAndFree")
	@ResponseBody
	public JSONPObject queryPlanAndFree(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId=getUserOnlyId(request);
			String applyAmount=request.getParameter("applyAmount");//借款金额
			String fixedRepayDate=request.getParameter("fixedRepayDate");//固定还款日
			String repayType=request.getParameter("repayType");//还款方式
			log.info("queryPlanAndFree  userOnlyId="+userOnlyId+";applyAmount="+applyAmount+";fixedRepayDate="+fixedRepayDate);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("applyAmount", applyAmount);
			map.put("fixedRepayDate", fixedRepayDate);
			map.put("repayType", repayType);
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryRepayPlanAndFree(map);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryPlanAndFree Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	/**
	 * 确认借款申请 借款信息
	 * 
	 * @return
	 */
	@RequestMapping(value="yczgd_confirmLoanApply")
	@ResponseBody
	public JSONPObject confirmLoanApply(HttpServletRequest request,@RequestParam String jsoncallback,BeanDto beanDto) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			log.info("confirmLoanApply  userOnlyId=" + getUserOnlyId(request));
			beanDto.getLoanDto().setUserOnlyId(getUserOnlyId(request));
			beanDto.getLoanDto().setApplyIp(Tools.getIpAddr(request));
			beanDto.getLoanDto().setContractVer("20001");//合同版本号(第一版合同版本号为10001)	
//			loanDto.setRepayType("0200");
			String result = WildflyBeanFactory.getYCZgdClient().confirmLoanApply(beanDto.getLoanDto());
			log.info("confirmLoanApply result:"+result);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("confirmLoanApply error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	/**
	 * 借款成功
	 * @return
	 */
	@RequestMapping("yczgd_toLendSuccess")
	public String toLendSuccess(HttpServletRequest request) {
		try {
			log.info("toLendSuccess  userOnlyId=" + getUserOnlyId(request) + ";orderId:" + request.getParameter("orderId"));
//			String provice = WildflyBeanFactory.getYCZgdQueryClient().queryUserProvince(getUserOnlyId(request));
//			request.setAttribute("provice", provice);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userOnlyId", getUserOnlyId(request));
			map.put("orderId", request.getParameter("orderId"));
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryLendSuccess(map);
			JSONObject obj = JSONObject.fromObject(result);
			if("0000".equals(obj.get("code"))){
				request.setAttribute("loanAmount", obj.get("loanAmount"));
				request.setAttribute("receiveAcc", obj.get("receiveAcc"));
				log.info("toLendSuccess result:"+result);
				return yclendSuccess;
			}else{
				log.error("toLendSuccess didn't find order:" + request.getParameter("orderId"));
				return ycaccOverView;
			}
		} catch (Exception e) {
			log.error("toLendSuccess error", e);
			return ERROR;
		}
	}
	
	@RequestMapping("counpon_query")
	@ResponseBody
	public JSONPObject counponQuery(HttpServletRequest request,@RequestParam String jsoncallback) {
		Map<String,Object> retMap = new HashMap<String,Object>();
		Map<String,Object> activityMap = new HashMap<String, Object>();
		try{
			boolean checkFirstLoad = Boolean.valueOf(request.getParameter("checkFirstLoad"));
			String loanAmount = request.getParameter("loanAmount");
			int firstCreditLimit = 0;
			String userOnlyId = getUserOnlyId(request);
//			String userOnlyId ="10000024271";
			//首次登录校验
			if(checkFirstLoad)
				loanAmount = "0";
			
			Map<String,Object> couponParamters = new HashMap<String, Object>();
			couponParamters.put("loanAmount", loanAmount);
			couponParamters.put("firstCreditLimit", firstCreditLimit);
			couponParamters.put("checkFirstLoad", checkFirstLoad);
			couponParamters.put("userOnlyId", userOnlyId);
			activityMap = creditApplyService.getCustomerCouponInfo(couponParamters);
			retMap.put("code", "0000");
			retMap.put("msg", "操作成功");
			retMap.putAll(activityMap);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(retMap));
		}catch(Exception e){
			log.error("counponQuery Error", e);
			retMap.put("code", "9999");
			retMap.put("msg", "系统异常");
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(retMap));
		}
	}

//	@RequestMapping("counpon_achieve")
//	@ResponseBody
//	public JSONPObject counponAchieve(HttpServletRequest request,@RequestParam String jsoncallback) {
//		try{
//			Map<String,Object> returnMap = new HashMap<String, Object>();
//			String couponContext = request.getParameter("couponContext");
//			if(!Check.isBlank(couponContext)){
//				List<Map> paramterList = com.alibaba.fastjson.JSONObject.parseArray(couponContext, Map.class);
//				for(Map<String,String> param:paramterList){
//					Map<String,Object> paramterMap = new HashMap<String, Object>();
//					paramterMap.put("URL",PropertiesHelper.getDfs("COUPON_ACHIEVE_URL"));
//					param.put("channel", "400001");
//					param.remove("storeId");
//					param.remove("couponDetailId");
//			        paramterMap.put("paramer", param);
//			        Map<String, Object> ret = HttpClientUtil.httpPost(paramterMap);
//			        if("success".equals(Convert.toStr(ret.get("status")))){
//			        	Map<String, Object> dataMap = JsonUtil.getMapFromJsonString(Convert.toStr(ret.get("result")));
//			        	returnMap.put(couponContext, Convert.toStr(ret.get("result")));
//			        	if("0000".equals(Convert.toStr(dataMap.get("code")))){
//				        	
//			        		
//				        }
//			        }
//				}
//				log.info("counponAchieve couponContext achieve info="+returnMap);
//			}
//			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(returnMap));
//		}catch(Exception ex){
//			log.error("counponAchieve error",ex);
//			return new JSONPObject(jsoncallback,ex);
//		}
//	}
	
	
	/**
	 * 查询掌柜贷用户使用状态
	 * @param userOnlyId
	 * @return json 
	 * code 0000(成功) 1000(失败)
	 * msg  success(成功) 失败原因(失败)
	 * status String
	 */
	@RequestMapping("yczgd_queryUserStatus")
	@ResponseBody
	public JSONPObject queryUserStatus(HttpServletRequest request,String userOnlyId,HttpServletResponse res){
		log.info("queryUserStatus userOnlyId:"+userOnlyId);
		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try{
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryZgdUserUseStatus(userOnlyId);
			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		}catch(Exception e){
			log.error("queryUserStatus  error!",e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	/**
	 * 查询用户提前还清信息
	 * @return
	 */
	@RequestMapping("yczgd_queryEarlyPlans")
	@ResponseBody
	public JSONPObject queryEarlyPlans(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId=getUserOnlyId(request);
			String dueId=request.getParameter("dueId");
			log.info("queryEarlyPlans  userOnlyId=" +userOnlyId+";dueId="+dueId);
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryEarlyPlans(userOnlyId, dueId);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryEarlyPlans Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	/**
	 * 查询用户提前还清信息
	 * @return
	 */
	@RequestMapping("yczgd_confirmEarlyPlans")
	@ResponseBody
	public JSONPObject confirmEarlyPlans(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId=getUserOnlyId(request);
			String dueId=request.getParameter("dueId");
			String preRepayAmount=request.getParameter("preRepayAmount");//归还本息金额（元）
			String preRepayAmount1=request.getParameter("preRepayAmount1");//提前归还本金金额（元）
			String presentCapitalBalance=request.getParameter("presentCapitalBalance");//当前期本金
			String overdueCapitalBalance=request.getParameter("overdueCapitalBalance");//贷款拖欠本金
			String normalInterest=request.getParameter("normalInterest");//正常利息
			String overdueInterest=request.getParameter("overdueInterest");//拖欠利息
			String defaultInterest=request.getParameter("defaultInterest");//罚息
			String preRepayKind="2";//提前还款类型 2--全部结清
			log.info("confirmEarlyPlans  userOnlyId=" +userOnlyId+";dueId="+dueId);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
			map.put("dueId", dueId);
			map.put("preRepayAmount", preRepayAmount);
			map.put("preRepayAmount1", preRepayAmount1);
			map.put("preRepayKind", preRepayKind);
			map.put("presentCapitalBalance", presentCapitalBalance);
			map.put("overdueCapitalBalance", overdueCapitalBalance);
			map.put("normalInterest", normalInterest);
			map.put("overdueInterest", overdueInterest);
			map.put("defaultInterest", defaultInterest);
			String result = WildflyBeanFactory.getYCZgdClient().confirmEarlyPlans(map);
//			res.setContentType("text/plain");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("confirmEarlyPlans Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	/**
	 * 显示用户的还款记录
	 * @return
	 */
	@RequestMapping("yczgd_queryPaymentHistory")
	@ResponseBody
	public JSONPObject queryPaymentHistory(HttpServletRequest request,@RequestParam String jsoncallback) {
//		String jsoncallback =request.getParameter("jsoncallback");//得到js函数名称
		try {
			String userOnlyId=getUserOnlyId(request);
			log.info("queryPaymentHistory  userOnlyId="+userOnlyId);
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryPaymentHistory(userOnlyId);
//			res.setContentType("text/plain;charset=UTF-8");
//			res.getWriter().write(jsoncallback + "("+result+")"); //返回jsonp数据  
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryPaymentHistory Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	@RequestMapping("yczgd_waitReview")
	public String waitReview(){
		return waitReview;
	}
	/**
	 * 掌柜贷咨询服务协议
	 * @return 合同里的相关要素
	 * @throws Exception 
	 */
	@RequestMapping("yczgd_viewConsultService")
	public String viewConsultService(HttpServletRequest request) {
		try {
			String userOnlyId = getUserOnlyId(request);
			log.info("viewConsultService  userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
 			String result = WildflyBeanFactory.getYCZgdQueryClient().viewConsultService(map);
			log.info("viewConsultService result:"+result);
			JSONObject js=JSONObject.fromObject(result);
			request.setAttribute("js", js);
			return "yc/viewConsultService";
		} catch (Exception e) {
			log.error("removeYcZgdWhite Error", e);
			return ERROR;
		}
	}
	/**
	 * 中国邮政储蓄银行小额贷款额度借款合同
	 * @return 合同里的相关要素
	 * @throws Exception 
	 */
	@RequestMapping("yczgd_viewPettyLoan")
	public String viewPettyLoan(HttpServletRequest request) {
		try {
			String userOnlyId = getUserOnlyId(request);
			String applyAmount = request.getParameter("applyAmount");
//			String lastRepayDate = request.getParameter("lastRepayDate");
			log.info("viewPettyLoan  userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
	 		map.put("applyAmount", applyAmount);
//	 		map.put("lastRepayDate", lastRepayDate);
 			String result = WildflyBeanFactory.getYCZgdQueryClient().viewPettyLoan(map);
			log.info("viewPettyLoan result:"+result);
			JSONObject js=JSONObject.fromObject(result);
			request.setAttribute("js", js);
			return "yc/viewPettyLoan";
		} catch (Exception e) {
			log.error("viewPettyLoan Error", e);
			return ERROR;
		}
	}
	/**
	 * 用户借款的时候看的
	 * 中国邮政储蓄银行小额贷款额度借款支用单
	 * @return 合同里的相关要素
	 * @throws Exception 
	 */
	@RequestMapping("yczgd_viewPettyLoanBill")
	public String viewPettyLoanBill(HttpServletRequest request) {
		try {
			String userOnlyId = getUserOnlyId(request);
			String applyAmount = request.getParameter("applyAmount");
			String lastRepayDate = request.getParameter("lastRepayDate");
			String flag = request.getParameter("flag");
			log.info("viewPettyLoanBill  userOnlyId="+userOnlyId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", userOnlyId);
	 		map.put("applyAmount", applyAmount);
	 		map.put("lastRepayDate", lastRepayDate);
	 		map.put("flag", flag);
 			String result = WildflyBeanFactory.getYCZgdQueryClient().viewPettyLoanBill(map);
			log.info("viewPettyLoanBill result:"+result);
			JSONObject js=JSONObject.fromObject(result);
			request.setAttribute("js", js);
			return "yc/viewPettyLoanBill";
		} catch (Exception e) {
			log.error("viewPettyLoanBill Error", e);
			return ERROR;
		}
	}
	/**
	 * 用户合同信息查询(账户概览页面查看合同)
	 * 中国邮政储蓄银行小额贷款额度借款支用单
	 * 根据dueId	借据ID
	 * @return 合同里的相关要素
	 * @throws Exception 
	 */
	@RequestMapping("yczgd_viewContract")
	public String viewContract(HttpServletRequest request) {
		try {
			String dueId=request.getParameter("dueId");
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userOnlyId", getUserOnlyId(request));
	 		map.put("dueId", dueId);
 			String result = WildflyBeanFactory.getYCZgdQueryClient().queryViewContract(map);
			log.info("viewContract result:"+result);
			JSONObject js=JSONObject.fromObject(result);
			request.setAttribute("js", js);
			return "yc/viewPettyLoanBill";
		} catch (Exception e) {
			log.error("viewContract Error", e);
			return ERROR;
		}
	}
	@RequestMapping("yczgd_viewPxContract")
	public String viewPxContract(HttpServletRequest request) {
		try {
			BigDecimal applyAmount=Convert.toBigDecimal(request.getParameter("applyAmount"));
			String  userOnlyId=getUserOnlyId(request);;
			String result = WildflyBeanFactory.getLoanInterfaceClient().queryContractInfo(applyAmount, userOnlyId);
			log.info("viewPxContract result:"+result);
			JSONObject js=JSONObject.fromObject(result);
			request.setAttribute("js", js);
			return "yc/yc_zgdLoanContract";
		} catch (Exception e) {
			log.error("viewPxContract Error", e);
			return ERROR;
		}
	}
	//个人信息查询及留存授权书字段获取
	@RequestMapping("yczgd_perInfoRetaPower")
	public String perInfoRetaPower(HttpServletRequest request){
		try {
			Map<String,String > dataMap=new HashMap<String,String>();
			String userName="";
			String certNo="";
			dataMap.put("userName", userName);
			dataMap.put("certNo", certNo);
			
			String dateTime=UserTools.getStrCurrentDate();
			dataMap.put("year", dateTime.substring(0, 4));
			dataMap.put("mouth", dateTime.substring(5, 7));
			dataMap.put("date", dateTime.substring(8));			
	
			String userOnlyId=getUserOnlyId(request);
			if(StringUtils.isNotBlank(userOnlyId)){
				ZgdQueryClient client=WildflyBeanFactory.getZgdQueryClient();				
				String result=client.queryUserInfo(userOnlyId);
				JSONObject json=JSONObject.fromObject(result);
				String code=json.getString("flag");
				if("0000".equals(code)){
					userName=json.getString("userName");
					certNo=json.getString("certNo");
					dataMap.put("userName", userName);
					dataMap.put("certNo", certNo);
				}
			}
			request.setAttribute("dataMap", dataMap);
		} catch (Exception e) {
			log.info("perInfoRetaPower error:", e);
		}
		return "yc/perInfoRetaPower";
	}
	/**
	 * 服务开通协议
	 * 邮乐网服务协议
	 * @throws Exception 
	 */
	@RequestMapping("yczgd_fwktxy")
	public String fwktxy(HttpServletRequest request) {
		log.info("fwktxy begin");
		try {
			return "yc/viewFwktxy";
		} catch (Exception e) {
			log.error("viewPettyLoan Error", e);
			return ERROR;
		}
	}
	/**
	 * 更新数据库到期提醒周期内弹框次数
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("zgd_updateExpireRemind")
	@ResponseBody
	public JSONPObject updateExpireRemindCount(HttpServletRequest request,@RequestParam String jsoncallback){
		try {
			String userOnlyId = getUserOnlyId(request);
//			String userOnlyId = "10000000391";
			String expireRemind = request.getParameter("remindCount");
			String result = WildflyBeanFactory.getAccountClient().updExpireRemindCount(userOnlyId,expireRemind);		
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
			
		} catch (Exception e) {
			log.info("updateExpireRemindCount error:", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
	
	@RequestMapping("test")
	@ResponseBody
	public String test(@RequestParam(required=false, value="name", defaultValue="world") String name) {
		return String.format("{\"result\":\"hello %s\"}", name);
	}
	
	@RequestMapping("testUser")
	@ResponseBody
	public String testUser(HttpServletRequest request) throws Exception {
		return String.format("{\"result\":\"hello %s\"}", getUserOnlyId(request));
	}
	
	/**
	 * 账户概览页 查询特殊商品（机具）借据
	 * @return
	 */
	@RequestMapping("/yczgd_queryJiJuDues")
	@ResponseBody
	public JSONPObject queryJiJuDues(HttpServletRequest request,@RequestParam String jsoncallback) {
		try {
			log.info("queryJiJuDues>>>>start" + "userOnlyId=" + getUserOnlyId(request));
			Map<String,Object> paras = new HashMap<String, Object>();
			paras.put("userOnlyId", getUserOnlyId(request));
			paras.put("currPage", request.getParameter("currPage"));
			paras.put("pageSize", request.getParameter("pageSize"));
			String result = WildflyBeanFactory.getYCZgdQueryClient().queryJiJuDues(paras);
			return new JSONPObject(jsoncallback,UhjWebJsonUtil.parseObjToJson(result));
		} catch (Exception e) {
			log.error("queryJiJuDues Error", e);
			return new JSONPObject(jsoncallback,e);
		}
	}
}