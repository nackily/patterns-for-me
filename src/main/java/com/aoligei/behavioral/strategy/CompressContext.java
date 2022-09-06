package com.aoligei.behavioral.strategy;

import org.apache.commons.compress.utils.IOUtils;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * CompressContext
 *
 * @author coder
 * @date 2022-09-05 17:17:22
 * @since 1.0.0
 */
public class CompressContext {

    private final CompressStrategy strategy;

    public CompressContext(CompressStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 压缩磁盘文件
     * @param rootPath 需要压缩的文件或文件夹
     * @param dest 目标文件
     * @throws IOException 异常
     */
    public void compressFile(Path rootPath, Path dest) throws IOException {
        compressFile(rootPath, null, dest);
    }

    /**
     * 压缩磁盘文件
     * @param rootPath 需要压缩的文件或文件夹
     * @param ignorePaths 忽略的文件名
     * @param dest 目标文件
     * @throws IOException 异常
     */
    public void compressFile(Path rootPath, Path[] ignorePaths, Path dest) throws IOException {
        List<CompressEntry> entries = new ArrayList<>();
        // 文件访问器，是文件夹则遍历该文件夹及子文件夹
        Files.walkFileTree(rootPath,
                new CompressVisitor(entries, rootPath, ignorePaths));
        OutputStream fos = new FileOutputStream(dest.toString());
        // 压缩
        strategy.compress(entries, fos);
        fos.close();
    }

    /**
     * 压缩类
     * @param classes 类
     * @param dest 目标文件
     * @throws IOException 异常
     */
    public void compressClasses(Class<?>[] classes, Path dest) throws IOException {
        List<CompressEntry> entries = new ArrayList<>();
        for (Class<?> c : classes) {
            System.out.println(MessageFormat.format("    :::: 待压缩的类【{0}】", c.getName()));
            // 类名
            String entryName = c.getSimpleName();
            // 包名
            String packagePath = Objects.requireNonNull(c.getResource("")).getPath();
            // 类完整路径
            String classPath = packagePath + File.separator + entryName + ".class";
            // 构造CompressEntry
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(classPath);
            IOUtils.copy(fis, bos, 1024);
            entries.add(new CompressEntry(bos.toByteArray(), Paths.get(entryName)));
            fis.close();
            bos.close();
        }
        OutputStream fos = new FileOutputStream(dest.toString());
        // 压缩
        strategy.compress(entries, fos);
        fos.close();
    }

    /**
     * 文件访问器
     */
    public static class CompressVisitor extends SimpleFileVisitor<Path> {

        private final List<CompressEntry> entries;      // 待压缩的对象列表
        private final Path destRootPath;                // 根路径
        private final Path[] ignorePaths;               // 需要忽略的文件路径

        public CompressVisitor(List<CompressEntry> entries, Path destRootPath, Path[] ignorePaths) {
            this.entries = entries;
            this.destRootPath = destRootPath;
            this.ignorePaths = ignorePaths;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            // 获取该文件的相对路径
            Path entryRelativePath = destRootPath.relativize(file);
            if (ignorePaths == null || !Arrays.asList(ignorePaths).contains(entryRelativePath)) {
                // 如果不是忽略的文件，添加进压缩列表
                System.out.println(MessageFormat.format("    :::: 待压缩的文件【{0}】", file));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                FileInputStream fis = new FileInputStream(file.toFile());
                IOUtils.copy(fis, bos, 1024);
                entries.add(new CompressEntry(bos.toByteArray(), entryRelativePath));
                fis.close();
                bos.close();
            } else {
                System.out.println(MessageFormat.format("    :::: 被忽略的文件【{0}】", file));
            }
            // 继续向后遍历
            return FileVisitResult.CONTINUE;
        }
    }

}
