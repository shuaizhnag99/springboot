package com.ule.uhj.Dcoffee.core.builder.source;

import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.builder.Builder;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Slot;
import com.ule.uhj.Dcoffee.object.model.inner.source.DataBaseSource;
import com.ule.uhj.Dcoffee.object.model.inner.source.DefaultSource;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/21.
 */
@Dcoffee(name="DataBaseSourceBuilder")
public class DataBaseSourceBuilder implements Builder{
    @Override
    public DataBaseSource build(Object... parameter) {
        if(parameter==null){
            return null;
        }

        ArrayList paramerList = (ArrayList)parameter[0];

        for(int i = 1;i<paramerList.size();i++){
            if(paramerList.get(i)==null){
                return null;
            }
        }
        try{
            return build((Source)paramerList.get(0),
                    (List<Parameter>)paramerList.get(1),
                    (List<Parameter>)paramerList.get(2),
                    (List<Slot>)paramerList.get(3));
        }catch (Exception e){
            return null;
        }
    }

    private DataBaseSource build(Source source,List<Parameter> parameterMapsList,List<Parameter> parameterValueList,List<Slot> sourceSlotMapsList){
        DataBaseSource result = new DataBaseSource();
        result.setID(source.getID());
        result.setCreateTime(source.getCreateTime());
        result.setUpdateTime(source.getUpdateTime());

        if(source instanceof DefaultSource){
            DefaultSource defaultSource = (DefaultSource)source;
            result.setDescriptor(defaultSource.getDescriptor());
            result.setSourceName(defaultSource.getSourceName());
            result.setSourceType(defaultSource.getSourceType());
            result.setSourceLevel(defaultSource.getSourceLevel());
            result.setRelation(Integer.toString(result.getSourceLevel()));
            result.setPrepareSql(defaultSource.getPrepareSql());
        }

        for(Parameter parameter : parameterMapsList){
            for(Parameter parameterValue : parameterValueList){
                if(parameterValue.equals(parameter)){
                    parameter.setParameterValue(parameterValue.getParameterValue());
                    break;
                }
            }
            result.addParameter(parameter);
        }


        for(Slot slot : sourceSlotMapsList){
            result.addSlot(slot);
        }

        return result;
    }

}
