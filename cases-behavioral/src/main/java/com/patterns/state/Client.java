package com.patterns.state;

/**
 * Client
 *
 * @author coder
 * @date 2022-07-15 16:30:44
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> Start -------------------------------------------------------------------------|");
        // 新建文稿
        Document doc = new Document("四川13所高校外籍师生走进三星堆等地领略巴蜀文明", "ssss");
        // 重新编辑 再次保存
        doc.saveDraft("四川13所高校外籍师生走进三星堆等地领略巴蜀文明", "7月14日，“文化中国·锦绣" +
                "四川——高校外籍师生巴蜀文化品悟之旅”活动走进四川德阳市绵竹年画村、广汉三星堆博物馆。" +
                "来自四川13所高校的26个国家的48名外籍师生在绵竹年画村提笔绘制年画，体验中国非物质文化魅力...");
        // 投稿
        doc.contribute();
        // 撤稿
        doc.revoke();
        // 审核
        doc.audit();
        // 下架
        doc.remove();
    }
}
