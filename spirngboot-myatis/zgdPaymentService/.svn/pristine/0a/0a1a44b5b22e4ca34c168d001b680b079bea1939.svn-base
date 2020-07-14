package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.app.zgd.bo.ForecastResultSaveBO;
import com.ule.uhj.app.zgd.dao.CreditApplyMapper;
import com.ule.uhj.app.zgd.dao.ForecastScopeMapper;
import com.ule.uhj.app.zgd.dao.ForecastScopeVpsWordMapper;
import com.ule.uhj.app.zgd.dao.ForecastVpsMapper;
import com.ule.uhj.app.zgd.dao.ForecastVpsWordMapper;
import com.ule.uhj.app.zgd.dao.ForecastWordMapper;
import com.ule.uhj.app.zgd.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.app.zgd.model.CreditApply;
import com.ule.uhj.app.zgd.model.CreditApplyExample;
import com.ule.uhj.app.zgd.model.ForecastScopeVpsWord;
import com.ule.uhj.app.zgd.model.ForecastScopeVpsWordExample;
import com.ule.uhj.app.zgd.model.ForecastVpsWord;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoExample.Criteria;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.ForecastResultService;
import com.ule.uhj.app.zgd.service.ForecastService;
@Service
public class ForecastServiceImpl implements ForecastService {
	private static Logger log = LoggerFactory.getLogger(ForecastService.class);
	  //企查查企业关键字精确获取企业信息code
	  private final String qchacha_info="4103";
	  
	  @Autowired
	  private InterfaceAccessInfoMapper interMapper;
	  @Autowired
	  private ForecastScopeMapper  fsMapper;
	  @Autowired
	  private ForecastScopeVpsWordMapper fsvwMapper;
	  @Autowired
	  private ForecastVpsWordMapper forecastVpsWordMapper;
	  @Autowired
	  private ForecastVpsMapper forecastVpsMapper;
	  @Autowired
	  private ForecastWordMapper forecastWordMapper;
	  @Autowired
	  private ForecastResultService forecastResultService;
	  @Autowired
	  private CreditApplyMapper caMapper;
	@Override
	public String getBusinessScope(String userOnlyId) {
		String no="";//注册号
	    String scope="";
		List<InterfaceAccessInfoWithBLOBs> list= getInterfaceList(userOnlyId, null, qchacha_info);
		if(null!=list&&list.size()>0){
			String  creditCode="";
			String  qistatus="";
			for(InterfaceAccessInfoWithBLOBs in :list){
				JSONObject json=getJsonFromString(in);
				String reposneStr= null;
				if(json != null){
					reposneStr = json.toJSONString();
				}
				log.info("getBusinessScope reposneStr result:"+reposneStr);
				if(reposneStr!= null && ((reposneStr.contains("Status")&&reposneStr.contains("200"))||reposneStr.contains("查询成功"))){
					log.info("getBusinessScope qichacha result:"+json.toString());
					if(null!=json){
						qistatus=getStringValueFromJosn(json, "Status");//注册号
						JSONObject jsonResult=getJSONObjectFromJosn(json, "Result");//json.getJSONObject("Result");
						if(StringUtils.isNotBlank(qistatus)&&qistatus.equals("200")&&null!=jsonResult){
						 						
							no=getStringValueFromJosn(jsonResult, "No");//注册号
							log.info("getBusinessScope....no"+no);
							creditCode=getStringValueFromJosn(jsonResult, "CreditCode");
							log.info("getBusinessScope....creditCode"+creditCode);
						 
							scope=getStringValueFromJosn(jsonResult, "Scope");//经营范围
							
                             break;
							}								
						}				
					}
					
				}
				
			} 
	 
	
		
		
		return scope;
	}
	private List<InterfaceAccessInfoWithBLOBs> getInterfaceList(String userOnlyId,String ApplyId,String interfaceType){
		log.info(String.format("查询接口信息参数 userOnlyid:%s,applyid:%s,interfaceType:%s begin", userOnlyId,ApplyId,interfaceType));
		List<InterfaceAccessInfoWithBLOBs> list=new ArrayList<InterfaceAccessInfoWithBLOBs>();
		try {
			InterfaceAccessInfoExample example = new InterfaceAccessInfoExample();
			Criteria criteria = example.createCriteria();
			
			if (StringUtils.isNotBlank(userOnlyId)){
				criteria.andUserOnlyIdEqualTo(userOnlyId);
			}
			if( StringUtils.isNotBlank(ApplyId)){
				criteria.andAppIdEqualTo(ApplyId);
			}
			if( StringUtils.isNotBlank(interfaceType)){
				criteria.andInterfaceTypeEqualTo(interfaceType);
			}
			example.setOrderByClause(" create_time desc ");
		
			list = interMapper.selectByExampleWithBLOBs(example);
		} catch (Exception e) {
			log.error("查询接口信息 error", e);
			list=null;
		}
		log.info(String.format("查询接口信息参数 userOnlyid:%s,applyid:%s,interfaceType:%s end", userOnlyId,ApplyId,interfaceType));
		return list;
	}
	private JSONObject getJsonFromString (InterfaceAccessInfoWithBLOBs in){
		JSONObject json=null;

		if(null==in||null==in.getResponseInfo()){
			return null;
		}
		String []charset={"UTF-8","GBK","ISO-8859-1", "GB2312"};
		
		for(String ch:charset){
			try {
				 log.info("转换json编码:"+ch);
			json = JSONObject.parseObject(new String(in.getResponseInfo(), ch));
			} catch (Exception e1) {
				log.error("转换json编码格式"+ch+" error");
				json=null;
				continue;
			}
			break;
			
		}

		return json;
	}
	private String getStringValueFromJosn(JSONObject json,String key){
		String value="";
		if(null!=json&&json.containsKey(key)){
		if(json.getString(key)!=null&&!json.getString(key).isEmpty()){
				
				value=json.getString(key);
			}
		}
		return value;
	}

	private JSONObject getJSONObjectFromJosn(JSONObject json,String key){
		JSONObject value=null;
		if(null!=json&&json.containsKey(key)){
			if(json.getJSONObject(key)!=null&&!json.getJSONObject(key).isEmpty()){
				
				value=json.getJSONObject(key);
			}
		}
		return value;
	}
	/**
	 * 1、通过useronlyid查询出经营范围记录，通过经营范围记录ID 查询经营范围编码与行业编码货预测码关联
	 * 查询出预测码或者vpsID（如果是预测码要再次查询Vps行业与预测码关联表查询出对应的vpsid） 通过vpsid 查询出对应的行业id，名称；
	 * 2、通过useronlyid没有查询出经营范围记录或者通过经营范围记录ID 没有查询经营范围编码与行业编码货预测码关联记录
	 * 通过useronlyid查询预测码表，然后查询Vps行业与预测码关联表查询出对应的vpsid 通过vpsid 查询出对应的行业id，名称
	 */
	@Override
	public List<Map<String, String>> getForecastKyeWord(String userOnlyId) {
		log.info("getForecastKyeWord...begin...useronlyid"+userOnlyId);
		List<Map<String, String>> reList=new ArrayList<Map<String,String>>();
		List<Map<String, String>> reList2=new ArrayList<Map<String,String>>();
		boolean flag=false;
		String scope= this.getBusinessScope(userOnlyId);
		log.info(String.format("useronlyid %s,scope %s", userOnlyId,scope));
		Map map = new LinkedHashMap();
		if(StringUtils.isNotBlank(scope)){
			
			log.info(String.format("useronlyid %s 查询经营范围编码表 t_j_forecastScope begin...", userOnlyId));
			List<String> list=fsMapper.selectByBusinessScope(scope);
			if(null!=list&&list.size()>0){
				for(int i = 0; i < list.size(); i++){
					map.put(list.get(i), "");
				}
				
				log.info(String.format("useronlyid %s 查询经营范围编码表 t_j_forecastScope end result size %s", userOnlyId,list.size()));
				log.info(String.format("useronlyid %s 查询经营范围编码与行业编码货预测码关联表 t_j_forecastScopeVpsWord begin...", userOnlyId));
				ForecastScopeVpsWordExample  svwExample=new ForecastScopeVpsWordExample();
				svwExample.createCriteria().andScopeCodeIn(list);
				List<ForecastScopeVpsWord> svwList=fsvwMapper.selectByExample(svwExample);
				List<String> vpsCode =new ArrayList<String>();
				List<String> wordCode =new ArrayList<String>();
				if(null!=svwList&&svwList.size()>0){
					log.info(String.format("useronlyid %s 查询经营范围编码与行业编码货预测码关联表 t_j_forecastScopeVpsWord begin...result size %s", userOnlyId,svwList.size()));
					for(ForecastScopeVpsWord fsv:svwList){
						if("1".equals(fsv.getCodeType())){
							wordCode.add(fsv.getForecastVpsCode());
						}else{
							vpsCode.add(fsv.getForecastVpsCode());
						}
						try{
							String value = (String)map.get(fsv.getScopeCode());
							if(value.equals("")){
								value += fsv.getForecastVpsCode();
							}else{
								value += "|" + fsv.getForecastVpsCode();
							}
							map.put(fsv.getScopeCode(), value);
						}catch(Exception e){
							log.error(e.getMessage());
						}
						
					}
					
					if(wordCode.size()>0){
						try{
							List mylist = forecastVpsWordMapper.queryObjsByWord(wordCode);
							for(int j = 0; j < mylist.size(); j++){
								ForecastVpsWord forecastVpsWord = (ForecastVpsWord)mylist.get(j);
								for(Iterator it1 = map.keySet().iterator(); it1.hasNext();){
									String scopeWord = (String)it1.next();
									String valuesStr = (String)map.get(scopeWord);
									String[] values = valuesStr.split("\\|");
									
									for(int k = 0; k < values.length; k++){
										if(values[k].equals(forecastVpsWord.getForecastCode())){
											if(valuesStr.equals("")){
												valuesStr += forecastVpsWord.getVpsCode();
											}else{
												valuesStr += "|" + forecastVpsWord.getVpsCode();
											}
										}
									}
									map.put(scopeWord, valuesStr);
								}
							}
						}catch(Exception e){
							log.error(e.getMessage());
						}
						
						log.info(String.format("useronlyid %s Vps行业与预测码关联表 t_j_forecastvpsword begin...", userOnlyId));
						List<String> vpsList=forecastVpsWordMapper.queryListByWord(wordCode);
						if(null!=vpsList&&vpsList.size()>0){
							log.info(String.format("useronlyid %s Vps行业与预测码关联表 t_j_forecastvpsword end result size%s...", userOnlyId,vpsList.size()));
							vpsCode.addAll(vpsList);
							
						}
						
						
					}
					
					if(null!=vpsCode&&vpsCode.size()>0){
						log.info(String.format("useronlyid %s Vps行业编码表 t_j_forecastvps begin...", userOnlyId));
						reList=forecastVpsMapper.queryListBycode(vpsCode);
						
					}
				}else{
					flag=true;
				}
				
				
				
			}else{
				flag=true;
			}
			if(flag){
				log.info(String.format("useronlyid %s 关键字预测码表 t_j_forecastword begin...", userOnlyId));
				
				List<String> forecastCodeList=forecastWordMapper.selectCodeByScope(scope);
				if(null!=forecastCodeList&&forecastCodeList.size()>0){
					try{
						for(int i = 0; i < forecastCodeList.size(); i++){
							map.put(forecastCodeList.get(i), "");
						}
						
						List mylist = forecastVpsWordMapper.queryObjsByWord(forecastCodeList);
						for(int j = 0; j < mylist.size(); j++){
							ForecastVpsWord forecastVpsWord = (ForecastVpsWord)mylist.get(j);
							for(Iterator it1 = map.keySet().iterator(); it1.hasNext();){
								String forecost = (String)it1.next();
								String valuesStr = (String)map.get(forecost);
								
								if(forecost.equals(forecastVpsWord.getForecastCode())){
									if(valuesStr.equals("")){
										valuesStr += forecastVpsWord.getVpsCode();
									}else{
										valuesStr += "|" + forecastVpsWord.getVpsCode();
									}
								}
								map.put(forecost, valuesStr);
							}
						}
					}catch(Exception e){
						log.error(e.getMessage());
					}
					
					log.info(String.format("useronlyid %s 关键字预测码表 t_j_forecastword ...result %s..", userOnlyId,forecastCodeList.size()));
					List<String> vpsList=forecastVpsWordMapper.queryListByWord(forecastCodeList);
					
					if(null!=vpsList&&vpsList.size()>0){
						log.info(String.format("useronlyid %s Vps行业编码表 t_j_forecastvps begin...", userOnlyId));
						reList=forecastVpsMapper.queryListBycode(vpsList);
					}
				}
				
				
			}
			
		}	
		Set showResult = new LinkedHashSet();
		try{
			for(Iterator it = map.values().iterator();it.hasNext();){
				String values = (String)it.next();
				if(values!=null && !values.equals("")){
					if(showResult.size() < 4){
						String[] valuesStr = values.split("\\|");
						for(int i = 0; i < valuesStr.length; i++){
							if(valuesStr[i].startsWith("0")){
								continue;
							}
							showResult.add(valuesStr[i]);
							if(showResult.size() == 4){
								break;
							}
						}
					}else{
						break;
					}
				}
				
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		String code="";
		String show="";
		int i=0;
		if(null!=reList&&reList.size()>0){
			for(Map<String, String> m:reList){
				String value=m.get("CODE");
				String mainBusiness=m.get("MAIN_BUSINESS");
				if(check(code, value)){
					++i;
//					if(i<5){
//						Map<String, String> rem=new HashMap<String, String>();
//						rem.put("value", value);				
//						rem.put("mainBusiness", mainBusiness);
//						reList2.add(rem);
//						show=show+value+",";
//					}
					
					code=code+value+",";
				}
				
			}
		}
		i = 0;
		if(null!=reList&&reList.size()>0){
		if(showResult != null && showResult.size() >0){
			for(Iterator it = showResult.iterator(); it.hasNext();){
				String resultShowStr = it.next().toString();
				for(Map<String, String> m:reList){
					String value=m.get("CODE");
					String mainBusiness=m.get("MAIN_BUSINESS");
					
						if(value.equals(resultShowStr)){
							Map<String, String> rem=new HashMap<String, String>();
							rem.put("value", value);				
							rem.put("mainBusiness", mainBusiness);
							reList2.add(rem);
							show=show+value+",";
						}
				}
			}
		}else{
			for(Map<String, String> m:reList){
				String value=m.get("CODE");
				String mainBusiness=m.get("MAIN_BUSINESS");
				if(i<5){
					Map<String, String> rem=new HashMap<String, String>();
					rem.put("value", value);				
					rem.put("mainBusiness", mainBusiness);
					reList2.add(rem);
					show=show+value+",";
				}
			}
		}

		}
		
		
		saveForecastResult(userOnlyId, scope, code,show);
		log.info("getForecastKyeWord...end...useronlyid"+userOnlyId+"result "+JSONObject.toJSONString(reList));
		log.info("getForecastKyeWord...end...useronlyid"+userOnlyId+"result "+JSONObject.toJSONString(reList2));
		
		return reList2;
	}
	
	private void saveForecastResult(String userOnlyId,String scope,String code,String show){
		try {
			CreditApplyExample caExample = new CreditApplyExample();
			caExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId);
			caExample.setOrderByClause(" CREATE_TIME desc");
			List<CreditApply> caList = caMapper.selectByExample(caExample);
			String applyId = "";
			if (null != caList && caList.size() > 0) {
				applyId = caList.get(0).getId();
			}
			ForecastResultSaveBO forecastResultSaveBO = new ForecastResultSaveBO();
			forecastResultSaveBO.setUserOnlyId(userOnlyId);
			forecastResultSaveBO.setBusinessScope(scope);
			forecastResultSaveBO.setForecastResult(code);
			forecastResultSaveBO.setApplyId(applyId);
			forecastResultSaveBO.setForecastResultShow(show);
			forecastResultService.saveScreeningScope(forecastResultSaveBO);
		} catch (Exception e) {
			log.error("saveForecastResult error", e);
		}
	}

	
	private boolean check(String code,String value){
		boolean flag=true;
		if(StringUtils.isBlank(code)){
			return true;
		}else{
			String cs[]=code.split(",");
			for(String st:cs){
				if(st.equals(value)){
					flag=false;
					break;
				}
			}
		}
		
		return flag;
	}
}
