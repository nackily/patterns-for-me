package com.aoligei.structural.proxy.virtual_proxy;

import java.text.MessageFormat;

/**
 * 图片
 *
 * @author coder
 * @date 2022-06-20 17:07:10
 * @since 1.0.0
 */
public class Image implements Graphic {

    private final String fileName;

    /**
     * 图片宽度
     */
    private double width;

    /**
     * 图片高度
     */
    private double height;

    public Image(String fileName) {
        this.fileName = fileName;
        this.loadImage(fileName);
    }

    private void loadImage(String fileName) {
        System.out.println("    开始加载图片");
        // 模拟加载图片
        width = Math.random() * (50) + 51;
        height = Math.random() * (50) + 51;
    }

    @Override
    public void draw() {
        System.out.println(MessageFormat.format("    已绘制图片[{0}]", this.fileName));
    }

    @Override
    public double getWidth() {
        System.out.println(MessageFormat.format("        图片宽度为 {0}", this.width));
        return this.width;
    }

    @Override
    public double getHeight() {
        System.out.println(MessageFormat.format("        图片高度为 {0}", this.height));
        return this.height;
    }

    @Override
    public void store() {
        System.out.println(MessageFormat.format("    已存储图片[{0}]", this.fileName));
    }
}
