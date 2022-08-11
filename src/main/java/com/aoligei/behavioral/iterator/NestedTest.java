package com.aoligei.behavioral.iterator;

import java.util.HashMap;
import java.util.Map;

/**
 * 嵌套遍历示例
 *
 * @author coder
 * @date 2022-08-11 15:00:01
 * @since 1.0.0
 */
public class NestedTest {
    public static void main(String[] args) {
        List0<String> array = new ArrayList0<>();
        array.add("tom");array.add("jack");
        array.add("tom");array.add("tony");
        array.add("tom");array.add("tony");
        // 统计每个名字出现的次数
        Map<String, Integer> group = new HashMap<>();
        Iterator0<String> iter = array.iterator();  // 外部迭代
        while (iter.hasNext()) {
            String name = iter.next();
            if (!group.containsKey(name)) {
                int count = 0;  // 计数器
                Iterator0<String> insideIter = array.iterator();  // 嵌套内的迭代
                while (insideIter.hasNext()) {
                    String item = insideIter.next();
                    if (name.equals(item)) {
                        count ++;
                    }
                }
                group.put(name, count);
            }
        }
        System.out.println(group);
    }
}
