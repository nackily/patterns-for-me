package com.aoligei.creational.abstract_factory;

import com.aoligei.creational.factory_method.AbstractFormatSaver;
import com.aoligei.creational.factory_method.JsonSaver;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json格式工厂
 *
 * @author coder
 * @date 2022-07-12 11:29:55
 * @since 1.0.0
 */
public class JsonFactory implements FormatFactory{
    @Override
    public AbstractFormatSaver createSaver() {
        return new JsonSaver(new ObjectMapper());
    }

    @Override
    public AbstractFormatLoader createLoader() {
        return new JsonLoader(new ObjectMapper());
    }
}
