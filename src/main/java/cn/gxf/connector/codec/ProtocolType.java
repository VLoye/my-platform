package cn.gxf.connector.codec;/**
 * Created by VLoye on 2019/3/12.
 */

import lombok.Data;

/**
 * @author V
 * @Classname ProtocolType
 * @Description
 **/

public enum  ProtocolType {
    SITP((byte) 0x01),
    REP((byte)0x02);

    public byte value;
    ProtocolType(byte value) {
        this.value = value;
    }

}
