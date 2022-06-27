package com.aoligei.behavioral.command.ide;

/**
 * 文档对象
 *
 * @author coder
 * @date 2022-06-27 14:23:59
 * @since 1.0.0
 */
public abstract class Document {

    /**
     * 文档名
     */
    private final String name;

    public Document(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
