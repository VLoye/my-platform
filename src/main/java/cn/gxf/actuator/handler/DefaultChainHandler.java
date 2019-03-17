package cn.gxf.actuator.handler;/**
 * Created by VLoye on 2019/2/3.
 */


import cn.gxf.Context.Context;
import cn.gxf.actuator.executor.exec.ServiceInvocationException;
import cn.gxf.actuator.loader.AbstractBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.logging.Handler;

/**
 * @author V
 * @Classname DefaultChainHandler
 * @Description
 **/
public class DefaultChainHandler extends AbstractChainHandler implements IChainHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultChainHandler.class);

    @Override
    public Boolean execute(Context context) throws Exception {
        Iterator<IHandler> iterator = chain.iterator();
        while (iterator.hasNext()) {
            IHandler handler = iterator.next();
            if (!handler.handler(context)) {
                return false;
            }
        }
        return true;
    }

}
