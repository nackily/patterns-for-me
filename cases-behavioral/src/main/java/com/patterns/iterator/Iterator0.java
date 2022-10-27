package com.patterns.iterator;

/**
 * 迭代器
 *
 * @author coder
 * @date 2022-08-09 10:36:31
 * @since 1.0.0
 */
public interface Iterator0<E> {

    /**
     * 是否还有后续元素
     * @return true:有
     */
    boolean hasNext();

    /**
     * 获取下一个元素
     * @return E
     */
    E next();
}
