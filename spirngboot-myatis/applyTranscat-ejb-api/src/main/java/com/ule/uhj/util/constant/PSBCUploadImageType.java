package com.ule.uhj.util.constant;
/**
 * 邮储上传图片类型 /01-身份证正面，02-身份证反面03-配偶身份证正面 04-配偶身份证反面 05-收入证明  06-人脸识别
 * @author zhangshuai
 * add time  20200309
 *
 */
public class PSBCUploadImageType {
	//////////////邮储使用参数/////////////////////
	//01-身份证正面
	public static final String idCardPositive="01";
	//02-身份证反面
	public static final String idCardOpposite="02";
	//03-配偶身份证正面
    public static final String spouseIdCardPositive="03";
	//04-配偶身份证反面
	public static final String spouseIdCardOpposite="04";
	//05-收入证明 
	public static final String incomecertificate="05";
	//06-人脸识别
	public static final String faceRecognition="06";
   //////////////邮储使用参数/////////////////////
	 //////////////掌柜贷人脸识别区分/////////////////////
	//申请时人脸识别
	public static final String faceRecognition_apply="061";
	//现金版支用时人脸识别
	public static final String faceRecognition_loan="062";
	
	//批销支用时人脸识别
	public static final String faceRecognition_px_loan="063";
	 //////////////掌柜贷人脸识别区分/////////////////////


}
