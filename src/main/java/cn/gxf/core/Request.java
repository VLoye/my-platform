package cn.gxf.core;/**
 * Created by VLoye on 2019/2/3.
 */

import cn.gxf.Utils.UUIDUtil;
import cn.gxf.actuator.executor.DefaultHeader;
import cn.gxf.connector.codec.ProtocolType;
import cn.gxf.connector.protocol.SITP;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author V
 * @Classname Request
 * @Description 请求信息
 **/
@Data
public class Request extends AbstarctMessage{
    private DefaultHeader header;

    private String appName;
    private String funcName;
    private String serviceName;
    // TODO: 2019/3/13 need to change list to map???
    private ArrayList params;

    private String remoteHost;
    private String remotePort;
    private String localHost;
    private String localPort;

    public static Request getSitpReq(SITP sitp){
        Request request = new Request();
        DefaultHeader header = DefaultHeader.getDefReqHeader();
        header.setType(ProtocolType.SITP);
        header.setSessionId(UUIDUtil.getUUID16());
        request.setHeader(header);
        request.setAppName(sitp.getAppName());
        request.setFuncName(sitp.getFunctionName());
        request.setServiceName(sitp.getServiceName());
        request.setParams(sitp.getParams());
        return request;
    }

}
