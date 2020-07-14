package com.ule.oracle.dao;


import com.ule.oracle.model.ThirdInterfaceCost;

public interface ThirdInterfaceCostMapper {

    int insertSelective(ThirdInterfaceCost thirdInterfaceCost);

    String selectUnitPriceByElementKey(String elementKey);

}
