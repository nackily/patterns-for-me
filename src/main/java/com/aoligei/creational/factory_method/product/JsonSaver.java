package com.aoligei.creational.factory_method.product;

import com.aoligei.creational.factory_method.AbstractFormatSaver;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json格式存储器
 *
 * @author coder
 * @date 2022-07-12 10:53:46
 * @since 1.0.0
 */
public class JsonSaver extends AbstractFormatSaver {

    private final ObjectMapper objectMapper;
    public JsonSaver(ObjectMapper objectMapper) {
        super(".json");
        this.objectMapper = objectMapper;
    }

    @Override
    protected String convert(Object obj) throws Exception {
        System.out.println("    即将开始转换对象为JSON格式");
        String tar = objectMapper.writeValueAsString(obj);
        System.out.println("        转换后内容：" + tar);
        return tar;
    }
}
