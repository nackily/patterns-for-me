package com.aoligei.creational.factory_method;


/**
 * xml格式转换器工厂
 *
 * @author coder
 * @date 2022-06-07 18:08:34
 * @since 1.0.0
 */
public class XmlSaveFactory implements FormatSaveFactory {

    @Override
    public AbstractFormatSaver createSaver() {
        return new XmlSaver();
    }

}
