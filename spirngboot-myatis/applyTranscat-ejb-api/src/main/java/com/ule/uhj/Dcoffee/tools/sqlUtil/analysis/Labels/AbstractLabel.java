package com.ule.uhj.Dcoffee.tools.sqlUtil.analysis.Labels;

import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.tools.sqlUtil.Label;

import java.util.*;

public abstract class AbstractLabel implements Label {
    protected String name;
    protected String innerContent;
    protected String tag;
    protected Map<String,Object> defined = new HashMap<String, Object>();

    public AbstractLabel() {
        tag = UUID.randomUUID().toString();
    }

    @Override
    public void copy(Label that) {
        if(that instanceof AbstractLabel){
            AbstractLabel obj = (AbstractLabel)that;
            obj.name = this.name;
            obj.innerContent = this.innerContent;
            obj.defined = this.defined;
            obj.tag = this.tag;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addDefined(String defined,Object value){
        this.defined.put(defined,value);
    }

    public String getInnerContent() {
        return innerContent;
    }

    public void setInnerContent(String innerContent) {
        this.innerContent = innerContent;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public boolean verify(String code) {
        return false;
    }

    @Override
    public String execute(List<Parameter> parameterList) throws Exception {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AbstractLabel that = (AbstractLabel) object;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
