package com.patterns.abstract_factory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 数据格式加载器
 *
 * @author coder
 * @date 2022-07-12 11:11:19
 * @since 1.0.0
 */
public abstract class AbstractFormatLoader {

    /**
     * 文件后缀
     */
    protected final String fileExtension;
    public AbstractFormatLoader(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * 加载并解析为对象
     * @param key 键 - 文件名
     * @param type 期待的对象类型
     * @param <T> 返回的类型
     * @return 还原的对象
     * @throws Exception Exception
     */
    public <T> T loadAndResolve(String key, Class<T> type) throws Exception {
        String context = this.load(key);
        return this.resolve(context, type);
    }

    /**
     * 加载文件为字符串
     * @param key 键 - 文件名
     * @return 字符串
     * @throws IOException IOException
     */
    protected String load(String key) throws IOException {
        System.out.println("    即将开始加载文件");
        String directory = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        String filename = directory + key + fileExtension;

        File file = new File(filename);
        try (Reader r = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);) {
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = r.read()) != -1) {
                sb.append((char) ch);
            }
            return sb.toString();
        }
    }

    /**
     * 解析为对象
     * @param content 字符串
     * @param type 期待的对象类型
     * @param <T> 返回的类型
     * @return 还原的对象
     * @throws Exception Exception
     */
    protected abstract <T> T resolve(String content, Class<T> type) throws Exception;
}
