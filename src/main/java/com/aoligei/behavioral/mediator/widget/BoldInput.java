package com.aoligei.behavioral.mediator.widget;

import com.aoligei.behavioral.mediator.AbstractWidget;
import com.aoligei.behavioral.mediator.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 字体加粗输入框
 *
 * @author coder
 * @date 2022-08-03 17:48:30
 * @since 1.0.0
 */
public class BoldInput extends AbstractWidget implements ItemListener {

    private JCheckBox boldField;

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JPanel();
        boldField = new JCheckBox();
        boldField.addFocusListener(this);

        // 添加组件并布局
        JLabel label = new JLabel("Bold: ");
        root.add(label);root.add(boldField);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                1, 1, null, 1, 1, 0, 0);
        layout.setConstraints(label, gbc);
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                3, 1, null, 3, 1, 1, 0);
        layout.setConstraints(boldField, gbc);
        root.setLayout(layout);

        // 选择项改变监听器
        boldField.addItemListener(this);
    }

    @Override
    public void setDisable() {
        boldField.setEnabled(false);
    }

    @Override
    public void setEnable() {
        boldField.setEnabled(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (headOfEventSource) {
            page.widgetChanged(this);
        }
    }

    public boolean getSelectState() {
        return boldField.isSelected();
    }

    public void setFontBold(boolean bold) {
        boldField.setSelected(bold);
    }

}
