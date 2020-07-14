package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;
import com.ule.uhj.dto.zgd.TransferModel;

@Transfer(name = "机构信息模型")
public class OrgunitDto extends CheckedTransModel implements Comparable{
    //机构等级-省级
    public static final Long LEVEL_PROVINCE = 4l;
    //机构等级-市级
    public static final Long LEVEL_CITY = 3l;
    //机构等级-县级
    public static final Long LEVEL_AREA = 2l;
    //机构等级-支局级
    public static final Long LEVEL_TOWN = 1l;
    private static final long serialVersionUID = 3239447791165331276L;

    @ParameterDescripor(Index = "org_code", Descript = "机构号")
    private String OrgCode;
    @ParameterDescripor(Index = "org_name", Descript = "机构名")
    private String OrgName;
    @ParameterDescripor(Index = "org_level", Descript = "机构等级")
    private Long OrgLevel;

    public String getOrgCode() {
        return OrgCode;
    }

    public void setOrgCode(String orgCode) {
        OrgCode = orgCode;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public Long getOrgLevel() {
        return OrgLevel;
    }

    public void setOrgLevel(Long orgLevel) {
        OrgLevel = orgLevel;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof OrgunitDto){
            return (int) (this.getOrgLevel()-((OrgunitDto) o).getOrgLevel());
        }
        return 0;
    }
}
