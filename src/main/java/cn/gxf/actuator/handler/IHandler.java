package cn.gxf.actuator.handler;/**
 * Created by VLoye on 2019/2/3.
 */

import cn.gxf.Context.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * @author V
 * @Classname IHandler
 * @Description
 **/
public interface IHandler {
    boolean handler(Context context) throws Exception;
}
