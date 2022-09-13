package com.aoligei.creational.builder.message.builder;

import com.aoligei.creational.builder.message.Builder;
import com.aoligei.creational.builder.message.NoticeStatus;
import com.aoligei.creational.builder.message.product.Mail;
import com.aoligei.creational.builder.message.product.ShortMessage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 短信建造者
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class ShortMessageBuilder implements Builder<ShortMessage> {

    private String senderPhone;
    private String recipientPhone;
    private String content;
    private boolean cancelable = false;
    private Integer delayTimes;
    private TimeUnit delayTimeUnit;
    private Integer retryTimes;

    @Override
    public void buildSender(String sender) {
        this.senderPhone = sender;
    }

    @Override
    public void buildRecipient(String recipient) {
        this.recipientPhone = recipient;
    }

    @Override
    public void addCc(String cc) {}

    @Override
    public void buildTheme(String theme) {}

    @Override
    public void buildBody(String body) {
        this.content = body;
    }

    @Override
    public void buildLink(String link) {}

    @Override
    public void addAttachment(Mail.Attachment attachment) {}

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
    public ShortMessage build() {
        validate();
        // 默认值
        if (delayTimes == null) {
            delayTimes = 0;
        }
        if (retryTimes == null) {
            retryTimes = 0;
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
        return new ShortMessage(NoticeStatus.INITIALIZED, senderPhone,
                recipientPhone, content, cancelable, delayTimes, delayTimeUnit, retryTimes);
    }

    /**
     * 校验
     */
    public void validate() {
        // 发件人收件人不能为空
        if (senderPhone == null || "".equals(senderPhone)) {
            throw new IllegalArgumentException("未指定发件人号码");
        }
        if (recipientPhone == null || "".equals(recipientPhone)) {
            throw new IllegalArgumentException("未指定收件人号码");
        }
        // 邮箱格式
        List<String> addresses = new ArrayList<>(Arrays.asList(senderPhone, recipientPhone));
        for (String item : addresses) {
            // 检查邮箱地址格式
            if (isPhoneNumber(item)) {
                throw new IllegalArgumentException(MessageFormat.format("非法的电话号码：{0}", item));
            }
        }
    }

    /**
     * 是否为手机号码
     * @param text text
     * @return 是：true
     */
    public static boolean isPhoneNumber(String text) {
        // 手机号码
        return text.matches("^[1]([3-9])[0-9]{8}$");
    }
}
