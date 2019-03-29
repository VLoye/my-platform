package cn.gxf.connector.handler;/**
 * Created by VLoye on 2019/3/13.
 */

import cn.gxf.Utils.CrcUtil;
import cn.gxf.core.DefaultHeader;
import cn.gxf.core.Constants;
import cn.gxf.core.Request;
import cn.gxf.connector.IProtocol;
import cn.gxf.connector.codec.DecoderFactory;
import cn.gxf.connector.codec.ProtocolType;
import cn.gxf.connector.protocol.SITP;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname RequestToByteEncoderHandler
 * @Description
 **/
public class RequestToByteEncoderHandler extends MessageToByteEncoder<Request> {
    private static final Logger logger = LoggerFactory.getLogger(RequestToByteEncoderHandler.class);


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request, ByteBuf byteBuf) throws Exception {
        DefaultHeader header = request.getHeader();
        if (header == null) {
            return;
        }
        byteBuf.writeBytes(header.encoder());

        //need to auto
        if (header.getType().equals(ProtocolType.SITP)) {
            SITP sitp = new SITP();
            sitp.setAppName(request.getAppName());
            sitp.setFunctionName(request.getFuncName());
            sitp.setServiceName(request.getServiceName());
            sitp.setParams(request.getParams());
            IProtocol<SITP> protocol = DecoderFactory.getDecoder(header.getType());
            byte[] bodys = protocol.encode(sitp);
            //length  7+4+4+bodys
            byteBuf.writeInt(Constants.headerLen + bodys.length);
            byteBuf.writeBytes(bodys);

            byte[] pck = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(pck);
            int crc = CrcUtil.getCrcValue(pck);
            byteBuf.resetReaderIndex();
            byteBuf.writeInt(crc);

            byte[] res = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(res);
            System.out.println(new String(res));
            byteBuf.resetReaderIndex();

        }

    }
}
