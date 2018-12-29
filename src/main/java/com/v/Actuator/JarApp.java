package com.v.Actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author V
 * @Classname JarApp
 * @Description
 **/
public class JarApp implements App {
    private static final Logger logger = LoggerFactory.getLogger(JarApp.class);

    private String name;
    private boolean isSuspend;
    private Map<String, Service> Services;
    private Actuator actuator;
    private String path;
    private ClassLoader classLoader;



    public String generateServiceKey(String funcName, String serviceName) {
        return funcName + "." + serviceName;
    }

    @Override
    public void load(String serviceKey) {
        String[] names = getClassAndMethodName(serviceKey);
        try {
            Class<?> clazz = classLoader.loadClass(names[0]);
            Object obj = clazz.newInstance();
            //// TODO: 2018/12/28
//            Method method = clazz.getMethod();

        } catch (ClassNotFoundException e) {
            logger.error("load class error:{}",e.getException());
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("class Instance fail:{}",e.getCause());
        }
    }

    @Override
    public void loadAll() {

    }

    //// TODO: 2018/12/28 need test
    private String[] getClassAndMethodName(String serviceKey) {
        int classNameLen = serviceKey.lastIndexOf(".");
        String className = serviceKey.substring(0,classNameLen);
        String methodName = serviceKey.substring(classNameLen+1,serviceKey.length()-classNameLen);
        return new String[]{className,methodName};
    }


    @Override
    public void unload(String serviceKey) {

    }

    @Override
    public void unloadAll() {

    }
}
