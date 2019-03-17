package cn.gxf.connector.codec;/**
 * Created by VLoye on 2019/3/13.
 */

import cn.gxf.actuator.executor.DefaultHeader;
import cn.gxf.connector.AbstractProtocol;
import cn.gxf.connector.IProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author V
 * @Classname DefaultHeadProtocol
 * @Description
 **/
@Deprecated
public class DefaultHeadProtocol extends AbstractProtocol<DefaultHeader> implements IProtocol<DefaultHeader>{
    private static final Logger logger = LoggerFactory.getLogger(DefaultHeadProtocol.class);

    @Override
    public byte[] encode(DefaultHeader defaultHeader) throws IOException {
        return new byte[0];
    }

    @Override
    public DefaultHeader decoder(byte[] data) {
        return null;
    }
}
