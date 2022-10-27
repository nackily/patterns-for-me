package com.patterns.command.ide.document;

import com.patterns.command.ide.Document;

/**
 * Java文档
 *
 * @author coder
 * @date 2022-06-27 12:54:14
 * @since 1.0.0
 */
public class JavaDocument extends Document {

    public JavaDocument(String name) {
        super(name);
    }

    public void formatJava() {
        System.out.println("    已格式化JAVA文档：" + this.getName());
    }
}
