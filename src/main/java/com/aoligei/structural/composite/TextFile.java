package com.aoligei.structural.composite;

import java.text.MessageFormat;

/**
 * 文本文件
 *
 * @author coder
 * @date 2022-06-30 18:11:30
 * @since 1.0.0
 */
public class TextFile extends AbstractFile {

    public TextFile(String name) {
        super(name);
    }

    @Override
    protected void destroyVirus() {
        System.out.println(MessageFormat.format("   文本文件[{0}]开始杀毒", super.name));
    }
}
