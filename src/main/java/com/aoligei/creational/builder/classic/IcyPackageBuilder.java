package com.aoligei.creational.builder.classic;


/**
 * 冰爽套餐Builder
 *
 * @author coder
 * @date 2022-05-30 11:23:30
 * @since 1.0.0
 */
public class IcyPackageBuilder extends AbstractBuilder {

    private final AbstractPackage pro = new IcyPackage();

    @Override
    public void buildStapleFood() {
        pro.setStapleFood("薄荷焖饭");
    }

    @Override
    public void buildSideDish() {
        pro.setSideDish("泡菜");
    }

    @Override
    public void buildSnack() {
        pro.setSnack("凉拌海带丝");
    }

    @Override
    public void buildDrinks() {
        pro.setDrinks("雪碧");
    }

    @Override
    public AbstractPackage build() {
        return this.pro;
    }
}
