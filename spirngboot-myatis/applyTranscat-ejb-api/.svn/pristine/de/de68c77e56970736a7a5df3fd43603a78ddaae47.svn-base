package com.ule.uhj.Dcoffee.core.builder.source;

import com.ule.uhj.Dcoffee.core.builder.AbstractBuilder;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Slot;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/23.
 */
public class SourceBuilder extends AbstractBuilder{
    private static final SourceBuilder mine = new SourceBuilder();

    private SourceBuilder(){
    }

    public static Source buildSource(Source source,List<Parameter> parameterMapsList,List<Parameter> parameterValueList,List<Slot> sourceSlotMapsList){
        return (Source)mine.build(source.getSourceType()+"Source",source,parameterMapsList,parameterValueList,sourceSlotMapsList);
    }
}
