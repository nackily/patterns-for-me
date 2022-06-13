package com.aoligei.structural.decorator;

/**
 * 抽象的配料装饰器
 *
 * @author coder
 * @date 2022-05-24 16:16:40
 * @since 1.0.0
 */
public abstract class BreakfastDecorator implements Breakfast {

    /**
     * 包装的具体组件
     */
    protected Breakfast breakfast;

    public BreakfastDecorator(Breakfast breakfast) {
        this.breakfast = breakfast;
    }
}
