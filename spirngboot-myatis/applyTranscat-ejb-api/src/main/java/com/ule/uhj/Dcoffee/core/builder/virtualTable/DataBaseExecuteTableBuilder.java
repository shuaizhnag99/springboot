package com.ule.uhj.Dcoffee.core.builder.virtualTable;

//import EDU.oswego.cs.dl.util.concurrent.FJTask;
import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.builder.Builder;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.connector.DataBaseConnectorV2;
import com.ule.uhj.Dcoffee.object.model.inner.source.DataBaseSource;
import com.ule.uhj.Dcoffee.object.model.inner.source.DefaultSource;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseVirtualTable;
import com.ule.uhj.Dcoffee.tools.JDBCTypesUtils;
import com.ule.uhj.Dcoffee.tools.sqlUtil.SqlUtilConstant;
import com.ule.uhj.util.Convert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/21.
 */
@Dcoffee(name="DataBaseExecuteTableBuilder")
public class DataBaseExecuteTableBuilder implements Builder{
    private DataBaseVirtualTable table;
    private List<DataBaseColumn> columns = new ArrayList<DataBaseColumn>();

    @Override
    public DataBaseVirtualTable build(Object... args) {
        if(args!=null) {
            ArrayList parameterList = (ArrayList) args[0];
            Connection connection = (Connection)parameterList.get(0);
            DataBaseSource source = (DataBaseSource)parameterList.get(1);
            try{
                createTable(source, connection);
                return table;
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }

    public void createTable(Source source,Connection connection) throws SQLException{
        StringBuffer prepareSql = new StringBuffer(256);
        String prepareSqlData = ((DefaultSource) source).getPrepareSql();
        if(prepareSqlData.lastIndexOf(SqlUtilConstant.SQL_END)==prepareSqlData.length()-1){
            prepareSqlData = prepareSqlData.substring(0,prepareSqlData.length()-1);
        }
        prepareSql.append(SqlUtilConstant.ALL).
                append(SqlUtilConstant.FROM).
                append(SqlUtilConstant.LBRACKETS).
                append(prepareSqlData).
                append(SqlUtilConstant.RBRACKETS).
                append(SqlUtilConstant.WHERE).
                append(SqlUtilConstant.ROWNUM).
                append(SqlUtilConstant.LESS).
                append("2");


        ResultSet resultSet=null;
        List<Parameter> sortParameterList = new ArrayList<Parameter>();
        String prepareStatementString = DataBaseConnectorV2.checkParamer(prepareSql.toString(),source.getParameterList(),sortParameterList,"?");
       try( PreparedStatement preparedStatement = connection.prepareStatement(prepareStatementString);){
    	   
    	   for(int i =0;i<sortParameterList.size();i++){
    		   Parameter p = sortParameterList.get(i);
    		   if(p.getParameterTypeString().equals("String")){
    			   preparedStatement.setString(i+1, Convert.toStr(p.getParameterValue(),""));
    		   }
    	   }
    	   resultSet = preparedStatement.executeQuery();
    	   
    	   if(resultSet!=null){
    		   table = new DataBaseVirtualTable();
    		   table.setDataBaseType(CoffeeRecipe.VirtualTable.TYPE_DATABASE_DYNAMIC);
    		   table.setTableName(source.getSourceName());
    		   addColumn(resultSet);
    	   }
       }finally {
    	   if (null!=resultSet) {
    		   resultSet.close();
    	   }
       }

    }

    private void addColumn(ResultSet resultSet){
        try{
            ResultSetMetaData data = resultSet.getMetaData();
            resultSet.next();
            int columnSize = data.getColumnCount();
            for(int i = 0;i<columnSize;i++){
                DataBaseColumn currentColumn = new DataBaseColumn();
                String label = data.getColumnLabel(i+1);
                currentColumn.setColumnName(label);
                currentColumn.setColumnParent(table);
                int type = data.getColumnType(i + 1);
                if(JDBCTypesUtils.isJavaNumberType(type)){
                    currentColumn.setColumnType(JDBCTypesUtils.jdbcTypeToJavaType(type));
                }
                this.columns.add(currentColumn);
            }
        }catch (Exception e){
        }
        table.setColumns(this.columns);
    }
}