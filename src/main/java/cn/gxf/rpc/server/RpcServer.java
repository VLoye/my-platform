package cn.gxf.rpc.server;/**
 * Created by VLoye on 2018/12/19.
 */

import cn.gxf.common.DefaultILifecycleManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetSocketAddress;


/**
 * @author V
 * @Classname RpcServer
 * @Description
 **/
@Deprecated
public class RpcServer extends DefaultILifecycleManager implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private static final String THREAD_NAME = "RpcServer";

    private EventLoopGroup boss;
    private EventLoopGroup worker;

    @Value("${server.port}")
    private int port;


    @Override
    public void run() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //todo  add handler
                            pipeline.addLast("Http-decoder", new HttpRequestDecoder());
                            pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
                            pipeline.addLast("http-encoder", new HttpResponseEncoder());
                        }
                    });
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(port+1)).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("Rpc server is interrupter.");
            return;
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

    @Override
    public void initialize()  throws LifecycleException {
        super.initialize();

        logger.info("Rpc server is initialized");
    }

    @Override
    public void startUp()  throws LifecycleException{
        super.startUp();
        new Thread(this,THREAD_NAME).start();
        logger.info("Rpc server is starting on port:{}",port);
        logger.info("Rpc server is started.");
    }

    @Override
    public void shutDown()  throws LifecycleException{
        super.shutDown();
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        logger.info("Rpc server is closed.");
    }


//    public static void main(String[] args) {
//        RpcServer rpcServer = new RpcServer();
//        rpcServer.startUp();
//    }
}
