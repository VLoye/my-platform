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
    //调用id，用于异步请求请用后响应信息返回。
    private String id;

    private String AppName;
    private String FunName;
    private String serviceName;

    private Object[] reqParamsType;
    private Object[] reqParams;

    private Map<String,Object> attributes;
    private List<Object> list;

}
