package com.ule.uhj.sldProxy.callbackRoutine;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;
import org.springframework.context.annotation.Scope;

/**
 * 企查查精确查询
 */
@Component
@Scope(value = "prototype")
public class Ti4103CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
    	JSONObject obj = JSONObject.fromObject(result);
    	String status = obj.getString("Status");
        return "200".equals(status);
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
