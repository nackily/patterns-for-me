package com.aoligei.creational.builder.message;

/**
 * 通知状态
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public enum NoticeStatus {

    /**
     * 通知已完成初始化
     */
    INITIALIZED(0, "已初始化"),

    /**
     * 通知正在被分析中
     */
    ANALYZING(1, "正在分析中"),

    /**
     * 消息正在排队等待推送中
     */
    QUEUING(10, "排队中"),

    /**
     * 推送失败后，正在重试中
     */
    RETRYING(99, "失败重试中"),

    /**
     * 推送成功
     */
    SENT(100, "已推送"),

    /**
     * 已被取消推送
     */
    CANCELED(-1, "已被取消");

    private final int code;
    private final String stateName;
    NoticeStatus(int code, String stateName) {
        this.code = code;
        this.stateName = stateName;
    }

    public int getCode() {
        return code;
    }

    public String getStateName() {
        return stateName;
    }
}
