package com.aoligei.behavioral.template_method;

/**
 * 已购住房入户
 *
 * @author coder
 * @date 2022-05-27 11:37:35
 * @since 1.0.0
 */
public class HouseEntryDomicile extends AbstractDomicile {

    public HouseEntryDomicile(String username) {
        super(username);
    }

    @Override
    protected void additionalMaterials() {
        System.out.println("    还应准备好：房产证书");
    }
}
