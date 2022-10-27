package com.patterns.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 模拟数据
 *
 * @author coder
 * @date 2022-07-27 16:34:00
 * @since 1.0.0
 */
public class Simulation {

    private final static List<UserPermissionConfig> CONFIGS = new ArrayList<>();
    static {
        CONFIGS.add(new UserPermissionConfig("tom",
                Arrays.asList("warehouse-manager", "salesperson"),
                Arrays.asList("system.user.query", "system.user.export-to-xlsx", "system.user.export-to-word")));
        CONFIGS.add(new UserPermissionConfig("lisa",
                Collections.singletonList("warehouse-manager"),
                Arrays.asList("system.user.query", "sys.user.detail")));
        CONFIGS.add(new UserPermissionConfig("jack",
                Arrays.asList("salesperson", "admin"),
                Collections.emptyList()));
    }

    public static UserPermissionConfig getUserConfig(String userKey) {
        return CONFIGS.stream()
                .filter(item -> item.getKey().equals(userKey))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未知用户"));
    }

    /**
     * 用户权限配置
     */
    public static class UserPermissionConfig {
        private final String key;                                         // 用户key
        private final List<String> roles;                                 // 用户拥有的角色
        private final List<String> elements;                              // 用户拥有的资源
        public UserPermissionConfig(String key, List<String> roles, List<String> elements) {
            this.key = key;
            this.roles = roles;
            this.elements = elements;
        }
        public String getKey() {
            return key;
        }
        public List<String> getRoles() {
            return roles;
        }
        public List<String> getElements() {
            return elements;
        }
    }
}
