package com.aoligei.creational.builder.classic;


/**
 * Main
 *
 * @author xg-ran
 * @date 2022-05-30 11:37:39
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("|=> 张三点餐 ------------------------------------------|");
        AbstractBuilder flamesBuilder = new FlamesPackageBuilder();
        new Director(flamesBuilder).construct();
        AbstractPackage flames = flamesBuilder.build();
        flames.printProject();

        System.out.println("|=> 李四点餐 ------------------------------------------|");
        AbstractBuilder icyBuilder = new IcyPackageBuilder();
        new Director(icyBuilder).construct();
        AbstractPackage icy = icyBuilder.build();
        icy.printProject();
    }
}
