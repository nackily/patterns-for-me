package com.patterns.factory_method.factory;


import com.patterns.factory_method.AbstractFormatSaver;
import com.patterns.factory_method.FormatSaveFactory;
import com.patterns.factory_method.product.JsonSaver;
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
