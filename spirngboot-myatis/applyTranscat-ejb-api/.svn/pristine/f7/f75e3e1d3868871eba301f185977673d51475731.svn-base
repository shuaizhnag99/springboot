package com.ule.uhj.Dcoffee.object.model.interaction.coffee;

import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;
import com.ule.uhj.Dcoffee.tools.inner.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/13.
 */
public abstract class AbstractCoffee<T> implements Coffee<T> {
    private String ID;

    private String sqlExeCount;

    private DataSet dataSet;

    private String sqlExe;

    private CoffeeBean coffeeBean;

    private Long resultSize;

    private String createTime;

    private List<Parameter> exeParameterList;

    private T extension;

    @Override
    public String getSqlExeCount() {
        return sqlExeCount;
    }

    @Override
    public void setSqlExeCount(String sqlExeCount) {
        this.sqlExeCount = sqlExeCount;
    }

    @Override
    public void setExtension(T extension) {
        this.extension = extension;
    }

    @Override
    public T getExtension() {
        return this.extension;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getSqlExe() {
        return sqlExe;
    }

    public void setSqlExe(String sqlExe) {
        this.sqlExe = sqlExe;
    }

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }

    public void setCoffeeBean(CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;
    }

    public Long getResultSize() {
        return resultSize;
    }

    public void setResultSize(Long resultSize) {
        this.resultSize = resultSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public List<Parameter> getExeParameterList() {
        return exeParameterList;
    }

    @Override
    public void setExeParameterList(List<Parameter> exeParameterList) {
        this.exeParameterList = exeParameterList;
    }

    @Override
    public Map toMap() {
        Map result = new HashMap();
        result.put("ID",this.ID);
        result.put("DATASET_ID",this.dataSet.getId());
        result.put("SQL_EXE",this.sqlExe!=null?this.sqlExe.getBytes():null);
        result.put("SUCCESS",this.extension!=null?true:false);
        result.put("COFFEE_BEAN",this.coffeeBean!=null?this.coffeeBean.toString().getBytes():null);
        result.put("RESULT_SIZE",this.resultSize);
        result.put("CREATE_TIME", DateUtil.currentTime());
        return result;
    }
}
