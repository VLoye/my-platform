package cn.gxf.connector.codec;/**
 * Created by VLoye on 2019/3/12.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ProtocolTypeFactory
 * @Description
 **/
public class ProtocolTypeFactory {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolTypeFactory.class);
    public static ProtocolType getType(byte type){
        switch (type){
            case 0x01:
                return ProtocolType.SITP;
            case 0x02:
                return ProtocolType.REP;
        }

        logger.error("error. No such Protocol : {}",type);
        return null;
    }
}
