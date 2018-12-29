package com.v.Actuator;

/**
 * @Description 应用接口
 *  一个jar包应用一个应用
 *  通过管理jar包来部署/卸载应用，实现热部署
 * @Author V
**/
public interface App {




    //加载应用
    void load(String serviceName);
    void loadAll();

    //卸载应用
    void unload(String serviceName);
    void unloadAll();

//    void suspend(String serviceName);
}
