package cn.gxf.core;/**
 * Created by VLoye on 2019/2/3.
 */

import cn.gxf.actuator.executor.DefaultHeader;
import lombok.Data;

/**
 * @author V
 * @Classname Response
 * @Description
 **/
@Data
public class Response extends AbstarctMessage{
    private DefaultHeader header;
    private boolean success;
    private Throwable e;
    private Object res;

}
