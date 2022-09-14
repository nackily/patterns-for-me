package com.aoligei.behavioral.template_method.domicile;

import com.aoligei.behavioral.template_method.AbstractDomicile;

/**
 * 亲属关系入户
 *
 * @author coder
 * @date 2022-05-27 11:39:13
 * @since 1.0.0
 */
public class RelativesEntryDomicile extends AbstractDomicile {

    public RelativesEntryDomicile(String username) {
        super(username);
    }

    @Override
    protected void additionalMaterials() {
        System.out.println("    还应准备好：亲属的身份证、户口簿");
    }
}
