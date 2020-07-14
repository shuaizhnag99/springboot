package com.ule.uhj.Dcoffee.object.model.inner.connector;

import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.object.model.inner.source.DataBaseSource;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.tools.sqlUtil.SqlUtil;

import java.util.Stack;

/**
 * Created by zhengxin on 2018/3/16.
 */
@Dcoffee(name="DataBaseConnector")
public class DataBaseConnector extends AbstractConnector {

    @Override
    public Coffee connect(Stack<? extends Source> stack)  {
        if(stack.empty()){
            return null;
        }
        SqlUtil sqlUtil = new SqlUtil();
        while(!stack.empty()){
            DataBaseSource currentSource = (DataBaseSource)stack.pop();
            sqlUtil.addSource(currentSource);
        }
        sqlUtil.build();
        return null;
    }

}
