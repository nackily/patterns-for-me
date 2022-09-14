package com.aoligei.structural.facade.equip.bulb.decorator;

import com.aoligei.structural.facade.equip.Bulb;
import com.aoligei.structural.facade.equip.bulb.LightEffectDecorator;

/**
 * 常量灯效
 *
 * @author coder
 * @date 2022-06-17 13:45:13
 * @since 1.0.0
 */
public class BrightEffectDecorator extends LightEffectDecorator {

    public BrightEffectDecorator(Bulb bulb) {
        super(bulb);
    }

    @Override
    public String attachEffects() {
        return "常亮效果的" + super.bulb.attachEffects();
    }
}
