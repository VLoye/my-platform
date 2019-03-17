package cn.gxf.actuator.handler;/**
 * Created by VLoye on 2019/3/11.
 */

/**
 * @author V
 * @Classname ChainHandlerFactory
 * @Description
 **/
public class ChainHandlerFactory {
    public static IChainHandler getDefaultChain(){
        IChainHandler chain = new DefaultChainHandler();


        chain.addLast(new ResponseInitHandler());
        chain.addLast(new ServiceHandler());
//        chain.addLast(new ResponseHandler());

        return chain;
    }
}
