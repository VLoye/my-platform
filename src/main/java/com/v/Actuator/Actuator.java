package com.v.Actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import com.v.common.DefaultLifecycleManager;
import com.v.common.LifecycleManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Map;
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
public class Actuator extends DefaultLifecycleManager implements LifecycleManager {

    private static Map<String,App> apps;
    private static Map<String,File> appFiles;

    //预加载
    private Boolean preLoad;

    //需要一个线程池
    //Thread Pool;
    private ThreadPoolExecutor servicesExecutorPool;

    //配置
    //Config config;包含线程池
    @Autowired
    private AcutatorConfig config;


    static{
        apps = new ConcurrentHashMap<String,App>();
        appFiles = new ConcurrentHashMap<String,File>();
    }

    @Override
    public void init() {
        servicesExecutorPool = new ThreadPoolExecutor(
                config.getCoreSize(),
                config.getMaxSize(),
                config.getKeepAlive(),
                config.getTimeUnit(),
                new LinkedBlockingQueue<Runnable>(config.getQueueSize()));
        if(config.isPreLoad())
            scanApps(apps,appFiles);

    }

    /**
     * @Description  扫描、加载app文件，初始化apps、appsfiles映射  todo 与数据库结合使用，记录应用加载情况。
     * @Param [apps, appFiles]
     * @return void
     * @Author V
    **/
    private void scanApps(Map<String, App> apps, Map<String, File> appFiles) {
        //todo
    }

    //服务调用接口  todo 自定义返回结果对象  context有点类似于报文信息？？？但是比报文具有更多的属性和行为
    //public Result invoke(Context context)

    @Override
    public void close() {
        //todo 资源释放
    }


    //需要一个控制器
    //Controller controller;


    //void submitInvocation();


}
