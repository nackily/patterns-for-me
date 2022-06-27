package com.aoligei.behavioral.command.game.command;

import com.aoligei.behavioral.command.game.GamePanel;
import com.aoligei.behavioral.command.game.setting.CommandType;

/**
 * 向右转向
 *
 * @author coder
 * @date 2022-06-22 17:50:20
 * @since 1.0.0
 */
public class RightCommand extends AbstractCommand {

    public RightCommand(GamePanel responder) {
        super(responder);
    }

    @Override
    public boolean enableSnapshot() {
        return true;
    }

    @Override
    public void execute() {
        if (responder.isLegitimateCommand(true)) {
            // 快照
            super.takeSnapshot();
            // 向右转向
            responder.setDirection(CommandType.RIGHT);
        }
    }

}
