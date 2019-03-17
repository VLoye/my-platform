package cn.gxf.Context;/**
 * Created by VLoye on 2018/12/17.
 */

import cn.gxf.actuator.Application;
import cn.gxf.core.Request;
import cn.gxf.core.Response;
import cn.gxf.actuator.handler.IChainHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author V
 * @Classname Context
 * @Description 包含服务调用上下文
 * 包含具体的方法信息，方法参数类型[]，调用对象，参数信息
 **/
@Data
public class Context {
// TODO: 2019/3/16 这个类需要重构 
    
    
    private static final Logger logger = LoggerFactory.getLogger(Context.class);
    private String sessionId;
    private Method method = null;
    private Throwable e;
    private Request request;
    private Response response;
    private IChainHandler chainHandler;
//    private Actuator actuator;
    private Object delegate;
    private Application app;
    private long startTime;

//    private boolean isSuspend;//是否挂起

//    private ContextBuilder bulider;//内部构造对象


    public Context(Request request) {
        this.request = request;
    }

}
