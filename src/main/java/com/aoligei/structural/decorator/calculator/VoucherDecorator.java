package com.aoligei.structural.decorator.calculator;

import com.aoligei.structural.decorator.AbstractCostDecorator;
import com.aoligei.structural.decorator.CostCalculator;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * 抵用券类型的费用装饰器
 *
 * @author coder
 * @date 2022-09-02 22:28:10
 * @since 1.0.0
 */
public class VoucherDecorator extends AbstractCostDecorator {

    /**
     * 抵用券面额
     */
    private final BigDecimal amount;

    public VoucherDecorator(CostCalculator calculator, BigDecimal amount) {
        super(calculator);
        this.amount = amount;
    }

    @Override
    public String description() {
        return calculator.description() +
                MessageFormat.format(" -> {0}元抵用券", amount);
    }

    @Override
    public BigDecimal finalCost() {
        // 费用 - 减去抵用券面额
        return calculator.finalCost().subtract(amount);
    }
}
