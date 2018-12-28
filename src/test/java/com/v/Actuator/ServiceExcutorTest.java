package com.v.Actuator;

import com.v.Actuator.executor.ServiceExcutor;
import com.v.Actuator.loader.AppLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by VLoye on 2018/12/18.
 */

@RunWith(JUnit4.class)
public class ServiceExcutorTest {
    private static final Logger logger = LoggerFactory.getLogger(ServiceExcutorTest.class);

    @Test
    public void testProxy() throws Exception {
        AppLoader appLoader = new AppLoader();
        appLoader.load("Trace001");
        Service serviceExecutor = ServiceExcutor.newBuilder()
                .setAppName("Trace001")
                .setFunctionName("com.v.Calculate")
                .setServiceName("add")
                .build();
        Object result = serviceExecutor.execute(new Integer[]{10,100});
        System.out.println(result);

    }


}