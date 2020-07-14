package com.ule.uhj.provider.pengyuan.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.ule.tools.creditService.bean.PYConstants;
import com.ule.tools.creditService.bean.PYParamsBean;
import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.provider.common.CommonTranzCode;
import com.ule.uhj.provider.pengyuan.bean.QueryCondition;
import com.ule.uhj.provider.pengyuan.bean.QueryConditions;
import com.ule.uhj.provider.pengyuan.util.HttpUtils;
import com.ule.uhj.provider.pengyuan.util.PyUtils;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;



public class PengyuanQueryService {

	protected static Log log = LogFactory.getLog(PengyuanQueryService.class);
	
	private static final String pengyuanHost = PropertiesHelper.getDfs("pengyuan_host");
	private static final String pengyuanPathUnzip = PropertiesHelper.getDfs("pengyuan_path_unzip");
	private static final String pengyuanUser = PropertiesHelper.getDfs("pengyuan_user");
	private static final String pengyuanPassword = PropertiesHelper.getDfs("pengyuan_password");
	private static final String pengyuanKeystorePassword = PropertiesHelper.getDfs("pengyuan_keystore_password");
//	private static final String pengyuanTruststorePassword = PropertiesHelper.getDfs("pengyuan_truststore_password");
	private static final String isTest = PropertiesHelper.getDfs("pengyuan_is_test");
	private static final String queryFile = PropertiesHelper.getDfs("pengyuan_query_file");
	
	/**
	 * 个人基本信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String personalBaseInfo(Map<String,Object> param) throws Exception {
		log.info("personalBaseInfo param="+param);
		param.put("queryType", "25160");
		param.put("subreportIDs", "10001");
		param.put("queryReasonID", "101");
		return requestUnzipApi(param);
	}
	
	/**
	 * 身份认证
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String identityAuth(Map<String,Object> param) throws Exception {
		log.info("identityAuth param="+param);
		param.put("queryType", "25160");
		param.put("subreportIDs", "10602");
		param.put("queryReasonID", "101");
		return requestUnzipApi(param);
	}
	
	/**
	 * 企业信用报告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String enterpriseCreditReport(Map<String,Object> param) throws Exception {
		log.info("enterpriseCreditReport param="+param);
		param.put("queryType", "25123");
		param.put("subreportIDs", "95001");
		param.put("queryReasonID", "101");
		return requestUnzipApi(param);
	}
	
	/**
	 * 个人信用报告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String personalInfoReport(Map<String,Object> param) throws Exception {
		log.info("personalInfoReport param="+param);
		param.put("queryType", "25136");
		param.put("subreportIDs", "96002");
		param.put("queryReasonID", "101");
		return requestUnzipApi(param);
	}
	
	
	/**
	 * 个人银行卡四要素核查
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String personalBankFour(Map<String,Object> param) throws Exception {
		log.info("personalBankFour param="+param);
		param.put("queryType", "25173");
		param.put("subreportIDs", "14506");
		param.put("queryReasonID", "101");
		return requestUnzipApi(param);
	}
	
	/**
	 * 手机号码核查及状态时长
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String mobileVerifyAndStateLength(Map<String,Object> param) throws Exception {
		log.info("mobileVerifyAndStateLength param="+param);

//		param.put("queryType", "25196");
//		param.put("subreportIDs", "96027");
//		param.put("queryReasonID", "101");
//		return requestUnzipApi(param);
		//接入基础服务组
		PYParamsBean pyParamsBean = new PYParamsBean();
		pyParamsBean.setQueryType(PYConstants.QUERYTYPE_PHONE_VER);
		pyParamsBean.setName(Convert.toStr(param.get("name")));
		pyParamsBean.setDocumentNo(Convert.toStr(param.get("documentNo")));
		pyParamsBean.setPhone(Convert.toStr(param.get("phone")));
		pyParamsBean.setSubreportIDs(PYConstants.SUBREPORTIDS_PHONE_VER);
		pyParamsBean.setQueryReasonID(PYConstants.SUBREPORT_QS_101);
		//pyParamsBean.setRefID("0001");
		String result = CreditServiceTools.phoneVerification(pyParamsBean);
		JSONObject obj = JSONObject.fromObject(result);
		if(!Check.isBlank(obj.get("data"))) {
			return JSONUtils.valueToString(obj.get("data"));
		}
		return null;
	}
	
//	public static void main(String[] args) throws Exception {
//		Map<String,Object> param = new HashMap<String, Object>();
//		param.put("name", "测试一");
//		param.put("documentNo", "110000199001011112");
//		param.put("phone", "13711111101");
//		PengyuanQueryService aa = new PengyuanQueryService();
//		aa.mobileVerifyAndStateLength(param);
//	}
	
	/**
	 * 个人风险信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String personalRiskInfo(Map<String,Object> param) throws Exception {
		log.info("personalRiskInfo param="+param);
		param.put("queryType", "25136");
		param.put("subreportIDs", "14217");
		param.put("queryReasonID", "101");
		return requestUnzipApi(param);
	}
	
	
	/**
	 * 个人反欺诈分析报告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String personalAntiFraudAnalysisReport(Map<String,Object> param) throws Exception {
		log.info("personalAntiFraudAnalysisReport param="+param);
		param.put("queryType", "25136");
		param.put("subreportIDs", "96043");
		param.put("queryReasonID", "101");
		return requestUnzipApi(param);
	}
	
	public String requestUnzipApi(Map<String,Object> map) throws Exception {
        String result = requestApi(pengyuanHost, pengyuanPathUnzip,map);
        return result;
    }

	public String requestZipApi(Map<String,Object> map) throws Exception {
        String result = requestApi(pengyuanHost, pengyuanPathUnzip,map);
        System.out.println(result);

        // 对压缩文本做进一步处理
        Map<String, Object> resultMap = JSON.parseObject(result);
        String status = (String) resultMap.get("status");
        if ("1".equals(status)) {
            String returnValue = (String) resultMap.get("returnValue");
            returnValue = PyUtils.decodeAndDecompress(returnValue, "UTF-8");
        }
        return result;
    }

    public static String requestApi(String host, String path, Map<String,Object> params) throws Exception {

        // https双向认证使用,配合PySSLContextUtil中的DefaultSSLContext
    	if(host.startsWith("https")){
    		String keyStoreFile =  Thread.currentThread().getContextClassLoader().getResource("/").getPath()+PropertiesHelper.getDfs("pengyuan_keystore_file");
    		log.info("requestApi pengyuan_keystore_file path="+keyStoreFile);
    		//    		String TrustStoreFile = Thread.currentThread().getContextClassLoader().getResource("/").getPath()+PropertiesHelper.getDfs("pengyuan_truststore_file");
    		System.setProperty("javax.net.debug", "all");
    		System.setProperty("javax.net.ssl.keyStore", keyStoreFile.substring(1));
    		System.setProperty("javax.net.ssl.keyStorePassword", pengyuanKeystorePassword);
//    		System.setProperty("javax.net.ssl.trustStore", "D:/file/ylwl.jks");
//    		System.setProperty("javax.net.ssl.trustStorePassword", pengyuanTruststorePassword);
    	}
    	
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = null;
        Map<String, String> bodys = new HashMap<String, String>();
//		bodys.put("format","xml");
        bodys.put("userID", pengyuanUser);
        bodys.put("password", pengyuanPassword);
        bodys.put("queryCondition", getQueryCondition(params));
        HttpResponse response = null;
        try{
        	 response = HttpUtils.doPost(host, path, "POST", headers, querys, bodys);
        }catch(Exception ex){
        	log.error("requestApi error=",ex);
        	return "";
        }
        String result = EntityUtils.toString(response.getEntity());
        log.info("pengyuan requestApi result ="+result);
        return result;
    }

    @SuppressWarnings("rawtypes")
	private static String getQueryCondition(Map<String,Object> params) throws Exception {

        if (Boolean.valueOf(isTest)) {
            // 读取文件方式（测试使用,仅供参考）
            return PyUtils.toString(new FileInputStream(queryFile), "UTF-8");
        } else {
            // 使用JavaBean/Map方式（正式使用,仅供参考）
            QueryConditions queryConditions = new QueryConditions();
            List<QueryCondition> conditions = new ArrayList<QueryCondition>();
            QueryCondition queryCondition = new QueryCondition();
            // 查询类型
            queryCondition.setQueryType(Convert.toStr(params.get("queryType")));
            List<QueryCondition.Item> items = new ArrayList<QueryCondition.Item>();
            
            CommonTranzCode ctc = CommonTranzCode.getTranzCode(Integer.parseInt(String.valueOf(params.get("tranzCode"))));
            List<String> needParamsList = new ArrayList<String>();
            needParamsList.addAll(Arrays.asList(ctc.getMandatoryFields()));
            needParamsList.add("subreportIDs");
            needParamsList.add("queryReasonID");
            
            for(Map.Entry entry:params.entrySet()){
            	String key = Convert.toStr(entry.getKey());
            	if(!"queryType".equals(key)&&needParamsList.contains(key)){
            		 items.add(new QueryCondition.Item(Convert.toStr(entry.getKey()), Convert.toStr(entry.getValue())));
            	}
            }
            // 业务流水号
            items.add(new QueryCondition.Item("refID", ""));
            
            queryCondition.setItems(items);
            conditions.add(queryCondition);
            queryConditions.setConditions(conditions);
            return JSON.toJSONString(queryConditions);
        }

    }
	
}
