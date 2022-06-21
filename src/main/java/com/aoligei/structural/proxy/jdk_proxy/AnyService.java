package com.aoligei.structural.proxy.jdk_proxy;

/**
 * 接口
 *
 * @author coder
 * @date 2022-06-21 17:22:47
 * @since 1.0.0
 */
public interface AnyService {

    /**
     * 目标方法_0
     */
    void targetFunc0();

    /**
     * 目标方法_1
     * @param num any number
     * @return anything
     */
    String targetFunc1(int num);

}
