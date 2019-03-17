package cn.gxf.actuator.handler;/**
 * Created by VLoye on 2019/2/3.
 */

import cn.gxf.Context.Context;
import cn.gxf.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author V
 * @Classname ServiceHandler
 * @Description
 **/
public class ServiceHandler extends AbstractHandler implements IHandler{
    private static final Logger logger = LoggerFactory.getLogger(ServiceHandler.class);
    @Override
    public boolean handler(Context context) throws Exception {
        Method method = context.getMethod();
        method.setAccessible(true);
//        LinkedHashMap<String, Object> params = context.getRequest().getParams();
        List list = context.getRequest().getParams();
        Object[] params = list.toArray();
        Object result = method.invoke(context.getDelegate(),params);
        method.setAccessible(false);
        logger.debug("serviceHandler execute result: {}",result.toString());
        Response response = context.getResponse();
        response.setSuccess(true);
        response.setRes(result);
        return true;
    }
}
