package cn.gxf.connector.handler;/**
 * Created by VLoye on 2019/3/14.
 */

import cn.gxf.actuator.executor.exec.ServiceInvocationException;
import cn.gxf.core.Constants;
import cn.gxf.core.DefaultHeader;
import cn.gxf.core.Response;
import cn.gxf.connector.codec.ProtocolTypeFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author V
 * @Classname ByteToResponseDecoderHandler
 * @Description
 **/
public class ByteToResponseDecoderHandler extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(ByteToResponseDecoderHandler.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < Constants.headerLen) {
            return;
        }
        Response response = new Response();
        DefaultHeader header = new DefaultHeader();
        header.setMagic(byteBuf.readInt());
        header.setVersion(byteBuf.readByte());
        header.setReply(byteBuf.readBoolean());
        header.setType(ProtocolTypeFactory.getType(byteBuf.readByte()));
        byte[] sessionId = new byte[16];
        byteBuf.readBytes(sessionId);
        header.setSessionId(new String(sessionId));
        header.setLength(byteBuf.readInt());
        response.setHeader(header);
        response.setSuccess(byteBuf.readBoolean());
        if (!response.isSuccess()) {
            int exeLen = byteBuf.readInt();
            byte[] bytes = new byte[exeLen];
            byteBuf.readBytes(bytes);
            String e = new String(bytes);
            response.setE(new ServiceInvocationException(e));
        }
        int tLen = byteBuf.readByte();
        if (tLen > 0) {
            byte[] types = new byte[tLen];
            byteBuf.readBytes(types);
            String type = new String(types);

            int vlen = byteBuf.readByte();
            byte[] lens = new byte[vlen];
            byteBuf.readBytes(lens);
            String value = new String(lens);

            response.setRes(getObject(type, value));
        }

        list.add(response);

    }

    private Object getObject(String className, String value) {
        switch (className) {
            case Constants.stringClass:
                return value;
            case Constants.intClass:
                return Integer.valueOf(value);
            case Constants.doubleClass:
                return Double.valueOf(value);
            case Constants.booleanClass:
                return Boolean.valueOf(value);
        }

        logger.error("No such type: [{}]", className);
        return null;
    }
}
