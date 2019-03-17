package cn.gxf.connector.codec;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.connector.IProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname DecoderFactory
 * @Description
 **/
public class DecoderFactory {
    private static final Logger logger  = LoggerFactory.getLogger(DecoderFactory.class);

    public static IProtocol getDecoder(ProtocolType type){
        switch (type){
            case SITP : return new SITPProtocol();
        }

        logger.error("nu such protocol value: {}",type.value);
        return null;
    }
}
