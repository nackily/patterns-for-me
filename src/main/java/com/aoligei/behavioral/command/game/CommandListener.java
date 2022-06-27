package com.aoligei.behavioral.command.game;

import com.aoligei.behavioral.command.game.command.*;
import com.aoligei.behavioral.command.game.setting.CommandType;
import com.aoligei.behavioral.command.game.setting.KeySetting;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * CommandListener
 *
 * @author coder
 * @date 2022-06-23 09:21:16
 * @since 1.0.0
 */
public class CommandListener extends KeyAdapter {

    private final GamePanel panel;
    public CommandListener(GamePanel panel) {
        this.panel = panel;
    }

    /**
     * 按键按下事件
     * @param e 事件
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // 监听按键动作
        CommandType commandType = KeySetting.get(e.getKeyCode());
        if (commandType == null) {
            System.out.println("未监听该按键");
            return;
        }
        AbstractCommand command = null;
        switch (commandType) {
            case FORWARD:
                command = new ForwardCommand(panel);
                break;
            case RIGHT:
                command = new RightCommand(panel);
                break;
            case BACKWARD:
                command = new BackwardCommand(panel);
                break;
            case LEFT:
                command = new LeftCommand(panel);
                break;
            case TOGGLE_STATE:
                command = new ToggleStateCommand(panel);
                break;
            case PREVIOUS:
                command = new PreviousCommand(panel);
                break;
            case NEW_GAME:
                command = new NewGameCommand(panel);
                break;
            default:
                command = new UnknownCommand(panel);
                break;
        }

        if (command.enableSnapshot()) {
            command.takeSnapshot();
        }
        command.execute();
    }
}
