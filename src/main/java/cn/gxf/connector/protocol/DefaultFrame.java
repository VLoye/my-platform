package cn.gxf.connector.protocol;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.Utils.CrcUtil;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * @author V
 * @Classname DefaultFrame
 * @Description Service Invocation Transport IProtocol
 * 4+4+1+1+1+16+4=31+data
 **/
@Data
public class DefaultFrame{
    private int magic;           // 4     DefaultFrame
    private int length;          // 4     报文长度
    private byte version;        // 1
    private byte reply;          // 1       0f 1t
    private byte type;           // 1         表示SITP
    private byte[] sessionId;    // 16
    private byte[] data;         // n        1 tlen 1 vlen
    private int crc;             //4

    private byte[] source;

    public byte[] toBytes() throws Exception {
        if (source != null) {
            return this.source;
        }
        ByteArrayOutputStream bout = null;
        DataOutputStream dout = null;
        try {
            bout = new ByteArrayOutputStream(4096);
            dout = new DataOutputStream(bout);
            dout.write(magic);
            dout.writeInt(length);
            dout.write(version);
            dout.write(reply);
            dout.write(type);
            dout.write(sessionId);
            dout.write(data);
            dout.flush();
            dout.writeInt(crc == 0 ? CrcUtil.getCrcValue(bout.toByteArray()) : crc);
            dout.flush();
            this.source = bout.toByteArray();
            return this.source;
        } finally {
            if (bout != null) {
                bout.close();
            }
            if (dout != null) {
                dout.close();
            }
        }
    }



}
