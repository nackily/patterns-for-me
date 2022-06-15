package com.aoligei.behavioral.strategy;

/**
 * 出行
 *
 * @author coder
 * @date 2022-06-14 15:27:06
 * @since 1.0.0
 */
public class Travel {

    private final DepartureStrategy departureStrategy;
    private final EpidemicPreventionOfDestStrategy preventionOfDestStrategy;

    public Travel(DepartureStrategy departureStrategy, EpidemicPreventionOfDestStrategy preventionOfDestStrategy) {
        this.departureStrategy = departureStrategy;
        this.preventionOfDestStrategy = preventionOfDestStrategy;
    }

    /**
     * 离开出发地
     */
    public void leaveDeparture() {
        this.departureStrategy.requirementsOfDeparture();
    }

    /**
     * 进入目的地
     */
    public void enterDestination() {
        this.preventionOfDestStrategy.prevent();
    }

}
