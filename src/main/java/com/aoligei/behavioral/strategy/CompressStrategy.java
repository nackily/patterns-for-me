package com.aoligei.behavioral.strategy;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * 压缩策略接口
 *
 * @author coder
 * @date 2022-09-05 16:31:33
 * @since 1.0.0
 */
public interface CompressStrategy {

    /**
     * 对象压缩
     * @param entries 待压缩列表
     * @param os 输出流
     * @throws IOException 异常
     */
    void compress(Collection<CompressEntry> entries, OutputStream os) throws IOException;
}
