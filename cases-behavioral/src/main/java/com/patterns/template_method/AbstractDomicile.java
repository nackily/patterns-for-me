package com.patterns.template_method;

import java.text.MessageFormat;

/**
 * 抽象的户籍地
 *
 * @author coder
 * @date 2022-05-27 10:14:39
 * @since 1.0.0
 */
public abstract class AbstractDomicile {

    private final String username;
    public AbstractDomicile(String username) {
        this.username = username;
    }

    /**
     * 迁移户口
     */
    public final void migrate () {
        System.out.println(MessageFormat.format("||--> migrate domicile for {0} ------------------------------------|", this.username));
        this.prepareBasicMaterials();
        this.additionalMaterials();
        this.doCheck();
        this.doBusiness();
        this.grantCertificate();
    }

    /**
     * 准备基本材料
     */
    private void prepareBasicMaterials(){
        System.out.println("    应准备好当前的身份证、原始户口簿、入户申请表");
    }

    /**
     * 准备附加材料
     */
    protected abstract void additionalMaterials();

    /**
     * 钩子方法，有些落户方式需要检查
     */
    protected void doCheck(){}

    /**
     * 办理业务
     */
    private void doBusiness(){
        System.out.println("    提交资料，由工作人员审核及办理");
        System.out.println("        户口已迁出");
        System.out.println("        已迁入新户口");
    }

    /**
     * 拿证
     */
    private void grantCertificate(){
        System.out.println("    发放新户口簿");
    }

}
