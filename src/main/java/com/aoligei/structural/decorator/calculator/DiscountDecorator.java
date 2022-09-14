package com.aoligei.structural.decorator.calculator;

import com.aoligei.structural.decorator.AbstractCostDecorator;
import com.aoligei.structural.decorator.CostCalculator;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * 折扣券类型的费用装饰器
 *
 * @author coder
 * @date 2022-09-02 22:28:10
 * @since 1.0.0
 */
public class DiscountDecorator extends AbstractCostDecorator {

    /**
     * 折扣
     */
    private final BigDecimal discount;

    public DiscountDecorator(CostCalculator calculator, BigDecimal discount) {
        super(calculator);
        this.discount = discount;
    }

    @Override
    public String description() {
        return calculator.description() +
                MessageFormat.format(" -> {0}折扣", discount.multiply(BigDecimal.valueOf(10)));
    }

    @Override
    public BigDecimal finalCost() {
        // 费用 * 折扣
        return calculator.finalCost().multiply(discount);
    }
}
