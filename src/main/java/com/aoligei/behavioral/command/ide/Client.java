package com.aoligei.behavioral.command.ide;

/**
 * Client
 *
 * @author coder
 * @date 2022-06-22 15:26:07
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("|==> Start--------------------------------------------------------|");
        // 打开java文档
        OpenDocumentButton.getInstance().click("someday.java");
        // 打开xml文档
        OpenDocumentButton.getInstance().click("july.xml");
        // 格式化当前文档
        ReformatCodeButton.getInstance().click();
        // 切换活跃文档
        ToggleDocumentButton.getInstance().click("someday.java");
        // 再次格式化当前文档
        ReformatCodeButton.getInstance().click();
    }
}
