package com.aoligei.creational.factory_method.product;

import com.aoligei.creational.factory_method.AbstractFormatSaver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * XML格式存储器
 *
 * @author coder
 * @date 2022-07-12 10:53:46
 * @since 1.0.0
 */
public class XmlSaver extends AbstractFormatSaver {

    public XmlSaver() {
        super(".xml");
    }

    @Override
    protected String convert(Object obj) throws Exception {
        System.out.println("    即将开始转换对象为XML格式");
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 编码
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(obj, writer);
        String tar = writer.toString();
        System.out.println("        转换后内容：" + tar);
        return tar;
    }
}
