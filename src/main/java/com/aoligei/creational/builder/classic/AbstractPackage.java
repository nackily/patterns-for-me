package com.aoligei.creational.builder.classic;

/**
 * 抽象套餐
 *
 * @author xg-ran
 * @date 2022-05-30 10:45:20
 * @since 1.0.0
 */
public abstract class AbstractPackage {

    protected String name;              // 套餐名
    protected String stapleFood;        // 主食
    protected String sideDish;          // 配菜
    protected String snack;             // 小吃
    protected String drinks;            // 饮料

    public AbstractPackage() {
        this.setName();
    }

    public abstract void setName();

    public void setStapleFood(String stapleFood) {
        this.stapleFood = stapleFood;
    }

    public void setSideDish(String sideDish) {
        this.sideDish = sideDish;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public void setDrinks(String drinks) {
        this.drinks = drinks;
    }

    public void printProject(){
        System.out.println(
                "套餐信息如下" + '\n' +
                "   套餐名：" + name + '\n' +
                "   主食：" + stapleFood + '\n' +
                "   配菜：" + sideDish + '\n' +
                "   小吃：" + snack + '\n' +
                "   饮料：" + drinks
        );
    }
}
