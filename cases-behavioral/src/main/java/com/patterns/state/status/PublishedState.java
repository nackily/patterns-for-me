package com.patterns.state.status;

import com.patterns.state.Document;
import com.patterns.state.State;

/**
 * 已发布状态
 *
 * @author coder
 * @date 2022-07-17 17:07:11
 * @since 1.0.0
 */
public class PublishedState implements State {

    private final Document doc;
    public PublishedState(Document doc) {
        this.doc = doc;
    }

    @Override
    public String getStateName() {
        return "已发布";
    }

    @Override
    public void saveDraft(String title, String content) {
        System.out.println("        非法操作：已发布状态不允许保存草稿");
    }

    @Override
    public void contribute() {
        System.out.println("        非法操作：已发布状态不允许投稿");
    }

    @Override
    public void audit() {
        System.out.println("        非法操作：已发布状态不需要审核");
    }

    @Override
    public void revoke() {
        doc.nextState(new DraftState(doc));
    }

    @Override
    public void remove() {
        doc.nextState(new RemovedState(doc));
    }
}
