package com.ule.uhj.Dcoffee.core.builder.dataset;

import com.ule.uhj.Dcoffee.core.builder.AbstractBuilder;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Connector;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/23.
 */
public class DataSetBuilder extends AbstractBuilder {
    private static final DataSetBuilder mine = new DataSetBuilder();

    private DataSetBuilder(){
    }

    public static DataSet buildDataSet(DataSet dataSet,List sources,Connector connector,List filters ){
        String dataSetType = "DataBase";
        return (DataSet)mine.build(dataSetType+"DataSet",dataSet,sources,connector,filters);
    }
}
