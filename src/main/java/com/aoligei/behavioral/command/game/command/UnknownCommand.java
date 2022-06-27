package com.aoligei.behavioral.command.game.command;

import com.aoligei.behavioral.command.game.GamePanel;

/**
 * 未知命令
 *
 * @author coder
 * @date 2022-06-24 13:13:08
 * @since 1.0.0
 */
public class UnknownCommand extends AbstractCommand{

    public UnknownCommand(GamePanel responder) {
        super(responder);
    }

    @Override
    public boolean enableSnapshot() {
        return false;
    }

    @Override
    public void execute() {

        System.out.println("未知命令");
    }
}
