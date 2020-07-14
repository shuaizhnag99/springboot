package com.ule.uhj.dto.zgd;

import com.ule.uhj.Annotation.ParameterDescripor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import java.util.Map;

public interface Descriptable {
    public default Map getParameterDescriptorTable(){
        Hashtable<String, String> PDT = new Hashtable<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            ParameterDescripor descripor = field.getDeclaredAnnotation(ParameterDescripor.class);
            if(descripor==null){
                continue;
            }
            PDT.put(descripor.Index(), descripor.Descript());
        }
        return PDT;
    }
}
