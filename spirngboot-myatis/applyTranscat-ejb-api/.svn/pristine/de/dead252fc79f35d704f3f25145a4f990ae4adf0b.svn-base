package com.ule.uhj.Dcoffee.tools.sqlUtil;

import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseColumn;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseVirtualTable;
import com.ule.uhj.Dcoffee.tools.StringUtils;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/20.
 */
public class SqlJointTools {

    public static String getSimpleTableName(List<DataBaseVirtualTable> tables,DataBaseVirtualTable table,String t){
        return t+tables.indexOf(table);
    }

    public static String delComma(String str){
        return str.substring(0,str.length()-1);
    }

    public static String delComma(StringBuffer str){
        return str.substring(0,str.length()-1);
    }

    public static String trimeString(String str){
        return " " + str + " ";
    }

    public static String getLinkStr(CoffeeRecipe.AssignmentType assignmentType){
        if(assignmentType.equals(CoffeeRecipe.AssignmentType.EQUAL)){
            return " = ";
        }else {
            return " LIKE ";
        }
    }

    public static String getAlias(DataBaseColumn column){
        if(StringUtils.isNotBlank(column.getAlias())){
            return SqlUtilConstant.AS + column.getAlias();
        }
        return "";
    }

    public static String parameterValueToString(DataBaseColumn column,Parameter parameter){
        String result;
        CoffeeRecipe.AssignmentType assignmentType = parameter.getAssignmentType();
        if(column.getColumnType().equals(String.class)){
            if(!assignmentType.equals(CoffeeRecipe.AssignmentType.EQUAL)){
                result = getLinkStr(assignmentType) +
                        markString(percentString(parameter.getParameterValue().toString(),assignmentType));
            }else{
                result = getLinkStr(assignmentType) + markString(parameter.getParameterValue().toString());
            }
        }else{
            result = getLinkStr(assignmentType) + parameter.getParameterValue().toString();
        }
        return result;
    }

    public static String markString(String str){
        return " '"+str+"' ";
    }

    public static String percentString(String str,CoffeeRecipe.AssignmentType assignmentType){
        if(assignmentType.equals(CoffeeRecipe.AssignmentType.LIKE_PREFIX)){
            return " %"+str+" ";
        }
        if(assignmentType.equals(CoffeeRecipe.AssignmentType.LIKE_SUFFIX)){
            return " " +str+"% ";
        }
        if(assignmentType.equals(CoffeeRecipe.AssignmentType.LIKE)){
            return " %"+str+"% ";
        }
        return str;
    }
}
