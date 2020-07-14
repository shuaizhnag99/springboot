package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.CreditReturnMapper;
import com.ule.uhj.app.zgd.dao.CustomerAddressMapper;
import com.ule.uhj.app.zgd.dao.CustomerCertMapper;
import com.ule.uhj.app.zgd.dao.CustomerIncomeMapper;
import com.ule.uhj.app.zgd.dao.CustomerStoreMapper;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.CreditReturn;
import com.ule.uhj.app.zgd.model.CreditReturnExample;
import com.ule.uhj.app.zgd.service.CreditReturnService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.ReturnReasonEnum;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonResult;

@Service
public class CreditReturnServiceImpl implements CreditReturnService {
	
	private static Logger log = LoggerFactory.getLogger(CreditReturnServiceImpl.class);
	@Autowired
	private CustomerCertMapper customerCertMapper;
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	@Autowired
	private CustomerStoreMapper customerStoreMapper;
	@Autowired
	private CustomerIncomeMapper customerIncomeMapper;
	@Autowired
	private CreditApplyMapper creditApplyMapper;
	@Autowired
	private CreditReturnMapper creditReturnMapper;
	
	
	/**
	 * 
	 * 查询审核退回需要修改的东东
	 * @param map
	 * @return String
	 */
	@Override
	public Map<String,Object> queryReviewReturn(Map<String, Object> map) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		log.info("queryReviewReturn userOnlyId:"+userOnlyId);
//		List<String> list=new ArrayList<String>();
		CreditReturnExample creditReturnExample =new CreditReturnExample();
		creditReturnExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		creditReturnExample.setOrderByClause("create_time desc");//默认按时间降序
		List<CreditReturn> creditReturnList=creditReturnMapper.selectByExample(creditReturnExample);
		if(creditReturnList!=null && creditReturnList.size()>0){
			CreditReturn ret=creditReturnList.get(0);
			log.info("queryReviewReturn ret:"+ret.toString());
			result.put("reason", ret);
			String reason = ret.getReason();
			List<String> reasonList = new ArrayList<String>();
			if(!Check.isBlank(reason)){
				String[] reasons = reason.split(",");
				for(int i = 0 ; i < reasons.length;i++){
					reasonList.add(reasons[i]);
					for(ReturnReasonEnum reasonEnum:ReturnReasonEnum.values()){
						if(reasonEnum.getReasonCode().equals(reasons[i])){
							reasonList.add(reasonEnum.getMessage());
						}
					}
				}
				result.put("reasonList", reasonList);
			}
		}
		return result;
	}
	
	@Override
	public String saveReviewReturn(Map<String, Object> map) throws Exception {
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		/*String owner=Convert.toStr(map.get("owner"));//owner   店铺所有人(地址的用户类型)   1 是本人  2是配偶       地址的用户类型和地址类型( 1-借款人   3-店铺地址)来判断是本人的
		String addressType=UhjConstant.addressType.store_address;//地址类型
		String propertyType=Convert.toStr(map.get("propertyType"));//店铺房产类型  0是自有 1：租赁    地址表
		String storeAddress=Convert.toStr(map.get("storeAddress"));//店铺地址     地址表
		
//		String storeInner=Convert.toStr(map.get("storeInner"));//店铺内部照片    已经保存早图片表了不用保存
//		String storeOutside=Convert.toStr(map.get("storeOutside"));//店铺外部照片   已经保存早图片表了不用保存
		
		String incomeType=Convert.toStr(map.get("incomeType"));//收入类型     收入表
		String incomeSubType=Convert.toStr(map.get("incomeSubType"));//收入子类型    收入表
		String description=Convert.toStr(map.get("description"));//备注        收入表
		BigDecimal incomeAmount=Convert.toBigDecimal(map.get("incomeAmount"));//金额      收入表
		
		String storeInnerGPS=Convert.toStr(map.get("storeInnerGPS"));//店铺内部GPS定位信息    店铺表
		String storeOutsideGPS=Convert.toStr(map.get("storeOutsideGPS"));//店铺外部GPS定位信息     店铺表
		String storeName=Convert.toStr(map.get("storeName"));//店铺名称     店铺表     要和地址表关联地址ID
		BigDecimal storeArea=Convert.toBigDecimal(map.get("storeArea"));//店铺面积          店铺表
		BigDecimal storeTurnover=Convert.toBigDecimal(map.get("storeTurnover"));//店铺营业额（每日）   店铺表
		BigDecimal rent=Convert.toBigDecimal(map.get("rent"));//租赁金额      店铺表
		String businessLicense=Convert.toStr(map.get("businessLicense"));//营业执照   店铺表
		
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String addressId=Convert.toStr(map.get("addressId"));//店铺地址     地址表
		
		CustomerStoreExample customerStoreExample =new CustomerStoreExample();
		customerStoreExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CustomerStore> customerStoreList=customerStoreMapper.selectByExample(customerStoreExample);
		if(customerStoreList!=null && customerStoreList.size()>0){
			CustomerStore customerStore =customerStoreList.get(0);
			customerStore.setCreateTime(DateUtil.currTimeStr());
//			customerStore.setAddressId(addressId);
			customerStore.setBusinessLicenceNo(businessLicense);
			customerStore.setLeasingAmount(rent);
			customerStore.setStoreArea(storeArea);
			customerStore.setStoreInnerGps(storeInnerGPS);
			customerStore.setStoreName(storeName);
			customerStore.setStoreOuterGps(storeOutsideGPS);
			customerStore.setStoreTurnover(storeTurnover);
			customerStore.setUserOnlyId(userOnlyId);
			customerStoreMapper.updateByExampleSelective(customerStore, customerStoreExample);
		}*/
		CreditApplyExample applyExample = new CreditApplyExample();
		applyExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
		List<CreditApply> applys = creditApplyMapper.selectByExample(applyExample);
		if(applys!=null&&applys.size()>0){
			CreditApply apply = applys.get(0);
			apply.setStatus(UhjConstant.applyStatus.APPLY_STATUS_REVIEW);
			apply.setUpdateTime(DateUtil.currTimeStr());
			creditApplyMapper.updateByExampleSelective(apply, applyExample);
		}
		return JsonResult.getInstance().addOk().toString();
	}
	
	
}
