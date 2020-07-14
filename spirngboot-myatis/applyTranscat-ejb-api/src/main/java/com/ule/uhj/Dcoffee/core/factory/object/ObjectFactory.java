package com.ule.uhj.Dcoffee.core.factory.object;

import com.ule.uhj.Dcoffee.core.factory.Factory;
import com.ule.uhj.Dcoffee.object.CoffeeObject;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface ObjectFactory extends Factory{
    public <T extends CoffeeObject> T getNewInstance(Class<? extends CoffeeObject> classz) throws Exception;
}
