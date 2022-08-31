## <center> 行为型 - 组合（Composite）设计模式
---

<div align="center">
   <img src="/doc/resource/composite/树.png" width="60%"/>
</div>

如果一个应用的核心模型是树形结构，那么我们就能用组合模式来表示它，组合模式就是为树形结构量身定制的。接下来，我们通过一个例子来展开组合模式的学习。

# 一、问题引入

> 对于文件系统来说，主要分为两类：文件和文件夹。他们主要的区别是文件夹下可以存放多个文件及其他的文件夹，文件系统是典型的树状结构。对于一个杀毒软件来说，当我们进行病毒查杀时，他会根据不同的文件类型提供不同的杀毒方式（这里我们只考虑两种文件格式，他们分别是图片格式和文本格式）。此时，我们需要对一个层级很深的文件夹进行病毒查杀，该如何设计呢？

很明显，这是一个递归的问题，我们需要对所有的文件夹进行遍历，然后逐个求解。在这个过程中，至少应该有两类对象，他们分别是文件（File）和文件夹（Folder）。

我们需要对他们一个一个的访问，如果是文件则进行病毒查杀；但如果是文件夹，则需要遍历文件夹的下一层然后再查杀病毒。这是一个不断递归的过程，直到所有的文件都已经完成病毒查杀。这个过程的伪代码大致如下所示：

```java
public void destroyVirus(Folder f) {
    List<File> files = f.getFiles();
    if (files != null && files.size() > 0) {
        for (File f : files) {
            if (f.getType = image) {
                System.out.println("针对图片杀毒");
            } else if (f.getType == text) {
                System.out.println("针对文本杀毒");
            }
        }
    }
    List<Folder> folders = f.getFolders();
    for (Folder f : folders) {
        // 递归
        destroyVirus(f);
    }
}
```

上面的代码看起来没啥太大的问题，至少当文件类型较少时，这个方法看起来并不复杂。但是当文件种类一旦多起来，这个条件分支将变得臃肿；并且，每新增一个文件类型的支持，我们就需要在杀毒过程中增加对应的条件分支进行处理。

# 二、解决方案

一般出现条件分支的地方就有可能出现多个行为，按照最少职责原则，我们应该针对于不同的文件类型，提供不同的对象予以表示；并且应对这些对象的行为进行统一，让其表现出相同的行为。简单来说，就是分别对图片格式和文本格式提供类`ImageFile`和`TextFile`，并且让他们都继承自抽象的`File`，并根据自身特点实现杀毒的行为。按照这个原则，得到以下的类图：
<div align="center">
   <img src="/doc/resource/composite/案例推导方案类图.jpg" width="70%"/>
</div>

将目光聚集杀毒的方法上，我们发现：不论是文件夹还是文件，都提供了同样的方法签名，那么我们就可以针对于他们抽象出更加统一的行为。如下类图所示：
<div align="center">
   <img src="/doc/resource/composite/案例最终方案类图.jpg" width="70%"/>
</div>

如上，我们将文件和文件夹的杀毒行为进行统一，对于文件夹的杀毒就是对下层的所有文件进行杀毒。文件夹维护了一个下层列表，对于列表中的元素，可以是文件夹类型，也可以是文件类型。另外提供了给列表添加元素的方法，对应着在文件夹下新增文件（或文件夹）的功能。对于新增一种文件类型来说，也只是新增一个`AbstractFile`的实现类，并且在需要的地方创建该实现类的实例即可。

对于使用的客户端来说，对于任意一个文件（或者文件夹）进行杀毒，只需要调用`destroyVirus()`方法即可，不用在意它是文件还是文件夹。并且对于多个文件，不管他们是多么复杂的树形结构，我们只需要调用最顶层的文件夹的`destroyVirus()`方法即可。这些体验都得益于委托的机制：文件夹不用执行杀毒任务，它仅仅是把杀毒的任务委托给所有具体的文件执行。

OK，到这里我们已经实现了组合模式，这个类图这个就是组合模式所要表达的内容。我们将在下面对组合模式进行深入的探讨，在此之前，我们先看一下这个案例实现的代码。

# 三、案例实现
对于上面分析的例子，实现代码如下：

**（1）抽象的文件系统**
```java
public abstract class AbstractFile {

    /**
     * 文件名
     */
    protected String name;
    public AbstractFile(String name) {
        this.name = name;
    }

    /**
     * 杀毒
     */
    protected abstract void destroyVirus();
}
```
**（2）文件夹**
```java
public class Folder extends AbstractFile {

    /**
     * 子节点
     */
    private final List<AbstractFile> children = new ArrayList<>();

    public Folder(String name) {
        super(name);
    }

    /**
     * 添加子节点
     * @param item 子节点
     */
    public void add(AbstractFile item) {
        this.children.add(item);
    }

    @Override
    protected void destroyVirus() {
        System.out.println(MessageFormat.format("   ==>开始处理文件夹[{0}]...", super.name));
        children.forEach(AbstractFile::destroyVirus);
    }
}

```
**（3）各种类型的文件**

**（3-1）图片文件**
```java
public class ImageFile extends AbstractFile {

    public ImageFile(String name) {
        super(name);
    }

    @Override
    protected void destroyVirus() {
        System.out.println(MessageFormat.format("   图片文件[{0}]开始杀毒", super.name));
    }
}

```
**（3-2）文本文件**
```java
public class TextFile extends AbstractFile {

    public TextFile(String name) {
        super(name);
    }

    @Override
    protected void destroyVirus() {
        System.out.println(MessageFormat.format("   文本文件[{0}]开始杀毒", super.name));
    }
}

```
**（4）客户端**

**（4-1）Client**
```java
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> 文件夹内容如下： --------------------------------------------------------|");
        // 构造如下的文件目录层次
        System.out.println("    =文档资料");
        System.out.println("        =杂项");
        System.out.println("            梦一样的早晨.txt");
        System.out.println("            身份证正反面.png");
        System.out.println("        =参考资料汇总");
        System.out.println("            =01-招投标书");
        System.out.println("                XXX项目标书模板.doc");
        System.out.println("            =02-设计方案");
        System.out.println("            参考链接汇总.txt");
        System.out.println("        备忘事项.jpg");

        AbstractFile root = new Folder("文档资料");
        Folder node_1_1 = new Folder("杂项");
        node_1_1.add(new TextFile("梦一样的早晨.txt"));
        node_1_1.add(new ImageFile("身份证正反面.png"));
        Folder node_1_2 = new Folder("参考资料汇总");
        Folder node_2_1 = new Folder("01-招投标书");
        node_2_1.add(new TextFile("XXX项目标书模板.doc"));
        node_1_2.add(node_2_1);
        node_1_2.add(new Folder("02-设计方案"));
        node_1_2.add(new TextFile("参考链接汇总.txt"));

        ((Folder) root).add(node_1_1);
        ((Folder) root).add(node_1_2);
        ((Folder) root).add(new ImageFile("备忘事项.jpg"));

        System.out.println("|==> 开始杀毒： -------------------------------------------------------------|");
        root.destroyVirus();
    }
}
```
**（4-2）运行结果**
```text
|==> 文件夹内容如下： --------------------------------------------------------|
    =文档资料
        =杂项
            梦一样的早晨.txt
            身份证正反面.png
        =参考资料汇总
            =01-招投标书
                XXX项目标书模板.doc
            =02-设计方案
            参考链接汇总.txt
        备忘事项.jpg
|==> 开始杀毒： -------------------------------------------------------------|
   ==>开始处理文件夹[文档资料]...
   ==>开始处理文件夹[杂项]...
   文本文件[梦一样的早晨.txt]开始杀毒
   图片文件[身份证正反面.png]开始杀毒
   ==>开始处理文件夹[参考资料汇总]...
   ==>开始处理文件夹[01-招投标书]...
   文本文件[XXX项目标书模板.doc]开始杀毒
   ==>开始处理文件夹[02-设计方案]...
   文本文件[参考链接汇总.txt]开始杀毒
   图片文件[备忘事项.jpg]开始杀毒
```

# 四、组合模式
## 4.1 通用结构
参考上面的例子，我们对组合模式的参与角色进行更加宽泛的定义：

- **Component**：组件，声明通用的行为，`AbstractFile`承担了组件的接口；
- **Composite**：组合组件，存储子组件、定义有子组件时的行为（注册子组件，移除子组件等），将自身（组合组件）的行为（`operation()`）委托给其他组件执行，`Folder`承担了组合组件的角色；
- **Leaf**：叶子节点组件，没有子组件，各种类型的文件承担了叶子节点组件；
- **Client**：调用客户端，可无差别调用组件中定义的行为；

回顾上面的例子，我们将文件夹和文件的行为进行了统一，使得客户端可以执行“杀毒”这一行为却又不关心请求的是文件对象还是文件夹对象。但这势必会带来其他的问题：文件和文件夹毕竟是不同的对象，他们在某些行为上（杀毒）可以统一，但在另一些行为上却无法表现一致（文件夹下可以存放其他文件，但文件不可以）。正是因为这一点，也使得组合模式通常会在两种方案中进行选择，这两种方案分别是更安全的组合以及更透明的组合。

**更安全的组合模式**

所谓的更安全的组合模式，是指顶层接口（与客户端直接交互的接口）中只包含共有的行为，对于差异化的行为不应出现在顶层接口中，通用类图如下。
<div align="center">
   <img src="/doc/resource/composite/更安全的组合模式.jpg" width="60%"/>
</div>

**更透明的组合模式**

更透明的组合模式是指，顶层接口中不仅包含共有的行为，也包含有差异化的行为（比如给当前组件注册子组件、移除子组件等），通用类图如下。
<div align="center">
   <img src="/doc/resource/composite/更透明的组合模式.jpg" width="60%"/>
</div>

**两种实现的对比**

安全性、透明性是相对的，在组合模式中不能让他们同时满足。那么，更安全的组合模式安全在哪？更透明的组合模式又透明在哪？

- **安全性**：安全性是针对于叶子组件来说的，叶子组件是不能拥有子组件的，所以`add()`对于叶子组件来说是不合适的行为。那么如果客户端不小心调用了`Leaf#add()`的方法，就意味着用户调用了一个不应该发生的行为，所以对比来看，第一种组合更加安全；
- **透明性**：透明性则是针对于组合组件来说的，组合节点存储了子组件，并且提供了相应的维护方法。所以如果顶层接口不提供维护的方法，那么对于叶子组件和组合组件在使用时就会不一致（例如，给当前组件添加子组件，需要先将当前组件的类型转为组合组件类型：`((Composite) component).add(child)`）。对比来看，第二种组合更加透明；

## 4.2 意图
> **将对象组合成树形结构以表示“部分 - 整体”的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。**

- **将对象组合成树形结构**：在一开始我们就说，组合模式是为树形结构量身定制的；
- **表示“部分 - 整体”的层次结构**：一个组合组件包含了多个叶子组件和其他的组合组件，这个组件即是整棵树的一部分（是其他组合组件的子组件），也是以这个组件为根节点的一颗完整的数；
- **使得用户对单个对象和组合对象的使用具有一致性**：借助一层又一层委托的机制，使得对于叶子组件的操作和对于组合组件的操作可以以同样的方式调用。

# 五、使用技巧
## 5.1 透明与安全的选择
如上所述，安全性和透明性在组合模式这个问题中是不可调和的两个矛盾点。一方面，当我们选择更安全的组合实现，在维护子节点列表时，不得不对叶子组件和组合组件进行区别对待，这对于用户来说通用性不够好；另一方面，如果选择更透明的组合实现，我们又不得不给叶子节点附加了一些看起来没有意义的行为。你可以在使用时，可根据需求灵活选择。

我个人觉得，相对于安全性，我们应该把更多的关注度放在通用性上面，毕竟这是组合模式的核心就是为客户提供一致的调用。如果为了更加安全，而让客户在维护子组件时不得不进行类型强转等操作，我觉得是不划算的，因为强转就违背了使用的一致性（当然，这里讨论的只是维护子组件的一致性）。所以我建议：透明性优先于安全性。

> 当我们选择更透明的组合实现时，我们可以在叶子节点没有的行为中定义缺省的实现（空方法）。从另一个角度来讲，我们可以将叶子组件看作是子组件为空的组合组件。我们也可以在叶子节点的不该有的行为方法中抛出异常，以此来警示客户端的错误调用。

## 5.2 子组件的排序
如果应用程序需要对树形结构按照某种特定的顺序进行遍历，那么我们应该仔细的设计对子组件的访问和管理，以便让子组件按照顺序排列。这一点可以参考[迭代器模式（Iterator）](https://www.yuque.com/coderran/pd/zgt7bc)。

# 六、从源码中看组合模式
如果你曾在项目中使用过 Spring 框架提供的缓存，那么你或许见过`CacheManager`接口。如果没见过，那`CaffeineCacheManager`、`RedisCacheManager`呢？这些都没听说过？OK，没关系，我会对他们进行一个大概的介绍。

**【1】**、 `CacheManager`是一个缓存管理器接口，定义了两个行为：①-通过缓存名称获取对应的 value；②-获取所有缓存名称的列表。部分代码截取如下
```java
public interface CacheManager {

	/**
	 * Get the cache associated with the given name.
	 * <p>Note that the cache may be lazily created at runtime if the
	 * native provider supports it.
	 * @param name the cache identifier (must not be {@code null})
	 * @return the associated cache, or {@code null} if such a cache
	 * does not exist or could be not created
	 */
	@Nullable
	Cache getCache(String name);

	/**
	 * Get a collection of the cache names known by this manager.
	 * @return the names of all caches known by the cache manager
	 */
	Collection<String> getCacheNames();

}
```
**【2】**、`CaffeineCacheManager`与`RedisCacheManager`都是`CacheManager`的实现类，前者是一个提供本地缓存实现的管理器，后者则是分布式缓存实现的管理器。除此之外，还有其他方式实现的管理器，此处不再扩展；

**【3】**、在`CacheManager`的所有实现中，有一个`CompositeCacheManager`。它较为特殊，其内部维护了一个`CacheManager`的集合，提供了初始化集合的方法，并且负责将所有向自身的请求转发到了其他`CacheManager`上。部分代码截取如下
```java
public class CompositeCacheManager implements CacheManager, InitializingBean {
    
    // 子组件
    private final List<CacheManager> cacheManagers = new ArrayList<>();
    
    // 子组件管理
    public CompositeCacheManager(CacheManager... cacheManagers) {
		setCacheManagers(Arrays.asList(cacheManagers));
	}
    public void setCacheManagers(Collection<CacheManager> cacheManagers) {
		this.cacheManagers.addAll(cacheManagers);
	}
    
    @Override
	@Nullable
	public Cache getCache(String name) {
		for (CacheManager cacheManager : this.cacheManagers) {
            // 委托给其他管理器
			Cache cache = cacheManager.getCache(name);
			if (cache != null) {
                // 返回第一个匹配成功的缓存
				return cache;
			}
		}
		return null;
	}
    
    @Override
	public Collection<String> getCacheNames() {
		Set<String> names = new LinkedHashSet<>();
		for (CacheManager manager : this.cacheManagers) {
            // 委托给其他管理器
			names.addAll(manager.getCacheNames());
		}
		return Collections.unmodifiableSet(names);
	}
}
```
可以看出，`CompositeCacheManager`使用了组合模式，并且采用的是更安全的组合模式。使用`CompositeCacheManager`我们可以让我们的项目同时支持多个缓存管理器，既可以用本地缓存，也可以用分布式缓存，还可以实现其他的缓存支持。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/structural/composite)

