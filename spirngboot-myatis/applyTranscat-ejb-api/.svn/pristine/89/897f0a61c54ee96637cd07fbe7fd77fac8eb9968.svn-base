package com.ule.uhj.Dcoffee.object.model.inner.source;

import com.ule.uhj.Dcoffee.object.model.inner.connector.Slot;
import com.ule.uhj.Dcoffee.object.model.inner.state.State;
import com.ule.uhj.Dcoffee.object.model.inner.state.StateMachine;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.VirtualTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public abstract class AbstractSource implements Source {
    protected String ID;

    protected String sourceName;

    protected String sourceType;

    protected String descriptor;

    protected String createTime;

    protected String updateTime;

    protected int sourceLevel;

    protected VirtualTable virtualTable;

    protected String prepareSql;

    protected List<Slot> slots;

    public AbstractSource() {
        slots = new ArrayList<Slot>();
    }

    @Override
    public VirtualTable getVirtualTable() {
        return this.virtualTable;
    }

    @Override
    public boolean canBeConnect(Source source) {
        return getConnectSlots(source)!=null;
    }

    @Override
    public List<Slot> getConnectSlots(Source source) {
        if(slots==null || source==null || source.getSlots()==null){
            return  null;
        }
        List<Slot> intersection = new ArrayList<Slot>(Arrays.asList(new Slot[slots.size()]));
        Collections.copy(intersection,slots);
        intersection.retainAll(source.getSlots());
        return intersection.size()>0?intersection:null;
    }

    public int getSlotWeight(Source source){
        return 1;
    }

    @Override
    public List<Slot> getSlots() {
        return this.slots;
    }

    @Override
    public void addSlot(Slot slot) {
        this.slots.add(slot);
    }

    @Override
    public boolean equals(Object obj) {
        if(null!=obj&&(obj.getClass().equals(this.getClass()))){
            Source targetSource = (Source)obj;
            return targetSource.getID().equals(this.getID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getID().hashCode();
    }

    @Override
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public void setVirtualTable(VirtualTable table) {
        this.virtualTable = table;
    }

    @Override
    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getSourceLevel() {
        return sourceLevel;
    }

    public void setSourceLevel(int sourceLevel) {
        this.sourceLevel = sourceLevel;
    }

    public String getPrepareSql() {
        return prepareSql;
    }

    public void setPrepareSql(String prepareSql) {
        this.prepareSql = prepareSql;
    }
}
