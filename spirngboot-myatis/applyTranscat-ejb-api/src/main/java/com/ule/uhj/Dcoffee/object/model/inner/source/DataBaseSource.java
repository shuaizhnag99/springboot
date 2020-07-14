package com.ule.uhj.Dcoffee.object.model.inner.source;

import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.Column;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseVirtualTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/14.
 */
public class DataBaseSource extends DefaultSource {
    private String relation = null;
    private List<Parameter> parameterList = new ArrayList<Parameter>();

    public String getRelation(){
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public void addParameter(Parameter parameter) {
        this.parameterList.add(parameter);
    }

    @Override
    public List<Parameter> getParameterList() {
        return this.parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String getSourceType() {
        return CoffeeRecipe.Source.DataBaseSource.type;
    }

}
