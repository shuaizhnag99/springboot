package com.ule.uhj.Dcoffee.object.model.inner.util;

import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class Parameter<T> {
    private String parameterName;

    private Class<?> parameterType;

    private String parameterTypeString;

    private Object parameterValue;

    private CoffeeRecipe.AssignmentType assignmentType;

    private String jointType;

    public Parameter(){
        super();
    }

    public Parameter(String parameterName,T parameterValue){
        this.parameterType = parameterValue != null ? parameterValue.getClass() : null;
        this.parameterTypeString = parameterType!=null ? this.parameterType.getSimpleName() : "";
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getParameterTypeString() {
        return parameterTypeString;
    }

    public void setParameterTypeString(String parameterTypeString) {
        this.parameterTypeString = parameterTypeString;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public T getParameterValue() {
        return (T)parameterValue;
    }

    public String getJointType() {
        return jointType;
    }

    public void setJointType(String jointType) {
        this.jointType = jointType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if (obj instanceof Parameter) {
        	Parameter targetParameter = (Parameter)obj;
        	
        	return this.parameterName.equals(targetParameter.getParameterName());
        	
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getParameterName().hashCode()+this.getParameterType().hashCode();
    }

    public CoffeeRecipe.AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(CoffeeRecipe.AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public void setParameterValue(Object parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "parameterName='" + parameterName + '\'' +
                ", parameterType=" + parameterType +
                ", parameterTypeString='" + parameterTypeString + '\'' +
                ", parameterValue=" + parameterValue +
                ", assignmentType=" + assignmentType +
                ", jointType='" + jointType + '\'' +
                '}';
    }
}