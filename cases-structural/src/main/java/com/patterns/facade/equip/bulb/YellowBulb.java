package com.patterns.facade.equip.bulb;

import com.patterns.facade.equip.Bulb;

/**
 * 黄灯
 *
 * @author coder
 * @date 2022-06-17 13:36:45
 * @since 1.0.0
 */
public class YellowBulb extends Bulb {

    @Override
    public String attachEffects() {
        return "黄色灯光";
    }

}
