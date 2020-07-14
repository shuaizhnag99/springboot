package com.ule.uhj.provider.yitu.service;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ule.tools.creditService.bean.BDReverseGeocoding;
import com.ule.tools.creditService.client.CreditServiceTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ule.uhj.sld.util.Convert;

import com.ule.uhj.sldProxy.util.PropertiesHelper;

public class BaiDuMapService {
	 protected static Log log = LogFactory.getLog(BaiDuMapService.class);
	 
	 private static String url=PropertiesHelper.getDfs("BD_GPS_QUERY_URL");
	 
	 private static String ak = PropertiesHelper.getDfs("ak");

	 public static String queryBDGpsInfo(Map<String,Object> param)throws Exception {
	 	log.info("queryBDGpsInfo location:"+Convert.toStr(param.get("location")));
	    BDReverseGeocoding bdReverseGeocoding = new BDReverseGeocoding();
	    bdReverseGeocoding.setLocation(Convert.toStr(param.get("location")));
	    bdReverseGeocoding.setCoordtype("gcj02ll");
	    String responseStr=CreditServiceTools.bdReverseSeocoding(bdReverseGeocoding);
	    log.info("queryBDGpsInfo responseStr="+responseStr);
	    Map<String, Object> object = (Map<String, Object>) JSONObject.parse(responseStr);
	    log.info("queryBDGpsInfo object="+object);
	    if("0000".equals(object.get("code")) && !"".equals(object.get("data"))){
	    	log.info("queryBDGpsInfo result="+object.get("data"));
	    	return object.get("data").toString();
	    }else{
	    	throw new Exception("调用基础服务部， 百度逆地理编码（根据经纬度坐标获取地址）接口失败！");
	    }
	 }
	 
//	 public static void main(String[] args) throws Exception {
//		 Map<String,Object> param =new HashMap<String, Object>();
//		 param.put("location", "31.2529,121.5710");
//		 queryBDGpsInfo(param);
//	}
}
