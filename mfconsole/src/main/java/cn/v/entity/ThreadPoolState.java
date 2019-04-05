package cn.v.entity;/**
 * Created by VLoye on 2019/3/16.
 */

import lombok.Data;

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
