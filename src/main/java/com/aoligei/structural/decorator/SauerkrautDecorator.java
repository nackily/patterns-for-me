package com.aoligei.structural.decorator;

/**
 * 酸菜配料
 *
 * @author coder
 * @date 2022-05-24 16:24:07
 * @since 1.0.0
 */
public class SauerkrautDecorator extends BreakfastDecorator {

    public SauerkrautDecorator(Breakfast breakfast) {
        super(breakfast);
    }

    @Override
    public String getDescription() {
        return this.breakfast.getDescription() + " + 酸菜";
    }

    @Override
    public int cost() {
        return this.breakfast.cost() + 2;
    }

}
