package com.aoligei.structural.facade.equip;

import com.aoligei.structural.facade.Equipment;

/**
 * 电灯泡
 *
 * @author coder
 * @date 2022-06-17 13:03:55
 * @since 1.0.0
 */
public abstract class Bulb implements Equipment {

    @Override
    public void on() {
        System.out.println("        " + this.attachEffects() + "已打开");
    }

    @Override
    public void off() {
        System.out.println("        " + this.attachEffects() + "已关闭");
    }

    @Override
    public void showEffects() {
        System.out.println("        " + this.attachEffects());
    }

    /**
     * 灯光效果
     * @return 效果
     */
    public abstract String attachEffects();
}
