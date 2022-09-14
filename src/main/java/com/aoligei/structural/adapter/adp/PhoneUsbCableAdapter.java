package com.aoligei.structural.adapter.adp;

import com.aoligei.structural.adapter.AccessAdapter;
import com.aoligei.structural.adapter.usb_cable.PhoneUsbCable;

/**
 * 手机数据线适配器
 *
 * @author coder
 * @date 2022-06-01 17:42:06
 * @since 1.0.0
 */
public class PhoneUsbCableAdapter implements AccessAdapter {

    private final PhoneUsbCable phoneUsbCable;

    public PhoneUsbCableAdapter(PhoneUsbCable phoneUsbCable) {
        this.phoneUsbCable = phoneUsbCable;
    }

    @Override
    public void access() {
        System.out.println("    手机数据线适配器接入 type-a 类型的接口，接出 usb-c 类型的接口");
        this.phoneUsbCable.accessTypeC();
    }

}
