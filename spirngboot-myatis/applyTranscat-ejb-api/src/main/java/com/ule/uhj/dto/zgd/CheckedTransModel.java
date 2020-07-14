package com.ule.uhj.dto.zgd;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public abstract class CheckedTransModel implements TransferModel, Serializable{
    protected Map<String, String> _checkResultMap = new Hashtable<>();

    @Override
    public void setModelCheckResult(String index, String result) {
        _checkResultMap.put(index, result);
    }

    @Override
    public String getModelcheckResult(String index) {
        String result = _checkResultMap.get(index);
        return result!=null ? result : "未校验";
    }
}
