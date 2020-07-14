package com.ule.uhj.Dcoffee.core.proxy.service;

import com.ule.uhj.Dcoffee.core.adapter.FilterBean;
import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.annotation.Inject;
import com.ule.uhj.Dcoffee.core.power.PowerSupply;
import com.ule.uhj.Dcoffee.core.proxy.AbstractCoffeeProxy;
import com.ule.uhj.Dcoffee.core.service.CoffeeService;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/15.
 */
@Dcoffee(name = "CoffeeServiceProxy")
public class CoffeeServiceProxy<T extends CoffeeService> extends AbstractCoffeeProxy{
    @Inject
    private PowerSupply powerSupply;
    private CoffeeService proxyService;
    public CoffeeServiceProxy(){
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<FilterBean> suffixFilter = new ArrayList<FilterBean>();
        List<FilterBean> prefixFilter = new ArrayList<FilterBean>();
        for(Object obj : args){
            if(obj instanceof CoffeeBean){
                CoffeeBean targetContext = (CoffeeBean)obj;
                String dataSetID = targetContext.getID();
                List<FilterBean> filterBeanList = powerSupply.getFilter(dataSetID);
                if(filterBeanList!=null){
                    for(FilterBean currentFilter: filterBeanList){
                        if(currentFilter.getType().equals(CoffeeRecipe.Filter.TYPE_PREFIX)){
                            prefixFilter.add(currentFilter);
                        }else{
                            suffixFilter.add(currentFilter);
                        }
                    }
                }
            }
        }

        if(prefixFilter!=null && prefixFilter.size()>0){
            for(int i=0;i<args.length;i++){
                Object obj = args[i];
                if(obj instanceof CoffeeBean){
                    CoffeeBean targetContext = (CoffeeBean)obj;
                    for(FilterBean currentBean : prefixFilter){
                        targetContext = (CoffeeBean)currentBean.doFilter(targetContext);
                    }
                    args[i] = targetContext;
                }
            }
        }

        Object result = method.invoke(proxyService, args);

        if(suffixFilter!=null && suffixFilter.size()>0){
            if(result instanceof Coffee){
                Coffee targetContext = (Coffee)result;
                for(FilterBean currentBean : suffixFilter){
                    targetContext = (Coffee)currentBean.doFilter(targetContext);
                }
                result = targetContext;
            }
        }

        return result;
    }

    @Override
    public CoffeeService getProxyInstance() {
        return (CoffeeService)Proxy.newProxyInstance(CoffeeService.class.getClassLoader(),new Class<?>[]{CoffeeService.class},this);
    }

    @Override
    public CoffeeServiceProxy hijack(Object o) {
        if(o instanceof CoffeeService){
            this.proxyService = (CoffeeService)o;
        }
        return this;
    }
}
