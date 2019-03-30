package cn.gxf.controller;/**
 * Created by VLoye on 2019/3/29.
 */

import cn.gxf.MyPlatformServer;
import cn.gxf.actuator.Application;
import cn.gxf.actuator.loader.ServiceApi;
import cn.gxf.actuator.loader.ServiceClassLoader;
import cn.gxf.ctrl.entity.AppInfo;
import cn.gxf.ctrl.entity.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author V
 * @Classname AppController
 * @Description
 **/
@RestController
@RequestMapping("/app")
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private MyPlatformServer server;

    @RequestMapping("/get/{appName}")
    @ResponseBody
    public AppInfo getApp(@PathVariable(value = "appName") String appName) {
        Application application = (Application) server.getActuator().getApps().get(appName);
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
        return appInfo;
    }

    @RequestMapping("/deploy/{appName}")
    public String deploy(@PathVariable String appName) {
        Application application = (Application) server.getActuator().getApps().get(appName);
        if (application != null) {
            return "failure. Service has deployed";
        }
        File file = new File(server.getActuator().getConfig().getAppsAbsolutePath() + "\\" + appName + ".jar");
        if (file.exists()) {
            server.getActuator().getAppFiles().put(appName, file);
        }
        try {
            application = (Application) server.getActuator().loadApp(file);
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            return "Failure. Unknown exception.";
        }
        if (application != null) {
            server.getActuator().getApps().put(appName, application);
            try {
                application.loadAndScan();
            } catch (Exception e) {
                return "Failure. Unknown exception.";
            }
            return "Deploy application success.";
        }
        return "Failure. Application does not exist";
    }

    @RequestMapping("/uninstall/{appName}")
    public String unDeploy(@PathVariable String appName) {
        Application application = (Application) server.getActuator().getApps().get(appName);
        if (application == null) {
            return "failure. Service is not exist";
        }
        server.getActuator().getApps().remove(appName);
        server.getActuator().getAppFiles().remove(appName);

        //回收
        try {
            ((ServiceClassLoader)application.getClassLoader()).close();
        } catch (IOException e) {
//
            logger.error("Application release resource cause a exec: [{}]",e);
        }
        application=null;
        logger.info("Application [{}] uninstall success.",appName);
        return "Uninstall application " + appName + " success.";

    }

}
