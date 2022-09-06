package com.aoligei.behavioral.strategy;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Client
 *
 * @author coder
 * @date 2022-09-05 17:59:29
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("|==> Start -------------------------------------------------------|");
        // 压缩磁盘文件为zip格式压缩包
        CompressContext context = new CompressContext(new ZipCompressor());
        context.compressFile(Paths.get("opt", "test", "funny"), Paths.get("opt", "test", "output.zip"));

        // 压缩磁盘文件为tar格式压缩包
        context = new CompressContext(new TarCompressor());
        context.compressFile(Paths.get("opt", "test", "funny"), Paths.get("opt", "test", "output.tar"));

        // 压缩class为jar包
        context = new CompressContext(new JarCompressor());
        Class<?>[] toPackageClasses = { JarCompressor.class, CompressStrategy.class, Client.class };
        context.compressClasses(toPackageClasses, Paths.get("opt", "test", "output.jar"));
    }

}
