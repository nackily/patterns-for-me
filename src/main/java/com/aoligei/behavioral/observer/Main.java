package com.aoligei.behavioral.observer;

/**
 * Main
 *
 * @author coder
 * @date 2022-05-13 09:17:43
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        // 主题：天气
        Weather subject = new Weather();
        // 观察者：Jack
        new Jack(subject);
        // 观察者：Tom
        new Tom(subject);

        // 主题状态发生变化
        subject.setState("2022年5月13日下雨");
        subject.setState("2022年5月14日天气晴朗");
    }

}
