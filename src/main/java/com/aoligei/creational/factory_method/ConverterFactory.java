package com.aoligei.creational.factory_method;

/**
 * 格式转换器工厂
 *
 * @author coder
 * @date 2022-06-07 11:23:01
 * @since 1.0.0
 */
public interface ConverterFactory {

    /**
     * 生产转换器
     * @return FormatConverter
     */
    FormatConverter createConverter();
}
