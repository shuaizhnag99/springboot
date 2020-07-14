package com.ule.uhj.app.zgd.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxin on 2018/4/19.
 */
public class FieldObj {
    private String ID;
    private String fieldId;
    private String fieldName;
    private String fieldType;
    private String filedDesc;
    private BigDecimal fieldWidth;
    private String translate;
    private String bind;
    private String required;
    private String readOnly;
    private BigDecimal sort;

    public Map<String,Object> toJavaScriptMap(){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("id",fieldId);
        resultMap.put("type",fieldType);
        resultMap.put("desc",filedDesc);
        resultMap.put("width",fieldWidth.intValue());
        resultMap.put("translated",translate);
        resultMap.put("required",required!= null && required.equals("true") ? true : false);
        resultMap.put("readonly",readOnly!= null && readOnly.equals("true") ? true : false);
        return resultMap;
    }

    public String getID() {
        return ID;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFiledDesc() {
        return filedDesc;
    }

    public void setFiledDesc(String filedDesc) {
        this.filedDesc = filedDesc;
    }

    public BigDecimal getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(BigDecimal fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }
}
