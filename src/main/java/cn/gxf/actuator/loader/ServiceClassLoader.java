package cn.gxf.actuator.loader;/**
 * Created by VLoye on 2019/2/2.
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author V
 * @Classname ServiceClassLoader
 * @Description 服务加载类，以应用为单位加载应用内的服务，维护应用内的所有服务。服务：加载器=1：1
 **/
public class ServiceClassLoader extends URLClassLoader {
    private static ConcurrentMap<String, Class<?>> cacheClass = new ConcurrentHashMap<String, Class<?>>();
    private String path;

    public ServiceClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class c = super.findClass(name);
        cacheClass.put(name,c);
        return c;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class c = cacheClass.get(name);
        if (c != null) {
            return c;
        }
        return super.loadClass(name);
    }
}
