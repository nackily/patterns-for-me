package com.aoligei.creational.abstract_factory;

import com.aoligei.creational.abstract_factory.factory.JsonFactory;
import com.aoligei.creational.abstract_factory.factory.XmlFactory;
import com.aoligei.creational.factory_method.AbstractFormatSaver;
import com.aoligei.creational.factory_method.DTO;

/**
 * Client
 *
 * @author coder
 * @date 2022-07-11 16:44:51
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) throws Exception {
        DTO dto = new DTO();
        dto.setName("tom");
        dto.setAge(60);
        System.out.println("|==> Start ---------------------------------------------------------------|");
        FormatFactory jsonFactory = new JsonFactory();
        AbstractFormatSaver jsonSaver = jsonFactory.createSaver();
        // 转换json并存储
        jsonSaver.convertAndStore("tom_json", dto);
        // 从磁盘加载并解析
        AbstractFormatLoader jsonLoader = jsonFactory.createLoader();
        jsonLoader.loadAndResolve("tom_json", DTO.class);

        FormatFactory xmlFactory = new XmlFactory();
        AbstractFormatSaver xmlSaver = xmlFactory.createSaver();
        // 转换格式并存储
        xmlSaver.convertAndStore("tom_xml", dto);
        // 从磁盘加载并解析
        AbstractFormatLoader xmlLoader = xmlFactory.createLoader();
        xmlLoader.loadAndResolve("tom_xml", DTO.class);
    }
}
