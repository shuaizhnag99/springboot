package com.ule.uhj.Dcoffee.object.model.inner.dataSet;

import com.ule.uhj.Dcoffee.core.adapter.CoffeeFilter;
import com.ule.uhj.Dcoffee.core.adapter.FilterBean;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Connector;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public abstract class AbstractDataSet implements DataSet{
    protected String ID;

    protected String descriptor;

    protected String name;

    protected String createTime;

    protected String updateTime;

    protected Connector connector;

    protected String support;

    protected List<Source> sources = new ArrayList<Source>();

    protected List<FilterBean> filters = new ArrayList<FilterBean>();

    @Override
    public Connector getConnector() {
        return connector;
    }

    @Override
    public List<Source> getSources() {
        return sources;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public List<FilterBean> getFilters() {
        return this.filters;
    }

    public void setFilters(List<FilterBean> filters) {
        this.filters = filters;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
