package com.patterns.proxy.jdk_proxy;

/**
 * 被代理类
 *
 * @author coder
 * @date 2022-06-21 17:27:26
 * @since 1.0.0
 */
public class AnyServiceImpl implements AnyService {
    @Override
    public void targetFunc0() {
        System.out.println("    =>> 执行目标方法");
    }

    @Override
    public String targetFunc1(int num) {
        return "Anything";
    }
}
