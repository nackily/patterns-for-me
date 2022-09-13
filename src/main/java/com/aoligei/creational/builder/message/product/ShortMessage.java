package com.aoligei.creational.builder.message.product;

import com.aoligei.creational.builder.message.NoticeStatus;
import java.util.concurrent.TimeUnit;

/**
 * 短信
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class ShortMessage {

    private final NoticeStatus state;                                         // 状态
    private final String senderPhone;                                         // 发信人号码
    private final String recipientPhone;                                      // 收信人号码
    private final String content;                                             // 短信内容
    private final boolean cancelable;                                         // 是否可被取消发送
    private final int delayTimes;                                             // 延时时间
    private final TimeUnit delayTimeUnit;                                     // 延时时间单位
    private final int retryTimes;                                             // 失败重试次数

    public ShortMessage(NoticeStatus state, String senderPhone, String recipientPhone, String content,
                        boolean cancelable, int delayTimes, TimeUnit delayTimeUnit, int retryTimes) {
        this.state = state;
        this.senderPhone = senderPhone;
        this.recipientPhone = recipientPhone;
        this.content = content;
        this.cancelable = cancelable;
        this.delayTimes = delayTimes;
        this.delayTimeUnit = delayTimeUnit;
        this.retryTimes = retryTimes;
    }

    public void printShortMessage() {
        String s = "    短信：\n" +
                "        状态：" + state.getStateName() + "\n" +
                "        发信人号码：" + senderPhone + "\n" +
                "        收信人号码：" + recipientPhone + "\n" +
                "        短信内容：" + content + "\n" +
                "        取消发送：" + (cancelable ? "支持" : "不支持") + "\n" +
                "        延时时间：" + delayTimes + " (" + delayTimeUnit.name() + ")\n" +
                "        失败重试次数：" + retryTimes;
        System.out.println(s);
    }
}
