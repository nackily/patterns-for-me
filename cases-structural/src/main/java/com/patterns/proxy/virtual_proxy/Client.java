package com.patterns.proxy.virtual_proxy;

/**
 * Client
 *
 * @author coder
 * @date 2022-06-20 17:47:32
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("|==> 打开文档【/res/a.png】---------------------------------------------|");
        Graphic image = new ImageProxy("/res/a.png");
        System.out.println("    获取图片宽度：");
        image.getWidth();
        // 对比使用代理和不使用代理两种情况，可以发现使用代理之后将图像加载的操作
        // 从 初始化对象时 延迟到 绘制图像时
        image.draw();
        System.out.println("    获取图片高度：");
        image.getHeight();
    }
}
