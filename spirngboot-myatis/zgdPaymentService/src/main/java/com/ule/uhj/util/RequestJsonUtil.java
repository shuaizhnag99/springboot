package com.ule.uhj.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/***
 * 郑鑫修订版V1.1
 * 特么的百度真是害人……
 * @author yfzx-sh-zhengxin
 *
 */
public class RequestJsonUtil {
	/***
     * 获取 request 中 json 字符串的内容
     * 
     * @param request
     * @return : <code>byte[]</code>
     * @throws IOException
     */
    public static Map<String,Object> getRequestMap(HttpServletRequest request)
            throws IOException {
    	Map<String,Object> responseMap = new HashMap<String, Object>();
    	String submitMethod = request.getMethod();
    	String data = new String();
    	if(submitMethod.equals("POST")){
    		data = getRequestPostStr(request);
    	}
    	if(StringUtils.isBlank(data)){
    		Map<String,String[]> requestMap = request.getParameterMap();
    		for(String key : requestMap.keySet()){
    			String[] value = requestMap.get(key);
    			responseMap.put(key, value[0]);
    		}
    		return responseMap;
    	}else{
    		responseMap = UhjWebJsonUtil.getForm(data, Map.class);
    		if(responseMap==null){
    			responseMap = new HashMap<String, Object>();
    			String[] paramers = data.split("&");
    			for(String paramer : paramers){
    				String[] paramer2 = paramer.split("=");
    				if(paramer2.length>=2){
    					responseMap.put(paramer2[0], paramer2[1]);
    				}else{
    					responseMap.put(paramer2[0], "");
    				}
    			}
    		}
    		return responseMap;
    	}
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
}
