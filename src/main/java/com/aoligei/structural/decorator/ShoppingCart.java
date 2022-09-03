package com.aoligei.structural.decorator;

import sun.plugin2.message.Message;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车
 *
 * @author coder
 * @date 2022-09-02 22:12:01
 * @since 1.0.0
 */
public class ShoppingCart implements CostCalculator {

    private final List<GoodsDetail> goods = new ArrayList<>();       // 商品清单

    public void addGoods(GoodsDetail detail) {
        this.goods.add(detail);
    }

    @Override
    public String description() {
        return "商品总费用";
    }

    @Override
    public BigDecimal finalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (GoodsDetail item : goods) {
            BigDecimal cost = item.price.multiply(BigDecimal.valueOf(item.number));
            totalCost = totalCost.add(cost);
        }
        return totalCost;
    }

    /**
     * 获取商品明细
     * @return 商品明细
     */
    public String getDetails() {
        List<String> details = goods.stream()
                .map(o -> MessageFormat.format("      商品名：【{0}】，商品单价：【{1}元】，商品数量：【{2}】",
                        o.name, o.price, o.number))
                .collect(Collectors.toList());
        return String.join("\n", details);
    }


    /**
     * 商品明细
     */
    public static class GoodsDetail {
        private final String name;            // 名称
        private final BigDecimal price;       // 单价
        private final int number;             // 数量
        public GoodsDetail(String name, BigDecimal price, int number) {
            this.name = name;
            this.price = price;
            this.number = number;
        }
    }
}
