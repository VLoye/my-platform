package cn.gxf.core;/**
 * Created by VLoye on 2019/3/14.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname AbstarctMessage
 * @Description
 **/
public abstract class AbstarctMessage implements Message{
    private static final Logger logger = LoggerFactory.getLogger(AbstarctMessage.class);
    protected Device sourceDevice;
    protected Device targetDevice;
    protected String channelId;

    @Override
    public Device getTargetDevice() {
        return targetDevice;
    }

    @Override
    public Device getSourceDevice() {
        return sourceDevice;
    }

    public void setSourceDevice(Device sourceDevice) {
        this.sourceDevice = sourceDevice;
    }

    public void setTargetDevice(Device targetDevice) {
        this.targetDevice = targetDevice;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
