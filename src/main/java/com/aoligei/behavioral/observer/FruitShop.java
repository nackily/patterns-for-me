package com.aoligei.behavioral.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * 水果店
 *
 * @author coder
 * @date 2022-07-14 17:40:59
 * @since 1.0.0
 */
public class FruitShop {

    /**
     * 最新到货的水果
     */
    private String latestArrivalFruit;

    /**
     * 通知列表
     */
    private final Set<Observer> observers = new HashSet<>();

    /**
     * 水果上新
     * @param arrivalFruit 到货的水果
     */
    public void newest(String arrivalFruit) {
        System.out.println("    当前新到水果：" + arrivalFruit);
        this.latestArrivalFruit = arrivalFruit;
        notifyObservers();
    }

    /**
     * 顾客登记
     * @param o 顾客
     */
    public void register(Observer o) {
        System.out.println("    顾客登记：" + o.getIdentityInfo());
        this.observers.add(o);
    }

    /**
     * 顾客注销
     * @param o 顾客
     */
    public void unregister(Observer o) {
        System.out.println("    顾客注销：" + o.getIdentityInfo());
        this.observers.remove(o);
    }

    /**
     * 通知所有顾客
     */
    public void notifyObservers() {
        System.out.println("    通知所有顾客...");
        this.observers.forEach(item -> item.accept(this.latestArrivalFruit));
    }
}
