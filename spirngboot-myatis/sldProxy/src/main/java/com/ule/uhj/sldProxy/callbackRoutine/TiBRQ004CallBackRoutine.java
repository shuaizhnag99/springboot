package com.ule.uhj.sldProxy.callbackRoutine;

import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 百融-自然人识别
 */
@Component
@Scope(value = "prototype")
public class TiBRQ004CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
    	JSONObject obj = JSONObject.fromObject(result);
		String response = obj.getString("responseCode");
		if("000000".equals(response)){
			JSONObject objs = obj.getJSONObject("responseMap");
			String status = objs.getString("code");
			return "600000".equals(status);
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
    
//    public static void main(String[] args) {
//		String results = "{\"message\":\"百融征信：查询成功!\",\"responseCode\":\"000000\",\"responseMap\":{\"product\":{\"sdCheckresult\":\"0\",\"xdCheckresult\":\"0\",\"checkCount2\":\"1\",\"ztCheckresult\":\"0\",\"item\":{\"caseTime\":\"[5,10)\",\"caseTypeCode\":\"6010000\"},\"costTime\":310,\"wfxwCheckresult\":\"1\"},\"swift_number\":\"3000379_20180918111103_3575\",\"flag\":{\"flag_badinfo\":1},\"code\":600000}}";
//		JSONObject obj = JSONObject.fromObject(results);
//		String response = obj.getString("responseCode");
//		if("000000".equals(response)){
//			JSONObject objs = obj.getJSONObject("responseMap");
//			String status = objs.getString("code");
//			System.out.println("600000".equals(status));
//		}
//	}
}
