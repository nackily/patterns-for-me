<center> 行为型 - 访问者（Visitor）设计模式
---
如果对所有设计模式进行复杂度排名，访问者模式总是能位居前列；但对所有设计模式的普适度进行排名，那么访问者模式或许会垫底。相对于其他模式来说，访问者模式的类图结构并不复杂，它的复杂体现在很难真正理解它的意图。今天，我们就来探秘访问者模式，揭开访问者模式不为人知的真实面目。为方便理解，我们仍将通过一个例子开始。
# 一、问题引入
<div align="center">
   <img src="/doc/resource/visitor/ruoyi.png"/>
</div>
对于一个 WEB 系统的系统管理模块来说，应该有多种资源类型，比如：

- **目录**：接收用户的点击事件，展开或折叠下层的菜单；
- **菜单**：接收用户的点击事件，为用户打开一个新的页签，并初始化一些数据；
- **按钮**：接收用户的点击事件，处理特定的业务；
> 首先各种资源并不是平级关系，他们往往呈现出树状结构，如上图所示，目录下挂载了多个菜单，同时菜单下有多个功能按钮，所以抽象的资源应该包含有子节点（children）。对于目录和菜单来说，我们通常希望他们能按照我们设定的上下顺序进行展示，所以我们给目录和菜单增加了一个排序码（order）。对于菜单来说，我们总是希望用户点击时打开一个新的页签，所以可能需要一个页面路由（path）；而按钮总是附带一个图标（icon）。

我们为这些资源类型建立了对象模型，如下图所示：
<div align="center">
   <img src="/doc/resource/visitor/resource-class-diagram.jpg"/>
</div>
> 某天，产品组的成员在商议过后通知开发组的组长：增加一个导出资源的功能，导出的格式为 XML。恰巧，这个需求最终交给了你。经过几分钟的考虑后，你决定动手。你在 AbstractResource 中增加了定义了抽象的导出行为`protected abstract String export()`，并且各个具体的资源也都实现了这一行为。这个功能一经上线后，得到了用户不错的反响，于是乎，产品组的同事召开了一个专题会议，会议决定：支持几种其他格式的导出功能，例如 JSON、CSV 等等格式。

你仍然打算如法炮制之前的做法，但很快你开始意识到事情可能没那么简单。经过几轮迭代后，原先功能单一的类在不知不觉间已经变得相当臃肿，并且未来仍然会持续膨胀。这些类在最初的设计中仅仅是用来表示一个具体的资源，但现在它甚至承载了导出文档这样对资源表示无任何意义的职责，这看起来似乎不太合理。
并且每增加一个新的导出功能，势必会修改现有的类，这让测试的同事也犯了难，他们不得不因为某一次的修改而对原先既有的功能进行回归测试。
# 二、解决方案
经过一番痛苦的摸索无果后，你最终决定向技术小组长请求协助。技术组长在听完你的陈述后，想了一会问道：“知道访问者模式吗？”你摇了摇头。技术组长没有办法，承诺你他会帮你构建一个示例。半个小时后，技术组长来到了你的工位，拉取了最新的代码，并打开了该模块的类图结构，如下所示。
<div align="center">
   <img src="/doc/resource/visitor/case-class-diagram.png"/>
</div>
在组长耐心的给你进行了 10 分钟的讲解后，你终于弄明白了上面的类图设计。原来是将导出文档的行为抽象到了另外一个类层级（XMLExportVisitor），并且对这个类层次也进行了抽象（Visitor）。这样一来，对于资源的表示（AbstractResource）和对于资源的访问（Visitor）就独立到两个维度，将原来散布在各个资源类中的导出行为统一实现在访问器中，使得资源的表示更加纯粹。
并且当需要导出新的文档格式时，只需要添加一个对应格式的导出访问器即可，不用改动现有的类，自然也就不会影响到既有的功能。
# 三、案例实现
## 3.1 类图注述
对于类图中各个部分的说明如下：

- **AbstractResource**：抽象的资源，定义了该资源的名称、子节点列表的维护。并且提供了接收一个访问者的接口【`accept(Visitor):String`】，返回一个格式化后的字符串；
- **Menu、Catalog、Button**：分别为菜单、目录、按钮资源。实现了接收访问者的行为，在该方法中调用访问者的具体方法【例如`Button#accept()`方法中：`return v.visitButton(this)`】，借此让访问者访问自己；
- **Visitor**：抽象的访问者，定义了针对于各种资源类型的访问行为【`visitMenu(Menu):String`、`visitCatalog(Catalog):String`、`visitButton(Button):String`】，以此实现同一个访问者针对于不同的资源类型有不一样的访问方式；
- **XmlExportVisitor**：xml 格式导出访问器，实现了针对于这种资源的访问。除此之外，还提供了对子节点的访问【`visitChildren(AbstractResource):String`】、以及统一的导出文档行为【`export(AbstractResource):String`】。
## 3.2 代码附录
**（1）抽象的资源**
```java
public abstract class AbstractResource {

    /**
     * 资源名字
     */
    protected final String name;

    /**
     * 子节点
     */
    private final List<AbstractResource> children = new ArrayList<>();

    /**
     * 接收访问者
     * @param v 访问者
     * @return String
     */
    protected abstract String accept(Visitor v);

    public AbstractResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public AbstractResource addChild(AbstractResource resource) {
        children.add(resource);
        return this;
    }

    public List<AbstractResource> getChildren() {
        return children;
    }

}
```
**（2）具体资源**
```java
public class Catalog extends AbstractResource{

    /**
     * 展示顺序
     */
    private final Integer order;
    public Catalog(String name, Integer order) {
        super(name);
        this.order = order;
    }

    @Override
    protected String accept(Visitor v) {
        return v.visitCatalog(this);
    }

    public Integer getOrder() {
        return order;
    }

}
```
```java
public class Menu extends AbstractResource {

    /**
     * 展示顺序
     */
    private final Integer order;

    /**
     * 访问路由
     */
    private final String path;

    public Menu(String name, Integer order, String path) {
        super(name);
        this.order = order;
        this.path = path;
    }

    @Override
    public String accept(Visitor v) {
        return v.visitMenu(this);
    }

    public Integer getOrder() {
        return order;
    }

    public String getPath() {
        return path;
    }

}
```
```java
public class Button extends AbstractResource{

    /**
     * 图标名
     */
    private final String icon;
    public Button(String name, String icon) {
        super(name);
        this.icon = icon;
    }

    @Override
    protected String accept(Visitor v) {
        return v.visitButton(this);
    }

    public String getIcon() {
        return icon;
    }
}
```
**（3）抽象的访问者**
```java
public interface Visitor {

    /**
     * 访问目录
     * @param c 目录
     * @return String
     */
    String visitCatalog(Catalog c);

    /**
     * 访问菜单
     * @param m 菜单
     * @return String
     */
    String visitMenu(Menu m);

    /**
     * 访问按钮
     * @param b 按钮
     * @return String
     */
    String visitButton(Button b);

}
```
**（4）具体访问者**
```java
public class XmlExportVisitor implements Visitor {

    public String export(AbstractResource resource) {
        String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        return header + resource.accept(this);
    }

    @Override
    public String visitCatalog(Catalog c) {
        return "<catalog>\n" +
                "    <name>" + c.getName() + "</name>\n" +
                "    <order>" + c.getOrder() + "</order>\n" +
                visitChildren(c) +
                "</catalog>";
    }

    @Override
    public String visitMenu(Menu m) {
        return "<menu>\n" +
                "    <name>" + m.getName() + "</name>\n" +
                "    <order>" + m.getOrder() + "</order>\n" +
                "    <path>" + m.getPath() + "</path>\n" +
                visitChildren(m) +
                "</menu>";
    }

    @Override
    public String visitButton(Button b) {
        return "<button>\n" +
                "    <name>" + b.getName() + "</name>\n" +
                "    <icon>" + b.getIcon() + "</icon>\n" +
                visitChildren(b) +
                "</button>";
    }

    /**
     * 访问子节点
     * @param resource 资源
     * @return String
     */
    private String visitChildren(AbstractResource resource) {
        StringBuilder sb = new StringBuilder();
        List<AbstractResource> children = resource.getChildren();
        for (AbstractResource o : children) {
            String childXml = o.accept(this);
            childXml = "    " + childXml.replace("\n", "\n    ") + "\n";
            sb.append(childXml);
        }
        return sb.toString();
    }
    
}
```
**（5）客户端**
```java
public class Client {
    public static void main(String[] args) {
        XmlExportVisitor visitor = new XmlExportVisitor();
        AbstractResource catalog = new Catalog("系统管理", 1)
                .addChild(new Menu("用户管理", 1, "/sys/user/index")
                        .addChild(new Button("新增用户", "add"))
                        .addChild(new Button("删除用户", "delete")))
                .addChild(new Menu("角色管理", 2, "/sys/role/index")
                        .addChild(new Button("角色列表", "query"))
                        .addChild(new Button("授权", "auth")));
        String catalogXml = visitor.export(catalog);
        System.out.println(catalogXml);
    }
}
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<catalog>
    <name>系统管理</name>
    <order>1</order>
    <menu>
        <name>用户管理</name>
        <order>1</order>
        <path>/sys/user/index</path>
        <button>
            <name>新增用户</name>
            <icon>add</icon>
        </button>
        <button>
            <name>删除用户</name>
            <icon>delete</icon>
        </button>
    </menu>
    <menu>
        <name>角色管理</name>
        <order>2</order>
        <path>/sys/role/index</path>
        <button>
            <name>角色列表</name>
            <icon>query</icon>
        </button>
        <button>
            <name>授权</name>
            <icon>auth</icon>
        </button>
    </menu>
</catalog>
```
# 四、访问者模式
## 4.1 意图
> **表示一个作用于某对象结构中的各元素的操作，它使你可以在不改变各元素的类的前提下定义作用于这些元素的新操作。**

访问者模式强调的是封装对象结构中的各种类型的元素的操作，正如上文中的案例一样，对于同样 xml 格式的导出，菜单和按钮将导出包含有不同的属性的标签。并且访问者模式使得我们无需更改任何元素的类，就能定义全新的操作方式，它得益于各个元素对象能将对于自身的访问委托给不同的访问者对象。这也使得对于各个元素的操作方式，可以独立于元素对象本身而变化（对于菜单类型的元素对象，可以有导出 xml 格式的操作，也可以有导出 json 格式的操作，甚至可以有其他任何格式的操作，但这些操作完全独立于菜单对象本身）。对于对一个元素对象的操作，可以根据需求进行选择，或者扩展新的操作。
## 4.2 通用类图
典型访问者模式的类图结构如下所示：
<div align="center">
   <img src="/doc/resource/visitor/class-diagram.jpg"/>
</div>
访问者模式的参与者有如下：

- **Element**：元素。定义一个 accept 行为，该行为接收一个访问者对象，表示一个访问者的来访。例如上文案例中的 AbstractResource 类；
- **ConcreteElement**：具体的元素。实现 accept 行为，调用访问者的操作方法，并将自身作为参数传递给访问者对象。例如上文案例中的菜单、按钮等类；
- **ObjectStructure**：表示一个对象结构。该对象结构通常为一系列元素对象的复合，比如元素列表，元素树等；
- **Visitor**：访问者。为每一种类型的元素对象声明一个访问方法；
- **ConcreteVisitor**：具体的访问者。实现对每一种类型元素的具体访问行为；
## 4.3 协作
大多数情况下，元素并不是单独存在的，他们往往互相组合构成了复合结构对象（ObjectStructure）。正如上文案例中一样，资源呈现出树形结构，目录下可以挂载多个菜单，菜单下同样可以有多个按钮。但在案例中，我们并没有提供一个单独的类表示这种复合的关系，但本质是一样的，如果我们将资源的子节点这一表示抽离到单独的类中表示，那这个类就等同于此处的 ObjectStructure。不管复合对象内部的元素组成如何复杂，所有对于复合对象的访问都将经历对复合结构的遍历，并最终细化到对于各个元素对象的访问。
下图演示了一个对象结构 anObjectStructure（内部包含了两个元素：element_a、element_b），和一个访问者（visitor）之间的协作关系：
<div align="center">
   <img src="/doc/resource/visitor/timing-diagram.svg"/>
</div>
## 5.1 特点
**（1）易于增加新的操作**
毋庸置疑，访问者模式使得我们更容易的新增一种对于元素的操作行为，并且这不会影响到现有的行为。我们只需要定义一个新的访问者对象，并且实现访问者的行为即可。
**（2）职责分明**
访问者模式使得我们将元素的表示和对于元素的操作分离开来，元素对象中仅保留了与其自身表示相关的职责。这对于元素表示的维护、对于元素对象的操作的扩展都更加清晰。
**（3）破坏了封装**
职责的分离有时候会带来新的问题，比如对于封装性的破坏。有时候，为了保护元素对象状态的安全性，我们特意将其声明为私有，并且不给其他任何对象暴露这个状态的引用。但是，如果访问者需要访问这个对象状态，那么你就会处于一个两难的境地。此时，你应该权衡相对于扩展性和安全性来说，哪一个更为重要。
**（4）增加新的 ConcreteElement 很困难**
访问者模式的特点决定了它必须为每一种元素提供一个与之对应的访问行为，这一特点也直接导致了增加新的元素将极其麻烦。每添加一个新的 ConcreteElement 都需要在 Visitor 中定义一个新的访问行为，同时也将在所有的访问者类中实现这一行为。
## 5.2 使用技巧
**（1）只有元素固定的场景下才适合访问者模式**
在上面，我们提到访问者模式对于增加新的 ConcreteElement 相当困难，所以当你想把访问者模式引入到手头的项目结构中去时，务必要对这一点进行评估。并且正是由于这一特点的限制，才使得他在项目中的使用率极低。因为在很多真实案例中，这个缺点是致命的。但是，如果你笃定在某个场景中元素较为固定，那么你往往能从访问者模式中获益。为方便理解，此处列举两个真实的案例来说明什么场景下元素相对固定：

- **文件系统的访问**：对于文件系统而言，元素无非就分为两种：文件和文件夹，并且我们笃信以后出现第三种类型元素的可能性极小。所以，对于文件系统的访问适合引入访问者模式；
- **抽象语法树的访问**：对于编译器来说，第一步就是将代码源文件解析成为抽象语法树。然后，编译器需要对抽象语法树中的各个节点进行大量的操作（例如，语义分析、变量检查、代码优化、格式美化等等）。使用访问者模式可对语法树中的节点对象解耦，使得各个节点中不必充斥着与该节点本身无关的行为。对于一种语法来说，元素是相对固定的，同样适合访问者模式；

**（2）如何遍历对象结构**
一个访问者必须访问对象结构中的每一个元素，所以必须遍历对象结构中的元素集合。遍历的行为可以放到对象结构中，也可以放在访问者中，还可以放在一个单独的迭代器（Iterator）中进行。三种方式没有优劣之分，可根据实际情况选择。
# 六、从源码中看访问者模式
在 Java7 的 nio 包下有一个 Files 工具类，使用 Files#walkFileTree(start:Path, visitor:FileVisitor) 可以实现对于某个目录中的文件树进行访问。该方法的第一个参数表示需要访问的起始目录，第二个参数是指定一个文件访问器对象。而文件访问器定义如下所示：
<div align="center">
   <img src="/doc/resource/visitor/FileVisitor.png"/>
</div>
如上图所示，在文件访问器中，定义了访问子目录和文件的行为，并且提供了在结束一个子目录访问时的行为方法，针对于访问文件失败的情况也提供了访问失败的行为方法。所有的这些方法都返回一个文件访问结果对象，FileVisitResult 是一个枚举类，它的定义如下所示：

- `FileVisitResult._CONTINUE_`==>> 继续
- `FileVisitResult._TERMINATE_`==>> 终止
- `FileVisitResult._SKIP_SIBLINGS_`==>> 继续但不访问文件（或目录）的同级
- `FileVisitResult._SKIP_SUBTREE_`==>> 继续但不访问子目录

这样，就可以通过返回一个枚举实例来控制接下来的遍历行为，比如你可以返回一个`FileVisitResult._CONTINUE_`来让文件访问器接着工作，也可以返回一个`FileVisitResult._TERMINATE_`来让文件访问器停止访问文件系统。
下面的程序片段演示了如何使用文件访问器来实现对某个目录的访问，并且在文件树中查找某个文件：
```java
public class Main {
    public static void main(String[] args) throws IOException {
        // 需要查找的文件
        String fileName = "what.txt";
        // 起始目录
        Path start = Paths.get("D:", "opt");

        // 定义文件访问器
        FileVisitor<Path> visitor = new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println(MessageFormat.format("==>> 开始访问目录[{0}]", dir));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(MessageFormat.format("==>> 正在访问文件[{0}]", file));
                if (file.endsWith(fileName)) {
                    System.out.println(MessageFormat.format("<<==>> 已找到文件[{0}]", fileName));
                    // 结束查找
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // 打印异常
                exc.printStackTrace();
                // 继续
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println(MessageFormat.format("<<== 结束访问目录[{0}]", dir));
                if (exc != null) {
                    exc.printStackTrace();
                }
                // 继续
                return FileVisitResult.CONTINUE;
            }
        };

        // 访问文件系统
        Files.walkFileTree(start, visitor);
    }
}




// 程序运行结果：
// ==>> 开始访问目录[D:\opt]
// ==>> 开始访问目录[D:\opt\fist_dir]
// <<== 结束访问目录[D:\opt\fist_dir]
// ==>> 开始访问目录[D:\opt\second_dir]
// ==>> 正在访问文件[D:\opt\second_dir\what.txt]
// <<==>> 已找到文件[what.txt]
```


# 附录
项目主页：[https://gitee.com/ry_always/DesignPatterns](/README.md)
案例中的截图参考自： [Ruoyi](http://demo.ruoyi.vip/)
案例代码：[.../visitor](/src/main/java/com/aoligei/behavioral/visitor)
