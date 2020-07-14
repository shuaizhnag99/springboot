package com.ule.uhj.Dcoffee.object.model.inner.state;

import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/19.
 */
public class PlanStateMachine implements StateMachine {
    private List<State> plane = new ArrayList<State>();

    private State state = null;

    private String tag = null;

    public PlanStateMachine(UtilPlane utilPlane) {
        this.plane = Arrays.asList(utilPlane.getStates());
        this.state = this.plane.get(0);
    }

    public PlanStateMachine(State state, List<State> plane) {
        this.state = state;
        this.plane = plane;
    }

    @Override
    public void negate() {
        if(state!=null && state.prev()!=null){
            state = plane.get(plane.indexOf(state)).prev();
        }
        this.tag = null;
    }

    public void negate(String tag) {
        if(state!=null && state.prev()!=null){
            state = plane.get(plane.indexOf(state)).prev();
        }
        this.tag = tag;
    }

    @Override
    public void forward() {
        if(state!=null && state.next()!=null){
            state = plane.get(plane.indexOf(state)).next();
        }
        this.tag = null;
    }

    public void forward(String tag) {
        if(state!=null && state.next()!=null){
            state = plane.get(plane.indexOf(state)).next();
        }
        this.tag = tag;
    }

    @Override
    public State current() {
        return state;
    }

    public enum UtilPlane{
        SQL_PLANE(new State[]{
                new LinearState(null,null, CoffeeRecipe.SqlState.START.getValue()),
                new LinearState(null,null, CoffeeRecipe.SqlState.MAIN_TABLE.getValue()),
                new LinearState(null,null, CoffeeRecipe.SqlState.ADDITION_TABLE.getValue()),
                new LinearState(null,null, CoffeeRecipe.SqlState.WHERE.getValue()),
        });

        private State[] states;

        private UtilPlane(State[] states){
            this.states = states;
        }

        public State[] getStates() {
            return states;
        }

//        public void setStates(State[] states) {
//            this.states = states;
//        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}