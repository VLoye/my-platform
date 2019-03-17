package cn.gxf.actuator.loader;/**
 * Created by VLoye on 2019/2/13.
 */


import cn.gxf.actuator.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname SingletonBean
 * @Description
 **/
public class SingletonBean extends AbstractBean implements IBean {
    private static final Logger logger = LoggerFactory.getLogger(SingletonBean.class);

    public SingletonBean(Application app, Class<?> clazz) {
        super(app, clazz);
    }


    @Override
    public Object getInstance(){
        if(instance == null){
            try {
                instance = createBean();
            } catch (Exception e) {
                logger.error("Create Bean error, cause by: {}", e);
                e.printStackTrace();
            }
        }
        return instance;

    }


}
