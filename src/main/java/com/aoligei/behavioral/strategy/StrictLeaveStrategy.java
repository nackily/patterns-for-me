package com.aoligei.behavioral.strategy;

/**
 * 严格放行政策
 *
 * @author coder
 * @date 2022-06-14 15:22:38
 * @since 1.0.0
 */
public class StrictLeaveStrategy implements DepartureStrategy {
    @Override
    public void requirementsOfDeparture() {
        System.out.println("    离开严格放行出发地的出行要求：");
        System.out.println("        需持48小时核酸检测阴性证明和健康码绿码。");
    }
}
