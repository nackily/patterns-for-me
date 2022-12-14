package com.patterns.interpreter.expression.symbol;

import com.patterns.interpreter.Expression;
import com.patterns.interpreter.expression.AbstractSymbolExpression;

/**
 * 或 符号表达式
 *
 * @author coder
 * @date 2022-07-27 16:25:47
 * @since 1.0.0
 */
public class OrExpression extends AbstractSymbolExpression {

    public OrExpression(Expression prev, Expression next) {
        super(prev, next);
    }

    @Override
    public boolean authenticate(String userKey) {
        return super.prev.authenticate(userKey)
                || super.next.authenticate(userKey);
    }
}
