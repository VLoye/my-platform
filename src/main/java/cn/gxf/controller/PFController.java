package cn.gxf.controller;

import cn.gxf.MyPlatformServer;
import cn.gxf.monitor.BusyThreadState;
import cn.gxf.monitor.ThreadPoolState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by VLoye on 2018/12/27.
 * 平台控制器，负责管理整个平台的组件以及为组件之间提供互相通讯
 */
@Controller
@RequestMapping("/monitor")
public class PFController {
    @Autowired
    private MyPlatformServer server;

    @RequestMapping("/threadPoolState")
    @ResponseBody
    public ThreadPoolState threadPoolState(){
        ThreadPoolState state = server.getActuator().getThreadPoolState();
        return state;
    }

    @RequestMapping("/busyThreads")
    @ResponseBody
    public List<BusyThreadState> busyThreadState(){
        return server.getActuator().getBusyThreadState();
    }



}
