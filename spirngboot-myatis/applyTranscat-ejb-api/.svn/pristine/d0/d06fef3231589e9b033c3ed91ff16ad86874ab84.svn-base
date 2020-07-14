package com.ule.uhj.Dcoffee.core.service;

import com.ule.uhj.Dcoffee.api.CoffeeMaker;
import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.annotation.Inject;
import com.ule.uhj.Dcoffee.core.power.PowerSupply;
import com.ule.uhj.Dcoffee.core.proxy.service.CoffeeServiceProxy;
import com.ule.uhj.Dcoffee.object.coupling.exception.MapDisconnectedException;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Connector;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Log;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;
import com.ule.uhj.Dcoffee.tools.inner.QuicklyCoffeeMahine;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
@Dcoffee(name="CoffeeMakerService",proxy = CoffeeServiceProxy.class)
public class CoffeeMakerService extends AbstractService implements CoffeeMaker{
    @Inject
    private PowerSupply powerSupply;
    private Coffee coffee;

    @Override
    public Coffee doService(CoffeeBean coffeeBean) {
        return make(coffeeBean);
    }

    @Override
    public Coffee make(CoffeeBean coffeeBean) {
        DataSet targetSet = powerSupply.getDataSetById(coffeeBean.getID(),coffeeBean.getParameters());
        if(targetSet==null){
            return QuicklyCoffeeMahine.makeBadCoffee(Log.warn("目标数据集不存在！").toString());
        }

        List<Source> sources = targetSet.getSources();
        Connector connector = targetSet.getConnector();

        if(sources!=null && sources.size()>0){
            try{
                coffee = connector.connect(sources);
                if(coffee!=null){
                    coffee.setCoffeeBean(coffeeBean);
                    coffee.setDataSet(targetSet);
                    coffee = powerSupply.boil(coffee);
                }
            }catch (MapDisconnectedException e1){
                return QuicklyCoffeeMahine.makeBadCoffee(e1);
            }
        }else{
            return QuicklyCoffeeMahine.makeBadCoffee(Log.warn("目标数据集未配置源！").toString());
        }
        return coffee;
    }
}
