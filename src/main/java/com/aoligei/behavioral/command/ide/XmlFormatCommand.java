package com.aoligei.behavioral.command.ide;

/**
 * 格式化XMl文档命令
 *
 * @author coder
 * @date 2022-06-27 12:53:25
 * @since 1.0.0
 */
public class XmlFormatCommand implements Command{

    private final XmlDocument xmlDocument;
    public XmlFormatCommand(XmlDocument xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    @Override
    public void response() {
        xmlDocument.formatXml();
    }
}
