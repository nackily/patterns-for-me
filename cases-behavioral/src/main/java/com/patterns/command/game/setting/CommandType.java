package com.patterns.command.game.setting;

/**
 * CommandType
 *
 * @author coder
 * @date 2022-06-24 11:59:16
 * @since 1.0.0
 */
public enum CommandType {

    /**
     * 向上转向
     */
    FORWARD("向上", 1),

    /**
     * 向右转向
     */
    RIGHT("向右",2),

    /**
     * 向下转向
     */
    BACKWARD("向下",3),

    /**
     * 向左转向
     */
    LEFT("向左",4),

    /**
     * 暂停/继续
     */
    TOGGLE_STATE("暂停/继续", -1),

    /**
     * 回退一步
     */
    PREVIOUS("回退一步", -1),

    /**
     * 新对局
     */
    NEW_GAME("新对局", -1);

    private final String desc;
    private final int directCode;
    CommandType(String desc, int directCode) {
        this.desc = desc;
        this.directCode = directCode;
    }

    public int getDirectCode() {
        return directCode;
    }

    public String getDesc() {
        return desc;
    }
}
