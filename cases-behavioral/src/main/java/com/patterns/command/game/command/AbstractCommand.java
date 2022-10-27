package com.patterns.command.game.command;

import com.patterns.command.game.CommandHistoryContext;
import com.patterns.command.game.GamePanel;
import com.patterns.command.game.setting.CommandType;

/**
 * 命令
 *
 * @author coder
 * @date 2022-06-22 17:40:17
 * @since 1.0.0
 */
public abstract class AbstractCommand {

    private boolean overed;
    private boolean gaming;
    private int steps;
    private int score;
    private int starX;
    private int starY;
    private int catX;
    private int catY;
    private CommandType direction;

    protected final GamePanel responder;
    public AbstractCommand(GamePanel responder) {
        this.responder = responder;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * 生成快照
     */
    public void takeSnapshot() {
        CommandHistoryContext context = responder.getContext();
        overed = responder.isOvered();
        gaming = responder.isGaming();
        steps = responder.getSteps();
        score = responder.getScore();
        starX = responder.getStarX();
        starY = responder.getStarY();
        catX = responder.getCatX();
        catY = responder.getCatY();
        direction = responder.getDirection();
        context.push(this);
    }

    /**
     * 撤销命令的影响
     */
    protected void unExecute() {
        responder.setGaming(this.gaming);
        responder.setOvered(this.overed);
        responder.setSteps(this.steps);
        responder.setScore(this.score);
        responder.setStarX(this.starX);
        responder.setStarY(this.starY);
        responder.setCatX(this.catX);
        responder.setCatY(this.catY);
        responder.setDirection(this.direction);
    }


    /**
     * 开启快照
     * @return true -> 开启快照
     */
    public abstract boolean enableSnapshot();

    /**
     * 执行命令
     */
    public abstract void execute();
}
