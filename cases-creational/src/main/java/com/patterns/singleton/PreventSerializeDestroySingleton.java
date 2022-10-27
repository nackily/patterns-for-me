package com.patterns.singleton;

import java.io.*;

/**
 * 阻止序列化破坏单例的案例
 *
 * @author coder
 * @date 2022-05-26 14:56:50
 * @since 1.0.0
 */
public class PreventSerializeDestroySingleton implements Serializable {

    private static final long serialVersionUID = 10000000000000L;

    private PreventSerializeDestroySingleton(){}

    private static PreventSerializeDestroySingleton INSTANCE = new PreventSerializeDestroySingleton();

    public static PreventSerializeDestroySingleton getInstance() {
        return INSTANCE;
    }


    private Object readResolve() {
        return INSTANCE;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 序列化 instance1 对象到磁盘
        PreventSerializeDestroySingleton instance1 = PreventSerializeDestroySingleton.getInstance();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("temp"));
        oos.writeObject(instance1);

        // 反序列化为对象 instance2
        File file = new File("temp");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        PreventSerializeDestroySingleton instance2 = (PreventSerializeDestroySingleton) ois.readObject();
        System.out.println("(instance1 == instance2) = " + (instance1 == instance2));
    }
}
