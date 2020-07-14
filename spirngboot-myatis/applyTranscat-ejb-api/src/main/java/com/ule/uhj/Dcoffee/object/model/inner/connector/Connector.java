package com.ule.uhj.Dcoffee.object.model.inner.connector;

import com.ule.uhj.Dcoffee.object.coupling.exception.MapDisconnectedException;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;

import java.util.Collection;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface Connector {
    public Coffee connect(Collection<? extends Source> iterable) throws MapDisconnectedException;
}
