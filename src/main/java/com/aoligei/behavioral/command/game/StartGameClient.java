package com.aoligei.behavioral.command.game;

import com.aoligei.behavioral.command.game.setting.CommandType;
import com.aoligei.behavioral.command.game.setting.KeySetting;
import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * StartGameClient
 *
 * @author coder
 * @date 2022-06-22 17:32:05
 * @since 1.0.0
 */
public class StartGameClient {

    public static void main(String[] args) {
        initKeySettings();
        // 初始化不可改变大小，居中，不可改变大小的窗口
        JFrame frame = new JFrame("my games");
        frame.setSize(850, 725);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加一个游戏面板
        GamePanel gamePanel = new GamePanel();
        frame.setContentPane(gamePanel);
        // 注册键盘监听器
        gamePanel.addKeyListener(new CommandListener(gamePanel));

        frame.setVisible(true);
    }


    /**
     * 初始化按键配置
     */
    public static void initKeySettings() {
        // 向上转向：W键、方向上键
        KeySetting.put(KeyEvent.VK_W, CommandType.FORWARD);
        KeySetting.put(KeyEvent.VK_UP, CommandType.FORWARD);
        // 向右转向：D键、方向右键
        KeySetting.put(KeyEvent.VK_D, CommandType.RIGHT);
        KeySetting.put(KeyEvent.VK_RIGHT, CommandType.RIGHT);
        // 向下转向：S键、方向下键
        KeySetting.put(KeyEvent.VK_S, CommandType.BACKWARD);
        KeySetting.put(KeyEvent.VK_DOWN, CommandType.BACKWARD);
        // 向左转向：A键、方向左键
        KeySetting.put(KeyEvent.VK_A, CommandType.LEFT);
        KeySetting.put(KeyEvent.VK_LEFT, CommandType.LEFT);
        // 继续/暂停：空格键
        KeySetting.put(KeyEvent.VK_SPACE, CommandType.TOGGLE_STATE);
        // 新对局：P键
        KeySetting.put(KeyEvent.VK_P, CommandType.NEW_GAME);
        // 后退一步：ESC键
        KeySetting.put(KeyEvent.VK_ESCAPE, CommandType.PREVIOUS);
    }

}
