package com.patterns.visitor.resource;

import com.patterns.visitor.AbstractResource;
import com.patterns.visitor.Visitor;

/**
 * 菜单
 *
 * @author coder
 * @date 2022-08-26 10:52:38
 * @since 1.0.0
 */
public class Menu extends AbstractResource {

    /**
     * 展示顺序
     */
    private final Integer order;

    /**
     * 访问路由
     */
    private final String path;

    public Menu(String name, Integer order, String path) {
        super(name);
        this.order = order;
        this.path = path;
    }

    @Override
    public String accept(Visitor v) {
        return v.visitMenu(this);
    }

    public Integer getOrder() {
        return order;
    }

    public String getPath() {
        return path;
    }

}
