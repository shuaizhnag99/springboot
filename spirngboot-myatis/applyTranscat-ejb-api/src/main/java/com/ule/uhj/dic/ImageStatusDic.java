package com.ule.uhj.dic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/***
 * 图片状态
 * @author zhangshuai
 *
 */
public enum ImageStatusDic{
	
	////////////////////图片公用状态///////////////////
	/**
	 * 10 照片清晰
	 */
	PHOTO_SUCESS("10","照片清晰","照片清晰","image","所有图片"),
	/**
	 * 空 未知状态
	 */
	PHOTO_DEFAULT("99","未知状态","需要上传图片","image","所有图片"),
	/**
	 * 0  未审核可重新上传
	 */
	PHOTO_ZERO("0","未审核需要上传","未审核需要上传","image","所有图片"),
    ////////////////////身份证正面照状态///////////////////
	/**
	 * 11 上传照片不正确，非身份证正面照
	 */
	CARD_POS_ERROR("11","上传照片不正确，非身份证正面照","非身份证正面照","certPositiveImg","身份证正面照"),
	
	/**
	 * 12 身份证照片模糊、看不清
	 */
	CARD_POS_NOT_CLEAR("12","身份证正面照模糊、看不清","不清晰","certPositiveImg","身份证正面照"),
	
	/**
	 * 13、身份证姓名不清晰或有遮挡
	 */
	CARD_POS_NAME_NOT_CLEAR("13","身份证正面照拍摄不完整","拍摄不完整","certPositiveImg","身份证正面照"),
	
	/**
	 * 14、身份证住址不清晰或有遮挡
	 */
	CARD_POS_ADDRESS_INCOMPLETE("14","身份证正面照非原件或为截屏图片","非原件或为截屏图片","certPositiveImg","身份证正面照"),
    ////////////////////身份证反面照状态///////////////////
	/**
	 * 11、上传照片不正确，非身份证反面照 
	 */
	CARD_NEG_ERROR("11","上上传照片不正确，非身份证反面照","非身份证反面照","certNegativeImg","身份证反面照"),
	/**
	 * 身份证反面照拍摄不完整;-12
	 */
	CARD_NEG_NOT_INCOMPLETE("12","身份证反面照拍摄不完整","拍摄不完整","certNegativeImg","身份证反面照"),
	/**
	 * 身份证已过有效期;-13
	 */
	CARD_NEG_INVALID("13","身份证已过有效期;","已过有效期","certNegativeImg","身份证反面照"),
	/**
	 * 身份证反面照模糊看不清;-14
	 */
	CARD_NEG_VAGUE("14","身份证反面照模糊看不清;","模糊看不清","certNegativeImg","身份证反面照"),
	/**
	 * 身份证反面照非原件或为截屏图片;-15
	 */
	CARD_NEG_SCREENSHOTS_PICTURES("15","身份证反面照非原件或为截屏图片","非原件或为截屏图片","certNegativeImg","身份证反面照"),

    ////////////////////手持身份证照片状态///////////////////
	/**
	 * 11、上传照片不正确，非手持身份证照片
	 */
	CARD_HOLD_ERROR("11","上传照片不正确，非手持身份证照片","非手持身份证照片","certHoldImg","手持身份证照片"),
	
	/**
	 * 12、手持身份证照，本人未出现在照片里
	 */
	CARD_HOLD_ONESELF_NOT_IN_PICTURES("12","手持身份证照，本人未出现在照片里","本人未出现在照片里","certHoldImg","手持身份证照片"),
	
	/**
	 * 13、手持身份证照模糊、看不清;
	 */
	CARD_HOLD_NOT_CLEAR("13","手持身份证照模糊、看不清","照片不清晰","certHoldImg","手持身份证照片"),
	
	/**
	 * 14、手持身份证照模糊、看不清;
	 */
	CARD_HOLD_NOT_ONESELF("14","手持身份证照，本人与身份证非同一人","本人与身份证非同一人","certHoldImg","手持身份证照片"),
	
	/**
	 * 15、手持身份证照，本人上半身及头部未拍全
	 */
	CARD_HOLD_SHOOTING_NOT_COMPLETE("15","手持身份证照，本人上半身及头部为拍全","上半身及头部未拍全","certHoldImg","手持身份证照片"),
	
	/**
	 * 16、手持身份证照非原件或为截屏图片;
	 */
	CARD_HOLD_SCREENSHOTS_PICTURES("16","手持身份证照非原件或为截屏图片","非原件或为截屏图片","certHoldImg","手持身份证照片"),
    ////////////////////房产证明状态///////////////////
	
	/**
	 * 11、上传照片不正确，非房产证明
	 */
	STORE_PROPERTY_ERROR("11","上传照片不正确，非房产证明","非房产证明","storePropertyUrl","房产证明"),
	
	/**
	 * 12、房产证明字迹模糊、看不清
	 */
	STORE_PROPERTY_NOT_CLEAR("12","房产证明字迹模糊、看不清","字迹模糊看不清","storePropertyUrl","房产证明"),
	
	/**
	 * 13、房产证明非原件或为截屏图片
	 */
	STORE_PROPERTY_SCREENSHOTS_PICTURES("13","房产证明非原件或为截屏图片","非原件或为截屏图片","storePropertyUrl","房产证明"),
	
    ////////////////////营业执照状态///////////////////
	
	/**
	 * 11、上传照片不正确，非营业执照
	 */
	BUSLIECNCE_ERROR("11","上传照片不正确，非营业执照","非营业执照","busLiecnceImg","营业执照"),
	
	/**
	 * 12、营业执照副本未拍全
	 */
	BUSLIECNCE_NOT_FULL("12","营业执照副本未拍全","未拍全","busLiecnceImg","营业执照"),
	
	/**
	 * 13、营业执照照片模糊、看不清
	 */
	BUSLIECNCE_NOT_CLEAR("13","营业执照照片模糊、看不清","不清晰","busLiecnceImg","营业执照"),
	/**
	 * 14、营业执照非原件或为截屏图片;
	 */
	BUSLIECNCE_SCREENSHOTS_PICTURES("14","营业执照非原件或为截屏图片","非原件或为截屏图片","busLiecnceImg","营业执照"),
	/**
	 *15、营业执照字迹模糊，请更换正本或副本;
	 */
	BUSLIECNCE_WRITING_BLURRED("15","营业执照字迹模糊，请更换正本或副本","字迹模糊请用正本或副本","busLiecnceImg","营业执照"),
	/**
	 *20、营业执照已注销或已过期;
	 */
	BUSLIECNCE_EXPIRE("20","营业执照已注销或已过期","营业执照已注销或已过期","busLiecnceImg","营业执照"),
	 ////////////////////营业执照旧状态///////////////////
	
	/**
	 * 16、营业执照副本未拍全
	 */
	BUSLIECNCE_PERSON_NOT_CLEAR("16","经营者不清晰","经营者不清晰","busLiecnceImgOld","营业执照"),
	
	/**
	 * 17、营业执照照片模糊、看不清
	 */
	BUSLIECNCE_DATE_NOT_CLEAR("17","注册日期不清晰","注册日期不清晰","busLiecnceImgOld","营业执照"),
	/**
	 * 18、营业执照非原件或为截屏图片;
	 */
	BUSLIECNCE_ADDRESS_NOT_CLEAR("18","营场所不清晰","营场所不清晰","busLiecnceImgOld","营业执照"),
	/**
	 *19、营业执照字迹模糊，请更换正本或副本;
	 */
	BUSLIECNCE_NOT_ORIGINAL("19","非原件","非原件","busLiecnceImgOld","营业执照"),
	
	
    ////////////////////店铺内照片状态///////////////////
	/**
	 * 11、上传照片不正确，非店铺内照片
	 */
	STORE_INNER_ERROR("11","上传照片不正确，非店铺内照片","非店铺内照片","storeInnerImge","店铺内照片"),
	
	/**
	 * 12、店铺内照片模糊，看不清
	 */
	STORE_INNER_NOT_CLEAR("12","店铺内照片模糊，看不清","不清晰","storeInnerImge","店铺内照片"),
	
	/**
	 * 13、店铺内照片不完整，需拍摄店内全景;
	 */
	STORE_INNER_INCOMPLETE("13","店铺内照片不完整，需拍摄店内全景","照片不完整","storeInnerImge","店铺内照片"),
	
	/**
	 * 14、店铺内照片模糊，看不清
	 */
	STORE_INNER_SCREENSHOTS_PICTURES("14","店铺内照片非原件或为截屏图片","非原件或为截屏图片","storeInnerImge","店铺内照片"),
    ////////////////////店铺外照片状态///////////////////
	/**
	 * 11、上传照片不正确，非店铺外照片
	 */
	STORE_ERROR("11","上传照片不正确，非店铺外照片","非店铺外照片","storeImge","店铺外照片"),
	
	/**
	 * 12、店铺外照片模糊、看不清全
	 */
	STORE_NOT_CLEAR("12","店铺外照片模糊、看不清","不清晰","storeImge","店铺外照片"),
	
	/**
	 * 13、店铺外照片模糊、看不清全
	 */
	STORE_INCLUDES_DOOR("13","店铺外照片需包含店招与大门","需包含店招与大门","storeImge","店铺外照片"),
	/**
	 * 14、店铺外照片非原件或为截屏图片;
	 */
	STORE_SCREENSHOTS_PICTURES("14","店铺外照片非原件或为截屏图片","非原件或为截屏图片","storeImge","店铺外照片");
	/**
	 * 状态码
	 */
	private String code;
	/**
	 * 状态描述
	 */
	private String desc;
	
	/**
	 * 状态简述
	 */
	private String simDesc;
	
	/**
	 * 分组ID
	 */
	private String groupId;
	
	/**
	 * 分组描述
	 */
	private String groupName;
	
	
	private ImageStatusDic(String code, String desc,String simDesc,String groupId,String groupName) {
		this.code = code;
		this.desc = desc;
		this.simDesc=simDesc;
		this.groupId=groupId;
		this.groupName=groupName;
	}
	public String getCode() {
		return code;
	}
//	public void setCode(String code) {
//		this.code = code;
//	}
	public String getDesc() {
		return desc;
	}
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
	public String getSimDesc() {
		return simDesc;
	}
//	public void setSimDesc(String simDesc) {
//		this.simDesc = simDesc;
//	}
	public String getGroupId() {
		return groupId;
	}
//	public void setGroupId(String groupId) {
//		this.groupId = groupId;
//	}
	public String getGroupName() {
		return groupName;
	}
//	public void setGroupName(String groupName) {
//		this.groupName = groupName;
//	}
	/**
	 * 根据gourpId获取相应ImageStatusDic 集合
	 * @param groupIds groupId集合 每个groupId用英文逗号分割
	 * @return
	 */
	public static List<ImageStatusDic> getByGroupId(String groupIds){
		 List<ImageStatusDic> list=new ArrayList<ImageStatusDic>();
		 if(StringUtils.isNotBlank(groupIds)){
			 
			 String [] ids=groupIds.split(",");
			 
			 for(String id:ids){
				 for(ImageStatusDic sd:ImageStatusDic.values()){
					 
					 if(sd.getGroupId().equals(id)){
						 
						 list.add(sd);
//						 System.out.println(String.format("groupId:%s--code:%s--desc:%s", sd.getGroupId(),sd.getCode(),sd.getDesc()));
					 }
				 }
				 
			 }
		 }
		 
		 return list;
	}
	/**
	 * 
	 * @param groupId 必填 单个字典分组ID
	 * @param codestr 非必填  可以是多个CODE，但是必须以英文逗号分开。可以为空，返回整组的值
	 * @return
	 */
	public static List<ImageStatusDic> getByGroupIdAndCodes(String groupId,String codestr){
		 List<ImageStatusDic> list=new ArrayList<ImageStatusDic>();
		 if(StringUtils.isNotBlank(groupId)){
				 for(ImageStatusDic sd:ImageStatusDic.values()){
					 
					 if(sd.getGroupId().equals(groupId)){
						 if(StringUtils.isNotBlank(codestr)){
							 String [] codes=codestr.split(",");
							 for(String code:codes){
								 
								 if(sd.getCode().equals(code)){
									 list.add(sd);
									 System.out.println(String.format("groupId:%s--code:%s--desc:%s", sd.getGroupId(),sd.getCode(),sd.getDesc()));
								 }
							 }
						 }else{
//							 System.out.println(String.format("groupId:%s--code:%s--desc:%s", sd.getGroupId(),sd.getCode(),sd.getDesc()));
							 list.add(sd);
						 }
					 }
				 }
			 
		 }
		 
		 return list;
	}
	
	/**
	 * 
	 * @param groupId 必填 单个字典分组ID
	 * @param codestr 必填  CODE 
	 * @return
	 */
	public static ImageStatusDic getOneByGroupIdAndCodes(String groupId,String code){
		 
		 if(StringUtils.isNotBlank(groupId)&&StringUtils.isNotBlank(code)){
				 for(ImageStatusDic sd:ImageStatusDic.values()){
					 if(sd.getGroupId().equals(groupId)&&sd.getCode().equals(code)){
						 System.out.println(String.format("groupId:%s--code:%s--desc:%s", sd.getGroupId(),sd.getCode(),sd.getDesc()));
									 return sd;
								 
					 }
				 }
			 
		 }
		 
		 return null;
	}
	@SuppressWarnings("static-access")
	public static void main(String args[]){
		getByGroupIdAndCodes("storeInnerImge", "11,12");
		getOneByGroupIdAndCodes("storeImge", "11");
		
		/*String upStatus="",errMsg="";
		String code="13";
		for(ImageStatusDic sd:getByGroupId("storeImge")){
			if(code.equals(sd.PHOTO_SUCESS.getCode())){
				upStatus="1";
				errMsg="sucess";
				break;
			}else{
				if(code.equals(sd.getCode())){
					upStatus="2";
					errMsg=sd.getDesc();
					break;
					
				}
			}
		}*/
//		System.out.println(errMsg);
	}
	
}
