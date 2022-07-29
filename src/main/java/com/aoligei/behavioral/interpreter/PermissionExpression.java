package com.aoligei.behavioral.interpreter;

import java.util.Arrays;
import java.util.List;

/**
 * 权限表达式
 *
 * @author coder
 * @date 2022-07-27 15:59:06
 * @since 1.0.0
 */
public class PermissionExpression implements Expression{

    /**
     * 权限值
     */
    private final String auth;

    /**
     * 类型
     */
    private final PermissionType type;

    public PermissionExpression(String auth, PermissionType type) {
        this.auth = auth;
        this.type = type;
    }

    @Override
    public boolean authenticate(String userKey) {
        switch (type) {
            case ROLE:
                List<String> roles = Simulation.getUserConfig(userKey).getRoles();
                return roles.contains(auth);
            case ELEMENT:
                List<String> elements = Simulation.getUserConfig(userKey).getElements();
                return elements.contains(auth);
            default:
                throw new RuntimeException("未知类型");
        }
    }
}

enum PermissionType {

    /**
     * 角色鉴权
     */
    ROLE("R:"),

    /**
     * 页面元素鉴权
     */
    ELEMENT("E:");

    final String key;

    PermissionType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public PermissionType fromKey(String key) {
        return Arrays.stream(PermissionType.values())
                .filter(item -> item.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }
}
