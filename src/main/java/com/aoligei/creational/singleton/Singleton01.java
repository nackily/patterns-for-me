package com.aoligei.creational.singleton;

/**
 * 饿汉式
 *
 * @author coder
 * @date 2022-05-25 17:05:39
 * @since 1.0.0
 */
public class Singleton01 {

    private Singleton01(){}

    private static final Singleton01 INSTANCE = new Singleton01();

    public static Singleton01 getInstance() {
        return INSTANCE;
    }
}
