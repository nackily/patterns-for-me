package com.aoligei.behavioral.observer;

import java.text.MessageFormat;

/**
 * Jack
 *
 * @author coder
 * @date 2022-05-13 10:59:06
 * @since 1.0.0
 */
public class Jack implements Observer{

    public Jack(Subject subject) {
        subject.registerObserver(this);
    }

    @Override
    public void update(Object o) {
        String o1 = (String) o;
        String action = o1.contains("下雨") ? "今天出门带把伞" : "空手出门";
        System.out.println(MessageFormat.format(" Jack 收到消息：{0}，{1}", o, action));
    }
}
