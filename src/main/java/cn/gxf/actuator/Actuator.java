package cn.gxf.actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import cn.gxf.ctrl.controller.PFController;
import cn.gxf.actuator.executor.ThreadNameFactory;
import cn.gxf.core.Bus;
import cn.gxf.core.Device;
import cn.gxf.core.Message;
import cn.gxf.core.Request;
import cn.gxf.actuator.executor.ServiceInvocationTask;
import cn.gxf.actuator.loader.ServiceClassLoader;
import cn.gxf.common.DefaultILifecycleManager;
import cn.gxf.common.ILifecycleManager;
import cn.gxf.common.LifeState;
import cn.gxf.monitor.BusyThreadState;
import cn.gxf.monitor.ThreadPoolState;
import lombok.Data;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.misc.JarFilter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author V
 * @Classname actuator
 * @Description 服务调用上层接口
 * 暴露服务调用接口，所有服务从这里开始
 * 获取请求参数后，生成一个封装对象，判断请求对象是否运行于同一进程，是则进行内部通信（需要注册到控制器），否则进行进程间通信/网络通信。
 **/
@Data
@Component
public class Actuator extends DefaultILifecycleManager implements ILifecycleManager, Device {
    private static final Logger logger = LoggerFactory.getLogger(Actuator.class);
    private static final long timeOut = 30 * 1000;
    private Map<String, App> apps = new ConcurrentHashMap<String, App>();
    private Map<String, File> appFiles = new ConcurrentHashMap<String, File>();

    //预加载
    private Boolean preLoad;

    //程池
    private ThreadPoolExecutor servicesExecutorPool;

    //配置
    private ActuatorConfig config;

    //是否需要一个结果集

    //需要一个控制器
    PFController controller;


    //void submitInvocation();


    public Actuator(ActuatorConfig config) {
        this.config = config;
    }

    @Override
    public void initialize() throws LifecycleException {
        super.initialize();
        servicesExecutorPool = new ServiceThreadPool(
                config.getCoreSize(),
                config.getMaxSize(),
                config.getKeepAlive(),
                config.getTimeUnit(),
                new LinkedBlockingQueue<Runnable>(config.getQueueSize()),
                new ThreadNameFactory("ServiceInvocationThread"));
        scanFiles();
        if (config.isPreLoad())
            scanApps();

        Iterator<App> iterator = apps.values().iterator();
        while (iterator.hasNext()) {
            Application application = (Application) iterator.next();
            application.initialize();
        }
        updateLifeState(LifeState.INITED);
    }

    /**
     * @return void
     * @Description 扫描指定目录下的jar包，初始化appFilse映射
     * @Param [appFiles]
     * @Author V
     **/
    private void scanFiles() {
        File file = new File(config.getAppsAbsolutePath());
        if (!file.exists()) {
            logger.error("app path is exist. please check the config.");
            throw new RuntimeException("app path is exist. please check the config.");
        }
        File[] files = file.listFiles(new JarFilter());
        for (File f : files) {
            appFiles.put(f.getName().replaceAll(".jar",""), f);
        }
    }

    /**
     * @return void
     * @Description 加载app文件，初始化apps映射,需判断是否开启预加载
     * @Param [apps, appFiles]
     * @Author V
     **/
    private void scanApps() {
        Set<Map.Entry<String, File>> entries = appFiles.entrySet();
        for (Map.Entry entry : entries) {
            try {
                apps.put((String) entry.getKey(), loadApp((File) entry.getValue()));
            } catch (MalformedURLException e) {
                logger.error("app[{}] load fail,cause by: {}", entry.getKey(), e);
            }
        }
        logger.info("Platform scanApps success, size[{}]", apps.size());
    }

    private App loadApp(File file) throws MalformedURLException {
        if (file == null) {
            return null;
        }
        String path = file.getPath();
        URL url = new URL("file:\\" + path);
        ClassLoader classLoader = new ServiceClassLoader(new URL[]{url}, App.class.getClassLoader());

        App app = new Application(file.getName().replaceAll(".jar",""), file.getPath(), classLoader);

        return app;
    }




    @Override
    public void startUp() throws LifecycleException {
        super.startUp();
        Bus.registry(config.getName(), this);
        updateLifeState(LifeState.STARTED);
    }

    @Override
    public void shutDown() throws LifecycleException {
        super.shutDown();
        Iterator<App> iterator = apps.values().iterator();
        while (iterator.hasNext()) {
            Application application = (Application) iterator.next();
            application.shutDown();
        }
        apps.clear();
        Iterator<File> fileIterator = appFiles.values().iterator();
        appFiles.clear();
        servicesExecutorPool.shutdownNow();
        updateLifeState(LifeState.CLOSED);
    }


    @Override
    public void receiveMsg(Message msg) {
        Request request = (Request) msg;
        logger.debug("Actuator receive a req :{}/{}/{}", request.getAppName(), request.getFuncName(), request.getServiceName());
        servicesExecutorPool.submit(new ServiceInvocationTask(request, this));

    }

    // TODO: 2019/3/16 monitor  可以将以下操作封装成一个对象
    public ThreadPoolState getThreadPoolState() {
        ThreadPoolState state = new ThreadPoolState();
        state.setCorePoolSize(servicesExecutorPool.getCorePoolSize());
        state.setActiveCount(servicesExecutorPool.getActiveCount());
        state.setMaxPoolSize(servicesExecutorPool.getMaximumPoolSize());
        state.setTaskCount(servicesExecutorPool.getTaskCount());
        state.setCompletedTaskCount(servicesExecutorPool.getCompletedTaskCount());
        return state;
    }

    public List<BusyThreadState> getBusyThreadState() {
        List list = new ArrayList<BusyThreadState>();
        Iterator iterator = servicesExecutorPool.getQueue().iterator();
        while (iterator.hasNext()) {
            ServiceInvocationTask task = (ServiceInvocationTask) iterator.next();

            BusyThreadState state = new BusyThreadState();
            state.setName(task.getThreadName());
            state.setExecutetime(System.currentTimeMillis()-task.getContext().getStartTime());
            state.setTraceId(task.getRequest().getHeader().getSessionId());
            state.setExecuteService(task.getRequest().getAppName() + "/" + task.getRequest().getFuncName() + "/" + task.getRequest().getServiceName());
            list.add(state);
        }
        return list;
    }
}
