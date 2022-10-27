package com.patterns.state.status;

import com.patterns.state.Document;
import com.patterns.state.State;

/**
 * 已下架状态
 *
 * @author coder
 * @date 2022-07-17 17:08:07
 * @since 1.0.0
 */
public class RemovedState implements State {

    private final Document doc;
    public RemovedState(Document doc) {
        this.doc = doc;
    }

    @Override
    public String getStateName() {
        return "已下架";
    }

    @Override
    public void saveDraft(String title, String content) {
        System.out.println("        非法操作：已下架状态不允许保存草稿");
    }

    @Override
    public void contribute() {
        System.out.println("        非法操作：已下架状态不能再投稿");
    }

    @Override
    public void audit() {
        System.out.println("        非法操作：已下架状态不需要审核");
    }

    @Override
    public void revoke() {
        doc.nextState(new DraftState(doc));
    }

    @Override
    public void remove() {
        System.out.println("        非法操作：已下架状态不允许再次下架");
    }
}
