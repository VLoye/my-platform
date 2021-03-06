package cn.gxf.proxy;/**
 * Created by VLoye on 2018/12/18.
 */

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

//    public static ServiceApi getServiceProxy(Context context){
//        return getProxy(ServiceApi,new ServiceInvokeHandler<ServiceApi>(ServiceApi));
//    }

    public static <T>  T getProxy(T t,InvocationHandler handler){
        T proxy = (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),t.getClass().getInterfaces(),handler);
        return proxy;
    }



}
