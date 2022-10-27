package com.patterns.facade.equip.bulb;

import com.patterns.facade.equip.Bulb;

/**
 * 红灯
 *
 * @author coder
 * @date 2022-06-17 13:36:45
 * @since 1.0.0
 */
public class RedBulb extends Bulb {

    @Override
    public String attachEffects() {
        return "红色灯光";
    }

}
