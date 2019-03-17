package cn.gxf.connector;/**
 * Created by VLoye on 2019/3/12.
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author V
 * @Classname ConnConfig
 * @Description
 **/
@Configuration
@ConfigurationProperties(prefix = "conn")
@Data
public class ConnConfig {
    private String host;
    private int port;
    private int bossThreads;
    private int workerThreads;

}
