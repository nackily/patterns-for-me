package com.aoligei.behavioral.interpreter;

/**
 * 表达式
 *
 * @author coder
 * @date 2022-07-27 16:00:14
 * @since 1.0.0
 */
public interface Expression {

    /**
     * 鉴权
     * @param userKey 用户
     * @return 是否拥有权限
     */
    boolean authenticate(String userKey);
}
