package com.aoligei.structural.proxy.jdk_proxy;

/**
 * Client
 *
 * @author coder
 * @date 2022-06-21 17:13:45
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        // 在 jdk 提供的动态代理中，有两个重要的类，一个是 Proxy，一个是 InvocationHandler。
        // Proxy 提供了一个静态方法 newProxyInstance 来生成代理对象，三个参数分别是：
        //     类加载器：决定哪个类加载器来加载代理类；
        //     接口列表：代理类要实现的接口列表，这个参数决定代理类生成后有哪些方法；
        //     调用处理程序：在调用代理对象的目标方法时的处理逻辑。
        // InvocationHandler 提供了一个 invoke 方法，该方法的逻辑会被附加到代理对象的目标方法中，三个参数分别是：
        //     代理对象：生成的代理对象；
        //     方法：客户端调用的方法（代理的方法可能不止一个）；
        //     调用参数：调用代理对象时，传入的参数。
        System.out.println("|==> Start -----------------------------------------------------|");
        // 实例调用处理程序
        ProxyInvocationHandler handler = new ProxyInvocationHandler();
        // 指定目标对象
        handler.setTarget(new AnyServiceImpl());
        // 生成代理对象
        AnyService proxy = (AnyService) handler.getProxy();
        // 调用目标方法
        proxy.targetFunc1(3);
    }
}
