package com.ule.uhj.dto.zgd;

import com.ule.uhj.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/11/26.
 */
public class MarketingDto implements Serializable {
    public static final int DATA_UPDATE_TYPE_ADD = 0;

    public static final int DATA_UPDATE_TYPE_SUB = 1;

    private String Code;

    private String Message;

    private String ActCode;

    private String ActExtendCode;

    private String DataName;

    private Object Data;

    private String DataType;

    private int DataUpdateType;

    private List<Gift> Gifts = new ArrayList<Gift>();

    private List<User> Users = new ArrayList<User>();

    public class User implements Serializable{
        private String userOnlyId;

        public String getUserOnlyId() {
            return userOnlyId;
        }

        public void setUserOnlyId(String userOnlyId) {
            this.userOnlyId = userOnlyId;
        }
    }

    public class Gift implements Serializable{
        private String GiftName;

        private String GiftCode;

        private String GiftType;

        private Map GiftExtendsData;

        public String getGiftName() {
            return GiftName;
        }

        public void setGiftName(String giftName) {
            GiftName = giftName;
        }

        public String getGiftCode() {
            return GiftCode;
        }

        public void setGiftCode(String giftCode) {
            GiftCode = giftCode;
        }

        public String getGiftType() {
            return GiftType;
        }

        public void setGiftType(String giftType) {
            GiftType = giftType;
        }

        public Map getGiftExtendsData() {
            return GiftExtendsData;
        }

        public void setGiftExtendsData(Map giftExtendsData) {
            GiftExtendsData = giftExtendsData;
        }
    }

    public int getDataUpdateType() {
        return DataUpdateType;
    }

    public void setDataUpdateType(int dataUpdateType) {
        DataUpdateType = dataUpdateType;
    }

    public User GetNewUser(){
        return new User();
    }

    public Gift GetNewGift(){
        return new Gift();
    }

    public void addUser(User user){
        this.Users.add(user);
    }

    public void addGift(Gift gift){
        this.Gifts.add(gift);
    }

    public List<User> getUsers() {
        return Users;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getActCode() {
        return ActCode;
    }

    public void setActCode(String actCode) {
        ActCode = actCode;
    }

    public String getActExtendCode() {
        return ActExtendCode;
    }

    public void setActExtendCode(String actExtendCode) {
        ActExtendCode = actExtendCode;
    }

    public String getDataName() {
        return DataName;
    }

    public void setDataName(String dataName) {
        DataName = dataName;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public List<Gift> getGifts() {
        return Gifts;
    }

    public void setGifts(List<Gift> gifts) {
        Gifts = gifts;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    @Override
    public String toString() {
        return "MarketingDto{" +
                "Code='" + Code + '\'' +
                ", Message='" + Message + '\'' +
                ", ActCode='" + ActCode + '\'' +
                ", ActExtendCode='" + ActExtendCode + '\'' +
                ", DataName='" + DataName + '\'' +
                ", Data=" + Data +
                ", DataUpdateType=" + DataUpdateType +
                ", Gifts=" + JsonUtil.getJsonStringFromObject(this.Gifts) +
                ", Users=" + JsonUtil.getJsonStringFromObject(this.Users) +
                '}';
    }
}
