package com.aoligei.behavioral.strategy;

/**
 * 出发地出行政策
 *
 * @author coder
 * @date 2022-06-14 15:13:48
 * @since 1.0.0
 */
public interface DepartureStrategy {

    /**
     * 离开出发地的出行要求
     */
    void requirementsOfDeparture();

}
