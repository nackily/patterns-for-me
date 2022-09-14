package com.aoligei.behavioral.mediator.widget;

import com.aoligei.behavioral.mediator.AbstractWidget;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 字体输入框
 *
 * @author coder
 * @date 2022-08-03 17:48:30
 * @since 1.0.0
 */
public class FontInput extends AbstractWidget implements DocumentListener {

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JTextField();
        root.addFocusListener(this);

        // 值改变监听器
        ((JTextField) root).getDocument().addDocumentListener(this);
    }

    @Override
    public void setDisable() {
        root.setEnabled(false);
    }

    @Override
    public void setEnable() {
        root.setEnabled(true);
    }

    public void setFontName(String fontName) {
        ((JTextField) root).setText(fontName);
    }

    /**
     * 获取当前的内容
     * @return 内容
     */
    public String getCurrentText() {
        return ((JTextField) root).getText();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (headOfEventSource) {
            // 关键字匹配字体
            page.widgetChanged(this);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (headOfEventSource) {
            // 关键字匹配字体
            page.widgetChanged(this);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
