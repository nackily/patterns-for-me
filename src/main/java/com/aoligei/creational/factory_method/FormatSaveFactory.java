package com.aoligei.creational.factory_method;

/**
 * 格式存储器工厂
 *
 * @author coder
 * @date 2022-06-07 11:23:01
 * @since 1.0.0
 */
public interface FormatSaveFactory {

    /**
     * 生产存储器
     * @return AbstractFormatSaver
     */
    AbstractFormatSaver createSaver();
}
