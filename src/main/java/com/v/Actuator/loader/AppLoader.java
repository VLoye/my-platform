package com.v.Actuator.loader;/**
 * Created by VLoye on 2018/12/17.
 */

import com.v.Actuator.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author V
 * @Classname AppLoader
 * @Description
 **/
public class AppLoader implements App {
    public static final Logger logger = LoggerFactory.getLogger(AppLoader.class);

    @Value("${app.path}")
    private String SYSTEM_PATH;

    public static ConcurrentHashMap<String, ClassLoader> loaders = new ConcurrentHashMap<String, ClassLoader>();


    @Override
    public void load(String appName) {
        if (loaders.containsKey(appName)) {
            logger.info("{} is already loaded.", appName);
            return;
        }
        String jarPath = generateAppPath(appName);
        //todo 测试路径
        jarPath = "file:\\D:\\Idea_Projects\\my-platform\\src\\main\\resources\\service\\Trace001.jar";
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

    private URL generateJarPath(String jarPath) throws MalformedURLException {
        return new URL("file",null,jarPath);
    }

    @Override
    public void unload(String appName) {
        if (loaders.containsKey(appName)) {
            loaders.remove(appName);
            logger.info("{} has suspend successfully.", appName);
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

}
