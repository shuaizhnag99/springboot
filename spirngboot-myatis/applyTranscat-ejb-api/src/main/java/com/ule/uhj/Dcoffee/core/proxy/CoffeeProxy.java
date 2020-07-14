package com.ule.uhj.Dcoffee.core.proxy;

import com.ule.uhj.Dcoffee.object.CoffeeObject;

import java.lang.reflect.InvocationHandler;

/**
 * Created by zhengxin on 2018/3/15.
 */
public interface CoffeeProxy<T> extends InvocationHandler,CoffeeObject {
    public T getProxyInstance();

    public CoffeeProxy hijack(T t);
}
