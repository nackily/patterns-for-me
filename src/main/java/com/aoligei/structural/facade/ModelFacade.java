package com.aoligei.structural.facade;

import com.aoligei.structural.facade.equip.bulb.GreenBulb;
import com.aoligei.structural.facade.equip.bulb.RedBulb;
import com.aoligei.structural.facade.equip.bulb.YellowBulb;
import com.aoligei.structural.facade.equip.bulb.decorator.BrightEffectDecorator;
import com.aoligei.structural.facade.equip.bulb.decorator.MarqueeEffectDecorator;
import com.aoligei.structural.facade.equip.bulb.decorator.StrobeEffectDecorator;
import com.aoligei.structural.facade.equip.speaker.EchoSoundEffectSpeaker;
import com.aoligei.structural.facade.equip.speaker.OriginalSoundEffectSpeaker;
import com.aoligei.structural.facade.equip.speaker.ReverbSoundEffectSpeaker;

/**
 * 模式门面
 *
 * @author coder
 * @date 2022-06-17 14:14:12
 * @since 1.0.0
 */
public enum ModelFacade {
    /**
     * 唯一实例
     */
    INSTANCE;

    private Equipment redBulb = new MarqueeEffectDecorator(new RedBulb());
    private Equipment greenBulb = new MarqueeEffectDecorator(new GreenBulb());
    private Equipment yellowBulb = new MarqueeEffectDecorator(new YellowBulb());
    private Equipment speaker = new EchoSoundEffectSpeaker();

    public void open() {
        System.out.println("|==> 打开设备-------------------------------------------------------------|");
        this.redBulb.on();
        this.greenBulb.on();
        this.yellowBulb.on();
        this.speaker.on();
        this.liveMode();
    }

    public void close() {
        System.out.println("|==> 关闭设备-------------------------------------------------------------|");
        this.redBulb.off();
        this.greenBulb.off();
        this.yellowBulb.off();
        this.speaker.off();
    }

    public void familyMode() {
        System.out.println("|==> 居家模式-------------------------------------------------------------|");
        this.greenBulb = new BrightEffectDecorator(new GreenBulb());
        this.yellowBulb = new BrightEffectDecorator(new YellowBulb());
        this.speaker = new ReverbSoundEffectSpeaker();
        System.out.println("    灯光效果：");
        this.greenBulb.showEffects();
        this.yellowBulb.showEffects();
        System.out.println("    音响效果：");
        this.speaker.showEffects();
    }

    public void liveMode() {
        System.out.println("|==> 现场模式-------------------------------------------------------------|");
        this.redBulb = new MarqueeEffectDecorator(new RedBulb());
        this.greenBulb = new MarqueeEffectDecorator(new GreenBulb());
        this.yellowBulb = new MarqueeEffectDecorator(new YellowBulb());
        this.speaker = new EchoSoundEffectSpeaker();
        System.out.println("    灯光效果：");
        this.redBulb.showEffects();
        this.greenBulb.showEffects();
        this.yellowBulb.showEffects();
        System.out.println("    音响效果：");
        this.speaker.showEffects();
    }

    public void professionalMode() {
        System.out.println("|==> 专业模式-------------------------------------------------------------|");
        this.greenBulb = new StrobeEffectDecorator(new GreenBulb());
        this.speaker = new OriginalSoundEffectSpeaker();
        System.out.println("    灯光效果：");
        this.greenBulb.showEffects();
        System.out.println("    音响效果：");
        this.speaker.showEffects();
    }
}
