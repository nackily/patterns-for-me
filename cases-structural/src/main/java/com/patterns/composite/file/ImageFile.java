package com.patterns.composite.file;

import com.patterns.composite.AbstractFile;

import java.text.MessageFormat;

/**
 * 图片文件
 *
 * @author coder
 * @date 2022-06-30 18:11:30
 * @since 1.0.0
 */
public class ImageFile extends AbstractFile {

    public ImageFile(String name) {
        super(name);
    }

    @Override
    protected void destroyVirus() {
        System.out.println(MessageFormat.format("   图片文件[{0}]开始杀毒", super.name));
    }
}
