package com.patterns.interpreter.expression;

import com.patterns.interpreter.Expression;
import com.patterns.interpreter.Simulation;

import java.util.List;

/**
 * 权限表达式
 *
 * @author coder
 * @date 2022-07-27 15:59:06
 * @since 1.0.0
 */
public class PermissionExpression implements Expression {

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

