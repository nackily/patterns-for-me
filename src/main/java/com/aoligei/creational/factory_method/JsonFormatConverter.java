package com.aoligei.creational.factory_method;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * json 格式转换器
 *
 * @author xg-ran
 * @date 2022-06-07 14:42:57
 * @since 1.0.0
 */
public class JsonFormatConverter extends FormatConverter {

    public JsonFormatConverter(Object component) {
        super(component);
    }

    @Override
    protected String convert(Object source) throws IOException {
        System.out.println("|==> 即将开始转换对象为JSON格式 ---------------------------------|");
        ObjectMapper mapper = (ObjectMapper) super.getComponent();
        String tar = mapper.writeValueAsString(source);
        System.out.println("    转换后内容：" + tar);
        return tar;
    }

}
