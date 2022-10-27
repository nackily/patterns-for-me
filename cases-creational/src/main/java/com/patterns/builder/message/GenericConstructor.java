package com.patterns.builder.message;

import com.patterns.builder.message.product.Mail;

import java.util.concurrent.TimeUnit;

/**
 * 通用的构造器
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class GenericConstructor<T> {

    private final Builder<T> builder;
    public GenericConstructor(Builder<T> builder) {
        this.builder = builder;
    }

    /**
     * 欢迎信息
     * @param sender 发送人
     * @param recipient 收件人
     * @return 通知
     */
    public T welcome(String sender, String recipient) {
        builder.buildSender(sender);
        builder.buildRecipient(recipient);
        builder.buildBody("欢迎首次使用系统！");
        builder.addAttachment(new Mail.Attachment("使用手册.pdf", 784, null));
        // 失败不重试
        builder.buildRetryTimes(0);
        return builder.build();
    }

    /**
     * 订单超时信息
     * @param sender 发送人
     * @param recipient 收件人
     * @return 通知
     */
    public T orderTimeout(String sender, String recipient) {
        builder.buildSender(sender);
        builder.buildRecipient(recipient);
        builder.buildBody("订单支付已超时，即将被取消！");
        // 允许取消
        builder.buildCancelable();
        // 5分钟超时时间
        builder.buildDelay(5, TimeUnit.MINUTES);
        // 失败重试 3 次
        builder.buildRetryTimes(3);
        return builder.build();
    }

    /**
     * 物流信息变更
     * @param sender 发送人
     * @param recipient 收件人
     * @param link 链接
     * @return 通知
     */
    public T logisticsChanged(String sender, String recipient, String link) {
        builder.buildSender(sender);
        builder.buildRecipient(recipient);
        builder.buildBody("物流信息已更新，访问链接可查看详细信息！");
        // 失败重试 2 次
        builder.buildRetryTimes(2);
        // 链接
        builder.buildLink(link);
        return builder.build();
    }

    /**
     * 订单完成通知
     * @param sender 发送人
     * @param recipient 收件人
     * @param bill 发票
     * @return 通知
     */
    public T orderCompleted(String sender, String recipient, Mail.Attachment bill) {
        builder.buildSender(sender);
        builder.buildRecipient(recipient);
        builder.buildTheme("订单已完成");
        builder.buildBody("一笔订单已完成，请查收附件中包含的发票！");
        // 发票附件
        builder.addAttachment(bill);
        return builder.build();
    }
}
