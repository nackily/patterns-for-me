package com.aoligei.behavioral.interpreter.expression;

import java.util.Arrays;

/**
 * 权限类型枚举
 *
 * @author coder
 * @date 2022-9-14 10:16:23
 * @since 1.0.0
 */
public enum PermissionType {

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
