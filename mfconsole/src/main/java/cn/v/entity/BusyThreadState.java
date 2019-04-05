package cn.v.entity;/**
 * Created by VLoye on 2019/3/16.
 */

import lombok.Data;

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
