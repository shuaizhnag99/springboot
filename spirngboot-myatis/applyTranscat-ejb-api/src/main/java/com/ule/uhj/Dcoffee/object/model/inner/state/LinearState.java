package com.ule.uhj.Dcoffee.object.model.inner.state;

/**
 * Created by zhengxin on 2018/3/19.
 */
public class LinearState implements State {
    private State nextState = null;

    private State prevState = null;

    private int value;

    public LinearState(State nextState, State prevState, int value) {
        this.value = value;
    }

    @Override
    public State next() {
        return nextState;
    }

    @Override
    public State prev() {
        return prevState;
    }

    @Override
    public int status() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof State){
            State target = (State)obj;
            return target.status() == (this.status());
        }
        return false;
    }
    @Override
    public int hashCode() {
    	int hash=this.status()+this.value;
    	
      return hash;
    }
}
