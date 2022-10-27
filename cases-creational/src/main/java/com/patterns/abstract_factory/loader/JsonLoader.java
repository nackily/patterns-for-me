package com.patterns.abstract_factory.loader;

import com.patterns.abstract_factory.AbstractFormatLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json格式加载器
 *
 * @author coder
 * @date 2022-07-12 11:20:53
 * @since 1.0.0
 */
public class JsonLoader extends AbstractFormatLoader {

    private final ObjectMapper objectMapper;

    public JsonLoader(ObjectMapper objectMapper) {
        super(".json");
        this.objectMapper = objectMapper;
    }

    @Override
    protected <T> T resolve(String content, Class<T> type) throws Exception {
        System.out.println("    即将开始解析JSON");
        T tar = objectMapper.readValue(content, type);
        System.out.println("        解析后内容：" + tar);
        return tar;
    }
}
