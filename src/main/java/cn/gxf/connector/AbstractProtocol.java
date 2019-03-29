package cn.gxf.connector;/**
 * Created by VLoye on 2019/3/12.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author V
 * @Classname AbstractProtocol
 * @Description
 **/
public abstract class AbstractProtocol<T> implements IProtocol<T>{
    private static final Logger logger = LoggerFactory.getLogger(AbstractProtocol.class);
    private static final String stringClass = "java.lang.String";
    private static final String intClass = "java.lang.Integer";
    private static final String doubleClass = "java.lang.Double";
    private static final String bigDecimalClass = "java.math.BigDecimal";


    protected Object getParams(String className, String value) {
        switch (className){
            case stringClass :return value;
            case intClass: return Integer.valueOf(value);
            case doubleClass:return Double.valueOf(value);
            case bigDecimalClass:return BigDecimal.valueOf(Long.valueOf(value));
        }

        logger.error("No such type: [{}]",className);
        return null;
    }

}
