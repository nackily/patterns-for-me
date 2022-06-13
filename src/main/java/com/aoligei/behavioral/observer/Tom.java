package com.aoligei.behavioral.observer;

import java.text.MessageFormat;

/**
 * Tom
 *
 * @author coder
 * @date 2022-05-13 10:46:51
 * @since 1.0.0
 */
public class Tom implements Observer {

    public Tom(Subject subject) {
        subject.registerObserver(this);
    }

    @Override
    public void update(Object o) {
        String o1 = (String) o;
        String action = o1.contains("下雨") ? "淋着雨上班" : "空手出门";
        System.out.println(MessageFormat.format(" Tom 收到消息：{0}，{1}", o, action));
    }
}
