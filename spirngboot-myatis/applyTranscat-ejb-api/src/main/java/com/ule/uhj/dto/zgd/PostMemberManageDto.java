package com.ule.uhj.dto.zgd;

import com.ule.uhj.util.Convert;
import com.ule.uhj.util.constant.POSTMEMBER_STATUS;

import java.beans.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/9/18.
 */
public class PostMemberManageDto implements Serializable{
    //掌柜贷地推
    public static final String REL_TYPE_ZGD = "zgd";
    //邮助手地推
    public static final String REL_TYPE_YZS = "yzs";
    //合影地推
    public static final String REL_TYPE_PHOTO = "photo";

    //地推额外身份
    private List<Long> postmemberStatusList = new ArrayList<>();

    //绑定关系编号
    private String RelTypeId;

    //绑定关系名
    private String RelTypeName;

    //绑定关系优先级
    private Long RelTypePriority;

    //地推人员编号
    private Long StatffId;

    //身份证号
    private String CertNo;

    //地推人员机构号
    private Long OrgCode;

    //名下绑定掌柜数
    private Long RelUserNum;

    //是否通过VPS查询数据，默认是
    private boolean QueryVps = true;

    //地推人员姓名
    private String MemberName;

    //地推手机号
    private String MobileNo;

    //原地推编号
    private String OriginMemberStatffId;

    //用户ID
    private String UserOnlyId;

    //用户ID组
    private String[] UserOnlyIds;

    //用户姓名
    private String UserName;

    //用户手机号
    private String UserMobileNo;

    //用户机构号
    private String UserOrgCode;

    //用户机构省
    private String UserProvinceName;

    //用户机构市
    private String UserCityName;

    //用户机构县
    private String UserAreaName;

    //用户机构镇
    private String UserTownName;

    //接口处理是否成功
    private boolean success = false;

    //接口处理结果描述
    private String message = "";

    //机构省代码
    private String OrgProvinceCode;

    //机构省名
    private String OrgProvinceName;

    //机构市代码
    private String OrgCityCode;

    //机构市名
    private String OrgCityName;

    //机构县代码
    private String OrgAreaCode;

    //机构县名
    private String OrgAreaName;

    //机构支局代码
    private String OrgTownCode;

    //机构支局名
    private String OrgTownName;

    //当前页
    private Long CurrentPage;

    //每页条目数
    private Long PageSize;

    //条目总量
    private Long totalSize;

    public static PostMemberManageDto newinstance(Map dataMap){
        PostMemberManageDto cloneDto = new PostMemberManageDto();
        Field[] fields = cloneDto.getClass().getDeclaredFields();
        for(Field field : fields){
            if(!Modifier.isStatic(field.getModifiers())){
                field.setAccessible(true);
                try{
                    Object fieldValue = dataMap.get(field.getName());
                    if(fieldValue!=null){
                        if(Number.class.isAssignableFrom(field.getType())){
                            field.set(cloneDto, Convert.toLong(fieldValue));
                        }else{
                            String FieldName = field.getName();
                            String MethodName = "set" + FieldName.substring(0,1).toUpperCase() + FieldName.substring(1,FieldName.length());
                            System.out.println(field.getType().getSimpleName());
                            if ( (fieldValue instanceof List) && (!List.class.isAssignableFrom(field.getType())) ) {
                                Method Setter = cloneDto.getClass().getDeclaredMethod(MethodName,List.class);
                                Setter.setAccessible(true);
                                Setter.invoke(cloneDto,((List)(fieldValue)));
                            }else{
                                Method Setter = cloneDto.getClass().getDeclaredMethod(MethodName,field.getType());
                                Setter.setAccessible(true);
                                Setter.invoke(cloneDto,fieldValue);
                            }
                        }
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
        return cloneDto;
    }

    public PostMemberManageDto DtoClone(){
        PostMemberManageDto cloneDto = new PostMemberManageDto();
        Field[] fields = cloneDto.getClass().getDeclaredFields();

        for(Field field : fields){
            if(!Modifier.isStatic(field.getModifiers())){
                field.setAccessible(true);
                try{
                    Object fieldValue = field.get(this);
                    if(fieldValue!=null){
                        if(Number.class.isAssignableFrom(field.getType())){
                            field.set(cloneDto, Convert.toLong(fieldValue));
                        }else{
                            field.set(cloneDto,fieldValue);
                        }
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
        return cloneDto;
    }

    public String[] getUserOnlyIds() {
        return UserOnlyIds;
    }

    public void setUserOnlyIds(List userOnlyIds) {
        if(userOnlyIds!=null && userOnlyIds.size()>0){
            String[] userOnlyIdsArray = new String[userOnlyIds.size()];
            for(int i = 0; i<userOnlyIds.size();i++){
                userOnlyIdsArray[i] = Convert.toStr(userOnlyIds.get(i),"");
            }
            this.setUserOnlyIds(userOnlyIdsArray);
        }

    }

    public String getUserMobileNo() {
        return UserMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        UserMobileNo = userMobileNo;
    }

    public void setUserOnlyIds(String[] userOnlyIds) {
        UserOnlyIds = userOnlyIds;
    }

    public String getRelTypeName() {
        return RelTypeName;
    }

    public void setRelTypeName(String relTypeName) {
        RelTypeName = relTypeName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserOrgCode() {
        return UserOrgCode;
    }

    public void setUserOrgCode(String userOrgCode) {
        UserOrgCode = userOrgCode;
    }

    public String getUserProvinceName() {
        return UserProvinceName;
    }

    public void setUserProvinceName(String userProvinceName) {
        UserProvinceName = userProvinceName;
    }

    public String getUserCityName() {
        return UserCityName;
    }

    public void setUserCityName(String userCityName) {
        UserCityName = userCityName;
    }

    public String getUserAreaName() {
        return UserAreaName;
    }

    public void setUserAreaName(String userAreaName) {
        UserAreaName = userAreaName;
    }

    public String getUserTownName() {
        return UserTownName;
    }

    public void setUserTownName(String userTownName) {
        UserTownName = userTownName;
    }

    public Long getRelUserNum() {
        return RelUserNum;
    }

    public void setRelUserNum(Long relUserNum) {
        RelUserNum = relUserNum;
    }

    public Long getOrgCode() {
        return OrgCode;
    }

    public void setOrgCode(Long orgCode) {
        OrgCode = orgCode;
    }

    public boolean isQueryVps() {
        return QueryVps;
    }

    public void setQueryVps(boolean queryVps) {
        QueryVps = queryVps;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getOrgProvinceCode() {
        return OrgProvinceCode;
    }

    public void setOrgProvinceCode(String orgProvinceCode) {
        OrgProvinceCode = orgProvinceCode;
    }

    public String getOrgCityCode() {
        return OrgCityCode;
    }

    public void setOrgCityCode(String orgCityCode) {
        OrgCityCode = orgCityCode;
    }

    public String getOrgAreaCode() {
        return OrgAreaCode;
    }

    public void setOrgAreaCode(String orgAreaCode) {
        OrgAreaCode = orgAreaCode;
    }

    public String getOrgTownCode() {
        return OrgTownCode;
    }

    public void setOrgTownCode(String orgTownCode) {
        OrgTownCode = orgTownCode;
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

    public String getRelTypeId() {
        return RelTypeId;
    }

    public void setRelTypeId(String relTypeId) {
        RelTypeId = relTypeId;
    }

    public Long getStatffId() {
        return StatffId;
    }

    public void setStatffId(Long statffId) {
        StatffId = statffId;
    }

    public String getUserOnlyId() {
        return UserOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        UserOnlyId = userOnlyId;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(Long currentPage) {
        CurrentPage = currentPage;
    }

    public Long getPageSize() {
        return PageSize;
    }

    public void setPageSize(Long pageSize) {
        PageSize = pageSize;
    }

    public String getOrgProvinceName() {
        return OrgProvinceName;
    }

    public void setOrgProvinceName(String orgProvinceName) {
        OrgProvinceName = orgProvinceName;
    }

    public String getOrgCityName() {
        return OrgCityName;
    }

    public void setOrgCityName(String orgCityName) {
        OrgCityName = orgCityName;
    }

    public String getOrgAreaName() {
        return OrgAreaName;
    }

    public void setOrgAreaName(String orgAreaName) {
        OrgAreaName = orgAreaName;
    }

    public String getOrgTownName() {
        return OrgTownName;
    }

    public void setOrgTownName(String orgTownName) {
        OrgTownName = orgTownName;
    }

    public String getOriginMemberStatffId() {
        return OriginMemberStatffId;
    }

    public void setOriginMemberStatffId(String originMemberStatffId) {
        OriginMemberStatffId = originMemberStatffId;
    }

    public Long getRelTypePriority() {
        return RelTypePriority;
    }

    public void setRelTypePriority(Long relTypePriority) {
        RelTypePriority = relTypePriority;
    }

    public String getCertNo() {
        return CertNo;
    }

    public void setCertNo(String certNo) {
        CertNo = certNo;
    }

    @Override
    public String toString() {
        return "PostMemberManageDto{" +
                "RelTypeId='" + RelTypeId + '\'' +
                ", RelTypeName='" + RelTypeName + '\'' +
                ", StatffId=" + StatffId +
                ", MobileNo='" + MobileNo + '\'' +
                ", OriginMemberStatffId='" + OriginMemberStatffId + '\'' +
                ", UserOnlyId='" + UserOnlyId + '\'' +
                ", UserOnlyIds=" + Arrays.toString(UserOnlyIds) +
                ", PageSize=" + PageSize +
                ", totalSize=" + totalSize +
                ", OrgCode=" + OrgCode +
                ", MemberName='" + MemberName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PostMemberManageDto that = (PostMemberManageDto) object;

        if (StatffId != null ? !StatffId.equals(that.StatffId) : that.StatffId != null) return false;
        if (UserOnlyId != null ? !UserOnlyId.equals(that.UserOnlyId) : that.UserOnlyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = StatffId != null ? StatffId.hashCode() : 0;
        result = 31 * result + (UserOnlyId != null ? UserOnlyId.hashCode() : 0);
        return result;
    }

    @Transient
    public List<Long> getPostmemberStatusList() {
        return postmemberStatusList;
    }

    public void setPostmemberStatusList(List<Long> postmemberStatusList) {
        this.postmemberStatusList = postmemberStatusList;
    }

    public boolean _isSomeStatus(Long status){
        if(this.postmemberStatusList == null || this.postmemberStatusList.size()<=0){
            return false;
        }
        return this.postmemberStatusList.contains(status);
    }

    /***
     * 是否是支局长 1-true 0-false
     * @return
     */
    public int isGeneral(){
        return _isSomeStatus(Long.parseLong(POSTMEMBER_STATUS.GENERAL)) ? 1 : 0;
    }

    /***
     * 是否通过实名认证 1-true 0-false
     * @return
     */
    public int isRealname(){
        return _isSomeStatus(Long.parseLong(POSTMEMBER_STATUS.REALNAME)) ? 1 : 0;
    }

    /***
     * 是否已授权 1-true 0-false
     * @return
     */
    public int isAOB (){
        return _isSomeStatus(Long.parseLong(POSTMEMBER_STATUS.AUTHORIZATION_OF_BUSSINESS)) ? 1 : 0;
    }
}
