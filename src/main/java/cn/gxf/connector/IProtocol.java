package cn.gxf.connector;/**
 * Created by VLoye on 2019/3/12.
 */


import java.io.IOException;

/**
 * @author V
 * @Classname IProtocol
 * @Description
 **/
public interface IProtocol<T> {
    byte[] encode(T t) throws IOException;
    T decoder(byte[] data);
}
