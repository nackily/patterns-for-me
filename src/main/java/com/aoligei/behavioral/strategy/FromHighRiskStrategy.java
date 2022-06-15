package com.aoligei.behavioral.strategy;

/**
 * 来自于高风险地区的防疫政策
 *
 * @author coder
 * @date 2022-06-14 14:41:20
 * @since 1.0.0
 */
public class FromHighRiskStrategy implements EpidemicPreventionOfDestStrategy {
    @Override
    public void prevent() {
        System.out.println("    来自高风险区的防疫措施：");
        System.out.println("        实施集中隔离直至抵川后满7天，第1、3、7天进行咽拭子核酸检测，最后1次双采双检阴性后解除隔离；");
        System.out.println("        解除集中隔离后，实施7天居家健康监测。");
    }
}
