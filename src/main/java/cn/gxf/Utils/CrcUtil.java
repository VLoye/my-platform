package cn.gxf.Utils;/**
 * Created by VLoye on 2019/3/12.
 */

import java.util.zip.CRC32;

/**
 * @author V
 * @Classname CrcUtil
 * @Description
 **/
public class CrcUtil extends CRC32 {
    public int getIntValue() {
        return (int) (super.getValue() & 0xffff);
    }

    public static int getCrcValue(byte[] bytes) {
        CrcUtil crcUtil = new CrcUtil();
        crcUtil.update(bytes);
        return crcUtil.getIntValue();
    }

    public static boolean check(byte[] bytes, int crc) {
        int c = getCrcValue(bytes);
        return c == crc ? true : false;
    }
}
