package com.ule.uhj.Dcoffee.tools.sqlUtil;

import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.source.DataBaseSource;
import com.ule.uhj.Dcoffee.object.model.inner.state.Capricious;
import com.ule.uhj.Dcoffee.object.model.inner.state.State;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseVirtualTable;

/**
 * Created by zhengxin on 2018/3/20.
 */
public class SqlApi implements Capricious{
    private SqlApiService sqlApiService;

    private SqlBufferTools sqlBufferTools;

    public SqlApi(SqlBufferTools sqlBufferTools){
        this.sqlBufferTools = sqlBufferTools;
        sqlApiService = new SqlApiService(sqlBufferTools);
    }

    public String build(DataBaseColumn[] interestingColumn){
        sqlApiService.build(interestingColumn);
        StringBuffer totalBuffer = new StringBuffer(512);
        totalBuffer.append(sqlBufferTools.getSelectBuffer().toString()).
                append(sqlBufferTools.getColumnBuffer().toString()).
                append(sqlBufferTools.getTablesBuffer().toString());

        if(sqlBufferTools.getLeftVirtualTableList().size()>0){
            totalBuffer.append(sqlBufferTools.getLeftBuffer().toString());
        }
        if(sqlBufferTools.getRightVirtualTableList().size()>0){
            totalBuffer.append(sqlBufferTools.getRightBuffer().toString());
        }
        if(sqlBufferTools.getParameterMap().size()>0){
            totalBuffer.append(sqlBufferTools.getWhereBuffer().toString());
        }
        return totalBuffer.toString();
    }

    public void addSource(DataBaseSource source){
        if(source.getRelation().equals(CoffeeRecipe.Source.DataBaseSource.Relation.MAIN)){
            sqlBufferTools.addMainTable((DataBaseVirtualTable) source.getVirtualTable());
        }
        if(source.getRelation().equals(CoffeeRecipe.Source.DataBaseSource.Relation.LEFT)){
            sqlBufferTools.addLeftTable((DataBaseVirtualTable) source.getVirtualTable());
        }
        if(source.getRelation().equals(CoffeeRecipe.Source.DataBaseSource.Relation.RIGHT)){
            sqlBufferTools.addRightTable((DataBaseVirtualTable) source.getVirtualTable());
        }
        sqlBufferTools.putSource(source);
    }

    @Override
    public State getState() {
        return sqlApiService.getState();
    }
}
