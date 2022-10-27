package com.patterns.facade;

/**
 * 设备
 *
 * @author coder
 * @date 2022-06-17 13:58:17
 * @since 1.0.0
 */
public interface Equipment {
    /**
     * 打开设备
     */
    void on();

    /**
     * 关闭设备
     */
    void off();

    /**
     * 展示设备效果
     */
    void showEffects();
}
