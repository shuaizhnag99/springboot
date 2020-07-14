package com.ule.uhj.Dcoffee.object.model.interaction.coffee;

import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface Coffee<T> extends CoffeeObject{
    public T getExtension();

    public void setExtension(T extension);

    public List<Parameter> getExeParameterList();

    public void setExeParameterList(List<Parameter> list);

    public String getID();

    public void setID(String ID);

    public DataSet getDataSet();

    public void setDataSet(DataSet dataSet);

    public String getSqlExe();

    public void setSqlExe(String sqlExe);

    public String getSqlExeCount();

    public void setSqlExeCount(String sqlExeCount);

    public CoffeeBean getCoffeeBean();

    public void setCoffeeBean(CoffeeBean coffeeBean);

    public Long getResultSize();

    public void setResultSize(Long resultSize);

    public String getCreateTime();

    public void setCreateTime(String createTime);

    public Map toMap();
}
