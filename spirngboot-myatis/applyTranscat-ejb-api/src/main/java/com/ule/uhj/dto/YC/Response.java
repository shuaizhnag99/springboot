package com.ule.uhj.dto.YC;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Response implements Serializable{
	//字典文件，替换出现频率高的描述用字段
	private Map<String,Integer> dual;
	//字典索引
	private Integer dualIndex;
	public enum Code{
		DOSERVICE_SUCCESS("000000","业务处理成功！"),
		TRANSCODE_NOTFOUNT("000001","交易码非法！"),
		UNKNOW_ERROR("999999","系统发生未知异常！");
		
		private String code;
		
		private String msg;
		
		public String getCode() {
			return code;
		}

//		public void setCode(String code) {
//			this.code = code;
//		}

		public String getMsg() {
			return msg;
		}

//		public void setMsg(String msg) {
//			this.msg = msg;
//		}

		private Code(String code,String msg){
			this.code = code;
			this.msg =msg;
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4468130804887761878L;
	
	private String code;
	
	private String message;
	
	private Map<String,Object> data;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response(String code,String message,Object dataObj) throws Exception {
		this.code = code;
		this.message = message;
		
		Class<? extends Object> objClass = dataObj.getClass();
//		if(objClass.getName().equals("java.util.Map")){
		if(dataObj instanceof Map){
			Set<String> keySet =  (Set<String>) objClass.getMethod("keySet").invoke(dataObj);
			if(keySet == null) throw new Exception("Response have no set");
			
			for(String key : keySet){
				Class[] paramer = new Class[1];
				paramer[0] = Object.class;
				Object value = objClass.getMethod("get",paramer).invoke(dataObj, key);
				this.data.put(key, value);
			}
		}
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", message=" + message + ", data="
				+ data + "]";
	}

	/***
	 * 基于字典的JSON压缩算法
	 * @return
	 */
	public String getCompressData(){
		this.dualIndex = 0;
		this.dual = new HashMap<String, Integer>();
		StringBuffer result = new StringBuffer();
		result.append(getCompressData(this.getData()));
		result.append("{");
		for(String key : dual.keySet()){
			result.append(dual.get(key)+":"+key+",");
		}
		return result.substring(0, result.length()-1);
	}
	
	/***
	 * 基于字典的JSON压缩算法
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getCompressData(Object data){
		if(data == null) return "";
		StringBuffer buffer = new StringBuffer();
		if(data instanceof Map){
			Map<String,Object> dataMap = (Map<String,Object>)data;
			buffer.append("{");
			for(String key : dataMap.keySet()){
				//字段加入字典
				if(dual.get(key)==null){
					dual.put(key, dualIndex++);
				}
				buffer.append(dual.get(key)+":");
				if((dataMap.get(key) instanceof Map) || (dataMap.get(key) instanceof List)){
					buffer.append(getCompressData(dataMap.get(key)));
				}else{
					buffer.append(dataMap.get(key)+",");
					continue;
				}
			}
			return buffer.substring(0,buffer.length()-1)+"},";
		}else if(data instanceof List){
			List<Object> dataList = (List<Object>)data;
			buffer.append("[");
			for(Object obj : dataList){
				if((obj instanceof Map) || obj instanceof List){
					buffer.append(getCompressData(obj));
				}else{
					return "";
				}
			}
			return buffer.substring(0,buffer.length()-1)+"],";
		}else{
			return "";
		}
	}
	
	public Response(String code,String message){
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
