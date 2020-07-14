package com.ule.uhj.Dcoffee.object.model.inner.virtualTable;

import com.ule.uhj.Dcoffee.object.CoffeeObject;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface VirtualTable extends CoffeeObject{
    public String getTableName();

    public List<? extends Column> getColumns();

    public Column getColumn(String columnName);
}
