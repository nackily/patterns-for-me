package com.patterns.builder.message;

import com.patterns.builder.message.product.Mail;

import java.util.concurrent.TimeUnit;

/**
 * 建造者
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public interface Builder<T> {

    /**
     * 设置通知发信人
     * @param sender 发信人
     */
    void buildSender(String sender);

    /**
     * 设置通知收信人
     * @param recipient 收信人
     */
    void buildRecipient(String recipient);

    /**
     * 添加抄送人
     * @param cc 抄送人
     */
    void addCc(String cc);

    /**
     * 设置主题
     * @param theme 主题
     */
    void buildTheme(String theme);

    /**
     * 设置通知正文
     * @param body 正文
     */
    void buildBody(String body);

    /**
     * 设置链接
     * @param link 链接
     */
    void buildLink(String link);

    /**
     * 添加附件
     * @param attachment 附件
     */
    void addAttachment(Mail.Attachment attachment);

    /**
     * 设置消息延迟发送
     * @param delayTimes 延时时间
     * @param unit 时间单位
     */
    void buildDelay(int delayTimes, TimeUnit unit);

    /**
     * 如果通知为延迟类型，设置通知可被取消
     */
    void buildCancelable();

    /**
     * 设置失败重试次数
     * @param times 重试次数
     */
    void buildRetryTimes(int times);

    /**
     * 构建通知消息
     * @return 通知
     */
    T build();
}
