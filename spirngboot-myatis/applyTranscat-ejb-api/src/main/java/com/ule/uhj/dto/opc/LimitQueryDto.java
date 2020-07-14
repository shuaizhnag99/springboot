package com.ule.uhj.dto.opc;

import com.ule.uhj.dto.zgd.ZgdQueryDto;
import com.ule.uhj.util.Convert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LimitQueryDto implements Serializable {
    private static final long serialVersionUID = -7018308608L;
    //开始日期
    public static final String CONDITION_TIME_BEGIN = "orderTimeBegin";
    //结束日期
    public static final String CONDITION_TIME_END = "orderTimeEnd";
    //掌柜姓名
    public static final String CONDITION_USER_NAME = "userName";
    //审核状态
    public static final String CONDITION_STATUS = "firstStatus";
    //机构号
    public static final String CONDITION_ORG = "orgCode";
    //累计支用次数
    public static final String CONDITION_LOAN_COUNT = "loanCount";
    //省
    public static final String CONDITION_PROVINCE = "province";
    //市
    public static final String CONDITION_CITY = "city";
    //区
    public static final String CONDITION_AREA = "area";
    //支局
    public static final String CONDITION_BRANCH = "branch";
    //是否地推
    public static final String CONDITION_SFDT = "sfdt";
    //申请开始时间
    public static final String CONDITION_APPLY_TIME_BEGIN = "applyTimeBegin";
    //申请结束时间
    public static final String CONDITION_APPLY_TIME_END = "applyTimeEnd";
    //进件开始时间
    public static final String CONDITION_IMPORT_TIME_BEGIN = "importTimeBegin";
    //进件结束时间
    public static final String CONDITION_IMPORT_TIME_END = "importTimeEnd";
    //渠道号
    public static final String CONDITION_CHANNELCODE = "channelCode";
    //授信次数
    public static final String CONDITION_CREDIT_COUNT = "creditCount";

    //响应结果
    private List<ZgdQueryDto> resultList = new ArrayList<ZgdQueryDto>();;

    //各类条件
    private Map<String,String> conditions = new HashMap<String, String>();

    //数据头
    private long firstResult;

    //数据尾
    private long maxResult;

    //总条目数
    private long totalSize;

    //本次查询实际条目数
    private long factSize;

    //处理成功标识
    private boolean success = false;

    public LimitQueryDto(){
        super();
    }

    /***
     * 便于老版本代码快速迭代
     * @param otherDto
     */
    public LimitQueryDto(ZgdQueryDto otherDto){
        conditions.put(CONDITION_TIME_BEGIN,otherDto.getStartDate());
        conditions.put(CONDITION_TIME_END,otherDto.getEndDate());
        conditions.put(CONDITION_LOAN_COUNT,otherDto.getTotalPayCount());
        conditions.put(CONDITION_PROVINCE,otherDto.getProvince());
        conditions.put(CONDITION_CITY,otherDto.getCity());
        conditions.put(CONDITION_AREA,otherDto.getArea());
        conditions.put(CONDITION_STATUS,otherDto.getFirstStatus());
        conditions.put(CONDITION_ORG,otherDto.getOrgCode());
        conditions.put(CONDITION_USER_NAME,otherDto.getUserName());
        conditions.put(CONDITION_SFDT,otherDto.getSfdt());
        conditions.put(CONDITION_APPLY_TIME_BEGIN,otherDto.getApplyTimeBegin());
        conditions.put(CONDITION_APPLY_TIME_END,otherDto.getApplyTimeEnd());
        conditions.put(CONDITION_IMPORT_TIME_BEGIN,otherDto.getImportTimeBegin());
        conditions.put(CONDITION_IMPORT_TIME_END,otherDto.getImportTimeEnd());
        conditions.put(CONDITION_BRANCH,otherDto.getTown());
        conditions.put(CONDITION_CHANNELCODE,otherDto.getChannelCode());
        conditions.put(CONDITION_CREDIT_COUNT,otherDto.getCreditCount());
        for(String key : conditions.keySet()){
            String value = conditions.get(key);
            if(value == null && "".equals(conditions.get(key))){
                conditions.remove(key);
            }
        }
        this.firstResult = otherDto.getFirstResult();
        this.maxResult = otherDto.getMaxResults();
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = new HashMap<String, String>();
        for(String key : conditions.keySet()){
            String value = conditions.get(key);
            if(value!=null && !"".equals(conditions.get(key))){
                this.conditions.put(key,value);
            }
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public void setFactSize(long factSize) {
        this.factSize = factSize;
    }

    @Override
    public String toString() {
        StringBuffer cacheKey = new StringBuffer(conditions.size()*5);
        for(String key : conditions.keySet()){
            cacheKey.append(key+":"+conditions.get(key)+"-");
        }
        return cacheKey.substring(0,cacheKey.length()-1);
    }

    public List<ZgdQueryDto> getResultList() {
        return resultList;
    }

    public void setResultList(List<Object[]> resultList) {
        for(Object[] obj : resultList){
            ZgdQueryDto zgdQueryDto = new ZgdQueryDto();
            zgdQueryDto.setFirstStatus(Convert.toStr(obj[0]));//状态
            zgdQueryDto.setUserOnlyId(Convert.toStr(obj[1]));//id
            zgdQueryDto.setOrgCode(Convert.toStr(obj[2]));//机构号
            zgdQueryDto.setProvince(Convert.toStr(obj[3]));//省
            zgdQueryDto.setCity(Convert.toStr(obj[4]));//市
            zgdQueryDto.setArea(Convert.toStr(obj[5]));//县
            zgdQueryDto.setUserName(Convert.toStr(obj[6]));//用户名
            zgdQueryDto.setLastUpdateTime(Convert.toStr(obj[7]));//最后更新时间
            zgdQueryDto.setBalance(Convert.toStr(obj[8]));//授信额度
            zgdQueryDto.setAvailBalance(Convert.toStr(obj[9]));//可用额度
            zgdQueryDto.setTotalPayCount(Convert.toStr(obj[10]));//总支用次数
            zgdQueryDto.setTotalPayAmount(Convert.toStr(obj[11]));//总支用金额
            zgdQueryDto.setBalanceDeadline(Convert.toStr(obj[12]));//到期日
            zgdQueryDto.setMobile(Convert.toStr(obj[13]));//type=1的手机
            zgdQueryDto.setYouZhuShouMobile(Convert.toStr(obj[14]));//type=2的手机
            zgdQueryDto.setLoanOfficerName(Convert.toStr(obj[16]));//信贷员名字
            zgdQueryDto.setLoanOfficerOrg(Convert.toStr(obj[17]));//信贷员机构号
            zgdQueryDto.setLoanOfficerPhone(Convert.toStr(obj[18]));//信贷员手机
            zgdQueryDto.setSfdt(Convert.toStr(obj[19]));//是否绑定地推
            zgdQueryDto.setApplyTimeBegin(Convert.toStr(obj[20]));//申请时间
            zgdQueryDto.setImportTimeBegin(Convert.toStr(obj[21]));//进件时间
            zgdQueryDto.setTown(Convert.toStr(obj[22]));//支局
            zgdQueryDto.setChannelCode(Convert.toStr(obj[23]));//渠道号
            zgdQueryDto.setTownOrgCode(Convert.toStr(obj[24]));//支局机构号
            zgdQueryDto.setCreditCount(Convert.toStr(obj[25]));//授信次数
            zgdQueryDto.setLimitType(Convert.toStr(obj[26]));//贷款类型
            zgdQueryDto.setZgdName(Convert.toStr(obj[27]));//type=1的掌柜贷地推姓名
            zgdQueryDto.setYzsName(Convert.toStr(obj[28]));//type=2的邮助手地推姓名
            
            this.resultList.add(zgdQueryDto);
        }
    }
}
