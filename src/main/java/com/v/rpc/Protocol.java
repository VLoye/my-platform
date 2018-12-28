package com.v.rpc;/**
 * Created by VLoye on 2018/12/19.
 */

import io.netty.buffer.ByteBuf;

/**
 * @author V
 * @Classname Protocol
 * @Description 自定协议接口
 **/
public interface Protocol {

    ByteBuf encoder();

    Object decoder();

}
