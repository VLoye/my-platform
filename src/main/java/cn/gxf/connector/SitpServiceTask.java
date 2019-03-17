package cn.gxf.connector;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.connector.handler.ByteToFrameDecoderHandler;
import cn.gxf.connector.handler.FrameToRequestDecoderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname SitpServiceTask
 * @Description
 **/
@Deprecated
public class SitpServiceTask implements Runnable{
    private static final Logger logger  = LoggerFactory.getLogger(SitpServiceTask.class);
    private ConnConfig config;
    private volatile boolean isStop;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    public SitpServiceTask(ConnConfig config, boolean isStop) {
        this.config = config;
        this.isStop = isStop;
    }

    @Deprecated
    public SitpServiceTask(ConnConfig config) {
        this.config = config;
    }

    @Override
    public void run(){
        try {
            startServer();
        } catch (Exception e) {
            logger.error("Connector[{}] start fail,cause by: {}",this.getClass(),e);
        }

    }
    public void startServer() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(config.getBossThreads());
        EventLoopGroup workerGroup = new NioEventLoopGroup(config.getWorkerThreads());
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
//                                channel.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast("ByteToFrameHandler",new ByteToFrameDecoderHandler());
                            pipeline.addLast("FrameToRequestDecoderHandler",new FrameToRequestDecoderHandler());
//                            pipeline.addLast("")

                        }
                    });
            ChannelFuture future = bootstrap.bind(config.getHost(),config.getPort()).sync();

            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
