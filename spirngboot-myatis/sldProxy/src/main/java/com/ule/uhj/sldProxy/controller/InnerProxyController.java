package com.ule.uhj.sldProxy.controller;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.SldService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhengXin on 2016/6/9.
 */
@Controller
@RequestMapping(value="/innerProxy")
public class InnerProxyController {
    private static Log log = LogFactory.getLog(InnerProxyController.class);
    @Resource(name="ZxSldService")
    private SldService zxSldService;
    @Resource(name="MerchantSldService")
    private SldService mtSldService;
    @Resource(name="BRSldService")
    private SldService brSldService;
    @Resource(name="TDSldService")
    private SldService tdSldService;
    @Resource(name="BDSldService")
    private SldService bdSldService;

    @RequestMapping("/doMTService")
    @ResponseBody
    public String doMTService(@RequestBody Request request){
        log.info("InnerProxyController类doMTService方法：开始 request = "+request);
        Response response = mtSldService.doService(request);
        log.info("InnerProxyController类doMTService方法：请求结束 response = "+response);
        try {
            String responseStr = new ObjectMapper().writeValueAsString(response);
            return responseStr;
        } catch (IOException e) {
            log.error("InnerProxyController类doMTService方法：异常 IOException",e);
            return null;
        }
    }

    @RequestMapping("/doZXService")
    @ResponseBody
    public String doZXService(@RequestBody Request request) {
    	 return null;
       /* log.info("InnerProxyController类doZXService方法：开始 request = " + request);
        if(request.getOpeartor()==null){
            log.warn("InnerProxyController类doZXService方法：不接收无操作记录的请求！");
            return "{\"message\":\"征信接口不接收无操作记录的请求！请在Request对象内setOpeartor!\"}";
        }
        try {
            log.info("InnerProxyController类doZXService方法：查询不到缓存，前往征信查询。");
            Response response = zxSldService.doService(request);
            if(response==null){
                throw new Exception("征信接口未返回有效值！");
            }
            log.info("InnerProxyController类doZXService方法：请求结束 response = " + response);
            String responseStr = new ObjectMapper().writeValueAsString(response);
            return responseStr;
        } catch (Exception e) {
            log.error("InnerProxyController类doZXService方法：查询征信异常 Exception", e);
            return null;
        }*/

        /*
        //先判断缓存内是否有对应数据
        List<Object> paramerList = request.getDataObjects();
        String transCode = request.getTransCode();
        TransZx transZx = transZxService.findTransZxInfo(transCode, paramerList);
        if (transZx != null) {//存在缓存，直接返回数据
            try {
                log.info("InnerProxyController类doZXService方法：查询到缓存！");
                Map<String, Object> responseMap = new HashMap<String, Object>();
                responseMap = new ObjectMapper().readValue(transZx.getContext(), Map.class);
                Response response = new Response();
                response.setResponseCode("00" + responseMap.get("code").toString());
                log.info("InnerProxyController类doZXService方法：ResponseCode " + response.getResponseCode());
                response.setMessage(responseMap.get("msg").toString());
                log.info("InnerProxyController类doZXService方法：ResponseMsg " + response.getMessage());
                if (responseMap.get("data") != null && !responseMap.get("data").toString().trim().equals("")) {
                    String dataStr = new ObjectMapper().writeValueAsString(responseMap.get("data"));
                    log.info("InnerProxyController类doZXService方法：ResponseData " + dataStr);
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("data", dataStr);
                    response.setResponseMap(resultMap);
                }
                log.info("InnerProxyController类doZXService方法：请求结束 response = " + response);
                String responseStr = new ObjectMapper().writeValueAsString(response);
                return responseStr;
            } catch (Exception e) {
                log.error("InnerProxyController类doZXService方法：查询缓存异常！" + e.getMessage(), e);
                return null;
            }
        } else {//无缓存的情况下，去征信查询数据
        }*/
    }

    @RequestMapping("/doBRService")
    @ResponseBody
    public String doBRService(@RequestBody Request request) {
        log.info("InnerProxyController类doBRService方法：开始 request = " + request);
        if (request.getOpeartor() == null) {
            log.warn("InnerProxyController类doBRService方法：不接收无操作记录的请求！");
            return "{\"message\":\"百融接口不接收无操作记录的请求！请在Request对象内setOpeartor!\"}";
        }
        try {
            Response response = brSldService.doService(request);
            if (response == null) {
                throw new Exception("百融接口未返回有效值！");
            }
            log.info("InnerProxyController类doBRService方法：请求结束 response = " + response);
            String responseStr = new ObjectMapper().writeValueAsString(response);
            return responseStr;
        } catch (Exception e) {
            log.error("InnerProxyController类doBRService方法：查询征信异常 Exception", e);
            return null;
        }
    }

    @RequestMapping("/doTDService")
    @ResponseBody
    public String doTDService(@RequestBody Request request){
        log.info("InnerProxyController类doTDService方法：开始 request = " + request);
        if (request.getOpeartor() == null) {
            log.warn("InnerProxyController类doTDService方法：不接收无操作记录的请求！");
            return "{\"message\":\"同盾接口不接收无操作记录的请求！请在Request对象内setOpeartor!\"}";
        }
        try {
            Response response = tdSldService.doService(request);
            if (response == null) {
                throw new Exception("同盾接口未返回有效值！");
            }
            log.info("InnerProxyController类doTDService方法：请求结束 response = " + response);
            String responseStr = new ObjectMapper().writeValueAsString(response);
            return responseStr;
        } catch (Exception e) {
            log.error("InnerProxyController类doTDService方法：未知异常 Exception", e);
            return null;
        }
    }
    
    @RequestMapping("/doBDService")
    @ResponseBody
    public String doBDService(@RequestBody Request request){
        log.info("InnerProxyController类doBDService方法：开始 request = " + request);
        if (request.getOpeartor() == null) {
            log.warn("InnerProxyController类doBDService方法：不接收无操作记录的请求！");
            return "{\"message\":\"百度接口不接收无操作记录的请求！请在Request对象内setOpeartor!\"}";
        }
        try {
            Response response = bdSldService.doService(request);
            if (response == null) {
                throw new Exception("百度接口未返回有效值！");
            }
            log.info("InnerProxyController类doBDService方法：请求结束 response = " + response);
            String responseStr = new ObjectMapper().writeValueAsString(response);
            return responseStr;
        } catch (Exception e) {
            log.error("InnerProxyController类doBDService方法：未知异常 Exception", e);
            return null;
        }
    }

}