package com.ule.uhj.app.zgd.model;

public class SaleActRegion extends SaleActRegionKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_J_SALE_ACT_REGION.REGION_LEVEL
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    private String regionLevel;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_J_SALE_ACT_REGION.REGION_LEVEL
     *
     * @return the value of T_J_SALE_ACT_REGION.REGION_LEVEL
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public String getRegionLevel() {
        return regionLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_J_SALE_ACT_REGION.REGION_LEVEL
     *
     * @param regionLevel the value for T_J_SALE_ACT_REGION.REGION_LEVEL
     *
     * @mbggenerated Thu May 10 15:20:08 CST 2018
     */
    public void setRegionLevel(String regionLevel) {
        this.regionLevel = regionLevel == null ? null : regionLevel.trim();
    }
}