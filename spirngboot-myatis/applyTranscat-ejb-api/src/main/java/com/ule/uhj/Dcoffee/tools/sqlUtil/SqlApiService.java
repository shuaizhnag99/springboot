package com.ule.uhj.Dcoffee.tools.sqlUtil;

import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.connector.DataBaseSlot;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Slot;
import com.ule.uhj.Dcoffee.object.model.inner.source.DataBaseSource;
import com.ule.uhj.Dcoffee.object.model.inner.state.Capricious;
import com.ule.uhj.Dcoffee.object.model.inner.state.PlanStateMachine;
import com.ule.uhj.Dcoffee.object.model.inner.state.State;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseVirtualTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/20.
 */
public class SqlApiService implements Capricious{
    private PlanStateMachine stateMachine;

    private SqlBufferTools sqlBufferTools;

    @Override
    public State getState() {
        return stateMachine.current();
    }

    public SqlApiService(SqlBufferTools sqlBufferTools) {
        stateMachine = new PlanStateMachine(PlanStateMachine.UtilPlane.SQL_PLANE);
        this.sqlBufferTools = sqlBufferTools;
    }

    private SqlApiService jointMainTable(DataBaseColumn[] interestingColumns){
        if(stateMachine.current().status()> CoffeeRecipe.SqlState.START.getValue()){
            return null;
        }
        sqlBufferTools.append(SqlUtilConstant.INIT,SqlUtilConstant.SELECT);
        for(DataBaseVirtualTable currentTable : sqlBufferTools.getMainVirtualTableList()){
            sqlBufferTools.append(SqlUtilConstant.MT,SqlUtilConstant.FROM + currentTable.getTableName() + SqlJointTools.trimeString(SqlJointTools.getSimpleTableName(sqlBufferTools.getMainVirtualTableList(), currentTable, SqlUtilConstant.MT)) +",");
            List<DataBaseColumn> columns = currentTable.getColumns();
            for(DataBaseColumn column : columns){
                if(interestingColumns!=null){
                    for(int i = 0;i<interestingColumns.length;i++){
                        if(interestingColumns[i].equals(column)){
                            sqlBufferTools.append(SqlUtilConstant.COLUMN,SqlJointTools.getSimpleTableName(sqlBufferTools.getMainVirtualTableList(), currentTable, SqlUtilConstant.MT)+SqlUtilConstant.PERIOD+column.getColumnName()+",");
                            break;
                        }
                    }
                }else{
                    sqlBufferTools.append(SqlUtilConstant.COLUMN,SqlJointTools.getSimpleTableName(sqlBufferTools.getMainVirtualTableList(), currentTable, SqlUtilConstant.MT)+SqlUtilConstant.PERIOD+column.getColumnName()+",");
                }
            }
            DataBaseSource currentSource = sqlBufferTools.getSourceMap().get(currentTable);
        }
        sqlBufferTools.setColumnBuffer(new StringBuffer(SqlJointTools.delComma(sqlBufferTools.getColumnBuffer())));
        sqlBufferTools.setTablesBuffer(new StringBuffer(SqlJointTools.delComma(sqlBufferTools.getTablesBuffer())));
        stateMachine.forward();
        return this;
    }

    public void build(DataBaseColumn[] interestingColumn){
    	SqlApiService sqlApiService=this.jointMainTable(interestingColumn);
    	if(null!= sqlApiService) {
    		sqlApiService= 	sqlApiService.leftJoin();
    		if(null!=sqlApiService) {
    			
    			sqlApiService.rightJoin().condition();
    		}
    	}
    }

    private SqlApiService leftJoin(){
        return join(SqlUtilConstant.LEFT);
    }

    private SqlApiService rightJoin(){
        return join(SqlUtilConstant.RIGHT);
    }

    private SqlApiService on(DataBaseColumn column1,DataBaseColumn column2){
        if(stateMachine.current().status()!=CoffeeRecipe.SqlState.ADDITION_TABLE.getValue()){
            return null;
        }

        DataBaseVirtualTable targetTable1=null,targetTable2=null;
        List<DataBaseVirtualTable> targetList1=null,targetList2=null;
        String targetKey1 = null ,targetKey2 = null;

        for(String key : sqlBufferTools.getAllTableList().keySet()){
            List<DataBaseVirtualTable> searchList = sqlBufferTools.getAllTableList().get(key);
            for(DataBaseVirtualTable currentDataBaseVirtualTable : searchList){
                for(DataBaseColumn currentColumn : currentDataBaseVirtualTable.getColumns()){
                    if(targetTable1!=null && targetTable2!=null){
                        break;
                    }
                    if(targetTable1==null && currentColumn.equals(column1)){
                        targetTable1 = currentDataBaseVirtualTable;
                        targetList1 = searchList;
                        targetKey1 = key;
                    }
                    if(targetTable2==null && currentColumn.equals(column2)){
                        targetTable2 = currentDataBaseVirtualTable;
                        targetList2 = searchList;
                        targetKey2 = key;
                    }
                }
                if(targetTable1!=null && targetTable2!=null){
                    break;
                }
            }
            if(targetTable1!=null && targetTable2!=null){
                if(stateMachine.getTag().equals(SqlUtilConstant.LEFT)){
                    sqlBufferTools.append(SqlUtilConstant.LT,SqlUtilConstant.ON +
                            SqlJointTools.getSimpleTableName(targetList1, targetTable1, targetKey1) +
                            SqlUtilConstant.PERIOD +
                            column1.getColumnName() +
                            SqlUtilConstant.EQUAL +
                            SqlJointTools.getSimpleTableName(targetList2, targetTable2, targetKey2) +
                            SqlUtilConstant.PERIOD +
                            column2.getColumnName() );
                }else if(stateMachine.getTag().equals(SqlUtilConstant.RIGHT)){
                    sqlBufferTools.append(SqlUtilConstant.RT,SqlUtilConstant.ON +
                            SqlJointTools.getSimpleTableName(targetList1, targetTable1, targetKey1) +
                            SqlUtilConstant.PERIOD +
                            column1.getColumnName() +
                            SqlUtilConstant.EQUAL +
                            SqlJointTools.getSimpleTableName(targetList2, targetTable2, targetKey2) +
                            SqlUtilConstant.PERIOD +
                            column2.getColumnName() );
                }
                stateMachine.negate();
                return this;
            }
        }
        return null;
    }

    private SqlApiService condition(){
        for(DataBaseColumn column : sqlBufferTools.getParameterMap().keySet()){
            condition(column,null);
        }
        return this;
    }

    private SqlApiService leftBrackets(){
        return condition(null,SqlUtilConstant.LBRACKETS);
    }
    private SqlApiService rightBrackets(){
        return condition(null,SqlUtilConstant.RBRACKETS);
    }

    private SqlApiService condition(DataBaseColumn column,String brackets){
        if(stateMachine.current().status()<CoffeeRecipe.SqlState.MAIN_TABLE.getValue() ||
                stateMachine.current().status()>CoffeeRecipe.SqlState.WHERE.getValue()){
            return null;
        }

        if(stateMachine.current().status() < CoffeeRecipe.SqlState.WHERE.getValue()){
            while (stateMachine.current().status() < CoffeeRecipe.SqlState.WHERE.getValue()){
                stateMachine.forward();
            }
            sqlBufferTools.append(SqlUtilConstant.WHERE,SqlUtilConstant.WHERE + " 1=1 ");
        }

        Parameter parameter = sqlBufferTools.getParameterMap().get(column);

        if(parameter!=null){

            if(brackets!=null){
                sqlBufferTools.append(SqlUtilConstant.WHERE,brackets.equals(SqlUtilConstant.LBRACKETS) ?
                        SqlUtilConstant.AND + SqlUtilConstant.LBRACKETS :
                        SqlUtilConstant.RBRACKETS );
                return this;
            }

            for(String key : sqlBufferTools.getAllTableList().keySet()){
                boolean isSearched = false;

                List<DataBaseVirtualTable> searchTableList = sqlBufferTools.getAllTableList().get(key);
                if(searchTableList==null || searchTableList.size()<=0){
                    continue;
                }
                for(DataBaseVirtualTable virtualTable : searchTableList){
                    for(DataBaseColumn currentColumn : virtualTable.getColumns()){
                        if(currentColumn.equals(column)){
                            isSearched = true;
                            sqlBufferTools.append(SqlUtilConstant.WHERE,parameter.getJointType() +
                                    SqlJointTools.getSimpleTableName(searchTableList, virtualTable, key) +
                                    SqlUtilConstant.PERIOD +
                                    currentColumn.getColumnName() +
                                    SqlJointTools.parameterValueToString(currentColumn, parameter));
                            break;
                        }
                    }
                    if(isSearched){
                        break;
                    }
                }
                if(isSearched){
                    break;
                }
            }
        }
        return this;
    }
    private SqlApiService join(String direct){
        if(stateMachine.current().status()!=CoffeeRecipe.SqlState.MAIN_TABLE.getValue()){
            return null;
        }
        List<DataBaseVirtualTable> yetVirtualTableList = new ArrayList<DataBaseVirtualTable>();
        List<DataBaseVirtualTable> targetVirtualTableList = null;
        String targetSimpleTableName = null;
        StringBuffer targetBuffer = null;

        if(direct.equals(SqlUtilConstant.LEFT)){
            targetSimpleTableName = SqlUtilConstant.LT;
            targetVirtualTableList = sqlBufferTools.getLeftVirtualTableList();
            targetBuffer = sqlBufferTools.getLeftBuffer();
        }else{
            targetSimpleTableName = SqlUtilConstant.RT;
            targetVirtualTableList = sqlBufferTools.getRightVirtualTableList();
            targetBuffer = sqlBufferTools.getRightBuffer();
        }

        int i = 0;
        while(yetVirtualTableList.size()!=targetVirtualTableList.size()){
            DataBaseVirtualTable joinTable = targetVirtualTableList.get(i);
            DataBaseSource joinTableSource = sqlBufferTools.getSourceMap().get(joinTable);
            DataBaseVirtualTable targetTable = null;
            DataBaseSource targetTableSource = null;
            if(yetVirtualTableList.size()<=0){
                for(DataBaseVirtualTable mainTable : sqlBufferTools.getMainVirtualTableList()){
                    if (sqlBufferTools.getSourceMap().get(mainTable).canBeConnect(joinTableSource)){
                        targetTable = mainTable;
                        break;
                    }
                }
            }else{
                for(DataBaseVirtualTable yetLeftVirtualTable : yetVirtualTableList){
                    if(sqlBufferTools.getSourceMap().get(yetLeftVirtualTable).canBeConnect(joinTableSource)){
                        targetTable = yetLeftVirtualTable;
                        break;
                    }
                }
            }
            targetTableSource = sqlBufferTools.getSourceMap().get(targetTable);
            targetBuffer.append(direct+joinTable.getTableName()+SqlJointTools.getSimpleTableName(targetVirtualTableList, joinTable, targetSimpleTableName));
            stateMachine.forward(direct);

            DataBaseColumn targetTableColumn = null,joinTableColumn =null;
            List<Slot> slots = joinTableSource.getConnectSlots(targetTableSource);
            for(Slot currentSlot : slots){
                DataBaseSlot currentDataBaseSlot = (DataBaseSlot) currentSlot;
                if(targetTableColumn == null && currentDataBaseSlot.getColumnParent().equals(targetTable)){
                    targetTableColumn = (DataBaseColumn)currentSlot;
                    continue;
                }
                if(joinTableColumn == null && currentDataBaseSlot.getColumnParent().equals(joinTable)){
                    joinTableColumn = (DataBaseColumn)currentSlot;
                    continue;
                }
            }
            on(joinTableColumn,targetTableColumn);
            yetVirtualTableList.add(joinTable);
            i++;
        }
        return this;
    }

    private SqlApiService orderBy(DataBaseColumn column,CoffeeRecipe.SortType sortType){
        return null;
    }

    private SqlApiService packPage(int pageSize,int currentPage){
        return null;
    }
}
