package com.v.Actuator;


/**
 * Created by VLoye on 2018/12/17.
 */
public interface Service {

    Object execute(Object[] params);
    Object execute(Object[] params, long times);
}
