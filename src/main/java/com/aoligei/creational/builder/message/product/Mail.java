package com.aoligei.creational.builder.message.product;

import com.aoligei.creational.builder.message.Builder;
import com.aoligei.creational.builder.message.NoticeStatus;
import com.sun.deploy.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Mail
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class Mail {

    private final NoticeStatus state;                                         // 邮件状态
    private final String fromAddress;                                         // 发件人邮箱
    private final String toAddress;                                           // 收件人邮箱
    private final String theme;                                               // 邮件主题
    private final String body;                                                // 邮件正文
    private final List<String> ccAddresses;                                   // 抄送人邮箱列表
    private final List<Attachment> attachments;                               // 附件列表
    private final boolean cancelable;                                         // 是否可被取消发送
    private final int delayTimes;                                             // 延时时间
    private final TimeUnit delayTimeUnit;                                     // 延时时间单位
    private final int retryTimes;                                             // 失败重试次数


    public Mail(NoticeStatus state, String fromAddress, String toAddress, String theme,
                String body, List<String> ccAddresses, List<Attachment> attachments, boolean cancelable,
                int delayTimes, TimeUnit delayTimeUnit, int retryTimes) {
        this.state = state;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.theme = theme;
        this.body = body;
        this.ccAddresses = ccAddresses;
        this.attachments = attachments;
        this.cancelable = cancelable;
        this.delayTimes = delayTimes;
        this.delayTimeUnit = delayTimeUnit;
        this.retryTimes = retryTimes;
    }

    public void printMail() {
        String s = "    邮件：\n" +
                "        状态：" + state.getStateName() + "\n" +
                "        发件人邮箱地址：" + fromAddress + "\n" +
                "        收件人邮箱地址：" + toAddress + "\n" +
                "        邮件主题：" + (theme == null ? "" : theme) + "\n" +
                "        邮件正文：" + (body == null ? "" : body) + "\n" +
                "        抄送人邮箱地址：" + StringUtils.join(ccAddresses, ", ") + "\n" +
                "        附件数量：" + attachments.size() + "\n" +
                "        取消发送：" + (cancelable ? "支持" : "不支持") + "\n" +
                "        延时时间：" + delayTimes + " (" + delayTimeUnit.name() + ")\n" +
                "        失败重试次数：" + retryTimes;
        System.out.println(s);
    }

    public static class Attachment {
        private final String name;                // 附件名
        private final int size;                   // 大小
        private final byte[] content;             // 附件内容
        public Attachment(String name, int size, byte[] content) {
            this.name = name;
            this.size = size;
            this.content = content;
        }
    }

}
