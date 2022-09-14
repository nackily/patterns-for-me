package com.aoligei.structural.facade.equip.bulb.decorator;

import com.aoligei.structural.facade.equip.Bulb;
import com.aoligei.structural.facade.equip.bulb.LightEffectDecorator;

/**
 * 跑马灯灯效
 *
 * @author coder
 * @date 2022-06-17 13:45:13
 * @since 1.0.0
 */
public class MarqueeEffectDecorator extends LightEffectDecorator {

    public MarqueeEffectDecorator(Bulb bulb) {
        super(bulb);
    }

    @Override
    public String attachEffects() {
        return "跑马灯效果的" + super.bulb.attachEffects();
    }
}
