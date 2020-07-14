package com.ule.uhj.app.zgd.util;


public enum ReturnReasonEnum {
	LicenceError("RT01","营业执照信息有误，请重新输入"),
	StoreNameError("RT02","店铺名称不正确，请重新输入"),
	StoreAddressError("RT03","店铺地址不正确，请重新输入"),
	StoreInnerPhotoError("RT04","店铺内照片拍摄不正确，请重新拍照"),
	StoreOutterPhotoError("RT05","店铺外照片拍摄不正确，请重新拍照"),
	StoreAcreageError("RT06","店铺面积不正确，请重新输入"),
	IncomeError("RT07","每日营业额不正确，请重新输入"),
	propertyTypeError("RT08","店铺房产类型不正确，请重新输入"),
	OtherIncomeError("RT09","其他收入不正确，请重新输入"),
	marialError("RT10","结婚证不正确，请重新输入"),
	StorePhotoError("RT11","店铺照片拍摄不正确，请与工作人员合影");
	
	private String reasonCode;
    private String message;
    
    ReturnReasonEnum(String reasonCode, String message) {
        this.reasonCode = reasonCode;
        this.message = message;
    }
    
    public static ReturnReasonEnum get(String reasonCode){
        for(ReturnReasonEnum returnReasonEnum : ReturnReasonEnum.values()){
            if(returnReasonEnum.getReasonCode().equals(reasonCode)){
                return returnReasonEnum;
            }
        }
        return null;
    }

	public String getReasonCode() {
		return reasonCode;
	}

 
	public String getMessage() {
		return message;
	}
 
    
    
    
}
