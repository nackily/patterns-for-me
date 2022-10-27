package com.patterns.template_method.domicile;

import com.patterns.template_method.AbstractDomicile;

/**
 * 学历入户
 *
 * @author coder
 * @date 2022-05-27 11:31:25
 * @since 1.0.0
 */
public class EducationEntryDomicile extends AbstractDomicile {

    public EducationEntryDomicile(String username) {
        super(username);
    }

    @Override
    protected void additionalMaterials() {
        System.out.println("    还应准备好：学历证书、学位证书");
    }

    @Override
    protected void doCheck() {
        System.out.println("    查验证书是否有效，并且学历至少要求为大学本科学历");
    }
}
