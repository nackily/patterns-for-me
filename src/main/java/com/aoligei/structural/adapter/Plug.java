package com.aoligei.structural.adapter;

/**
 * 插头
 *
 * @author xg-ran
 * @date 2022-06-01 09:21:48
 * @since 1.0.0
 */
public class Plug {

    public static void main(String[] args) {
        System.out.println("手机充电时接线，该插座接出 usb-a 类型的接口：");
        AccessUsbCable phone = new PhoneUsbCableAdapter(new PhoneUsbCable());
        phone.access();

        System.out.println("游戏机充电时接线，该插座接出 usb-a 类型的接口：");
        AccessUsbCable gameConsole = new GameConsoleUsbCableAdapter(new GameConsoleUsbCable());
        gameConsole.access();
    }

}
