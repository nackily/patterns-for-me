package com.aoligei.behavioral.iterator.aggregate;

import com.aoligei.behavioral.iterator.Iterator0;
import com.aoligei.behavioral.iterator.List0;

/**
 * 基于链表的列表
 *
 * @author coder
 * @date 2022-08-09 14:09:04
 * @since 1.0.0
 */
public class LinkedList0<E> implements List0<E> {

    private Node<E> head;                   // 链表头
    private Node<E> tail;                   // 链表尾
    private int size;                       // 存储元素数量

    @Override
    public void add(E item) {
        final Node<E> oldTail = tail;
        Node<E> newNode = new Node<>(item, oldTail, null);
        this.tail = newNode;
        if (head == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size ++;
    }

    @Override
    public E get(int index) {
        Node<E> item;
        if (index < (size >> 1)) {
            // index 在前半部分，从前向后找
            item = head;
            for (int i = 0; i < index; i++) {
                item = item.next;
            }
        } else {
            // index 在后半部分，从后向前找
            item = tail;
            for (int i = size - 1; i > index; i--) {
                item = tail.prev;
            }
        }
        return item.element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator0<E> iterator() {
        return new LinkedIterator(false);
    }

    /**
     * 获取一个倒序的迭代器
     * @return Iterator0<E>
     */
    public Iterator0<E> reversedIterator() {
        return new LinkedIterator(true);
    }

    /**
     * 节点
     *
     * @author coder
     * @date 2022-8-9 14:32:58
     * @since 1.0.0
     */
    private static class Node<E> {
        private E element;                  // 当前节点内的元素
        private Node<E> prev;               // 前一个节点
        private Node<E> next;               // 后一个接口
        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * 链表顺序迭代器
     *
     * @author coder
     * @date 2022-8-9 14:32:58
     * @since 1.0.0
     */
    private class LinkedIterator implements Iterator0<E> {

        private final boolean reversed;             // 是否倒序遍历
        private Node<E> nextNode;                   // 下一个节点

        public LinkedIterator(boolean reversed) {
            this.reversed = reversed;
            if (reversed) {
                nextNode = tail;
            } else {
                nextNode = head;
            }
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            E element = nextNode.element;
            if (reversed) {
                nextNode = nextNode.prev;
            } else {
                nextNode = nextNode.next;
            }
            return element;
        }
    }
}
