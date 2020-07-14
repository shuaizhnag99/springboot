package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;
import com.ule.uhj.dto.zgd.TransferModel;

@Transfer(name = "人员信息模型")
public class PostmemberDto extends CheckedTransModel{
    //员工等级-普通员工
    public static final Long LEVEL_COMMON = 0l;
    //员工等级-支局长，叫我将军！
    public static final Long LEVEL_GENERAL = 1l;
    //账户状态-正常
    public static final Long ACCOUNT_STATUS_NORMAL = 1l;
    //账户状态-冻结
    public static final Long ACCOUNT_STATIS_FREEZE = 0l;
    private static final long serialVersionUID = -2172293326515936714L;

    @ParameterDescripor(Index = "name", Descript = "人员姓名")
    private String name;

    @ParameterDescripor(Index = "phone_number", Descript = "手机号")
    private String phoneNumber;

    @ParameterDescripor(Index = "cert_no", Descript = "身份证号")
    private String certNo;

    @ParameterDescripor(Index = "staff_id", Descript = "员工编号")
    private Long staffId;

    @ParameterDescripor(Index = "level", Descript = "员工等级")
    private Long level;

    @ParameterDescripor(Index = "authorized", Descript = "是否已授权")
    private Boolean isAuthorized;

    @ParameterDescripor(Index = "real_name", Descript = "是否已实名认证")
    private Boolean isRealName;

    @ParameterDescripor(Index = "real_name_start_date", Descript = "实名认证开始日期")
    private String realNameStartDate;

    @ParameterDescripor(Index = "real_name_end_date", Descript = "实名认证结束日期")
    private String realNameEndDate;

    @ParameterDescripor(Index = "account_status", Descript = "账户状态")
    private Long accountStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }

    public Boolean getRealName() {
        return isRealName;
    }

    public void setRealName(Boolean realName) {
        isRealName = realName;
    }

    public String getRealNameStartDate() {
        return realNameStartDate;
    }

    public void setRealNameStartDate(String realNameStartDate) {
        this.realNameStartDate = realNameStartDate;
    }

    public String getRealNameEndDate() {
        return realNameEndDate;
    }

    public void setRealNameEndDate(String realNameEndDate) {
        this.realNameEndDate = realNameEndDate;
    }

    public Long getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Long accountStatus) {
        this.accountStatus = accountStatus;
    }
}
