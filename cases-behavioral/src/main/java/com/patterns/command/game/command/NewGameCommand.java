package com.patterns.command.game.command;

import com.patterns.command.game.GamePanel;

/**
 * 新游戏命令
 *
 * @author coder
 * @date 2022-06-22 17:50:20
 * @since 1.0.0
 */
public class NewGameCommand extends AbstractCommand {

    public NewGameCommand(GamePanel responder) {
        super(responder);
    }

    @Override
    public boolean enableSnapshot() {
        return false;
    }

    @Override
    public void execute() {
        // 游戏停止时才允许新开游戏
        boolean gameSuspend = !responder.isGaming();
        boolean gameOvered = responder.isOvered();
        if (gameSuspend || gameOvered) {
            responder.initializeNewGame();
            responder.startGame();
            // 新游戏开始时，清除所有快照
            responder.getContext().clear();
        }
    }

}
