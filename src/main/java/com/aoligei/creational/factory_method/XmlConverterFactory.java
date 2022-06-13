package com.aoligei.creational.factory_method;


/**
 * xml格式转换器工厂
 *
 * @author coder
 * @date 2022-06-07 18:08:34
 * @since 1.0.0
 */
public class XmlConverterFactory implements ConverterFactory{

    @Override
    public FormatConverter createConverter() {
        // todo 在此处构造转换器所需的组件
        return new XmlFormatConverter(null);
    }

}
