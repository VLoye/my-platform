package cn.v;/**
 * Created by VLoye on 2019/4/5.
 */


import cn.v.entity.HttpResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import java.io.IOException;


/**
 * @author V
 * @Classname HttpUtil
 * @Description
 **/
public class HttpUtil {


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
