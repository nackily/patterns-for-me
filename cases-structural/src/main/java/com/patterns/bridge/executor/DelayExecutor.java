package com.patterns.bridge.executor;

import com.patterns.bridge.AbstractNotifer;
import com.patterns.bridge.AbstractTriggerExecutor;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 延时一段时间执行的执行器
 *
 * @author coder
 * @date 2022-07-07 18:57:18
 * @since 1.0.0
 */
public class DelayExecutor extends AbstractTriggerExecutor {

    /**
     * 延迟秒数
     */
    private final int delaySeconds;

    public DelayExecutor(AbstractNotifer handler, int delaySeconds) {
        super(handler);
        this.delaySeconds = delaySeconds;
    }

    @Override
    protected void execute() throws InterruptedException {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(MessageFormat.format("    [{0}]已提交通知到延迟执行处理器...", time));
        Thread.sleep(delaySeconds * 1000L);
        super.notifer.doNotify();
    }
}
