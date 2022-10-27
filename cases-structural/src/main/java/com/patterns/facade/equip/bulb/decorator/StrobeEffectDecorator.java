package com.patterns.facade.equip.bulb.decorator;

import com.patterns.facade.equip.Bulb;
import com.patterns.facade.equip.bulb.LightEffectDecorator;

/**
 * 频闪灯效
 *
 * @author coder
 * @date 2022-06-17 13:45:13
 * @since 1.0.0
 */
public class StrobeEffectDecorator extends LightEffectDecorator {

    public StrobeEffectDecorator(Bulb bulb) {
        super(bulb);
    }

    @Override
    public String attachEffects() {
        return "频闪效果的" + super.bulb.attachEffects();
    }
}
