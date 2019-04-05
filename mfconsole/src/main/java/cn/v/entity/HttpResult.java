package cn.v.entity;/**
 * Created by VLoye on 2019/4/5.
 */

import lombok.Data;


/**
 * @author V
 * @Classname HttpResult
 * @Description
 **/
@Data
public class HttpResult {
    private boolean isSuccess;
    private String rsp;

    public HttpResult(boolean isSuccess, String rsp) {
        this.isSuccess = isSuccess;
        this.rsp = rsp;
    }

    public HttpResult(String rsp) {
        this(true,rsp);
    }

    public HttpResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
