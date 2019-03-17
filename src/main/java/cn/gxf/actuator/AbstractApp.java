package cn.gxf.actuator;/**
 * Created by VLoye on 2018/12/27.
 */


import cn.gxf.common.DefaultILifecycleManager;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author V
 * @Classname AbstractApp
 * @Description
 **/
@Data
public abstract class AbstractApp extends DefaultILifecycleManager implements App {
    protected Map<String,Method> servicesMap = new ConcurrentHashMap<String, Method>();
    protected boolean isSuspend;

    protected ClassLoader classLoader;//加载器，一个应用对应一个，父加载器为appclassLoader

}
