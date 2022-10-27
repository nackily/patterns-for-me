package com.patterns.builder.message.builder;

import com.patterns.builder.message.Builder;
import com.patterns.builder.message.NoticeStatus;
import com.patterns.builder.message.product.Mail;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 邮件建造者
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class MailBuilder implements Builder<Mail> {

    private String fromAddress;
    private String toAddress;
    private String theme;
    private String body;
    private final List<String> ccAddresses = new ArrayList<>();
    private final List<Mail.Attachment> attachments = new ArrayList<>();
    private boolean cancelable = false;
    private Integer delayTimes;
    private TimeUnit delayTimeUnit;
    private Integer retryTimes;

    @Override
    public void buildSender(String sender) {
        this.fromAddress = sender;
    }

    @Override
    public void buildRecipient(String recipient) {
        this.toAddress = recipient;
    }

    @Override
    public void buildTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public void buildBody(String body) {
        this.body = body;
    }

    @Override
    public void buildLink(String link) {}

    @Override
    public void addCc(String cc) {
        this.ccAddresses.add(cc);
    }

    @Override
    public void addAttachment(Mail.Attachment attachment) {
        this.attachments.add(attachment);
    }

    @Override
    public void buildDelay(int delayTimes, TimeUnit unit) {
        this.delayTimes = delayTimes;
        this.delayTimeUnit = unit;
    }

    @Override
    public void buildCancelable() {
        this.cancelable = true;
    }

    @Override
    public void buildRetryTimes(int times) {
        this.retryTimes = times;
    }

    @Override
    public Mail build() {
        validate();
        // 默认值
        if (delayTimes == null) {
            delayTimes = 0;
        }
        if (retryTimes == null) {
            retryTimes = 1;
        }
        if (delayTimeUnit == null) {
            delayTimeUnit = TimeUnit.SECONDS;
        }
        // 参数修正
        if (delayTimes < 0) {
            delayTimes = 0;
        }
        if (retryTimes < 0) {
            retryTimes = 0;
        }
        return new Mail(NoticeStatus.INITIALIZED, fromAddress,
                toAddress, theme, body, ccAddresses, attachments,
                cancelable, delayTimes, delayTimeUnit, retryTimes);
    }

    /**
     * 校验
     */
    public void validate() {
        // 发件人收件人不能为空
        if (fromAddress == null || "".equals(fromAddress)) {
            throw new IllegalArgumentException("未指定发件人邮箱地址");
        }
        if (toAddress == null || "".equals(toAddress)) {
            throw new IllegalArgumentException("未指定收件人邮箱地址");
        }
        // 邮箱格式
        List<String> addresses = new ArrayList<>(Arrays.asList(fromAddress, toAddress));
        addresses.addAll(ccAddresses);
        for (String item : addresses) {
            // 检查邮箱地址格式
            if (isEmail(item)) {
                throw new IllegalArgumentException(MessageFormat.format("非法的邮箱地址：{0}", item));
            }
        }
    }

    /**
     * 是否为邮箱地址
     * @param text text
     * @return 是：true
     */
    public static boolean isEmail(String text) {
        return text.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$\n");
    }
}
