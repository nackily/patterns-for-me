package com.aoligei.behavioral.command.ide;

/**
 * 切换文档按钮
 *
 * @author coder
 * @date 2022-06-27 13:03:22
 * @since 1.0.0
 */
public class ToggleDocumentButton {

    private static final ToggleDocumentButton INSTANCE = new ToggleDocumentButton();

    public static ToggleDocumentButton getInstance() {
        return INSTANCE;
    }

    public void click(String name) {
        Document doc = Application.getInstance().getDocument(name);
        if (doc == null) {
            // 没有该文档
            return;
        }
        Application.getInstance().toggle(doc);
    }
}
