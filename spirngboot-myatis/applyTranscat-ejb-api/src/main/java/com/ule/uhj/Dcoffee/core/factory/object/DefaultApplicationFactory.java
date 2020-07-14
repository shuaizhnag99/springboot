package com.ule.uhj.Dcoffee.core.factory.object;

import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.annotation.Inject;
import com.ule.uhj.Dcoffee.core.power.PowerSupply;
import com.ule.uhj.Dcoffee.core.proxy.CoffeeProxy;
import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class DefaultApplicationFactory implements ApplicationObjectFactory{
    private Map<String,Class> injectMap;
    private PowerSupply powerSupply;
    private Map<String,Object> instanceMap;

    @Override
    public void setCoffeeInstanceMap(Map<String,Object> coffeeInstanceMap){
        this.instanceMap = coffeeInstanceMap;
    }

    @Override
    public <T extends CoffeeObject> T getNewInstance(Class<? extends CoffeeObject> classz) throws InstantiationException,NoSuchMethodException,IllegalAccessException{
        Field[] fields = classz.getDeclaredFields();
        Dcoffee dcoffee = classz.getAnnotation(Dcoffee.class);
        T instance = null;
        try{
            if(dcoffee!=null && dcoffee.scope().equals(CoffeeRecipe.SINGLE)){
                instance = (T)instanceMap.get(dcoffee.name());
            }else{
                instance = (T)classz.newInstance();
            }
        }catch (Exception e){
            throw new NoSuchMethodException("实例对象没有默认构造器！");
        }

        if(injectMap==null || injectMap.size()<=0){
            return instance;
        }

        for(Field currentField : fields){
            currentField.setAccessible(true);

            if(currentField.getType().equals(PowerSupply.class) || PowerSupply.class.isAssignableFrom(currentField.getType())){
                currentField.set(instance,this.powerSupply.clone());
                continue;
            }

            Inject inject = currentField.getAnnotation(Inject.class);
            if(inject==null){
                continue;
            }
            Class injectClass = injectMap.get(currentField.getName());
            if(currentField.getType().equals(injectClass) || currentField.getType().isAssignableFrom(injectClass)){
                try{
                    Object injectClassInstance = null;
                    if(CoffeeObject.class.isAssignableFrom(injectClass) || CoffeeObject.class.equals(injectClass)){
                        injectClassInstance  = getNewInstance(injectClass);
                    }else{
                        injectClassInstance = injectClass.newInstance();
                    }
                    currentField.set(instance,injectClassInstance);
                }catch (Exception e){
                    continue;
                }
            }
        }

        if(dcoffee!=null && dcoffee.proxy()!=null && !dcoffee.proxy().equals(Object.class)){
            Class proxyClass = dcoffee.proxy();
            return (T)((CoffeeProxy)getNewInstance(proxyClass)).hijack(instance).getProxyInstance();
        }

        return instance;
    }

    @Override
    public void setPowerSupply(PowerSupply powerSupply) {
        this.powerSupply = powerSupply;
    }

    public void setInjectMap(Map<String, Class> injectMap) {
        this.injectMap = injectMap;
    }

    public static void main(String args[]){
        List<Long> list = new ArrayList<Long>();
        Object obj = (Object)list;
        System.out.println(Collection.class.isAssignableFrom(obj.getClass()));
    }
}
