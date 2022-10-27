package com.patterns.command.game.command;

import com.patterns.command.game.GamePanel;
import com.patterns.command.game.setting.CommandType;

/**
 * 向左转向
 *
 * @author coder
 * @date 2022-06-22 17:50:20
 * @since 1.0.0
 */
public class LeftCommand extends AbstractCommand {

    public LeftCommand(GamePanel responder) {
        super(responder);
    }

    @Override
    public boolean enableSnapshot() {
        return true;
    }

    @Override
    public void execute() {
        if (responder.isLegitimateCommand(true)) {
            // 向左转向
            responder.setDirection(CommandType.LEFT);
        }
    }

}
