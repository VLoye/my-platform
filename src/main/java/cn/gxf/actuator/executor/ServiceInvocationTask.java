package cn.gxf.actuator.executor;/**
 * Created by VLoye on 2019/3/9.
 */

import cn.gxf.Context.Context;
import cn.gxf.actuator.Actuator;
import cn.gxf.actuator.Application;
import cn.gxf.actuator.executor.exec.ServiceInvocationException;
import cn.gxf.actuator.executor.exec.ServiceNotFoundException;
import cn.gxf.actuator.handler.ChainHandlerFactory;
import cn.gxf.connector.codec.ProtocolType;
import cn.gxf.core.Bus;
import cn.gxf.core.Device;
import cn.gxf.core.Request;
import cn.gxf.core.Response;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * @author V
 * @Classname ServiceInvocationTask
 * @Description
 **/
@Data
public class ServiceInvocationTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServiceInvocationTask.class);
    private Context context;
    private Actuator actuator;
    private Request request;
    private String threadName;


    public ServiceInvocationTask(Request request, Actuator actuator) {
        this.request = request;
        this.actuator = actuator;
    }


    @Override
    public void run() {
        long start = context.getStartTime();
        startLog(start);

        boolean success = false;
        try {
//            context = new Context(request); remove to executeBefore
            initContext(context);
            success = context.getChainHandler().execute(context);
        } catch (Exception e) {
            context.setE(e);
        }

        Response response = context.getResponse();
        if (!success || context.getE() != null) {
            response.setE(context.getE());
            response.setSuccess(false);
        }

        endLog(start, success, response);

        Bus.sendMsg(response);
    }

    private void endLog(long start, boolean success, Response response) {
        logger.info("Service execute result: [{}]", success);
        if (!success) {
            logger.error("Service execute cause a exception :", response.getE());
        }
        if (response.getRes() != null) {
            logger.info("Execution Result: [type: [{}] | value [{}]]", response.getRes().getClass(), String.valueOf(response.getRes()));
        }
        logger.info("Elapsed Time: [{}]ms", System.currentTimeMillis() - start);
        logger.info("[---------------- Trace End   ----------------]");
    }

    private void startLog(long current) {
        logger.info("[---------------- Trace Start ----------------]");
        logger.info("Trace ID:[{}]", request.getHeader().getSessionId());
        logger.info("Start timestamp: [{}]", current);
        logger.info("Target service: [{}/{}/{}]", request.getAppName(), request.getFuncName(), request.getServiceName());
        Iterator iterator = request.getParams().iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            logger.info("Params: [type: [{}] | value [{}]]", o.getClass(), String.valueOf(o));
        }
    }

    private void initContext(Context context) throws ServiceNotFoundException {
        context.setSessionId(request.getHeader().getSessionId());
        Response response = new Response();
        DefaultHeader header = DefaultHeader.getDefRepHeader();
        header.setSessionId(request.getHeader().getSessionId());
        header.setReply(false);
        header.setType(ProtocolType.REP);
        response.setHeader(header);
        Device sourceDevice = request.getSourceDevice();
        Device targetDevice = request.getTargetDevice();
        response.setSourceDevice(targetDevice);
        response.setTargetDevice(sourceDevice);
        response.setChannelId(request.getChannelId());
        response.setChannelId(request.getChannelId());
        context.setResponse(response);

        Application app = (Application) actuator.getApps().get(request.getAppName());
        if (app == null) {
            logger.warn("Invalid request ,app not found: [{}]", request.getAppName());
            throw new ServiceNotFoundException("Service Invocation fail, no such app: " + request.getAppName());
        }
        Method method = app.getServicesMap().get("/" + request.getFuncName() + "/" + request.getServiceName());
        if (method == null) {
            logger.warn("Invalid request ,service not found: [{}]", request.getServiceName());
            throw new ServiceNotFoundException("Service Invocation fail, no such function or service: " + request.getFuncName() + "/" + request.getAppName());
        }
        Object delegate = app.getIOC().get(method.getDeclaringClass()).getInstance();
        context.setApp(app);
        context.setMethod(method);
        context.setDelegate(delegate);
        context.setChainHandler(ChainHandlerFactory.getDefaultChain());
    }

}
