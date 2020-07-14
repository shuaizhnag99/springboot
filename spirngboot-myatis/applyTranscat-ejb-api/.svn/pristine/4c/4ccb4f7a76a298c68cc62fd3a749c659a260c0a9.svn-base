package com.ule.uhj.Dcoffee.object.model.inner.connector;

/**
 * Created by zhengxin on 2018/3/23.
 */
public class DefaultSlot implements Slot {
    private String ID;

    private String slotName;

    private String slotType;

    private String mapName;

    @Override
    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object obj) {
        if(null!=obj&&(obj.getClass().equals(this.getClass()) || this.getClass().isAssignableFrom(obj.getClass()))){
            DefaultSlot targetSlot = (DefaultSlot)obj;
            return targetSlot.getID().equals(this.getID()) && this.getMapName().equals(targetSlot.getMapName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    public DefaultSlot() {
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }
}
