package com.ule.uhj.sldProxy.callbackRoutine;

import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 同盾-贷前申请审核提交
 */
@Component
@Scope(value = "prototype")
public class TiTDQ001CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
    	JSONObject obj = JSONObject.fromObject(result);
		String response = obj.getString("responseCode");
		if("000000".equals(response)){
			JSONObject objs = obj.getJSONObject("responseMap");
            String dataStr = objs.getString("data");
            JSONObject dataObj = JSONObject.fromObject(dataStr);
			return dataObj.getBoolean("success");
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
//		String results = "{\"message\":\"\",\"responseCode\":\"000000\",\"responseMap\":{\"data\":{\"report_id\":\"ER20180917111033E55D6BA9\",\"success\":false}}}";
//		JSONObject obj = JSONObject.fromObject(results);
//		String response = obj.getString("responseCode");
//		if("000000".equals(response)){
//			JSONObject objs = obj.getJSONObject("responseMap");
//			JSONObject data = objs.getJSONObject("data");
//			System.out.println(data.getBoolean("success"));
//		}
//	}
}
