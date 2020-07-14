package com.ule.uhj.init.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.app.zgd.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.init.dao.AnalysisMapper;
import com.ule.uhj.init.modle.CorpChangeInfo;
import com.ule.uhj.init.modle.CropInfo;
import com.ule.uhj.init.service.AnalysisService;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.Convert;




@Service("AnalysisService")
public class AnalysisServiceImpl implements AnalysisService {
	private static final Log log = LogFactory.getLog(AnalysisServiceImpl.class);

	@Autowired
	private InterfaceAccessInfoMapper interfaceAccessInfoMapper;
	
	@Autowired
	private  AnalysisMapper analysisMapper;
	
	private String QICHACHA="4103";
	private String QIANHAI="9102";
	
	
	@Override
	public void analysisInterface(Map<String, String> map) {
		
		log.info("------analysisInterface start -----------");
		
		//step1查询出所有的需要解析的报文
		log.info("------analysisInterface step1查询出所有的需要解析的报文-----------");
		List<String> interfaceIds = analysisMapper.queryInterfaceId(map);
		log.info("------analysisInterface step1 interfaceIds.size:"+interfaceIds.size());
		
		//step2解析的报文
		log.info("------analysisInterface step2解析的报文-----------");
		for(String interfaceId:interfaceIds){
			try {
				List<CropInfo> crops =new ArrayList<CropInfo>();
				InterfaceAccessInfoExample infoWithBlobExample = new InterfaceAccessInfoExample();
				infoWithBlobExample.createCriteria().andIdEqualTo(interfaceId);
				List<InterfaceAccessInfoWithBLOBs> interfaceAccessBLOBs =interfaceAccessInfoMapper.selectByExampleWithBLOBs(infoWithBlobExample);
				
				for(InterfaceAccessInfoWithBLOBs blob:interfaceAccessBLOBs){
					if(QICHACHA.equals(blob.getInterfaceType())){//企查查报文4103报文
						crops = analysisQiChaCha(blob);
					}else if(QIANHAI.equals(blob.getInterfaceType())){//前海征信9102报文
						crops = analysisQianHai(blob);
					}else{
						//以后拓展使用
					}
					
					//step3保存报文数据到表
					log.info("------analysisInterface step3保存数据-----------");
					if(crops==null || crops.size()==0){//报文返回非正常，或解析错误
						CropInfo moren=new CropInfo();
						moren.setInterfaceId(blob.getId());
						moren.setUserOnlyId(blob.getUserOnlyId());
						if(null==crops) {
							crops=new ArrayList<CropInfo>();
						}
						crops.add(moren);
					}
					for(CropInfo crop:crops){
						saveCrop(crop);					//保存报文企业信息
						
						List<CorpChangeInfo> changes =crop.getCorpChange();
						if(changes!=null && changes.size()>0){
							saveCropChange(changes);	//变更信息保存				
						}
						
						
					}				
				}
			} catch (Exception e) {
				log.error("------analysisInterface chucuo continue -----------",e);
			}
		}
		log.info("------analysisInterface end -----------");
	}
	
	
	
	
	private void saveCropChange(List<CorpChangeInfo> changes) {
		log.info("------analysisInterface changes -----------"+JSON.toJSONString(changes));
		analysisMapper.saveCropChange(changes);
		log.info("------analysisInterface changes -----------"+JSON.toJSONString(changes));
	}




	private void saveCrop(CropInfo crop) {
		analysisMapper.saveCrop(crop);
	}

	//解析企查查报文
	private ArrayList<CropInfo> analysisQiChaCha(InterfaceAccessInfoWithBLOBs blob) {
		ArrayList<CropInfo> rs= new ArrayList<CropInfo>();
		CropInfo crop=new CropInfo();		
		crop.setInterfaceId(blob.getId());
		crop.setUserOnlyId(blob.getUserOnlyId());
		
		JSONObject json=getJsonFromString(blob);
		log.info("qichacha result:"+json);
		if(null!=json){
			String qistatus=getStringValueFromJosn(json, "Status");//注册号
			JSONObject jsonResult=getJSONObjectFromJosn(json, "Result");
			if(StringUtils.isNotBlank(qistatus)&&qistatus.equals("200")&&null!=jsonResult){
												
				String no=getStringValueFromJosn(jsonResult, "No");//注册号
				String creditCode=getStringValueFromJosn(jsonResult, "CreditCode");
				if(null==no||StringUtils.isBlank(no)||"null".equals(no)){
					no=creditCode;
				}
				
				String name=getStringValueFromJosn(jsonResult, "Name");//名称
				String belongOrg=getStringValueFromJosn(jsonResult, "BelongOrg");//登记机关
				String endDate=getStringValueFromJosn(jsonResult, "EndDate");//注销/吊销日期
				String startDate=getStringValueFromJosn(jsonResult, "StartDate");//成立日期
				String scope=getStringValueFromJosn(jsonResult, "Scope");//经营范围
				String qcOperName=getStringValueFromJosn(jsonResult, "OperName");//法人代表
				String status=getStringValueFromJosn(jsonResult, "Status");//企业状态
				String address=getStringValueFromJosn(jsonResult, "Address");//经营地址
				String econKind=getStringValueFromJosn(jsonResult, "EconKind");//企业类型
				
				crop.setCorpName(name);
				crop.setLicenseNo(no);
				crop.setManager(qcOperName);
				crop.setStartTime(startDate.substring(0, 10));
				crop.setCorpAddress(address);
				crop.setCorpStatus(status);
				crop.setCorpType(econKind);
				crop.setDepertment(belongOrg);
				crop.setBusinessScope(scope);
				
				JSONArray ChangeRecords=getJsonArrayFromJosn(jsonResult, "ChangeRecords");
				if(null!=ChangeRecords&&ChangeRecords.size()>0){
					List<CorpChangeInfo> corpChange=new ArrayList<CorpChangeInfo>();
					for(Object cr:ChangeRecords){
						JSONObject records=JSONObject.parseObject(JSONObject.toJSONString(cr));//JSONObject.fromObject(cr);
						
						String projectName=getStringValueFromJosn(records, "ProjectName");
						String afterContent=getStringValueFromJosn(records, "AfterContent");
						String beforeContent=getStringValueFromJosn(records, "BeforeContent");		
						String changeDate=getStringValueFromJosn(records, "ChangeDate");		
						if(StringUtils.isNotBlank(changeDate)&&!"null".equals(changeDate)){
							changeDate=changeDate.substring(0, 10);
						}
						CorpChangeInfo cc =new CorpChangeInfo();
						cc.setChaneItem(projectName);
						cc.setContentAfter(afterContent);
						cc.setContentBefore(beforeContent);
						cc.setInterfaceId(blob.getId());
						try {
							cc.setChangeTime(DateUtil.parse(changeDate));							
						} catch (Exception e) {
							
						}
						
						corpChange.add(cc);
					}
					crop.setCorpChange(corpChange);
				}
			}								
		}				
		
		rs.add(crop);
		return rs;
	}
	
	
	//解析前海征信报文
	private ArrayList<CropInfo> analysisQianHai(InterfaceAccessInfoWithBLOBs blob) {
		ArrayList<CropInfo> rs = new ArrayList<CropInfo>();
		
		JSONObject qhJson=getJsonFromString(blob);
		log.info("解析前海征信userOnlyId报文 ："+qhJson);
		if(null!=qhJson){
			
			JSONObject jsonEntMgrInc=getJSONObjectFromJosn(qhJson, "entMgrInc");
			if(null!=jsonEntMgrInc){
				log.info("解析前海征信userOnlyId报文entMgrInc ："+jsonEntMgrInc);
				JSONArray jsonRecordsArr =jsonEntMgrInc.getJSONArray("records");
				log.info("解析前海征信userOnlyId报文 jsonRecordsArr："+jsonRecordsArr);
				for(int i=0;i<jsonRecordsArr.size();i++){  
                    JSONObject jsonRecord = jsonRecordsArr.getJSONObject(i);  
                    log.info("解析前海征信userOnlyId报文 jsonRecord："+jsonRecord);
                    if("E000000".equals(jsonRecord.get("erCode"))){
                    	JSONArray eAPArr =jsonRecord.getJSONArray("extInvstAndPosition");
                    	
                    	log.info("解析前海征信userOnlyId报文 eAPArr："+eAPArr);
                    	for(int j=0;j<eAPArr.size();j++){ 
                    		JSONObject eAP = eAPArr.getJSONObject(j); 
                    	  /*"content":"法人",
                    		"establishDate":"2016-11-14",
                    		"registerCap":"5.000000",
                    		"entType":"个体",
                    		"entName":"太湖县百里镇喜缘数码",
                    		"relation":"就任职务",
                    		"registerCapCur":"人民币元",
                    		"entStatus":"在营（开业）",
                    		"registerNo":"340825600283323"*/
                    		
                    		String content=Convert.toStr(eAP.get("content"));
                    		String establishDate=Convert.toStr(eAP.get("establishDate"));
                    		String registerCap=Convert.toStr(eAP.get("registerCap"));
                    		String entType=Convert.toStr(eAP.get("entType"));
                    		String entName=Convert.toStr(eAP.get("entName"));
                    		String relation=Convert.toStr(eAP.get("relation"));
                    		String registerCapCur=Convert.toStr(eAP.get("registerCapCur"));
                    		String entStatus=Convert.toStr(eAP.get("entStatus"));
                    		String registerNo=Convert.toStr(eAP.get("registerNo"));                    		
                    		
                    		CropInfo c = new CropInfo();
                    		c.setInterfaceId(blob.getId());
                    		c.setUserOnlyId(blob.getUserOnlyId());
                    		c.setCorpName(entName);
                    		c.setLicenseNo(registerNo);
                    		c.setStartTime(establishDate);
                    		c.setCorpStatus(entStatus);
                    		c.setCorpType(entType);
                    		
                    		rs.add(c);
                    	}
                    }
                }  			
			}
		}
		return rs;
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
	
	private JSONObject getJSONObjectFromJosn(JSONObject json,String key){
		JSONObject value=null;
		if(null!=json&&json.containsKey(key)){
			if(json.getJSONObject(key)!=null&&!json.getJSONObject(key).isEmpty()){
				
				value=json.getJSONObject(key);
			}
		}
		return value;
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
	
	private JSONArray getJsonArrayFromJosn(JSONObject json,String key){
		JSONArray value=null;
		if(null!=json&&json.containsKey(key)){
				 if(null!=json.getJSONArray(key)&&!json.getJSONArray(key).isEmpty()){
					 value=json.getJSONArray(key);
				 }
		}
		return value;
	}
}