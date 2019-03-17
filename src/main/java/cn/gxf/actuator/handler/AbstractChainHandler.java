package cn.gxf.actuator.handler;/**
 * Created by VLoye on 2019/2/3.
 */

import java.util.LinkedList;

/**
 * @author V
 * @Classname AbstractChainHandler
 * @Description
 **/
public abstract class AbstractChainHandler implements IChainHandler{
    protected LinkedList chain = new LinkedList();

    @Override
    public IChainHandler addLast(IHandler handler) {
        chain.addLast(handler);
        return this;
    }

    @Override
    public IChainHandler addFirst(IHandler handler) {
        chain.addFirst(handler);
        return this;
    }
}
