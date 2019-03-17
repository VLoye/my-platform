package cn.gxf.actuator.handler;/**
 * Created by VLoye on 2019/2/3.
 */

import cn.gxf.Context.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * @author V
 * @Classname DefaultHandler
 * @Description
 **/
@Deprecated
public class DefaultHandler implements IHandler{


    @Override
    public boolean handler(Context context) throws InvocationTargetException, IllegalAccessException {
        return false;
    }
}
