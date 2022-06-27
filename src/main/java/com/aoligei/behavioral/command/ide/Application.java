package com.aoligei.behavioral.command.ide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 应用程序
 *
 * @author coder
 * @date 2022-06-27 12:58:09
 * @since 1.0.0
 */
public class Application {

    private static final Application INSTANCE = new Application();

    public static Application getInstance(){
        return INSTANCE;
    }

    /**
     * 已打开的文档列表
     */
    private final List<Document> openedDocs = new ArrayList<>();

    /**
     * 当前活跃文档名
     */
    private Document activeDoc = null;

    /**
     * 添加文档对象
     * @param doc 文档对象
     */
    public void open (Document doc) {
        // 当前文档已经打开
        if (openedDocs.stream().anyMatch(item -> item.getName().equals(doc.getName()))) {
            return;
        }
        openedDocs.add(doc);
        activeDoc = doc;
        System.out.println("    已打开文档：" + doc.getName());
    }

    /**
     * 关闭文档
     * @param doc 文档对象
     */
    public void close (Document doc) {
        openedDocs.remove(doc);
        activeDoc = openedDocs.size() > 0
                ? openedDocs.get(0)
                : null;
    }

    /**
     * 切换文档为活跃文档
     * @param doc 文档对象
     */
    public void toggle(Document doc) {
        if (openedDocs.contains(doc)) {
            activeDoc = doc;
            System.out.println("    已切换至文档：" + doc.getName());
        }
    }

    /**
     * 获取当前活跃文档的名称
     * @return 文档名
     */
    public Document getActiveDoc() {
        return activeDoc;
    }

    /**
     * 获取文档对象
     * @param name 文档名
     * @return 文档对象
     */
    public Document getDocument(String name) {
        return openedDocs.stream()
                .filter(item -> name.equals(item.getName()))
                .findFirst().orElse(null);
    }
}
