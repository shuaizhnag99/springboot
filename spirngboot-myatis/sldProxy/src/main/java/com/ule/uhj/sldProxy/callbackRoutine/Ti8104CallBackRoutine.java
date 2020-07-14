package com.ule.uhj.sldProxy.callbackRoutine;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ule.uhj.sld.service.ThirdInterfaceCallBackRoutine;

/**
 * 百度身份认证（公安内部照片对比）
 */
@Component
@Scope(value = "prototype")
public class Ti8104CallBackRoutine implements ThirdInterfaceCallBackRoutine {

    private String result;

    @Override
    public Boolean isSuccess() {
    	List<String> changeCodeList = Arrays.asList("0","222350","222351","222354","222355");
    	JSONObject obj = JSONObject.fromObject(result);
    	String response = obj.getString("error_code");
		if(changeCodeList.contains(response)){
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
    
//    public static void main(String[] args) {
//    	Ti8104CallBackRoutine ti8104CallBackRoutine = new Ti8104CallBackRoutine();
//		String results = "{\"timestamp\":1541151705,\"result\":{\"score\":90},\"cached\":0,\"error_code\":222350,\"log_id\":7584899494001,\"error_msg\":\"SUCCESS\"}";
//		ti8104CallBackRoutine.init(results);
//		System.out.println(ti8104CallBackRoutine.isSuccess());
		
//	}
}
