package cn.gxf.actuator.handler;

import cn.gxf.Context.Context;
import cn.gxf.actuator.executor.exec.ServiceInvocationException;

/**
 * Created by VLoye on 2018/12/27.
 */
public interface IChainHandler {

    Boolean execute(Context context) throws Exception;

    IChainHandler addLast(IHandler handler);

    IChainHandler addFirst(IHandler handler);
}
