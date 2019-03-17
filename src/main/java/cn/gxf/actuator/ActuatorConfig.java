package cn.gxf.actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * @author V
 * @Classname ActuatorConfig
 * @Description
 **/
@Data
@Component
@ConfigurationProperties(prefix = "actuator")
public class ActuatorConfig {
    private String name;
    private Boolean isLocalFirst;
    private boolean preLoad;
    private int coreSize;
    private int maxSize;
    private long keepAlive;
    private TimeUnit timeUnit;
    private int queueSize;


    private String appsAbsolutePath;


    public ActuatorConfig() {
        this.isLocalFirst = true;
        this.coreSize = 8;
        this.maxSize = 16;
        this.keepAlive = 5;
        this.timeUnit = TimeUnit.SECONDS;
        this.queueSize = 512;
        this.preLoad = true;
    }

}
