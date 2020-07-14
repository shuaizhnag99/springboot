package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ule.uhj.app.zgd.dao.InterfaceAccessInfoMapper;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoExample;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;

@Service("interfaceAccessInfoService")
public class InterfaceAccessInfoServiceImpl implements InterfaceAccessInfoService{
	private static Logger log = LoggerFactory.getLogger(InterfaceAccessInfoServiceImpl.class);
	@Autowired
	private InterfaceAccessInfoMapper interfaceAccessInfoMapper;

	@Override
	public List<InterfaceAccessInfoWithBLOBs> queryInterfaceAccessInfoWithBLOBs(Map<String,Object> param)
			throws Exception {
		InterfaceAccessInfoExample interfaceInfoExample = new InterfaceAccessInfoExample();
		interfaceInfoExample.createCriteria().andUserOnlyIdEqualTo(Convert.toStr(param.get("userOnlyId")))
		.andInterfaceTypeEqualTo(Convert.toStr(param.get("tranzCode")));
		return interfaceAccessInfoMapper.selectByExampleWithBLOBs(interfaceInfoExample);
	}
	
	/**
	 * 保存接口返回数据
	 */
	@Override
	public void saveInterfaceData(InterfaceAccessInfoWithBLOBs interfaceInfo)
			throws Exception {
		log.info("saveInterfaceData begin..."+interfaceInfo.getUserOnlyId()+" transCode:"+interfaceInfo.getInterfaceType());
		if(!UhjConstant.transCode.QUERY_REPORT.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.REPORT_TOKEN.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.REPORT_DATA.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.RAW_DATA.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.BUSSINESS_RAW_DATA.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.ACCESS_JOB_STATUS.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.QIAN_HAI_VERIFYPHONE.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.TONGDUN_QUERY.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.REPORT_BY_STOKEN.equals(interfaceInfo.getInterfaceType())&&
				!UhjConstant.transCode.PENGYUAN_VERIFYPHONE.equals(interfaceInfo.getInterfaceType())){
			interfaceAccessInfoMapper.insertSelective(interfaceInfo);
		}else{
			InterfaceAccessInfoExample interfaceExample = new InterfaceAccessInfoExample();
			interfaceExample.createCriteria().andUserOnlyIdEqualTo(interfaceInfo.getUserOnlyId()).andInterfaceTypeEqualTo(interfaceInfo.getInterfaceType());
			List<InterfaceAccessInfoWithBLOBs> infoWithBlobs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(interfaceExample);
			if(infoWithBlobs!=null&&infoWithBlobs.size()>0){
				log.info("updateInterfaceData begin...");
				InterfaceAccessInfoWithBLOBs interfaceAccessInfoWithBLOBs =infoWithBlobs.get(0);
				interfaceAccessInfoWithBLOBs.setAppId(interfaceInfo.getAppId());
				interfaceAccessInfoWithBLOBs.setUserOnlyId(interfaceInfo.getUserOnlyId());
				interfaceAccessInfoWithBLOBs.setInterfaceType(interfaceInfo.getInterfaceType());			
				if(interfaceInfo.getRequestInfo()!=null){
					interfaceAccessInfoWithBLOBs.setRequestInfo(interfaceInfo.getRequestInfo());
				}
				interfaceAccessInfoWithBLOBs.setResponseInfo(interfaceInfo.getResponseInfo());
				interfaceAccessInfoWithBLOBs.setStatus(interfaceInfo.getStatus());
//				interfaceAccessInfoWithBLOBs.setRestAccesssCount(interfaceInfo.getRestAccesssCount());
				interfaceAccessInfoWithBLOBs.setUpdateTime(DateUtil.currTimeStr());
				interfaceAccessInfoMapper.updateByExampleWithBLOBs(interfaceAccessInfoWithBLOBs, interfaceExample);
			}else{
				interfaceAccessInfoMapper.insertSelective(interfaceInfo);
			}
		}
	}

	@Override
	public boolean queryInterfaceStatus(String userOnlyId) throws Exception {
		log.info("queryInterfaceStatus userOnlyId:"+userOnlyId);
		List<String> interfaceTypes = new ArrayList<String>();
		interfaceTypes.add(UhjConstant.transCode.REPORT_DATA);
		interfaceTypes.add(UhjConstant.transCode.RAW_DATA);
		InterfaceAccessInfoExample interfaceExample = new InterfaceAccessInfoExample();
		interfaceExample.createCriteria().andUserOnlyIdEqualTo(userOnlyId).andInterfaceTypeIn(interfaceTypes).andStatusEqualTo(UhjConstant.interfaceStutas.success);
		List<InterfaceAccessInfoWithBLOBs> infoWithBlobs = interfaceAccessInfoMapper.selectByExampleWithBLOBs(interfaceExample);
		if(infoWithBlobs!=null&&infoWithBlobs.size()==2){
			return true;
		}
		return false;
	}
}
