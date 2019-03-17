package cn.gxf.rpc.client;/**
 * Created by VLoye on 2018/12/19.
 */

import cn.gxf.common.DefaultILifecycleManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author V
 * @Classname RpcClient
 * @Description
 **/
@Deprecated
public class RpcClient extends DefaultILifecycleManager implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private static final String THREAD_NAME = "RpcClient";
    private String host;
    private int port;
    private EventLoopGroup worker;
    private Bootstrap bootstrap;
    private Channel channel;


    @Override
    public void run() {
        try {
            channel = bootstrap.connect(new InetSocketAddress(host, port)).sync().channel();
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
            logger.error("Rpc Client is Interrupter：{}", e.getMessage());
        }
    }

    @Override
    public void initialize() throws LifecycleException{
        super.initialize();
        worker = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //todo add client handler
//                        pipeline.addLast()
                    }
                });
        initialized = true;
        logger.info("Rpc client is initialized.");
    }

    @Override
    public void startUp()  throws LifecycleException{
        if (initialized == false) {
            initialize();
        }
        super.startUp();
        new Thread(this, THREAD_NAME).start();
        logger.info("Rpc client is started.");
    }

    @Override
    public void shutDown()  throws LifecycleException{
        if (worker != null && channel != null) {
            try {
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                //ignore
            }
            worker.shutdownGracefully();
        }
    }
}
