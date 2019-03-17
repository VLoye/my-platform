package cn.gxf.core;/**
 * Created by VLoye on 2019/2/3.
 */

import io.netty.util.AttributeKey;

/**
 * @author V
 * @Classname Constants
 * @Description
 **/
public class Constants {
    public static final String servicePath = "";
    public static final int magic = 0x00475846;  //GXF
    public static final int headerLen = 31;

    public static final String stringClass = "java.lang.String";
    public static final String intClass = "java.lang.Integer";
    public static final String doubleClass = "java.lang.Double";

    public static final String ACTUATOR = "actuator";
    public static final String SITP = "sitp";

    public static final AttributeKey<String> SOURCE_DEVICE = AttributeKey.newInstance("SOURCE_DEVICE");
    public static final AttributeKey<String> TARGEt_DEVICE = AttributeKey.newInstance("TARGEt_DEVICE");
}
