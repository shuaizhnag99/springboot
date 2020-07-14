package com.ule.uhj.Dcoffee.tools.sqlUtil;

/**
 * Created by zhengxin on 2018/3/19.
 */

import com.ule.uhj.Dcoffee.object.model.inner.source.DataBaseSource;
import com.ule.uhj.Dcoffee.object.model.inner.state.Capricious;
import com.ule.uhj.Dcoffee.object.model.inner.state.State;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;

/**
 * Created by zhengxin on 2018/3/16.
 */
public class SqlUtil implements Capricious {
    private SqlBufferTools sqlBufferTools;
    private SqlApi sqlApi;

    @Override
    public State getState() {
        return sqlApi.getState();
    }

    public SqlUtil(){
        sqlBufferTools = new SqlBufferTools();
        sqlBufferTools.initMemMap();
        sqlApi = new SqlApi(sqlBufferTools);
    }

    public void addSource(DataBaseSource source){
        sqlApi.addSource(source);
    }

    public String build(){
        return build(null);
    }

    public String build(DataBaseColumn[] interestingColumn){
        return sqlApi.build(interestingColumn);
    }
}
