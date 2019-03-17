package cn.gxf.connector;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.common.DefaultILifecycleManager;
import cn.gxf.common.ILifecycleManager;
import cn.gxf.core.Device;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author V
 * @Classname ConnService
 * @Description
 **/
public class ConnService extends DefaultILifecycleManager implements ILifecycleManager{
    private static final Logger logger = LoggerFactory.getLogger(ConnService.class);
    private static final ArrayList<ILifecycleManager> lifes = new ArrayList<ILifecycleManager>();

//    public static Map<String,Channel> channelMap = new ConcurrentHashMap<String,Channel>(1024);



    @Override
    public void initialize() throws LifecycleException {
        super.initialize();
    }

    @Override
    public void startUp() throws LifecycleException {
        super.startUp();
    }
}
