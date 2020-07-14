package com.ule.uhj.Dcoffee.tools;

import java.util.List;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class CollectionUtil {
    public static <T> T searchForList(T target,List<T> list,Compartor<T> compartor){
        if(compartor!=null){
            for(T t : list){
                if(compartor.compare(target,t)){
                    return t;
                }
            }
            return null;
        }
        for(T t : list){
            if(t.equals(target)){
                return t;
            }
        }
        return null;
    }

    public interface Compartor<T>{
        boolean compare(T obj1,T obj2);
    }
}
