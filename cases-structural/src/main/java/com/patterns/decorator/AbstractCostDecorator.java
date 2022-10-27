package com.patterns.decorator;

/**
 * 抽象的装饰器
 *
 * @author coder
 * @date 2022-09-02 22:21:38
 * @since 1.0.0
 */
public abstract class AbstractCostDecorator implements CostCalculator {

    /**
     * 包装的费用计算器
     */
    protected final CostCalculator calculator;

    public AbstractCostDecorator(CostCalculator calculator) {
        this.calculator = calculator;
    }

}
