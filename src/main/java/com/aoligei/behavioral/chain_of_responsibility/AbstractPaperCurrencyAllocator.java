package com.aoligei.behavioral.chain_of_responsibility;

/**
 * 纸币分配器
 *
 * @author coder
 * @date 2022-07-19 11:58:28
 * @since 1.0.0
 */
public abstract class AbstractPaperCurrencyAllocator {

    /**
     * 下一个纸币分配器
     */
    protected AbstractPaperCurrencyAllocator nextAllocator;

    protected void setNextAllocator(AbstractPaperCurrencyAllocator next){
        this.nextAllocator = next;
    }

    /**
     * 分配纸币
     * @param request cur
     */
    public abstract void allocate(CurrencyRequest request);
}
