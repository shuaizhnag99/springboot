package com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans;

import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface CoffeeBean extends CoffeeObject{
    public void setPageSize(Long size);

    public void setPageCurr(Long curr);

    public Long getPageSize();

    public Long getPageCurr();

    public String getID();

    public void setID(String ID);

    public Parameter getParameter(String parameterName);

    public List<Parameter> getParameters();

    public void putParamer(Parameter parameter);
}
