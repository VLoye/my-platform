package cn.gxf.core;/**
 * Created by VLoye on 2019/3/14.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author V
 * @Classname Bus
 * @Description
 **/
public class Bus {
    private static final Logger logger = LoggerFactory.getLogger(Bus.class);
    private static final Map<String, Device> devices = new ConcurrentHashMap<String, Device>();

    public static void registry(String deviceName, Device device) {
        devices.put(deviceName,device);
    }
    public static void unRegistry(String deviceName) {
        devices.remove(deviceName);
    }

    public static Device getDevice(String deviceName){
        return devices.get(deviceName);
    }

    public static void sendMsg(Message msg){
        Device tarDevice = msg.getTargetDevice();
        if (tarDevice == null){
            logger.error("ERROR. Target device is not exit.");
        }
        tarDevice.receiveMsg(msg);
    }

}
