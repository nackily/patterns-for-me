package com.patterns.facade.equip;

import com.patterns.facade.Equipment;

/**
 * 音箱
 *
 * @author coder
 * @date 2022-06-17 13:53:55
 * @since 1.0.0
 */
public abstract class Speaker implements Equipment {

    @Override
    public void on() {
        System.out.println("        音箱已打开");
    }

    @Override
    public void off() {
        System.out.println("        音箱已关闭");
    }

}
