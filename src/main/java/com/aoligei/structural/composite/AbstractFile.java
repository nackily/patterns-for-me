package com.aoligei.structural.composite;

/**
 * 抽象的文件系统
 *
 * @author coder
 * @date 2022-06-30 18:06:50
 * @since 1.0.0
 */
public abstract class AbstractFile {

    /**
     * 文件名
     */
    protected String name;
    public AbstractFile(String name) {
        this.name = name;
    }

    /**
     * 杀毒
     */
    protected abstract void destroyVirus();
}
