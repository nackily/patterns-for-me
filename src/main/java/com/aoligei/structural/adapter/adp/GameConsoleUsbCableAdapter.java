package com.aoligei.structural.adapter.adp;

import com.aoligei.structural.adapter.AccessAdapter;
import com.aoligei.structural.adapter.usb_cable.GameConsoleUsbCable;

/**
 * 游戏机数据线适配器
 *
 * @author coder
 * @date 2022-06-01 17:42:06
 * @since 1.0.0
 */
public class GameConsoleUsbCableAdapter implements AccessAdapter {

    private final GameConsoleUsbCable gameConsoleUsbCable;

    public GameConsoleUsbCableAdapter(GameConsoleUsbCable gameConsoleUsbCable) {
        this.gameConsoleUsbCable = gameConsoleUsbCable;
    }

    @Override
    public void access() {
        System.out.println("    游戏机数据线适配器接入 type-a 类型的接口，接出 usb-b 类型的接口");
        this.gameConsoleUsbCable.accessTypeB();
    }

}
