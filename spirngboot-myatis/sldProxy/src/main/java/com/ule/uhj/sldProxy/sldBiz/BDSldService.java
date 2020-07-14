package com.ule.uhj.sldProxy.sldBiz;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ule.tools.creditService.bean.BDLocationSearch;
import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.uhj.sldProxy.util.CreditException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.impl.DefaultSldService;
import com.ule.uhj.sld.model.SldOperateLog;
import com.ule.uhj.sld.util.Convert;
import com.ule.uhj.sldProxy.dao.SldOperateLogMapper;
import com.ule.uhj.sldProxy.service.InterfaceAccessInfoService;
import com.ule.uhj.sldProxy.util.PropertiesHelper;

/**
 * 百度接口-内部对接服务
 */
@Component(value="BDSldService")
public class BDSldService  extends DefaultSldService {

    @Autowired
    SldOperateLogMapper sldOperateLogMapper;
    @Autowired
	private InterfaceAccessInfoService interfaceAccessInfoService;
    /***
     * 百度查询成功
     */
    public static  final String SUCCESS = "000000";

    /***
     * 经纬度未传入时的错误代码
     */
    public  static final String PARAMS_ERROR = "000001";
    /***
     * 百度查询失败
     */
    public static final String QUERY_ERROR = "900001";

	@Override
    public Response doService(Request request){
        log.info("百度查询：开始，request="+request.toString());
        //返回结果
        Response response = new Response();
        //传入参数
        Map<String,Object> dataMap = request.getDataMap();
        
        GetMethod method = null;
        String location = Convert.toStr(dataMap.get("location"));
        String query = Convert.toStr(dataMap.get("query"));
        String radius = Convert.toStr(dataMap.get("radius"));
        if(StringUtils.isBlank(location)&&StringUtils.isBlank(query)&&StringUtils.isBlank(radius)){
        	log.info("百度查询：经纬度、关键字或查询范围为空！无法查询！");
            response.setResponseCode(PARAMS_ERROR);
            response.setMessage("百度查询：经纬度、关键字或查询范围为空！无法查询！");
            return response;
        }
        try{
			BDLocationSearch bdLocationSearch = new BDLocationSearch();
			bdLocationSearch.setQuery(query);
			bdLocationSearch.setLocation(location);
			bdLocationSearch.setRadius(radius);
            bdLocationSearch.setPage_num("1");
            bdLocationSearch.setPage_size("1");

			String responseStr=CreditServiceTools.bdLocationSearch(bdLocationSearch);
            log.info("基础服务部返回的数据"+responseStr);
			Map<String, Object> object = (Map<String, Object>)JSONObject.parse(responseStr);
			if(object.get("code").equals("0000") && !object.get("data").equals("")){
                Map<String, Object> data=(Map)object.get("data");
				if((Integer)data.get("status")==0){
					response.setResponseCode(SUCCESS);
					response.setMessage("百度接口查询成功");
					response.setResponseMap(data);
				}else{
					throw new Exception("百度接口访问失败");
				}
			}else{
				throw new Exception("调用基础服务部，百度周边信息查询接口失败！");
			}
			saveOperateLog(request);
        }catch (HttpException e) {
			log.error("baidu interface access HttpException error", e);
			response.setResponseCode(QUERY_ERROR);
            response.setMessage("百度店铺周边信息查询失败！调用百度API时发生异常："+e);
		}catch (IOException e) {  
			log.error("queryTotalCountOfBaiduPlaceByKeyWordList IOException error", e);
			response.setResponseCode(QUERY_ERROR);
            response.setMessage("百度店铺周边信息查询失败！调用百度API时发生异常："+e);
		}catch (Exception e){
            log.error("百度：发起查询失败！", e);
            response.setResponseCode(QUERY_ERROR);
            response.setMessage("百度店铺周边信息查询失败！调用百度API时发生异常："+e);
        }
        
        interfaceAccessInfoService.saveInterfaceRecord(JSONObject.toJSONString(request), JSONObject.toJSONString(response),request.getUserOnlyId(),request.getTransCode(),"300");
        return response;
    }
	
	private void saveOperateLog(Request request){
        log.info("BRSldService类updateTransBrInfo方法 ：开始。");
        try{
            SldOperateLog sldOperateLog = request.getOpeartor();
            sldOperateLog.setOperationContent("调用百度接口"+request.getTransCode());
            sldOperateLogMapper.insert(sldOperateLog);
            log.info("BDSldService类saveOperateLog方法：插入操作记录成功！");
        }catch (Exception e){
            log.error("BDSldService类saveOperateLog方法：插入操作记录时异常！"+e.getMessage(),e);
        }
    }

}