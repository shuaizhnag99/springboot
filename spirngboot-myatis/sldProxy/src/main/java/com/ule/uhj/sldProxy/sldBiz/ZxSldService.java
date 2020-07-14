package com.ule.uhj.sldProxy.sldBiz;

import com.ule.tools.creditService.client.CreditServiceTools;
import com.ule.tools.sign.SignEnc;
import com.ule.uhj.sld.biz.dto.Request;
import com.ule.uhj.sld.biz.dto.Response;
import com.ule.uhj.sld.biz.service.impl.DefaultSldService;
import com.ule.uhj.sld.constant.ZXTransConstant;
import com.ule.uhj.sld.model.SldOperateLog;
import com.ule.uhj.sld.util.Base64TImgUtil;
import com.ule.uhj.sldProxy.dao.SldOperateLogMapper;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhengXin on 2016/6/8.
 */
@Component("ZxSldService")
public class ZxSldService extends DefaultSldService {
    @Autowired
    SldOperateLogMapper sldOperateLogMapper;

    @Override
    public Response doService(Request request){
        log.info("ZxSldService类doService方法 : 开始,传入参数 = "+request.toString());
        try {
            String responseStr = getResponseString(request);
            if(StringUtils.isBlank(responseStr)){
                log.info("ZxSldService类doService方法：征信接口未返回有效值！");
                return null;
            }
            Map<String,Object> responseMap = new ObjectMapper().readValue(responseStr,Map.class) ;
            Response response = new Response();
            response.setResponseCode("00"+responseMap.get("code").toString());
            response.setMessage(responseMap.get("msg").toString());
            if(responseMap.get("data")!=null && !responseMap.get("data").toString().trim().equals("")){
                String dataStr = new ObjectMapper().writeValueAsString(responseMap.get("data"));
                Map<String,Object> resultMap = new HashMap<String, Object>();
                resultMap.put("data",dataStr);
                response.setResponseMap(resultMap);
            }
            updateTransZxInfo(request,responseStr);
            return response;
        } catch (Exception e) {
            log.info("ZxSldService类doService方法 : 异常",e);
            return null;
        }
    }

    /***
     * 保存征信缓存
     * @param request
     * @param responseStr
     * @return
     */
    private void updateTransZxInfo(Request request,String responseStr) throws Exception{
        log.info("ZxSldService类updateTransZxInfo方法 ：开始 request=" + request + ",responseStr=" + responseStr);
        try{
            SldOperateLog sldOperateLog = request.getOpeartor();
            sldOperateLog.setOperationContent("调用征信接口"+request.getTransCode()+"："+responseStr);
            sldOperateLogMapper.insert(sldOperateLog);
            log.info("ZxSldService类updateTransZxInfo方法：插入操作记录成功！");
        }catch (Exception e){
            log.error("ZxSldService类updateTransZxInfo方法：插入操作记录时异常！"+e.getMessage(),e);
            return;
        }
    }

    private String getResponseString(Request request) throws Exception {
        for(Method method : CreditServiceTools.class.getMethods()){
            if(method.getName().equals(request.getRequestMethod())){
                Object[] paramers = new Object[request.getDataObjects().size()+3];
                paramers[0] = ZXTransConstant.ZX_APP_KEY;
                log.info("ZxSldService类getResponseString方法：参数ZX_APP_KEY="+ZXTransConstant.ZX_APP_KEY);
                paramers[1] = ZXTransConstant.ZX_SIGN_KEY;
                log.info("ZxSldService类getResponseString方法：ZX_SIGN_KEY="+ZXTransConstant.ZX_SIGN_KEY+",ZX_SIGN="+paramers[1].toString());
                paramers[2] = ZXTransConstant.ZX_SIGN_ID;
                log.info("ZxSldService类getResponseString方法：ZX_SIGN_ID="+ZXTransConstant.ZX_SIGN_ID+",ZX_SIGN_ID="+paramers[2].toString());
                int index = 3;
                for(Object obj : request.getDataObjects()){
                    paramers[index] = obj;
                    log.info("ZxSldService类getResponseString方法：参数"+index+"="+paramers[index].toString());
                    if(paramers[index].toString().startsWith(Base64TImgUtil.SUB_FLAG)){
                        log.info("ZxSldService类getResponseString方法：发现文件格式参数="+index);
                        String objStr = obj.toString();
                        objStr=objStr.substring(12,objStr.length());
                        File file = Base64TImgUtil.getImgByBase64(objStr, Base64TImgUtil.IMG_JPG);
                        paramers[index]=file;
                        log.info("ZxSldService类getResponseString方法：文件转换成功!");
                    }
                    index++;
                }
                String result = method.invoke(null,paramers).toString();
                log.info("ZxSldService类getResponseString方法：本次调用返回结果"+result);
                for(int i =0;i<paramers.length;i++){
                    if(paramers[i] instanceof File){
                        File file = (File)paramers[i];
                        if(file.exists()){
                            String fileName = file.getName();
                            log.info("ZxSldService类getResponseString方法:准备删除缓存文件"+fileName);
                            if (!file.delete()) {
                                
                            }
                            log.info("ZxSldService类getResponseString方法:已删除缓存文件"+fileName);
                        }
                    }
                }
                return result;
            }
        }
        return null;
    }
}