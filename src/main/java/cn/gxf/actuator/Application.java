package cn.gxf.actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import cn.gxf.actuator.loader.*;
import cn.gxf.common.LifeState;
import lombok.Data;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author V
 * @Classname Application
 * @Description
 **/
@Data
public class Application extends AbstractApp implements App {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    //    private static final Map<String, Method> MAPPING = new ConcurrentHashMap<String, Method>(128);
    private Map<Class<?>, IBean> IOC = new ConcurrentHashMap<Class<?>, IBean>(128);
    private String name;
    private Actuator actuator;
    private static String path;


    public Application(String name, String path, ClassLoader classLoader) {
        this.name = name;
        this.path = path;
        this.classLoader = classLoader;
    }

    @Override
    public void initialize() throws LifecycleException {
        super.initialize();
        try {
            loadAndScan();
        } catch (Exception e) {
            logger.error("App[{}] initialize fail,cause by: {}",this.getName(),e);
            throw new LifecycleException(e);
        }
        updateLifeState(LifeState.INITED);
    }

    @Override
    public void shutDown() throws LifecycleException {
        super.shutDown();
        try {
            ((URLClassLoader)classLoader).close();
        } catch (IOException e) {
            // nothing to do
        }
        servicesMap.clear();
        IOC.clear();
        updateLifeState(LifeState.CLOSED);
    }



    /**
     * @return void
     * @Description classloader加载jar，同时完成mapping的的映射 ，完成IOC的依赖注入。
     * @Param []
     * @Author V
     **/
    public void loadAndScan() throws Exception {
        JarFile jarFile = new JarFile(new File(path));
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement();
            String fileName = jarEntry.getName();
            if (fileName.endsWith(".class")) {
                fileName = fileName.replaceAll("/", ".");
                String appName = fileName.substring(0, fileName.lastIndexOf("."));
                Class c = classLoader.loadClass(appName);
                //mapping
                if (c.isAnnotationPresent(Api.class)) {
                    Api apiAnnotion = (Api) c.getAnnotation(Api.class);
                    String apiName = apiAnnotion.value();
                    if (StringUtils.isEmpty(apiName)) {
                        apiName = appName.substring(appName.lastIndexOf(".") + 1);
                        apiName = apiName.substring(0, 1).toLowerCase() + apiName.substring(1);
                    }
                    Method[] methods = c.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(ServiceApi.class)) {
                            ServiceApi serviceApiAnnotion = method.getAnnotation(ServiceApi.class);
                            String serviceName = serviceApiAnnotion.value();
                            if (StringUtils.isEmpty(serviceName)) {
                                serviceName = method.getName();
                            }
                            String serviceMapping = generateKey(apiName, serviceName);
                            servicesMap.put(serviceMapping, method);
                        }
                    }
                }
                //ioc
                if (c.isAnnotationPresent(Bean.class) || c.isAnnotationPresent(Api.class)) {
                    Bean beanAnnotion = (Bean) c.getAnnotation(Bean.class);
                    //// TODO: 2019/2/13 可重构 
                    if (beanAnnotion == null || beanAnnotion.type().equals(BeanType.Singleton)) {
                        IOC.put(c, new SingletonBean(this, c));
                    } else
                        IOC.put(c, new PrototypeBean(this, c));
                }

            }
        }
        //IOC 初始化（单例初始化，多例不做处理）
        Set<Map.Entry<Class<?>, IBean>> entrySet = IOC.entrySet();
        for (Map.Entry<Class<?>, IBean> entry : entrySet) {
            Class c = entry.getKey();
            entry.getValue().getInstance();
        }

        logger.info("App[{}] load succeed",name);
//        System.out.println();
    }

    private String generateKey(String apiName, String serviceName) {
        if (!apiName.startsWith("/")) {
            apiName = "/" + apiName;
        }
        if (!serviceName.startsWith("/")) {
            serviceName = "/" + serviceName;
        }
        return apiName + serviceName;
    }


    @Deprecated
    @Override
    public void load(String serviceKey) {

    }

    @Override
    public void loadAll() {

    }




    @Override
    public void unload(String serviceKey) {

    }

    @Override
    public void unloadAll() {

    }

}
