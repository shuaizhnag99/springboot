package com.ule.uhj.Dcoffee.tools.inner;

import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;

/**
 * Created by zhengxin on 2018/3/13.
 */
public class QuicklyCoffeeMahine {
    public static Coffee makeBadCoffee(Throwable e){
        return null;
    }

    public static Coffee makeBadCoffee(String errorLog){
        return makeBadCoffee(getGroundCoffee(errorLog));
    }

    public static Throwable getGroundCoffee(String errorLog){
        return null;
    }
}
