## <center> 行为型 - 单例（Singleton）设计模式
---

# 一、单例模式
单例模式应该是所有设计模式中结构最简单的一个了，同时它也是面试中被考的最多的设计模式。

## 1.1 意义
对于一些类来说，控制其有且只有一个实例是必要的。比如一个桌面端的应用程序，尽管它可以打开多个窗口，但他仅有一个窗口管理器。再比如Java虚拟机，应用实例需要与应用当前的运行环境进行交互时可使用`java.lang.Runtime`来转达（例如获取处理器的数量可以使用`java.lang.Runtime#availableProcessors()`方法），但对于一个应用实例来说，交互器一个就够用了。

## 1.2 意图
> **保证一个类仅有一个实例，并提供一个访问它的全局访问点。**

这个意图比较容易理解并且表述也相当准确，此处不再过多赘述。

## 1.3 类图结构
<div align="center">
   <img src="/doc/resource/singleton/经典单例模式类图.jpg" width="30%"/>
</div>

单例模式的通用类图结构如下所示，在单例模式中，提供了一个自身的实例对象（`Singleton#instance`）以及一个可获取该实例对象的行为（`Singleton#getInstance():Singleton`）。并且，任何时候，单例类不应该将构造方法暴露给外部，不允许在该类自身以外的地方创建新的对象。

单例模式足够简单，我们可以将更多目光放在一个更值得关注的点上面：单例的线程安全性。简而言之，对于创建实例（`Singleton#instance`）的过程来说，如果我们无法保证有且只能有一个线程创建实例对象，那就表明该单例模式并非线程安全的。下面，我们列举了几种常见的线程安全的单例模式以及简单的分析。

# 二、实现案例
单例模式在 Java 中可以有多种写法，但常见的线程安全的写法只有 4 种，这 4 种也都很常用。

## 2.1 饿汉式
```java
public class Singleton01 {
    
    private Singleton01(){}
    private static final Singleton01 INSTANCE = new Singleton01();
    
    public static Singleton01 getInstance() {
        return INSTANCE;
    }
}
```

> “饿汉式”所描述的意思是 INSTANCE 在类加载的时候就已经被初始化，这个 INSTANCE 可能还没有被使用过。

这种单例的线程安全是由 JVM 的类加载机制保证的，开发者不用额外处理线程安全的问题。“饿汉式”写法的优点是相当简单，缺点是浪费内存空间（实例被创建出来了，但是并没有被使用，这期间一直占用着内存）。

## 2.2 双重检查锁
```java
public class Singleton02 {

    private Singleton02(){}

    private volatile static Singleton02 INSTANCE;

    public static Singleton02 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton02.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton02();
                }
            }
        }
        return INSTANCE;
    }
}
```

> “双检锁”（DCL - Double Check Lock）在平时的写法中用得很多，这种写法的思想是“懒汉式”——等需要使用的时候再去初始化实例。

**（要点一）为什么需要两个 check**

使用了两个“非空检查”，并且使用了 synchronized 关键字，所以叫双重检查锁。第一个检查和第二个检查均不能省略。

- 第一个 check 是为了减少不必要的锁，当 INSTANCE 不为空时，表示当前已经存在一个实例，可以直接使用，不用上锁（如果不加上这个 check，当多个线程同时需要获取 INSTANCE 时，会出现资源竞争，而且线程越多，资源竞争越大）；
- 第二个 check 是为了解决锁竞争带来的问题，如果没有第二个 check，则可能会被创建多个实例。

针对第二个 check 的必要性说明，有一种可能性的工作情况如下所示：

1. 假设现在有 Thread-A 已经处于 synchronized 代码块内，正在创建实例对象；
1. 此时，Thread-B 进入第一个 check，发现 INSTANCE 为空，则开始等待  Thread-A 释放锁；
1. Thread-A 创建完实例，退出 synchronized 代码块，释放锁；
1. Thread-B 获取到锁，继续执行，进入第二个 check ，发现 INSTANCE 已经被初始化好了，则直接退出 synchronized  代码块。

**（要点二）volatile的作用**

> *问题：INSTANCE 被 volatile 修饰，那么 volatile 在这里到底起了什么作用呢？*

这个问题在面试中经常出现。总的来说，volatile 的作用是禁止指令重排序，进而避免极端情况下使用拿到的 INSTANCE 时报错。 我们知道，对象的创建大致可以分为三个阶段：

1. 给对象分配内存，
1. 调用构造器方法，执行对象的初始化，
1. 将对象的引用赋值给变量。

在三个阶段中， 2 和 3 都需要依赖于 1，所以 1 是最先执行的；但是 2 和 3 不需要必然的先后关系，所以虚拟机在执行时，可能会出现 132，也可能出现 123。132 这种情况就是指令重排序，而这里的 volatile 关键字就可以让其不会出现 132 这种情况。如下所示，出现 132 的大致情况是这样的：

- time1：thread-1 开始给对象分配内存
- time2：thread-1 将当前对象的引用赋值给变量“INSTANCE”
- time3：thread-2 进入代码块，判断变量“INSTANCE”是否为空
- time4：thread-2 发现“INSTANCE”不为空，开始使用“INSTANCE”
- time5：thread-1 初始化对象完成

在上述描述的过程中，time4 时刻，thread-2 拿到的 “INSTANCE”所指向的对象是一个没有初始化完成的对象，此时就会发生异常。

## 2.3 枚举
```java
public enum Singleton03 {
    INSTANCE;
}
```
枚举类是天然的线程安全，并且在任何情况下都是单例。那么枚举类是如何做到线程安全的呢？ 我们把编译后的 Singleton03.class 文件反编译之后，得到 Singleton03 类反编译后的源码是这样的：
```java
public final class Singleton03 extends Enum {

    public static Singleton03[] values() {
        return (Singleton03[])$VALUES.clone();
    }

    public static Singleton03 valueOf(String s) {
        return (Singleton03)Enum.valueOf(com/aoligei/creational/singleton/Singleton03, s);
    }

    private Singleton03(String s, int i) {
        super(s, i);
    }

    public static final Singleton03 INSTANCE;
    private static final Singleton03 $VALUES[];

    static {
        INSTANCE = new Singleton03("INSTANCE", 0);
        $VALUES = (new Singleton03[] {
            INSTANCE
        });
    }
}
```
从上面的代码不难看出，所谓的 INSTANCE 枚举量不过也是被 static final 修饰的类常量，并且在类加载时就会被初始化（静态代码块中初始化）**。**由此看来，枚举实现的单例和“饿汉式”基本思路是一致的，用同样的方式实现了单个实例和保证线程安全。

## 2.4 静态内部类
```java
public class Singleton04 {

    private Singleton04(){}

    public static Singleton04 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton04 INSTANCE = new Singleton04();
    }
}
```

静态内部类实际上是把创建实例的唯一性和线程安全性都交给了 JVM，并且很好的实现了懒加载（也属于“懒汉式”的一种）。

- **保证单个实例：**虚拟机加载类的机制，保证了在只有一个类加载器的前提下，同一个类只会被加载一次，也就保证了只会有一个 INSTANCE；
- **保证线程安全：**虚拟机会保证一个类的 <clinit>() 方法在多线程环境中被正确地加锁、同步，如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的 <clinit>() 方法，其他线程都需要阻塞等待，直到活动线程执行 <clinit>() 方法完毕；
- **保证的懒加载：**只有在第一次调用 getInstance() 方法时，虚拟机才会加载 SingletonHolder 类，也就是说 INSTANCE 只会在第一次调用 getInstance() 方法时被初始化。

# 三、扩展

> 我们现在已经编写好了上述 4 种单例写法中的任意一种，在不改变类的前提下，还有没有办法破坏实例唯一性这个原则？

有，反射、克隆、序列化都可能会破坏实例的唯一性。我们应该如何防止唯一性被破坏呢？

## 3.1 防止反射破坏
反射破坏单例模式的核心是强制调用类的构造器**，**使得本身已经有实例的单例类再创建新的实例。防止这种方式的破坏就得在私有的构造方法中判断当前类是否已经创建过实例，如果没有，允许创建一个，并且将该实例指向 INSTANCE ，如果已经创建过，则返回当前 INSTANCE 或者抛出异常。例如，以“饿汉式”为例，防止反射破坏唯一性的代码如下：
```java
public class PreventReflexDestroySingleton {

    private PreventReflexDestroySingleton(){
        if (INSTANCE != null) {
            throw new RuntimeException("已经有实例了");
        }
    }

    private static PreventReflexDestroySingleton INSTANCE = new PreventReflexDestroySingleton();

    public static PreventReflexDestroySingleton getInstance() {
        return INSTANCE;
    }
}
```

## 3.2 防止克隆破坏
实际上，克隆和单例模式并不经常出现，当我们希望一个类的示例永远只有一个的时候，是断然不会实现 Cloneable 接口的。这本身就很矛盾，唯一一个合理的解释可能是：我希望我的类在大多数时间都是只有一个实例的，但是在某些时刻我希望它有一个口子能提供给我一个创建新实例的机会。 还是回到正题上来，如何防止克隆对单例的破坏？

- **方法1：**不实现 Cloneable 接口；
- **方法2：**如果实现了 Cloneable 接口，则重写 clone 方法，在这里返回已有的实例。

```java
public class PreventCloneDestroySingleton implements Cloneable {

    private PreventCloneDestroySingleton(){}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // return super.clone();
        return INSTANCE;
    }

    private static PreventCloneDestroySingleton INSTANCE = new PreventCloneDestroySingleton();

    public static PreventCloneDestroySingleton getInstance() {
        return INSTANCE;
    }
}
```

## 3.3 防止序列化破坏
序列化对单例的破坏，性质和克隆有些类似，需要类实现 Serializable 接口，相对于克隆，序列化在很多时候可能就显得难以避免了。比如，现在要求一个需要序列化的类有且只有一个实例。该如何实现呢？ 只需要添加一个方法名为“readResolve”的方法，在该方法中返回唯一的实例即可。
```java
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

```
为什么添加一个“readResolve”的方法就能保证有且仅有一个实例呢？简单来说，就是当我们反序列化时，在 readObject() 方法会去检查这个类有没有一个名字为“readResolve”的方法，如果有，则以这个方法的返回作为反序列化后得到的对象；如果没有这样的方法，则重新创建一个对象。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/creational/singleton)
