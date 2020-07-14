package com.ule.uhj.app.zgd.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ule.uhj.app.zgd.dao.AccountInfoMapper;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CustomerInfoMapper;
import com.ule.uhj.app.zgd.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.app.zgd.model.AccountInfo;
import com.ule.uhj.app.zgd.model.AccountInfoExample;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.CustomerInfo;
import com.ule.uhj.app.zgd.model.CustomerInfoExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.CarrierOperatorAuthService;
import com.ule.uhj.app.zgd.service.CreditRuleService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.http.HttpClientUtil;

@Service("carrierOperatorService")
public class CarrierOperatorServiceImpl implements CarrierOperatorAuthService{
	private static Logger log = LoggerFactory.getLogger(CarrierOperatorServiceImpl.class);
	
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private InterfaceAccessInfoMapper interfaceAccessInfoMapper;
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	@Autowired
	private CreditRuleService creditRuleService;
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
	@Autowired
	private AccountInfoMapper accountInfoMapper;

	private static String appkey_url = PropertiesHelper.getDfs("app_interface_url");
	
	@Override
	public Map<String,Object> queryCarrierMobile(String userOnlyId) throws Exception {
		log.info("queryCarrierMobile userOnlyId:"+userOnlyId);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userOnlyId", userOnlyId);
		List<Map<String,Object>> phones = customerInfoMapper.queryCustomerInfo(param);
		if(phones!=null&&phones.size()>0){
			resultMap = phones.get(0);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> saveRequestData(String userOnlyId,String flag,Map<String,Object> requestMap)
			throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		log.info("saveRequestData request:"+userOnlyId);
		Map<String,Object> param = new HashMap<String, Object>();
		if("juxinli".equals(flag)){
			//获得安全凭证码
			param.put("userOnlyId", userOnlyId);
			param.put("transCode", UhjConstant.transCode.REPORT_TOKEN);
			saveInterfaceAccessInfo(param, requestMap);
			//获得报告状态
			param.put("userOnlyId", userOnlyId);
			param.put("transCode", UhjConstant.transCode.ACCESS_JOB_STATUS);
			saveInterfaceAccessJobStatus(param, requestMap);
			//根据用户基本信息获取用户报告数据
			param.put("userOnlyId", userOnlyId);
			param.put("transCode", UhjConstant.transCode.REPORT_DATA);
			saveInterfaceAccessInfo(param, requestMap);
			//根据用户基本信息返回移动运营商JSON原始数据的接口
			param.put("transCode", UhjConstant.transCode.RAW_DATA);
			saveInterfaceAccessInfo(param, requestMap);
			//根据用户基本信息返回电商JSON原始数据接口
//			param.put("transCode", UhjConstant.transCode.BUSSINESS_RAW_DATA);
//			saveInterfaceAccessInfo(param, requestMap);
			//修改申请表状态为等待审核
			updateCreditApply(userOnlyId);
		}
		resultMap.put("code", "0000");
		resultMap.put("msg", "保存成功");
		return resultMap;
	}
	
	private void updateCreditApply(String userOnlyId){
		CreditApplyExample applyExample = new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
		if(applys!=null&&applys.size()>0){
			CreditApply apply = applys.get(0);
			apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_NORMAL);
			apply.setUpdateTime(DateUtil.currTimeStr());
			creditApplyMapper.updateByExampleSelective(apply, applyExample);
		}
	}
	
	private void saveInterfaceAccessInfo(Map<String,Object> param,Map<String,Object> requestMap){
		String userOnlyId = Convert.toStr(param.get("userOnlyId"));
		String transCode = Convert.toStr(param.get("transCode"));
		InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
		infoWithBlobExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(transCode);
		List<InterfaceAccessInfoWithBLOBs> infoWithBlobs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
		String appId = "";
		try {
			Map<String, Object> creditMap = queryCreditApplyInfo(userOnlyId);
			appId = Convert.toStr(creditMap.get("appId"));
		} catch (Exception e) {
			log.error("saveInterfaceAccessInfo error"+e.getMessage());
		}
		if(infoWithBlobs!=null&&infoWithBlobs.size()>0){
			InterfaceAccessInfoWithBLOBs interfaceAccessInfoWithBLOBs =infoWithBlobs.get(0);
			interfaceAccessInfoWithBLOBs.setAppId(appId);
			interfaceAccessInfoWithBLOBs.setRequestInfo(JsonUtil.getJsonStringFromObject(requestMap).getBytes());
			interfaceAccessInfoWithBLOBs.setResponseInfo(null);
			interfaceAccessInfoWithBLOBs.setStatus(UhjConstant.interfaceStutas.holdon);
			interfaceAccessInfoWithBLOBs.setCreateTime(DateUtil.currTimeStr());
			interfaceAccessInfoWithBLOBs.setUpdateTime(DateUtil.currTimeStr());
			interfaceAccessInfoMapper.updateByExampleWithBLOBs(interfaceAccessInfoWithBLOBs, infoWithBlobExample);
		}else{
			InterfaceAccessInfoWithBLOBs infoWithBlob = new InterfaceAccessInfoWithBLOBs();
			infoWithBlob.setAppId(appId);
			infoWithBlob.setUserOnlyId(userOnlyId);
			infoWithBlob.setInterfaceType(transCode);
			infoWithBlob.setStatus(UhjConstant.interfaceStutas.holdon);
			infoWithBlob.setRestAccesssCount(null);
			infoWithBlob.setRequestInfo(JsonUtil.getJsonStringFromObject(requestMap).getBytes());
			infoWithBlob.setCreateTime(DateUtil.currTimeStr());
			interfaceAccessInfoMapper.insert(infoWithBlob);
		}
	}
	
	private void saveInterfaceAccessJobStatus(Map<String,Object> param,Map<String,Object> requestMap){
		String userOnlyId = Convert.toStr(param.get("userOnlyId"));
		String transCode = Convert.toStr(param.get("transCode"));
		InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
		infoWithBlobExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(transCode);
		List<InterfaceAccessInfoWithBLOBs> infoWithBlobs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
		String appId = "";
		try {
			Map<String, Object> creditMap = queryCreditApplyInfo(userOnlyId);
			appId = Convert.toStr(creditMap.get("appId"));
		} catch (Exception e) {
			log.error("saveInterfaceAccessInfo error"+e.getMessage());
		}
		if(infoWithBlobs!=null&&infoWithBlobs.size()>0){
			InterfaceAccessInfoWithBLOBs interfaceAccessInfoWithBLOBs =infoWithBlobs.get(0);
			interfaceAccessInfoWithBLOBs.setAppId(appId);
			interfaceAccessInfoWithBLOBs.setRequestInfo(JsonUtil.getJsonStringFromObject(requestMap).getBytes());
			interfaceAccessInfoWithBLOBs.setResponseInfo(null);
			interfaceAccessInfoWithBLOBs.setStatus(UhjConstant.interfaceStutas.holdon);
			interfaceAccessInfoWithBLOBs.setCreateTime(DateUtil.currTimeStr());
			interfaceAccessInfoWithBLOBs.setUpdateTime(DateUtil.currTimeStr());
			interfaceAccessInfoMapper.updateByExampleWithBLOBs(interfaceAccessInfoWithBLOBs, infoWithBlobExample);
		}else{
			InterfaceAccessInfoWithBLOBs infoWithBlob = new InterfaceAccessInfoWithBLOBs();
			infoWithBlob.setAppId(appId);
			infoWithBlob.setUserOnlyId(userOnlyId);
			infoWithBlob.setInterfaceType(transCode);
			infoWithBlob.setStatus(UhjConstant.interfaceStutas.holdon);
			infoWithBlob.setRestAccesssCount(null);
			infoWithBlob.setRequestInfo(JsonUtil.getJsonStringFromObject(requestMap).getBytes());
			infoWithBlob.setCreateTime(DateUtil.currTimeStr());
			interfaceAccessInfoMapper.insert(infoWithBlob);
		}
	}

	@Override
	public Map<String, Object> queryJuXinLiData(String userOnlyId)
			throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		log.info("queryJuXinLiData userOnlyId:"+userOnlyId);
		List<String> interfaceTypes = new ArrayList<String>();
		interfaceTypes.add(UhjConstant.transCode.REPORT_DATA);
		interfaceTypes.add(UhjConstant.transCode.RAW_DATA);
		InterfaceAccessInfoExample infoWithBlobBusRawExample = new InterfaceAccessInfoExample();
		infoWithBlobBusRawExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeIn(interfaceTypes).andStatusEqualTo(UhjConstant.interfaceStutas.success);
		List<InterfaceAccessInfoWithBLOBs> infoWithBlobBusRaws = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobBusRawExample);
		if(infoWithBlobBusRaws!=null&&infoWithBlobBusRaws.size()==2){
			resultMap.put("code", "0000");
			resultMap.put("msg", "查询报告数据成功");
		}else{
			if(infoWithBlobBusRaws!=null&&checkDate(infoWithBlobBusRaws.get(0).getUpdateTime())){
				resultMap.put("code", "0002");
				resultMap.put("msg", "获取报告数据失败");
			}
			resultMap.put("code", "0001");
			resultMap.put("msg", "未获取报告数据");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> querySuanhuaData(String userOnlyId)
			throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		log.info("queryJuXinLiData userOnlyId:"+userOnlyId);
		InterfaceAccessInfoExample infoWithBlobBusRawExample = new InterfaceAccessInfoExample();
		infoWithBlobBusRawExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(UhjConstant.transCode.QUERY_REPORT);
		List<InterfaceAccessInfoWithBLOBs> infoWithBlobBusRaws = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobBusRawExample);
		if(infoWithBlobBusRaws!=null&&infoWithBlobBusRaws.size()>0){
			byte[] responseStr = infoWithBlobBusRaws.get(0).getResponseInfo();
			if(responseStr!=null&&responseStr.length>0){
				resultMap.put("code", "0000");
				resultMap.put("msg", "查询报告数据成功");
			}else{
				resultMap.put("code", "0001");
				resultMap.put("msg", "未获取报告数据");
			}
		}
		return resultMap;
	}

	@Override
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestData(String userOnlyId) throws Exception {
		List<String> interfaceTypes = new ArrayList<String>();
		interfaceTypes.add(UhjConstant.transCode.REPORT_DATA);
		interfaceTypes.add(UhjConstant.transCode.RAW_DATA);
//		interfaceTypes.add(UhjConstant.transCode.QUERY_REPORT);
		InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
		List<String> statusList = new ArrayList<String>();
		statusList.add(UhjConstant.interfaceStutas.faild);
		statusList.add(UhjConstant.interfaceStutas.holdon);
		infoWithBlobExample.createCriteria().andStatusIn(statusList).andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeIn(interfaceTypes);
		List<InterfaceAccessInfoWithBLOBs> InterfaceAccessInfoWithBLOBs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
		if(InterfaceAccessInfoWithBLOBs!=null&&InterfaceAccessInfoWithBLOBs.size()>0){
			for(InterfaceAccessInfoWithBLOBs blobs:InterfaceAccessInfoWithBLOBs){
				if(checkDate(blobs.getUpdateTime())||checkDate(blobs.getCreateTime())){
					//创建时间超过120分钟，状态改为失败
					if(!Check.isBlank(blobs.getUserOnlyId())){
						log.info("queryUnRequestData status 失败，userOnlyId"+blobs.getUserOnlyId());
//						blobs.setId(blobs.getId());
						InterfaceAccessInfoExample infoExample = new InterfaceAccessInfoExample();
						infoExample.createCriteria().andIdEqualTo(blobs.getId());
						InterfaceAccessInfoWithBLOBs record = new InterfaceAccessInfoWithBLOBs();
						record.setStatus(UhjConstant.interfaceStutas.faild);
						interfaceAccessInfoMapper.updateByExampleSelective(record, infoExample);
						//creditRule状态改为失败
						Map<String,Object> ruleMap = new HashMap<String, Object>();
						ruleMap.put("userOnlyId", blobs.getUserOnlyId());
						ruleMap.put("ruleRefId", UhjConstant.ruleRefId.operator_authorization);
						ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
						creditRuleService.saveCreditRuleService(ruleMap);
						//creditApply状态改为失败
						CreditApplyExample applyExample = new CreditApplyExample();
						applyExample.createCriteria().andUserOnlyIdEqualTo(blobs.getUserOnlyId());
						List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
						if(applys!=null&&applys.size()>0){
							CreditApply apply = applys.get(0);
							apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_OPERATOR);
							creditApplyMapper.updateByExampleSelective(apply, applyExample);
						}
						//对应的7101的状态设为失败
						InterfaceAccessInfoExample infoWithBlobsExample = new InterfaceAccessInfoExample();
						infoWithBlobsExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(UhjConstant.transCode.REPORT_TOKEN);
						List<InterfaceAccessInfoWithBLOBs> InterfaceAccessBLOBs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobsExample);
						if(InterfaceAccessBLOBs!=null&&InterfaceAccessBLOBs.size()>0){
							InterfaceAccessInfoWithBLOBs infoBlobs = InterfaceAccessBLOBs.get(0);
							infoBlobs.setStatus(UhjConstant.interfaceStutas.faild);
							interfaceAccessInfoMapper.updateByExampleWithBLOBs(infoBlobs, infoWithBlobsExample);
						}
					}
					InterfaceAccessInfoWithBLOBs.remove(blobs);
				}
			}
		}else{
			List<String> interTypes = new ArrayList<String>();
			interTypes.add(UhjConstant.transCode.REPORT_TOKEN);
			interTypes.add(UhjConstant.transCode.ACCESS_JOB_STATUS);
			for(String interfaceType:interTypes){
				InterfaceAccessInfoExample infoBlobsExample = new InterfaceAccessInfoExample();
				infoBlobsExample.createCriteria().andStatusEqualTo(UhjConstant.interfaceStutas.holdon).andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(interfaceType);
				List<InterfaceAccessInfoWithBLOBs> InterfaceAccess = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoBlobsExample);
				if(InterfaceAccess!=null&&InterfaceAccess.size()>0){
					InterfaceAccessInfoWithBLOBs infoBlobs = InterfaceAccess.get(0);
					infoBlobs.setStatus(UhjConstant.interfaceStutas.success);
					interfaceAccessInfoMapper.updateByExampleWithBLOBs(infoBlobs, infoBlobsExample);
				}
			}
		}
		return InterfaceAccessInfoWithBLOBs;
	}
	
	@Override
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestTokenData()
			throws Exception {
		InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
		List<String> interfaceTypes =new ArrayList<String>();
		interfaceTypes.add(UhjConstant.transCode.REPORT_TOKEN);
		interfaceTypes.add(UhjConstant.transCode.REPORT_BY_STOKEN);
		interfaceTypes.add(UhjConstant.transCode.TONGDUN_QUERY);
		
		infoWithBlobExample.createCriteria().andStatusEqualTo(UhjConstant.interfaceStutas.holdon).andInterfaceTypeIn(interfaceTypes);
		return interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
	}
	
	@Override
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestSuanHuaData()
			throws Exception {
		InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
		infoWithBlobExample.createCriteria().andStatusEqualTo(UhjConstant.interfaceStutas.holdon).andInterfaceTypeEqualTo(UhjConstant.transCode.QUERY_REPORT);
		return interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
	}

	/**
	 * 校验时间是否距离当前时间超过24小时,超过返回true
	 * @param date
	 * @return
	 */
	public boolean checkDate(String dateStr){
		if(!Check.isBlank(dateStr)){
			String currDateStr = DateUtil.currTimeStr();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = format.parse(dateStr);
				Date currDate = format.parse(currDateStr);
				long time = currDate.getTime() - date.getTime();
				return time>24*60*60*1000;
			} catch (ParseException e) {
				log.error("checkDate error!",e);
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> queryCreditApplyInfo(String userOnlyId)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CreditApplyExample applyExample = new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
		String appId = "";
		if(applys!=null&&applys.size()>0){
			appId = applys.get(0).getId();
		}
		CustomerInfoExample infoExample = new CustomerInfoExample();
		infoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(infoExample);
		if(customerInfos!=null&&customerInfos.size()>0){
			String orgCode = customerInfos.get(0).getOrgCode();
			resultMap.put("orgCode", orgCode);
		}
		resultMap.put("appId", appId);
		return resultMap;
	}

	@Override
	public void updateReportData(String userOnlyId) throws Exception {
		log.info("updateReportData userOnlyId"+userOnlyId);
		List<String> interfaceTypes = new ArrayList<String>();
		interfaceTypes.add(UhjConstant.transCode.REPORT_TOKEN);
		interfaceTypes.add(UhjConstant.transCode.ACCESS_JOB_STATUS);
		for(String interfaceType:interfaceTypes){
			InterfaceAccessInfoExample infoWithBlobsExample = new InterfaceAccessInfoExample();
			infoWithBlobsExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(interfaceType);
			List<InterfaceAccessInfoWithBLOBs> InterfaceAccessBLOBs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobsExample);
			if(InterfaceAccessBLOBs!=null&&InterfaceAccessBLOBs.size()>0){
				for(InterfaceAccessInfoWithBLOBs blobs:InterfaceAccessBLOBs){
					blobs.setStatus("3");
					interfaceAccessInfoMapper.updateByExampleWithBLOBs(blobs, infoWithBlobsExample);
				}
			}
		}
	}
	
	@Override
	public void updateReportDataFaild(String userOnlyId) throws Exception {
		log.info("updateReportDataFaild userOnlyId"+userOnlyId);
		List<String> interfaceTypes = new ArrayList<String>();
		interfaceTypes.add(UhjConstant.transCode.REPORT_TOKEN);
		interfaceTypes.add(UhjConstant.transCode.ACCESS_JOB_STATUS);
		interfaceTypes.add(UhjConstant.transCode.REPORT_DATA);
		interfaceTypes.add(UhjConstant.transCode.RAW_DATA);
		for(String interfaceType:interfaceTypes){
			InterfaceAccessInfoExample infoWithBlobsExample = new InterfaceAccessInfoExample();
			infoWithBlobsExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(interfaceType);
			List<InterfaceAccessInfoWithBLOBs> InterfaceAccessBLOBs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobsExample);
			if(InterfaceAccessBLOBs!=null&&InterfaceAccessBLOBs.size()>0){
				InterfaceAccessInfoWithBLOBs infoBlobs = InterfaceAccessBLOBs.get(0);
				infoBlobs.setStatus("2");
				interfaceAccessInfoMapper.updateByExampleWithBLOBs(infoBlobs, infoWithBlobsExample);
			}
		}
		//creditRule状态改为失败
		Map<String,Object> ruleMap = new HashMap<String, Object>();
		ruleMap.put("userOnlyId", userOnlyId);
		ruleMap.put("ruleRefId", UhjConstant.ruleRefId.operator_authorization);
		ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
		creditRuleService.saveCreditRuleService(ruleMap);
		//creditApply状态改为失败
		CreditApplyExample applyExample = new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
		if(applys!=null&&applys.size()>0){
			CreditApply apply = applys.get(0);
			apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_OPERATOR);
			creditApplyMapper.updateByExampleSelective(apply, applyExample);
		}
	}

	@Override
	public void updateApplyStatus(String userOnlyId,String authorizationOrg) throws Exception {
		//creditRule状态改为成功
		Map<String,Object> ruleMap = new HashMap<String, Object>();
		ruleMap.put("userOnlyId", userOnlyId);
		ruleMap.put("ruleRefId", UhjConstant.ruleRefId.operator_authorization);
		ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_true);
		creditRuleService.saveCreditRuleService(ruleMap);
		
		//确定生成是 聚信立报告，还是算话
	    ruleMap.put("ruleRefId", UhjConstant.ruleRefId.authorization_org);
	    ruleMap.put("ruleOutput", authorizationOrg);
	    creditRuleService.saveCreditRuleService(ruleMap);
	    
		//creditApply状态改为等待审核
		CreditApplyExample applyExample = new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
		if(applys!=null&&applys.size()>0){
			CreditApply apply = applys.get(0);
			apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_REVIEW);
			creditApplyMapper.updateByExampleSelective(apply, applyExample);
		}
	}
	@Override
	public void updateApplyStatusNew(String userOnlyId,String type) throws Exception {
		//creditRule状态改为成功
		Map<String,Object> ruleMap = new HashMap<String, Object>();
		ruleMap.put("userOnlyId", userOnlyId);
		ruleMap.put("ruleRefId", UhjConstant.ruleRefId.operator_authorization);
		if("PY".equals(type)){
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_skip2py);
		}else{
			ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_skip);
		}
		creditRuleService.saveCreditRuleService(ruleMap);
		
		//creditApply状态改为等待审核
		CreditApplyExample applyExample = new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
		if(applys!=null&&applys.size()>0){
			CreditApply apply = applys.get(0);
			apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_REVIEW);
			creditApplyMapper.updateByExampleSelective(apply, applyExample);
		}
		List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
		AccountInfoExample accountInfoExample = new AccountInfoExample();
		accountInfoExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		accountInfos = accountInfoMapper.selectByExample(accountInfoExample);
		if(accountInfos!=null && accountInfos.size()>0){
			AccountInfo info=accountInfos.get(0);
			info.setApplyStatus((short) 3);
			accountInfoMapper.updateByPrimaryKey(info);
		}
	}

	/**
	 * 查询未拉取到报告的数据
	 */
	@Override
	public boolean queryFaildRequest(String userOnlyId) throws Exception {
		List<String> interfaceTypes = new ArrayList<String>();
		interfaceTypes.add(UhjConstant.transCode.REPORT_DATA);
		interfaceTypes.add(UhjConstant.transCode.RAW_DATA);
		InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
		infoWithBlobExample.createCriteria().andStatusEqualTo(UhjConstant.interfaceStutas.holdon).andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeIn(interfaceTypes);
		List<InterfaceAccessInfoWithBLOBs> InterfaceAccessInfoWithBLOBs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
		if(InterfaceAccessInfoWithBLOBs!=null&&InterfaceAccessInfoWithBLOBs.size()>0){
			for(InterfaceAccessInfoWithBLOBs blobs:InterfaceAccessInfoWithBLOBs){
				if(checkDate(blobs.getUpdateTime())||checkDate(blobs.getCreateTime())){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<InterfaceAccessInfoWithBLOBs> queryUnRequestJobStatus(String userOnlyId)
			throws Exception {
		InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
		infoWithBlobExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andStatusEqualTo(UhjConstant.interfaceStutas.holdon).andInterfaceTypeEqualTo(UhjConstant.transCode.ACCESS_JOB_STATUS);
		return interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
	}

	@Override
	public boolean querySuanHuaInfo(InterfaceAccessInfoWithBLOBs blobs) {
			boolean rs=false;
			if(blobs!=null){
			
			try {
				
				String userOnlyId = blobs.getUserOnlyId();
				String reportByStokenResult="";
				String requestInfo = new String(blobs.getRequestInfo());
				Map<String,Object> requestMap = JsonUtil.getMapFromJsonString(requestInfo);
				String stoken = Convert.toStr(requestMap.get("stoken"));
				
				if(checkDate1(blobs.getUpdateTime())){//报告超时
					
					//creditRule状态改为失败
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.operator_authorization);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					//creditApply状态改为失败
					CreditApplyExample applyExample = new CreditApplyExample();
					applyExample.createCriteria().andUserOnlyIdEqualTo(blobs.getUserOnlyId());
					List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
					if(applys!=null&&applys.size()>0){
						CreditApply apply = applys.get(0);
						apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_OPERATOR);
						creditApplyMapper.updateByExampleSelective(apply, applyExample);
					}
					//对应的7101的状态设为失败					
					blobs.setStatus(UhjConstant.interfaceStutas.faild);
					interfaceAccessInfoService.saveInterfaceData(blobs);
					
				}else{
					//取运营商报告
					Map<String, String> paramMap =new HashMap<String, String>();
					paramMap.put("stoken", stoken);
					paramMap.put("userOnlyId", blobs.getUserOnlyId());
					paramMap.put("tranzCode", UhjConstant.transCode.REPORT_BY_STOKEN);
					log.info("reportByStoken param:"+paramMap.toString());
					reportByStokenResult = HttpClientUtil.sendPost(appkey_url, paramMap);
					log.info("reportByStoken result:"+reportByStokenResult);
					
					Map<String,Object> reportByStokenMap = JsonUtil.getMapFromJsonString(reportByStokenResult);
					if("001".equals(reportByStokenMap.get("statusCode"))){
						String data = Convert.toStr(reportByStokenMap.get("data"));
						
						InterfaceAccessInfoWithBLOBs interfaceAccessBlob = new InterfaceAccessInfoWithBLOBs();
						interfaceAccessBlob.setUserOnlyId(blobs.getUserOnlyId());
						interfaceAccessBlob.setAppId(blobs.getAppId());
						interfaceAccessBlob.setInterfaceType(UhjConstant.transCode.REPORT_BY_STOKEN);
						interfaceAccessBlob.setRequestInfo(blobs.getRequestInfo());
						interfaceAccessBlob.setResponseInfo(JSONObject.fromObject(data).toString().getBytes());
						interfaceAccessBlob.setCreateTime(DateUtil.currTimeStr());
						interfaceAccessBlob.setUpdateTime(DateUtil.currTimeStr());
						interfaceAccessBlob.setStatus(UhjConstant.interfaceStutas.success);
						interfaceAccessInfoService.saveInterfaceData(interfaceAccessBlob);
						rs=true;
						
					}
				}
				
			} catch (Exception e) {
				log.error("querySuanHuaInfo!",e);
			}
			
		}
		return rs;
	}
	
	@Override
	public boolean queryTongdunInfo(InterfaceAccessInfoWithBLOBs blobs) {
			boolean rs=false;
			if(blobs!=null){
			
			try {
				
				String userOnlyId = blobs.getUserOnlyId();
				String reportByStokenResult="";
				String requestInfo = new String(blobs.getRequestInfo());
				Map<String,Object> requestMap = JsonUtil.getMapFromJsonString(requestInfo);
				String task_id = Convert.toStr(requestMap.get("task_id"));
				
				if(checkDate1(blobs.getUpdateTime())){//报告超时
					
					//creditRule状态改为失败
					Map<String,Object> ruleMap = new HashMap<String, Object>();
					ruleMap.put("userOnlyId", userOnlyId);
					ruleMap.put("ruleRefId", UhjConstant.ruleRefId.operator_authorization);
					ruleMap.put("ruleOutput", UhjConstant.ruleOutput.ruleOutput_false);
					creditRuleService.saveCreditRuleService(ruleMap);
					//creditApply状态改为失败
					CreditApplyExample applyExample = new CreditApplyExample();
					applyExample.createCriteria().andUserOnlyIdEqualTo(blobs.getUserOnlyId());
					List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
					if(applys!=null&&applys.size()>0){
						CreditApply apply = applys.get(0);
						apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_OPERATOR);
						creditApplyMapper.updateByExampleSelective(apply, applyExample);
					}
					//对应的7101的状态设为失败					
					blobs.setStatus(UhjConstant.interfaceStutas.faild);
					interfaceAccessInfoService.saveInterfaceData(blobs);
					
				}else{
					//取运营商报告
					Map<String, String> paramMap =new HashMap<String, String>();
					paramMap.put("task_id", task_id);
					paramMap.put("userOnlyId", blobs.getUserOnlyId());
					paramMap.put("tranzCode", UhjConstant.transCode.TONGDUN_QUERY);
					log.info("reportByStoken param:"+paramMap.toString());
					reportByStokenResult = HttpClientUtil.sendPost(appkey_url, paramMap);
					log.info("reportByStoken result:"+reportByStokenResult);
					
					Map<String,Object> reportByStokenMap = JsonUtil.getMapFromJsonString(reportByStokenResult);
					if("0".equals(Convert.toStr(reportByStokenMap.get("code")))){
						String data = Convert.toStr(reportByStokenMap.get("data"));
						
						InterfaceAccessInfoWithBLOBs interfaceAccessBlob = new InterfaceAccessInfoWithBLOBs();
						interfaceAccessBlob.setUserOnlyId(blobs.getUserOnlyId());
						interfaceAccessBlob.setAppId(blobs.getAppId());
						interfaceAccessBlob.setInterfaceType(UhjConstant.transCode.TONGDUN_QUERY);
						interfaceAccessBlob.setRequestInfo(blobs.getRequestInfo());
						interfaceAccessBlob.setResponseInfo(JSONObject.fromObject(reportByStokenResult).toString().getBytes());
						interfaceAccessBlob.setCreateTime(DateUtil.currTimeStr());
						interfaceAccessBlob.setUpdateTime(DateUtil.currTimeStr());
						interfaceAccessBlob.setStatus(UhjConstant.interfaceStutas.success);
						interfaceAccessInfoService.saveInterfaceData(interfaceAccessBlob);
						rs=true;
						
					}
				}
				
			} catch (Exception e) {
				log.error("querySuanHuaInfo!",e);
			}
			
		}
		return rs;
	}
	
	/**
	 * 校验时间是否距离当前时间超过24小时,超过返回true
	 * @param date
	 * @return
	 */
	public boolean checkDate1(String dateStr){
		if(!Check.isBlank(dateStr)){
			String currDateStr = DateUtil.currTimeStr();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = format.parse(dateStr);
				Date currDate = format.parse(currDateStr);
				long time = currDate.getTime() - date.getTime();
				return time>60*60*1000;
			} catch (ParseException e) {
				log.error("checkDate error!",e);
			}
		}
		return false;
	}

	/**
	 * 通用查询接口方法
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<InterfaceAccessInfoWithBLOBs> queryIntegerfaceAccessInfo(InterfaceAccessInfoExample example)
			throws Exception {
		List<InterfaceAccessInfoWithBLOBs> InterfaceAccessInfoWithBLOBs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(example);
		return InterfaceAccessInfoWithBLOBs;
	}

	@Override
	public boolean queryAndParseQHIntegerfaceAccessInfo1(String userOnlyId) throws Exception {
		// 查询接口数据
		InterfaceAccessInfoExample example = new InterfaceAccessInfoExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(UhjConstant.transCode.QIAN_HAI_VERIFYPHONE);
		example.setOrderByClause(" create_time desc ");
		List<InterfaceAccessInfoWithBLOBs> InterfaceAccessInfoWithBLOBs = queryIntegerfaceAccessInfo(example);
		if(!CollectionUtils.isEmpty(InterfaceAccessInfoWithBLOBs)) {
			InterfaceAccessInfoWithBLOBs interfaceAccessInfoWithBLOBs = InterfaceAccessInfoWithBLOBs.get(0);
			String response =  new String(interfaceAccessInfoWithBLOBs.getResponseInfo(), "UTF-8");
			log.info("queryAndParseQHIntegerfaceAccessInfo 前海json数据是："+response);
			
			if("fail".equals(response)){
				String requestInfo =  new String(interfaceAccessInfoWithBLOBs.getRequestInfo(), "UTF-8");
				JSONObject json = JSONObject.fromObject(requestInfo);
				String result =null;
				
				Map<String, String> param =new HashMap<String, String>();
				param.put("appId", json.getString("appId"));
				param.put("idCard", json.getString("idCard"));
				param.put("name", json.getString("name"));
				param.put("phone", json.getString("phone"));
				param.put("userOnlyId", "userOnlyId");
				param.put("tranzCode", json.getString("tranzCode"));
				
				try {				
					log.info("verifyPhone param:"+param.toString());
					result = HttpClientUtil.sendPost(appkey_url, param);
					log.info("verifyPhone result:"+result);
					
					interfaceAccessInfoWithBLOBs.setResponseInfo(JSONObject.fromObject(result).toString().getBytes());
					interfaceAccessInfoWithBLOBs.setUpdateTime(DateUtil.currTimeStr());
					interfaceAccessInfoMapper.updateByExampleSelective(interfaceAccessInfoWithBLOBs, example);;
				} catch (Exception e) {
					result ="fail";
				}
			}
		}else{
			return false;
		}
		return true;
	}
	
	@Override
	public boolean queryAndParsePengyuanIntegerfaceAccessInfo(String userOnlyId) throws Exception {
		// 查询接口数据
		InterfaceAccessInfoExample example = new InterfaceAccessInfoExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(UhjConstant.transCode.PENGYUAN_VERIFYPHONE);
		example.setOrderByClause(" create_time desc ");
		List<InterfaceAccessInfoWithBLOBs> InterfaceAccessInfoWithBLOBs = queryIntegerfaceAccessInfo(example);
		if(!CollectionUtils.isEmpty(InterfaceAccessInfoWithBLOBs)) {
			InterfaceAccessInfoWithBLOBs interfaceAccessInfoWithBLOBs = InterfaceAccessInfoWithBLOBs.get(0);
			String response =  new String(interfaceAccessInfoWithBLOBs.getResponseInfo(), "UTF-8");
			log.info("queryAndParsePengyuanIntegerfaceAccessInfo 鹏元在网时长json数据是："+response);
			
			if("fail".equals(response)){
				String requestInfo =  new String(interfaceAccessInfoWithBLOBs.getRequestInfo(), "UTF-8");
				JSONObject json = JSONObject.fromObject(requestInfo);
				String result =null;
				
				Map<String, String> param =new HashMap<String, String>();
				param.put("appId", json.getString("appId"));
				param.put("idCard", json.getString("idCard"));
				param.put("name", json.getString("name"));
				param.put("phone", json.getString("phone"));
				param.put("userOnlyId", "userOnlyId");
				param.put("tranzCode", json.getString("tranzCode"));
				
				try {				
					log.info("verifyPhone param:"+param.toString());
					result = HttpClientUtil.sendPost(appkey_url, param);
					log.info("verifyPhone result:"+result);
					
					interfaceAccessInfoWithBLOBs.setResponseInfo(JSONObject.fromObject(result).toString().getBytes());
					interfaceAccessInfoWithBLOBs.setUpdateTime(DateUtil.currTimeStr());
					interfaceAccessInfoMapper.updateByExampleSelective(interfaceAccessInfoWithBLOBs, example);
				} catch (Exception e) {
					result ="fail";
				}
			}
		}else {
			return false;
		}
		return true;
	}
	
	
	@Override
	public boolean queryAndParseQHIntegerfaceAccessInfo(String userOnlyId) throws Exception {
		// 查询接口数据
		InterfaceAccessInfoExample example = new InterfaceAccessInfoExample();
		example.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeEqualTo(UhjConstant.transCode.QIAN_HAI_VERIFYPHONE);;
		List<InterfaceAccessInfoWithBLOBs> InterfaceAccessInfoWithBLOBs = queryIntegerfaceAccessInfo(example);
		if(!CollectionUtils.isEmpty(InterfaceAccessInfoWithBLOBs)) {
			InterfaceAccessInfoWithBLOBs interfaceAccessInfoWithBLOBs = InterfaceAccessInfoWithBLOBs.get(0);
			String response =  new String(interfaceAccessInfoWithBLOBs.getResponseInfo(), "UTF-8");
			log.info("queryAndParseQHIntegerfaceAccessInfo 前海json数据是："+response);
			JSONObject json = JSONObject.fromObject(response);
			
			//开始解析json并最终确定返回值
			boolean uniqueFlag = false;
			boolean useTimeFlag = false;
			if(null != json && json.containsKey("checkResult") && json.containsKey("inUseTime")) {
				JSONObject uniqueObject = json.getJSONObject("checkResult");
				JSONObject useTimeObject = json.getJSONObject("inUseTime");
				// 解析：手机号码姓名证件号一致性验证
				if(null != uniqueObject && uniqueObject.containsKey("records")) {
					JSONArray records = uniqueObject.getJSONArray("records");
					if(null != records && !records.isEmpty()) {
						JSONObject record = records.getJSONObject(0);
						if(null != record && record.containsKey("erCode") && record.containsKey("checkResult")) {
							String erCode = record.getString("erCode");
							String checkResult = record.getString("checkResult");
							log.info("uniqueObject  erCode："+erCode+" checkResult: "+checkResult);
							if(!StringUtils.isEmpty(erCode) && !StringUtils.isEmpty(checkResult)) {
								if("E000000".equals(erCode)) {
									if("00".equals(checkResult) || "11".equals(checkResult) || "66".equals(checkResult)) {
										uniqueFlag = true;
									}
								}
							}
						}
					}
				}
				// 解析：通用手机号码状态及在网时长
				if(null != useTimeObject && useTimeObject.containsKey("records")) {
					JSONArray records = useTimeObject.getJSONArray("records");
					if(null != records && !records.isEmpty()) {
						JSONObject record = records.getJSONObject(0);
						if(null != record && record.containsKey("erCode") && record.containsKey("inUseTime")) {
							String erCode = record.getString("erCode");
							String inUseTime = record.getString("inUseTime");
							log.info("useTimeObject  erCode："+erCode+" inUseTime: "+inUseTime);
							if(!StringUtils.isEmpty(erCode) && !StringUtils.isEmpty(inUseTime)) {
								if("E000000".equals(erCode)) {
									if("60".equals(inUseTime) || "6".equals(inUseTime) || "5".equals(inUseTime)) {
										useTimeFlag = true;
									}
								}
							}
						}
					}
				}
			}
			if(uniqueFlag && useTimeFlag) {
				return true;
			}
			
		}
		return false;
	}


}
