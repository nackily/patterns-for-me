package com.aoligei.creational.builder.variant;

/**
 * Client
 *
 * @author coder
 * @date 2022-05-31 15:37:26
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("满意类型：");
        MaleFriend friend_1 = new MaleFriend.Builder().name("张三").ownHouse(true).ownCar(true).build();
        print(friend_1);

        System.out.println("一般类型：");
        MaleFriend friend_2 = new MaleFriend.Builder().name("李四").ownHouse(true).build();
        print(friend_2);

        System.out.println("不满意类型：");
        MaleFriend friend_3 = new MaleFriend.Builder().name("王五").build();
        print(friend_3);
    }

    private static void print(MaleFriend friend) {
        System.out.println(
                "   姓名：" + friend.getName() + "\n" +
                "   是否有车：" + (friend.isOwnCar() ? "是" : "否") + "\n" +
                "   是否有房：" + (friend.isOwnHouse() ? "是" : "否") + "\n" +
                "   满意程度：" + friend.getSatisfactionLevel()
        );
    }
}
