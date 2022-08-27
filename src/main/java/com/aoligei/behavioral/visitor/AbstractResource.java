package com.aoligei.behavioral.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源
 *
 * @author coder
 * @date 2022-08-26 10:50:51
 * @since 1.0.0
 */
public abstract class AbstractResource {

    /**
     * 资源名字
     */
    protected final String name;

    /**
     * 子节点
     */
    private final List<AbstractResource> children = new ArrayList<>();

    /**
     * 接收访问者
     * @param v 访问者
     * @return String
     */
    protected abstract String accept(Visitor v);

    public AbstractResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public AbstractResource addChild(AbstractResource resource) {
        children.add(resource);
        return this;
    }

    public List<AbstractResource> getChildren() {
        return children;
    }

}
