package cn.gxf.actuator.executor.exec;/**
 * Created by VLoye on 2019/3/15.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ServiceInvocationException
 * @Description
 **/
public class ServiceInvocationException extends Exception{
    private static final Logger logger = LoggerFactory.getLogger(ServiceInvocationException.class);

    public ServiceInvocationException(String message) {
        super(message);
    }

    public ServiceInvocationException(Throwable cause) {
        super(cause);
    }
}
