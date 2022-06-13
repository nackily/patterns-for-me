package com.aoligei.creational.factory_method;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Main
 *
 * @author xg-ran
 * @date 2022-06-07 11:23:01
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 23);

        FormatConverter converter = new JsonConverterFactory().createConverter();
        converter.convertAndStore(map, "C:\\Users\\Coder\\Desktop\\1.json");
    }
}
