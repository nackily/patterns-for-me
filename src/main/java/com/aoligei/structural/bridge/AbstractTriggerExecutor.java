package com.aoligei.structural.bridge;

/**
 * 抽象的触发机制执行器
 *
 * @author coder
 * @date 2022-07-07 18:01:29
 * @since 1.0.0
 */
public abstract class AbstractTriggerExecutor {

    /**
     * 通知器
     */
    protected final AbstractNotifer notifer;


    public AbstractTriggerExecutor(AbstractNotifer handler) {
        this.notifer = handler;
    }

    /**
     * 执行
     * @throws InterruptedException 中断异常
     */
    protected abstract void execute() throws InterruptedException;

}
