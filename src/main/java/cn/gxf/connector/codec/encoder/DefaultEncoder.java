package cn.gxf.connector.codec.encoder;/**
 * Created by VLoye on 2019/3/13.
 */

import cn.gxf.core.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname DefaultEncoder
 * @Description
 **/
@Deprecated
public class DefaultEncoder extends MessageToByteEncoder<Response>{
    private static final Logger logger = LoggerFactory.getLogger(DefaultEncoder.class);


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {

    }
}
