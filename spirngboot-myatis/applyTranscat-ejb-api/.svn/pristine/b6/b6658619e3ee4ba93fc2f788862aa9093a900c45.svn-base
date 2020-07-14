package com.ule.uhj.Dcoffee.core.power;

import com.ule.uhj.Dcoffee.core.adapter.FilterBean;
import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Connector;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.VirtualTable;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
@Dcoffee(name="PowerSupply",scope = CoffeeRecipe.SINGLE)
public interface PowerSupply {
    public DataSet getDataSetById(String Id,List<Parameter> parameters);

    public Coffee boil(Coffee greenCoffee);

    public PowerSupply clone();

    public Connector getConnector(String Id);

    public List<Source> getSources(String Id,List<Parameter> parameterList);

    public VirtualTable getVirtualTable(Source source,List<Parameter> parameterList);

    public List<FilterBean> getFilter(String dataSetId);
}
