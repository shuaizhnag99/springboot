package com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans;

import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.tools.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class StandardBean implements CoffeeBean{
    private String dataSetId;

    private Long pageSize;

    private Long pageCurr;

    private List<Parameter> parameters = new ArrayList<Parameter>();

    public StandardBean(){
        super();
    }

    public Long getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageCurr() {
        return pageCurr;
    }

    @Override
    public void setPageCurr(Long pageCurr) {
        this.pageCurr = pageCurr;
    }

    @Override
    public String getID() {
        return dataSetId;
    }

    @Override
    public void setID(String ID) {
        this.dataSetId = ID;
    }

    @Override
    public Parameter getParameter(String parameterName) {
        return CollectionUtil.searchForList(new Parameter(parameterName, null), this.parameters, new CollectionUtil.Compartor<Parameter>() {
            @Override
            public boolean compare(Parameter obj1, Parameter obj2) {
                return obj1.getParameterName().equals(obj2.getParameterName());
            }
        });
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public void putParamer(Parameter parameter) {
        if(!parameters.contains(parameter)){
            parameters.add(parameter);
        }
    }

    @Override
    public String toString() {
        return "StandardBean{" +
                "dataSetId='" + dataSetId + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
