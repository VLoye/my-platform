package com.v.Actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import com.v.Actuator.controller.PFController;
import com.v.common.DefaultILifecycleManager;
import com.v.common.ILifecycleManager;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.JarFilter;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author V
 * @Classname Actuator
 * @Description 服务调用上层接口
 * 曝露服务调用接口，所有服务从这里开始
 * 获取请求参数后，生成一个封装对象，判断请求对象是否运行于同一进程，是则进行内部通信（需要注册到控制器），否则进行进程间通信/网络通信。
 **/
@Data
public class Actuator extends DefaultILifecycleManager implements ILifecycleManager {
    private static final Logger logger = LoggerFactory.getLogger(Actuator.class);

    public static Map<String,App> apps;
    public static Map<String,File> appFiles;

    //预加载
    private Boolean preLoad;

    //程池
    private ThreadPoolExecutor servicesExecutorPool;

    //配置
    @Autowired
    private AcutatorConfig config;

    //是否需要一个结果集

    //需要一个控制器
    PFController controller;


    //void submitInvocation();


    static{
        apps = new ConcurrentHashMap<String,App>();
        appFiles = new ConcurrentHashMap<String,File>();
    }

    @Override
    public void init() {
        super.init();
        servicesExecutorPool = new ThreadPoolExecutor(
                config.getCoreSize(),
                config.getMaxSize(),
                config.getKeepAlive(),
                config.getTimeUnit(),
                new LinkedBlockingQueue<Runnable>(config.getQueueSize()));
        scanFiles(appFiles);
        if(config.isPreLoad())
            scanApps(apps,appFiles);
    }

    /**
     * @Description  扫描指定目录下的jar包，初始化appFilse映射
     * @Param [appFiles]
     * @return void
     * @Author V
    **/
    private void scanFiles(Map<String, File> appFiles) {
        File file = new File(getConfig().getAppAbsolutePath());
        if(file == null){
            logger.error("app path is null");
        }
        File[] filse = file.listFiles(new JarFilter());
        for (File f:filse){
            appFiles.put(f.getName(),f);
        }
    }

    /**
     * @Description  加载app文件，初始化apps映射,需判断是否开启预加载   todo 与数据库结合使用，记录应用加载情况。
     * @Param [apps, appFiles]
     * @return void
     * @Author V
    **/
    private void scanApps(Map<String, App> apps, Map<String, File> appFiles) {
        //// TODO: 2018/12/28
        Set<Map.Entry<String,File>> entries = appFiles.entrySet();
        for(Map.Entry entry: entries){
            apps.put((String)entry.getKey(),loadApp((File)entry.getValue()));
        }
    }

    private App loadApp(File file) {
        if (file == null){
            return null;
        }
        App app = new JarApp();

        return app;
    }


    //服务调用接口  todo 自定义返回结果对象  context有点类似于报文信息？？？但是比报文具有更多的属性和行为
    //public Result invoke(Context context)


    @Override
    public void start() {
        super.start();
    }

    @Override
    public void close() {
        //todo 资源释放
        super.close();

    }




}
