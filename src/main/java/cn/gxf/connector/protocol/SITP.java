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
    private String appName;
    private String functionName;
    private String serviceName;
    private ArrayList params;

}
