package com.ule.uhj.Dcoffee.object.model.inner.virtualTable;

import com.ule.uhj.Dcoffee.object.CoffeeObject;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface Column extends CoffeeObject{
    public<T extends Object> T values();

    public String getColumnName();
}
