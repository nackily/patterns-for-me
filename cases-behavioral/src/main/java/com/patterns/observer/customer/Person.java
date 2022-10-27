package com.patterns.observer.customer;

import com.patterns.observer.Observer;

import java.text.MessageFormat;
import java.util.Random;

/**
 * 个人性质的顾客
 *
 * @author coder
 * @date 2022-07-14 17:50:00
 * @since 1.0.0
 */
public class Person implements Observer {

    private final String name;
    public Person(String name) {
        this.name = name;
    }

    @Override
    public String getIdentityInfo() {
        return name;
    }

    @Override
    public void accept(String info) {
        System.out.println(MessageFormat.format("        [{0}]接收到通知：[{1}]，回复：[{2}]",
                this.getIdentityInfo(),
                info,
                new Random().nextBoolean() ? "我不喜欢吃这些水果，下次再说" : "我马上来店里"));
    }
}
