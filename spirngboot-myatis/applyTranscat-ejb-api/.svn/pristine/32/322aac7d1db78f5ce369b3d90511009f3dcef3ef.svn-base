package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;
import com.ule.uhj.dto.zgd.TransferModel;

@Transfer(name = "多级机构信息模型")
public class MultilevelOrgunitDto extends CheckedTransModel{

    private static final long serialVersionUID = 3987103601136650592L;
    @ParameterDescripor(Index = "province", Descript = "省级机构信息模型")
    private OrgunitDto province;

    @ParameterDescripor(Index = "city", Descript = "市级机构信息模型")
    private OrgunitDto city;

    @ParameterDescripor(Index = "area", Descript = "县级机构信息模型")
    private OrgunitDto area;

    @ParameterDescripor(Index = "town", Descript = "支局级机构信息模型")
    private OrgunitDto town;

    public OrgunitDto getProvince() {
        return province;
    }

    public void setProvince(OrgunitDto province) {
        this.province = province;
    }

    public OrgunitDto getCity() {
        return city;
    }

    public void setCity(OrgunitDto city) {
        this.city = city;
    }

    public OrgunitDto getArea() {
        return area;
    }

    public void setArea(OrgunitDto area) {
        this.area = area;
    }

    public OrgunitDto getTown() {
        return town;
    }

    public void setTown(OrgunitDto town) {
        this.town = town;
    }
}
