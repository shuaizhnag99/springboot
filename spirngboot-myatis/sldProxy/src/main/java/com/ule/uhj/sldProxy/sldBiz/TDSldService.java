package com.ule.uhj.sldProxy.sldBiz;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.impl.DefaultSldService;
import com.ule.uhj.sld.constant.TDTransCodeEnum;
import com.ule.uhj.sld.constant.ZXTransConstant;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sld.util.DesUtil;
import com.ule.uhj.sldProxy.dao.SldOperateLogMapper;
import com.ule.uhj.sldProxy.service.InterfaceAccessInfoService;
import com.ule.uhj.sldProxy.sldBiz.BetaInterHelperService;
import com.ule.uhj.sldProxy.util.Convert;

/**
 * Created by yfzx-sh-zhengxin
 */
@Component("TDSldService")
public class TDSldService extends DefaultSldService{
    //接入组要求签证生成的参数前缀
    private static final String PARAMER_PRESIX = "param=";

    @Autowired
    SldOperateLogMapper sldOperateLogMapper;
    @Autowired
    BetaInterHelperService betaInterHelperService;
    @Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;

    @Override
    public Response doService(Request request){
        try {
        	log.info("TDSldService doService request="+request);
            String responseStr = "";
            //beta数据走挡板
            try{
//            	String betaResponse =betaInterHelperService.getBetaInterfaceResponse(request.getTransCode());
            	String betaResponse =betaInterHelperService.getBlockTextByCondition(request.getUserOnlyId(),request.getTransCode(),null,Convert.toStr(request.getDataMap().get("id_number")));
                if(!Check.isBlank(betaResponse)){
                	responseStr = betaResponse;
                	log.info("doService block response transCode="+request.getTransCode()+"responseStr="+responseStr);
                }
            }catch(Exception ex){
            	log.error("doService block error...",ex);
            }
            if(Check.isBlank(responseStr)) {
            	 responseStr = getResponseString(request);
            }
            
            if(StringUtils.isBlank(responseStr)){
                log.info("TDSldService类doService方法：同盾接口未返回有效值！");
                return null;
            }
            Map<String,Object> responseMap = new ObjectMapper().readValue(responseStr,Map.class) ;
            Response response = new Response();
            response.setResponseCode("000000");
            if(!responseMap.get("code").toString().equals("200")){
            	response.setResponseCode("00" + responseMap.get("code").toString());
            }
            response.setMessage(Convert.toStr(responseMap.get("msg"), ""));
            if(responseMap.get("data")!=null && !responseMap.get("data").toString().trim().equals("")){
                String dataStr = responseMap.get("data").toString().replace("\\", "");//new ObjectMapper().writeValueAsString(responseMap.get("data").toString().replace("\\", ""));
                dataStr = dataStr.replace("\\", "").replace("\\", "").replace("\\", "");
                Map<String,Object> resultMap = new HashMap<String, Object>();
                resultMap.put("data",dataStr);
                response.setResponseMap(resultMap);
            }
            interfaceAccessInfoService.saveInterfaceRecord(JSONObject.toJSONString(request), JSONObject.toJSONString(response),request.getUserOnlyId(),request.getTransCode(),"300");
            return response;
        } catch (Exception e) {
            log.info("TDSldService类doService方法 : 异常", e);
            return null;
        }
    }

    private String getResponseString(Request request){
        ObjectMapper objectMapper = new ObjectMapper();
        //本次请求所需的参数
        Map<String,Object> paramerMap = request.getDataMap();
        //appkey由接入组分配
        String appKey = ZXTransConstant.ZX_APP_KEY;
        //signkey由接入组分配
        String signKey = ZXTransConstant.ZX_SIGN_KEY;
        //本次请求参数的JSON形式
        String requestStr = null;
        //本次请求返回的结果
        String responseStr = null;
        
        String reportId="";
        for(Method method : CreditServiceTools.class.getMethods()){
            if(method.getName().equals(request.getRequestMethod())){
                method.setAccessible(true);
                Class targetBean;
                try{
                    targetBean = Class.forName(TDTransCodeEnum.get(request.getTransCode()).getRequestType());
                }catch (Exception e){
                    log.error("TDSldService:获取目标bean时反射异常。",e);
                    return null;
                }
                //使用JSON串传输的接口
                //if(targetBean.getSimpleName().equals(String.class.getSimpleName())){
            	if(String.class.isAssignableFrom(targetBean)) {
                    try{
                        requestStr = Convert.toStr(paramerMap.get("report_id"), "");
                        reportId=(String)paramerMap.get("report_id");
                    }catch (Exception e){
                        log.error("TDSldService:获取报告report_id异常"+paramerMap,e);
                        return null;
                    }
                    try{
                        responseStr = Convert.toStr(method.invoke(null, requestStr),"");
                        log.info("TDSldService:本次请求获得的响应结果responseStr="+responseStr);
                    }catch (InvocationTargetException te){
                        log.error("TDSldService:反射目标指定异常！method="+method.getName(),te);
                        return null;
                    }catch (IllegalAccessException ie){
                        log.error("TDSldService:指定的同盾接口方法无访问权限！method="+method.getName(),ie);
                        return null;
                    }catch (Exception e){
                        log.error("TDSldService:发生未知异常!",e);
                        return null;
                    }
                }else{
                    //使用实体Bean传输的接口
                    Object targetBeanInstance = null;
                    try{
                        Constructor constructor = targetBean.getConstructor();
                        targetBeanInstance = constructor.newInstance();
                    }catch (NoSuchMethodException ne){
                        log.error("TDSldService:指定的实体类未提供默认构造器！class="+targetBean.getName(),ne);
                        return null;
                    }catch(Exception e){
                        log.error("TDSldService:发生未知异常!",e);
                        return null;
                    }
                    for(String objKey : paramerMap.keySet()){
                        Object objValue = paramerMap.get(objKey);
                        String setMethodName = "set"+objKey.substring(0,1).toUpperCase()+objKey.substring(1, objKey.length());
                        try{
                            Method setMethod = targetBean.getDeclaredMethod(setMethodName, objValue.getClass());
                            setMethod.setAccessible(true);
                            setMethod.invoke(targetBeanInstance,objValue);
                            log.info("TDSldService:"+targetBean.getSimpleName()+"对象的"+objKey+"字段已注入"+"该字段的值为:"+objValue);
                        }catch (NoSuchMethodException e){
                            log.error("TDSldService:未找到指定的赋值方法:"+setMethodName+"，不尝试强制注入,objKey="+objKey,e);
                            continue;
                        }catch (Exception e){
                            log.error("TDSldService:发生未知异常！赋值方法:"+setMethodName+"，objKey="+objKey,e);
                            continue;
                        }
                    }

                    try{
                            responseStr = Convert.toStr(method.invoke(null, targetBeanInstance),"");
                        log.info("TDSldService:本次请求获得的响应结果responseStr="+responseStr);
                    }catch (InvocationTargetException te){
                        log.error("TDSldService:反射目标指定异常！method="+method.getName(),te);
                        return null;
                    }catch (IllegalAccessException ie){
                        log.error("TDSldService:指定的同盾接口方法无访问权限！method="+method.getName(),ie);
                        return null;
                    }catch (Exception e){
                        log.error("TDSldService:发生未知异常!",e);
                        return null;
                    }

                }
                break;
            }
        }
        return responseStr;
    }

//    public static void main(String[] args){
//        System.out.println(DesUtil.encrypt("33252619801109511X"));
//        SldService sldService = new DefaultSldService();
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("name","王媛");
//        map.put("id_number","411325198810162932");
//        map.put("mobile","18017376373");
//        map.put("loan_term",12);
//        map.put("report_id","ER2017030915274212113448");
//        Request request = sldService.getRequest(DefaultSldService.REQUEST_TYPE_TD,TDTransCodeEnum.TD_REPORT_QUERY.getTransCode(),map);
//        SldOperateLog sldOperateLog = new SldOperateLog();
//        request.setOpeartor(sldOperateLog);
//        sldService.doService(request);
//    }
}