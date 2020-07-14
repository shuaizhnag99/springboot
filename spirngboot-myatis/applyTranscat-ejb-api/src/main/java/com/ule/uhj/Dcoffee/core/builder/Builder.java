package com.ule.uhj.Dcoffee.core.builder;

import com.ule.uhj.Dcoffee.object.CoffeeObject;

/**
 * Created by zhengxin on 2018/3/23.
 */
public interface Builder extends CoffeeObject {
    public  <T extends CoffeeObject> T build(Object... args);
}
