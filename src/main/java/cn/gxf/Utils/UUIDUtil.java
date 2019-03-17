package cn.gxf.Utils;/**
 * Created by VLoye on 2019/3/14.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author V
 * @Classname UUIDUtil
 * @Description
 **/
public class UUIDUtil {
    private static final Logger logger = LoggerFactory.getLogger(UUIDUtil.class);

    public static String getUUID16(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,16);
        return uuid;
    }

}
