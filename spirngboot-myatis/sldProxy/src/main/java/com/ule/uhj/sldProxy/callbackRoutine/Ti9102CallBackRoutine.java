package com.ule.uhj.sldProxy.callbackRoutine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;
import org.springframework.context.annotation.Scope;

/**
 * 前海征信-个人对外投资（好信高管通）
 */
@Component
@Scope(value = "prototype")
public class Ti9102CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
    	
		JSONObject js=JSONObject.fromObject(result);
		JSONObject entMgrInc=JSONObject.fromObject(js.get("entMgrInc"));
		JSONArray records=JSONArray.fromObject(entMgrInc.get("records"));
		Object[] strs = records.toArray(); //json转为数组 
	    for(Object object :strs){
	    	JSONObject objs=JSONObject.fromObject(object);
			if("E000000".equals(objs.get("erCode"))){
				return true;
			}	
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
