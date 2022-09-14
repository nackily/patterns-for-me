package com.aoligei.structural.flyweight.shape;

import com.aoligei.structural.flyweight.Shape;

import java.awt.*;

/**
 * 矩形
 *
 * @author coder
 * @date 2022-07-04 11:46:18
 * @since 1.0.0
 */
public class Rectangle implements Shape {

    private final boolean fill;
    public Rectangle(boolean fill) {
        this.fill = fill;
        System.out.println("    创建一个新的矩形对象");
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height, Color c) {
        g.setColor(c);
        if (this.fill) {
            g.fillRect(x, y, width, height);
        } else {
            g.drawRect(x, y, width, height);
        }
    }
}
