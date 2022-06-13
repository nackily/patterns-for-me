package com.aoligei.creational.factory_method;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json格式转换器工厂
 *
 * @author coder
 * @date 2022-06-07 18:08:34
 * @since 1.0.0
 */
public class JsonConverterFactory implements ConverterFactory{

    @Override
    public FormatConverter createConverter() {
        return new JsonFormatConverter(new ObjectMapper());
    }

}
