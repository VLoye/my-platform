package cn.gxf.actuator;/**
 * Created by VLoye on 2019/3/16.
 */

import cn.gxf.Context.Context;
import cn.gxf.actuator.executor.ServiceInvocationTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author V
 * @Classname ServiceThreadPool
 * @Description
 **/
public class ServiceThreadPool extends ThreadPoolExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ServiceThreadPool.class);

    public ServiceThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ServiceThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ServiceThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ServiceThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
//        return super.newTaskFor(runnable, value);
        return new MonitorTaskFuture(runnable, value);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        ServiceInvocationTask task = getTask(r);
        task.setThreadName(t.getName());
        //error
        task.setContext(new Context(task.getRequest()));
        task.getContext().setStartTime(System.currentTimeMillis());

    }

    private ServiceInvocationTask getTask(Runnable r) {
        ServiceInvocationTask task;
        if (r instanceof MonitorTaskFuture) {
            task = ((MonitorTaskFuture) r).task;
        } else if (r instanceof ServiceInvocationTask) {
            task = (ServiceInvocationTask) r;
        } else
            throw new IllegalStateException("The r must be a ServiceInvocationTask or a MonitorTaskFuture");
        return task;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }
}
