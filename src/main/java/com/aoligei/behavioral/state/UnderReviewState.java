package com.aoligei.behavioral.state;

import java.util.Arrays;

/**
 * 审核中状态
 *
 * @author coder
 * @date 2022-07-17 16:36:21
 * @since 1.0.0
 */
public class UnderReviewState implements State {

    private final Document doc;
    public UnderReviewState(Document doc) {
        this.doc = doc;
    }

    @Override
    public String getStateName() {
        return "审核中";
    }

    @Override
    public void saveDraft(String title, String content) {
        System.out.println("        非法操作：审核中状态不允许保存草稿");
    }

    @Override
    public void contribute() {
        System.out.println("        非法操作：当前文稿正在审核中，请勿重复投稿！");
    }

    @Override
    public void audit() {
        // 如果包含了某些敏感信息，审核不通过，其他情况审核通过
        String[] sensitiveKeywords = new String[]{"资本", "社会", "恐怖"};
        String title = doc.getTitle();
        String content = doc.getContent();
        boolean val1 = Arrays.stream(sensitiveKeywords)
                .anyMatch(item -> title.contains(item) || content.contains(item));
        if (val1) {
            System.out.println("        包含敏感信息，审核失败");
            doc.nextState(new DraftState(doc));
        } else {
            System.out.println("        审核通过");
            doc.nextState(new PublishedState(doc));
        }
    }

    @Override
    public void revoke() {
        System.out.println("        非法操作：审核中状态不允许撤稿");
    }

    @Override
    public void remove() {
        System.out.println("        非法操作：审核中状态不允许下架");
    }
}
