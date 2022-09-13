package com.aoligei.behavioral.command.ide.command;

import com.aoligei.behavioral.command.ide.Command;
import com.aoligei.behavioral.command.ide.document.JavaDocument;

/**
 * 格式化Java文档命令
 *
 * @author coder
 * @date 2022-06-27 12:53:25
 * @since 1.0.0
 */
public class JavaFormatCommand implements Command {

    private final JavaDocument javaDocument;
    public JavaFormatCommand(JavaDocument javaDocument) {
        this.javaDocument = javaDocument;
    }

    @Override
    public void response() {
        javaDocument.formatJava();
    }
}
