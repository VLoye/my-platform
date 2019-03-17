package cn.gxf.actuator;

import cn.gxf.actuator.loader.ServiceClassLoader;
import org.junit.Test;

import java.net.URL;

/**
 * Created by VLoye on 2019/2/12.
 */
public class ApplicationTest {
    @Test
    public void loadAndScan() throws Exception {
        String path = "D:\\Idea_Projects\\my-platform\\src\\main\\resources\\service\\Trace001-1.0.jar";
        URL url = new URL("file:\\"+path);
        Application application = new Application("test",
                path,
                new ServiceClassLoader(new URL[]{url},ApplicationTest.class.getClassLoader()));
        application.loadAndScan();

        System.out.println();
    }

}