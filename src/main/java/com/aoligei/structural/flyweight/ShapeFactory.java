package com.aoligei.structural.flyweight;

import com.aoligei.structural.flyweight.shape.Line;
import com.aoligei.structural.flyweight.shape.Oval;
import com.aoligei.structural.flyweight.shape.Rectangle;

import java.util.HashMap;

/**
 * 共享图像工厂
 *
 * @author coder
 * @date 2022-07-04 11:50:04
 * @since 1.0.0
 */
public class ShapeFactory {

    /**
     * 缓存
     */
    private static final HashMap<SupportedShape, Shape> CACHES = new HashMap<>();

    /**
     * 获取图像对象
     * @param s 图像类型
     * @return 图像对象
     */
    public static Shape getShape(SupportedShape s) {
        if (CACHES.containsKey(s)) {
            return CACHES.get(s);
        }
        Shape shape = null;
        switch (s) {
            case LINE:
                shape = new Line();
                break;
            case RECT:
                shape = new Rectangle(false);
                break;
            case RECT_FILL:
                shape = new Rectangle(true);
                break;
            case OVAL:
                shape = new Oval(false);
                break;
            case OVAL_FILL:
                shape = new Oval(true);
                break;
            default:
                throw new RuntimeException("不支持的图像类型");
        }
        CACHES.put(s, shape);
        return shape;
    }


    public static enum SupportedShape {
        LINE,                       // 线条
        RECT,                       // 矩形
        RECT_FILL,                  // 填充矩形
        OVAL,                       // 椭圆形
        OVAL_FILL;                  // 填充椭圆形
    }
}
