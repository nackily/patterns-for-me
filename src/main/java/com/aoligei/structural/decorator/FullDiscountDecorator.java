package com.aoligei.structural.decorator;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * 满减券类型的费用装饰器
 *
 * @author coder
 * @date 2022-09-02 22:28:10
 * @since 1.0.0
 */
public class FullDiscountDecorator extends AbstractCostDecorator {

    /**
     * 满减券面额
     */
    private final BigDecimal amount;

    /**
     * 满减门槛
     */
    private final BigDecimal threshold;

    public FullDiscountDecorator(CostCalculator calculator, BigDecimal amount, BigDecimal threshold) {
        super(calculator);
        this.amount = amount;
        this.threshold = threshold;
    }

    @Override
    public String description() {
        if (aboveThreshold()) {
            return calculator.description() +
                    MessageFormat.format(" -> 满{0}减{1}", threshold, amount);
        }
        return null;
    }

    @Override
    public BigDecimal finalCost() {
        if (aboveThreshold()) {
            // 费用 - 减去优惠面额
            return calculator.finalCost().subtract(amount);
        }
        // 无法满减 -> 原价
        return calculator.finalCost();
    }

    /**
     * 原始金额是否高于满减门槛
     * @return true:是
     */
    public boolean aboveThreshold () {
        return calculator.finalCost().compareTo(threshold) > 0;
    }
}
