package com.aoligei.structural.bridge.executor;

import com.aoligei.structural.bridge.AbstractNotifer;
import com.aoligei.structural.bridge.AbstractTriggerExecutor;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定时执行的执行器
 *
 * @author coder
 * @date 2022-07-07 18:57:18
 * @since 1.0.0
 */
public class TimerExecutor extends AbstractTriggerExecutor {

    /**
     * 定时表达式
     */
    private final String express;

    public TimerExecutor(AbstractNotifer handler, String express) {
        super(handler);
        this.express = express;
    }

    @Override
    protected void execute() throws InterruptedException {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(MessageFormat.format("    [{0}]已提交通知到定时执行处理器，定时表达式为【{1}】...", time, express));
        // todo: 在此处实现定时触发的机制
        Thread.sleep(2000);
        // 模拟系统统计报表
        int num = 2;
        double total = 38900.0;
        String content = MessageFormat.format(super.notifer.getContent(), num, total);
        super.notifer.setContent(content);
        super.notifer.doNotify();
    }
}
