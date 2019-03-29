package cn.gxf.connector.protocol;/**
 * Created by VLoye on 2019/3/12.
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author V
 * @Classname SITP
 * @Description
 **/
@Data
public class SITP {
    private String appName;         //应用名
    private String functionName;    //功能名
    private String serviceName;     //服务名
    private ArrayList params;       //参数

}
