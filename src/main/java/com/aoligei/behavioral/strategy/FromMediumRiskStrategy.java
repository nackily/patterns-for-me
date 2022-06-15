package com.aoligei.behavioral.strategy;

/**
 * 来自于中风险地区的防疫政策
 *
 * @author coder
 * @date 2022-06-14 14:41:20
 * @since 1.0.0
 */
public class FromMediumRiskStrategy implements EpidemicPreventionOfDestStrategy {
    @Override
    public void prevent() {
        System.out.println("    来自中风险区的防疫措施：");
        System.out.println("        实施居家隔离直至抵川后满7天，第1、3、7天进行咽拭子核酸检测，最后1次双采双检阴性后解除隔离。");
    }
}
