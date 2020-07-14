package com.ule.uhj.sldProxy.sldBiz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ule.oracle.dao.ConstantMapper;
import com.ule.oracle.dao.InterfaceBlockMapper;
import com.ule.oracle.dao.InterfaceRecordMapper;
import com.ule.oracle.model.Constant;
import com.ule.oracle.model.ConstantExample;
import com.ule.oracle.model.InterfaceBlock;
import com.ule.oracle.model.InterfaceBlockExample;
import com.ule.oracle.model.InterfaceBlockExample.Criteria;
import com.ule.oracle.model.InterfaceRecordExample;
import com.ule.oracle.model.InterfaceRecordWithBLOBs;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

@Component("BetaInterHelperService")
public class BetaInterHelperService {
	private static Log log = LogFactory.getLog(BetaInterHelperService.class);
	
	@Autowired
    ConstantMapper constantMapper;
	@Autowired
	InterfaceRecordMapper interfaceRecordMapper;
	@Autowired
	InterfaceBlockMapper interfaceBlockMapper;
	
	public String getBetaInterfaceResponse(String transCode){
		log.info("interface block check...");
		if("beta".equals(PropertiesHelper.getDfs("env"))|| "testing".equals(PropertiesHelper.getDfs("env"))){
			log.info("interface block env is beta...");
			ConstantExample constantExample = new ConstantExample();
			constantExample.createCriteria().andElementKeyEqualTo("block_"+transCode);
			List<Constant> list = constantMapper.selectByExample(constantExample);
			if(list!=null&&list.size()>0){
				return list.get(0).getElementValue();
			}
		}
		return null;
	}

	public String getBetaInterfaceResponseByInterfaceInfo(String transCode){
		log.info("interface block check...");
		if("beta".equals(PropertiesHelper.getDfs("env"))|| "testing".equals(PropertiesHelper.getDfs("env"))){
			log.info("interface block env is beta...");
			InterfaceRecordExample constantExample = new InterfaceRecordExample();
			String trans_code = "block_"+transCode;
			constantExample.createCriteria().andInterfaceTypeEqualTo(trans_code);
			List<InterfaceRecordWithBLOBs> list = interfaceRecordMapper.selectByExampleWithBLOBs(constantExample);
			if(list!=null&&list.size()>0){
				String response_str = new String(list.get(0).getResponseInfo());
				log.info("response_str = "+response_str);
				return response_str;
			}
		}
		return null;
	}
	
	public String getBlockTextByCondition(String userOnlyId,String transCode,String type,String remark){
		log.info("getBlockTextByCondition block check...");
		try {
			if("beta".equals(PropertiesHelper.getDfs("env"))|| "testing".equals(PropertiesHelper.getDfs("env"))){
				log.info("interface block env is beta...");
				InterfaceBlockExample example = new InterfaceBlockExample();
				Criteria cr = example.createCriteria();
				if(Check.isBlank(transCode)||Check.isBlank(userOnlyId)) {
					return null;
				}else {
					cr.andUserOnlyIdEqualTo(userOnlyId).andInterfaceCodeEqualTo(transCode).andStatusEqualTo("1");
				}
				if(!Check.isBlank(type)) {
					cr.andInterfaceTypeEqualTo(type);
				}
				if(!Check.isBlank(remark)) {
					cr.andRemarkEqualTo(remark);
				}
				
				List<InterfaceBlock> list = interfaceBlockMapper.selectByExampleWithBLOBs(example);
				if(list!=null&&list.size()>0){
					String response_str = new String(list.get(0).getBlockText(),"UTF-8");
					log.info("response_str = "+response_str);
					return response_str;
				}
			}
		}catch (Exception e) {
			log.info("getBlockTextByCondition error...",e);
		}
		return null;
	}
	
}
