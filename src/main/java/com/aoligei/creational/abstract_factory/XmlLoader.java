package com.aoligei.creational.abstract_factory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * xml格式加载器
 *
 * @author coder
 * @date 2022-07-12 11:20:53
 * @since 1.0.0
 */
public class XmlLoader extends AbstractFormatLoader {

    public XmlLoader() {
        super(".xml");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T resolve(String content, Class<T> type) throws Exception {
        System.out.println("    即将开始解析XML");
        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        T tar = (T) unmarshaller.unmarshal(stream);
        System.out.println("        解析后内容：" + tar);
        return tar;
    }
}
