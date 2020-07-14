package com.ule.uhj.sldProxy.callbackRoutine;

import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 手机二要素及状态核验
 */
@Component
@Scope(value = "prototype")
public class Ti1306CallBackRoutine implements ThirdInterfaceCallBackRoutine {
    /** 接口返回数据 */
    private String result;

    @Override
    public Boolean isSuccess() {
    	JSONObject obj = JSONObject.fromObject(result);
    	String status = obj.getString("status");
        return "1".equals(status);
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
