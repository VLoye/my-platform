package com.v.Actuator;/**
 * Created by VLoye on 2018/12/27.
 */


import java.util.Map;

/**
 * @author V
 * @Classname AbstractApp
 * @Description
 **/
public abstract class AbstractApp implements App {
    private Map<String,Service> servicesMap;

    private boolean isSuspend;

    private ClassLoader classLoader;//父加载器，一个应用对应一个，

}
