package com.aoligei.behavioral.iterator;


import com.aoligei.behavioral.iterator.aggregate.ArrayList0;
import com.aoligei.behavioral.iterator.aggregate.LinkedList0;

/**
 * Client
 *
 * @author coder
 * @date 2022-08-09 09:37:44
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> test for array list ----------------------------------------------------|");
        List0<String> array = new ArrayList0<>();
        for (int i = 0; i < 5; i++) {
            array.add("array element for [" + i + "]");
        }

        System.out.println("    顺序遍历器：");
        Client.doIterator(array.iterator());

        System.out.println("    随机遍历器：");
        Iterator0<String> randomIter = ((ArrayList0<String>) array).randomIterator();
        Client.doIterator(randomIter);

        System.out.println("|==> test for linked list ----------------------------------------------------|");
        List0<String> linked = new LinkedList0<>();
        for (int i = 0; i < 4; i++) {
            linked.add("linked element for [" + i + "]");
        }

        System.out.println("    正序遍历器：");
        Client.doIterator(linked.iterator());

        System.out.println("    倒序遍历器：");
        Iterator0<String> reversedIter = ((LinkedList0<String>) linked).reversedIterator();
        Client.doIterator(reversedIter);

    }


    private static void doIterator(Iterator0<String> iter) {
        while (iter.hasNext()) {
            String item = iter.next();
            System.out.println("        " + item);
        }
    }
}
