package com.ule.uhj.api.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ule.wildfly.entity.Cluster;
import com.ule.wildfly.util.ServerProviderUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ule.uhj.dto.YC.Request;
import com.ule.uhj.dto.YC.Response;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.ycZgd.YCRequestClient;
import com.ule.uhj.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/service")
public class NothiceUleResource{
	private static Log log = LogFactory.getLog(NothiceUleResource.class);
	
	/***
	 * 额度申请进度通知交易码
	 */
	public static final String TRANSCODE_YCNOTICEULE_APPLY = "320001";
	
	/***
	 * 贷款申请进度通知交易码
	 */
	public static final String TRANSCODE_YCNOTICEULE_LOAN = "320002";

	private static Cluster cluster;
	static {
		try{
			cluster = ServerProviderUtils.getClusterInfoFromConsul(WildflyBeanFactory.moduleName,WildflyBeanFactory.appName);
		}catch (Exception e){
		}
	}
	
	/**
	 * @param dataMap
	 * in 		
{
	"data": {
		"loanId": "贷款ID  必输	放款成功时返回值",
		"loanProgress": "贷款当前进度  必输	1-申请成功",
		"loanContractNo": "贷款合同编号  必输	贷款放款时必返回",
		"duebillNo":"贷款借据编号  必输	贷款放款时必返回",
		"lendAmount":"放款金额  必输	放款成功时必返回",
		"lendDate":"放款日期  必输	放款成功时必返回"
	},
	"head": {
		"senderDate": "20151230",
		"tranCode": "320001",
		"flowno": "00000001",
		"senderTime": "151010"
	}
}

out : 
{
	"code":"000000",
	"message":"交易成功",
	"object": {
		"lineId": "f7c99a3860534a778976c319d730f943"
	}
}
	 * @return
	 */
	@RequestMapping("/limitNotice")
	@ResponseBody
	public Map<String, Object> limitNotice(@RequestParam Map<String, String> dataMap) {
		log.info("in limitNotice");
		String dataStr = dataMap.get("data");
		String headStr = dataMap.get("head");
		Map<String,Object> headMap = JsonUtil.getMapFromJsonString(headStr);
		Map<String,Object> requestDataMap = JsonUtil.getMapFromJsonString(dataStr);
		//交易代码，根据此判断执行业务
		String transCode = headMap.get("tranCode").toString();
		Map<String,Object> obj = new HashMap<String, Object>();
		try {
			YCRequestClient client = WildflyBeanFactory.getYCRequestClient();
//					UhjClientFactory.getInstance().getHJClient(YCRequestClient.class);
					//UhjClientFactoryUrl.getInstance("jnp://127.0.0.1:1099").getHJClient(YCRequestClient.class);
			Response response;
			Request request = client.getRequest(transCode,YCRequestClient.OUT);
			request.setEjbUrl(cluster.getProviderUrls());
			request.setEjbVersion(cluster.getVersion());
			request.setDataMap(requestDataMap);
			request.setMoudleName(WildflyBeanFactory.moduleName);
			request.setAppName(WildflyBeanFactory.appName);
			log.info("YCRequestClient request = "+request);
			response = client.revicedRequest(request);
			log.info("YCRequestClient response = "+response);
			obj.put("code", response.getCode());
			obj.put("message", response.getMessage());
			obj.put("object", response.getData());
		} catch (Exception e) {
			log.error("limitNotice error",e);
		}
		return obj;
	}
	
	private Object parseObj(Object obj){
		if(obj != null){
			if(obj instanceof Map){
				Map map = new HashMap();
				for(Object obj1 : ((Map)obj).keySet()){
					map.put(obj1, parseObj( ((Map)obj).get(obj1) ));
				}
				return map;
			}else if(obj instanceof List){
				List list = new ArrayList();
				for(Object obj1 : (List)obj){
					list.add(parseObj(obj1));
				}
				return list;
			}else{
				return obj;
			}
		}
		return null;
	}

			
}
