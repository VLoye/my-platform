package cn.gxf.rpc.Jrpc;/**
 * Created by VLoye on 2018/12/19.
 */



import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author V
 * @Classname JrpcMessageEncoder
 * @Description
 **/
public class JrpcMessageEncoder extends MessageToByteEncoder<JrpcMessage>{


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, JrpcMessage jrpcMessage, ByteBuf byteBuf) throws Exception {

    }
}
