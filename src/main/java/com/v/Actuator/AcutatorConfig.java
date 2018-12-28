package com.v.Actuator;/**
 * Created by VLoye on 2018/12/27.
 */

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;


/**
 * @author V
 * @Classname AcutatorConfig
 * @Description
 **/
@Data
@ConfigurationProperties(prefix = "actuator")
public class AcutatorConfig {
    private Boolean isLocalFirst;
    private boolean preLoad;
    private int coreSize;
    private int maxSize;
    private long keepAlive;
    private TimeUnit timeUnit;
    private int queueSize;


    public AcutatorConfig() {
        this.isLocalFirst = true;
        this.coreSize = 8;
        this.maxSize = 16;
        this.keepAlive = 5;
        this.timeUnit = TimeUnit.SECONDS;
        this.queueSize = 512;
        this.preLoad = true;
    }

}
