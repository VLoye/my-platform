package cn.gxf.rpc.Jrpc;/**
 * Created by VLoye on 2018/12/19.
 */



import lombok.Data;

import java.util.List;

/**
 * @author V
 * @Classname Jrpc
 * @Description
 **/
public class Jrpc {

    Body body;






    @Data
    public static class Body{
        private String appName;
        private String funcName;
        private String serviceName;
        private boolean isReply;
        private List<Jrpc.ParamPair> paramsList;

    }



    @Data
    public static class ParamPair {
        private String key;
        private String value;

    }
}
