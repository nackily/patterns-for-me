package com.patterns.visitor;

import com.patterns.visitor.resource.Button;
import com.patterns.visitor.resource.Catalog;
import com.patterns.visitor.resource.Menu;
import com.patterns.visitor.visitor.XmlExportVisitor;

/**
 * Client
 *
 * @author coder
 * @date 2022-08-23 14:47:49
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        XmlExportVisitor visitor = new XmlExportVisitor();
        AbstractResource catalog = new Catalog("系统管理", 1)
                .addChild(new Menu("用户管理", 1, "/sys/user/index")
                        .addChild(new Button("新增用户", "add"))
                        .addChild(new Button("删除用户", "delete")))
                .addChild(new Menu("角色管理", 2, "/sys/role/index")
                        .addChild(new Button("角色列表", "query"))
                        .addChild(new Button("授权", "auth")));
        String catalogXml = visitor.export(catalog);
        System.out.println(catalogXml);
    }
}
