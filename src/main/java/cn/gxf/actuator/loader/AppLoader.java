package cn.gxf.actuator.loader;/**
 * Created by VLoye on 2018/12/17.
 */

import cn.gxf.actuator.App;
import cn.gxf.common.DefaultILifecycleManager;
import cn.gxf.common.ILifecycleManager;
import cn.gxf.common.LifeState;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author V
 * @Classname AppLoader
 * @Description
 **/
@Deprecated
public class AppLoader extends DefaultILifecycleManager implements App,ILifecycleManager{
    public static final Logger logger = LoggerFactory.getLogger(AppLoader.class);
    public static final String jarPath = "D:\\Idea_Projects\\my-platform\\src\\main\\resources\\service\\";


    @Value("${app.path}")
    private String SYSTEM_PATH;

    public static ConcurrentHashMap<String, ClassLoader> loaders = new ConcurrentHashMap<String, ClassLoader>();


    @Override
    public void load(String appName) throws Exception{
        if (loaders.containsKey(appName)) {
            logger.info("{} is already loaded.", appName);
            return;
        }
        String jarPath = generateAppPath(appName);
        //todo 测试路径  路径信息需要封装到配置中
        URL url = null;
//        File file = new File("D:\\");
//        File[] files = file.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                return name!=null && name.endsWith(".jar");
//            }
//        });
//        File[] files1 = file.listFiles();
        try {
            url = generateJarPath(jarPath);
        } catch (MalformedURLException e) {
            logger.error("The {} jar path is error.", appName);
            //Todo 可能需要进一步通过回控制台
            return;
        }
        URLClassLoader loader = new URLClassLoader(new URL[]{url});

        if (loader != null) {
            loaders.put(appName, loader);
            logger.info("{} has loaded successfully.", appName);
        }

    }

    @Override
    public void loadAll() throws Exception{
        File jarFile = new File(jarPath);
        File[] jars = jarFile.listFiles();
        for (File file : jars) {
            load(file.getName());
        }
    }


    private URL generateJarPath(String appName) throws MalformedURLException {
        return new URL("file", null, jarPath + appName);
    }

    @Override
    public void unload(String appName) throws Exception{
        ClassLoader classLoader = loaders.get(appName);
        if (classLoader != null) {
            try {
                ((URLClassLoader) classLoader).close();
            } catch (IOException e) {
                logger.error("app shutDown failed :{}",e);
            }
            loaders.remove(appName);
            logger.info("{} has suspend successfully.", appName);
        }
    }

    @Override
    public void unloadAll() throws Exception{
        for (String appName : loaders.keySet()) {
           unload(appName);
        }
    }



//    @Override
//    public void suspend(String appName) {
//        if (loaders.containsKey(appName)) {
//            loaders.remove(appName);
//            logger.info("{} has suspend successfully.", appName);
//        }
//    }


    private String generateAppPath(String appName) {
        return SYSTEM_PATH + "\\" + appName + ".jar";
    }

    @Override
    public void initialize() throws LifecycleException {
        super.initialize();
        updateLifeState(LifeState.INITED);
    }

    @Override
    public void startUp() throws LifecycleException {
        super.startUp();
        try {
            loadAll();
        } catch (Exception e) {

            throw new LifecycleException("LifeCycle cause a exception: {}",e);
        }
    }

    @Override
    public void shutDown() throws LifecycleException {
        super.shutDown();
        try {
            unloadAll();
        } catch (Exception e) {
            throw new LifecycleException("LifeCycle cause a exception: {}",e);
        }
    }
}
