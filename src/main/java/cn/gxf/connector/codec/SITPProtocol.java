package cn.gxf.connector.codec;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.connector.AbstractProtocol;
import cn.gxf.connector.IProtocol;
import cn.gxf.connector.protocol.SITP;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author V
 * @Classname SITPProtocol
 * @Description
 **/
public class SITPProtocol extends AbstractProtocol<SITP> implements IProtocol<SITP> {

    @Override
    public byte[] encode(SITP sitp) throws IOException {
        byte[] res = null;
        ByteArrayOutputStream bout = null;
        DataOutputStream dout = null;
        try {
            bout = new ByteArrayOutputStream();
            dout = new DataOutputStream(bout);
            dout.writeByte(sitp.getAppName().length());
            dout.write(sitp.getAppName().getBytes());
            dout.writeByte(sitp.getFunctionName().length());
            dout.write(sitp.getFunctionName().getBytes());
            dout.writeByte(sitp.getServiceName().length());
            dout.write(sitp.getServiceName().getBytes());
            Iterator iterator = sitp.getParams().iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                //type
                dout.writeByte(o.getClass().getName().length());
                dout.write(o.getClass().getName().getBytes());
                //value
                dout.write(String.valueOf(o).getBytes().length);//has problem??
                dout.write(String.valueOf(o).getBytes());
            }
            dout.flush();
            res = bout.toByteArray();
        } finally {
            bout.close();
            dout.close();
        }
        return res;
    }

    @Override
    public SITP decoder(byte[] data) {
        int offset = 0;
        SITP sitp = new SITP();
        int appl = data[offset++];
        String app = new String(data, offset, appl);
        offset += appl;

        int funl = data[offset++];
        String fun = new String(data, offset, funl);
        offset += funl;

        int servicel = data[offset++];
        String service = new String(data, offset, servicel);
        offset += servicel;

        sitp.setAppName(app);
        sitp.setFunctionName(fun);
        sitp.setServiceName(service);

        ArrayList list  = new ArrayList();
        while (offset < data.length) {
            int classl = data[offset++];
            String className = new String(data, offset, classl);
            offset += classl;

            int valuel = data[offset++];
            String value = new String(data, offset, valuel);
            offset += valuel;

            Object o = getParams(className, value);
            list.add(o);
        }
        sitp.setParams(list);
        return sitp;
    }

}
