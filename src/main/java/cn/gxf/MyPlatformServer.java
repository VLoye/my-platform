package cn.gxf;/**
 * Created by VLoye on 2019/1/13.
 */

import cn.gxf.actuator.Actuator;
import cn.gxf.actuator.ActuatorConfig;
import cn.gxf.common.DefaultILifecycleManager;
import cn.gxf.common.ILifecycleManager;
import cn.gxf.common.LifeState;
import cn.gxf.connector.ConnConfig;
import cn.gxf.connector.SITPService;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author V
 * @Classname MyPlatformServer
 * @Description 平台启动程序
 **/

@Service
public class MyPlatformServer extends DefaultILifecycleManager implements ILifecycleManager, InitializingBean, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(MyPlatformServer.class);

    private Actuator actuator;
    @Autowired
    private ActuatorConfig actuatorConfig;

    @Autowired
    private ConnConfig connConfig;

    // TODO: 2019/3/15 to serviceList
    private SITPService sitpService;

    public MyPlatformServer() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startUp();

        logger.info("\n" +
                        "****************************************" + "\n" +
                        "*          中间业务系统启动成功        *" + "\n" +
                        "****************************************" + "\n" +
                        " 处理器核心线程数：{}        " + "\n" +
                        " 处理器最大线程数：{}        " + "\n" +
                        " 处理器等待队列大小：{}        " + "\n" +
                        " 服务预加载是否开启：{}        " + "\n" +
                        " SITP服务端口号：{}        " + "\n" +
                        "****************************************",
                actuatorConfig.getCoreSize(), actuatorConfig.getMaxSize(), actuatorConfig.getQueueSize(), actuatorConfig.isPreLoad(), connConfig.getPort()
        );

    }

    @Override
    public void initialize() throws LifecycleException {
        super.initialize();
        actuator = new Actuator(actuatorConfig);
        actuator.initialize();
        sitpService = new SITPService(connConfig);
        sitpService.initialize();
        updateLifeState(LifeState.INITED);

    }

    @Override
    public void startUp() throws LifecycleException {
        super.startUp();
        actuator.startUp();
        sitpService.startUp();
        updateLifeState(LifeState.STARTED);
    }

    @Override
    public void shutDown() throws LifecycleException {
        super.shutDown();
        actuator.shutDown();
        sitpService.shutDown();
        updateLifeState(LifeState.CLOSED);
//        System.out.println("****************************************");
//        System.out.println("*          中间业务系统关闭成功        *");
//        System.out.println("****************************************");
        logger.info("\n" +
                "****************************************" + "\n" +
                "*          中间业务系统关闭成功        *" + "\n" +
                "****************************************");
    }



    public Actuator getActuator() {
        return actuator;
    }

    @Override
    public void destroy() throws Exception {
        shutDown();
    }
}
