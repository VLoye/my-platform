package cn.gxf.actuator.loader;/**
 * Created by VLoye on 2019/2/18.
 */

import cn.gxf.actuator.Actuator;
import cn.gxf.actuator.Application;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author V
 * @Classname AbstractBean
 * @Description
 **/

public abstract class AbstractBean implements IBean {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBean.class);

    public AbstractBean(Application app, Class<?> clazz) {
        this.app = app;
        this.clazz = clazz;
    }


    protected Application app;
    protected Class<?> clazz;
    protected Object instance;
    protected BeanType beanType = BeanType.Singleton;


    protected Object createBean() throws Exception {
        Object instance = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DI.class)) {
                Class c1 = field.getType();
                AbstractBean bean = (AbstractBean) app.getIOC().get(c1);
                if(bean == null){
                    throw new Exception(String.format("Bean %s inject bean type %s failure",clazz.toString(),c1.toString()));
                }
                field.setAccessible(true);
                field.set(instance, bean.getInstance());
                field.setAccessible(false);
            }
        }
        return instance;
    }




}
