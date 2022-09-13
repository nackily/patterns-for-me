package com.aoligei.creational.builder.message;

import com.aoligei.creational.builder.message.builder.MailBuilder;
import com.aoligei.creational.builder.message.builder.ShortMessageBuilder;
import com.aoligei.creational.builder.message.builder.SiteLatterBuilder;
import com.aoligei.creational.builder.message.product.Mail;
import com.aoligei.creational.builder.message.product.ShortMessage;
import com.aoligei.creational.builder.message.product.SiteLetter;

/**
 * Client
 *
 * @author coder
 * @date 2022-09-09 16:27:20
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> Start ---------------------------------------|");
        // 一封欢迎新用户的站内信
        SiteLetter welcome = new GenericConstructor<>(new SiteLatterBuilder())
                .welcome("system", "jack");
        welcome.printSiteMessage();

        // 一条订单超时的短信
        ShortMessage timeout = new GenericConstructor<>(new ShortMessageBuilder())
                .orderTimeout("13135", "18790452415");
        timeout.printShortMessage();

        // 一条物流信息变更的站内信
        SiteLetter logisticsChanged = new GenericConstructor<>(new SiteLatterBuilder())
                .logisticsChanged("system", "tom",
                        "https://on.mall/logistics?id=797e983f-30c0-4ab6-855f-5b24c3f3f543");
        logisticsChanged.printSiteMessage();

        // 一条订单完成的邮件
        Mail mail = new GenericConstructor<>(new MailBuilder())
                .orderCompleted("system@on-mall.com", "67545139@163.com",
                        new Mail.Attachment("a560e36a-31ea.png", 345, null));
        mail.printMail();
    }
}
