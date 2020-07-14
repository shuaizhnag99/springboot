package com.ule.uhj.zgd.service;

import com.ule.uhj.Dcoffee.api.spring.SpringCoffeeMaker;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Created by zhengxin on 2018/3/21.
 */
@Service("WebCoffeeMaker")
public class WebCoffeeMaker extends SpringCoffeeMaker {
    private static Log log = LogFactory.getLog(WebCoffeeMaker.class);
    public WebCoffeeMaker() {
        super();
    }

    @Override
    public Coffee make(CoffeeBean coffeeBean) {
        log.info("WebCoffeeMake Start.CoffeeBean = "+coffeeBean);
        Coffee coffee = super.make(coffeeBean);
        log.info("WebCoffeeMake End.Coffee = "+coffee);
        return coffee;
    }


}
