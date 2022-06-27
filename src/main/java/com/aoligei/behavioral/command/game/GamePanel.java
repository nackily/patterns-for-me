package com.aoligei.behavioral.command.game;

import com.aoligei.behavioral.command.game.setting.CommandType;
import com.aoligei.behavioral.command.game.setting.Hint;
import com.aoligei.behavioral.command.game.setting.ImageResource;
import com.aoligei.behavioral.command.game.setting.KeySetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * 游戏面板
 *
 * @author coder
 * @date 2022-06-22 17:55:08
 * @since 1.0.0
 */
public class GamePanel extends JPanel implements ActionListener {
    private boolean overed = false;                                 // 当前对局是否结束
    private boolean gaming = false;                                 // 正在游戏中
    private final Timer timer = new Timer(300, this);  // 计时器
    private int steps = 0;                                          // 移动步数计数器
    private int score = 0;                                          // 分数计数器
    private int starX;                                              // 星星的X坐标
    private int starY;                                              // 星星的Y坐标
    private int catX;                                               // 猫的X坐标
    private int catY;                                               // 猫的Y坐标
    private CommandType direction;                                  // 猫的移动方向
    private static final Random RANDOM = new Random();              // 随机数生成器
    private final CommandHistoryContext context = new CommandHistoryContext();

    public GamePanel() {
        // 让面板获取焦点，否则无法监听事件
        this.setFocusable(true);
        // 初始化一个新游戏对局
        this.initializeNewGame();
        // 开始游戏
        this.startGame();
    }

    /**
     * 初始化一个新游戏对局
     */
    public void initializeNewGame() {
        this.overed = false;
        this.steps = this.score = 0;
        // 在游戏区域内随机生成一个星星
        // 星星的像素是 20*20，游戏区域宽高是 800*600，游戏区域的左上角坐标为（25,75）：
        // 星星的X坐标的随机取值范围为[0,800/20) * 20 + 25
        // 星星的Y坐标的随机取值范围为[0,600/20) * 20 + 75
        this.starX = RANDOM.nextInt(40) * 20 + 25;
        this.starY = RANDOM.nextInt(30) * 20 + 75;
        // 猫从游戏区域的左上开始，往右移动
        this.catX = 25;
        this.catY = 75;
        this.direction = CommandType.RIGHT;
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        this.gaming = true;
        this.timer.start();
        // 刷新
        this.repaint(25, 25, 800, 650);
    }

    /**
     * 暂停游戏
     */
    public void stopGame() {
        this.gaming = false;
        this.timer.stop();
        // 刷新
        this.repaint(25, 25, 800, 650);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 绘制游戏区域
        super.paintComponent(g);
        this.setBackground(Color.white);
        this.paintScoreArea(g);
        this.paintGameArea(g);
        // 对局暂停中
        if (!isGaming()) {
            this.paintSuspendArea(g, true);
        }
        // 对局已结束
        if (this.isOvered()) {
            this.paintSuspendArea(g, false);
        }
    }

    /**
     * 绘制对局挂起区域
     * @param g Graphics
     * @param allowedContinue 允许继续对局
     */
    private void paintSuspendArea(Graphics g, boolean allowedContinue) {
        g.setColor(Color.WHITE);
        g.fillRect(200, 200, 450, 250);
        // 提示当前状态
        String stateHint = allowedContinue ? Hint.HINT_FOR_PAUSE : Hint.HINT_FOR_GAME_OVER;
        g.setColor(new Color(135, 206,235));
        g.setFont(new Font("Consoles", Font.BOLD, 25));
        g.drawString(stateHint, 350, 250);
        // 按键提示
        java.util.List<String> settingsDesc = KeySetting.getSettingsDesc();
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Consoles", Font.BOLD, 16));
        for (int i = 0, y = 280; i < settingsDesc.size(); i++, y += 25) {
            g.drawString(settingsDesc.get(i), 230, y);
        }
    }

    /**
     * 绘制得分区
     * @param g Graphics
     */
    private void paintScoreArea(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(25, 25, 800, 50);
        // 得分、步数情况
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consoles", Font.BOLD, 16));
        String scoreInfo = "得分：" + this.score;
        g.drawString(scoreInfo, 65, 55);
        String stepsInfo = "步数：" + this.steps;
        g.drawString(stepsInfo, 700, 55);
    }

    /**
     * 绘制游戏区
     * @param g Graphics
     */
    private void paintGameArea(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(25, 75, 800, 600);
        // 绘制星星
        ImageResource.STAR.paintIcon(this, g, this.starX, this.starY);
        // 绘制猫
        ImageResource.CAT.paintIcon(this, g, this.catX, this.catY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isOvered() ){
            return;
        }
        // 计步器
        this.steps ++;
        // 猫向当前方向移动
        switch (this.direction) {
            case FORWARD:
                this.catY -= 20;break;
            case RIGHT:
                this.catX += 20;break;
            case BACKWARD:
                this.catY += 20;break;
            case LEFT:
                this.catX -= 20;break;
            default:break;
        }
        // 猫是否到达边界
        if (this.gameOver()) {
            // 游戏结束
            this.overed = true;
        }
        // 抵达星星的位置
        if (this.catX == this.starX && this.catY == this.starY) {
            // 得分器
            this.score ++;
            // 重新生成一个星星
            this.starX = RANDOM.nextInt(40) * 20 + 25;
            this.starY = RANDOM.nextInt(30) * 20 + 75;
        }
        // 刷新页面
        this.repaint(25, 25, 800, 650);
        // 重新启动计时器
        timer.start();
    }

    /**
     * 猫是否越界
     * @return true:越界
     */
    private boolean gameOver() {
        // 猫超过左边界时X坐标 < 区域左上角X
        if (this.catX < 25) {
            return true;
        }
        // 猫超过右边界时X坐标 > 区域左上角X + 区域宽 - 猫的宽
        // 25 + 800 - 20 = 805
        if (this.catX > 805) {
            return true;
        }
        // 猫超过上边界时Y坐标 < 区域左上角Y
        if (this.catY < 75) {
            return true;
        }
        // 猫超过下边界时Y坐标 > 区域左上角Y + 区域高 - 猫的高
        // 75 + 600 - 20 = 655
        return this.catY > 655;
    }

    /**
     * 当前转向请求是否合法，
     * 不允许转向后的方向与当前方向在一条直线上
     * @param horizontally 转向水平转向
     * @return 是否合法
     */
    public boolean isLegitimateCommand(boolean horizontally) {
        if (direction.getDirectCode() % 2 == 0) {
            return !horizontally;
        } else {
            return horizontally;
        }
    }

    public boolean isGaming() {
        return gaming;
    }

    public void setGaming(boolean gaming) {
        this.gaming = gaming;
    }

    public boolean isOvered() {
        return overed;
    }

    public void setOvered(boolean overed) {
        this.overed = overed;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStarX() {
        return starX;
    }

    public void setStarX(int starX) {
        this.starX = starX;
    }

    public int getStarY() {
        return starY;
    }

    public void setStarY(int starY) {
        this.starY = starY;
    }

    public int getCatX() {
        return catX;
    }

    public void setCatX(int catX) {
        this.catX = catX;
    }

    public int getCatY() {
        return catY;
    }

    public void setCatY(int catY) {
        this.catY = catY;
    }

    public CommandType getDirection() {
        return direction;
    }

    public void setDirection(CommandType dire) {
        this.direction = dire;
    }

    public CommandHistoryContext getContext() {
        return context;
    }

}
