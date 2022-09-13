package com.aoligei.behavioral.command.ide.button;

import com.aoligei.behavioral.command.ide.Application;
import com.aoligei.behavioral.command.ide.command.OpenDocumentCommand;

/**
 * 打开文档按钮
 *
 * @author coder
 * @date 2022-06-27 15:04:15
 * @since 1.0.0
 */
public class OpenDocumentButton {

    private static final OpenDocumentButton INSTANCE = new OpenDocumentButton();

    public static OpenDocumentButton getInstance() {
        return INSTANCE;
    }

    public void click(String name) {
        new OpenDocumentCommand(Application.getInstance(), name).response();
    }
}
