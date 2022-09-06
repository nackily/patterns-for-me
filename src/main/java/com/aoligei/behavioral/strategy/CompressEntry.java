package com.aoligei.behavioral.strategy;

import java.nio.file.Path;

/**
 * 压缩对象元数据
 *
 * @author coder
 * @date 2022-09-06 10:04:34
 * @since 1.0.0
 */
public class CompressEntry {

    private final byte[] content;             // 压缩对象字节数组
    private final Path relativePath;          // 相对于跟目录的路径

    public CompressEntry(byte[] content, Path relativePath) {
        this.content = content;
        this.relativePath = relativePath;
    }

    public byte[] getContent() {
        return content;
    }

    public Path getRelativePath() {
        return relativePath;
    }
}