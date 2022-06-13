package com.aoligei.behavioral.observer;

/**
 * 主题
 *
 * @author xg-ran
 * @date 2022-05-13 09:56:05
 * @since 1.0.0
 */
public interface Subject {

    /**
     * 注册观察者
     * @param observer observer
     */
    void registerObserver(Observer observer);

    /**
     * 注销观察者
     * @param observer observer
     */
    void unregisterObserver(Observer observer);

    /**
     * 当主题状态变化时，通知所有观察者
     */
    void notifyObservers();

}
