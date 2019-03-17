package cn.gxf.connector.handler;/**
 * Created by VLoye on 2019/3/13.
 */

import cn.gxf.common.Constant;
import cn.gxf.connector.SITPService;
import cn.gxf.core.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ServiceInvocationHandler
 * @Description
 **/
public class ServiceInvocationHandler extends SimpleChannelInboundHandler<AbstarctMessage>{
    private static final Logger logger = LoggerFactory.getLogger(ServiceInvocationHandler.class);
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
//        logger.debug("Server received a service invocation message: {}",request);
//        request.set
//        Bus.sendMsg(request);
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstarctMessage request) throws Exception {
        logger.debug("Server received a service invocation message: {}",request);
        request.setSourceDevice(Bus.getDevice(channelHandlerContext.channel().attr(Constants.SOURCE_DEVICE).get()));
        request.setTargetDevice(Bus.getDevice(Constants.ACTUATOR));
        request.setChannelId(channelHandlerContext.channel().attr(SITPService.ATTR_KEY_CHANNELID).get());
        Bus.sendMsg(request);
    }
}
