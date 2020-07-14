package com.ule.uhj.dto.opc;

//import com.ule.jboss.util.StringUtils;
import com.ule.uhj.Annotation.Trans;
import com.ule.uhj.util.Convert;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 还款分润报表传输模型
 * Created by zhengxin on 2018/3/7.
 */
public class ShareProfitQueryDto implements Serializable{

    //查询是否成功的标识 true-本次查询处理成功，后台确认返回结果 false-后台查询异常
    private boolean isSuccess;
    //后台查询异常时，此处输出异常信息
    private StringBuffer errorMessage;

    private int errorMessageNumber;

    //数据头
    private long firstResult;

    //数据尾
    private long maxResult;

    //数据总条目数
    private long totalSize;

    //本次查询实际条目数
    private long factSize;

    //查询获得的记录
    private List<ShareProfitQueryDto> resultList;

    // 查询日期-开始
    @Trans
    private String startDate;
    // 查询日期-结束
    @Trans
    private String endDate;
    // 邮储一级分行
    @Trans
    private String firstBank;
    // 邮储二级分行
    private String secondaryBanks;
    // 掌柜姓名
    @Trans
    private String userName;
    // 掌柜手机号
    @Trans
    private String mobilePhone;
    // 掌柜机构号
    @Trans
    private String orgCode;
    //掌柜ID
    private String userOnlyId;
    //省
    @Trans
    private String province;
    //市
    @Trans
    private String city;
    //县
    @Trans
    private String country;
    // 邮储借据编号
    private String loanNumber;
    // 借款金额
    private String loanAmount;
    // 借款日期
    private String loanDate;
    // 还款日期
    private String repayDate;
    // 还款本金
    private String repayAmount;
    // 还款利息
    private String repayInterest;
    // 还款罚息
    private String penaltyInterest;
    // 邮政收入
    private String postIncome;

    /***
     * 示例应用
     * @param argsp
     */
    public static void main(String argsp[]){
        //查询第0-19条数据，总计20条
        ShareProfitQueryDto dto = new ShareProfitQueryDto(0,20);
        //设置查询条件(所有条件均为可选条件)
        dto.setUserName("郑鑫");//掌柜姓名
        dto.setStartDate("2017-01-01");//开始时间
        dto.setRepayDate("2018-01-01");//结束时间
        dto.setProvince("火星省");//省
        dto.setCity("火星市");//市
        dto.setCountry("火星县");//县
        dto.setOrgCode("abc123");//机构号
        dto.setMobilePhone("13838790700");//掌柜手机号
        //调用OpcLimitQueryClient中的shareProfitQuery方法来触发查询
        //dto = client.shareProfitQuery(dto);
        //先识别查询是否成功
        if(dto!=null && dto.isSuccess){
            for(ShareProfitQueryDto currentDto : dto.getResultList()){
                //获取到所需数据
                System.out.println(currentDto);
            }
        }else{
            //打印错误信息
            System.out.println(dto.getErrorMessage());
        }
    }

    public String getErrorMessage() {
        synchronized (this){
            return Convert.toStr(errorMessage.toString());
        }
    }

    public void setErrorMessage(String errorMessage) {
        synchronized(this){
            StackTraceElement[] tracer = Thread.currentThread().getStackTrace();
            this.errorMessage
                    .append(Integer.toString(errorMessageNumber++))
                    .append(".")
                    .append(tracer[2].getClassName())
                    .append(":")
                    .append(errorMessage)
                    .append("\n");
        }
    }

    public Map<String,Object> toMap(){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field currentField : fields){
            currentField.setAccessible(true);
            Annotation transAnnotation = currentField.getAnnotation(Trans.class);
            if(transAnnotation != null){
                try{
                    resultMap.put(currentField.getName(),currentField.get(this));
                }catch (Exception e){
                    continue;
                }
            }
        }
        return resultMap;
    }

    @Override
    public String toString() {
        return "ShareProfitQueryDto{" +
                "isSuccess成功标识位=" + isSuccess +
                ", errorMessage错误信息='" + errorMessage + '\'' +
                ", firstResult数据头=" + firstResult +
                ", maxResult数据量=" + maxResult +
                ", totalSize真实条目数= " + totalSize +
                ", factSize本次查询条目数= " + factSize +
                ", startDate开始日期='" + startDate + '\'' +
                ", endDate结束日期='" + endDate + '\'' +
                ", firstBank一级分行='" + firstBank + '\'' +
                ", secondaryBanks二级分行='" + secondaryBanks + '\'' +
                ", userName掌柜姓名='" + userName + '\'' +
                ", mobilePhone掌柜手机号='" + mobilePhone + '\'' +
                ", orgCode掌柜机构号='" + orgCode + '\'' +
                ", userOnlyId掌柜ID='" + userOnlyId + '\'' +
                ", province=省'" + province + '\'' +
                ", city=市'" + city + '\'' +
                ", country=县'" + country + '\'' +
                ", loanNumber借据编号='" + loanNumber + '\'' +
                ", loanAmount=借款金额'" + loanAmount + '\'' +
                ", loanDate=借款日期'" + loanDate + '\'' +
                ", repayDate=还款日期'" + repayDate + '\'' +
                ", repayAmount=还款本金'" + repayAmount + '\'' +
                ", repayInterest=还款利息'" + repayInterest + '\'' +
                ", penaltyInterest=还款罚息'" + penaltyInterest + '\'' +
                ", postIncome=邮政收入'" + postIncome + '\'' +
                '}';
    }

    /***
     * 实例时应当指出查询范围
     * @param firstResult
     * @param maxResult
     */
    public ShareProfitQueryDto(long firstResult,long maxResult){
        this.firstResult = firstResult;
        this.maxResult = maxResult;
        resultList =  new ArrayList<ShareProfitQueryDto>();
        errorMessage = new StringBuffer(256);
        errorMessageNumber = 0;
    }

    public ShareProfitQueryDto(){
        super();
        resultList =  new ArrayList<ShareProfitQueryDto>();
        errorMessage = new StringBuffer(256);
        errorMessageNumber = 0;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
        if(StringUtils.isNotBlank(this.startDate) && this.startDate.length()==14){
            this.startDate+=" 00:00:00";
        }
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
        if(StringUtils.isNotBlank(this.endDate) && this.endDate.length()==14){
            this.endDate+=" 23:59:59";
        }
    }

    public long getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(long firstResult) {
        this.firstResult = firstResult;
    }

    public long getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(long maxResult) {
        this.maxResult = maxResult;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getFactSize() {
        return factSize;
    }

    public List<ShareProfitQueryDto> getResultList() {
        return resultList;
    }

    public void setResultList(List<ShareProfitQueryDto> resultList) {
        this.resultList = resultList;
        this.factSize = resultList.size();
    }

    public void transResultList(List<Object[]> resultList){
        this.resultList = new ArrayList<ShareProfitQueryDto>();
        for(Object[] objects : resultList){
            ShareProfitQueryDto dto = new ShareProfitQueryDto();
            dto.setProvince(Convert.toStr(objects[1]));//省
            dto.setCity(Convert.toStr(objects[2]));//市
            dto.setCountry(Convert.toStr(objects[3]));//县
            dto.setFirstBank(Convert.toStr(objects[4]));//一级分行
            dto.setSecondaryBanks(Convert.toStr(objects[5]));//二级分行
            dto.setUserName(Convert.toStr(objects[6]));//用户姓名
            dto.setUserOnlyId(Convert.toStr(objects[7]));//用户id
            dto.setLoanNumber(Convert.toStr(objects[8]));//借据编号
            dto.setLoanAmount(Convert.toStr(objects[9]));//借款金额
            dto.setLoanDate(Convert.toStr(objects[10]));//借款日期
            dto.setRepayDate(Convert.toStr(objects[11]));//还款日期
            dto.setRepayAmount(Convert.toStr(objects[12]));//还款本金
            dto.setRepayInterest(Convert.toStr(objects[13]));//还款利息
            dto.setPenaltyInterest(Convert.toStr(objects[14]));//还款罚息
            dto.setPostIncome(Convert.toStr(objects[15]));//邮政收入
            dto.setOrgCode(Convert.toStr(objects[20]));//用户机构号
            dto.setMobilePhone(Convert.toStr(objects[21]));//用户手机号
            this.resultList.add(dto);
        }
        this.factSize = this.resultList.size();
    }

    public String getUserOnlyId() {
        return userOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId;
    }

    public String getFirstBank() {
        return firstBank;
    }

    public void setFirstBank(String firstBank) {
        this.firstBank = firstBank;
    }

    public String getSecondaryBanks() {
        return secondaryBanks;
    }

    public void setSecondaryBanks(String secondaryBanks) {
        this.secondaryBanks = secondaryBanks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(String repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(String repayInterest) {
        this.repayInterest = repayInterest;
    }

    public String getPostIncome() {
        return postIncome;
    }

    public void setPostIncome(String postIncome) {
        this.postIncome = postIncome;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getPenaltyInterest() {
        return penaltyInterest;
    }

    public void setPenaltyInterest(String penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
    }
}
