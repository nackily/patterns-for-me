package com.aoligei.behavioral.state.status;


import com.aoligei.behavioral.state.Document;
import com.aoligei.behavioral.state.State;

/**
 * 草稿状态
 *
 * @author coder
 * @date 2022-07-17 16:12:57
 * @since 1.0.0
 */
public class DraftState implements State {

    private final Document doc;
    public DraftState(Document doc) {
        this.doc = doc;
    }

    @Override
    public String getStateName() {
        return "草稿";
    }

    @Override
    public void saveDraft(String title, String content) {
        doc.setTitle(title);
        doc.setContent(content);
    }

    @Override
    public void contribute() {
        doc.nextState(new UnderReviewState(doc));
    }

    @Override
    public void audit() {
        System.out.println("        非法操作：草稿状态不允许审核");
    }

    @Override
    public void revoke() {
        System.out.println("        非法操作：草稿状态不允许撤稿");
    }

    @Override
    public void remove() {
        System.out.println("        非法操作：草稿状态不允许下架");
    }
}
