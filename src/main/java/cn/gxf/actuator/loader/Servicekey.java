package cn.gxf.actuator.loader;/**
 * Created by VLoye on 2018/12/29.
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author V
 * @Classname Servicekey
 * @Description 服务内存化唯一标识，持有服务的一些基本属性
 **/
@EqualsAndHashCode
@Data
@ToString
public class Servicekey {
    private String className;
    private String methodName;
    private Object[] paramType;
}
