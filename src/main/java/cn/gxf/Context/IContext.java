package cn.gxf.Context;

import cn.gxf.actuator.Application;
import cn.gxf.actuator.handler.IChainHandler;
import cn.gxf.core.Request;
import cn.gxf.core.Response;

/**
 * Created by VLoye on 2019/3/26.
 */
public interface IContext {
    Request getRequest();
    Response getResponse();
    Application getApp();
    IChainHandler getChainHandler();
}
