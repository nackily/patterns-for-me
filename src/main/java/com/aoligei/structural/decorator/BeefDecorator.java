package com.aoligei.structural.decorator;

/**
 * 牛肉配料
 *
 * @author xg-ran
 * @date 2022-05-24 16:24:07
 * @since 1.0.0
 */
public class BeefDecorator extends BreakfastDecorator {

    public BeefDecorator(Breakfast breakfast) {
        super(breakfast);
    }

    @Override
    public String getDescription() {
        return this.breakfast.getDescription() + " + 牛肉";
    }

    @Override
    public int cost() {
        return this.breakfast.cost() + 3;
    }
}
