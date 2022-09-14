package com.aoligei.structural.facade.equip.bulb;

import com.aoligei.structural.facade.equip.Bulb;

/**
 * 绿灯
 *
 * @author coder
 * @date 2022-06-17 13:36:45
 * @since 1.0.0
 */
public class GreenBulb extends Bulb {

    @Override
    public String attachEffects() {
        return "绿灯灯光";
    }

}
