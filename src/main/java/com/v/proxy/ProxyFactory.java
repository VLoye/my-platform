package com.v.proxy;/**
 * Created by VLoye on 2018/12/18.
 */

import com.v.Actuator.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author V
 * @Classname ProxyFactory
 * @Description 代理工厂
 **/
public class ProxyFactory {
    private static final Logger logger = LoggerFactory.getLogger(ProxyFactory.class);

    public static Service getServiceProxy(Service service){
        return getProxy(service,new ServiceInvokeHandler<Service>(service));
    }

    public static <T>  T getProxy(T t,InvocationHandler handler){
        T proxy = (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),t.getClass().getInterfaces(),handler);
        return proxy;
    }



}
