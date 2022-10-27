package com.patterns.bridge.executor;

import com.patterns.bridge.AbstractNotifer;
import com.patterns.bridge.AbstractTriggerExecutor;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 立即执行的执行器
 *
 * @author coder
 * @date 2022-07-07 18:42:23
 * @since 1.0.0
 */
public class ImmediatelyExecutor extends AbstractTriggerExecutor {

    public ImmediatelyExecutor(AbstractNotifer handler) {
        super(handler);
    }

    @Override
    protected void execute() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(MessageFormat.format("    [{0}]已提交通知到立即执行处理器...", time));
        super.notifer.doNotify();
    }
}
