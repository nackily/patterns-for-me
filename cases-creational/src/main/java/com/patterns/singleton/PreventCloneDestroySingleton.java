package com.patterns.singleton;

/**
 * 阻止克隆破坏单例的案例
 *
 * @author coder
 * @date 2022-05-26 14:52:57
 * @since 1.0.0
 */
public class PreventCloneDestroySingleton implements Cloneable {

    private PreventCloneDestroySingleton(){}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // return super.clone();
        return INSTANCE;
    }

    private static PreventCloneDestroySingleton INSTANCE = new PreventCloneDestroySingleton();

    public static PreventCloneDestroySingleton getInstance() {
        return INSTANCE;
    }
}
