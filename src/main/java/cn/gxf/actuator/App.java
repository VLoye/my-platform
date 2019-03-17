package cn.gxf.actuator;

/**
 * @Description 应用接口
 * 一个jar包应用一个应用
 * 通过管理jar包来部署/卸载应用，实现热部署
 * @Author V
 **/
public interface App {


    //加载应用
    void load(String serviceName) throws Exception;

    void loadAll() throws Exception;

    //卸载应用
    void unload(String serviceName) throws Exception;

    void unloadAll() throws Exception;

//    void suspend(String serviceName);

}
