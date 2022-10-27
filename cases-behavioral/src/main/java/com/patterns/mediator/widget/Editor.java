package com.patterns.mediator.widget;

import com.patterns.mediator.AbstractWidget;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * 编辑器
 *
 * @author coder
 * @date 2022-08-03 17:48:30
 * @since 1.0.0
 */
public class Editor extends AbstractWidget implements DocumentListener {

    private boolean emptyText = false;
    private JTextArea content;

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        content = new JTextArea();
        root = new JScrollPane(content);
        content.addFocusListener(this);

        String defaultText = "中文效果：\n" +
                "   这是当前所选字体的呈现效果\n\n" +
                "英文效果：\n" +
                "   this is the rendering of the currently selected font\n\n" +
                "基础样式：\n" +
                "   abcdefghijklmnopqrstuvwxyz\n" +
                "   ABCDEFGHIJKLMNOPQRSTUVWXYZ\n" +
                "   0123456789 (){}[]\n" +
                "   +-*/= .,;:!? #&$%@|^\n\n" +
                "示例段落：\n" +
                "   罗辑艰难地站了起来，在虚弱的颤抖中，他只有扶着墓碑才能站住。他腾出一只手来，整理了一下自己满是泥浆的湿衣服和蓬乱的头发，随后摸索着，从上衣口袋中掏出一个金属管状物，那是一支已经充满电的手枪。\n" +
                "   然后，他面对着东方的晨光，开始了地球文明和三体文明的最后对决。\n\n" +
                "......";
        content.setText(defaultText);
        content.setLineWrap(true);
        content.setLineWrap(true);

        content.getDocument().addDocumentListener(this);
    }

    @Override
    public void setDisable() {
        content.setEnabled(false);
    }

    @Override
    public void setEnable() {
        content.setEnabled(true);
    }

    public void changeFont(String fontName, int size, boolean bold) {
        content.setFont(new Font(fontName, bold ? Font.BOLD : Font.PLAIN ,size));
    }

    public boolean isEmptyText() {
        return emptyText;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (headOfEventSource && emptyText) {
            // 当文本区域内容从无到有时，启用其他组件
            String text = content.getText();
            if (!"".equals(text.trim())) {
                emptyText = false;
                page.widgetChanged(this);
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (headOfEventSource) {
            // 当文本区域没有内容时，禁用其他组件
            String text = content.getText();
            if (text == null || "".equals(text.trim())) {
                emptyText = true;
                page.widgetChanged(this);
            }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
