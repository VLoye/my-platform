package cn.gxf.connector.handler;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.core.Constants;
import cn.gxf.Utils.CrcUtil;
import cn.gxf.connector.protocol.DefaultFrame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author V
 * @Classname AbstractDecoderHandler
 * @Description
 **/
public abstract class AbstractDecoderHandler extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDecoderHandler.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() <= 15) {
            return;//len to short
        }
        int magic = byteBuf.readInt();
        if (magic != Constants.magic) {
            // magic error, clear all
            byteBuf.clear();
        }
        DefaultFrame frame = new DefaultFrame();
        frame.setMagic(magic);
        frame.setVersion(byteBuf.readByte());
        frame.setReply(byteBuf.readByte());
        frame.setType(byteBuf.readByte());
        byte[] sessionId = new byte[16];
        byteBuf.readBytes(sessionId);
        frame.setSessionId(sessionId);
        int len = byteBuf.readInt();
        frame.setLength(len);
        if (byteBuf.readableBytes() < len - Constants.headerLen + 4) { //4 = crc
            byteBuf.resetReaderIndex();
            return; //len to short
        }
        byte[] datas = new byte[len - Constants.headerLen];
        byteBuf.readBytes(datas);
        frame.setData(datas);
        frame.setCrc(byteBuf.readInt());
        //crc check
        byteBuf.resetReaderIndex();
        byte[] pck = new byte[len - 4];
        byteBuf.readBytes(pck);
        ;
        if (CrcUtil.check(pck, frame.getCrc())) {
            list.add(frame);
            logger.debug("receive a message: {}", frame.toString());
        } else {
            logger.warn("Crc check fail, discard message: [{}]", frame.toString());
        }

    }
}
