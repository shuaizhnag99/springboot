package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.CountryAddressMapper;
import com.ule.uhj.app.zgd.dao.CustomerAddressMapper;
import com.ule.uhj.app.zgd.model.CountryAddress;
import com.ule.uhj.app.zgd.model.CountryAddressExample;
import com.ule.uhj.app.zgd.model.CustomerAddress;
import com.ule.uhj.app.zgd.model.CustomerAddressExample;
import com.ule.uhj.app.zgd.service.CustomerAddressService;
import com.ule.uhj.app.zgd.util.DateUtil;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.util.Check;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.http.HttpClientUtil;

@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {
	
	private static Logger log = LoggerFactory.getLogger(CustomerAddressServiceImpl.class);
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	@Autowired
	private CountryAddressMapper countryAddressMapper;
	
	@Override
	public String saveCustomerAddress(Map<String, Object> map) throws Exception {
		////店铺地址信息有可能被审核退回重新修改
		log.info("saveCustomerAddress map:"+map);
		Map<String,Object> addressMap=new HashMap<String, Object>();
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String addressType=Convert.toStr(map.get("addressType"));
		String address=Convert.toStr(map.get("address"));
		String customerType=Convert.toStr(map.get("customerType"));
		String propertyType=Convert.toStr(map.get("propertyType"));
		String storeInnerGPS=Convert.toStr(map.get("storeInnerGPS"));
//		String contactId=Convert.toStr(map.get("contactId"));
		String addressId="";
		if(addressType.equals(UhjConstant.addressType.store_address)){
			if(!Check.isBlank(storeInnerGPS)){
				addressMap=queryBDGpsInfo(storeInnerGPS,userOnlyId);
			}
			log.info("queryBDGpsInfo addressMap:"+addressMap);
			String province=Convert.toStr(addressMap.get("province"));
			String city=Convert.toStr(addressMap.get("city"));
			String area=Convert.toStr(addressMap.get("area"));
			CustomerAddressExample customerAddressExample =new CustomerAddressExample();
			customerAddressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAddressTypeEqualTo(addressType);
			List<CustomerAddress> customerAddressList=customerAddressMapper.selectByExample(customerAddressExample);
			if(customerAddressList==null || customerAddressList.size()<=0){
				CustomerAddress customerAddress =new CustomerAddress();
				customerAddress.setAddress(address);
				customerAddress.setAddressType(addressType);
//				customerAddress.setContactId(contactId);
				customerAddress.setCreateTime(DateUtil.currTimeStr());
				customerAddress.setCustomerType(customerType);
				customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
				customerAddress.setPropertyType(propertyType);
				customerAddress.setUserOnlyId(userOnlyId);
				customerAddress.setProvince(province);
				customerAddress.setCity(city);
				customerAddress.setArea(area);
				customerAddressMapper.insertSelective(customerAddress);
				addressId=customerAddress.getId();
			}else{
				CustomerAddress customerAddress =customerAddressList.get(0);
				if(!Check.isBlank(address)){
					customerAddress.setAddress(address);
				}
				if(!Check.isBlank(customerType)){
					customerAddress.setCustomerType(customerType);
				}
				customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
				if(!Check.isBlank(propertyType)){
					customerAddress.setPropertyType(propertyType);
				}
				if(!Check.isBlank(storeInnerGPS)){
					customerAddress.setProvince(province);
					customerAddress.setCity(city);
					customerAddress.setArea(area);
				}
				customerAddress.setAddressType(addressType);
//				customerAddress.setContactId(contactId);
				customerAddress.setUserOnlyId(userOnlyId);
				customerAddress.setUpdateTime(DateUtil.currTimeStr());
				customerAddressMapper.updateByExampleSelective(customerAddress, customerAddressExample);
			}
		}else{
			addressType=UhjConstant.addressType.registration_address;
			addressMap=analysisLocation(address);
			log.info("getCountryMap addressMap:"+addressMap);
			String province=Convert.toStr(addressMap.get("province"));
			String city=Convert.toStr(addressMap.get("city"));
			String area=Convert.toStr(addressMap.get("area"));
			CustomerAddressExample customerAddressExample =new CustomerAddressExample();
			customerAddressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAddressTypeEqualTo(addressType);
			List<CustomerAddress> customerAddressList=customerAddressMapper.selectByExample(customerAddressExample);
			
			String addType=UhjConstant.addressType.home_address;
			CustomerAddressExample addressExample =new CustomerAddressExample();
			addressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAddressTypeEqualTo(addType);
			List<CustomerAddress> addressList=customerAddressMapper.selectByExample(addressExample);
			
			if(customerAddressList==null || customerAddressList.size()<=0){
				CustomerAddress customerAddress =new CustomerAddress();
				customerAddress.setAddress(address);
				customerAddress.setAddressType(addressType);
//				customerAddress.setContactId(contactId);
				customerAddress.setCreateTime(DateUtil.currTimeStr());
				customerAddress.setCustomerType(customerType);
				customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
				customerAddress.setPropertyType(propertyType);
				customerAddress.setUserOnlyId(userOnlyId);
				customerAddress.setProvince(province);
				customerAddress.setCity(city);
				customerAddress.setArea(area);
				customerAddressMapper.insertSelective(customerAddress);
			}else{
				CustomerAddress customerAddress =customerAddressList.get(0);
				customerAddress.setAddress(address);
				customerAddress.setAddressType(addressType);
//				customerAddress.setContactId(contactId);
				customerAddress.setCustomerType(customerType);
				customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
				customerAddress.setPropertyType(propertyType);
				customerAddress.setUserOnlyId(userOnlyId);
				customerAddress.setProvince(province);
				customerAddress.setCity(city);
				customerAddress.setArea(area);
				customerAddress.setUpdateTime(DateUtil.currTimeStr());
				customerAddressMapper.updateByExampleSelective(customerAddress, customerAddressExample);
			}
			
			if(addressList==null || addressList.size()<=0){
				CustomerAddress customerAddress =new CustomerAddress();
				customerAddress.setAddress(address);
				customerAddress.setAddressType(addType);
//				customerAddress.setContactId(contactId);
				customerAddress.setCreateTime(DateUtil.currTimeStr());
				customerAddress.setCustomerType(customerType);
				customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
				customerAddress.setPropertyType(propertyType);
				customerAddress.setUserOnlyId(userOnlyId);
				customerAddress.setProvince(province);
				customerAddress.setCity(city);
				customerAddress.setArea(area);
				customerAddressMapper.insertSelective(customerAddress);
			}else{
				CustomerAddress customerAddress =addressList.get(0);
				customerAddress.setAddress(address);
				customerAddress.setAddressType(addType);
//				customerAddress.setContactId(contactId);
				customerAddress.setCustomerType(customerType);
				customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
				customerAddress.setPropertyType(propertyType);
				customerAddress.setUserOnlyId(userOnlyId);
				customerAddress.setProvince(province);
				customerAddress.setCity(city);
				customerAddress.setArea(area);
				customerAddress.setUpdateTime(DateUtil.currTimeStr());
				customerAddressMapper.updateByExampleSelective(customerAddress, addressExample);
			}
			if(province==null || city==null || area==null ){
				return "1000";
			}
		}
		return addressId;
	}
	
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> queryBDGpsInfo(String location,String userOnlyId) throws Exception{
		log.info("queryBDGpsInfo location:"+location);
		Map<String, Object> bdGpsMap = new HashMap<String, Object>();
		Map<String,String> paramMap = new HashMap<String, String>();
		String appkey_url = PropertiesHelper.getDfs("app_interface_url");
		paramMap.put("location", location);
		paramMap.put("tranzCode", UhjConstant.transCode.BAIDU_GPS_INFO);
		paramMap.put("userOnlyId", userOnlyId);
		log.info("queryBDGpsInfo paramMap:"+paramMap);
		String returnStr = HttpClientUtil.sendPost(appkey_url, paramMap);
		if(!Check.isBlank(returnStr)){
			Map<String,Object> gpsMap = JsonUtil.getMapFromJsonString(returnStr);
			String resultStr = Convert.toStr(gpsMap.get("result"));
			if(!Check.isBlank(resultStr)){
				Map<String,Object> resultMap = JsonUtil.getMapFromJsonString(resultStr);
				String formatted_address = Convert.toStr(resultMap.get("formatted_address"));
				bdGpsMap = analysisLocation(formatted_address);
				bdGpsMap.put("formatted_address", formatted_address);
			}
		}
		log.info("queryBDGpsInfo result:"+bdGpsMap.toString());
		return bdGpsMap;
	}
	
	private String getCode(String code){
		if(code!=null&&!"".equals(code)){
			return Convert.toStr(Integer.parseInt(code));
		}
		return code;
	}
	
	public Map<String,Object> analysisLocation(String county){
		log.info("analysisLocation address:"+county);
		Map<String,Object> map = new HashMap<String, Object>();
		String[] provinceCity = {"北京","上海","天津","重庆","香港","澳门"};
		String[] municipalityProvince = {"广西","宁夏","内蒙古","西藏","新疆"};
		
		/** step1 匹配省  若匹配到截取省字之后的内容做后续处理 */
		List<CountryAddress> provinceAddress = countryAddressMapper.selectProvinceName();
		CountryAddress province = null;
		List<CountryAddress> citiesOfProvince = null;
		for(int i = 0 ; i < provinceAddress.size() ; i++){
			String provinceName = provinceAddress.get(i).getName();
			if(county.startsWith(provinceName)){
				province = provinceAddress.get(i);
				break;
			}
		}
		if(province != null){
			county = county.substring(province.getName().length());
			if(county.startsWith("省")){
				county = county.substring(1);
			}
			for(int i = 0 ; i <provinceCity.length ; i ++){
				if(province.getName().equals(provinceCity[i])){
					if(county.startsWith("省")||county.startsWith("市")){
						county = county.substring(1);
					}
				}
			}
			for(int i = 0 ; i <municipalityProvince.length ; i ++){
				if(province.getName().equals(municipalityProvince[i])){
					if(county.indexOf("自治区")>=0){
						county = county.substring(county.indexOf("自治区")+3);
					}
				}
			}
			log.info("analysisLocation 截取省之后："+county);
			citiesOfProvince = countryAddressMapper.selectCityByProvince(province.getCode());
		}else{
			log.info("analysisLocation 未匹配到省："+county);
			citiesOfProvince = countryAddressMapper.selectCityName();
		}
		Collections.sort(citiesOfProvince, new Comparator<CountryAddress>() {
			@Override
			public int compare(CountryAddress o1, CountryAddress o2) {
				return o2.getName().length() - o1.getName().length();
			}
		});
		
		/** step2 匹配市  若匹配到截取市字之后的内容做后续处理 */
		CountryAddress city = null;
		List<CountryAddress> areasOfCities = null;
		if(citiesOfProvince!=null&&citiesOfProvince.size()>0){
			for(int i = 0 ; i < citiesOfProvince.size() ; i++){
				String cityName = citiesOfProvince.get(i).getName().substring(0, citiesOfProvince.get(i).getName().length() - 1);
				if(!Check.isBlank(cityName)&&county.startsWith(cityName)){
					city = citiesOfProvince.get(i);
					break;
				}
			}
		}
		if(city != null){
			county = county.substring(city.getName().length());
			if(county.startsWith("市")){
				county = county.substring(1);
			}
			log.info("analysisLocation 截取完市之后："+county);
			areasOfCities = countryAddressMapper.selectCityByProvince(city.getCode());
		}else{
			log.info("analysisLocation 未查询到市："+county);
			if(province != null){
				areasOfCities = countryAddressMapper.selectCountyByProvince(province.getCode());
			}else{
				areasOfCities = countryAddressMapper.selectCountyName();
			}
		}
		Collections.sort(areasOfCities, new Comparator<CountryAddress>() {
			@Override
			public int compare(CountryAddress o1, CountryAddress o2) {
				return o2.getName().length() - o1.getName().length();
			}
		});
		
		
		/** step3 匹配县镇村  */
		List<CountryAddress> areas = new ArrayList<CountryAddress>();
		int flag = 0;
		int length = areasOfCities.get(0).getName().substring(0, areasOfCities.get(0).getName().length() - 1).length();
		for(int i = 0 ; i < areasOfCities.size() ; i++){
			String areaName = areasOfCities.get(i).getName().substring(0, areasOfCities.get(i).getName().length() - 1);
			if(!Check.isBlank(areaName)&&county.startsWith(areaName)){
				if(length!=areaName.length()){
					flag = 1;
				}
				areas.add(areasOfCities.get(i));
			}
		}
		
		CountryAddress area = null;
		if(areas.size() == 1){
			log.info("analysisLocation 查到一个县"+areas.get(0).getCode()+'-'+areas.get(0).getName());
			area = areas.get(0);
			CountryAddressExample example = new CountryAddressExample();
			example.createCriteria().andCodeEqualTo(area.getParentCode());
			city = countryAddressMapper.selectByExample(example).get(0);
			example = new CountryAddressExample();
			example.createCriteria().andCodeEqualTo(city.getParentCode());
			province = countryAddressMapper.selectByExample(example).get(0);
		}else{
			if(city != null){
				log.info("analysisLocation 县没查到或者查到多个  有市"+city.getName());
				if(areas.size() == 0){
					String cityName = city.getName().substring(0, city.getName().length() - 1);
					for(int i = 0 ; i < areasOfCities.size() ; i++){
						String areaName = areasOfCities.get(i).getName().substring(0, areasOfCities.get(i).getName().length() - 1);
						if(cityName.equals(areaName)){
							area = areasOfCities.get(i);
							break;
						}
					}
					if(area == null){
						area = areasOfCities.get(0);
					}
				}else{
					area = areas.get(0);
				}
				CountryAddressExample example = new CountryAddressExample();
				example.createCriteria().andCodeEqualTo(area.getParentCode());
				city = countryAddressMapper.selectByExample(example).get(0);
				example = new CountryAddressExample();
				example.createCriteria().andCodeEqualTo(city.getParentCode());
				province = countryAddressMapper.selectByExample(example).get(0);
			}else{
				if(areas.size() > 1 && flag == 1){
					log.info("县查到多个，且长度不一样，则取最长的");
					area = areas.get(0);
					CountryAddressExample example = new CountryAddressExample();
					example.createCriteria().andCodeEqualTo(area.getParentCode());
					city = countryAddressMapper.selectByExample(example).get(0);
					example = new CountryAddressExample();
					example.createCriteria().andCodeEqualTo(city.getParentCode());
					province = countryAddressMapper.selectByExample(example).get(0);
				}else{
					log.info("县没查到");
					// 报错
					return new HashMap<String,Object>();
				}
			}
		}
		
		map.put("province", getCode(province.getCode())+"-"+province.getName());
		map.put("city", getCode(city.getCode())+"-"+city.getName());
		map.put("area", getCode(area.getCode())+"-"+area.getName());
		log.info("analysisLocation 返回结果："+map.toString());
		return map;
	}


	@Override
	public void saveCustomerCertAddress(Map<String, Object> map)
			throws Exception {
		log.info("saveCustomerCertAddress map:"+map);
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String addressType=Convert.toStr(map.get("addressType"));
		String province=Convert.toStr(map.get("province"));
		String city=Convert.toStr(map.get("city"));
		String area=Convert.toStr(map.get("area"));
		String address=Convert.toStr(map.get("address"));
		String customerType=Convert.toStr(map.get("customerType"));
		String propertyType=Convert.toStr(map.get("propertyType"));
		CustomerAddressExample customerAddressExample =new CustomerAddressExample();
		customerAddressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAddressTypeEqualTo(addressType);
		List<CustomerAddress> customerAddressList=customerAddressMapper.selectByExample(customerAddressExample);
		if(customerAddressList==null || customerAddressList.size()<=0){
			CustomerAddress customerAddress =new CustomerAddress();
			customerAddress.setAddress(address);
			customerAddress.setAddressType(addressType);
			customerAddress.setCreateTime(DateUtil.currTimeStr());
			customerAddress.setCustomerType(customerType);
			customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
			customerAddress.setPropertyType(propertyType);
			customerAddress.setUserOnlyId(userOnlyId);
			customerAddress.setProvince(province);
			customerAddress.setCity(city);
			customerAddress.setArea(area);
			customerAddressMapper.insertSelective(customerAddress);
		}else{
			CustomerAddress customerAddress =customerAddressList.get(0);
			customerAddress.setAddress(address);
			customerAddress.setAddressType(addressType);
			customerAddress.setCustomerType(customerType);
			customerAddress.setPermanentFlag(UhjConstant.permanentFlag.yes);
			customerAddress.setPropertyType(propertyType);
			customerAddress.setUserOnlyId(userOnlyId);
			customerAddress.setProvince(province);
			customerAddress.setCity(city);
			customerAddress.setArea(area);
			customerAddress.setUpdateTime(DateUtil.currTimeStr());
			customerAddressMapper.updateByExampleSelective(customerAddress, customerAddressExample);
		}
	}
	
	
	
	@Override
	public CustomerAddress queryCustomerAddress(Map<String, Object> map)throws Exception {
		log.info("queryCustomerAddress map:"+map);
		CustomerAddress result=new CustomerAddress();
		String userOnlyId=Convert.toStr(map.get("userOnlyId"));
		String addressType=Convert.toStr(map.get("addressType"));
		CustomerAddressExample customerAddressExample =new CustomerAddressExample();
		customerAddressExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andAddressTypeEqualTo(addressType);
		List<CustomerAddress> customerAddressList=customerAddressMapper.selectByExample(customerAddressExample);
		if(customerAddressList!=null){
			result=customerAddressList.get(0);
		}
		return result;
	}
	
	
}
