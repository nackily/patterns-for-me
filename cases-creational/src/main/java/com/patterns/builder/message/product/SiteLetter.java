package com.patterns.builder.message.product;

import com.patterns.builder.message.NoticeStatus;

import java.util.concurrent.TimeUnit;

/**
 * 站内信
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class SiteLetter {

    private final NoticeStatus state;                                         // 状态
    private final String senderAccount;                                       // 发信人账号
    private final String recipientAccount;                                    // 收信人账号
    private final String body;                                                // 正文
    private final String redirectUrl;                                         // 重定向URL
    private final boolean cancelable;                                         // 是否可被取消发送
    private final int delayTimes;                                             // 延时时间
    private final TimeUnit delayTimeUnit;                                     // 延时时间单位
    private final int retryTimes;                                             // 失败重试次数

    public SiteLetter(NoticeStatus state, String senderAccount, String recipientAccount,
                      String body, String redirectUrl, boolean cancelable,
                      int delayTimes, TimeUnit delayTimeUnit, int retryTimes) {
        this.state = state;
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.body = body;
        this.redirectUrl = redirectUrl;
        this.cancelable = cancelable;
        this.delayTimes = delayTimes;
        this.delayTimeUnit = delayTimeUnit;
        this.retryTimes = retryTimes;
    }

    public void printSiteMessage() {
        String s = "    站内信：\n" +
                "        状态：" + state.getStateName() + "\n" +
                "        发信人账号：" + senderAccount + "\n" +
                "        收信人账号：" + recipientAccount + "\n" +
                "        正文：" + (body == null ? "" : body) + "\n" +
                "        重定向URL：" + (redirectUrl == null ? "" : redirectUrl) + "\n" +
                "        取消发送：" + (cancelable ? "支持" : "不支持") + "\n" +
                "        延时时间：" + delayTimes + " (" + delayTimeUnit.name() + ")\n" +
                "        失败重试次数：" + retryTimes;
        System.out.println(s);
    }

}
