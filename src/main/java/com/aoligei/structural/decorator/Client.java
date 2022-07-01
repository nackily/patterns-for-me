package com.aoligei.structural.decorator;

/**
 * Client
 *
 * @author coder
 * @date 2022-05-24 16:01:55
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("Tom 点了一份牛肉面");
        Breakfast breakfast4Tom = new BeefDecorator(new Noodles());
        System.out.println("花费：" + breakfast4Tom.cost());
        System.out.println("包含有：" + breakfast4Tom.getDescription());

        System.out.println("Jack 点了一份酸菜羊肉米粉，酸菜要了双份");
        Breakfast breakfast4Jack = new SauerkrautDecorator(new SauerkrautDecorator(new MuttonDecorator(new Vermicelli())));
        System.out.println("花费：" + breakfast4Jack.cost());
        System.out.println("包含有：" + breakfast4Jack.getDescription());
    }
}
