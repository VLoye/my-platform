package cn.gxf.connector;/**
 * Created by VLoye on 2019/3/13.
 */

import cn.gxf.actuator.executor.exec.ServiceInvocationException;
import cn.gxf.connector.handler.ResponseHandler;
import cn.gxf.core.Request;
import cn.gxf.core.Response;
import cn.gxf.connector.handler.ByteToResponseDecoderHandler;
import cn.gxf.connector.handler.RequestToByteEncoderHandler;
import cn.gxf.connector.protocol.SITP;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author V
 * @Classname SITPClient
 * @Description
 **/
public class SITPClient {
    private static final Logger logger = LoggerFactory.getLogger(SITPClient.class);
    private static final long TIMEOUT = 30 * 1000;
    private Map<String, Response> reps = new ConcurrentHashMap<String, Response>();
    public static final AttributeKey<Map> REPS = AttributeKey.newInstance("reps");
    private Bootstrap bootstrap;
    private EventLoopGroup group;
    private volatile Channel channel;
    private long timeOut;
    private String host;
    private int port;


    public SITPClient(String host, int port) throws InterruptedException {
        this.host = host;
        this.port = port;
        if (initBootstrap()) {
            connect();
        }
    }

    private boolean initBootstrap() {
        group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.attr(REPS).set(reps);
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("RequestToByteEncoderHandler", new RequestToByteEncoderHandler());
                        pipeline.addLast("ByteToResponseDecoderHandler", new ByteToResponseDecoderHandler());
                        pipeline.addLast("ResponseHandler", new ResponseHandler());
                    }
                });
        return true;
    }

    public void connect() throws InterruptedException {

        ChannelFuture f = bootstrap.connect(host, port).sync();
        channel = f.channel();
//        channel.closeFuture().sync();
//        channel.writeAndFlush(Template.getRequestTemplate());

    }

    public Object invoke(String app, String fun, String service, ArrayList params) throws ServiceInvocationException {
        SITP sitp = new SITP();
        sitp.setAppName(app);
        sitp.setFunctionName(fun);
        sitp.setServiceName(service);
        sitp.setParams(params);
        return invoke(sitp);
    }

    public Object invoke(SITP sitp) throws ServiceInvocationException {
        return invoke(sitp, TIMEOUT);
    }

    public Object invoke(SITP sitp, long timeOut) throws ServiceInvocationException {
        Request request = Request.getSitpReq(sitp);
        String sessionId = request.getHeader().getSessionId();
        try {
            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            logger.error("send message to server error, cause by: {}", e);
            throw new ServiceInvocationException(e);
        }
        long curTime = System.currentTimeMillis();
        Response response = null;
        while (curTime + timeOut > System.currentTimeMillis()) {
            response = reps.get(sessionId);
            if (response != null) {
                Throwable t = response.getE();
                if (t != null) {
                    throw new ServiceInvocationException(t.getMessage());
                }
                Object result = response.getRes();
                if (result != null) {
                    return result;
                }
                return null;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new ServiceInvocationException(e);
            }
        }
        throw new ServiceInvocationException("Fail. Service invocation time out");
    }


    public static void main(String[] args) throws InterruptedException, ServiceInvocationException {
        SITPClient client = new SITPClient("localhost", 9999);
        client.connect();

        ArrayList list = new ArrayList();
        list.add(10001);
        list.add(10002);
        list.add(new BigDecimal(1000));
        Object res = client.invoke("Trace002-1.0", "trace", "transfer", list);
        System.out.println(res);
    }
}
