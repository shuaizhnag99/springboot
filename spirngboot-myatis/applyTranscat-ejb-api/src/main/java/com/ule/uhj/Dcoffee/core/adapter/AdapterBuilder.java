package com.ule.uhj.Dcoffee.core.adapter;

import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.StandardBean;

import javax.script.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/4/11.
 */
public class AdapterBuilder {
    //@todo 待实现
    public static void initFunction(){
    }

    public static List<FilterBean> build(List<FilterBean> filterBeanList){
        List<FilterBean> resultList = new ArrayList<FilterBean>();
        for(FilterBean filterBean : filterBeanList){
            ScriptEngine engine = new ScriptEngineManager().getEngineByName(CoffeeRecipe.FILTER_SCRIPT_JS);
            StringBuffer jsBuffer = new StringBuffer(512);
            jsBuffer.append("function doFilter(coffee,util){").
                    append(filterBean.getExecuteCode()).
                    append("return coffee;}");
            try{
                engine.eval(jsBuffer.toString());
            }catch (ScriptException e){
                continue;
            }
            Invocable invocable = (Invocable)engine;
            CoffeeFilter currentFilter = invocable.getInterface(CoffeeFilter.class);
            filterBean.setCoffeeFilter(currentFilter);
            resultList.add(filterBean);
        }
        return resultList;
    }
}
