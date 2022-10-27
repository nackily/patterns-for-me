package com.patterns.memento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Random;

/**
 * 游戏
 *
 * @author coder
 * @date 2022-06-28 17:56:54
 * @since 1.0.0
 */
public class Game implements ActionListener {

    private int money;                                                      // 金币
    private String bgm;                                                     // 背景音乐
    private int bgmProgress;                                                // 背景音乐播放进度
    private int bloodBar;                                                   // 血条
    private final Timer timer = new Timer(1000, this);         // 计时器
    private final Random random = new Random();                             // 随机数生成器

    public Game(String bgm) {
        this.actionPerformed(null);
    }

    /**
     * 切换BGM
     * @param bgm bgm
     */
    public void switchBgm(String bgm) {
        // 从头开始播放音乐
        this.bgmProgress = 1;
        this.bgm = bgm;
    }


    public void showStatus() {
        String status = MessageFormat.format("      游戏状态为【金币：{0}，血条：{1}，BGM：{2}，BGM播放进度：{3}秒】",
                money, bloodBar, bgm, bgmProgress);
        System.out.println(status);
    }

    /**
     * 创建存档点
     * @return 存档点
     */
    public Savepoint createSavepoint() {
        System.out.println("    开始存档...");
        this.showStatus();
        return new Savepoint(this.money, this.bgm, this.bloodBar);
    }

    /**
     * 读档
     * @param point 存档点
     */
    public void restore(Savepoint point) {
        System.out.println("    恢复存档...");
        this.money = point.money;
        this.switchBgm(point.bgm);
        this.bloodBar = point.bloodBar;
        this.showStatus();
    }

    /**
     * 定时器计时结束时执行
     * @param e e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // 每过一秒，金币+100，血条重置为1-100之间的随机数
        this.money += 100;
        this.bloodBar = random.nextInt(100);
        this.bgmProgress += 1;
        // 游戏开始，计时器每过 1s 打印一次游戏状态
        this.showStatus();
        // 计时开始
        timer.start();
    }

    /**
     * 存档状态
     */
    protected static class Savepoint {
        private final int money;
        private final String bgm;
        private final int bloodBar;
        public Savepoint(int money, String bgm, int bloodBar) {
            this.money = money;
            this.bgm = bgm;
            this.bloodBar = bloodBar;
        }
    }
}
