package com.patterns.iterator;

/**
 * 集合接口
 *
 * @author coder
 * @date 2022-08-09 10:34:44
 * @since 1.0.0
 */
public interface List0<E> {

    /**
     * 添加元素
     * @param item 元素
     */
    void add(E item);

    /**
     * 获取元素
     * @param index 下标
     * @return E
     */
    E get(int index);

    /**
     * 集合大小
     * @return int
     */
    int size();

    /**
     * 获取一个迭代器
     * @return Iterator0<E>
     */
    Iterator0<E> iterator();
}
