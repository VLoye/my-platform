package cn.gxf.ctrl.entity;/**
 * Created by VLoye on 2019/3/16.
 */

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author V
 * @Classname ServiceInfo
 * @Description
 **/
@Data
public class ServiceInfo {
    private String name;
    private String decription;
    private Class<?> returnType;
    private Class<?>[] paramsType;

}
