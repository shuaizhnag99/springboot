package com.ule.uhj.Dcoffee.object.model.inner.source;

import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Slot;
import com.ule.uhj.Dcoffee.object.model.inner.state.Capricious;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.Column;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.VirtualTable;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/13.
 */
public interface Source extends CoffeeObject{
    public String getID();

    public VirtualTable getVirtualTable();

    public List<Slot> getSlots();

    public void addSlot(Slot slot);

    public boolean canBeConnect(Source source);

    public List<Slot> getConnectSlots(Source source);

    public int getSlotWeight(Source source);

    public List<Parameter> getParameterList();

    public void addParameter(Parameter parameter);

    public String getCreateTime();

    public String getUpdateTime();

    public String getSourceType();

    public String getSourceName();

    public void setVirtualTable(VirtualTable table);
}
