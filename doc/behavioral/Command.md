## <center> 行为型 - 解释器（Interpreter）设计模式
---

命令模式是一个复杂的行为模式，要理解它是不容易的，我花了很多的时间收集关于命令模式的资料，但结果却不尽人意。我发现在《Design patterns- Elements of reusable object-oriented software》一书中，对于命令模式的描述相当晦涩（事实上，大抵因为我水平不够）；而《Head First Design Patterns》一书中，描述比较通俗易懂，并且该书中关于智能家居的例子对于命令模式来说很是贴切，推荐去看一看。除了这两本书之外，我还发现一篇讲解很好的文章，该文章的总结部分很是精准。我已将该文档附录在本文结尾处，有兴趣的同学就去看看吧。

回归正题，我们已经介绍了几篇不错的参阅资料，但本篇的行文组织、阐述方式却和它们关系不大。希望通过这篇文章，能让你对命令模式不再感到陌生。

# 一、演进历程
我发现很多博文关于设计模式都是浅尝辄止，贴出来一大段概念和总结，但是并不对其加以任何说明，这让人很是头疼，甚至一整篇文章通读下来，竟也不知道这个模式究竟有何用处。今天，咱们就来聊一下命令模式的正确打开方式是什么。

## 1.1 不要在一个地方处理所有业务
自我入行面向对象的第一天，就被同事告知要摒弃面向过程的某些固有观念，比如不要在一个地方处理所有的业务。

> 举例来说，当输入用户名和密码后，我们希望实现点击“登录”按钮就进行用户登录的所有操作，那么这个逻辑的伪代码可能如下所示：

```java
public void button_click() {
    // 点击的按钮是“登录”
    if (button == "登录") {
        // 检查用户名和密码是否正确
        if (check) {
            // 检索用户是否存在
            if (exist) {
                // 密码比对
                if (matched){
                    // 记录登录日志...
                    // 查询用户权限...
                    // 跳转到首页...
                }
            }
        }
    }
}
```

## 1.2 职责分离必然带来分层
优秀的设计总是把关注点根据职责进行分离，让系统的各个部分都只关心自己的工作职责，当不得不需要和其他部分协作时，再和其他部分进行数据交互。

> 还是上面的例子，用户点击按钮这个行为做了太多的工作，我们将整个过程拆分成 GUI 层（为用户提供页面的展示）和后台层（ 处理登录细节）两个部分，这两个部分是各自独立的部分。整个过程就变成了 GUI 层处理用户点击按钮的事件，并向后台层发起一个登录请求。

职责的分离意味着一个完整的部分被拆分成具有更小职责范围的部分，但这也就意味着职责的分层。描述的更简单一些：原先在一块中的代码逻辑被拆分成了两个部分，当这两个部分进行协作时，必然有一方需要向另一方发起请求。发起请求的一方就是调用者，而响应请求的一方就是接收者。

这样做的好处除了职责分明之外，还有就是降低了耦合度（一个部分的变化不会影响到其他部分的代码）。除此之外，还有一个隐藏的好处：同层的对象或许可以相互替换。比如，用户的登录分为了管理员登录和普通用户的登录，他们同属于后台层，这样 GUI 层就可以选择向他们中的一个发起请求。

## 1.3 封装请求
尽管我们已经将职责拆分，各层之间的交互工作通过请求协调。但有些时候，做到这个程度还不够，我们还需要对这个模型进一步解耦。如果将各层之间的交互，通过命令的形式进行传递与组织，往往能使结构更加灵活，这里的命令指的是对请求的一系列封装。下面将通过一个案例来进一步说明为何需要封装请求，以及如何封装请求。

# 二、案例引入
作为开发者，对 IDE 的使用应该并不陌生，如下图所示：
<div align="center">
   <img src="/doc/resource/command/idea格式化代码.png" width="100%"/>
</div>

我们常常在编辑代码时，都需要对类的代码进行格式化，这很简单，通过菜单中`Code -> Reformat Code`按钮即可完成。让我们来对该应用的职责进行划分：

- 一个应用程序对象，这个对象就代表着当前的应用程序；
- 多个按钮对象，用户可通过点击按钮来实现相应的功能；
- 多个文档对象，文档对象中提供了对当前文档进行格式化的功能；

粗略的一想，当用户点击了 Reformat Code 按钮时，按钮对象调用文档对象的格式化功能不就可以实现对代码的格式化了吗。但仔细一想，我们就意识到这个说法是存在问题的：

- **问题一**：在 IDEA 中，文档对象是动态生成的，这意味按钮对象在运行期并不知道该调用哪一个文档对象来进行响应，只有应用程序知道；
- **问题二**：不同的文档对象可能提供了不同的接口来实现代码的格式化，比如在 XML 格式的文档对象中实现代码格式化的方法是 `public void format_xml()`，而在 JAVA 文档对象中是 `public void format_java()`；

那么，该如何解决这个问题呢？命令模式提出了以下的解决方法：

- 第一个问题：既然按钮对象无法确定该由哪个文档对象来响应点击事件，那么可以把请求本身封装成对象。应用程序在发起一个请求时，指定一个文档对象，并放入请求对象中；
- 第二个问题：不同的文档对象提供了不同的接口，最主要的原因是因为文档对象本身的差异化，那么，我们可以把请求对象进行抽象，不同的文档对象来搭配不同的请求对象，所有的请求对象屏蔽了文档对象的差异，且对外提供了统一的声明；

按照上面的分析，我们得出这样的职责划分和工作流程：

<div align="center">
   <img src="/doc/resource/command/案例职责划分.jpg" width="80%"/>
</div>

我们通过对请求过程进行解耦，对请求本身进行封装，得到了一个可以向未知的对象（文档对象由应用程序指定，按钮对象并不知道由谁来响应请求）发起一个可变的请求（按钮对象调用的是 Request 的 response() 方法，但按钮对象并不知道是哪一个具体的实现）的结构，而这就是命令模式的核心。

# 三、案例实现
这里已经为上面的分析结果编写了一个简单的示例，提供了示例中提到的类对象，除此之外，还提供了一个客户端，用以模拟用户对菜单的点击。

**（1） 请求对象**

**（1-1） 请求接口**
```java
public interface Command {
    /**
     * 响应命令
     */
    void response();
}
```
**（1-2） 打开文档请求**
```java
public class OpenDocumentCommand implements Command {
    private final Application application;
    private final String name;
    public OpenDocumentCommand(Application application, String name) {
        this.application = application;
        this.name = name;
    }

    @Override
    public void response() {
        if (null == name || "".equals(name)) {
            return;
        }
        Document document = null;
        if (name.endsWith(".xml")) {
            document = new XmlDocument(name);
        } else if (name.endsWith(".java")) {
            document = new JavaDocument(name);
        }

        application.open(document);
    }
}
```
**（1-3） 切换文档为活跃窗口请求**
```java
public class ToggleDocumentButton {
    private static final ToggleDocumentButton INSTANCE = new ToggleDocumentButton();

    public static ToggleDocumentButton getInstance() {
        return INSTANCE;
    }

    public void click(String name) {
        Document doc = Application.getInstance().getDocument(name);
        if (doc == null) {
            // 没有该文档
            return;
        }
        Application.getInstance().toggle(doc);
    }
}
```
**（1-4） 格式化 XML 文档请求**
```java
public class XmlFormatCommand implements Command{
    private final XmlDocument xmlDocument;
    public XmlFormatCommand(XmlDocument xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    @Override
    public void response() {
        xmlDocument.formatXml();
    }
}
```
**（1-5） 格式化 JAVA文档请求**
```java
public class JavaFormatCommand implements Command{
    private final JavaDocument javaDocument;
    public JavaFormatCommand(JavaDocument javaDocument) {
        this.javaDocument = javaDocument;
    }

    @Override
    public void response() {
        javaDocument.formatJava();
    }
}
```

**（2）文档对象**

**（2-1）抽象的文档**
```java
public abstract class Document {
    /**
     * 文档名
     */
    private final String name;

    public Document(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```
**（2-2）JAVA文档**
```java
public class JavaDocument extends Document {

    public JavaDocument(String name) {
        super(name);
    }

    public void formatJava() {
        System.out.println("    已格式化JAVA文档：" + this.getName());
    }
}
```
**（2-3）XML文档**
```java
public class XmlDocument extends Document{

    public XmlDocument(String name) {
        super(name);
    }

    public void formatXml() {
        System.out.println("    已格式化XML文档：" + this.getName());
    }
}
```

**（3）按钮对象**

**（3-1）打开文档按钮**
```java
public class OpenDocumentButton {

    private static final OpenDocumentButton INSTANCE = new OpenDocumentButton();

    public static OpenDocumentButton getInstance() {
        return INSTANCE;
    }

    public void click(String name) {
        new OpenDocumentCommand(Application.getInstance(), name).response();
    }
}
```
**（3-2）切换文档按钮**
```java
public class ToggleDocumentButton {

    private static final ToggleDocumentButton INSTANCE = new ToggleDocumentButton();

    public static ToggleDocumentButton getInstance() {
        return INSTANCE;
    }

    public void click(String name) {
        Document doc = Application.getInstance().getDocument(name);
        if (doc == null) {
            // 没有该文档
            return;
        }
        Application.getInstance().toggle(doc);
    }
}
```
**（3-3）格式化文档按钮**
```java
public class ReformatCodeButton {

    private static final ReformatCodeButton INSTANCE = new ReformatCodeButton();

    public static ReformatCodeButton getInstance() {
        return INSTANCE;
    }

    public void click() {
        Document doc = Application.getInstance().getActiveDoc();
        if (null == doc) {
            return;
        }
        Command command = null;
        if (doc.getName().endsWith(".xml")) {
            command = new XmlFormatCommand((XmlDocument) doc);
        } else if (doc.getName().endsWith(".java")) {
            command = new JavaFormatCommand((JavaDocument) doc);
        } else {
            // 其他格式
        }
        command.response();
    }
}
```

**（4）应用程序**
```java
public class Application {

    private static final Application INSTANCE = new Application();

    public static Application getInstance(){
        return INSTANCE;
    }

    /**
     * 已打开的文档列表
     */
    private final List<Document> openedDocs = new ArrayList<>();

    /**
     * 当前活跃文档名
     */
    private Document activeDoc = null;

    /**
     * 添加文档对象
     * @param doc 文档对象
     */
    public void open (Document doc) {
        // 当前文档已经打开
        if (openedDocs.stream().anyMatch(item -> item.getName().equals(doc.getName()))) {
            return;
        }
        openedDocs.add(doc);
        activeDoc = doc;
        System.out.println("    已打开文档：" + doc.getName());
    }

    /**
     * 关闭文档
     * @param doc 文档对象
     */
    public void close (Document doc) {
        openedDocs.remove(doc);
        activeDoc = openedDocs.size() > 0
                ? openedDocs.get(0)
                : null;
    }

    /**
     * 切换文档为活跃文档
     * @param doc 文档对象
     */
    public void toggle(Document doc) {
        if (openedDocs.contains(doc)) {
            activeDoc = doc;
            System.out.println("    已切换至文档：" + doc.getName());
        }
    }

    /**
     * 获取当前活跃文档的名称
     * @return 文档名
     */
    public Document getActiveDoc() {
        return activeDoc;
    }

    /**
     * 获取文档对象
     * @param name 文档名
     * @return 文档对象
     */
    public Document getDocument(String name) {
        return openedDocs.stream()
                .filter(item -> name.equals(item.getName()))
                .findFirst().orElse(null);
    }
}
```
**（5）客户端**

**（5-1）Client**
```java
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> Start--------------------------------------------------------|");
        // 打开java文档
        OpenDocumentButton.getInstance().click("someday.java");
        // 打开xml文档
        OpenDocumentButton.getInstance().click("july.xml");
        // 格式化当前文档
        ReformatCodeButton.getInstance().click();
        // 切换活跃文档
        ToggleDocumentButton.getInstance().click("someday.java");
        // 再次格式化当前文档
        ReformatCodeButton.getInstance().click();
    }
}
```
**（5-2）运行结果**
```text
|==> Start--------------------------------------------------------|
    已打开文档：someday.java
    已打开文档：july.xml
    已格式化XML文档：july.xml
    已切换至文档：someday.java
    已格式化JAVA文档：someday.java
```

# 四、命令模式
## 4.1 结构
命令模式的通用类图如下所示：
<div align="center">
   <img src="/doc/resource/command/经典命令模式类图.jpg" width="60%"/>
</div>

命令模式的参与者有如下：

- **Invoker**：调用者，安排某个对象来执行一个请求；
- **Command**：命令的抽象，声明执行操作的接口；
- **ConcreteCommand**：将当前命令与具体的接收者对象进行绑定，当前命令执行请求时，调用接收者的相应操作，以实现 execute() 动作；
- **Receiver**：接收者，知道如何实施与执行一个具体请求，任何类都可以作为接收者；

对于命令模式的类图，有以下几点需要注意：

- 命令模式的核心在于封装请求，所以调用者和接收者并不是命令模式强调的重点，命令模式的重点是类图中的深色背景部分。
- 对于 Invoker，并不限制于拥有 1 个命令对象，Invoker 可以拥有多个命令对象，当客户端执行某个操作时，可以一次性完成多个命令的执行；
- 对于 Receiver，可以是任意类，因为命令的接收者总是未知的，往往无法对其进行抽象（当然，如果这些命令的接收者有着固定的特征，也可对其进行抽象）；
- 命令对象存在两种极端：一种极端是命令没有接收者，在命令的 execute() 方法中就已经执行了请求；另一种极端是命令只负责调用接收者的 action() 方法，没有任何自己的逻辑。

## 4.2 适用场景
借助于对调用对象和接收对象的解耦，命令模式竟然拥有了更广的适用场景。

**（A）抽象出待执行的动作以参数化某对象**

命令模式将待执行的动作封装为命令对象，我们可以轻松的将命令作为方法的参数进行传递、将命令对象保存在其他对象中、在运行时切换对象拥有的命令以此来改变对象的行为。

**（B）延迟执行请求**

命令对象并不依赖于调用者，这意味着命令对象和调用者可以拥有完全独立的生命周期。调用者在调用命令对象的执行方法后，可能就已经被回收，但命令对象可以仍在执行请求。借助于这个特点，可以轻松实现延迟任务。

> 例如，系统希望在 30 分钟后进行停机维护，我们可以发起一个延迟命令并开始执行，该命令的请求执行方法中开始计时，到达 30 分钟时，触发停机维护的任务。

**（C）把请求放入队列中**

把请求放入队列中，我们可以得到如下好处：
+ 对请求进行排队：借助于队列的先进先出特征，我们可以实现对到达的请求进行排队执行；
+ 对请求回滚：记录下所有操作并按先后顺序对其进行排列，可轻松实现多个回滚操作；

**（D）支持撤销操作**

可在`execute()`调用之前，存储接收者的状态，在撤销操作时，用存储的状态来恢复接收者到调用`execute()`之前的状态。此时，Command 接口需要添加一个`unExecute()`操作，该操作用来取消上一个`execute()`调用产生的影响。当执行命令被存储在一个历史列表中时，我们就可以进行连续撤销的操作。

> 稍后，我们将实现一个可以连续撤销的案例——小猫摘星星游戏。当执行命令被记录在一个历史列表中时，我们可以很好的搭配备忘录模式（Memento），例如在稍后的游戏案例中`CommandHistoryContext`类就承担了备忘录模式中的 CareTaker 角色。

## 4.3 意图
来看一下命令模式的意图：
> **将一个请求封装为一个对象，从而使你可用不同的请求对客户进行参数化；对请求排队或记录请求日志，以及支持可撤消的操作。**

从之前分析的内容结合着意图来看，命令模式借助于封装请求，这样就实现了在运行时使用不同的请求对象来改变行为；命令模式还可以将命令放入队列，来实现对请求进行排队；在命令对象中还记录日志。在命令对象中执行请求之前，记录接收者的状态，这样就能实现对当前请求执行的撤销，使得接收者在撤销后可恢复到之前的状态。

## 4.4 使用技巧

**（1）命令的智能程度没有好坏之分**

在之前，我们已经提到过命令的两个极端。一个极端是所有的处理逻辑都在命令的 execute() 中实现，这看起来就好像命令非常智能，不需要借助于外部接口就能满足需求；而另一个极端是所有的处理逻辑都由命令绑定的接收者实现，命令只需要在 execute() 方法中调用接收者的 action() 方法。在这两种极端之间的命令，则会在 execute() 和 action() 方法中各处理一部分逻辑。命令的智能程度与命令的好坏没有关系，存在即是合理。
> 当需要定义与已有的类无关的命令、当没有合适的接收者、或当一个命令隐式地知道它的接收者时，可以使用前一种极端方式。比如，IDEA 中打开一个新的 Project 时，我们需要打开一个新的 Application，此时，并没有合适的接收者来处理这个请求。

**（2）根据需求选择合适的方案**

如果应用只需要支持一次撤销操作，那么我们可以有两种方式实现：

1. 存储最近一次的命令以及接收者的状态
2. 通过执行反向的操作来实现，比如说接收者是一盏灯，命令是开灯/关灯，那么对开灯命令来说，它的撤销命令是关灯命令；此时，只需在开灯命令的 unExecute() 方法中调用接收者的关灯命令即可，反之亦然。

如果应用需要支持多次撤销操作，那么我们只有按顺序存储每一个命令和接收者的状态。这时，这个命令就起着和原型模式（Prototype）同样的作用。

**（3）如果需要，可以将多个命令装配成一个复合命令**

复合命令本身是一个独立的命令，和普通命令不同的是，复合命令的`execute()`方法中，调用了其他（多个）命令的`execute()`方法。

> 例如，在带有格式的编辑器中，我们经常能使用居中这个功能。我们知道，居中意味着水平居中和垂直居中。此时，我们可以将居中的命令设置为复合命令，他维护了另外两个命令——水平居中和垂直居中。当我们调用居中命令的`execute()`方法时，先调用水平居中命令的实现，再调用垂直居中的实现。

复合命令可以采用[组合模式（Composite）](Composite.md)，这样就能实现更为复杂的命令。该复合命令可以像一棵树一样被展开，内部包含了其他的命令，其他命令内部还有命令，这样，我们就能实现一键开启多个功能。

# 五、扩展案例
在本文一开篇时，就提到：命令模式是一个比较复杂的模式。这里为了帮助更好的理解命令模式，再贴一个实现案例。该实现案例是一个小游戏，游戏名为小猫摘星星，游戏规则设定如下：

> a. 小猫朝着当前的方向移动，如果沿途遇到小星星，则摘取该星星；
> b. 星星被摘取后，将在游戏屏幕内随机生成一颗新的星星；
> c. 小猫碰到边界，游戏结束，否则，游戏一直进行下去；
> d. 游戏对局中提供对移动步数、星星得分的统计；
> e. 游戏对局中提供暂停/继续功能、提供转向功能（只允许水平向垂直转向，或者垂直向水平转向）、提供新开对局功能；
> f. 当游戏处于暂停或结束时，可使用后退一步功能恢复当前游戏到最近一次转向之前的状态；

该游戏的代码参见附录。游戏的运行效果如下所示：
<div align="center">
   <img src="/doc/resource/command/gaming.gif" width="80%"/>
</div>

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/behavioral/command/ide)    [小猫摘星星代码](/src/main/java/com/aoligei/behavioral/command/game)

推荐阅读文章：[Command Design Pattern in Java](https://www.journaldev.com/1624/command-design-pattern)
