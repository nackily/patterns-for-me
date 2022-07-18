package com.aoligei.behavioral.state;

/**
 * 状态
 *
 * @author coder
 * @date 2022-07-15 16:32:23
 * @since 1.0.0
 */
public interface State {

    /**
     * 获取状态名称
     * @return 状态名称
     */
    String getStateName();

    /**
     * 保存草稿
     * @param title 标题
     * @param content 正文
     */
    void saveDraft(String title, String content);

    /**
     * 投稿
     */
    void contribute();

    /**
     * 审核
     */
    void audit();

    /**
     * 撤稿
     */
    void revoke();

    /**
     * 下架
     */
    void remove();
}
