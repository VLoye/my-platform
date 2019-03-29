package cn.gxf.actuator.executor.core;/**
 * Created by VLoye on 2019/3/15.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author V
 * @Classname ThreadNameFactory
 * @Description
 **/
public class ThreadNameFactory implements ThreadFactory{
    private static final Logger logger = LoggerFactory.getLogger(ThreadNameFactory.class);
    private static final AtomicInteger number = new AtomicInteger(1);
    private String prefix;
    private final ThreadGroup group;

    public ThreadNameFactory(String prefix) {
        this.prefix = prefix;
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix+"-"+number.getAndIncrement();
        Thread t = new Thread(group,r,name,0);
        return t;
    }
}
