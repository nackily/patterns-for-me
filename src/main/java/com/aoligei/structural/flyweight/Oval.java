package com.aoligei.structural.flyweight;

import java.awt.*;

/**
 * 椭圆形
 *
 * @author coder
 * @date 2022-07-04 11:46:18
 * @since 1.0.0
 */
public class Oval implements Shape{

    private final boolean fill;
    public Oval(boolean fill) {
        this.fill = fill;
        System.out.println("    创建一个新的椭圆形对象");
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height, Color c) {
        g.setColor(c);
        if (this.fill) {
            g.fillOval(x, y, width, height);
        } else {
            g.drawOval(x, y, width, height);
        }
    }
}
