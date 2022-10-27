package com.patterns.singleton;

/**
 * 阻止反射破坏单例的案例
 *
 * @author coder
 * @date 2022-05-26 14:52:13
 * @since 1.0.0
 */
public class PreventReflexDestroySingleton {

    private PreventReflexDestroySingleton(){
        if (INSTANCE != null) {
            throw new RuntimeException("已经有实例了");
        }
    }

    private static PreventReflexDestroySingleton INSTANCE = new PreventReflexDestroySingleton();

    public static PreventReflexDestroySingleton getInstance() {
        return INSTANCE;
    }
}
