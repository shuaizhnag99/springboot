package com.ule.uhj.Dcoffee.api.spring;

import com.ule.uhj.Dcoffee.api.CoffeeMaker;
import com.ule.uhj.Dcoffee.core.application.DcoffeeApplicationContext;
import com.ule.uhj.Dcoffee.core.service.CoffeeService;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;

/**
 * Created by zhengxin on 2018/3/21.
 */
public class SpringCoffeeMaker implements CoffeeMaker {
    private CoffeeService coffeeService;

    public SpringCoffeeMaker(){
        try{
            this.coffeeService = (CoffeeService)DcoffeeApplicationContext.getDcoffeeApplicationContext().getCoffeeObject("CoffeeMakerService", CoffeeService.class);
        }catch (Exception e){
        }
    }

    @Override
    public Coffee make(CoffeeBean coffeeBean) {
        return (Coffee)this.doService(coffeeBean);
    }

    @Override
    public Object doService(CoffeeBean coffeeBean) {
        if(null==coffeeService){
            try{
                this.coffeeService = (CoffeeService)DcoffeeApplicationContext.getDcoffeeApplicationContext().getCoffeeObject("CoffeeMakerService", CoffeeService.class);
            }catch (Exception e){
            	
            }
        }
        if(null!=coffeeService) {
        	
        	return coffeeService.doService(coffeeBean);
        }else {
        	return null;
        }
    }
}
