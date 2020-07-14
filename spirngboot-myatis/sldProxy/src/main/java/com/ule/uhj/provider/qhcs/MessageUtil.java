package com.ule.uhj.provider.qhcs;

import com.ule.uhj.sld.util.DateUtil;

public class MessageUtil {
	public static String getMHeader(String appId,String orgCode,String chnlId,String authCode,String authDate) {

		String transNo = appId + System.currentTimeMillis();

		StringBuffer sb = new StringBuffer(
				"{\"orgCode\":\""+orgCode+"\","
				+ "\"chnlId\":\""+chnlId+"\"," 
				+ "\"transNo\":\"" + transNo+ "\"," 
				+ "\"transDate\":\""+DateUtil.currTimeStr()+"\","
				+ "\"authCode\":\""+authCode+"\","
				+ "\"authDate\":\""+authDate+"\"}"
				);
		
//		StringBuffer sb = new StringBuffer(
//                "{\"orgCode\":\"10000000\","
//                + "\"chnlId\":\"qhcs-dcs\","
//                + "\"transNo\":\"A00000126\","
//                + "\"transDate\":\"2017-08-10 14:12:14\","
//                + "\"authCode\":\"CRT001A2\","
//                + "\"authDate\":\"2017-8-10 14:12:14\"}");
		return sb.toString();
	}

	public static String getBusiData(String appId, String phone, String idCard,String name) {

		String batchNo = appId + System.currentTimeMillis();
		String seqNo = "ule" + System.currentTimeMillis();
		
		StringBuffer sb = new StringBuffer(
		// "{\"batchNo\":\"abc123\","
		// + "\"records\":[{\"idNo\":\"440102198301114447\","
		// + "\"idType\":\"0\","
		// + "\"name\":\"米么联调\","
		// + "\"reasonCode\":\"01\","
		// + "\"entityAuthCode\":\"a000111\","
		// + "\"entityAuthDate\":\"2017-8-10\","
		// + "\"seqNo\":\"r123456789\"}]}");
				"{\"batchNo\":\""+batchNo+"\","
						+ "\"records\":[{\"idNo\":\""+idCard+"\","
						+ "\"mobileNo\":\""+phone+"\"," 
						+ "\"idType\":\"0\","
						+ "\"name\":\""+name+"\"," 
						+ "\"reasonCode\":\"01\","
						+ "\"entityAuthCode\":\"a000111\","
						+ "\"entityAuthDate\":\"2017-8-10\","
						+ "\"seqNo\":\""+seqNo+"\"}]}"
				);
		return sb.toString();
	}
	
	
	public static String getBusiDataMSC8184(String appId, String idCard,String name) {

		String batchNo = appId + System.currentTimeMillis();
		
		String seqNo = "ule" + System.currentTimeMillis();
		
		StringBuffer sb = new StringBuffer(
				"{\"batchNo\":\""+batchNo+"\","
						+ "\"records\":[{\"idNo\":\""+idCard+"\","
						//+ "\"mobileNo\":\""+phone+"\"," 
						+ "\"idType\":\"0\","
						+ "\"name\":\""+name+"\"," 
						+ "\"reasonCode\":\"01\","
						+ "\"entityAuthCode\":\"a000111\","
						+ "\"entityAuthDate\":\"2017-8-10\","
						+ "\"seqNo\":\""+seqNo+"\"}]}"
				);
		return sb.toString();
	}

	public static String getSecurityInfo(String signatureValue, String userName,String pwd) {
		StringBuffer sb = new StringBuffer(
				"{\"signatureValue\":\""+ signatureValue+ "\","
				+ "\"userName\":\""+userName+"\","
				+ "\"userPassword\":\""	+ pwd + "\"}"
				);
		
//		StringBuffer sb = new StringBuffer("{\"signatureValue\":\"" + signatureValue
//                + "\",\"userName\":\"V_PA025_QHCS_DCS\",\"userPassword\":\"" + pwd + "\"}");
		return sb.toString();
	}
}
