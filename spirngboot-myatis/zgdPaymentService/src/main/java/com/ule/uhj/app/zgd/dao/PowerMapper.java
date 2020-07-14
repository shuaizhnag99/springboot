package com.ule.uhj.app.zgd.dao;

import com.ule.uhj.Dcoffee.core.adapter.FilterBean;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Slot;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.app.zgd.model.FieldObj;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/22.
 */
public interface PowerMapper {

    public String getDataSetDescriptorById(Map map);

    public List<DataSet> getDataSetById(Map id);

    public List<Source> getSourcesByDatasetId(String id);

    public List<Slot> getSlotsBySourceId(String id);

    public List<Parameter> getParameterMapsBySourceId(String id);

    public void keepWarm(Map coffeeMap);

    public List<FilterBean> getFilterByDataSetId(String dataSetId);

    public List<DataSet> getDataSetBySupport(Map support);

    public List<FieldObj> getFieldObjByDataSetId(Map parameter);
}
