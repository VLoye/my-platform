package cn.v.entity;/**
 * Created by VLoye on 2019/4/5.
 */

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.http.HttpClient;

/**
 * @author V
 * @Classname HttpResult
 * @Description
 **/
@Data
public class HttpResult {
    private static final Logger logger = LoggerFactory.getLogger(HttpResult.class);
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
