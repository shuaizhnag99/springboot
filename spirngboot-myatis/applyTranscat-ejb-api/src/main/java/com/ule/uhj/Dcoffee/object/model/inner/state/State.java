package com.ule.uhj.Dcoffee.object.model.inner.state;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface State{

    public State next();

    public State prev();

    public int status();

    public boolean equals(Object o);
    
    public int hashCode();
}
