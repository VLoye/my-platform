package cn.v;/**
 * Created by VLoye on 2019/4/5.
 */

import cn.v.entity.AppInfo;
import cn.v.entity.HttpResult;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author V
 * @Classname HttpUtil
 * @Description
 **/
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public HttpResult sendPost(String url) {
        return doRequest(url,HttpType.POST);
    }

    public HttpResult sendGet(String url) {
        return doRequest(url,HttpType.GET);
    }



    private String entityToMap(HttpEntity entity) {
        String entityStr = null;
        try {
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Map<String, AppInfo> appInfoMap = new HashMap<String, AppInfo>();
//        System.out.println(entityStr);
//        List json = JSONObject.parseArray(entityStr, AppInfo.class);

        return entityStr;
    }


    private HttpResult doRequest(String url,HttpType type){
        HttpClient client = new DefaultHttpClient();
        HttpRequestBase base = null;
        HttpResponse response = null;
            switch (type){
            case GET:
                base= new HttpGet(url);
                break;
            case POST:
                base = new HttpPost(url);
                break;
        }
        try {
            response = client.execute(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("Rsp status code [{}]",response.getStatusLine().getStatusCode());
        if (response.getStatusLine().getStatusCode() == 200){
            return new HttpResult(true,entityToMap(response.getEntity()));
        }
        else
            return new HttpResult(false);
    }


    private enum HttpType{
        GET(),
        POST(),
        ;
    }
}
