package cn.gxf.controller;/**
 * Created by VLoye on 2019/3/16.
 */

import cn.gxf.MyPlatformServer;
import cn.gxf.actuator.App;
import cn.gxf.actuator.Application;
import cn.gxf.actuator.loader.ServiceApi;
import cn.gxf.ctrl.entity.AppInfo;
import cn.gxf.ctrl.entity.ServiceInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author V
 * @Classname ServiceController
 * @Description
 **/
@RestController
@RequestMapping("/service")
public class ServiceController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    @Autowired
    private MyPlatformServer server;

    @RequestMapping("/list")
    public List getApps() {
        List list = new ArrayList<AppInfo>();
        Iterator<App> iterator = server.getActuator().getApps().values().iterator();
        while (iterator.hasNext()) {
            Application application = (Application) iterator.next();
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(application.getName());
            List<ServiceInfo> services = new ArrayList<ServiceInfo>();
            Set<Map.Entry<String, Method>> methodSet = application.getServicesMap().entrySet();
            for (Map.Entry<String, Method> entry : methodSet) {
                ServiceInfo serviceInfo = new ServiceInfo();
                serviceInfo.setName(entry.getKey());
                serviceInfo.setDecription(entry.getValue().getAnnotation(ServiceApi.class).description());
                serviceInfo.setParamsType(entry.getValue().getParameterTypes());
                serviceInfo.setReturnType(entry.getValue().getReturnType());
                services.add(serviceInfo);
            }
            appInfo.setServices(services);
            list.add(appInfo);
        }
        return list;
    }



}
