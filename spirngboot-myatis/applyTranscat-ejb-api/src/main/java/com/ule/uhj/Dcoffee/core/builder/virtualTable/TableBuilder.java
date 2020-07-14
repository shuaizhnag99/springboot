package com.ule.uhj.Dcoffee.core.builder.virtualTable;

import com.ule.uhj.Dcoffee.core.builder.AbstractBuilder;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.VirtualTable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/28.
 */
public class TableBuilder extends AbstractBuilder{

    private static final TableBuilder tableBuilder = new TableBuilder();

    public static VirtualTable buildTable(Object... args){
        if(args!=null){
            List parameterList = Arrays.asList(args);
            String tableType = (String)parameterList.get(0);
            if(tableType.equals(CoffeeRecipe.VirtualTable.TYPE_DATABASE_DYNAMIC)){
                if(parameterList.size()>=3 &&
                        parameterList.get(1) instanceof Connection &&
                        parameterList.get(2) instanceof Source){
                    return (VirtualTable)tableBuilder.build(tableType,parameterList.get(1),parameterList.get(2));
                }
            }
        }
        return null;
    }

}
