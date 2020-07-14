package com.ule.uhj.Dcoffee.core.builder;

import com.ule.uhj.Dcoffee.core.application.DcoffeeApplicationContext;
import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.util.Convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by zhengxin on 2018/3/23.
 */
public abstract class AbstractBuilder implements Builder {
	private static Log log = LogFactory.getLog(AbstractBuilder.class);

    @Override
    public CoffeeObject build(Object... args) {
        String buildType = Convert.toStr(args[0]);
        try{
            Builder targetBuilder =
                    (Builder) DcoffeeApplicationContext.getDcoffeeApplicationContext().getCoffeeObject(buildType+"Builder",Builder.class);
            List<Object> parametersArray = new ArrayList<Object>();
            for(int i=1;i<args.length;i++){
                parametersArray.add(args[i]);
            }
            return targetBuilder.build(parametersArray);
        }catch (Exception e){
        	log.error(e);
        }
        return null;
    }
}
