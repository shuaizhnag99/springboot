package com.ule.uhj.dto.zgd;

import java.io.Serializable;

/**
 * Created by zhengxin on 2018/11/13.
 */
public class BussinessCardDto implements Serializable{
    //接口调用是否成功
    private boolean success;

    //失败原因
    private String message;

    //店铺名称
    private String storeName;

    //用户id
    private String userOnlyId;

    //注册号
    private String regCode;

    //信用代码
    private String creditCode;

    //店铺所有人类型
    private String owner;

    //法人姓名
    private String legalPersonName;

    //店铺地址
    private String storeAddress;

    //注册时间
    private String regDate;

    public String toJsonpString(){
        return "jsoncallback("+this.toString()+")";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUserOnlyId() {
        return userOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId;
    }
}
