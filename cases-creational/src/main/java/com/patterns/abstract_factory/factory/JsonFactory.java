package com.patterns.abstract_factory.factory;

import com.patterns.abstract_factory.AbstractFormatLoader;
import com.patterns.abstract_factory.FormatFactory;
import com.patterns.abstract_factory.loader.JsonLoader;
import com.patterns.factory_method.AbstractFormatSaver;
import com.patterns.factory_method.product.JsonSaver;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json格式工厂
 *
 * @author coder
 * @date 2022-07-12 11:29:55
 * @since 1.0.0
 */
public class JsonFactory implements FormatFactory {
    @Override
    public AbstractFormatSaver createSaver() {
        return new JsonSaver(new ObjectMapper());
    }

    @Override
    public AbstractFormatLoader createLoader() {
        return new JsonLoader(new ObjectMapper());
    }
}
