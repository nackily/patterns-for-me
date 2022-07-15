package com.aoligei.behavioral.observer;

/**
 * Client
 *
 * @author coder
 * @date 2022-7-14 17:29:54
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> Start --------------------------------------------------------------------|");
        // 水果店
        FruitShop shop = new FruitShop();
        // 个人性质的顾客
        Person per1 = new Person("张婶");
        Person per2 = new Person("陈皮皮");
        // 组织性质的顾客
        Organization org1 = new Organization("小王面馆");
        Organization org2 = new Organization("第一街区中学食堂");
        // 登记
        shop.register(per1);
        shop.register(org1);
        shop.register(org2);
        // 上新
        shop.newest("300斤芒果，128斤榴莲");
        // 注销
        shop.unregister(org2);
        // 登记
        shop.register(per2);
        // 上新
        shop.newest("400斤香蕉，37斤菠萝蜜");
    }
}
