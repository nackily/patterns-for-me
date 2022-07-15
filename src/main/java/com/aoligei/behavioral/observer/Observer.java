package com.aoligei.behavioral.observer;

/**
 * 被通知者
 *
 * @author coder
 * @date 2022-07-14 17:36:24
 * @since 1.0.0
 */
public interface Observer {

    /**
     * 获取顾客的身份信息
     * @return 身份信息
     */
    String getIdentityInfo();

    /**
     * 接收通知
     * @param info 通知内容
     */
    void accept(String info);
}
