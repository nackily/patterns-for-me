package com.patterns.strategy.compressor;

import com.patterns.strategy.CompressEntry;
import com.patterns.strategy.CompressStrategy;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * tar格式压缩器
 *
 * @author coder
 * @date 2022-09-05 20:56:22
 * @since 1.0.0
 */
public class TarCompressor implements CompressStrategy {

    @Override
    public void compress(Collection<CompressEntry> entries, OutputStream os) throws IOException {
        System.out.println("    ==>> 开始压缩[.tar]文件...");

        // TAR输出流
        TarArchiveOutputStream tos = new TarArchiveOutputStream(os);
        for (CompressEntry element : entries) {
            byte[] bytes = element.getContent();
            // 相对路径替换为linux文件分隔符，否则无法创建目录
            String entryName = element.getRelativePath().toString().replace("\\", "/");
            TarArchiveEntry tarEntry = new TarArchiveEntry(entryName);
            tarEntry.setSize(bytes.length);
            // 添加进压缩列表
            tos.putArchiveEntry(tarEntry);
            // 写入输出流
            tos.write(bytes);
            tos.closeArchiveEntry();
        }
        tos.close();

        System.out.println("    <<== 压缩[.tar]文件完成...");
    }
}
