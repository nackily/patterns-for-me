package com.patterns.visitor.visitor;

import com.patterns.visitor.AbstractResource;
import com.patterns.visitor.Visitor;
import com.patterns.visitor.resource.Button;
import com.patterns.visitor.resource.Catalog;
import com.patterns.visitor.resource.Menu;

import java.util.List;

/**
 * XML格式导出访问器
 *
 * @author coder
 * @date 2022-08-26 11:20:06
 * @since 1.0.0
 */
public class XmlExportVisitor implements Visitor {

    public String export(AbstractResource resource) {
        String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        return header + resource.accept(this);
    }

    @Override
    public String visitCatalog(Catalog c) {
        return "<catalog>\n" +
                "    <name>" + c.getName() + "</name>\n" +
                "    <order>" + c.getOrder() + "</order>\n" +
                visitChildren(c) +
                "</catalog>";
    }

    @Override
    public String visitMenu(Menu m) {
        return "<menu>\n" +
                "    <name>" + m.getName() + "</name>\n" +
                "    <order>" + m.getOrder() + "</order>\n" +
                "    <path>" + m.getPath() + "</path>\n" +
                visitChildren(m) +
                "</menu>";
    }

    @Override
    public String visitButton(Button b) {
        return "<button>\n" +
                "    <name>" + b.getName() + "</name>\n" +
                "    <icon>" + b.getIcon() + "</icon>\n" +
                visitChildren(b) +
                "</button>";
    }

    /**
     * 访问子节点
     * @param resource 资源
     * @return String
     */
    private String visitChildren(AbstractResource resource) {
        StringBuilder sb = new StringBuilder();
        List<AbstractResource> children = resource.getChildren();
        for (AbstractResource o : children) {
            String childXml = o.accept(this);
            childXml = "    " + childXml.replace("\n", "\n    ") + "\n";
            sb.append(childXml);
        }
        return sb.toString();
    }

}
