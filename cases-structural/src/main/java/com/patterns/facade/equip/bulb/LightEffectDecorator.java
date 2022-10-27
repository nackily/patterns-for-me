package com.patterns.facade.equip.bulb;

import com.patterns.facade.equip.Bulb;

/**
 * 灯效装饰器
 *
 * @author coder
 * @date 2022-06-17 13:38:43
 * @since 1.0.0
 */
public abstract class LightEffectDecorator extends Bulb {

    protected final Bulb bulb;

    public LightEffectDecorator(Bulb bulb) {
        this.bulb = bulb;
    }
}
