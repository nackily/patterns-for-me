package com.patterns.proxy.virtual_proxy;

/**
 * Graphics
 *
 * @author coder
 * @date 2022-06-20 17:02:31
 * @since 1.0.0
 */
public interface Graphic {

    /**
     * 绘制图像到屏幕
     */
    void draw();

    /**
     * 图像宽度
     * @return 宽度
     */
    double getWidth();

    /**
     * 图像高度
     * @return 高度
     */
    double getHeight();

    /**
     * 存储图片
     */
    void store();

}
