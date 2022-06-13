package com.aoligei.structural.decorator;

/**
 * 粉条
 *
 * @author coder
 * @date 2022-05-24 16:15:38
 * @since 1.0.0
 */
public class Vermicelli implements Breakfast {
    @Override
    public String getDescription() {
        return "粉条";
    }

    @Override
    public int cost() {
        return 7;
    }
}
