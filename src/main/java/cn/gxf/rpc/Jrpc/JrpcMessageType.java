package cn.gxf.rpc.Jrpc;/**
 * Created by VLoye on 2018/12/19.
 */

/**
 * @author V
 * @Classname JrpcMessageType
 * @Description
 **/
public enum  JrpcMessageType {
    INVOKE(1),
    REPLY(2),
    ;


    private int type;

    JrpcMessageType(int i) {
    }

    public int getType() {
        return type;
    }
}
