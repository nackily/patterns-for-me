package com.patterns.command.ide.command;

import com.patterns.command.ide.Application;
import com.patterns.command.ide.Command;
import com.patterns.command.ide.Document;
import com.patterns.command.ide.document.JavaDocument;
import com.patterns.command.ide.document.XmlDocument;

/**
 * 打开文档命令
 *
 * @author coder
 * @date 2022-06-27 14:49:22
 * @since 1.0.0
 */
public class OpenDocumentCommand implements Command {

    private final Application application;
    private final String name;
    public OpenDocumentCommand(Application application, String name) {
        this.application = application;
        this.name = name;
    }

    @Override
    public void response() {
        if (null == name || "".equals(name)) {
            return;
        }
        Document document = null;
        if (name.endsWith(".xml")) {
            document = new XmlDocument(name);
        } else if (name.endsWith(".java")) {
            document = new JavaDocument(name);
        }

        application.open(document);
    }
}
