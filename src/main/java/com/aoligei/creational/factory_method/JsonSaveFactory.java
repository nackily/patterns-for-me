package com.aoligei.creational.factory_method;


import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json格式存储器工厂
 *
 * @author coder
 * @date 2022-06-07 18:08:34
 * @since 1.0.0
 */
public class JsonSaveFactory implements FormatSaveFactory {

    @Override
    public AbstractFormatSaver createSaver() {
        return new JsonSaver(new ObjectMapper());
    }

}
