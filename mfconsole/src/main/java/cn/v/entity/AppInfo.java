package cn.v.entity;/**
 * Created by VLoye on 2019/3/16.
 */


import lombok.Data;

import java.util.List;

/**
 * @author V
 * @Classname AppInfo
 * @Description
 **/
@Data
public class AppInfo {
    private String appName;
    private List<ServiceInfo> services;


}
