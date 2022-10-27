package com.patterns.singleton;

/**
 * 懒汉式
 *
 * @author coder
 * @date 2022-05-25 17:08:25
 * @since 1.0.0
 */
public class Singleton02 {

    private Singleton02(){}

    private volatile static Singleton02 INSTANCE;

    public static Singleton02 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton02.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton02();
                }
            }
        }
        return INSTANCE;
    }
}
