package cn.v;/**
 * Created by VLoye on 2019/4/5.
 */

import cn.v.entity.JSONUtil;
import cn.v.entity.HttpResult;



/**
 * @author V
 * @Classname CommandExecutor
 * @Description
 **/
public class CommandExecutor {

    private static final String URL = "http://127.0.0.1:9527";
    private static final String SYNTAX_ERROR = "The syntax of the command is incorrect. Please retry or input 'help' for help";
    private HttpUtil util = new HttpUtil();


    public void execute(String[] args) {
        String command = args[0];
        HttpResult result = null;
        switch (command) {
            case CommandConstant.BUSYTHREAD: {
                result = util.sendGet(URL + "/monitor/busyThreads");
                System.out.println(JSONUtil.toJsonFormat(result.getRsp()));
                break;
            }
            case CommandConstant.SERVICES: {
                if (args.length == 2) {
                    String appName = args[1];
                    result = util.sendGet(URL + "/app/get/" + appName);
                    System.out.println(JSONUtil.toJsonFormat(result.getRsp()));
                } else if (args.length == 1) {
                    result = util.sendGet(URL + "/service/list");
                    System.out.println(JSONUtil.toJsonFormat(result.getRsp()));
                } else
                    System.out.println(SYNTAX_ERROR);
                break;
            }
            case CommandConstant.THREADINFO: {
                result = util.sendGet(URL + "/monitor/threadPoolState");
                System.out.println(JSONUtil.toJsonFormat(result.getRsp()));
                break;
            }
            case CommandConstant.DEPLOY:{
                if (args.length == 2) {
                    String appName = args[1];
                    result = util.sendPost(URL + "/app/deploy/" + appName);
                    System.out.println(JSONUtil.toJsonFormat(result.getRsp()));
                } else
                    System.out.println(SYNTAX_ERROR);
                break;
            }
            case CommandConstant.UNINSTALL:{
                if (args.length == 2) {
                    String appName = args[1];
                    result = util.sendPost(URL + "/app/uninstall/" + appName);
                    System.out.println(JSONUtil.toJsonFormat(result.getRsp()));
                } else
                    System.out.println(SYNTAX_ERROR);
                break;
            }
            case CommandConstant.SHUTDOWN:{
                result = util.sendPost(URL+"/actuator/shutdown");
                if(result.isSuccess()){
                    System.out.println("My-Platform system is shutting down");
                }
                break;
            }
            case CommandConstant.HELP:{
                System.out.println("services                获取系统当前部署的所有应用的业务服务信息");
                System.out.println("services [appName]      获取系统当前部署的指定应用的业务服务信息");
                System.out.println("deploy [appName]        部署指定应用服务");
                System.out.println("uninstall [appName]     卸载指定应用服务");
                System.out.println("threadInfo              获取核心处理器线程池状态信息");
                System.out.println("busyThread              核心处理器当前正在执行的交易信息");
                System.out.println("shutdown                关闭MyPlatform系统");
                System.out.println("help                    获取命令帮助");

                break;
            }
            default:
                System.out.println(SYNTAX_ERROR);
        }

    }

}
