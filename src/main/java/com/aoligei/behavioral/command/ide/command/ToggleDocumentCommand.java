package com.aoligei.behavioral.command.ide.command;

import com.aoligei.behavioral.command.ide.Application;
import com.aoligei.behavioral.command.ide.Command;
import com.aoligei.behavioral.command.ide.Document;

/**
 * 切换文档命令
 *
 * @author coder
 * @date 2022-06-27 14:49:22
 * @since 1.0.0
 */
public class ToggleDocumentCommand implements Command {

    private final Application application;
    private final Document doc;
    public ToggleDocumentCommand(Application application, Document doc) {
        this.application = application;
        this.doc = doc;
    }

    @Override
    public void response() {
        application.toggle(doc);
    }
}
