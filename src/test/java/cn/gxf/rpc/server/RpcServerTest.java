package cn.gxf.rpc.server;

import org.apache.catalina.LifecycleException;
import org.junit.Test;

/**
 * Created by VLoye on 2018/12/19.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RpcServerTest {

    @Test
    public void rpcServerTest() throws LifecycleException {
        RpcServer rpcServer = new RpcServer();

        rpcServer.startUp();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    rpcServer.shutDown();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();



    }

}