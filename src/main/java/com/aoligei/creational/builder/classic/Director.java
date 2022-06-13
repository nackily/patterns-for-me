package com.aoligei.creational.builder.classic;


/**
 * Director
 *
 * @author coder
 * @date 2022-05-30 11:35:24
 * @since 1.0.0
 */
public class Director {

    private final AbstractBuilder builder;

    public Director(AbstractBuilder builder) {
        this.builder = builder;
    }

    public void construct () {
        this.builder.buildStapleFood();
        this.builder.buildSideDish();
        this.builder.buildSnack();
        this.builder.buildDrinks();
    }
}
