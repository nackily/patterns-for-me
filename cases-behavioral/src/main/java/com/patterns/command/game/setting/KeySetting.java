package com.patterns.command.game.setting;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 按键配置
 *
 * @author coder
 * @date 2022-06-24 10:39:48
 * @since 1.0.0
 */
public class KeySetting {

    private static final Map<Integer, CommandType> SETTINGS = new HashMap<>(4);

    /**
     * 获取按键设置的描述
     * @return 描述
     */
    public static List<String> getSettingsDesc() {
        Map<CommandType, List<Map.Entry<Integer, CommandType>>> groupedByCommand = SETTINGS.entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue));
        return groupedByCommand.entrySet().stream()
                .map(item -> {
                    String allKeys = item.getValue().stream().map(Map.Entry::getKey)
                            .map(KeyEvent::getKeyText)
                            .collect(Collectors.joining(" , "));
                    return item.getKey().getDesc() +
                            "：[" +
                            allKeys +
                            "]";
                }).collect(Collectors.toList());
    }

    /**
     * 添加配置
     * @param key 按键
     * @param type 命令类型
     */
    public static void put(Integer key, CommandType type) {
        SETTINGS.put(key, type);
    }

    /**
     * 获取命令类型
     * @param key 按键
     * @return 命令类型
     */
    public static CommandType get(Integer key) {
        return SETTINGS.get(key);
    }
}
