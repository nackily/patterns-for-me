package com.aoligei.behavioral.state;

import com.aoligei.behavioral.state.status.DraftState;

import java.text.MessageFormat;

/**
 * 文稿
 *
 * @author coder
 * @date 2022-07-17 16:20:58
 * @since 1.0.0
 */
public class Document implements State {
    private String title;                       // 文稿标题
    private String content;                     // 文稿正文
    private State currentState;                 // 当前状态

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
        // 新建文稿
        currentState = new DraftState(this);
    }

    /**
     * 切换状态
     * @param nextState 下个状态
     */
    public void nextState(State nextState) {
        System.out.println(MessageFormat.format("    状态转换：【{0}】 -> 【{1}】",
                currentState.getStateName(), nextState.getStateName()));
        currentState = nextState;
    }

    @Override
    public String getStateName() {
        return currentState.getStateName();
    }

    @Override
    public void saveDraft(String title, String content) {
        currentState.saveDraft(title, content);
    }

    @Override
    public void contribute() {
        currentState.contribute();
    }

    @Override
    public void audit() {
        currentState.audit();
    }

    @Override
    public void revoke() {
        currentState.revoke();
    }

    @Override
    public void remove() {
        currentState.remove();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
