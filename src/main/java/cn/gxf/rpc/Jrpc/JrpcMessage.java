package cn.gxf.rpc.Jrpc;/**
 * Created by VLoye on 2018/12/19.
 */

import lombok.Data;

import java.util.List;

/**
 * @author V
 * @Classname JrpcMessage
 * @Description
 **/
public class JrpcMessage {
    private byte Magic;
    private byte packLen;
    private byte type;
    private long traceId;
    private long spanId;
    private int sample;

    private Body body;


    @Data
    public static class Body{
        private String appName;
        private String funcName;
        private String serviceName;
        private boolean isReply;
        private List<JrpcMessage.ParamPair> paramsList;

    }
    @Data
    public static class ParamPair {
        private String key;
        private String value;

    }

}
