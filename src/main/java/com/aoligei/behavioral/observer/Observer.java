package com.aoligei.behavioral.observer;

/**
 * 观察者
 *
 * @author coder
 * @date 2022-05-13 09:56:05
 * @since 1.0.0
 */public interface Observer {

    /**
     * 每当观察到的对象发生变化时，都会调用此方法
     * @param o 主题状态改变时通知的消息对象
     */
    void update(Object o);
}

