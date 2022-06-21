package com.aoligei.structural.proxy.jdk_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * ProxyInvocationHandler
 *
 * @author coder
 * @date 2022-06-21 17:42:09
 * @since 1.0.0
 */
public class ProxyInvocationHandler implements InvocationHandler {

    /**
     * 被代理对象
     */
    private Object target;

    /**
     * 设置目标对象
     * @param target 目标对象
     */
    protected void setTarget(Object target){
        this.target = target;
    }

    /**
     * 获取代理对象
     * @return 代理对象
     */
    protected Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=>> 代理对象的Java类型：" + proxy.getClass().getSimpleName());
        System.out.println("=>> 调用的方法名：" + method.getName());
        System.out.println("=>> 调用方法的参数：" + Arrays.toString(args));

        System.out.println("    =>> 执行目标方法之前");
        Object res = method.invoke(target, args);
        System.out.println("    =>> 返回：" + res);
        System.out.println("    =>> 执行目标方法之后");
        return res;
    }

}
