package com.aoligei.creational.prototype;

/**
 * 合同
 *
 * @author coder
 * @date 2022-06-06 15:04:52
 * @since 1.0.0
 */
public abstract class Contract implements Cloneable {

    private String type;                        // 合同类型
    private String buyer;                       // 买方
    private String mediator = "不靠谱中间商";     // 中间商
    private String seller;                      // 卖方

    public Contract(String type) {
        this.type = type;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    /**
     * 签署合同
     * @param productOwner 商品归属方
     * @param other 另一方
     */
    public abstract void signed (String productOwner, String other);

    @Override
    public Contract clone() {
        try {
            Contract clone = (Contract) super.clone();
            System.out.println("    复印了一份房屋" + this.type);
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
