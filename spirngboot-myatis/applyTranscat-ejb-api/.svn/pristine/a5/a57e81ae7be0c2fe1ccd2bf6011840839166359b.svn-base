package com.ule.uhj.Dcoffee.core.adapter;

import com.ule.uhj.Dcoffee.core.adapter.adapterUtil.AdapterUtilTools;
import com.ule.uhj.Dcoffee.object.CoffeeObject;

/**
 * Created by zhengxin on 2018/4/11.
 */
public class FilterBean {
    private String ID;

    private int level;

    private String dataSetId;

    private String type;

    private String executeCode;

    private byte[] executeCodeByte;

    private CoffeeFilter coffeeFilter;

    public CoffeeObject doFilter(CoffeeObject coffee){
        return coffeeFilter.doFilter(coffee,new AdapterUtilTools());
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExecuteCode() {
        return executeCode;
    }

    public void setExecuteCode(String executeCode) {
        this.executeCode = executeCode;
    }

    public void setCoffeeFilter(CoffeeFilter coffeeFilter) {
        this.coffeeFilter = coffeeFilter;
    }

    public byte[] getExecuteCodeByte() {
        return executeCodeByte;
    }

    public void setExecuteCodeByte(byte[] executeCodeByte) {
        this.executeCodeByte = executeCodeByte;
        this.executeCode = new String(executeCodeByte);
    }
}
