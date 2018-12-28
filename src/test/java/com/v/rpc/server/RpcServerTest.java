package com.v.rpc.server;

import com.v.MyPlatformApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by VLoye on 2018/12/19.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RpcServerTest {

    @Test
    public void rpcServerTest(){
        RpcServer rpcServer = new RpcServer();

        rpcServer.start();

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
                rpcServer.close();
            }
        });
        thread.start();



    }

}