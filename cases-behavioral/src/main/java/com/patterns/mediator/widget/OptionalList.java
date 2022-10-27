package com.patterns.mediator.widget;

import com.patterns.mediator.AbstractWidget;
import com.patterns.mediator.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 可选择字体列表
 *
 * @author coder
 * @date 2022-08-03 16:41:05
 * @since 1.0.0
 */
public class OptionalList extends AbstractWidget implements ListSelectionListener {

    private DefaultListModel<String> model;
    @Override
    @SuppressWarnings(value = {"unchecked"})
    protected void buildComponentUI() {
        // 初始化所有选项
        String[] fonts = allOptional();
        model = new DefaultListModel<>();
        for (String font : fonts) {
            model.addElement(font);
        }

        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JList<>(model);
        root.addFocusListener(this);

        // 不可多选
        ((JList<String>) root).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 注册选择监听器
        ((JList<String>) root).addListSelectionListener(this);
    }

    @Override
    public void setDisable() {
        root.setEnabled(false);
    }

    @Override
    public void setEnable() {
        root.setEnabled(true);
    }

    /**
     * 获取当前选择的字体名字
     * @return 字体名
     */
    @SuppressWarnings(value = {"unchecked"})
    public String selectedFontName(){
        return ((JList<String>) root).getSelectedValue();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (headOfEventSource) {
            // 仅当鼠标释放、并且有选中选项时才处理
            if (null != selectedFontName() && !e.getValueIsAdjusting()) {
                page.widgetChanged(this);
            }
        }
    }

    /**
     * 筛选可选列表
     * @param keyword 关键字
     */
    public void filter(String keyword) {
        List<String> current = currentOptional();
        String[] all = allOptional();
        for (String item : all) {
            // 需要添加到可选列表中
            if (item.contains(keyword) && !current.contains(item)) {
                model.addElement(item);
            }
            // 从可选列表中移除
            if (!item.contains(keyword) && current.contains(item)) {
                model.removeElement(item);
            }
        }
    }

    /**
     * 所有列表
     * @return String[]
     */
    private String[] allOptional() {
        return Arrays.stream(Page.OptionalFont.values())
                .map(Page.OptionalFont::getKey)
                .toArray(String[]::new);
    }

    /**
     * 当前列表
     * @return List<String>
     */
    private List<String> currentOptional() {
        List<String> current = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            current.add(model.get(i));
        }
        return current;
    }
}
