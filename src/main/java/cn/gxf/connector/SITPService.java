package cn.gxf.connector;/**
 * Created by VLoye on 2019/3/12.
 */

import cn.gxf.common.DefaultILifecycleManager;
import cn.gxf.common.ILifecycleManager;
import cn.gxf.common.LifeState;
import cn.gxf.connector.handler.ByteToFrameDecoderHandler;
import cn.gxf.connector.handler.FrameToRequestDecoderHandler;
import cn.gxf.connector.handler.ResponseToByteEncoderHandler;
import cn.gxf.connector.handler.ServiceInvocationHandler;
import cn.gxf.core.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author V
 * @Classname SITPService
 * @Description
 **/
public class SITPService extends DefaultILifecycleManager implements ILifecycleManager,Device {
    private static final Logger logger = LoggerFactory.getLogger(SITPService.class);
    private volatile boolean isStop = false;
    public static final AttributeKey<String> ATTR_KEY_CHANNELID = AttributeKey.newInstance("CHANNEL_ID");
    //    CountDownLatch latch = new CountDownLatch(1);
    private ServerBootstrap bootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Map<String,Channel> channels = new ConcurrentHashMap<String,Channel>(128);

    private ConnConfig config;


    public SITPService() {
    }

    public SITPService(ConnConfig config) {
        this.config = config;
    }


    @Override
    public void initialize() throws LifecycleException {
        super.initialize();
        initBootstap();
        updateLifeState(LifeState.INITED);
    }

    @Override
    public void startUp() throws LifecycleException {
        super.startUp();
        try {
            ChannelFuture future = bootstrap.bind(config.getHost(),config.getPort()).sync();
//            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new LifecycleException("SITP service start fail, cause by: {}",e);
        }
        Bus.registry(Constants.SITP,this);
        updateLifeState(LifeState.STARTED);
    }

    //服务端关闭
    @Override
    public void shutDown() throws LifecycleException {
        super.shutDown();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        Bus.unRegistry(Constants.SITP);
        updateLifeState(LifeState.CLOSED);
    }

    private boolean initBootstap() {
        bossGroup = new NioEventLoopGroup(config.getBossThreads());
        workerGroup = new NioEventLoopGroup(config.getWorkerThreads());

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        String channelId = genSessionId();
                        channel.attr(ATTR_KEY_CHANNELID).set(channelId);
                        channel.attr(Constants.SOURCE_DEVICE).set(Constants.SITP);
                        channels.put(channelId,channel);
//                                channel.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                        channel.closeFuture().addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                channels.remove(channelId);
                            }
                        });
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("ByteToFrameDecoderHandler", new ByteToFrameDecoderHandler());
                        pipeline.addLast("FrameToRequestDecoderHandler", new FrameToRequestDecoderHandler());
                        pipeline.addLast("ServiceInvocationHandler",new ServiceInvocationHandler());
//                            pipeline.addLast("")
                        pipeline.addLast("ResponseToByteEncoderHandler",new ResponseToByteEncoderHandler());

                    }
                });
        logger.debug("SITP service bootstap init success.");
        return true;
    }

    private String genSessionId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

    public static void main(String[] args) {
        ConnConfig config = new ConnConfig();
        config.setBossThreads(1);
        config.setWorkerThreads(2);
        config.setHost("localhost");
        config.setPort(9999);
        SITPService service = new SITPService(config);
        try {
            service.startUp();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receiveMsg(Message msg) {
        if(msg instanceof Response){
            Response response = (Response)msg;
            Channel channel = channels.get(response.getChannelId());
            channel.writeAndFlush(response);
        }
    }
}
