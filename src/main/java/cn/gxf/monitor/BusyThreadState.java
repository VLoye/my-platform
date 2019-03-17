package cn.gxf.monitor;/**
 * Created by VLoye on 2019/3/16.
 */

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname BusyThreadState
 * @Description
 **/
@Data
public class BusyThreadState {
    private String name;
    private String traceId;
    private String executeService;
    private long executetime;
}
