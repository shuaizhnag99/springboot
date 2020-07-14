package com.ule.uhj.Dcoffee.object.model.inner.virtualTable;

import com.ule.uhj.Dcoffee.tools.CollectionUtil;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class DataBaseVirtualTable implements VirtualTable {
    private String dataBaseType;

    private String tableName;

    private List<DataBaseColumn> columns;

    @Override
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public List<DataBaseColumn> getColumns() {
        return columns;
    }

    @Override
    public DataBaseColumn getColumn(String columnName) {
        return CollectionUtil.searchForList(new DataBaseColumn(columnName, null,this), this.columns, new CollectionUtil.Compartor<DataBaseColumn>() {
            @Override
            public boolean compare(DataBaseColumn obj1, DataBaseColumn obj2) {
                return obj1.getColumnName().equalsIgnoreCase(obj2.getColumnName());
            }
        });
    }

    public String getDataBaseType(){
        return this.dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj instanceof DataBaseVirtualTable){
            DataBaseVirtualTable dataBaseVirtualTable = (DataBaseVirtualTable)obj;
            return dataBaseVirtualTable.getTableName().equals(this.getTableName()) &&
                    dataBaseVirtualTable.getDataBaseType().equals(this.getDataBaseType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getTableName().hashCode()+this.getDataBaseType().hashCode();
    }

    public void setColumns(List<DataBaseColumn> columns) {
        this.columns = columns;
    }
}
