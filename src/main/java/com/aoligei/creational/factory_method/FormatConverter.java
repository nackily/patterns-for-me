package com.aoligei.creational.factory_method;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 数据格式转换器
 *
 * @author coder
 * @date 2022-06-07 11:23:01
 * @since 1.0.0
 */
public abstract class FormatConverter {

    /**
     * 转换格式所需的依赖组件，比如转换为json格式需要依赖<ObjectMapper>组件
     */
    protected Object component;

    public Object getComponent() {
        return component;
    }

    /**
     * 为转换器初始化一些组件
     * @param component 组件
     */
    public FormatConverter(Object component) {
        this.component = component;
    }

    /**
     * 转换并存储到磁盘
     * @param source 源对象
     * @param filename 存储文件名
     */
    public final void convertAndStore(Object source, String filename) throws IOException {
        String targetText = this.convert(source);
        this.store(targetText, filename);
    }

    /**
     * 转换格式
     * @param source 源对象
     * @return 转换后的对象
     * @throws IOException IOException
     */
    protected abstract String convert(Object source) throws IOException;

    /**
     * 存储到磁盘
     * @param target 转换后的内容
     * @param filename 存储文件名
     */
    protected void store(String target, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(target);
        }
    }

}
