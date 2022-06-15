package com.aoligei.behavioral.strategy;

/**
 * Client
 *
 * @author coder
 * @date 2022-06-14 14:34:11
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        // 假设：当前上海有中风险区，采用宽松出行政策，Jack 从上海到成都
        System.out.println("|==> Jack 从【上海 -> 成都】----------------------------------------------------------|");
        Travel travelForJack = new Travel(new EasyLeaveStrategy(), new FromMediumRiskStrategy());
        travelForJack.leaveDeparture();
        travelForJack.enterDestination();

        // 假设：当前天津为低风险区，采用严格限制出行政策，Tom 从天津到成都
        System.out.println("|==> Tom 从【天津 -> 成都】-----------------------------------------------------------|");
        Travel travelForTom = new Travel(new StrictLeaveStrategy(), new FromLowRiskStrategy());
        travelForTom.leaveDeparture();
        travelForTom.enterDestination();
    }
}
