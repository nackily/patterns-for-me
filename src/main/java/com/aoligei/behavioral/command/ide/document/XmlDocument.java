package com.aoligei.behavioral.command.ide.document;

import com.aoligei.behavioral.command.ide.Document;

/**
 * Xml文档
 *
 * @author coder
 * @date 2022-06-27 12:54:14
 * @since 1.0.0
 */
public class XmlDocument extends Document {

    public XmlDocument(String name) {
        super(name);
    }

    public void formatXml() {
        System.out.println("    已格式化XML文档：" + this.getName());
    }
}
