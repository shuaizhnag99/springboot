package com.ule.uhj.sldProxy.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.provider.common.CommonTranzCode;
import com.ule.uhj.provider.common.RequestJsonUtil;
import com.ule.uhj.provider.yitu.util.HttpUtils;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.service.CommonService;
import com.ule.uhj.sldProxy.service.InterfaceAccessInfoService;
import com.ule.uhj.sldProxy.sldBiz.BetaInterHelperService;
import com.ule.uhj.sldProxy.util.Check;
import com.ule.uhj.sldProxy.util.JsonResult;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/interface")
public class CommonController {
	private static Logger log = LoggerFactory.getLogger(CommonController.class);
	private static String commonResultStr = "{\"returnCode\":\"%s\",\"returnMessage\":\"%s\"}";

	@Autowired
	BetaInterHelperService betaInterHelperService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	
	protected static String APPKEY_ANDORID="DED9AC1DB38446CFBA3415B9B903C069";
	protected static String APPKEY_IOS="9909C1D3C24872418A83D91539714AC7";

	/***
	* 百融征信发生异常，查询成功
     */
	public static  final String SUCCESS = "000000";

	/***
	 * 用户姓名未传入时的错误代码
	 */
	public  static final String NOT_NAME = "000001";

	/***
	 * 身份证未传入时的错误代码
	 */
	public  static final String NOT_ID = "000002";

	/***
	 * 手机号码传入时的错误代码
	 */
	public  static final String NOT_CELL = "000003";

	/***
	 * 没有获取到查询所需的签证
	 */
	public  static final String NOT_SIGN = "000004";

	/***
	 * 百融征信发生异常，查询失败
	 */
	public static  final String QUERY_FAILED = "000005";

	/***
	 * 银行卡传入时的错误代码
	 */
	public static  final String NOT_CARD = "000006";
	/***
	 * 接口返回无数据时的错误代码
	 */
	public static  final String NO_RESULT = "000007";

	/***
	 * 解析百融征信返回数据异常
	 */
	public static final String DE_DATA_ERROR = "900001";

	/**
	 * @param paras
	 * 检查必须有 tranzCode
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@ResponseBody
	@RequestMapping(value="/commontranz",produces = "application/json; charset=utf-8")
	public String commonTranz(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> paras = new HashMap<String, Object>();
		if(request.getContentType().contains("form-urlencoded")){
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						paras.put(paramName, paramValue);
					}
				}
			}
		}else{
			paras = RequestJsonUtil.getRequestMap(request);
			if(paras == null || paras.size()<=0){
				throw new Exception("json数据未接收!");
			}
		}

		CommonTranzCode ctc = CommonTranzCode.getTranzCode(Integer.parseInt(String.valueOf(paras.get("tranzCode"))));

		if(ctc != null){//找到对应的交易信息
			log.info("ctc is not null. ctc code is : " + ctc.getTranzCode()+" userOnlyId:"+String.valueOf(paras.get("userOnlyId")));
			//检查必填参数
			String cm = checkMandatory(ctc, paras);
			if(cm != null && cm.trim().length() > 0){
				return String.format(commonResultStr, new Object[]{"9999", "please check mandatory parameters!"});
			}
			
			//调用相应方法
			Class<?> clazz = ctc.getClazz();
			Map<String, Object> responseResult = new HashMap<String, Object>();
			String result = null;
			try {
				Object obj = clazz.newInstance();
				Method method = clazz.getMethod(ctc.getMethodName(), Map.class);

				if("beta".equals(PropertiesHelper.getDfs("env"))|| "testing".equals(PropertiesHelper.getDfs("env"))){
					//beta数据走挡板\
					String betaResponse = "";
					if(ctc.getTranzCode() == 8102){
						String idCardSide =Convert.toStr(paras.get("sideType"));
						betaResponse =betaInterHelperService.getBlockTextByCondition(Convert.toStr(paras.get("userOnlyId")),
								Convert.toStr(ctc.getTranzCode()),idCardSide.equals("back")?"302":"301",null);
//						ctc = CommonTranzCode.getTranzCode(idCardSide.equals("back")?81022:81021);
					} else {
						betaResponse =betaInterHelperService.getBlockTextByCondition(Convert.toStr(paras.get("userOnlyId")),Convert.toStr(ctc.getTranzCode()),null,null);
					}
//					String betaResponse =betaInterHelperService.getBetaInterfaceResponseByInterfaceInfo(ctc.getTranzCode().toString());
//					if(ctc.getTranzCode()==81022 || ctc.getTranzCode()==81021){
//						ctc = CommonTranzCode.getTranzCode(8102);
//					}
					if(!com.ule.uhj.sld.util.Check.isBlank(betaResponse)){
						log.info("doService transCode="+ctc.getTranzCode().toString()+";betaResponse="+betaResponse);
						result = betaResponse;
					}
				}

				if(result == null){
					result = (String)method.invoke(obj, paras);
				}
				log.info("userOnlyId:"+String.valueOf(paras.get("userOnlyId"))+" commonTranz result"+result.length());
				
				if("5102".equals(String.valueOf(paras.get("tranzCode")))||"5103".equals(String.valueOf(paras.get("tranzCode")))){
					if(result!=null){
						Map<String,Object> resultM = getMapFromJsonString(result);
						String process_code = Convert.toStr(resultM.get("process_code"));
						if("30000".equals(process_code)){
							try {
					            Thread.sleep(60000L);
					        } catch (InterruptedException e) {
					        	log.error("休眠60秒失败，请核查", e);
					        	Thread.currentThread().interrupt();
					        }
						}
					}
				}
				//request的base64图片信息过滤，防止blob内容过大
				for(String key:paras.keySet()) {
					if(key.indexOf("image")>-1&&Convert.toStr(paras.get(key)).length()>200&&
							("3100".equals(Convert.toStr(paras.get("tranzCode")))
							||"3101".equals(Convert.toStr(paras.get("tranzCode")))
							||"3102".equals(Convert.toStr(paras.get("tranzCode")))
							||"8301".equals(Convert.toStr(paras.get("tranzCode")))
							||"8104".equals(Convert.toStr(paras.get("tranzCode")))
						)) {
						paras.put(key, null);
					}
				}
				//保存接口记录
				interfaceAccessInfoService.saveInterfaceRecord(com.alibaba.fastjson.JSONObject.toJSONString(paras), result,Convert.toStr(paras.get("userOnlyId")),Convert.toStr(paras.get("tranzCode")),"300");
				//记入调用接口记录
				if(!Check.isBlank(result) && !"4100".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"7102".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"7103".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"5101".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"5102".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"5103".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"5104".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6201".equals(Convert.toStr(paras.get("tranzCode")))//算话接口不需要保存，自己重写保存
						&& !"6202".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6203".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6204".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6205".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6206".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6301".equals(Convert.toStr(paras.get("tranzCode")))//同盾运营商数据接口不需要保存，自己重写保存
						&& !"6302".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6303".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6304".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6305".equals(Convert.toStr(paras.get("tranzCode")))
						&& !"6306".equals(Convert.toStr(paras.get("tranzCode")))
						//&& !"8201".equals(Convert.toStr(paras.get("tranzCode"))) 百度OCR文字识别
						&& !"9101".equals(Convert.toStr(paras.get("tranzCode"))) //前海运营商
						&& !"9102".equals(Convert.toStr(paras.get("tranzCode"))) //前海对外投资
						&& !Convert.toStr(paras.get("tranzCode"),"").startsWith("13") //鹏元
						){
					Map<String,Object> responseMap = new HashMap<String, Object>();
					String url = PropertiesHelper.getDfs("SAVE_INTERFACE_INFO");
					String userOnlyId = Convert.toStr(paras.get("userOnlyId"));
					if(!Check.isBlank(userOnlyId)){
						String tranzCode = Convert.toStr(paras.get("tranzCode"));
						String prdCode = Convert.toStr(paras.get("prdCode"));
						String param = com.alibaba.fastjson.JSONObject.toJSONString(paras);
						responseMap.put("userOnlyId", userOnlyId);
						responseMap.put("tranzCode", tranzCode);
						if(!Check.isBlank(prdCode)){
							responseMap.put("prdCode", prdCode);
						}
						responseMap.put("params", param);
						if(result!=null&&!"".equals(result)){
							result = result.replaceAll("&", "######***");
						}
						responseMap.put("result", result);
						log.info("commonTranz param...userOnlyId"+userOnlyId+" tranzCode:"+tranzCode);
						Map<String,String> headers = new HashMap<String, String>();
						Map<String,Object> querys = new HashMap<String, Object>();
						HttpUtils.sendPost(url, null, null, headers, querys, responseMap);
					}
				}
				return result;
			} catch (Exception e) {
				log.error("commonTranz error", e);
				return JsonResult.getInstance().add("returnCode",  "9999").add("returnMessage", e.getMessage()).toString();
			}
		}else{
			return JsonResult.getInstance().add("returnCode",  "9999").add("returnMessage",  "didn't find tranzCode:" + String.valueOf(paras.containsKey("tranzCode")) ).toString();
		}
	}
	
	public static Map getMapFromJsonString(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}
	
//	private Object[] enum2array(Enumeration<?> es){
//		Object[] r = null;
//		if(es != null){
//			List<Object> list = new ArrayList<Object>();
//			while(es.hasMoreElements()){
//				list.add(es.nextElement());
//			}
//			r = list.toArray();
//		}
//		return r;
//	}
	
	/**
	 * 检查必填参数
	 * @param ctc
	 * @param paras
	 * @return
	 */
	public String checkMandatory(CommonTranzCode ctc, Map<String, Object> paras){
		StringBuffer b = new StringBuffer();
		for(String field : ctc.getMandatoryFields()){
			if(paras.get(field) == null){
				b.append(field).append(",");
			}
		}
		if(b.length() > 0){
			b.setLength(b.length() - 1);
		}
		return b.toString();
	}
}
