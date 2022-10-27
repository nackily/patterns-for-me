package com.patterns.command.game.command;

import com.patterns.command.game.GamePanel;

/**
 * 切换状态命令
 *
 * @author coder
 * @date 2022-06-23 10:40:00
 * @since 1.0.0
 */
public class ToggleStateCommand extends AbstractCommand {

    public ToggleStateCommand(GamePanel responder) {
        super(responder);
    }

    @Override
    public boolean enableSnapshot() {
        return false;
    }

    @Override
    public void execute() {
        if (responder.isGaming()) {
            responder.stopGame();
        } else {
            responder.startGame();
        }
    }
}
