package com.aoligei.structural.flyweight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 应用程序
 *
 * @author coder
 * @date 2022-07-03 19:12:20
 * @since 1.0.0
 */
public class Application extends JFrame implements ActionListener {

    private final Color[] supportedColors = new Color[] {Color.BLUE, Color.RED, Color.BLACK, Color.MAGENTA};
    private final JPanel panel = new JPanel();
    public Application(int width, int height) {
        super.setSize(width, height);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 刷新按钮
        JButton button = new JButton("once again");
        button.addActionListener(this);
        // 组件布局
        Container container = super.getContentPane();
        container.add(panel, BorderLayout.CENTER);
        container.add(button, BorderLayout.SOUTH);
    }

    /**
     * 获取一个随机的图像类型
     * @return 图像类型
     */
    private ShapeFactory.SupportedShape getRandomShape() {
        ShapeFactory.SupportedShape[] allSupported = ShapeFactory.SupportedShape.values();
        return allSupported[(int) (Math.random() * allSupported.length)];
    }

    /**
     * 获取一个随机的左上角X坐标
     * @return x
     */
    private int getRandomX() {
        return (int) (Math.random() * super.getSize().width);
    }

    /**
     * 获取一个随机的左上角Y坐标
     * @return y
     */
    private int getRandomY() {
        return (int) (Math.random() * super.getSize().height);
    }

    /**
     * 获取一个随机的图像宽度
     * @return 宽度，范围在0~画布宽度的十分之一
     */
    private int getRandomWidth() {
        return (int) (Math.random() * (super.getSize().width / 10));
    }

    /**
     * 获取一个随机的图像高度
     * @return 高度，范围在0~画布高度的十分之一
     */
    private int getRandomHeight() {
        return (int) (Math.random() * (super.getSize().height / 10));
    }

    /**
     * 获取一个随机的颜色
     * @return 颜色
     */
    private Color getRandomColor() {
        int index = (int) (Math.random() * supportedColors.length);
        return supportedColors[index];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Graphics graphics = panel.getGraphics();
        for (int i = 0; i < 100; i++) {
            // 随机获取一个图像对象
            ShapeFactory.SupportedShape supportedShape = this.getRandomShape();
            Shape shape = ShapeFactory.getShape(supportedShape);
            shape.draw(graphics, getRandomX(), getRandomY(), getRandomWidth(), getRandomHeight(), getRandomColor());
        }
    }



    public static void main(String[] args) {
        System.out.println("|==> Start -------------------------------------------------|");
        Application app = new Application(800, 650);
        app.setVisible(true);
    }
}
