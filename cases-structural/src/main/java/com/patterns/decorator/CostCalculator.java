package com.patterns.decorator;

import java.math.BigDecimal;

/**
 * 费用计算器
 *
 * @author coder
 * @date 2022-09-02 21:57:10
 * @since 1.0.0
 */
public interface CostCalculator {

    /**
     * 计算描述
     * @return 描述
     */
    String description();

    /**
     * 商品的最终费用
     * @return 最终费用
     */
    BigDecimal finalCost();

}
