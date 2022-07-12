package com.aoligei.creational.factory_method;


/**
 * Main
 *
 * @author coder
 * @date 2022-06-07 11:23:01
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) throws Exception {
        DTO dto = new DTO();
        dto.setName("Jack");
        dto.setAge(23);
        System.out.println("|==> Start ---------------------------------------------------------------|");
        AbstractFormatSaver jsonSaver = new JsonSaveFactory().createSaver();
        jsonSaver.convertAndStore("Jack_json", dto);

        AbstractFormatSaver xmlSaver = new XmlSaveFactory().createSaver();
        xmlSaver.convertAndStore("Jack_xml", dto);
    }
}
