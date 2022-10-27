package com.patterns.builder.message.builder;

import com.patterns.builder.message.Builder;
import com.patterns.builder.message.NoticeStatus;
import com.patterns.builder.message.product.Mail;
import com.patterns.builder.message.product.SiteLetter;

import java.util.concurrent.TimeUnit;

/**
 * 站内信建造者
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class SiteLatterBuilder implements Builder<SiteLetter> {

    private String senderAccount;
    private String recipientAccount;
    private String body;
    private String redirectUrl;
    private boolean cancelable = false;
    private Integer delayTimes;
    private TimeUnit delayTimeUnit;
    private Integer retryTimes;

    @Override
    public void buildSender(String sender) {
        this.senderAccount = sender;
    }

    @Override
    public void buildRecipient(String recipient) {
        this.recipientAccount = recipient;
    }

    @Override
    public void addCc(String cc) {}

    @Override
    public void buildTheme(String theme) {}

    @Override
    public void buildBody(String body) {
        this.body = body;
    }

    @Override
    public void buildLink(String link) {
        this.redirectUrl = link;
    }

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
    public SiteLetter build() {
        validate();
        // 默认值
        if (delayTimes == null) {
            delayTimes = 0;
        }
        if (retryTimes == null) {
            retryTimes = 2;
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
        return new SiteLetter(NoticeStatus.INITIALIZED, senderAccount, recipientAccount,
                body, redirectUrl, cancelable, delayTimes, delayTimeUnit, retryTimes);
    }

    /**
     * 校验
     */
    public void validate() {
        // 发件人收件人不能为空
        if (senderAccount == null || "".equals(senderAccount)) {
            throw new IllegalArgumentException("未指定发件人账号");
        }
        if (recipientAccount == null || "".equals(recipientAccount)) {
            throw new IllegalArgumentException("未指定收件人账号");
        }
    }
}
