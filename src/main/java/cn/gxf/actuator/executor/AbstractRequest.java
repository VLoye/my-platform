package cn.gxf.actuator.executor;/**
 * Created by VLoye on 2019/3/9.
 */

import cn.gxf.core.Request;

/**
 * @author V
 * @Classname AbstractRequest
 * @Description
 **/

public class AbstractRequest extends Request {
    protected long uuid;
    protected long timestamp;
    protected boolean replay;

}
