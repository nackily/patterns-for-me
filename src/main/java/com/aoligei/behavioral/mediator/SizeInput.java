package com.aoligei.behavioral.mediator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * 字体大小输入框
 *
 * @author coder
 * @date 2022-08-03 17:48:30
 * @since 1.0.0
 */
public class SizeInput extends AbstractWidget implements ChangeListener {

    private JSpinner sizeSpinner;

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JPanel();
        sizeSpinner = new JSpinner(new SpinnerNumberModel(15, 9, 40, 1));
        ((JSpinner.DefaultEditor) sizeSpinner.getEditor()).getTextField().addFocusListener(this);

        // 添加组件并布局
        JLabel label = new JLabel("Size: ");
        root.add(label);root.add(sizeSpinner);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                1, 1, null, 1, 1, 0, 0);
        layout.setConstraints(label, gbc);
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                3, 1, null, 3, 1, 1, 0);
        layout.setConstraints(sizeSpinner, gbc);
        root.setLayout(layout);

        // 字体大小改变监听器
        sizeSpinner.addChangeListener(this);
    }

    @Override
    protected void setDisable() {
        sizeSpinner.setEnabled(false);
    }

    @Override
    protected void setEnable() {
        sizeSpinner.setEnabled(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (headOfEventSource) {
            page.widgetChanged(this);
        }
    }

    public int getFontSize(){
        return (int) sizeSpinner.getValue();
    }

    public void setFontSize(int size) {
        sizeSpinner.setValue(size);
    }
}
