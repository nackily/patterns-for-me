package com.aoligei.behavioral.strategy;

import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * jar格式压缩器
 *
 * @author coder
 * @date 2022-09-05 20:20:32
 * @since 1.0.0
 */
public class JarCompressor implements CompressStrategy {

    @Override
    public void compress(Collection<CompressEntry> entries, OutputStream os) throws IOException {
        System.out.println("    ==>> 开始压缩[.jar]文件...");

        // JAR输出流
        JarArchiveOutputStream jos = new JarArchiveOutputStream(os);
        for (CompressEntry element : entries) {
            byte[] bytes = element.getContent();
            // 相对路径替换为linux文件分隔符，否则无法创建目录
            String entryName = element.getRelativePath().toString().replace("\\", "/");
            JarArchiveEntry jarEntry = new JarArchiveEntry(entryName);
            jarEntry.setSize(bytes.length);
            // 添加进压缩列表
            jos.putArchiveEntry(jarEntry);
            // 写入输出流
            jos.write(bytes);
            jos.closeArchiveEntry();
        }
        jos.close();

        System.out.println("    <<== 压缩[.jar]文件完成...");
    }

}
