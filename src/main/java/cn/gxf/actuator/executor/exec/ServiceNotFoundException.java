package cn.gxf.actuator.executor.exec;/**
 * Created by VLoye on 2019/3/15.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ServiceNotFoundException
 * @Description
 **/
public class ServiceNotFoundException extends ServiceInvocationException {
    private static final Logger logger = LoggerFactory.getLogger(ServiceNotFoundException.class);

    public ServiceNotFoundException(String message) {
        super(message);
    }
}
