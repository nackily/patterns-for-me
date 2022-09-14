package com.aoligei.behavioral.strategy.compressor;

import com.aoligei.behavioral.strategy.CompressEntry;
import com.aoligei.behavioral.strategy.CompressStrategy;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZIP格式压缩器
 *
 * @author coder
 * @date 2022-09-05 16:37:43
 * @since 1.0.0
 */
public class ZipCompressor implements CompressStrategy {
    @Override
    public void compress(Collection<CompressEntry> entries, OutputStream os) throws IOException {
        System.out.println("    ==>> 开始压缩[.zip]文件...");

        // ZIP输出流
        ZipOutputStream zipOs = new ZipOutputStream(os);
        // 逐个压缩并写入输出流
        for (CompressEntry element : entries) {
            // 相对路径
            Path entryPath = element.getRelativePath();
            ZipEntry entry = new ZipEntry(entryPath.toString());
            // 添加进压缩列表
            zipOs.putNextEntry(entry);
            // 写入输出流
            zipOs.write(element.getContent());
            zipOs.closeEntry();
        }
        zipOs.close();

        System.out.println("    <<== 压缩[.zip]文件完成...");
    }

}
