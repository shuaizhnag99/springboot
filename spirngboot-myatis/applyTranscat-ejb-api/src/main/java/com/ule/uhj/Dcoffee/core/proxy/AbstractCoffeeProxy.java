package com.ule.uhj.Dcoffee.core.proxy;

import java.lang.reflect.Method;

/**
 * Created by zhengxin on 2018/3/15.
 */
public abstract class AbstractCoffeeProxy implements CoffeeProxy {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
