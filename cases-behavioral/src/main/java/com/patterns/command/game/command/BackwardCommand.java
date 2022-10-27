package com.patterns.command.game.command;

import com.patterns.command.game.GamePanel;
import com.patterns.command.game.setting.CommandType;

/**
 * 向下转向
 *
 * @author coder
 * @date 2022-06-22 17:50:20
 * @since 1.0.0
 */
public class BackwardCommand extends AbstractCommand {

    public BackwardCommand(GamePanel responder) {
        super(responder);
    }

    @Override
    public boolean enableSnapshot() {
        return true;
    }

    @Override
    public void execute() {
        if (responder.isLegitimateCommand(false)) {
            // 向下转向
            responder.setDirection(CommandType.BACKWARD);
        }
    }

}
