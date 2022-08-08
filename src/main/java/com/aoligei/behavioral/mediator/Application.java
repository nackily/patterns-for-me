package com.aoligei.behavioral.mediator;

import javax.swing.*;
import java.awt.*;

/**
 * 应用入口
 *
 * @author coder
 * @date 2022-08-03 16:30:11
 * @since 1.0.0
 */
public class Application {

    public static void main(String[] args) {
        JFrame frame = new JFrame("FontSelector");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        // 添加页面
        OptionalList fontList = new OptionalList();
        FontInput fontInput = new FontInput();
        SizeInput sizeInput = new SizeInput();
        BoldInput boldInput = new BoldInput();
        Editor displayBox = new Editor();
        Page page = new Page(fontList, fontInput, sizeInput, boldInput, displayBox);
        fontList.setPage(page);
        fontInput.setPage(page);
        sizeInput.setPage(page);
        boldInput.setPage(page);
        displayBox.setPage(page);

        frame.getContentPane().add(page, BorderLayout.CENTER);

        frame.setVisible(true);
    }

}
