package com.aoligei.structural.decorator;

/**
 * 早餐
 *
 * @author xg-ran
 * @date 2022-05-24 16:07:03
 * @since 1.0.0
 */
public interface Breakfast {

    /**
     * 描述
     * @return 早餐的内容
     */
    String getDescription();

    /**
     * 计算费用
     * @return 早餐的价格
     */
    int cost();

}
