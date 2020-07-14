package com.ule.uhj.Dcoffee.tools.sqlUtil;

import com.ule.uhj.Dcoffee.object.model.inner.source.DataBaseSource;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseVirtualTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 2018/3/20.
 */
@Deprecated
public class SqlBufferTools {
    private StringBuffer selectBuffer = new StringBuffer(256);

    private StringBuffer columnBuffer = new StringBuffer(256);

    private StringBuffer tablesBuffer = new StringBuffer(256);

    private StringBuffer leftBuffer = new StringBuffer(256);

    private StringBuffer rightBuffer = new StringBuffer(256);

    private StringBuffer whereBuffer = new StringBuffer(256);

    private Map<DataBaseVirtualTable,DataBaseSource> sourceMap = new HashMap<DataBaseVirtualTable, DataBaseSource>();

    private List<DataBaseVirtualTable> mainVirtualTableList = new ArrayList<DataBaseVirtualTable>();

    private List<DataBaseVirtualTable> leftVirtualTableList = new ArrayList<DataBaseVirtualTable>();

    private List<DataBaseVirtualTable> rightVirtualTableList = new ArrayList<DataBaseVirtualTable>();

    private Map<String,List<DataBaseVirtualTable>> allTableList = new HashMap<String, List<DataBaseVirtualTable>>();

    private Map<DataBaseColumn,Parameter> parameterMap = new HashMap<DataBaseColumn, Parameter>();

    public void initMemMap(){
        allTableList.put(SqlUtilConstant.MT,mainVirtualTableList);
        allTableList.put(SqlUtilConstant.LT,leftVirtualTableList);
        allTableList.put(SqlUtilConstant.RT,rightVirtualTableList);
    }

    public void addMainTable(DataBaseVirtualTable mainTable){
        mainVirtualTableList.add(mainTable);
    }

    public void addLeftTable(DataBaseVirtualTable leftTable){
        mainVirtualTableList.add(leftTable);
    }

    public void addRightTable(DataBaseVirtualTable rightTable){
        mainVirtualTableList.add(rightTable);
    }

    public void putSource(DataBaseSource source){
        sourceMap.put((DataBaseVirtualTable) source.getVirtualTable(), source);
    }

    public void append(String type,String str){
        if(type.equals(SqlUtilConstant.INIT)){
            selectBuffer.append(str);
        }

        if(type.equals(SqlUtilConstant.MT)){
            tablesBuffer.append(str);
        }

        if(type.equals(SqlUtilConstant.LT)){
            leftBuffer.append(str);
        }

        if(type.equals(SqlUtilConstant.RT)){
            rightBuffer.append(str);
        }

        if(type.equals(SqlUtilConstant.COLUMN)){
            columnBuffer.append(str);
        }

        if(type.equals(SqlUtilConstant.WHERE)){
            whereBuffer.append(str);
        }
    }

    public List<DataBaseVirtualTable> getMainVirtualTableList() {
        return mainVirtualTableList;
    }

    public void setMainVirtualTableList(List<DataBaseVirtualTable> mainVirtualTableList) {
        this.mainVirtualTableList = mainVirtualTableList;
    }

    public List<DataBaseVirtualTable> getLeftVirtualTableList() {
        return leftVirtualTableList;
    }

    public void setLeftVirtualTableList(List<DataBaseVirtualTable> leftVirtualTableList) {
        this.leftVirtualTableList = leftVirtualTableList;
    }

    public List<DataBaseVirtualTable> getRightVirtualTableList() {
        return rightVirtualTableList;
    }

    public void setRightVirtualTableList(List<DataBaseVirtualTable> rightVirtualTableList) {
        this.rightVirtualTableList = rightVirtualTableList;
    }

    public Map<String, List<DataBaseVirtualTable>> getAllTableList() {
        return allTableList;
    }

    public void setAllTableList(Map<String, List<DataBaseVirtualTable>> allTableList) {
        this.allTableList = allTableList;
    }

    public Map<DataBaseColumn, Parameter> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<DataBaseColumn, Parameter> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<DataBaseVirtualTable, DataBaseSource> getSourceMap() {
        return sourceMap;
    }

    public void setSourceMap(Map<DataBaseVirtualTable, DataBaseSource> sourceMap) {
        this.sourceMap = sourceMap;
    }

    public StringBuffer getWhereBuffer() {
        return whereBuffer;
    }

    public void setWhereBuffer(StringBuffer whereBuffer) {
        this.whereBuffer = whereBuffer;
    }

    public StringBuffer getRightBuffer() {
        return rightBuffer;
    }

    public void setRightBuffer(StringBuffer rightBuffer) {
        this.rightBuffer = rightBuffer;
    }

    public StringBuffer getLeftBuffer() {
        return leftBuffer;
    }

    public void setLeftBuffer(StringBuffer leftBuffer) {
        this.leftBuffer = leftBuffer;
    }

    public StringBuffer getTablesBuffer() {
        return tablesBuffer;
    }

    public void setTablesBuffer(StringBuffer tablesBuffer) {
        this.tablesBuffer = tablesBuffer;
    }

    public StringBuffer getColumnBuffer() {
        return columnBuffer;
    }

    public void setColumnBuffer(StringBuffer columnBuffer) {
        this.columnBuffer = columnBuffer;
    }

    public StringBuffer getSelectBuffer() {
        return selectBuffer;
    }

    public void setSelectBuffer(StringBuffer selectBuffer) {
        this.selectBuffer = selectBuffer;
    }
}
