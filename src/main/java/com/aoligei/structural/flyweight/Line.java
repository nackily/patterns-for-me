package com.aoligei.structural.flyweight;

import java.awt.*;

/**
 * 线条
 *
 * @author coder
 * @date 2022-07-04 11:33:47
 * @since 1.0.0
 */
public class Line implements Shape {

    public Line() {
        System.out.println("    创建一个新的线条对象");
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height, Color c) {
        g.setColor(c);
        // 线条的终点坐标为：[x + width, y + height]
        g.drawLine(x, y, x + width, x + height);
    }
}
