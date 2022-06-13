package com.aoligei.creational.prototype;

/**
 * 租售合同
 *
 * @author coder
 * @date 2022-06-06 15:35:24
 * @since 1.0.0
 */
public class LeaseContract extends Contract {

    public LeaseContract() {
        super("租售合同");
    }

    @Override
    public void signed(String productOwner, String other) {
        this.setBuyer(other);
        this.setSeller(productOwner);
        System.out.println("    房屋租方为：" + other);
        System.out.println("    房屋出租方为：" + productOwner);
    }

}
