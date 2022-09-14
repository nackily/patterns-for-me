package com.aoligei.structural.bridge.notifer;

import com.aoligei.structural.bridge.AbstractNotifer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 站内消息通知
 *
 * @author coder
 * @date 2022-07-07 19:05:18
 * @since 1.0.0
 */
public class SiteMessageNotifer extends AbstractNotifer {

    public SiteMessageNotifer(String identity, String content) {
        super(identity, content);
    }

    @Override
    public void doNotify() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(MessageFormat.format("       [{0}]发送站内消息，【用户编号：{1}】，【内容：{2}】", time, super.identity, super.content));
    }
}
