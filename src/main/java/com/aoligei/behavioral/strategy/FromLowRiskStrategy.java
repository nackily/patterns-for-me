package com.aoligei.behavioral.strategy;

/**
 * 来自于低风险地区的防疫政策
 *
 * @author coder
 * @date 2022-06-14 14:41:20
 * @since 1.0.0
 */
public class FromLowRiskStrategy implements EpidemicPreventionOfDestStrategy {
    @Override
    public void prevent() {
        System.out.println("    来自低风险区的防疫措施：");
        System.out.println("        实施3天2次（间隔24小时）咽拭子核酸检测。");
    }
}
