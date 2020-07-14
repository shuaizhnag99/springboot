package com.ule.uhj.Dcoffee.tools.sqlUtil.analysis.Labels.impl;

import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.tools.sqlUtil.analysis.Labels.SimpleLable;
import com.ule.uhj.util.Convert;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

public class IFLabel extends SimpleLable{
    private static final String TEST = "test";
    @Override
    public String execute(List<Parameter> parameterList) throws Exception {
        String testStr = Convert.toStr(this.defined.get(TEST),"");
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
        for(Parameter parameter : parameterList){
            scriptEngine.put(parameter.getParameterName(),parameter.getParameterValue());
        }

        boolean isHit = Boolean.parseBoolean(Convert.toStr(scriptEngine.eval(testStr)));
        if(isHit){
            return this.innerContent;
        }else{
            return "";
        }
    }
}
