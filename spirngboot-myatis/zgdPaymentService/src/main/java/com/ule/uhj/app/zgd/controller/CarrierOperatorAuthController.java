package com.ule.uhj.app.zgd.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.model.CustomerAddress;
import com.ule.uhj.app.zgd.model.InterfaceAccessInfoWithBLOBs;
import com.ule.uhj.app.zgd.service.CarrierOperatorAuthService;
import com.ule.uhj.app.zgd.service.CustomerAddressService;
import com.ule.uhj.app.zgd.service.CustomerInfoService;
import com.ule.uhj.app.zgd.service.InterfaceAccessInfoService;
import com.ule.uhj.app.zgd.service.JuXinLiService;
import com.ule.uhj.app.zgd.util.MapSign;
import com.ule.uhj.app.zgd.util.UhjConstant;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.zgd.ZgdQueryClient;
import com.ule.uhj.init.service.ConstantService;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sld.util.DateUtil;
import com.ule.uhj.util.CommonHelper;
import com.ule.uhj.util.DesUtil;
import com.ule.uhj.util.JsonUtil;
import com.ule.uhj.util.PropertiesHelper;
import com.ule.uhj.util.SecurityVerfiy;
import com.ule.uhj.util.UhjWebJsonUtil;
import com.ule.uhj.util.http.HttpClientUtil;

@Controller
@RequestMapping("/carrierOperatorAuth")
public class CarrierOperatorAuthController {
	protected static Log log = LogFactory.getLog(CarrierOperatorAuthController.class);
	
	@Autowired
	private CarrierOperatorAuthService carrieroperatorService;
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private CustomerAddressService  customerAddressService;
	
	@Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private PdfController pdfController;
	@Autowired
	private JuXinLiService juxinliService;
	
	private static String juxinliSupportOperator="联通,福建移动,浙江移动,吉林移动,上海移动,陕西电信,山西电信,江苏电信,河南电信,黑龙江电信,天津电信,北京电信,安徽电信,河北电信,内蒙古电信,贵州电信";
	
	private static String juxinliNeedCertInfo="吉林电信,陕西电信,山西电信,湖南电信,浙江电信,重庆电信,广西电信,云南电信,山东电信,江苏电信";
	
	private static String appkey_url = PropertiesHelper.getDfs("app_interface_url");
	
	private String getUserOnlyId(HttpServletRequest request) throws Exception {
		String usronlyId =CommonHelper.getUserOnlyId(request);
//		String usronlyId="10000026049";
		return usronlyId;
	}
	
	
	static final Map<String, String> provinceMap = new HashMap<String, String>();  
	  
    static {  	//001:0  配置表示 001安徽合肥市   0表示 不配置 1表示聚信立  2表示 算话     如果有问题请联系伍海涛   
		//安徽
		provinceMap.put("province_1","001:0,002:0,003:0,004:0,005:0,006:0,007:0,008:0,009:0,010:0,011:0,012:0,014:0,015:0,016:0,017:0");
		//北京
		provinceMap.put("province_2","018:0,428:0");	
		//重庆
		provinceMap.put("province_3","019:0,433:0");
		//福建
		provinceMap.put("province_4","020:0,021:0,022:0,023:0,024:0,025:0,026:0,027:0,028:0");	
		//甘肃
		provinceMap.put("province_5","029:0,030:0,031:0,032:0,033:0,034:0,035:0,036:0,037:0,038:0,039:0,040:0,041:0,042:0");	
		//广东
		provinceMap.put("province_6","043:0,044:0,045:0,046:0,047:0,048:0,049:0,050:0,051:0,052:0,053:0,054:0,055:0,056:0,057:0,058:0,059:0,060:0,061:0,062:0,063:0");	
		//广西
		provinceMap.put("province_7","064:0,065:0,066:0,067:0,068:0,069:0,070:0,071:0,072:0,073:0,074:0,075:0,076:0,077:0");	
		//贵州
		provinceMap.put("province_8","078:0,079:0,080:0,081:0,082:0,083:0,084:0,085:0,086:0");	
		//海南
		provinceMap.put("province_9","087:0,088:0,431:0,436:0");	
		//河北
		provinceMap.put("province_10","090:0,091:0,092:0,093:0,094:0,095:0,096:0,097:0,098:0,099:0,100:0");	
		//黑龙江
		provinceMap.put("province_11","101:0,102:0,103:0,104:0,105:0,106:0,107:0,108:0,109:0,110:0,111:0,112:0,113:0");	
		//河南
		provinceMap.put("province_12","114:0,115:0,116:0,117:0,118:0,119:0,120:0,121:0,122:0,123:0,124:0,125:0,126:0,127:0,128:0,129:0,130:0,434:0");	
		//湖北
		provinceMap.put("province_14","132:0,133:0,134:0,135:0,136:0,137:0,138:0,139:0,140:0,141:0,142:0,143:0,144:0,145:0");	
		//湖南
		provinceMap.put("province_15","146:0,147:0,148:0,149:0,150:0,151:0,152:0,153:0,154:0,155:0,156:0,157:0,158:0,159:0");	
		//江苏
		provinceMap.put("province_16","160:0,161:0,162:0,163:0,164:0,165:0,166:0,167:0,168:0,169:0,170:0,171:0,172:0");	
		//江西
		provinceMap.put("province_17","173:0,174:0,175:0,176:0,177:0,178:0,179:0,180:0,181:0,182:0,183:0");
		//吉林
		provinceMap.put("province_18","184:0,185:0,186:0,187:0,188:0,189:0,190:0,191:0,192:0");
		//辽宁
		provinceMap.put("province_19","193:0,194:0,195:0,196:0,197:0,198:0,199:0,200:0,201:0,202:0,203:0,204:0,205:0,206:0");
		//内蒙古
		provinceMap.put("province_21","207:0,208:0,209:0,210:0,211:0,212:0,213:0,214:0,215:0,216:0,217:0,218:0");	
		//宁夏
		provinceMap.put("province_22","219:0,220:0,221:0,222:0,223:0");
		//青海
		provinceMap.put("province_23","224:0,225:0,226:0,227:0,228:0,229:0,230:0,231:0");
		//山东
		provinceMap.put("province_24","232:0,233:0,234:0,235:0,236:0,237:0,238:0,239:0,240:0,241:0,242:0,243:0,244:0,245:0,246:0,247:0,248:0");
		//上海
		provinceMap.put("province_25","249:0,430:0");
		//山西
		provinceMap.put("province_26","250:0,251:0,252:0,253:0,254:0,255:0,256:0,257:0,258:0,259:0,260:0");
		//陕西
		provinceMap.put("province_27","261:0,262:0,263:0,264:0,265:0,266:0,267:0,268:0,269:0,270:0");
		//四川
		provinceMap.put("province_28","271:0,272:0,273:0,274:0,275:0,276:0,277:0,278:0,279:0,280:0,281:0,282:0,283:0,284:0,285:0,286:0,287:0,288:0,289:0,290:0,291:0");
		//台湾
		provinceMap.put("province_29","355:0");
		//天津
		provinceMap.put("province_30","292:0,429:0");
		//新疆
		provinceMap.put("province_31","293:0,294:0,295:0,296:0,297:0,298:0,299:0,300:0,301:0,302:0,303:0,304:0,305:0,306:0,307:0");
		//西藏
		provinceMap.put("province_32","308:0,309:0,310:0,311:0,312:0,313:0,314:0");
		//云南
		provinceMap.put("province_33","315:0,316:0,317:0,318:0,319:0,320:0,321:0,322:0,323:0,324:0,325:0,326:0,327:0,328:0,329:0,330:0");
		//浙江
		provinceMap.put("province_34","331:0,332:0,333:0,334:0,335:0,336:0,337:0,338:0,339:0,340:0,341:0");
    }  
	
	
	
//	@SuppressWarnings("unchecked")
//	public String queryContant(String type,String defaultValue) throws Exception{
//		log.info("queryContant type:"+type);
//		//查询流程版本号
//		Map<String,Class<?>> map = new HashMap<String, Class<?>>();
//		map.put(type,String.class);
//		ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
//		Map<String,Object> constantMap = zgdQueryClient.queryZgdConstantValue2(map);
//		List<String> contantList = (List<String>)constantMap.get(type);
//		String contant = Convert.toStr(contantList.get(0),defaultValue);
//		log.info("queryContant content:"+contant);
//		return contant;
//	}
	
	/**
	 * 页面加载获取手机号
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryBindMobileNo")
	@ResponseBody
	public JSONPObject queryBindMobileNo(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			String userOnlyId = getUserOnlyId(request);
			log.info("queryBindMobileNo userOnlyId:"+userOnlyId);
			Map<String,Object> param = new HashMap<String, Object>();
			if(!Check.isBlank(userOnlyId)){
				param.put("userOnlyId", userOnlyId);
				param.put("certType", UhjConstant.certType.idcard);
				Map<String,Object> customerMap = customerInfoService.queryCustomerInfo(param);
				BigDecimal maritalStatus = Convert.toBigDecimal(customerMap.get("maritalStatus"));
				if(maritalStatus!=null&&maritalStatus.compareTo(UhjConstant.maritalStatus.married)==0){
					Map<String,Object> contactMap = customerInfoService.queryCustomerSpouseInfo(param);
					resultMap.put("contactInfo", contactMap);
				}
				resultMap.put("customerInfo", customerMap);
				if(customerMap!=null){
					Map<String,String> parmMap = new HashMap<String, String>();
					//调用聚信力接口进行密码重置
					parmMap.put("userName", Convert.toStr(customerMap.get("name")));
					parmMap.put("certNo", Convert.toStr(customerMap.get("certNo")));
					parmMap.put("account", Convert.toStr(customerMap.get("phone")));
					parmMap.put("userOnlyId", userOnlyId);
					log.info("queryBindMobileNo param:userName"+Convert.toStr(customerMap.get("name"))+" account:"+Convert.toStr(customerMap.get("phone")));
					Map<String,Object> operateMap = queryOperate(parmMap);
					log.info("queryBindMobileNo userOnlyId:"+userOnlyId+" operateMap:"+operateMap.toString());
					resultMap.put("operateMap", operateMap);
				}
				if(resultMap!=null&&resultMap.size()>0){
					resultMap.put("code", "0000");
					resultMap.put("msg", "查询成功");
				}
				
				
				//查询运行商授权配置
				String phone= Convert.toStr(customerMap.get("phone"));
				String userFlag =queryOperate(userOnlyId,phone);
				resultMap.put("userFlag", userFlag);
				if("3".equals(userFlag)){
					Map<String,Object> operateMap = new HashMap<String, Object>();
					operateMap.put("needCertInfo", true);
					resultMap.put("operateMap", operateMap);
				}
			}
		}catch(Exception e){
			log.error("queryBindMobileNo error!",e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	private String queryOperate(String userOnlyId, String phone) {
		String userFlag="1";
		try{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userOnlyId", userOnlyId);
		
		//查询流程版本号
		Map<String,Class<?>> map = new HashMap<String, Class<?>>();
		
		//A 查询是否配置走算话的手机 Convert.toStr(customerMap.get("phone"))
		map.put("phoneConfig",String.class);
		ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
		Map<String,Object> suanhuaPhoneMap = zgdQueryClient.queryZgdConstantValue2(map);
		List<String> operate = (List<String>)suanhuaPhoneMap.get("phoneConfig");
		String phoneConfig = Convert.toStr(operate.get(0),"1");
		log.info("phoneConfig: "+phoneConfig);
		if(phoneConfig.contains(phone)){
			Map<String,String> phoneConfigMap=getMapByString(phoneConfig);
			userFlag=phoneConfigMap.get(phone); 
			log.info("phoneConfig userFlag: "+userFlag);
		}else{
			
			

			String item=matchNum(phone); //运行商 YD、LT、DX
			log.info("initWorkFlow matchNum:"+item);
			
			param.put("addressType", "3");
			CustomerAddress ca= customerAddressService.queryCustomerAddress(param);
			String city=(ca.getCity().split("-"))[0];
			if(city.length()==1){
				city="00"+city;
			}else if(city.length()==2){
				city="0"+city;
			}
			
			String provinceNo=(ca.getProvince().split("-"))[0];
			log.info("initWorkFlow provinceNo:"+provinceNo+";city"+city);
			//B 市一级判断
			userFlag=userFlagByCity(provinceNo,city);
			if("0".equals(userFlag)){
				//C 判断是否走 区域、运营商配置
				map.clear();
				map.put("province-"+provinceNo,String.class);
				Map<String,Object> provinceMap = zgdQueryClient.queryZgdConstantValue2(map);
				operate = (List<String>)provinceMap.get("province-"+provinceNo);
				String provinceStr = Convert.toStr(operate.get(0),"1");
				Map<String,String> userFlagMap=getMapByString(provinceStr);
				
				log.info("initWorkFlow userFlagMap:"+userFlagMap.toString());
				
				userFlag=userFlagMap.get(item); 
				if("0".equals(userFlag)){
					userFlag=userFlagMap.get("default");
				}
				if("0".equals(userFlag)){
					//D 判断配置运营商授权
					map.clear();
					map.put("operate",String.class);
					Map<String,Object> constantMap = zgdQueryClient.queryZgdConstantValue2(map);
					operate = (List<String>)constantMap.get("operate");
					userFlag= Convert.toStr(operate.get(0),"1");
					log.info("initWorkFlow userFlag:"+userFlag);
				}
			}
		}
		
	}catch(Exception e){
		log.error("queryOperate error!",e);
	}
		
	return userFlag;
}

	private String userFlagByCity(String province,String city) {
		String result="0";
		try {
			
			String cityStr = provinceMap.get("province_"+province);
			Map<String,String> cityMap=getMapByString(cityStr);
			result=cityMap.get(city);
		} catch (Exception e) {
			result="0";
		}
		return result;
	}

	private Map<String, String> getMapByString(String provinceStr) {
		Map<String,String> result = new HashMap<String, String>();
		String[] provinceArr = provinceStr.split(",");
		String key="key";
		String value="value";
		for (String a : provinceArr) {
			String [] arr=a.split(":");
			result.put(arr[0], arr[1]);
        }
		return result;
	}

	private Map<String,Object> queryOperate(Map<String,String> parmMap){
		log.info("queryOperate userOnlyId"+Convert.toStr(parmMap.get("userOnlyId")));
		Map<String,Object> juxinMap = new HashMap<String, Object>();
		Map<String,Object> operateMap = new HashMap<String, Object>();
		juxinMap = queryJuXinLi(parmMap);
		String operatorName = "";
		boolean needCertInfo = false;
		boolean support = false;
		if(juxinMap!=null&&juxinMap.containsKey("token")){
			parmMap.put("token", Convert.toStr(juxinMap.get("token")));
			parmMap.put("website", Convert.toStr(juxinMap.get("webSite")));
			operatorName = Convert.toStr(juxinMap.get("operatorName"));
			String webSite = Convert.toStr(juxinMap.get("webSite"));
			log.info("operatorName:"+operatorName+"webSite:"+webSite);
			//查询是否支持找回密码，运营商信息
			String[] supportNames = juxinliSupportOperator.split(",");
			if(!Check.isBlank(operatorName)){
				if("chinaunicom".equals(webSite)){
					support = true;
				}else{
					for(int i = 0 ; i < supportNames.length ; i ++){
						if(operatorName.equals(supportNames[i])){
							support = true;
							break;
						}
					}
				}
				log.info("queryOperate result:"+support);
				operateMap.put("support", support);
				operateMap.put("operatorName", operatorName);
				if(!support){
					if(operatorName.indexOf("湖南移动")!=-1){
						operateMap.put("province", "HNYD");
						operateMap.put("msg", "湖南移动不允许重置密码");
					}else if(operatorName.indexOf("湖南电信")!=-1){
						operateMap.put("province", "HNDX");
						operateMap.put("msg", "湖南电信重置密码");
					}else if(operatorName.indexOf("江苏移动")!=-1){
						operateMap.put("province", "JSYD");
						operateMap.put("msg", "江苏移动重置密码");
					}else if(operatorName.indexOf("移动")!=-1){
						operateMap.put("province", "YD");
						operateMap.put("msg", "该地区暂不支持重置密码，建议拨打客服热线10086或到营业厅办理");
					}else if(operatorName.indexOf("电信")!=-1){
						operateMap.put("province", "DX");
						operateMap.put("msg", "该地区暂不支持重置密码，建议拨打客服热线10000或到营业厅办理");
					}
				}
			}else{
				operateMap.put("code", "0001");
				operateMap.put("msg", Convert.toStr(juxinMap.get("msg")));
			}
			//查询是否需要显示身份填写信息
			String juxinliOperator = juxinliSupportOperator+juxinliNeedCertInfo;
			String[] names = juxinliOperator.split(",");
			if(!Check.isBlank(operatorName)){
				if("chinaunicom".equals(webSite)){
					needCertInfo = true;
				}else{
					for(int i = 0 ; i < names.length ; i ++){
						if(operatorName.equals(names[i])){
							needCertInfo = true;
							break;
						}
					}
				}
			}
			operateMap.put("needCertInfo", needCertInfo);
		}
		return operateMap;
	}
	
	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/sendPassword")
	@ResponseBody
	public JSONPObject sendPassword(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String result = "";
		Map<String,String> parmMap = new HashMap<String, String>();
		try{
			//根据传入的标志判断使用聚信力或者算话(测试时用)
			String userOnlyId = getUserOnlyId(request);
			String userName = Convert.toStr(request.getParameter("userName"));
//			userName = new String(userName.getBytes("ISO-8859-1"),"UTF-8");
			String certNo = Convert.toStr(request.getParameter("certNo"));
			String mobileNo = Convert.toStr(request.getParameter("mobileNo"));
			String flag = Convert.toStr(request.getParameter("flag"));
			String password = Convert.toStr(request.getParameter("password"));
			//调用聚信力接口发送验证码
			parmMap.put("userOnlyId", userOnlyId);
			parmMap.put("userName", userName);
			parmMap.put("certNo", certNo);
			parmMap.put("account", mobileNo);
			parmMap.put("flag", flag);
			parmMap.put("password", password);
			log.info("sendPassword userOnlyId:"+userOnlyId);
			resultMap = queryJuXinLi(parmMap);
			log.info("sendPassword result:"+resultMap.toString());
		}catch(Exception e){
			log.info("sendPassword error:"+e.getMessage());
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 校验验证码
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/checkServerPassword")
	@ResponseBody
	public JSONPObject checkServerPassword(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,String> parmMap = new HashMap<String, String>();
		try{
			//根据传入的标志判断使用聚信力或者算话
			String userOnlyId = getUserOnlyId(request);
			String userName = Convert.toStr(request.getParameter("userName"));
//			userName = new String(userName.getBytes("ISO-8859-1"),"UTF-8");
			String certNo = Convert.toStr(request.getParameter("certNo"));
			String mobileNo = Convert.toStr(request.getParameter("mobileNo"));
			String flag = Convert.toStr(request.getParameter("flag"));
			String token = Convert.toStr(request.getParameter("token"));
			String password = Convert.toStr(request.getParameter("password"));
			String smsCode = Convert.toStr(request.getParameter("smsCode"));
			String website = Convert.toStr(request.getParameter("website"));
			String operatorName = Convert.toStr(request.getParameter("operatorName"));
			String queryPwd = Convert.toStr(request.getParameter("queryPwd"));
			//调用聚信力接口进行数据采集
			parmMap.put("userOnlyId", userOnlyId);
			parmMap.put("userName", userName);
			parmMap.put("certNo", certNo);
			parmMap.put("account", mobileNo);
			parmMap.put("token", token);
			parmMap.put("password", password);
			parmMap.put("captcha", smsCode);
			parmMap.put("flag", flag);
			parmMap.put("webSite", website);
			parmMap.put("queryPwd", queryPwd);
			parmMap.put("operatorName", operatorName);
			parmMap.put("tranzCode", UhjConstant.transCode.COLLECT_MSG);
			log.info("checkServerPassword userName"+userName+" token:"+token+" captcha:"+smsCode+" queryPwd"+queryPwd+" operatorName"+operatorName);
			log.info("checkServerPassword param:"+parmMap.toString());
			resultMap = handlePasswordByJuXinLi(parmMap);
			log.info("checkServerPassword result:"+resultMap.toString());
		}catch(Exception e){
			log.error("checkServerPassword error!",e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/resetPwd")
	@ResponseBody
	public JSONPObject resetPwd(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,String> parmMap = new HashMap<String, String>();
		try{
			//根据传入的标志判断使用聚信力或者算话(测试时用)
			String userName = Convert.toStr(request.getParameter("userName"));
			String certNo = Convert.toStr(request.getParameter("certNo"));
			String mobileNo = Convert.toStr(request.getParameter("mobileNo"));
			String flag = Convert.toStr(request.getParameter("flag"));
			String token = Convert.toStr(request.getParameter("token"));
			String newPassword = Convert.toStr(request.getParameter("newservicecode"));
			String oldPassword = Convert.toStr(request.getParameter("oldservicecode"));
			String smsCode = Convert.toStr(request.getParameter("smsCode"));
			String website = Convert.toStr(request.getParameter("website"));
			String operatorName = Convert.toStr(request.getParameter("operatorName"));
			//调用聚信力接口进行密码重置
				parmMap.put("userName", userName);
//				parmMap.put("name", new String(userName.getBytes("ISO-8859-1"),"UTF-8"));
				parmMap.put("certNo", certNo);
				parmMap.put("account", mobileNo);
				parmMap.put("token", token);
				parmMap.put("password", newPassword);
				parmMap.put("newPassword", oldPassword);
				parmMap.put("captcha", smsCode);
				parmMap.put("flag",  flag);
				parmMap.put("website",  website);
				parmMap.put("userOnlyId", getUserOnlyId(request));
				parmMap.put("operatorName", operatorName);
				log.info("resetPwd param:userName"+userName+" token:"+token+" captcha:"+smsCode);
				resultMap = handlePasswordByJuXinLi(parmMap);
				log.info("resetPwd result:"+resultMap.toString());
		}catch(Exception e){
			log.error("resetPwd error!",e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 查询聚信力数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryJuXinLi(Map<String,String> data){
		Map<String,String> parmMap = new HashMap<String, String>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try{
			//获取支持的数据源列表
//			parmMap.put("userOnlyId", Convert.toStr(data.get("userOnlyId")));
//			parmMap.put("tranzCode", UhjConstant.transCode.DATA_SOURCE);
//			String dataSourceStr = HttpClientUtil.sendPost(appkey_url, parmMap);
//			Map<String,Object> dataMap = JsonUtil.getMapFromJsonString(dataSourceStr);
			//提交申请表单获取回执信息
//			JSONArray selected_website = new JSONArray();
//			JSONObject paramWebsite = new JSONObject();
			String token = "";
//			if(dataMap!=null&&dataMap.containsKey("data")){
//				List<Map<String,Object>> dataList = (List<Map<String,Object>>)dataMap.get("data");
//				if(dataList!=null&&dataList.size()>0){
//					for(int i = 0;i<dataList.size();i++){
//						Map<String,Object> webMap = dataList.get(i);
//						paramWebsite.put("website", Convert.toStr(webMap.get("website")));
//					    paramWebsite.put("category", Convert.toStr(webMap.get("category")));
//					    selected_website.add(paramWebsite);
//					    resultMap.put("selected_website", selected_website);
//					}
//					parmMap.put("selected_website", selected_website.toString());
//					parmMap.put("name", new String(Convert.toStr(data.get("userName")).getBytes("ISO-8859-1"),"UTF-8"));
					parmMap.put("name", Convert.toStr(data.get("userName")));
					parmMap.put("id_card_num", Convert.toStr(data.get("certNo")));
					parmMap.put("cell_phone_num", Convert.toStr(data.get("account")));
					parmMap.put("tranzCode", UhjConstant.transCode.GET_RECEIPT);
					parmMap.put("userOnlyId", Convert.toStr(data.get("userOnlyId")));
					String receiptStr = HttpClientUtil.sendPost(appkey_url, parmMap);
					Map<String,Object> receiptMap = JsonUtil.getMapFromJsonString(receiptStr);
					if(receiptMap!=null&&receiptMap.containsKey("data")){
						Map<String,Object> daMap = (Map<String,Object>)receiptMap.get("data");
						if(daMap!=null&&daMap.containsKey("datasource")){
							Map<String,Object> datasourceMap = (Map<String,Object>)daMap.get("datasource");
							if(datasourceMap!=null&&datasourceMap.containsKey("website")){
								parmMap.put("webSite", Convert.toStr(datasourceMap.get("website")));
								resultMap.put("webSite", Convert.toStr(datasourceMap.get("website")));
								parmMap.put("operatorName", Convert.toStr(datasourceMap.get("name")));
								resultMap.put("operatorName", Convert.toStr(datasourceMap.get("name")));
							}
						}
						if(daMap!=null&&daMap.containsKey("token")){
							
							token = Convert.toStr(daMap.get("token"));
						}
						log.info("queryJuXinLi userOnlyId:"+Convert.toStr(data.get("userOnlyId"))+" token:"+token);
						parmMap.put("token", token);
						resultMap.put("token", token);
						if("sendPassword".equals(Convert.toStr(data.get("flag")))){
							parmMap.put("flag", Convert.toStr(data.get("flag")));
							parmMap.put("password", Convert.toStr(data.get("password")));
							parmMap.put("account", Convert.toStr(data.get("account")));
							parmMap.put("userOnlyId", Convert.toStr(data.get("userOnlyId")));
							resultMap = handlePasswordByJuXinLi(parmMap);
						}
					}else{
						resultMap.put("code", "0001");
						resultMap.put("msg", Convert.toStr(null!=receiptMap?receiptMap.get("message"):""));
					}
//				}
//			}
		}catch(Exception e){
			log.error("queryJuXinLi error!",e);
			resultMap.put("code", "0001");
			resultMap.put("msg", "请求超时");
		}
		return resultMap;
	}
	
	/**
	 * 聚信力发送验证码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> handlePasswordByJuXinLi(Map<String,String> parmMap)throws Exception{
		log.info("handlePasswordByJuXinLi parmMap:userOnlyId"+Convert.toStr(parmMap.get("userOnlyId"))+"flag:"+Convert.toStr(parmMap.get("flag")));
		Map<String,Object> resultMap = new HashMap<String, Object>();
		parmMap.put("tranzCode", UhjConstant.transCode.COLLECT_MSG);
		//提交数据源采集请求，第一次请求是发送验证码
		if("sendPassword".equals(Convert.toStr(parmMap.get("flag")))){
			log.info("parmMap:"+parmMap.toString());
			Map<String,String> requestMap = new HashMap<String, String>();
			requestMap.put("token", parmMap.get("token"));
			requestMap.put("account", parmMap.get("account"));
			requestMap.put("password", parmMap.get("password"));
			requestMap.put("website", parmMap.get("website"));
			String collectStr = juxinliService.queryCollectMsg(parmMap);
			log.info("sendPassword userOnlyId="+Convert.toStr(parmMap.get("userOnlyId"))+" collectStr"+collectStr);
			Map<String,Object> collectMap = JsonUtil.getMapFromJsonString(collectStr);
			if(collectMap!=null&&collectMap.containsKey("data")){
				Map<String,Object> daMap = (Map<String,Object>)collectMap.get("data");
				if((boolean)collectMap.get("success").equals(true)&&"10002".equals(Convert.toStr(daMap.get("process_code")))){
					resultMap.put("code", "0000");
					resultMap.put("msg", "验证码发送成功");
					resultMap.put("processCode", Convert.toStr(daMap.get("process_code")));
					resultMap.put("token", Convert.toStr(parmMap.get("token")));
					resultMap.put("selected_website", Convert.toStr(parmMap.get("selected_website")));
					resultMap.put("website", Convert.toStr(parmMap.get("webSite")));
					resultMap.put("operatorName", Convert.toStr(parmMap.get("operatorName")));
				}else if((boolean)collectMap.get("success").equals(true)&&"10008".equals(Convert.toStr(daMap.get("process_code")))){
					resultMap.put("code", "0000");
					resultMap.put("msg", "联通用户直接采集数据");
					resultMap.put("processCode", Convert.toStr(daMap.get("process_code")));
					resultMap.put("token", Convert.toStr(parmMap.get("token")));
					resultMap.put("selected_website", Convert.toStr(parmMap.get("selected_website")));
					resultMap.put("website", Convert.toStr(parmMap.get("webSite")));
					//信息采集申请结束，保存请求数据
					Map<String,String> paMap = new HashMap<String, String>();
					paMap.put("userName", Convert.toStr(parmMap.get("name")));
					paMap.put("certNo", Convert.toStr(parmMap.get("id_card_num")));
					paMap.put("account", Convert.toStr(parmMap.get("cell_phone_num")));
					paMap.put("userOnlyId", Convert.toStr(parmMap.get("userOnlyId")));
					paMap.put("token", Convert.toStr(parmMap.get("token")));
					paMap.put("website", Convert.toStr(parmMap.get("webSite")));
					saveJuXinLiRequestData(paMap);
				}else if((boolean)collectMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))){
					resultMap.put("code", "0001");
					resultMap.put("msg", Convert.toStr(daMap.get("content")));
				}else if((boolean)collectMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))
						&&("100022".equals(Convert.toStr(daMap.get("process_code")))||"100023".equals(Convert.toStr(daMap.get("process_code"))))){
					resultMap.put("code", "0003");
					resultMap.put("msg", "请前往移动官网设置查询密码，如你已设置，请在页面输入查询密码");
					resultMap.put("processCode", Convert.toStr(daMap.get("process_code")));
					resultMap.put("token", Convert.toStr(parmMap.get("token")));
					resultMap.put("selected_website", Convert.toStr(parmMap.get("selected_website")));
					resultMap.put("website", Convert.toStr(parmMap.get("webSite")));
					resultMap.put("operatorName", Convert.toStr(parmMap.get("operatorName")));
				}else if((boolean)collectMap.get("success").equals(true)&&"ERROR".equals(Convert.toStr(daMap.get("type")))){
					if("30000".equals(Convert.toStr(daMap.get("process_code")))){
						resultMap.put("code", "0001");
						resultMap.put("msg", Convert.toStr(daMap.get("content")));
					}
				}else{
					resultMap.put("code", "0001");
					resultMap.put("msg", Convert.toStr(collectMap.get("message")));
				}
			}else{
				resultMap.put("code", "0001");
				resultMap.put("msg", Convert.toStr(null!=collectMap?collectMap.get("message"):""));
			}
		}
		//提交数据源采集请求，第二次请求是校验验证码，通过则进行数据采集
		else if("checkPassword".equals(Convert.toStr(parmMap.get("flag")))){
			parmMap.put("type", "SUBMIT_CAPTCHA");
			if(!Check.isBlank(parmMap.get("queryPwd"))){
				parmMap.put("type", "SUBMIT_QUERY_PWD");
			}
			String checkStr = juxinliService.queryCollectMsg(parmMap);
//			String checkStr = HttpClientUtil.sendPost(appkey_url, parmMap);
			log.info("checkPassword userOnlyId="+Convert.toStr(parmMap.get("userOnlyId"))+" checkStr"+checkStr);
			Map<String,Object> checkMap = JsonUtil.getMapFromJsonString(checkStr);
			if(checkMap!=null&&checkMap.containsKey("data")){
				Map<String,Object> daMap = (Map<String,Object>)checkMap.get("data");
				log.info("result data:"+daMap.toString());
				if((boolean)checkMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))&&"10008".equals(Convert.toStr(daMap.get("process_code")))){
					resultMap.put("code", "0000");
					resultMap.put("msg", Convert.toStr(daMap.get("content")));
					//信息采集申请结束，保存请求数据
					Map<String,String> paMap = new HashMap<String, String>();
					paMap.put("userName", Convert.toStr(parmMap.get("userName")));
					paMap.put("certNo", Convert.toStr(parmMap.get("certNo")));
					paMap.put("account", Convert.toStr(parmMap.get("account")));
					paMap.put("userOnlyId", Convert.toStr(parmMap.get("userOnlyId")));
					paMap.put("token", Convert.toStr(parmMap.get("token")));
					paMap.put("website", Convert.toStr(parmMap.get("webSite")));
					saveJuXinLiRequestData(paMap);
				}else if((boolean)checkMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))
						&&("10002".equals(Convert.toStr(daMap.get("process_code")))||"10001".equals(Convert.toStr(daMap.get("process_code")))||"10004".equals(Convert.toStr(daMap.get("process_code")))||"10006".equals(Convert.toStr(daMap.get("process_code"))))){
					resultMap.put("code", "0001");
					resultMap.put("msg", Convert.toStr(daMap.get("content")));
					parmMap.put("token", Convert.toStr(parmMap.get("token")));
					parmMap.put("website", Convert.toStr(parmMap.get("webSite")));
//					parmMap.put("name", new String(Convert.toStr(data.get("userName")).getBytes("ISO-8859-1"),"UTF-8"));
					parmMap.put("name", Convert.toStr(parmMap.get("userName")));
					parmMap.put("id_card_num", Convert.toStr(parmMap.get("certNo")));
					parmMap.put("password", Convert.toStr(parmMap.get("password")));
					parmMap.put("account", Convert.toStr(parmMap.get("account")));
					parmMap.put("userOnlyId", Convert.toStr(parmMap.get("userOnlyId")));
//					handlePasswordByJuXinLi(parmMap);
				}else if((boolean)checkMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))
						&&("100017".equals(Convert.toStr(daMap.get("process_code")))||"100018".equals(Convert.toStr(daMap.get("process_code"))))){
					resultMap.put("code", "0002");
					resultMap.put("msg", Convert.toStr(daMap.get("content")));
					//信息采集申请结束，保存请求数据
					Map<String,String> paMap = new HashMap<String, String>();
					paMap.put("userName", Convert.toStr(parmMap.get("userName")));
					paMap.put("certNo", Convert.toStr(parmMap.get("certNo")));
					paMap.put("account", Convert.toStr(parmMap.get("account")));
					paMap.put("userOnlyId", Convert.toStr(parmMap.get("userOnlyId")));
					paMap.put("token", Convert.toStr(parmMap.get("token")));
					paMap.put("website", Convert.toStr(parmMap.get("webSite")));
				}else if((boolean)checkMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))
						&&("100022".equals(Convert.toStr(daMap.get("process_code")))||"100023".equals(Convert.toStr(daMap.get("process_code"))))){
					resultMap.put("code", "0003");
					resultMap.put("msg", "请前往移动官网设置查询密码，如你已设置，请在页面输入查询密码");
					resultMap.put("website", Convert.toStr(parmMap.get("webSite")));
					resultMap.put("operatorName", Convert.toStr(parmMap.get("operatorName")));
					//信息采集申请结束，保存请求数据
					Map<String,String> paMap = new HashMap<String, String>();
					paMap.put("userName", Convert.toStr(parmMap.get("userName")));
					paMap.put("certNo", Convert.toStr(parmMap.get("certNo")));
					paMap.put("account", Convert.toStr(parmMap.get("account")));
					paMap.put("userOnlyId", Convert.toStr(parmMap.get("userOnlyId")));
					paMap.put("token", Convert.toStr(parmMap.get("token")));
					paMap.put("website", Convert.toStr(parmMap.get("webSite")));
				}else if((boolean)checkMap.get("success").equals(true)&&"ERROR".equals(Convert.toStr(daMap.get("type")))){
					resultMap.put("code", "0004");
					resultMap.put("msg", Convert.toStr(daMap.get("content")));
				}else if((boolean)checkMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))){
					resultMap.put("code", "0005");
					resultMap.put("msg", Convert.toStr(daMap.get("content")));
				}else{
					resultMap.put("code", "9999");
					resultMap.put("msg", Convert.toStr(checkMap.get("message")));
				}
			}else{
				resultMap.put("code", "9999");
				resultMap.put("msg", Convert.toStr(checkMap.get("message")));
			}
		}
		//聚信力重置密码发送验证码
		else if("sendResetPassword".equals(Convert.toStr(parmMap.get("flag")))){
			parmMap.put("userName", Convert.toStr(parmMap.get("userName")));
			Map<String,Object> juxinMap = queryJuXinLi(parmMap);
			if(juxinMap!=null&&juxinMap.containsKey("token")){
				parmMap.put("token", Convert.toStr(juxinMap.get("token")));
				parmMap.put("website", Convert.toStr(juxinMap.get("webSite")));
//				String operatorName = Convert.toStr(juxinMap.get("operatorName"));
//				String webSite = Convert.toStr(juxinMap.get("webSite"));
				resultMap.put("token", Convert.toStr(juxinMap.get("token")));
				resultMap.put("website", Convert.toStr(juxinMap.get("webSite")));
				resultMap.put("operatorName", Convert.toStr(juxinMap.get("operatorName")));
				parmMap.put("tranzCode", UhjConstant.transCode.RESET_PDW);
				parmMap.put("name", Convert.toStr(juxinMap.get("name")));
				parmMap.put("idCard", Convert.toStr(juxinMap.get("id_card_num")));
				String resendStr = HttpClientUtil.sendPost(appkey_url, parmMap);
				log.info("sendResetPassword userOnlyId="+Convert.toStr(parmMap.get("userOnlyId"))+" resendStr"+resendStr);
				Map<String,Object> resendMap = JsonUtil.getMapFromJsonString(resendStr);
				if(resendMap!=null&&resendMap.containsKey("data")){
					Map<String,Object> daMap = (Map<String,Object>)resendMap.get("data");
					if((boolean)resendMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))&&"10002".equals(Convert.toStr(daMap.get("process_code")))){
						resultMap.put("code", "0000");
						resultMap.put("msg", Convert.toStr(daMap.get("content")));
						resultMap.put("token", Convert.toStr(juxinMap.get("token")));
					}else if((boolean)resendMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))){
						resultMap.put("code", "9999");
						resultMap.put("msg", Convert.toStr(daMap.get("content")));
					}else if((boolean)resendMap.get("success").equals(true)&&"ERROR".equals(Convert.toStr(daMap.get("type")))){
						resultMap.put("code", "9999");
						resultMap.put("msg", Convert.toStr(daMap.get("content")));
					}else{
						resultMap.put("code", "9999");
						resultMap.put("msg", Convert.toStr(daMap.get("message")));
					}
				}else{
					resultMap.put("code", "9999");
					resultMap.put("msg", Convert.toStr(resendMap.get("message")));
				}
			}
		}
		//聚信力重置密码校验验证码
		else if("checkResetPassword".equals(Convert.toStr(parmMap.get("flag")))){
			parmMap.put("type", "SUBMIT_RESET_PWD");
			parmMap.put("tranzCode", UhjConstant.transCode.RESET_PDW);
//			//重置密码成功，联通直接运营商授权
				log.info("checkResetPassword param:"+parmMap.toString());
				String resendStr = HttpClientUtil.sendPost(appkey_url, parmMap);
				log.info("checkResetPassword userOnlyId="+Convert.toStr(parmMap.get("userOnlyId"))+" resendStr"+resendStr);
				Map<String,Object> resendMap = JsonUtil.getMapFromJsonString(resendStr);
				if(resendMap!=null&&resendMap.containsKey("data")){
					Map<String,Object> daMap = (Map<String,Object>)resendMap.get("data");
					if((boolean)resendMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))&&"11000".equals(Convert.toStr(daMap.get("process_code")))){
						resultMap.put("code", "0000");
						resultMap.put("msg", Convert.toStr(daMap.get("content")));
						resultMap.put("pass", "N");
					}else if((boolean)resendMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))
							&&("10001".equals(Convert.toStr(daMap.get("process_code")))||"10004".equals(Convert.toStr(daMap.get("process_code")))||"10006".equals(Convert.toStr(daMap.get("process_code"))))){
						resultMap.put("code", "0001");
						resultMap.put("msg", "为确保账户安全，请再次输入验证码");
						parmMap.put("token", Convert.toStr(parmMap.get("token")));
						parmMap.put("website", Convert.toStr(parmMap.get("webSite")));
//						parmMap.put("name", new String(Convert.toStr(data.get("userName")).getBytes("ISO-8859-1"),"UTF-8"));
						parmMap.put("name", Convert.toStr(parmMap.get("userName")));
						parmMap.put("id_card_num", Convert.toStr(parmMap.get("certNo")));
						parmMap.put("password", Convert.toStr(parmMap.get("password")));
						parmMap.put("account", Convert.toStr(parmMap.get("account")));
						parmMap.put("userOnlyId", Convert.toStr(parmMap.get("userOnlyId")));
//						handlePasswordByJuXinLi(parmMap);
					}else if((boolean)resendMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))&&"31000".equals(Convert.toStr(daMap.get("process_code")))){
						resultMap.put("code", "0001");
						resultMap.put("msg", "重置密码有误，建议到营业厅重置密码");
						resultMap.put("pass", "N");
					}else if((boolean)resendMap.get("success").equals(true)&&"CONTROL".equals(Convert.toStr(daMap.get("type")))){
						resultMap.put("code", "9999");
						resultMap.put("msg", Convert.toStr(daMap.get("content")));
						resultMap.put("pass", "N");
					}else if((boolean)resendMap.get("success").equals(true)&&"ERROR".equals(Convert.toStr(daMap.get("type")))){
						resultMap.put("code", "9999");
						resultMap.put("msg", Convert.toStr(daMap.get("content")));
						resultMap.put("pass", "N");
					}else{
						resultMap.put("code", "9999");
						resultMap.put("msg", Convert.toStr(daMap.get("message")));
						resultMap.put("pass", "N");
					}
				}else{
					resultMap.put("code", "9999");
					resultMap.put("msg", Convert.toStr(resendMap.get("message")));
					resultMap.put("pass", "N");
				}
			}
//		}
		return resultMap;
	}
	
	/**
	 * 初始化工作流
	 * @param userOnlyId
	 */
	@SuppressWarnings("unchecked")
	public void initWorkFlow(String userOnlyId,String userName){
		log.info("initWorkFlow userOnlyId:"+userOnlyId+" userName:"+userName);
		Map<String,String> paramMap = new HashMap<String, String>();
		String initUrl = PropertiesHelper.getDfs("INIT_WORK_FLOW_URL");
		log.info("initWorkFlow userOnlyId:"+userOnlyId+" initUrl:"+initUrl);
//		HttpClient client = null;
		try {
			//查询流程版本号
			//查询流程版本号
			Map<String,Class<?>> map = new HashMap<String, Class<?>>();
			map.put("ProcessKey",String.class);
			ZgdQueryClient zgdQueryClient = WildflyBeanFactory.getZgdQueryClient();
			Map<String,Object> constantMap = zgdQueryClient.queryZgdConstantValue2(map);
			List<String> ProcessKey = (List<String>)constantMap.get("ProcessKey");
			String processKey = Convert.toStr(ProcessKey.get(0),"zgdApprove_v2");
			log.info("initWorkFlow processKey:"+processKey);
			
			Map<String,Object> creditMap = carrieroperatorService.queryCreditApplyInfo(userOnlyId);
//			Map<String, Object> vps = VpsInfoService.getVpsInfoByUserOnlyId(userOnlyId);
//			String orgCode=Convert.toStr(vps.get(VpsInfoService.OrgCode));
			String code = Convert.toStr(creditMap.get("orgCode"));
//			orgCode = code!=null&&!"".equals(code)?code:orgCode;
			paramMap.put("initType", "startProcessInstan");
			paramMap.put("processKey", processKey);
			paramMap.put("businessKey", Convert.toStr(creditMap.get("appId")));
			paramMap.put("customerName", userName);
			paramMap.put("userOnlyId", userOnlyId);
			paramMap.put("applyTime", DateUtil.currTimeStr());
			paramMap.put("status", UhjConstant.applyStatus.APPLY_STATUS_REVIEW);
			paramMap.put("appId", Convert.toStr(creditMap.get("appId")));
			paramMap.put("orgCode", code);
			log.info("initWorkFlow paramMap:"+paramMap);
			Map<String, String> p=MapSign.sign(paramMap,PropertiesHelper.getSecurityKey("workFlowSecur"));
			String jsonStr = JsonUtil.getJsonStringFromMap(p);
			
			try( CloseableHttpClient client =  HttpClients.createDefault();){
				HttpPost httpPost = new HttpPost(initUrl);
				RequestConfig config=RequestConfig.custom().setConnectTimeout(45000)
						.setSocketTimeout(45000)
						.setConnectionRequestTimeout(60000)
						.build();
				httpPost.setConfig(config);
//				client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//				client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
//				client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
				log.info("HttpClientUtil类httpPost方法 : 本次发送json格式数据，jsonString = "+jsonStr);
				StringEntity paramerEntity = new StringEntity(jsonStr,"UTF-8");
				paramerEntity.setContentEncoding("UTF-8");
				paramerEntity.setContentType("application/json");
				httpPost.setEntity(paramerEntity);
				HttpEntity responseEntity  = client.execute(httpPost).getEntity();
				log.info("HttpClientUtil类httpPost方法 : 请求完毕！"+httpPost.getRequestLine());
				String responseStr = EntityUtils.toString(responseEntity,"UTF-8");
				
			}catch (Exception e){
				log.error("initWorkFlow DefaultHttpClient error!",e);
			}
//			HttpClientUtil.sendPost(initUrl, p);
		} catch (Exception e) {
			log.error("initWorkFlow error!",e);
		}
//		finally{
//			if(client!=null){
//				client.getConnectionManager().shutdown();
//			}
//		}
	}
	
//	public static void main(String[] args) {
//		initWorkFlow("10000000391","赵杰");
//	}
	
	@RequestMapping("/saveData")
	@ResponseBody
	public JSONPObject saveData(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,String> param = new HashMap<String, String>();
		param.put("userOnlyId", Convert.toStr(request.getParameter("userOnlyId")));
//		String userName = Convert.toStr(request.getParameter("userName"));
//		try {
//			param.put("userName", new String(userName.getBytes("ISO-8859-1"),"UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		param.put("userName", Convert.toStr(request.getParameter("userName")));
		param.put("certNo", Convert.toStr(request.getParameter("certNo")));
		param.put("account", Convert.toStr(request.getParameter("account")));
		param.put("token", Convert.toStr(request.getParameter("token")));
		saveJuXinLiRequestData(param);
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(""));
	}
	
	/**
	 * 聚信立保存报告请求接口数据
	 * @param param
	 * @return
	 */
	public Map<String,Object> saveJuXinLiRequestData(Map<String,String> param){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String userOnlyId = Convert.toStr(param.get("userOnlyId"));
		String name = Convert.toStr(param.get("userName"));
		String idCard = Convert.toStr(param.get("certNo"));
		String phone = Convert.toStr(param.get("account"));
		String token = Convert.toStr(param.get("token"));
		String org_name = Convert.toStr(PropertiesHelper.getDfs("org_name"));
		String client_secret = Convert.toStr(PropertiesHelper.getDfs("client_secret"));
		log.info("saveJuXinLiRequestData userOnlyId:"+userOnlyId+" token:"+token+" org_name:"+org_name+" client_secret:"+client_secret);
		Map<String,Object> requestMap = new HashMap<String, Object>();
		try {
			requestMap.put("userOnlyId", userOnlyId);
			requestMap.put("name", name);
			requestMap.put("idCard", idCard);
			requestMap.put("phone", phone);
			requestMap.put("token", token);
			requestMap.put("org_name", org_name);
			requestMap.put("client_secret", client_secret);
			return carrieroperatorService.saveRequestData(userOnlyId,"juxinli",requestMap);
		} catch(Exception e){
			log.error("saveJuXinLiRequestData error!",e);
		}
		return resultMap;
	}
	
	/**
	 * 查询报告是否已获取
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@RequestMapping("/queryReportData")
	@ResponseBody
	public JSONPObject queryReportData(HttpServletRequest request, HttpServletResponse response,@RequestParam String jsoncallback){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			String userOnlyId = getUserOnlyId(request);
			resultMap = carrieroperatorService.queryJuXinLiData(userOnlyId);
		} catch(Exception e){
			log.error("queryReportData error!",e);
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(resultMap));
	}
	
	/**
	 * 根据请求数据去查询聚信立接口,返回值入库
	 * http://money.beta.ule.com:8080/lendvps/carrierOperatorAuth/saveJuXinLiResponseData.do
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("/saveJuXinLiResponseData")
	@ResponseBody
	public void saveJuXinLiResponseData(HttpServletRequest request){
		try{
			//验签
			SecurityVerfiy.securityVerfiy(request, PropertiesHelper.getSecurityKey("uhjLendvpsSecurity"));
			//查询未获取到报告的即状态是3的7101的token
			List<InterfaceAccessInfoWithBLOBs> interfaceAccessBLOBs = carrieroperatorService.queryUnRequestTokenData();
			for(InterfaceAccessInfoWithBLOBs blobs:interfaceAccessBLOBs){
				if(UhjConstant.transCode.REPORT_TOKEN.equals(blobs.getInterfaceType())){
					//查询聚信立报告
					queryJuxinliInfo(request,blobs);
				}else if(UhjConstant.transCode.REPORT_BY_STOKEN.equals(blobs.getInterfaceType())){
					//查询算话报告
					boolean rs =carrieroperatorService.querySuanHuaInfo(blobs);
					
					if(rs){
						String userOnlyId = blobs.getUserOnlyId();
						
						//开启工作流
						Map<String,Object> para = new HashMap<String, Object>();
						para.put("userOnlyId", userOnlyId);
						para.put("certType", UhjConstant.certType.idcard);
						Map<String,Object> customerMap = customerInfoService.queryCustomerInfo(para);
						
						log.info("修改状态，开始工作流。。。。。userOnlyId:"+userOnlyId);
						carrieroperatorService.updateApplyStatus(userOnlyId,"2");						
						initWorkFlow(userOnlyId,Convert.toStr(customerMap.get("name")));
						log.info("修改状态。。。。。userOnlyId:"+userOnlyId);
						
					}
				}else if(UhjConstant.transCode.TONGDUN_QUERY.equals(blobs.getInterfaceType())){
					//查询算话报告
					boolean rs =carrieroperatorService.queryTongdunInfo(blobs);
					
					if(rs){
						String userOnlyId = blobs.getUserOnlyId();
						
						//开启工作流
						Map<String,Object> para = new HashMap<String, Object>();
						para.put("userOnlyId", userOnlyId);
						para.put("certType", UhjConstant.certType.idcard);
						Map<String,Object> customerMap = customerInfoService.queryCustomerInfo(para);
						
						log.info("修改状态，开始工作流。。。。。userOnlyId:"+userOnlyId);
						carrieroperatorService.updateApplyStatus(userOnlyId,"3");						
						initWorkFlow(userOnlyId,Convert.toStr(customerMap.get("name")));
						log.info("修改状态。。。。。userOnlyId:"+userOnlyId);
						
					}
				}
			}
			
		}catch(Exception e){
			log.error("saveJuXinLiResponseData!",e);
		}
	}

	public void queryJuxinliInfo(HttpServletRequest request,InterfaceAccessInfoWithBLOBs blobs ){
		try{
			//查询未获取到报告的即状态是3的7101的token
			if(blobs!=null){
				String access_token = analysisToken(blobs);
				String userOnlyId = blobs.getUserOnlyId();
				log.info("userOnlyId:"+userOnlyId+" access_token:"+access_token);
				//查询报告状态接口查询报告状态
				int isSuccess = queryJxinliAccessInfo(userOnlyId,access_token);
				log.info("userOnlyId:"+userOnlyId+" isSuccess:"+isSuccess);
				if(isSuccess==1){
					//查询具体的7102,7103报告
					boolean isComplete = queryCompleteData(userOnlyId,access_token);
					log.info("userOnlyId:"+userOnlyId+" isComplete:"+isComplete);
					if(isComplete){
						//开启工作流
						boolean isInit = judgeInitWorkFlow(isComplete, userOnlyId);
						log.info("userOnlyId:"+userOnlyId+" isInit:"+isInit);
						if(isInit){
							//上传合同到服务器
							try{
								saveContactPdf(request,userOnlyId);
								log.info("save pdf userOnlyId:"+userOnlyId);
							}catch(Exception e){
								log.error("saveJuXinLiResponseData error!",e);
							}
						}
					}else{
						log.info("状态返回成功，但未获取到报告：userOnlyId:"+userOnlyId+" isSuccess:"+isSuccess);
						carrieroperatorService.updateReportData(userOnlyId);
					}
				}else if(isSuccess==3){
					log.info("状态返回获取中，未获取到报告：userOnlyId:"+userOnlyId+" isSuccess:"+isSuccess);
					carrieroperatorService.updateReportData(userOnlyId);
				}else{
					log.info("状态返回失败，获取不到报告：userOnlyId:"+userOnlyId+" isSuccess:"+isSuccess);
					carrieroperatorService.updateReportDataFaild(userOnlyId);
				}
			}
		}catch(Exception e){
			log.error("queryJuxinliInfo!",e);
		}
	}
	//查询7101获取access_token
	@SuppressWarnings("unchecked")
	public String analysisToken(InterfaceAccessInfoWithBLOBs blobs){
		String access_token = "";
		try{
			String userOnlyId = blobs.getUserOnlyId();
			log.info("analysisToken begin userOnlyId:"+userOnlyId);
			String requestInfo = new String(blobs.getRequestInfo());
			Map<String,Object> requestMap = JsonUtil.getMapFromJsonString(requestInfo);
			String org_name = Convert.toStr(requestMap.get("org_name"));
			String clientSecret = Convert.toStr(requestMap.get("client_secret"));
			String name = Convert.toStr(requestMap.get("name"));
			String idCard = Convert.toStr(requestMap.get("idCard"));
			String phone = Convert.toStr(requestMap.get("phone"));
			String token = Convert.toStr(requestMap.get("token"));
			log.info("userOnlyId:"+userOnlyId+"analysisToken name"+name+"idCard"+DesUtil.encrypt(idCard)+"phone"+phone);
			Map<String,String> param = new HashMap<String, String>();
			//获得安全凭证码
			param.put("userOnlyId", userOnlyId);
			param.put("token", token);
			param.put("phone", phone);
			param.put("idCard", idCard);
			param.put("name", name);
			param.put("client_secret", clientSecret);
			param.put("org_name", org_name);
			param.put("tranzCode", UhjConstant.transCode.REPORT_TOKEN);
			log.info("userOnlyId:"+userOnlyId+"analysisToken request:"+param.toString());
			//获得安全凭证码
			String reportTokenStr = HttpClientUtil.sendPost(appkey_url, param);
			Map<String,Object> reportTokenMap = JsonUtil.getMapFromJsonString(reportTokenStr);
			if(reportTokenMap!=null&&reportTokenMap.containsKey("access_token")){
				access_token = Convert.toStr(reportTokenMap.get("access_token"));
			}
			log.info("analysisToken token:"+access_token);
		}catch(Exception e){
			log.error("analysisToken error!",e);
		}
		return access_token;
	}
	//查询报告状态
	@SuppressWarnings("unchecked")
	public int queryJxinliAccessInfo(String userOnlyId,String access_token){
		int flag = 2;
		try{
			//查询报告状态接口查询报告状态
			log.info("查询报告结束，准备数据:"+userOnlyId);
			List<InterfaceAccessInfoWithBLOBs> accessJobStatusBLOBs = carrieroperatorService.queryUnRequestJobStatus(userOnlyId);
			if(accessJobStatusBLOBs!=null&&accessJobStatusBLOBs.size()>0){
				String requestInfo = new String(accessJobStatusBLOBs.get(0).getRequestInfo());
				Map<String,Object> requestMap = JsonUtil.getMapFromJsonString(requestInfo);
				log.info("userOnlyId:"+userOnlyId+"queryJxinliAccessInfo request:"+requestMap.toString());
				//获得报告拉取状态
				String clientSecret = Convert.toStr(requestMap.get("client_secret"));
				String name = Convert.toStr(requestMap.get("name"));
				String idCard = Convert.toStr(requestMap.get("idCard"));
				String phone = Convert.toStr(requestMap.get("phone"));
				log.info("userOnlyId:"+userOnlyId+"queryJxinliAccessInfo name"+name+"idCard"+DesUtil.encrypt(idCard)+"phone"+phone);
				Map<String,String> param = new HashMap<String, String>();
				//获得安全凭证码
				param.put("userOnlyId", userOnlyId);
				param.put("phone", phone);
				param.put("idCard", idCard);
				param.put("name", name);
				param.put("client_secret", clientSecret);
				param.put("category", "mobile");
				param.put("rptCheck", "false");
				param.put("tranzCode", UhjConstant.transCode.ACCESS_JOB_STATUS);
				param.put("access_token", access_token);
				log.info("queryJxinliAccessInfo param:"+param.toString());
				String accessStatusStr = HttpClientUtil.sendPost(appkey_url, param);
				Map<String,Object> accessStatusMap = JsonUtil.getMapFromJsonString(accessStatusStr);
				String code = Convert.toStr(accessStatusMap.get("code"));
				log.info("queryJxinliAccessInfo code "+code+" message:"+Convert.toStr(accessStatusMap.get("message")));
				if("30015".equals(code)){
					Object data = accessStatusMap.get("data");
					 String conInfo=JsonUtil.getJsonStringFromObject(data);
					 Map<String,Object> contractMap=JsonUtil.getMapFromJsonString(conInfo);
					List<Map<String,Object>> list = (List<Map<String,Object>>)contractMap.get("details");
					log.info("queryJxinliAccessInfo list:"+list);
					for(int i = 0 ; i<list.size();i++){
						Map<String,Object> detailMap =  list.get(i);
						String category = Convert.toStr(detailMap.get("category"));
						String websiteStatus = Convert.toStr(detailMap.get("websiteStatus"));
						String statusDescribe = Convert.toStr(detailMap.get("statusDescribe"));
						log.info("queryJxinliAccessInfo websiteStatus:"+websiteStatus);
						if("mobile".equals(category)&&"SUCCESS".equals(websiteStatus)){
							flag = 1;
							return flag;
						}else if("mobile".equals(category)&&"PENDING".equals(websiteStatus)){
							flag = 3;
							return flag;
						}else if("mobile".equals(category)&&"FAILED".equals(websiteStatus)){
							log.info("queryJxinliAccessInfo 失败 statusDescribe:"+statusDescribe);
							flag = 2;
							return flag;
						}else{
							flag = 3;
						}
					}
				}else{
					flag = 2;
				}
			}
		}catch(Exception e){
			log.error("queryJxinliAccessInfo error!",e);
		}
		return flag;
	}
	//查询7102,7103报告
	@SuppressWarnings("unchecked")
	public boolean queryCompleteData(String userOnlyId,String access_token){
		boolean isComplete = false;
		try{
			log.info("userOnlyId:"+userOnlyId+"access_token:"+access_token);
			List<InterfaceAccessInfoWithBLOBs> InterfaceAccessInfoWithBLOBs = carrieroperatorService.queryUnRequestData(userOnlyId);
			String result = "";
			if(InterfaceAccessInfoWithBLOBs!=null&&InterfaceAccessInfoWithBLOBs.size()>0){
				for(int i=0;i<InterfaceAccessInfoWithBLOBs.size();i++){
					InterfaceAccessInfoWithBLOBs interblobs = InterfaceAccessInfoWithBLOBs.get(i);
					String requestInfo = new String(interblobs.getRequestInfo());
					Map<String,String> requestMap = JsonUtil.getMapFromJsonString(requestInfo);
					//根据用户基本信息获取用户报告数据
					String org_name = Convert.toStr(requestMap.get("org_name"));
					String clientSecret = Convert.toStr(requestMap.get("client_secret"));
					String name = Convert.toStr(requestMap.get("name"));
					String idCard = Convert.toStr(requestMap.get("idCard"));
					String phone = Convert.toStr(requestMap.get("phone"));
					log.info("userOnlyId:"+userOnlyId+"queryCompleteData org_name"+org_name+"clientSecret"+clientSecret+"name"+name+"idCard"+DesUtil.encrypt(idCard)+"phone"+phone);
					Map<String,String> param = new HashMap<String, String>();
					param.put("userOnlyId", userOnlyId);
					param.put("phone", phone);
					param.put("idCard", idCard);
					param.put("name", name);
					param.put("client_secret", clientSecret);
					param.put("access_token", access_token);
					param.put("org_name", org_name);
					param.put("tranzCode", interblobs.getInterfaceType());
					//获得报告拉取状态
					log.info("userOnlyId:"+userOnlyId+"queryCompleteData queryReport param:"+param.toString());
					result = HttpClientUtil.sendPost(appkey_url, param);
					log.info("userOnlyId:"+interblobs.getUserOnlyId()+" result length:"+result.length());
					String status=null;
					byte[] responseInfo=null;
					try{
						Map<String,Object> resultMap = JsonUtil.getMapFromJsonString(result);
						status=Convert.toStr(resultMap.get("status"));
						log.info("queryCompleteData status:"+status);
						responseInfo=JSONObject.fromObject(resultMap).toString().getBytes("UTF-8");
					}catch(Exception e){
						status=UhjConstant.interfaceStutas.faild;
						responseInfo=result.getBytes();
					}
					InterfaceAccessInfoWithBLOBs interfaceAccessBlob = new InterfaceAccessInfoWithBLOBs();
					log.info("queryCompleteData params tranzCode:"+interblobs.getInterfaceType()+"userOnlyId"+userOnlyId);
					Map<String,Object> creditMap = carrieroperatorService.queryCreditApplyInfo(userOnlyId);
					String appId = Convert.toStr(creditMap.get("appId"));
					interfaceAccessBlob.setAppId(appId);
					interfaceAccessBlob.setUserOnlyId(userOnlyId);
					interfaceAccessBlob.setType("");
					interfaceAccessBlob.setInterfaceType(interblobs.getInterfaceType());
//					interfaceAccessBlob.setRequestInfo(JSONObject.fromObject(paramMap).toString().getBytes("UTF-8"));
					interfaceAccessBlob.setResponseInfo(responseInfo);
					interfaceAccessBlob.setCreateTime(DateUtil.currTimeStr());
					interfaceAccessBlob.setStatus(status);
					log.info("queryCompleteData status:"+status);
					interfaceAccessInfoService.saveInterfaceData(interfaceAccessBlob);
				}
				//判断是否拉取到7102,7103报告
				isComplete = interfaceAccessInfoService.queryInterfaceStatus(userOnlyId);
			}
		}catch(Exception e){
			log.error("queryCompleteData error!",e);
		}
		return isComplete;
	}
	//开启工作流
	public boolean judgeInitWorkFlow(boolean isComplete,String userOnlyId){
		try{
			if(isComplete){
				Map<String,Object> para = new HashMap<String, Object>();
				para.put("userOnlyId", userOnlyId);
				para.put("certType", UhjConstant.certType.idcard);
				Map<String,Object> customerMap = customerInfoService.queryCustomerInfo(para);
				log.info("修改状态，开始工作流。。。。。userOnlyId:"+userOnlyId);
				carrieroperatorService.updateApplyStatus(userOnlyId,"1");
				initWorkFlow(userOnlyId,Convert.toStr(customerMap.get("name")));
				log.info("开始工作流。。。。。userOnlyId:"+userOnlyId);
				return true;
			}
		}catch(Exception e){
			log.error("initWorkFlow error!",e);
		}
		return false;
	}
	
	public void saveContactPdf(HttpServletRequest request,String userOnlyId){
		log.info("saveContactPdf userOnlyId:"+userOnlyId);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userOnlyId", userOnlyId);
		//保存邮乐快捷支付协议和掌柜贷咨询服务协议
		for(int i =2;i <= 3 ; i++){
			params.put("flag", i);
			pdfController.contractPdfUpload(request, params);
		}
		//保存个人信息查询及留存授权书
//		params.clear();
//		params.put("userOnlyId", userOnlyId);
//		params.put("bussinessType", "limit");
//		pdfController.saveContact(params);
		log.info("saveContactPdf end....");
	}
	
	
	/**
	 * 保存接口返回数据
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveInterfaceAccessInfo")
	@ResponseBody
	public void saveInterfaceAccessInfo(HttpServletRequest request){
		try{
			InterfaceAccessInfoWithBLOBs interfaceAccessBlob = new InterfaceAccessInfoWithBLOBs();
			log.info("saveInterfaceAccessInfo begin....");
			//处理返回数据
			InputStream  io = request.getInputStream();
			StringBuilder sb = new StringBuilder();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(io, Charset.forName("UTF-8")));
			  int cp;
			  while ((cp = rd.read()) != -1) {
			     sb.append((char) cp);
			  }
		    } finally {
		      io.close();
		    }
		    String aa = URLDecoder.decode(sb.toString());
//		    log.info("saveInterfaceAccessInfo URL:"+aa);
		    String[] bb = aa.split("&");
		    Map<String,String> param = new HashMap<String, String>();//参数
		    if(bb!=null && bb.length>0){
		    	 for(String temp:bb){
//			    	log.info("temp:"+temp);
		    		 try{
		    			 param.put(temp.substring(0,temp.indexOf("=")),temp.substring(temp.indexOf("=")+1,temp.length()));
		    		 }catch(Exception e){
		    			 continue;
		    		 }
//		    		 if(! (temp.indexOf("=")>=temp.length()) ){
//		    			 param.put(temp.substring(0,temp.indexOf("=")),temp.substring(temp.indexOf("=")+1,temp.length()));
//		    		 }
			    }
		    }
		    log.info("saveInterfaceAccessInfo substring end userOnlyId:"+Convert.toStr(param.get("userOnlyId")));
			String result = Convert.toStr(param.get("result"));
			String paras = Convert.toStr(param.get("params"));
			String tranzCode = Convert.toStr(param.get("tranzCode"));
			String userOnlyId = Convert.toStr(param.get("userOnlyId"));
			String prdCode = Convert.toStr(param.get("prdCode"));
			log.info("saveInterfaceAccessInfo tranzCode:"+tranzCode+"userOnlyId"+userOnlyId);
			Map<String,Object> paramMap = JsonUtil.getMapFromJsonString(paras);
			String status=null;
			byte[] responseInfo=null;
			try{
				if(result!=null&&!"".equals(result)){
					result = result.replaceAll("######***", "&");
				}
				Map<String,Object> resultMap = JsonUtil.getMapFromJsonString(result);
				status=Convert.toStr(resultMap.get("status"));
				responseInfo=JSONObject.fromObject(resultMap).toString().getBytes("UTF-8");
			}catch(Exception e){
				status=UhjConstant.interfaceStutas.faild;
				responseInfo=result.getBytes();
			}
			if(!Check.isBlank(userOnlyId)){
				log.info("saveInterfaceAccessInfo params tranzCode:"+tranzCode+"userOnlyId"+userOnlyId);
				Map<String,Object> creditMap = carrieroperatorService.queryCreditApplyInfo(userOnlyId);
				String appId = Convert.toStr(creditMap.get("appId"));
				interfaceAccessBlob.setAppId(appId);
				interfaceAccessBlob.setUserOnlyId(userOnlyId);
				interfaceAccessBlob.setType(prdCode);
				interfaceAccessBlob.setInterfaceType(tranzCode);
				interfaceAccessBlob.setRequestInfo(JSONObject.fromObject(paramMap).toString().getBytes("UTF-8"));
				interfaceAccessBlob.setResponseInfo(responseInfo);
				interfaceAccessBlob.setCreateTime(DateUtil.currTimeStr());
				if(!Check.isBlank(status)
						&&(tranzCode.equals(UhjConstant.transCode.RAW_DATA)
						||tranzCode.equals(UhjConstant.transCode.REPORT_DATA)
						||tranzCode.equals(UhjConstant.transCode.BUSSINESS_RAW_DATA)
						||tranzCode.equals(UhjConstant.transCode.QUERY_REPORT))){
					interfaceAccessBlob.setStatus(status);
				}else if(tranzCode.equals(UhjConstant.transCode.RAW_DATA)
					||tranzCode.equals(UhjConstant.transCode.REPORT_DATA)
					||tranzCode.equals(UhjConstant.transCode.BUSSINESS_RAW_DATA)
					||tranzCode.equals(UhjConstant.transCode.QUERY_REPORT)){
					interfaceAccessBlob.setStatus(UhjConstant.interfaceStutas.faild);
				}else{
					interfaceAccessBlob.setStatus(UhjConstant.interfaceStutas.success);
				}
				log.info("saveInterfaceAccessInfo userOnlyId:"+userOnlyId+" result:"+result.length()+" status:"+interfaceAccessBlob.getStatus());
				interfaceAccessInfoService.saveInterfaceData(interfaceAccessBlob);
			}
		}catch(Exception e){
			log.error("saveInterfaceAccessInfo error!",e);
		}
	}
	
	@RequestMapping("/pdfUpload")
	@ResponseBody
	public String pdfUpload(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		String userOnlyId = Convert.toStr(request.getParameter("userOnlyId"));
		String flag = Convert.toStr(request.getParameter("flag"));
		map.put("userOnlyId", userOnlyId);
		map.put("flag", flag);
		if("4".equals(flag)){
			map.put("applyAmount", "10000");
			map.put("periods", "3");
			map.put("loanRemark", "3");
			map.put("repayType", "2");
		}
		return pdfController.contractPdfUpload(request, map);
	}
	
	
    /*
    1、移动号段有134,135,136,137, 138,139,147,150,151, 152,157,158,159,178,182,183,184,187,188。
    2、联通号段有130，131，132，155，156，185，186，145，176。
    3、电信号段有133，153，177.173，180，181，189。
    */	
	private String matchNum(String mobPhnNum) {
	    String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[23478]{1})|([4]{1}[7]{1})|([7]{1}[8]{1}))[0-9]{8}$";
	    String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1})|([4]{1}[5]{1})|([7]{1}[6]{1}))[0-9]{8}$";
	    String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1})|([7]{1}[37]{1}))[0-9]{8}$";
		
        String rs="YD";
        // 判断手机号码是否是11位
        if (mobPhnNum.length() == 11) {
            // 判断手机号码是否符合中国移动的号码规则
            if (mobPhnNum.matches(YD)) {
            	rs="YD";
            }
            // 判断手机号码是否符合中国联通的号码规则
            else if (mobPhnNum.matches(LT)) {
            	rs="LT";
            }
            // 判断手机号码是否符合中国电信的号码规则
            else if (mobPhnNum.matches(DX)) {
            	rs="DX";
            }
        }
        return rs;
    }
	
	/**
	 * 前海接口调用
	 */
	@ResponseBody
	@RequestMapping("invokeQH") 
	public JSONPObject invokeQH(HttpServletRequest request,@RequestParam String jsoncallback) {
		Map<String, Object> map = new HashMap<String, Object>();
		//判断是否有前海征信
		boolean b = false;
//		String userOnlyId="5555555550";
//		try {
//			userOnlyId = getUserOnlyId(request);
//		} catch (Exception e1) {
//		}
		String mobileOperatorsType = "";
		try{
			Map<String,Class<?>> paramMap = new HashMap<String, Class<?>>();
			paramMap.put("MobileOperatorsType",String.class);
			ZgdQueryClient client = WildflyBeanFactory.getZgdQueryClient();
			Map<String,Object> constantMap = client.queryZgdConstantValue(paramMap);
			if(constantMap!=null){
				mobileOperatorsType = ((List<String>)constantMap.get("MobileOperatorsType")).get(0);
			}
		}catch (Exception e) {
			log.error("invokeQH query mobileOperatorsType error!",e);
		}
		try {
			String userOnlyId=getUserOnlyId(request);;
			log.info("invokeQH userOnlyId:"+userOnlyId);
			//解析json确定是否通过验证
			if(!StringUtils.isEmpty(userOnlyId)) {
				if("PY".equals(mobileOperatorsType)){
					b = carrieroperatorService.queryAndParsePengyuanIntegerfaceAccessInfo(userOnlyId);
					log.info("鹏元解析结果："+b);
				}
				if(!b){
					b = carrieroperatorService.queryAndParseQHIntegerfaceAccessInfo1(userOnlyId);
					if(b){
						mobileOperatorsType = "QH";
					}
					log.info("前海征信解析结果："+b);
				}
				Map<String,Object> para = new HashMap<String, Object>();
				para.put("userOnlyId", userOnlyId);
				para.put("certType", UhjConstant.certType.idcard);
				Map<String,Object> customerMap = customerInfoService.queryCustomerInfo(para);
				log.info("修改状态，开始工作流。。。。。userOnlyId:"+userOnlyId);
				carrieroperatorService.updateApplyStatusNew(userOnlyId,mobileOperatorsType);
				initWorkFlow(userOnlyId,Convert.toStr(customerMap.get("name")));
				log.info("开始工作流。。。。。userOnlyId:"+userOnlyId);
			}
			map.put("code", "0000");
		} catch (Exception e) {
			log.error("queryBindMobileNo error!",e);
			map.put("code", "0000");
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(map));
	}
	
	
	public static void main(String[] args) {
		CarrierOperatorAuthController s = new CarrierOperatorAuthController();
		
		String userFlag=s.userFlagByCity("1","001");
		System.out.println(userFlag);
	}
}
