package com.v.Actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import java.util.List;
import java.util.Map;

/**
 * @author V
 * @Classname Context
 * @Description 服务调起上下文
 **/
public class Context {

    //span 需要引入sleuth


    //调用id，用于异步请求请用后响应信息返回。
    private String id;

/* invocation information /Service
    可以包装在一起
    private String AppName;
    private String FunName;
    private String serviceName;
    private boolean isNeedReply;

    private Object[] reqParamsType;
    private Object[] reqParams;
    */

    private Map<String,Object> attributes;
    private List<Object> list;

    // todo 远程主机信息？  同样异步消息返回时，需要根据远程主机的url去返回数据   map？

}
