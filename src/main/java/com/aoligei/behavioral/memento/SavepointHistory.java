package com.aoligei.behavioral.memento;

import java.util.Stack;

/**
 * 历史存档点
 *
 * @author coder
 * @date 2022-06-28 17:55:59
 * @since 1.0.0
 */
public class SavepointHistory {

    /**
     * 当前游戏
     */
    private final Game currentGame;

    /**
     * 存档点容器
     */
    private final Stack<Game.Savepoint> savepointStack = new Stack<>();
    public SavepointHistory(Game currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * 存档
     */
    public void store() {
        Game.Savepoint point = currentGame.createSavepoint();
        savepointStack.push(point);
    }

    /**
     * 读档
     */
    public void restore() {
        if (!savepointStack.isEmpty()) {
            Game.Savepoint point = savepointStack.pop();
            currentGame.restore(point);
        }
    }
}
