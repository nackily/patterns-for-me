package com.aoligei.creational.prototype.contract;

import com.aoligei.creational.prototype.AbstractContract;

/**
 * 买卖合同
 *
 * @author coder
 * @date 2022-06-06 15:35:24
 * @since 1.0.0
 */
public class SalesContract extends AbstractContract {

    public SalesContract() {
        super("买卖合同");
    }

    @Override
    public void signed(String productOwner, String other) {
        this.setBuyer(other);
        this.setSeller(productOwner);
        System.out.println("    房屋买方为：" + other);
        System.out.println("    房屋出售方为：" + productOwner);
    }

}
