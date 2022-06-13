package com.aoligei.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气
 *
 * @author xg-ran
 * @date 2022-05-13 11:01:13
 * @since 1.0.0
 */
public class Weather implements Subject{

    private Object state;
    private final List<Observer> observers = new ArrayList<Observer>();

    /**
     * 更新主题状态
     * @param state 消息
     */
    public void setState(String state) {
        this.state = state;
        this.notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(o -> o.update(state));
    }

}
