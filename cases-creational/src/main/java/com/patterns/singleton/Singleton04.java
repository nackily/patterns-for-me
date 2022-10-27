package com.patterns.singleton;

/**
 * 静态内部类
 *
 * @author coder
 * @date 2022-05-25 17:16:04
 * @since 1.0.0
 */
public class Singleton04 {

    private Singleton04(){}

    public static Singleton04 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton04 INSTANCE = new Singleton04();
    }
}
