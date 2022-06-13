package com.aoligei.creational.builder.classic;


/**
 * 抽象的建造者
 *
 * @author xg-ran
 * @date 2022-05-30 11:19:12
 * @since 1.0.0
 */
public abstract class AbstractBuilder {

    /**
     * 主食
     */
    public abstract void buildStapleFood();

    /**
     * 配菜
     */
    public abstract void buildSideDish();

    /**
     * 小吃
     */
    public abstract void buildSnack();

    /**
     * 饮料
     */
    public abstract void buildDrinks();

    /**
     * 返回对象
     * @return AbstractProject
     */
    public abstract AbstractPackage build();
}
