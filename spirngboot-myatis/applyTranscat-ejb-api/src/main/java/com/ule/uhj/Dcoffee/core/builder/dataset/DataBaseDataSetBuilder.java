package com.ule.uhj.Dcoffee.core.builder.dataset;

import com.ule.uhj.Dcoffee.core.adapter.FilterBean;
import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.builder.Builder;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Connector;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DefaultDataSet;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/21.
 */
@Dcoffee(name="DataBaseDataSetBuilder")
public class DataBaseDataSetBuilder implements Builder{
    @Override
    public DefaultDataSet build(Object... args) {
        if(args==null){
            return null;
        }
        ArrayList parameterrList = (ArrayList)args[0];
        DataSet dataSet = parameterrList.get(0)!=null ? (DataSet)parameterrList.get(0) : null;
        List<Source> sourceList = parameterrList.get(1)!=null ? (List<Source>)parameterrList.get(1) : null;
        Connector connector = parameterrList.get(2)!=null ? (Connector)parameterrList.get(2) : null;
        List<FilterBean> filterBeans = parameterrList.get(3)!=null ? (List<FilterBean>)parameterrList.get(3) : null;

        return build(dataSet,sourceList,connector,filterBeans);
    }

    public DefaultDataSet build(DataSet dataSet,List<Source> sourceList,Connector connector,List<FilterBean> filterBeans){
        DefaultDataSet targetSet = new DefaultDataSet();
        if(dataSet instanceof DefaultDataSet){
            targetSet.setId(dataSet.getId());
            targetSet.setName(((DefaultDataSet) dataSet).getName());
            targetSet.setDescriptor(((DefaultDataSet) dataSet).getDescriptor());
            targetSet.setCreateTime(((DefaultDataSet) dataSet).getCreateTime());
            targetSet.setUpdateTime(((DefaultDataSet) dataSet).getUpdateTime());
            targetSet.setSources(sourceList);
            targetSet.setConnector(connector);
        }
        targetSet.setFilters(filterBeans);
        return targetSet;
    }
}
