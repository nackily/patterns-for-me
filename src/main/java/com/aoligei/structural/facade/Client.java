package com.aoligei.structural.facade;

/**
 * Client
 *
 * @author coder
 * @date 2022-06-17 14:12:49
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        // 打开所有设备
        ModelFacade.INSTANCE.open();
        // 切换到居家模式
        ModelFacade.INSTANCE.familyMode();
        // 切换到专业模式
        ModelFacade.INSTANCE.professionalMode();
        // 切换到现场模式
        ModelFacade.INSTANCE.liveMode();
        // 关闭所有设备
        ModelFacade.INSTANCE.close();
    }

}
