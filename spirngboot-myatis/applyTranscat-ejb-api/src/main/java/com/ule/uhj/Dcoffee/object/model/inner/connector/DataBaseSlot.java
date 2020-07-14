package com.ule.uhj.Dcoffee.object.model.inner.connector;

import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.Column;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.VirtualTable;

/**
 * Created by zhengxin on 2018/3/14.
 */
public class DataBaseSlot extends DataBaseColumn implements Slot {
    private String ID;
    private DataBaseColumn column;

    @Override
    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object obj) {
        if(null!=obj&&obj.getClass().equals(this.getClass())){
            Slot targetSlot = (Slot)obj;
            return targetSlot.getID().equals(this.getID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    public DataBaseSlot(String name, Object value, VirtualTable columnParent) {
        this.column = new DataBaseColumn(name, value, columnParent);
    }

    public DataBaseSlot() {
    }

    @Override
    public Object values() {
        return column.values();
    }

    @Override
    public Class getColumnType() {
        return column.getColumnType();
    }

    @Override
    public void setColumnType(Class columnType) {
        column.setColumnType(columnType);
    }

    @Override
    public String getAlias() {
        return column.getAlias();
    }

    @Override
    public void setAlias(String alias) {
        column.setAlias(alias);
    }

    @Override
    public VirtualTable getColumnParent() {
        return column.getColumnParent();
    }

    @Override
    public void setColumnParent(VirtualTable columnParent) {
        column.setColumnParent(columnParent);
    }

    @Override
    public String getColumnName() {
        return column.getColumnName();
    }

    @Override
    public void setColumnName(String columnName) {
        column.setColumnName(columnName);
    }
}
