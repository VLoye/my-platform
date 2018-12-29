package com.v.Actuator.executor;/**
 * Created by VLoye on 2018/12/17.
 */

import com.v.Actuator.App;
import com.v.Actuator.Service;
import com.v.Actuator.loader.AppLoader;
import com.v.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author V
 * @Classname ServiceExcutor
 * @Description
 * 一个包含服务调用上下文的一个对象
 * 包含具体的方法信息，方法参数类型[]，调用对象，参数信息
 **/
public class ServiceExcutor implements Service {
    //todo 关于如何将jar包的class文件保存到一个map中，map<functionName-serviceName,Service>   签名？   true

    private static final Logger logger = LoggerFactory.getLogger(ServiceExcutor.class);
    private App app;

    private Method method = null;
    private Object delegate = null;
    private boolean isSuspend;//是否挂起

    private ServiceExcutorBulider bulider;//内部构造对象

    //私有
    private ServiceExcutor() {
    }

    public static ServiceExcutor.ServiceExcutorBulider newBuilder() {
        return new ServiceExcutorBulider();
    }
    public static ServiceExcutor.ServiceExcutorBulider newBuilder(String appName, String funcName, String serviceName) {
        return new ServiceExcutorBulider(appName,funcName,serviceName);
    }


    @Override
    public Object execute(Object[] params) {
        try {
            return method.invoke(delegate, params);
        } catch (Exception e) {
            logger.error("{} execute error.", method.getName());
        }
        return null;
    }


    @Override
    public Object execute(Object[] params, long times) {
        return null;
    }


    //build设计模式
    public static class ServiceExcutorBulider {
        private String appName;
        private String funcName;
        private String serviceName;

        public ServiceExcutorBulider() {
        }

        public ServiceExcutorBulider(String appName, String funcName, String serviceName) {
            this.appName = appName;
            this.funcName = funcName;
            this.serviceName = serviceName;
        }

        public ServiceExcutorBulider setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public ServiceExcutorBulider setFunctionName(String funcName) {
            this.funcName = funcName;
            return this;
        }

        public ServiceExcutorBulider setServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Service build() {
            ServiceExcutor serviceExcutor =  new ServiceExcutor();
            ClassLoader loader = AppLoader.loaders.get(appName);
            if (loader == null) {
                logger.error("{} is not load.", appName);
                return null;
            }

            Class<?> clazz = null;
            try {
//                clazz = Class.forName("Calculate",true,loader);还没加载进来
                clazz = loader.loadClass(funcName);
            } catch (ClassNotFoundException e) {
                logger.error("{} class not Found.",funcName);
                return null;
            }

            if (clazz == null) {
                return null;
            }

            Object obj = null;
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("{} class instance fail.",clazz.getName());
                return null;
            }
            serviceExcutor.delegate = obj;

            Method method = null;
            try {
                method = clazz.getDeclaredMethod(serviceName, Integer.class,Integer.class);
            } catch (NoSuchMethodException e) {
                logger.error("[{}] not such method.",method.getName());
                return null;
            }
            serviceExcutor.method = method;
            Service proxy = ProxyFactory.getServiceProxy(serviceExcutor);

            return proxy;
        }

    }

}
