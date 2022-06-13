package com.aoligei.structural.decorator;

/**
 * 羊肉配料
 *
 * @author xg-ran
 * @date 2022-05-24 16:24:07
 * @since 1.0.0
 */
public class MuttonDecorator extends BreakfastDecorator {

    public MuttonDecorator(Breakfast breakfast) {
        super(breakfast);
    }

    @Override
    public String getDescription() {
        return this.breakfast.getDescription() + " + 羊肉";
    }

    @Override
    public int cost() {
        return this.breakfast.cost() + 5;
    }
}
