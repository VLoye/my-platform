package cn.gxf.actuator;

import cn.gxf.MyPlatformServer;
import cn.gxf.core.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.concurrent.Future;


/**
 * Created by VLoye on 2019/3/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActuatorTest {

    @Autowired
    public MyPlatformServer server;


    @Test
    public void testInvocation(){
        Request request = getReq();
//        Future future = server.getActuator().serviceReq(request);
        System.out.println("");
    }

    public Request getReq() {
        Request req = new Request();
        req.setAppName("Trace001-1.0.jar");
        req.setFuncName("calculate");
        req.setServiceName("add");
        LinkedHashMap<String,Object> params = new LinkedHashMap<String,Object>();
        params.put("1",100);
        params.put("2",200);
//        req.setParams(params);
        return req;
    }
}