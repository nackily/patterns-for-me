package com.aoligei.behavioral.command.ide;

/**
 * 格式化按钮
 *
 * @author coder
 * @date 2022-06-27 13:03:22
 * @since 1.0.0
 */
public class ReformatCodeButton {

    private static final ReformatCodeButton INSTANCE = new ReformatCodeButton();

    public static ReformatCodeButton getInstance() {
        return INSTANCE;
    }

    public void click() {
        Document doc = Application.getInstance().getActiveDoc();
        if (null == doc) {
            return;
        }
        Command command = null;
        if (doc.getName().endsWith(".xml")) {
            command = new XmlFormatCommand((XmlDocument) doc);
        } else if (doc.getName().endsWith(".java")) {
            command = new JavaFormatCommand((JavaDocument) doc);
        } else {
            // 其他格式
        }
        command.response();
    }
}
