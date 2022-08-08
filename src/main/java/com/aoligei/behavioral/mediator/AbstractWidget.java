package com.aoligei.behavioral.mediator;


import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 组件
 *
 * @author coder
 * @date 2022-08-03 16:15:46
 * @since 1.0.0
 */
public abstract class AbstractWidget implements FocusListener {

    protected Page page;                        // 挂载页面
    protected JComponent root;                  // 当前部件，当部件由多个子部件构成时，该部件为顶层部件
    protected boolean headOfEventSource;        // 事件源头

    public AbstractWidget() {
        buildComponentUI();
    }

    /**
     * 直接交互对象
     * @param page 页面
     */
    protected void setPage(Page page) {
        this.page = page;
    }


    @Override
    public void focusGained(FocusEvent e) {
        // 得到焦点，组件可能为事件的触发源头
        this.headOfEventSource =  true;
    }

    @Override
    public void focusLost(FocusEvent e) {
        // 失去焦点，组件不可能为事件的触发源头
        this.headOfEventSource =  false;
    }

    /**
     * 获取组件
     * @return JComponent
     */
    protected JComponent getRoot() {
        return root;
    }

    /**
     * 创建UI组件
     */
    protected abstract void buildComponentUI();

    /**
     * 设置部件不可用
     */
    protected abstract void setDisable();

    /**
     * 设置部件可用
     */
    protected abstract void setEnable();

}
