## <center> 行为型 - 原型（Prototype）设计模式
---

# 一、原型模式
在介绍其他的设计模式时，为了方便理解，都会引入一些例子，一步一步的进入主题，这是为了方便理解。但原型模式不需要借助案例来帮助理解，因为他足够简单。现在，让我们直接进入主题。

## 1.1 意图

> **用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。**

结合着意图来看，原型模式的目的在于利用现有的原型创建新的对象，创建的方式为拷贝，所要创建对象的类型由原型实例指定（用原型实例指定创建对象的种类）。

> 原型模式译自英文'Prototype'一词，该词的中文释义有：原型、样机、样板的涵义。从其翻译的涵义中也可看出这个模式的核心是'复刻'，而'复刻'又依赖于现有的'模板'。

## 1.2 类图结构
<div align="center">
   <img src="/doc/resource/prototype/经典原型模式类图.jpg" width="50%"/>
</div>

原型模式的参与者有如下角色：

- **Prototype**：声明一个可以克隆自身的行为。可使用接口，也可使用抽象类，可根据实际情况而定。
- **ConcretePrototype**：实现克隆自身的操作。

# 二、实现拷贝
在《Design patterns- Elements of reusable object-oriented software》一书中指出，原型模式最困难的部分在于如何正确的实现拷贝操作。尤其是当对象结构包含循环引用时，这尤其棘手。

在 Java 中，提供了拷贝对象的支持，例如克隆。例如`Object#clone()`就默认实现了对象的浅拷贝，如果需要实现深拷贝，则需要重写`Object#clone()`方法。除此之外，想要克隆对象，类必须实现`java.lang.Cloneable`接口。

> 如果我们想要使用对象的`clone()`方法，就必须让该类实现`java.lang.Cloneable`接口，否则在调用`clone()`方法时，会得到一个`java.lang.CloneNotSupportedException`，但是在编译期并不会得到任何提示。

## 2.1 深拷贝与浅拷贝

上面我们已经提到了深拷贝和浅拷贝，如果到此为止你仍然不知道拷贝操作是什么，那么你应该先去查阅资料，因为本章的重点不是介绍拷贝操作，所以不在此阐述什么是拷贝操作。对于深拷贝和浅拷贝，他们之间有什么区别呢？简而言之：

1. 对于基本数据类型属性：直接复制其值，对于这些属性的值原对象和拷贝对象各自变化，互不影响；
1. 对于引用类型的属性：深拷贝是递归处理，直到遇到基本数据类型。浅拷贝则是直接复制地址引用，因为浅拷贝是复制的地址引用，所以当修改其中一个对象时，另一个对象也会跟着改变（因为他们指向的是同一个对象）。
1. 有一些特殊的对象，遵循基本数据类型的原则，比如包装类，借助于不可变机制，能保证原对象和拷贝对象各自隔离。

## 2.2 浅拷贝示例

`java.lang.Object#clone()`默认就是浅拷贝的实现，所以，我们可以直接借助于该方法实现，示例代码如下：
```java
public class ShallowClone implements Cloneable {
    private int property1;
    private String property2;
    public void setProperty1(int property1) {
        this.property1 = property1;
    }
    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

## 2.3 深拷贝示例
深拷贝实现方式有很多，这里介绍一种利用 jdk 序列化的方式进行深拷贝。原理是先将原对象进行序列化，再将序列化得到的结果反序列化成为对象，反序列化后对象与原对象互相隔离。
```java
public class DeepClone implements Cloneable, Serializable {

    public String val1 = "test";

    public Object val2;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepClone target = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            target = (DeepClone) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return target;
    }
}
```

利用序列化实现深拷贝较为简单，但同时也要求该对象必须实现`java.io.Serializable`接口，且其中的引用类型的属性也必须实现`java.io.Serializable`接口。

> 1. 也可以通过其他第三方工具实现序列化，比如 Jackson 、Gson 等。相比 jdk 实现的序列化，这些工具不要求对象必须实现`java.io.Serializable`接口，更加灵活。
> 1. 除此之外，还可以通过在`java.lang.Object#clone()`方法中逐个处理需要深拷贝的属性的方式实现深拷贝，但该方法较为繁琐，需要挨个属性进行考虑，当该属性的类中包含有其他引用类型属性时，还需要递归处理。

# 三、案例实现
## 3.1 案例概述

> 现有一家中间商，该中间商经营的业务范围包括有房屋买卖，房屋租售等。当中间商促成买方和卖方进行交易时，会从现有的模板库中调出来标准的房屋买卖合同进行复印，然后由双方进行签字，合同生效；房屋租售业务流程类似。

## 3.2 代码附录
根据这样的业务约定，我们可以套用原型模式进行设计，示例代码如下。

**（1）抽象的合同**
```java
public abstract class Contract implements Cloneable {

    private String type;                        // 合同类型
    private String buyer;                       // 买方
    private String mediator = "不靠谱中间商";    // 中间商
    private String seller;                      // 卖方

    public Contract(String type) {
        this.type = type;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    /**
     * 签署合同
     * @param productOwner 商品归属方
     * @param other 另一方
     */
    public abstract void signed (String productOwner, String other);

    @Override
    public Contract clone() {
        try {
            Contract clone = (Contract) super.clone();
            System.out.println("    复印了一份房屋" + this.type);
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
```
**（2）具体的合同**

**（2-1）买卖合同**
```java
public class SalesContract extends Contract {

    public SalesContract() {
        super("买卖合同");
    }

    @Override
    public void signed(String productOwner, String other) {
        this.setBuyer(other);
        this.setSeller(productOwner);
        System.out.println("    房屋买方为：" + other);
        System.out.println("    房屋出售方为：" + productOwner);
    }
}
```
**（2-1）租售合同**
```java
public class LeaseContract extends Contract {

    public LeaseContract() {
        super("租售合同");
    }

    @Override
    public void signed(String productOwner, String other) {
        this.setBuyer(other);
        this.setSeller(productOwner);
        System.out.println("    房屋租方为：" + other);
        System.out.println("    房屋出租方为：" + productOwner);
    }
}
```
**（3）客户端**

**（3-1）Client**
```java
public class Client {
    public static void main(String[] args) throws CloneNotSupportedException {
        // 实例化原型购房合同
        Contract sales = new SalesContract();
        // 实例化原型租售合同
        Contract lease = new LeaseContract();
        System.out.println("|==> 现有[张三]欲购置[李四]的房子-------------------------------------|");
        Contract clone4Sales = sales.clone();
        clone4Sales.signed("李四", "张三");
        System.out.println("|==> 现有[杰克]欲租赁[汤姆]的房子-------------------------------------|");
        Contract clone4Lease = lease.clone();
        clone4Lease.signed("汤姆", "杰克");
    }
}
```
**（3-2）运行结果**
```text
|==> 现有[张三]欲购置[李四]的房子-------------------------------------|
    复印了一份房屋买卖合同
    房屋买方为：张三
    房屋出售方为：李四
|==> 现有[杰克]欲租赁[汤姆]的房子-------------------------------------|
    复印了一份房屋租售合同
    房屋租方为：杰克
    房屋出租方为：汤姆
```

# 四、使用技巧

**（1）优先浅拷贝**

对于原型模式来说，并没有规定必须使用浅拷贝实现或者深拷贝实现。所以在实现时，应根据实际需求出发，灵活调整。在满足需要的前提下，可优先考虑浅拷贝实现，因为浅拷贝实现更加简单。

**（2）原型管理器**

在开发中，我们常常会给原型模式加一个原型管理器组件，该管理器组件内部维护所有原型的集合，负责初始化所有原型，并且在需要使用新对象的地方调用封装的方法直接获取拷贝对象。如下所示：
```java
public class ContractManager {

    private static final Map<String, Contract> CACHE = new HashMap<>();

    public static void loadCache() {
        Contract sales = new SalesContract();
        CACHE.put("sales", sales);
        Contract lease = new LeaseContract();
        CACHE.put("lease", lease);
    }

    public static Contract newInstance(String key){
        Contract contract = CACHE.get(key);
        if (contract == null) {
            throw new RuntimeException("不支持的合同类型");
        }
        return contract.clone();
    }
}
```

# 五、从源码中看原型模式

在 jdk 的源码中，很多类都实现了 Cloneable 接口，比如`java.util.ArrayList`。
```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable{
        
    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
    
}
```

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/creational/prototype)

