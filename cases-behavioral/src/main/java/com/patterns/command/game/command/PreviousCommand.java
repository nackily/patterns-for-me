package com.patterns.command.game.command;

import com.patterns.command.game.CommandHistoryContext;
import com.patterns.command.game.GamePanel;

/**
 * 回退到上一步命令
 *
 * @author coder
 * @date 2022-06-24 10:09:44
 * @since 1.0.0
 */
public class PreviousCommand extends AbstractCommand {

    public PreviousCommand(GamePanel responder) {
        super(responder);
    }

    @Override
    public boolean enableSnapshot() {
        return false;
    }

    @Override
    public void execute() {
        // 游戏结束时才允许回退一步
        if (responder.isOvered()) {
            CommandHistoryContext context = responder.getContext();
            // 快照库不为空
            if (!context.isEmpty()) {
                context.pop().unExecute();
            }
        }
    }
}
