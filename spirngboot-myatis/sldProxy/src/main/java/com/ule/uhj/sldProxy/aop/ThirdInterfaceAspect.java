package com.ule.uhj.sldProxy.aop;

import com.alibaba.fastjson.JSONObject;
import com.ule.oracle.dao.ThirdInterfaceCostMapper;
import com.ule.oracle.model.ThirdInterfaceCost;
import com.ule.uhj.provider.common.CommonTranzCode;
import com.ule.uhj.provider.common.RequestJsonUtil;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.constant.InterfaceChargeEnum;
import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;
import com.ule.uhj.sldProxy.util.ClassUtil;
import com.ule.uhj.sldProxy.util.Convert;
import com.ule.uhj.sldProxy.util.JsonResult;
import com.ule.uhj.sldProxy.util.SpringContextHookUtil;
import com.ule.uhj.util.ProxyDateUtil;
import com.ule.uhj.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Component
@Aspect
public class ThirdInterfaceAspect {
    private static Log log = LogFactory.getLog(ThirdInterfaceAspect.class);

    private static List<String> callBackRoutineList;

    static {
        String packageName = "com.ule.uhj.sldProxy.callbackRoutine";
        callBackRoutineList = ClassUtil.getClassNameByPackage(packageName);
        log.info("callBackRoutintList:" + callBackRoutineList);
    }

    @Autowired
    private ThirdInterfaceCostMapper thirdInterfaceCostMapper;

    @Autowired
    private SpringContextHookUtil springContextHookUtil;

    @Pointcut("execution(* com.ule.uhj.sldProxy.controller.CommonController.commonTranz(..))")
    public void commonTranzPointcut() {
    }

    @Pointcut("execution(* com.ule.uhj.sldProxy.controller.InnerProxyController.do*Service(..))")
    public void innerProxyPointcut(){
    }

    @Around(value = "innerProxyPointcut()")
    public Object innerProxyAround(ProceedingJoinPoint pjp) throws Throwable{
        log.info("innerProxy hook success.");
        Request request = null;
        ThirdInterfaceCallBackRoutine callBackRoutine = null;
        Object tiResultObj = null;
        Throwable throwable = null;

        try{
            request = (Request) pjp.getArgs()[0];
        }catch (Exception e){
            log.error("innerProxyHook : request format error!",e);
            return null;
        }
        try{
            tiResultObj = pjp.proceed(pjp.getArgs());
            log.info("innerProxyHook : origin function call success. tiResultObj：" + tiResultObj);
        }catch (Throwable t){
            throwable = t;
            if(throwable!=null){
                throw throwable;
            }
        }
        
        String userOnlyId = null;
        String transCode = null;
        String chargeSource = null;
        try {
            userOnlyId = request.getUserOnlyId();
            transCode = request.getTransCode();
            chargeSource = request.getChargeSource();
            if((callBackRoutine = getCallBackRoutine(transCode)) != null) {
                CallBackResult callBackResult = getCallBackResult(callBackRoutine,tiResultObj);
                log.info(String.format("userOnlyId：%s，transCode：%s，chargeSource：%s，callBackResult：%s", userOnlyId, transCode, chargeSource, callBackResult));
                this.saveThirdInterfaceCost(callBackResult, transCode, request.getUserOnlyId(), request.getChargeSource());
                log.info(String.format("收费记录保存成功，userOnlyId：%s，transCode：%s，chargeSource：%s", userOnlyId, transCode, chargeSource));
            }
        } catch (Exception e) {
            log.error(String.format("收费记录保存失败，userOnlyId：%s，transCode：%s，chargeSource：%s", userOnlyId, transCode, chargeSource), e);
        }
        return tiResultObj;
    }

    //@Around(value = "commonTranzPointcut()")
    public Object commonTranzPointcut(ProceedingJoinPoint pjp) throws Throwable{

        log.info("commonTranz hook success.");
        ServletRequestAttributes servletRequestAttributes = null;
        HttpServletRequest request = null;
        ThirdInterfaceCallBackRoutine callBackRoutine = null;
        Object tiResultObj = null;
        Throwable throwable = null;
        try{
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = servletRequestAttributes.getRequest();
            if(request==null){
                log.error("HttpServletRequest is null.");
                throw new Exception("HttpServletRequest is null.");
            }
        }catch (Exception e){
            log.error("commonTranzHook 该方法无法获取ServletRequestAttributes，可能非web接口。");
            return null;
        }

        Map<String,Object> paras = null;
        try {
            //和commontranz.do接口解析方法保持一致
            paras = new HashMap<String, Object>();
            if(request.getContentType().contains("form-urlencoded")) {
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
            } else {
                paras = RequestJsonUtil.getRequestMap(request);
                if(paras == null || paras.size()<=0) {
                    throw new Exception("json数据未接收!");
                }
            }
        } catch (Exception e) {
            log.error("参数解析异常", e);
            throw new RuntimeException(e);
        }
        
        CommonTranzCode ctc = CommonTranzCode.getTranzCode(Integer.parseInt(String.valueOf(paras.get("tranzCode"))));
        if(ctc==null){
            return JsonResult.getInstance().add("returnCode",  "9999").add("returnMessage",  "didn't find tranzCode:" + String.valueOf(paras.containsKey("tranzCode")) ).toString();
        }
        try{
            tiResultObj = pjp.proceed(pjp.getArgs());
            log.info("commonTranzHook : origin function call success.tiResultObj：" + tiResultObj);
        }catch (Throwable t){
            throwable = t;
            if(throwable!=null){
                throw throwable;
            }
        } 
        
        try {
            if (StringUtils.isBlank(Convert.toStr(tiResultObj, ""))) {
                return tiResultObj;
            }
            JSONObject jsonObject = JSONObject.parseObject(Convert.toStr(tiResultObj,""));
            String returnCode = jsonObject.getString("returnCode");
            if (StringUtils.equals(returnCode, "9999")) {
                return tiResultObj;
            }
        } catch (Exception e) {
        }
        String transCode = null;
        String userOnlyId = null;
        String chargeSource = null;
        try {
            transCode = ctc.getTranzCode().toString();
            if((callBackRoutine = getCallBackRoutine(transCode)) != null) {
                userOnlyId = Convert.toStr(paras.get("userOnlyId"));
                chargeSource = Convert.toStr(paras.get("chargeSource"));
                CallBackResult callBackResult = getCallBackResult(callBackRoutine,tiResultObj);
                log.info(String.format("userOnlyId：%s，transCode：%s，chargeSource：%s，callBackResult：%s", userOnlyId, transCode, chargeSource, callBackResult));
                this.saveThirdInterfaceCost(callBackResult, transCode, userOnlyId, chargeSource);
                log.info(String.format("收费记录保存成功，userOnlyId：%s，transCode：%s，chargeSource：%s", userOnlyId, transCode, chargeSource));
            }
        } catch (Exception e) {
            log.error(String.format("收费记录保存失败，userOnlyId：%s，transCode：%s，chargeSource：%s", userOnlyId, transCode, chargeSource), e);
        }
        return tiResultObj;
    }

    private ThirdInterfaceCallBackRoutine getCallBackRoutine(String transCode){
        String className = "Ti" + transCode + "CallBackRoutine";
        if (callBackRoutineList.contains(className)) {
            Object targetBean = springContextHookUtil.getTargetBean("ti" + transCode + "CallBackRoutine");
            if (targetBean != null && targetBean instanceof ThirdInterfaceCallBackRoutine) {
                return (ThirdInterfaceCallBackRoutine) targetBean;
            }
        }
        return null;
    }

    private CallBackResult getCallBackResult(ThirdInterfaceCallBackRoutine callBackRoutine, Object tiResultObj) {
        CallBackResult callBackResult = new CallBackResult();
        String resultString = Convert.toStr(tiResultObj, "");
        try {
            callBackRoutine.init(resultString);
            callBackResult.setSuccess(callBackRoutine.isSuccess());
            callBackResult.setSecondOpinion(callBackRoutine.secondOpinion());
            callBackResult.setSign(callBackRoutine.getSign());
        } catch (Exception e) {
            log.error("getCallBackResult error!", e);
        }
        return callBackResult;
    }

    private void saveThirdInterfaceCost(CallBackResult result, String transCode, String userOnlyId, String chargeSource) {
        ThirdInterfaceCost tic = new ThirdInterfaceCost();
        InterfaceChargeEnum e = InterfaceChargeEnum.getByTransCode(transCode);
        if (StringUtils.equals(e.getChargeDetail(), InterfaceChargeEnum.BAIDU_ORC.getChargeDetail())) {
            String key = "hj_baidu_yingye_zhizhao_ocr_count";
            String value = RedisUtils.get(key);
            Integer count = value == null ? 0 : Integer.valueOf(value);
            count++;
            if (count <= 200) {
                RedisUtils.set(key, String.valueOf(count), ProxyDateUtil.getSecondsTomorrow());
                result.setSuccess(false);
                tic.setRemark("百度营业执照OCR每天前200次免费，当前第" + count + "次");
            }
        }
        tic.setUserOnlyId(userOnlyId);
        tic.setChargeSource(chargeSource);
        tic.setChargeDetail(e.getChargeDetail());
        if (result.isSuccess()) {
            String unitPrice = thirdInterfaceCostMapper.selectUnitPriceByElementKey(e.getChargeDetail());
            if (StringUtils.isNotBlank(unitPrice)) {
                tic.setAmount(new BigDecimal(unitPrice));
            } else {
                tic.setAmount(new BigDecimal("0"));
                tic.setRemark("调用成功，但单价查询失败，检查字典表配置");
            }
        } else {
            tic.setAmount(new BigDecimal("0"));
        }
        tic.setCreateBy(userOnlyId);
        tic.setUpdateBy(userOnlyId);
        thirdInterfaceCostMapper.insertSelective(tic);
    }

    class CallBackResult {
        private String secondOpinion;
        private Boolean success;
        private String sign;

        public String getSecondOpinion() {
            return secondOpinion;
        }

        public void setSecondOpinion(String secondOpinion) {
            this.secondOpinion = secondOpinion;
        }

        public Boolean isSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
