package com.v.rpc.Jrpc;/**
 * Created by VLoye on 2018/12/19.
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author V
 * @Classname JrpcMessageDecoder
 * @Description
 **/
public class JrpcMessageDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(JrpcMessageDecoder.class);

    private static final byte JRPC_MAGIC = 0x01;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        boolean legalMagic = checkMagic(byteBuf);
        if (!legalMagic) {
            logger.error("Illegal Magic");
            channelHandlerContext.close();
        }


    }

    private boolean checkMagic(ByteBuf byteBuf) {
        if (byteBuf.getByte(1) == JRPC_MAGIC) {
            return true;
        }
        return false;
    }
}
