package com.ule.uhj.sldProxy.callbackRoutine;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 百度营业执照OCR
 */
@Component
@Scope(value = "prototype")
public class Ti8201CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
    	Map<String,Object> licenseData = (Map)JSON.parse(result);
    	if("success".equals(licenseData.get("status"))){
    		return true;
    	}
    	return false;
    }

    @Override
    public void init(String s) {
        this.result = s;
    }

    @Override
    public String secondOpinion() {
        return null;
    }

    @Override
    public String getSign() {
        return null;
    }
}
