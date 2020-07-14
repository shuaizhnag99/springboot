package com.ule.uhj.Dcoffee.object.model.inner.virtualTable;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class DataBaseColumn<T> implements Column {
    private String columnName;

    private String alias;

    private Class columnType;

    private T columnValue;

    private VirtualTable columnParent;

    public DataBaseColumn(String name,T value,VirtualTable columnParent){
        this.columnName = name;
        this.columnValue = value;
        this.columnParent = columnParent;
        this.alias = new String();
    }

    public DataBaseColumn(){
        super();
    }

    @Override
    public T values() {
        return this.columnValue;
    }

    public Class getColumnType() {
        return columnType;
    }

    public void setColumnType(Class columnType) {
        this.columnType = columnType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public VirtualTable getColumnParent() {
        return columnParent;
    }
    public void setColumnParent(VirtualTable columnParent) {
        this.columnParent = columnParent;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj instanceof DataBaseColumn){
            DataBaseColumn dataBaseColumn = (DataBaseColumn)obj;
            return dataBaseColumn.getColumnName().equals(this.getColumnName()) &&
                    dataBaseColumn.getColumnParent().equals(this.getColumnParent());
        }
        return false;
    }


    @Override
    public int hashCode() {
        return this.getColumnName().hashCode()+this.getColumnType().hashCode()+this.getColumnParent().hashCode();
    }
}
