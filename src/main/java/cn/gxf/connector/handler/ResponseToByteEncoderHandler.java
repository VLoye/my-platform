package cn.gxf.connector.handler;/**
 * Created by VLoye on 2019/3/14.
 */

import cn.gxf.core.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author V
 * @Classname ResponseToByteEncoderHandler
 * @Description
 **/
public class ResponseToByteEncoderHandler extends MessageToByteEncoder<Response> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseToByteEncoderHandler.class);


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(response.getHeader().encoder());

        byte[] bodys = getBody(response);
        byteBuf.writeInt(byteBuf.readableBytes() + bodys.length);
        byteBuf.writeBytes(bodys);
    }

    private byte[] getBody(Response response) throws IOException {
        DataOutputStream dos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            boolean success = response.isSuccess();
            dos.writeByte(success ? 0x01 : 0x00);
            if (!success) {
                String e = response.getE().getMessage();
                dos.writeInt(e.length());
                dos.writeBytes(e);
            }
            Object o = response.getRes();
            if (o != null) {
                dos.writeByte(o.getClass().getName().length());
                dos.writeBytes(o.getClass().getName());

                dos.writeByte(String.valueOf(o).getBytes().length);
                dos.writeBytes(String.valueOf(o));
            }else {//object is null
                dos.writeByte(0);
            }
            dos.flush();
            return baos.toByteArray();
        } finally {
            baos.close();
            dos.close();
        }

    }
}
