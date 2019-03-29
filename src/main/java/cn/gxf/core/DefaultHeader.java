package cn.gxf.core;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.Utils.UUIDUtil;
import cn.gxf.connector.codec.ProtocolType;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author V
 * @Classname DefaultHeader
 * @Description 4+1+1+1+16=23 +4+4=31
 **/
@Data
public class DefaultHeader {
    private int magic; //4
    private boolean reply; //1
    private byte version;
    private ProtocolType type;
    private String sessionId; //16
    private int length;
    private int crc;


    //len 23
    public byte[] encoder() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(magic);
            dos.writeByte(version);
            dos.writeByte(reply?0x01:0x00);
            dos.writeByte((byte)type.value);
            dos.write(sessionId.getBytes());
        }finally {
            baos.close();
            dos.close();
        }
        return baos.toByteArray();
    }


    public static DefaultHeader getDefReqHeader(){
        DefaultHeader header = new DefaultHeader();
        header.setMagic(Constants.magic);
        header.setReply(true);
        header.setVersion((byte) 0x01);
        header.setSessionId(UUIDUtil.getUUID16());
        return header;
    }

    public static DefaultHeader getDefRepHeader(){
        DefaultHeader header = new DefaultHeader();
        header.setMagic(Constants.magic);
        header.setReply(false);
        header.setVersion((byte) 0x01);
        return header;
    }
}
