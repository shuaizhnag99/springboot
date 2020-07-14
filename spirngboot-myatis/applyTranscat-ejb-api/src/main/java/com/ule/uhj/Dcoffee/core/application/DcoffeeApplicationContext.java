package com.ule.uhj.Dcoffee.core.application;

import com.ule.uhj.Dcoffee.core.adapter.AdapterBuilder;
import com.ule.uhj.Dcoffee.core.adapter.adapterUtil.AdapterUtilTools;
import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.annotation.Inject;
import com.ule.uhj.Dcoffee.core.factory.object.ApplicationObjectFactory;
import com.ule.uhj.Dcoffee.core.factory.object.DefaultApplicationFactory;
import com.ule.uhj.Dcoffee.core.power.PowerSupply;
import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.coupling.exception.DuplicateDefinitionException;
import com.ule.uhj.Dcoffee.tools.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by zhengxin on 2018/3/14.
 */
public class DcoffeeApplicationContext {
	private static Log log = LogFactory.getLog(DcoffeeApplicationContext.class);
    private static final ThreadLocal<ApplicationObjectFactory> god = new ThreadLocal<ApplicationObjectFactory>();
    private PowerSupply powerSupply;
    private static Map<String, Class> coffeeMaps = new ConcurrentHashMap<String, Class>();
    private static Map<String,Object> coffeeInstanceMap = new ConcurrentHashMap<String, Object>();
    private static final DcoffeeApplicationContext dcoffeeApplicationContext = new DcoffeeApplicationContext();

    static {
        if(coffeeMaps.size()<=0){
            try{
                AdapterBuilder.initFunction();
                Class.forName(CoffeeRecipe.packageName+".tools.inner.CoffeeScanner");
            }catch (Exception e){
            	log.error(e);
            }
        }
    }

    private DcoffeeApplicationContext(){
    }

    public static DcoffeeApplicationContext getDcoffeeApplicationContext(){
        return dcoffeeApplicationContext;
    }

    public static void addBeans(String coffeeName,Class classz,Object instance) throws Exception{
        if(classz==null){
            return;
        }
        Dcoffee dcoffee = (Dcoffee)classz.getAnnotation(Dcoffee.class);
        if(dcoffee==null){
            return;
        }
//        if(dcoffeeApplicationContext.coffeeMaps.get(coffeeName)!=null){
//            throw new DuplicateDefinitionException(String.format("%s被重复定义！",coffeeName));
//        }
        coffeeName = StringUtils.isNotBlank(dcoffee.name()) ? dcoffee.name() : coffeeName;
        dcoffeeApplicationContext.coffeeMaps.put(coffeeName,classz);

        if(dcoffee.scope().equals(CoffeeRecipe.SINGLE)
                && instance!=null
                && (instance.getClass().equals(classz) || classz.isAssignableFrom(instance.getClass())) ){
            coffeeInstanceMap.put(coffeeName,instance);
        }
    }

    public CoffeeObject getCoffeeObject(String coffeeName,Class<? extends CoffeeObject> classz) throws Exception{
        Class target = this.coffeeMaps.get(coffeeName);

        if(target==null || (!target.equals(classz) && !(classz.isAssignableFrom(target))) ){
            throw new ClassNotFoundException("映射表内无指定的Object.");
        }

        if(powerSupply == null){
            throw new NullPointerException("PowerSupply 尚未就绪.");
        }

        ApplicationObjectFactory factory = god.get();
        if(factory==null){
            factory = new DefaultApplicationFactory();
            god.set(factory);
        }
        factory.setPowerSupply(powerSupply.clone());
        factory.setInjectMap(checkInjectObject(target));
        factory.setCoffeeInstanceMap(coffeeInstanceMap);

        CoffeeObject targetInstance = factory.getNewInstance(target);

        return targetInstance;
    }

    private Map<String,Class> checkInjectObject(Class<? extends CoffeeObject> classz) throws ClassNotFoundException{
        Field[] fields = classz.getDeclaredFields();
        Map<String,Class> injectMaps = new HashMap<String, Class>();
        for(Field field : fields){
            field.setAccessible(true);
            Inject inject = field.getAnnotation(Inject.class);
            if(inject==null){
                continue;
            }

            String dependName = inject.value();
            Class dependClass =null;

            if(dependName!=null && dependName.length()>0){
                dependClass = this.coffeeMaps.get(dependName);
            }

            if(dependClass==null){
                Class targetClass = field.getType();
                for(String classKey : coffeeMaps.keySet()){
                    Class currentCoffeeClassz = coffeeMaps.get(classKey);
                    if(currentCoffeeClassz.equals(targetClass) || targetClass.isAssignableFrom(currentCoffeeClassz)){
                        dependClass = coffeeMaps.get(classKey);
                    }
                }
            }

            if(dependClass==null){
                throw new ClassNotFoundException(String.format("%s 依赖的 %s 并未注册！",classz.getName(),dependName));
            }

            if(dependClass.getAnnotation(Dcoffee.class)!=null && CoffeeObject.class.isAssignableFrom(dependClass)){
                injectMaps.putAll(checkInjectObject(dependClass));
            }

            injectMaps.put(dependName,dependClass);
        }
        return injectMaps;
    }

    public void setPowerSupply(PowerSupply powerSupply) {
        this.powerSupply = powerSupply;
        try{
            addBeans(PowerSupply.class.getSimpleName(),PowerSupply.class,powerSupply);
        }catch (Exception e){
        }
    }
}
