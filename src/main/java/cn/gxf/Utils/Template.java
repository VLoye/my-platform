package cn.gxf.Utils;/**
 * Created by VLoye on 2019/3/13.
 */

import cn.gxf.core.Constants;
import cn.gxf.core.DefaultHeader;
import cn.gxf.core.Request;
import cn.gxf.connector.codec.ProtocolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author V
 * @Classname Template
 * @Description
 **/
public class Template {
    private static final Logger logger = LoggerFactory.getLogger(Template.class);
    public static Request getRequestTemplate(){
        Request request = new Request();
        DefaultHeader header = new DefaultHeader();
        header.setMagic(Constants.magic);
        header.setVersion((byte) 1);
        header.setReply(true);
        header.setType(ProtocolType.SITP);
        request.setHeader(header);
        request.setAppName("trace");
        request.setFuncName("cal");
        request.setServiceName("add");
        ArrayList params = new ArrayList();
        params.add(1);
        params.add("test");
        request.setParams(params);
        return request;
    }

}
