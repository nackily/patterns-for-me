package com.aoligei.creational.builder.classic;


/**
 * 烈焰套餐Builder
 *
 * @author xg-ran
 * @date 2022-05-30 11:23:30
 * @since 1.0.0
 */
public class FlamesPackageBuilder extends AbstractBuilder {

    private final AbstractPackage pro = new FlamesPackage();

    @Override
    public void buildStapleFood() {
        pro.setStapleFood("辣子鸡套饭");
    }

    @Override
    public void buildSideDish() {
        pro.setSideDish("酱菜");
    }

    @Override
    public void buildSnack() {
        pro.setSnack("泡椒凤爪");
    }

    @Override
    public void buildDrinks() {
        pro.setDrinks("冰红茶");
    }

    @Override
    public AbstractPackage build() {
        return this.pro;
    }
}
