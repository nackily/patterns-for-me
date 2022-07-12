package com.aoligei.creational.factory_method;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * 数据格式存储器
 *
 * @author coder
 * @date 2022-07-12 10:42:28
 * @since 1.0.0
 */
public abstract class AbstractFormatSaver {

    /**
     * 文件存储格式
     */
    protected final String fileExtension;
    public AbstractFormatSaver(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * 转换格式并存储对象
     * @param key 键
     * @param obj 原始对象
     */
    public void convertAndStore(String key, Object obj) throws Exception {
        String formatContent = this.convert(obj);
        this.store(key, formatContent);
    }

    /**
     * 格式转换
     * @param obj 原始对象
     * @return 格式化后的字符串
     * @throws Exception Exception
     */
    protected abstract String convert(Object obj) throws Exception;

    /**
     * 内容写入文件
     * @param key 键 - 作为文件名
     * @param content 内容
     * @throws IOException IOException
     */
    protected void store(String key, String content) throws IOException {
        System.out.println("    即将开始写入文件");
        String directory = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        String filename = directory + key + this.fileExtension;
        // 写入文件
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        }
    }
}
