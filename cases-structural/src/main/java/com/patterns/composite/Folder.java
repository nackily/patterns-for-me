package com.patterns.composite;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹
 *
 * @author coder
 * @date 2022-06-30 18:11:30
 * @since 1.0.0
 */
public class Folder extends AbstractFile {

    /**
     * 子节点
     */
    private final List<AbstractFile> children = new ArrayList<>();

    public Folder(String name) {
        super(name);
    }

    /**
     * 添加子节点
     * @param item 子节点
     */
    public void add(AbstractFile item) {
        this.children.add(item);
    }

    @Override
    protected void destroyVirus() {
        System.out.println(MessageFormat.format("   ==>开始处理文件夹[{0}]...", super.name));
        children.forEach(AbstractFile::destroyVirus);
    }
}
