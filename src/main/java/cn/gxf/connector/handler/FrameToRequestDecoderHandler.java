package cn.gxf.connector.handler;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.actuator.executor.DefaultHeader;
import cn.gxf.core.Request;
import cn.gxf.connector.IProtocol;
import cn.gxf.connector.codec.DecoderFactory;
import cn.gxf.connector.codec.ProtocolType;
import cn.gxf.connector.codec.ProtocolTypeFactory;
import cn.gxf.connector.protocol.DefaultFrame;
import cn.gxf.connector.protocol.SITP;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname FrameToRequestDecoderHandler
 * @Description
 **/
public class FrameToRequestDecoderHandler extends SimpleChannelInboundHandler<DefaultFrame> {
    private static final Logger logger = LoggerFactory.getLogger(FrameToRequestDecoderHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultFrame frame) throws Exception {
        Channel channel = channelHandlerContext.channel();
//        ConnService.channelMap.containsKey();

        DefaultHeader header = new DefaultHeader();
        header.setMagic(frame.getMagic());
        header.setLength(frame.getLength());
        header.setVersion(frame.getVersion());
        header.setReply(frame.getReply() == 0x01 ? true : false);
        header.setType(ProtocolTypeFactory.getType(frame.getType()));
        header.setSessionId(new String(frame.getSessionId()));
        Request request = new Request();
        request.setHeader(header);

        // TODO: 2019/3/13 auto change
        if(header.getType() == ProtocolType.SITP) {
            IProtocol<SITP> protocol = DecoderFactory.getDecoder(header.getType());
            SITP sitp = protocol.decoder(frame.getData());
            request.setAppName(sitp.getAppName());
            request.setFuncName(sitp.getFunctionName());
            request.setServiceName(sitp.getServiceName());
            request.setParams(sitp.getParams());
        }else {
            logger.error("Unknow protocol");
        }
        logger.debug("Receive a request message: [{}]",request);
        channelHandlerContext.fireChannelRead(request);
    }
}
