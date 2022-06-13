package com.aoligei.structural.decorator;

/**
 * 面条
 *
 * @author xg-ran
 * @date 2022-05-24 16:12:36
 * @since 1.0.0
 */
public class Noodles implements Breakfast {

    @Override
    public String getDescription() {
        return "面条";
    }

    @Override
    public int cost() {
        return 8;
    }

}
