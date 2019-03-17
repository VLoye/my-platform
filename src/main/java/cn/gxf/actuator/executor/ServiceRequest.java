package cn.gxf.actuator.executor;/**
 * Created by VLoye on 2019/3/9.
 */

import lombok.Data;

/**
 * @author V
 * @Classname ServiceRequest
 * @Description
 **/
@Data
public class ServiceRequest extends AbstractRequest{
    private String appName;
    private String sceneName;
    private String serviceName;

}
