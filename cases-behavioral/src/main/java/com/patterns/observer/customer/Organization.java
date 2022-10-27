package com.patterns.observer.customer;

import com.patterns.observer.Observer;

import java.text.MessageFormat;

/**
 * 组织性质的顾客
 *
 * @author coder
 * @date 2022-07-14 17:50:59
 * @since 1.0.0
 */
public class Organization implements Observer {

    private final String name;
    public Organization(String name) {
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
                "我马上安排员工来店里购买"));
    }
}
