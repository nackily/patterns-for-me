package com.patterns.interpreter.expression;

import com.patterns.interpreter.Expression;

/**
 * 符号表达式
 *
 * @author coder
 * @date 2022-07-27 16:17:33
 * @since 1.0.0
 */
public abstract class AbstractSymbolExpression implements Expression {

    /**
     * 符号前面的权限表达式
     */
    protected final Expression prev;

    /**
     * 符号后面的权限表达式
     */
    protected final Expression next;

    public AbstractSymbolExpression(Expression prev, Expression next) {
        this.prev = prev;
        this.next = next;
    }

    /**
     * 多个权限表达式鉴权
     * @param userKey 用户
     * @return 是否拥有权限
     */
    @Override
    public abstract boolean authenticate(String userKey);
}
