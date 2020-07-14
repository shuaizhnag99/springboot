package com.ule.uhj.sldProxy.callbackRoutine;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;
import com.ule.uhj.sld.util.Check;
import com.ule.uhj.sldProxy.util.Convert;

/**
 * 联网核查
 */
@Component
@Scope(value = "prototype")
public class Ti3100CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
    	try{
    		JSONObject data =JSONObject.fromObject(result);
    		JSONObject verify_result =JSONObject.fromObject(data.get("verify_result"));
    		String rtn_verify=Convert.toStr(verify_result.get("rtn"));
    		if(Check.isBlank(rtn_verify)){
    			return false;
    		}
    		return true;
    	}catch(Exception e){
    		return false;
    	}
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
