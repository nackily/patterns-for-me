package com.patterns.facade.equip.speaker;

import com.patterns.facade.equip.Speaker;

/**
 * 回声音效
 *
 * @author coder
 * @date 2022-06-17 14:07:29
 * @since 1.0.0
 */
public class EchoSoundEffectSpeaker extends Speaker {
    @Override
    public void showEffects() {
        System.out.println("        音箱使用回声音效");
    }
}
