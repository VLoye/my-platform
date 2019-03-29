package cn.gxf.actuator;/**
 * Created by VLoye on 2019/3/16.
 */

import cn.gxf.actuator.executor.core.ServiceInvocationTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.FutureTask;

/**
 * @author V
 * @Classname MonitorTaskFuture
 * @Description
 **/
public class MonitorTaskFuture<T> extends FutureTask<T> {
    private static final Logger logger = LoggerFactory.getLogger(MonitorTaskFuture.class);
    ServiceInvocationTask task;

    public MonitorTaskFuture(Runnable runnable, T result) {
        super(runnable, result);
        this.task = (ServiceInvocationTask)runnable;
    }

}
