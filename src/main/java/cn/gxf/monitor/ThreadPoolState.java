package cn.gxf.monitor;/**
 * Created by VLoye on 2019/3/16.
 */

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ThreadPoolStata
 * @Description
 **/
@Data
public class ThreadPoolState {
    private int corePoolSize;
    private int maxPoolSize;
    private int activeCount;
    private long taskCount;
    private long completedTaskCount;

}
