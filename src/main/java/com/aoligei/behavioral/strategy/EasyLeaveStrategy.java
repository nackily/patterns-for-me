package com.aoligei.behavioral.strategy;

/**
 * 宽松放行政策
 *
 * @author coder
 * @date 2022-06-14 15:22:38
 * @since 1.0.0
 */
public class EasyLeaveStrategy implements DepartureStrategy {
    @Override
    public void requirementsOfDeparture() {
        System.out.println("    离开宽松放行出发地的出行要求：");
        System.out.println("        持健康码绿码。");
    }
}
