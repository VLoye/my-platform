package cn.v.entity;/**
 * Created by VLoye on 2019/3/16.
 */

import lombok.Data;

import java.util.Arrays;

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
