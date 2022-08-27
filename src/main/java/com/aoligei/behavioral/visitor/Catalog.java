package com.aoligei.behavioral.visitor;

/**
 * 目录
 *
 * @author coder
 * @date 2022-08-26 11:05:46
 * @since 1.0.0
 */
public class Catalog extends AbstractResource{

    /**
     * 展示顺序
     */
    private final Integer order;
    public Catalog(String name, Integer order) {
        super(name);
        this.order = order;
    }

    @Override
    protected String accept(Visitor v) {
        return v.visitCatalog(this);
    }

    public Integer getOrder() {
        return order;
    }

}
