package com.aoligei.structural.bridge;

/**
 * 抽象的通知发送器
 *
 * @author coder
 * @date 2022-07-07 18:20:36
 * @since 1.0.0
 */
public abstract class AbstractNotifer {

    /**
     * 身份标识：1.站内消息 -> 用户编号；2.邮件 -> 邮箱；3.短信/机器人语音通话 -> 电话号码
     */
    protected final String identity;

    /**
     * 通知内容
     */
    protected String content;


    public AbstractNotifer(String identity, String content) {
        this.identity = identity;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 通知用户
     */
    public abstract void doNotify();
}
