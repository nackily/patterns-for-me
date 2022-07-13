package com.aoligei.creational.abstract_factory;

import com.aoligei.creational.factory_method.AbstractFormatSaver;

/**
 * 格式处理器工厂
 *
 * @author coder
 * @date 2022-07-11 17:13:44
 * @since 1.0.0
 */
public interface FormatFactory {

    /**
     * 生产存储器
     * @return AbstractFormatSaver
     */
    AbstractFormatSaver createSaver();

    /**
     * 生产加载器
     * @return AbstractFormatLoader
     */
    AbstractFormatLoader createLoader();

}
