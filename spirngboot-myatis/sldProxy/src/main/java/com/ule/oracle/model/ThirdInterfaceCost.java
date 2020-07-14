package com.ule.oracle.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class ThirdInterfaceCost {
    /** 主键 */
    private Long id;
    /** 用户唯一标识 */
    private String userOnlyId;
    /** 收费来源，详见【sldJava - ChargeSourceEnum】 */
    private String chargeSource;
    /** 收费明细，详见【sldJava - InterfaceChargeEnum】 */
    private String chargeDetail;
    /** 单次调用花费的金额 */
    private BigDecimal amount;
    /** 备注 */
    private String remark;
    /** 创建时间 */
    private String createAt;
    /** 创建人 */
    private String createBy;
    /** 更新时间 */
    private String updateAt;
    /** 更新人 */
    private String updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserOnlyId() {
        return userOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        this.userOnlyId = userOnlyId;
    }

    public String getChargeSource() {
        return chargeSource;
    }

    public void setChargeSource(String chargeSource) {
        this.chargeSource = chargeSource;
    }

    public String getChargeDetail() {
        return chargeDetail;
    }

    public void setChargeDetail(String chargeDetail) {
        this.chargeDetail = chargeDetail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
