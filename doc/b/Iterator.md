## 把遍历做到极致的迭代器模式
---

迭代器模式很常用，我们在工作过程中经常要和它打交道，比如`增强for`、`forEach`和`iterator` 都和迭代器模式有着密不可分的联系。尽管在此之前你已经了解过迭代器模式，但你知道它是如何工作的吗？知道迭代器模式是用来解决什么问题的吗？本章我们就来讨论迭代器（Iterator）模式。和以往一样，我们仍然从一个例子逐步切入主题。

# 一、问题引入
有时，我们需要将一组对象放入容器中进行维护，我们应根据实际需求选择合适的容器类型，比如列表（List）、键值对（Map）、树（Tree）等等。不同的容器类型采用不一样的数据结构，每一种数据结构都有着自己独特的数据组织方式。同时，容器应该提供某种访问内部元素的方式，否则容器就仿佛黑洞一样没有任何意义。

对于 数组结构 和 树结构 来说，我们可以按照下图的方式遍历其内部维护的元素（左：数组，右：树）：
<div align="center">
   <img src="/doc/resource/iterator/数组和树遍历图.jpg" width="90%"/>
</div>

对于不同的数据来说，遍历的方式可能是不同的。比如上图中的 数组结构 和 树结构 ，遍历他们的方式可能如下代码片段所示。

```java
// =======================================遍历数组
public void foreachArray(Object[] array) {
	for (int i = 0; i < array.length; i++) {
		Object element = array[i];
		// ==>> 使用元素
	}
}

// =======================================遍历树
public void foreachTree(Tree rootNode) {
	// 使用当前节点
	while (rootNode.children.length > 0) {
		// 处理所有子节点
		Tree[] children = rootNode.children;
		for (int i = 0; i < children.length; i++) {
			Tree child = children[i];
			// 递归处理子节点
			foreachTree(child)
		}
	}
}
```

**（问题一）容器应该保证内部结构的封装性**

有一种方式能让外部遍历到所有的内部元素，那就是内部元素对外界完全透明。但这明显行不通，内部元素对外界透明意味着失去了对象的封装性，对象的安全性会大打折扣（例如 `java.util.ArrayList`，内部维护了一个对象数组，但却无法从外部拿到这个数组的引用）。我们需要一种遍历内部元素的方式，并且还要保证容器维护的内部元素不直接暴露出来。
<div align="center">
   <img src="/doc/resource/iterator/ArrayList内部结构.jpg" width="50%"/>
</div>

**（问题二）不同的数据结构遍历方式不一样**

我们仅仅只是展示了两种数据结构的遍历方式，想象一下，现在我们需要对 链表、键值对、图 等其他的数据结构进行遍历，可以复用上面的遍历方式吗？明显不能。那么，该如何做，才能使得我们对所有的容器都可以使用一致的遍历方式？

**（问题三）遍历方法支持扩展**

对于同一种数据结构来说，有时用户希望按照不同的顺序来遍历他们。比如对于列表（数组、链表等）来说，大多数时间我们都能按照先后顺序逐个访问其内部元素，但是有些特殊情况下，却希望顺序是颠倒过来的（先进入列表的元素后访问）；甚至极端情况下，还要求访问元素的顺序是随机的。需求永远是无止境的，我们无法预知所有可能的情况，我们能做的就是提供通用的遍历方式，并且支持扩展新的遍历方式。下图中列举了对于树形结构的两种遍历方式。
<div align="center">
   <img src="/doc/resource/iterator/树的不同遍历方式.jpg" width="85%"/>
</div>

# 二、解决方案
## 2.1 解放遍历过程

迭代器模式能完美的解决上面的所有问题，并且它还在其他方面展现出了令人欣喜的特性。迭代器模式鼓励我们将对容器的遍历过程从容器对象中解放出来，放入到一个迭代器对象中。比如，对于一个 ArrayList （基于数组的列表）类中元素的遍历动作，可借助于一个 ArrayListIterator（数组列表迭代器）进行。他们之间的关系如下图所示：

<div align="center">
   <img src="/doc/resource/iterator/ArrayList和Iterator关系.jpg" width="50%"/>
</div>

> 在上图中，ArrayListIterator 是一个针对于 ArrayList 的迭代器类，当用户希望遍历已有的 arrayList 对象时，需要实例化一个迭代器对象（arrayListIterator）。`currentPos`标记即将访问的下一个元素在数组中的索引，初始为 0（表示从数组中第一个元素开始遍历）。`hasNext()`方法返回是否还有下一个待访问的元素，当数组中的所有元素都已被访问过时，返回 false；否则，返回 true。`getNextElement()`方法用于返回下一个元素，该操作将让`currentPos`向前推进，指向下一个未访问的元素。周而复始，直至`currentPos`的值等于`ArrayList#size()`，此时就已完成了对所有元素的遍历。 

## 2.2 对所有迭代器的抽象
在前面我们说过，不同的容器对于其内部元素组织方式可能完全不一样，这意味着我们无法仅用一个迭代器对象来遍历所有的容器类型。换句话说，不同的数据结构应该有与之对应的迭代器对象。基于数组的列表使用迭代器类 ArrayListIterator，基于链表的列表使用迭代器类 LinkedListIterator，基于散列表+列表组织的键值对使用迭代器类 HashMapIterator ，多路树使用迭代器类 TreeIterator，等等。此时，我们应该对迭代器进行抽象，在对所有迭代器的抽象中（Iterator）定义统一的行为，例如`getNextElement()`、`hasNext()`。如下所示：

<div align="center">
   <img src="/doc/resource/iterator/迭代器类图演进过程（一）.jpg" width="90%"/>
</div>

> 事实上，容器（Container）的层级关系远比上图中描述的复杂，这个图仅仅只是演示数据结构的丰富性的一个例子而已。毕竟，这里我们讨论的重点是迭代器而非容器，所以不会花时间去解释容器应该有哪些。

## 2.3 如何创建迭代器
每一个迭代器对象都是为了访问容器对象，所以迭代器（Iterator）必须依赖于容器（Container），否则，迭代器将没有意义。但是，实际情况是迭代器对象只能依赖于一种特定类型的容器，比如说 ArrayListIterator 只能用来遍历 ArrayList 实例，而 LinkedListIterator 只能用来遍历 LinkedList 实例。那么，我们该如何描述每一个迭代器类只能适配一种特定的数据结构？
这个问题牵涉了两个对象层次，一个是容器对象层次，它用于维护内部元素；一个是迭代器对象层次，它用于遍历容器的内部元素。如果你已经看过了本系列的其他模式，那么你应该知道如何描述在两个维度上的特定组合，没错，就是[工厂方法（Factory Method）](/doc/creational/FactoryMethod.md)。 好了，让我们将创建迭代器的过程加入到类图结构中去。

<div align="center">
   <img src="/doc/resource/iterator/迭代器类图演进过程（二）.jpg" width="85%"/>
</div>

> 在如上的类图结构中，每一个容器类都需要实现`createIterator()`方法，在该方法中返回一个特定类型的迭代器。比如在`ArrayList#createIterator()`方法中，将返回类型为 ArrayListIterator 类型的迭代器（`return new ArrayListItrator(this)`）。每一个迭代器依赖于一个容器对象，以便迭代器在工作中时，能访问到所有的内部元素。每一个迭代器对象自己决定采用哪种顺序进行遍历，但都需提供`hasNext()`方法用以判断是否已遍历完成，提供`getNextElement()`方法获取下一个元素并且将遍历过程向后推进。

## 2.4 问题回顾
回顾在上面提及的三个问题，我们对迭代器模式如何解决这些问题进行一个总结。

**问题 ==>> 不同的数据结构遍历方式不一样**

引入迭代器模式后，我们可以对所有的数据类型采用同样的遍历方式。就像下图中的示例代码一样：
<div align="center">
  <img src="/doc/resource/iterator/迭代器示例代码.png" width="40%"/>
</div>

**问题 ==>> 遍历方法应该支持扩展**

迭代器模式并未限制每一种容器类只能使用一种迭代器，你可以为任意一种容器类型实现新的迭代器。例如，针对数组列表，我们希望有时候能支持随机遍历，我们只需要实现一个 RandomIterator，并且给 ArrayList 类增加一个`randomIterator()`方法，在该方法中实例化一个 RandomIterator 即可（`return new RandomIterator(this);`）。在接下来的案例中，我们为数组类型的列表实现了支持随机遍历的迭代器。

**问题 ==>> 保证内部结构的封装性**

我们可以借助于内部类的方式来保证容器内部结构的封装性，内部类可以访问容器类的所有成员。将迭代器声明为容器类的内部类，既实现了容器的内部结构对外不透明，又使得迭代器对象可以访问到容器的所有细节。在接下来的案例中，就采用了内部类声明的迭代器。

# 三、实现案例
## 3.1 案例定义
一切的数据结构都起源于数组和链表，那么我们就以他们来演示如何为容器对象构建迭代器。我们约定：
> - 对数组提供两个迭代器，一个是按照先后顺序逐个推进，另一个则随机从剩下未访问的元素中取出一个；
> - 对链表提供的迭代器支持按照从链表头到链表尾的顺序，或者相反的顺序推进，默认从头到尾的顺序；
> - 为了和 JDK 的实现区分开来，在所有类的命名后以 '0' 结尾（例如：ArrayList0）；
> - 引入泛型来表示两种容器的存储类型；

## 3.2 类图结构
我已经实现了该案例，为了在阅读代码时有一个清晰的结构脉络，这里先介绍一下该案例的类图结构。

<div align="center">
   <img src="/doc/resource/iterator/案例类图.png" width="80%"/>
</div>

案例的类图结构如上图所示，对于类图中的类的解释如下：

- **List0<E>**：列表接口，定义了常用的添加元素（`add(E):void`）、获取大小（`size():int`）、获取元素（`get(int):E`）的行为，除此之外，还定义了创建迭代器的行为（`iterator():Iterator0<E>`）;
- **ArrayList0、LinkedList0**：基于数组、链表实现的列表；
- **Node<E>**：链表中节点的封装；
- **Iterator<E>**：迭代器接口，定义了获取下一个元素（`next():E`）、是否还有下一个元素（`hasNext():boolean`）行为；
- **RandomIterator、DefaultArrayIterator、LinkedIterator**：分别为数组的随机迭代器、数组的顺序迭代器、链表的迭代器（构造器中的参数表示当前迭代器是否按照倒序推进）；

## 3.3 代码附录
<div align="center">
   <img src="/doc/resource/iterator/代码附录.png" width="95%"/>
</div>

代码层次及类说明如上所示，更多内容请参考[案例代码](/src/main/java/com/aoligei/behavioral/iterator)。客户端示例代码如下
```java
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
```
运行结果如下
```text
|==> test for array list ----------------------------------------------------|
    顺序遍历器：
        array element for [0]
        array element for [1]
        array element for [2]
        array element for [3]
        array element for [4]
    随机遍历器：
        array element for [2]
        array element for [0]
        array element for [1]
        array element for [4]
        array element for [3]
|==> test for linked list ----------------------------------------------------|
    正序遍历器：
        linked element for [0]
        linked element for [1]
        linked element for [2]
        linked element for [3]
    倒序遍历器：
        linked element for [3]
        linked element for [2]
        linked element for [1]
        linked element for [0]
```

# 四、迭代器模式
## 4.1 意图
> **提供一种方法顺序访问一个聚合对象中各个元素，而又不需暴露该对象的内部表示。**

迭代器模式为客户端提供了一种方式（迭代器）以访问聚合对象（容器对象）中的各个元素。同时，客户端在使用时对这个聚合对象的内部是如何组织的没有任何感知，巧的是客户端在大多数时间对一个容器内部是如何存储数据的并不关心。在用户看来，不管内部是采用了哪些数据结构，只要容器实现了迭代器的接口，那就可以按照统一的方法遍历其内部元素。

## 4.2 通用结构分析
典型迭代器模式的类图结构如下所示：

<div align="center">
   <img src="/doc/resource/iterator/经典迭代器模式类图.jpg" width="80%"/>
</div>

迭代器模式的参与者有如下：

- **Iterator**：迭代器抽象。定义访问和遍历元素的接口；
- **ConcreteIterator**：针对于特定聚合（ConcreteAggregate）对象的迭代器，依赖于一个聚合对象；
- **Aggregate**：聚合抽象。定义创建相应迭代器对象的接口，以及其他的行为；
- **ConcreteAggregate**：负责创建与之对应的迭代器实例；

在迭代器模式中，迭代器一般包含有如下几个行为：

- `first()`：初始化该迭代器，设置第一个访问的元素；
- `next()`：推进迭代器，设置下一个应该访问的元素；
- `isDone()`：获取迭代器已完成遍历的标志；
- `currentItem()`：获取当前的元素；

> 这些行为并不一定总是需要分开，比如我们可以将`first()`行为隐藏于迭代器对象的构造器中，因为对于迭代器来说，初始化的最好时机往往是迭代器实例化的时候。另外，`currentItem()`行为也总是隐藏在`next()`行为后面，这意味着在迭代器向前推进时，总是会返回一个当前的元素。例如在上面的案例中，我们仅向用户暴露了`next()`和`hasNext()`两个行为，其他的行为则被隐藏在内部实现中。

# 五、深入
## 5.1 特点
**（1）支持以不同的顺序遍历聚合对象**

尽管在本章的案例中我们已经实现了对于同一个数据结构提供多个迭代器，例如为数组提供了顺序迭代器和随机迭代器。但我仍不厌其烦的再次强调：迭代器模式支持我们对于一个聚合对象遍历的顺序多样化。例如对于树形结构而言，分为深度优先遍历、广度优先遍历，而深度优先又分为前序遍历、中序遍历及后序遍历等等。诸如此类的遍历方式，我们可以通过定义不同的迭代器来支持，在使用时，只需要用一个不同的迭代器实例代替原先的实例即可，我们甚至可以自己定义迭代器的子类以支持新的遍历方式。这就是面向接口编程的魅力。

**（2）迭代器简化了聚合的工作**

迭代器将如何遍历聚合对象的工作承接过来，这样就简化了聚合的接口，因为聚合接口不需要再定义如何遍历自身的相关行为。

**（3）嵌套遍历**

所谓嵌套遍历指的是在同一个聚合对象上的多重遍历，这得益于同一个聚合对象可以创建多个迭代器对象，并且这些迭代器各个维护自己的状态，相互之间没有影响。如下代码片段所示：
```java
public class NestedTest {
    public static void main(String[] args) {
        List0<String> array = new ArrayList0<>();
        array.add("tom");array.add("jack");
        array.add("tom");array.add("tony");
        array.add("tom");array.add("tony");
        // 统计每个名字出现的次数
        Map<String, Integer> group = new HashMap<>();
        Iterator0<String> iter = array.iterator();  // 外部迭代
        while (iter.hasNext()) {
            String name = iter.next();
            if (!group.containsKey(name)) {
                int count = 0;  // 计数器
                Iterator0<String> insideIter = array.iterator();  // 嵌套内的迭代
                while (insideIter.hasNext()) {
                    String item = insideIter.next();
                    if (name.equals(item)) {
                        count ++;
                    }
                }
                group.put(name, count);
            }
        }
        System.out.println(group);
    }
}
```
该代码片段演示了如何使用嵌套的迭代器来统计数组列表中的每个元素的出现次数。外部迭代遍历每个元素，当元素未被统计时，通过内部迭代器来累加当前元素的出现次数，否则跳过这个元素。该例子的完整代码已附录在文末。
## 5.2 适用场景
**（1）隐藏数据结构的复杂性**

当某个容器内部的数据结构相当复杂，并且希望对客户端隐藏内部的复杂性时，可使用迭代器模式来简化客户端的遍历过程。不管一个容器内部的数据结构如何复杂，对客户端来说，只需要通过几个简单的行为就可以完成遍历过程。这对于优化客户端的体验很有帮助。

> 例如，有一个这样的聚合对象，在内部元素较少时，我们采用链表的方式存储。而当内部元素较多时，我们则采用红黑树的方式存储，借助于红黑树的特质，使得在查找元素的效率上能得到提升。但是，对这个对象的遍历过程却相当痛苦，因为要面临着两种数据组织方式。此时，我们就可以用迭代器模式来封装迭代的过程，从而隐藏了内部细节的复杂性。

**（2）重用代码**

对于容器来说，描述迭代过程的代码往往体积非常庞大，并且较为复杂。当这些代码散落在程序业务逻辑中时，它会让业务逻辑模糊不清，降低了业务代码的可维护性。因此，将遍历代码封装到特定的迭代器类中可使程序代码更加简洁，逻辑更加清晰。

**（3）希望以同样的方法遍历不同的容器**

迭代器模式定义了一套遍历容器内部元素的规范，每个特定的迭代器在规范约束下各自实现，这样客户端能以同样的方式来遍历不同的容器。特别是，当一个容器的内部数据结构未知时，这一点显得尤为重要。有了迭代器模式托底，即便容器内部将来如何变化，多么复杂，客户端不用关心，也不会受到内部细节变化的影响。

# 六、使用技巧
## 6.1 谁控制迭代
在本章中，遍历向前推进的过程封装在如 next() 这样的行为中，而这个行为是由客户端在负责调用，一般将这样推进过程由客户端实现的迭代器称为“外部迭代器”。事实上，除此以外，还有一种“内部迭代器”，顾名思义，内部迭代器指的是迭代的推进过程由迭代器自身负责，内部迭代器的实现需要借助于类似函数对象这样的手段。考虑再三，我决定内部迭代器的更多细节不在此阐述，有兴趣的朋友可自行了解。如果有机会，我将单独出一篇介绍如何构建内部迭代器。内部迭代器的使用通常像如下代码所示：
```java
public static void main(String[] args) {
    java.util.List<String> list = fromSomeWhere();
    list.forEach(o -> System.out.println(o));		// 内部迭代
}
```
## 6.2 适当增加迭代器的健壮性
不管迭代器是如何工作的，在遍历一个容器的同时更改这个容器内部的元素是非常危险的。如果在遍历的同时，增加新的元素，可能将导致漏掉某个元素的访问；而移除已有的元素，可能导致对一个元素的多次访问。在 Java 中，用 `java.util.ConcurrentModifiedException` 来描述这个问题。不止一种办法能保证迭代器的健壮性，这里提供两个实现思路：

- 有一种做法是在迭代器初始化时，拷贝该容器对象，遍历仅针对拷贝容器的内部元素。尽管这种实现方式确实能保证遍历的健壮性，但付出的代价太大，选择这种方式应慎重再慎重；
- 还有一种做法是在容器对象和迭代器对象中间引入一种同步机制。比如说，迭代器在实例化时就将自己注册到容器对象中，当容器对象中发生插入和删除元素行为时，调整所有迭代器对象的内部状态，以保证遍历能正确进行下去。

## 6.3 权衡封装性和扩展性
有时候，我们不得不在封装性和扩展性之间做出选择。尽管迭代器对象和聚合对象之间有千丝万缕的联系，但他们毕竟属于两个对象，迭代器对象在某些时候必须要访问到聚合对象中的私有成员。这时，我们就得做出取舍：

- 选择封装性：我们可以将迭代器类作为聚合类的内部类，这样迭代器对象就可以轻松访问任意成员，而不需要对外部提供任何访问私有成员的方法；
- 选择扩展性：我们不得不将一些私有成员的访问暴露给外界，这也在一定程度上暴露了聚合对象的细节。

在本章的案例实现中，我们在封装性和扩展性之间选择了封装性。回顾我们在案例中基于链表实现的列表（LinkedList0），我们使用了一个内部类（LinkedIterator）来实现了链表的迭代器。如果某天需要在外部定义一个全新的迭代器类，有可能我们需要为列表增加一些方法（例如访问链表头的方法：`getHead():Node<E>`），以便我们能初始化迭代器对象。值得一提的是，JDK 实现的 ArrayList、LinkedList 等在这个问题上，作出了和我们同样的选择。

# 附录
[回到主页](/README.md)&emsp;[案例代码](/src/main/java/com/aoligei/behavioral/iterator)
