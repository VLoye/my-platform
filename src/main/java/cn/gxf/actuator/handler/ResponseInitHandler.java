package cn.gxf.actuator.handler;/**
 * Created by VLoye on 2019/3/15.
 */

import cn.gxf.Context.Context;
import cn.gxf.core.DefaultHeader;
import cn.gxf.connector.codec.ProtocolType;
import cn.gxf.core.Device;
import cn.gxf.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ResponseInitHandler
 * @Description
 **/
public class ResponseInitHandler implements IHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseInitHandler.class);

    @Override
    public boolean handler(Context context) throws Exception {
        Response response = new Response();
        DefaultHeader header = DefaultHeader.getDefRepHeader();
        header.setSessionId(context.getRequest().getHeader().getSessionId());
        header.setReply(false);
        header.setType(ProtocolType.REP);
        response.setHeader(header);

        Device sourceDevice = context.getRequest().getSourceDevice();
        Device targetDevice = context.getRequest().getTargetDevice();
        response.setSourceDevice(targetDevice);
        response.setTargetDevice(sourceDevice);
        response.setChannelId(context.getRequest().getChannelId());
        return true;
    }
}
