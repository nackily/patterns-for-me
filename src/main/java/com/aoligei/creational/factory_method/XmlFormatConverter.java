package com.aoligei.creational.factory_method;

/**
 * xml 格式转换器
 *
 * @author coder
 * @date 2022-06-07 14:42:57
 * @since 1.0.0
 */
public class XmlFormatConverter extends FormatConverter {

    public XmlFormatConverter(Object component) {
        super(component);
    }

    @Override
    protected String convert(Object source) {
        System.out.println("|==> 即将开始转换对象为XML格式 ---------------------------------|");
        // todo 在此处实现转换xml格式
        return null;
    }

}
