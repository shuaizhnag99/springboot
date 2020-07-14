package com.ule.uhj.Dcoffee.object.model.inner.dataSet;

import com.ule.uhj.Dcoffee.core.adapter.CoffeeFilter;
import com.ule.uhj.Dcoffee.core.adapter.FilterBean;
import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Connector;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface DataSet extends CoffeeObject{
    public <T extends Connector> T getConnector();

    public String getId();

    public List<Source> getSources();

    public List<FilterBean> getFilters();
}
