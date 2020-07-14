package com.ule.uhj.sldProxy.sldBiz;

import org.springframework.stereotype.Component;

import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.impl.DefaultSldService;

/**
 * Created by ZhengXin on 2016/6/9.
 */
@Component(value="MerchantSldService")
public class MerchantSldService extends DefaultSldService{
    @Override
    public Response doService(Request request){
        log.info("项目迁移 商乐贷暂停 MerchantSldService类doService方法 开始:request = "+request);
      //项目迁移 商乐贷暂停
//        MerchantBaseTools mbt = MerchantBaseTools.getInstance();
//        Map<String,Object> resultMap;
//        try {
//            if(request.getRequestMethod().equals("TT")){
//                log.info("MerchantSldService类doService方法:本次请求为TT查询.");
//                Response response = doTTService(request);
//                log.info("MerchantSldService类doService方法:TT查询结果="+response);
//                return response;
//            }
//            Method getEjbClientMethod = mbt.getClass().getDeclaredMethod("getEjbClient", String.class, String.class);
//            getEjbClientMethod.setAccessible(true);
//            String[] requestMethods = request.getRequestMethod().split(",");
//            log.info("MerchantSldService类doService方法 获取指定接口方法,Server="+requestMethods[0]+",Bean="+requestMethods[1]);
//            Object client = getEjbClientMethod.invoke(mbt, requestMethods[0], requestMethods[1]);
//            log.info("MerchantSldService类doService方法 获取方法成功！");
//            Method[] clientMethods = client.getClass().getMethods();
//            for(Method method : clientMethods){
//                if(method.getName().equals(requestMethods[2])){
//                    log.info("MerchantSldService类doService方法 已找到指定方法 ："+requestMethods[2]);
//                    method.setAccessible(true);
//                    Object[] paramers = new Object[request.getDataObjects().size()];
//                    int index = 0;
//                    for(Object obj : request.getDataObjects()){
//                        if(obj instanceof Integer){
//                            obj = Long.parseLong(obj.toString());
//                        }
//                        paramers[index] = obj;
//                        index++;
//                    }
//                    Object resultObj = method.invoke(client, paramers);
//                    if(resultObj == null){
//                        Response response = new Response();
//                        response.setResponseMap(null);
//                        response.setResponseCode("000002");
//                        response.setMessage("商家组接口未返回有效值!");
//                        log.info("MerchantSldService类doService方法 商家组接口未返回有效值!");
//                        return response;
//                    }
//                    ObjectMapper mapper = new ObjectMapper();
//                    resultMap = new HashMap<String, Object>();
//                    resultMap.put("data",mapper.writeValueAsString(resultObj));
//                    Response response = new Response();
//                    response.setResponseMap(resultMap);
//                    response.setResponseCode("000000");
//                    response.setMessage("SUCCESS");
//                    log.info("MerchantSldService类doService方法 商家组接口返回值 = " + resultMap.toString());
//                    return response;
//                }
//            }
//            log.info("MerchantSldService类doService方法 未找到指定方法 ："+requestMethods[2]);
//            Response response = new Response();
//            response.setResponseCode("000001");
//            response.setMessage("未找到指定方法！");
//            return response;
//        } catch (Exception e) {
//            Response response = new Response();
//            response.setResponseCode("999999");
//            response.setMessage("系统未知异常!");
//            log.info(e.getMessage(), e);
//            return response;
//        }
        return null;
    }

//    private Response doTTService(Request request) throws IOException {
//        List<Object> paramer = request.getDataObjects();
//        Response response = new Response();
//        if(paramer==null || paramer.size()==0 ||paramer.size()>1){
//            response.setResponseCode("000003");
//            response.setMessage("TT查询参数不可为空！");
//        }else{
//            Object result = CacheUtils.getTTClient().get(paramer.get(0).toString());
//            if(result==null){
//                response.setResponseCode("000002");
//                response.setMessage("TT未查询到指定的key，key="+paramer.get(0).toString());
//            }else{
//                response.setMessage("SUCCESS");
//                response.setResponseCode("000000");
//                Map<String,Object> resultMap = new HashMap<String, Object>();
//                resultMap.put("data",new ObjectMapper().writeValueAsString(result));
//                response.setResponseMap(resultMap);
//            }
//        }
//        return response;
//    }
}
