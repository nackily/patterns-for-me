package com.patterns.iterator.aggregate;

import com.patterns.iterator.Iterator0;
import com.patterns.iterator.List0;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 基于数组的列表
 *
 * @author coder
 * @date 2022-08-09 10:43:48
 * @since 1.0.0
 */
public class ArrayList0<E> implements List0<E> {

    private Object[] elementArray;          // 元素数组
    private int size;                       // 存储元素的数量

    public ArrayList0() {
        // 默认容量为 10
        this(10);
    }
    public ArrayList0(int capacity) {
        elementArray = new Object[capacity];
    }

    @Override
    public void add(E item) {
        if (size >= elementArray.length) {
            // 扩容
            Object[] expandArray = new Object[elementArray.length * 2];
            System.arraycopy(expandArray, 0, expandArray, 0, expandArray.length);
            elementArray = expandArray;
        }
        elementArray[size] = item;
        size ++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index + 1 > size) {
            // 下标越界
            throw new ArrayIndexOutOfBoundsException();
        }
        return (E) elementArray[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator0<E> iterator() {
        return new DefaultArrayIterator();
    }

    /**
     * 获取一个随机迭代器
     * @return Iterator0<E>
     */
    public Iterator0<E> randomIterator() {
        return new RandomIterator();
    }


    /**
     * 基于数组列表的顺序迭代器
     *
     * @author coder
     * @date 2022-8-9 11:03:55
     * @since 1.0.0
     */
    private class DefaultArrayIterator implements Iterator0<E> {

        private int pos;            // 下一个元素的下标

        @Override
        public boolean hasNext() {
            return pos != size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (pos >= size) {
                throw new NoSuchElementException();
            }
            return (E) elementArray[pos++];
        }
    }

    /**
     * 基于数组列表的随机迭代器
     *
     * @author coder
     * @date 2022-8-9 11:03:55
     * @since 1.0.0
     */
    private class RandomIterator implements Iterator0<E> {

        private int[] tmp;                                  // 数组中元素的下标数组
        private final Random random = new Random();         // 随机

        public RandomIterator() {
            tmp = new int[size];
            for (int i = 0; i < size; i++) {
                tmp[i] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return tmp.length > 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            int randomVal = random.nextInt(tmp.length);
            int index = tmp[randomVal];
            E e = (E) elementArray[index];
            // 从数组中移除已访问的下标
            int[] shrinkTmp = new int[tmp.length - 1];
            System.arraycopy(tmp, 0, shrinkTmp, 0, randomVal);
            System.arraycopy(tmp, randomVal + 1, shrinkTmp, randomVal, tmp.length - randomVal - 1);
            tmp = shrinkTmp;
            return e;
        }
    }
}
