package cn.gxf.connector.handler;/**
 * Created by VLoye on 2019/3/14.
 */

import cn.gxf.core.Response;
import cn.gxf.connector.SITPClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ResponseHandler
 * @Description
 **/
public class ResponseHandler extends SimpleChannelInboundHandler<Response>{
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        String sessionId = response.getHeader().getSessionId();
        channelHandlerContext.channel().attr(SITPClient.REPS).get().put(sessionId,response);

        logger.debug("SITP Client received a response: {}",response);
    }
}
