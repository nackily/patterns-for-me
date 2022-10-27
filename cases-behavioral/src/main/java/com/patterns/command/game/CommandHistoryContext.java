package com.patterns.command.game;

import com.patterns.command.game.command.AbstractCommand;

import java.util.Stack;

/**
 * 历史快照
 *
 * @author coder
 * @date 2022-06-23 22:21:04
 * @since 1.0.0
 */
public class CommandHistoryContext {

    /**
     * 历史命令
     */
    private final Stack<AbstractCommand> history = new Stack<>();

    /**
     * 入栈
     * @param c 命令
     */
    public void push(AbstractCommand c) {
        history.push(c);
    }

    /**
     * 出栈
     * @return 命令
     */
    public AbstractCommand pop() {
        return history.pop();
    }

    /**
     * 栈是否为空
     * @return true是
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }

    /**
     * 清空快照
     */
    public void clear() {
        history.clear();
    }
}
