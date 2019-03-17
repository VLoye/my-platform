package cn.gxf;/**
 * Created by VLoye on 2019/1/13.
 */

import cn.gxf.actuator.ActuatorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author V
 * @Classname TestController
 * @Description
 **/
@RestController
public class TestController {
    @Autowired
    ActuatorConfig config;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String testConfig(){
        return String.valueOf(config.getMaxSize());
    }
}
