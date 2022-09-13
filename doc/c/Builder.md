## <center> 创建型 - 建造者（Builder）设计模式
---

我们知道，《Design Patterns - Elements of Reusable Object-Oriented Software》一书中共罗列了 23 种设计模式，但事实是，极少有人会熟悉所有的模式。注意这里我指的是熟悉，那意味着你能在手头没有任何参考资料时将一些模式恰当地引入到现有的结构中来，因为你对这些模式已经了然于胸，你清楚的知道他们的特点、使用场景和一些容易掉入的陷阱。甚至你曾经在你的项目中多次使用过，此时你已经积累了一定的使用经验，并在不自知的时候就已经总结了这些模式的使用技巧。

有一个良性循环是，如果一些设计模式很常用，那么我们就会有更多的机会使用到他们，反过来，正因为我们多次的使用，所以我们对他们会更加熟悉。从第一次依葫芦画瓢时的捉襟见肘，到后来不经意间的信手拈来，我们暗自庆幸岁月赠与的实力沉淀。慢慢地你开始发现，在有些模式的使用过程中，我们对面向对象的理解也开始深刻起来了，也会在突然间就对从前很困惑的一段话有了自己的理解。这得益于你在多次挫败和自我怀疑后，对于问题的思考和自信心的重塑，换句话说，想的问题越多，提升就越快。

而建造者就是上面所说的模式之一，他很常用，并且可能在使用过程中会出现多种多样的问题。今天我们就来聊一聊建造者模式。我们将从一个案例开始，逐步进入建造者模式，并且以我个人的经验对该模式进行更加深层次的讨论。

# 一、问题引入
假如我们正在构建一个电商平台中的消息通知模块，该模块接收来自于业务系统发起的通知请求，并实现将通知推送给用户。需要注意的是，通知总是多种多样的，比如当新用户注册成功后，我们推送给用户欢迎消息并在消息中附带系统的使用手册，以便用户能快速掌握系统的使用。再比如用户下单后超过一段时间未完成支付时，系统不仅要取消订单，并且要及时推送消息进行告知。其他时候，比如订单的物流更新、订单完成时等等情况，我们都需要以通知的形式告知用户。为了方便演示，我们仅考虑以下四种常用的通知，事实上其他的通知也是类似的，读者可自行扩展。

- **欢迎通知**：欢迎用户手册进入系统，并附带一份系统的快速使用手册；
- **订单支付超时通知**：通知用户支付已超时，订单即将被取消；
- **物流信息变更通知**：通知用户物流已更新，并提供物流最新状态的访问链接；
- **订单完成通知**：通知用户订单已完成，并附带与订单相关的票据，比如发票；

同时，通知的形式也可能是多种多样的，比如平台内部的私信（站内信）、短信、邮件和微信通知等等。同样为了方便演示，此处仅考虑站内信、短信和邮件通知三种通知方式。这三种通知方式的所需的信息并不统一，比如邮件可以携带附件，但短信则无法发送任何形式的文件，他们之间的差异很大。三种方式的属性列表分别如下所示。

**（A）站内信属性列表**

> - senderAccount：发信人账号，必需；
> - recipientAccount：收信人账号，必需；
> - body：正文；
> - redirectUrl：重定向 URL；

**（B）短信属性列表**

> - senderPhone：发信人手机号码，必需，格式只能为手机号码；
> - recipientPhone：收信人手机号码，必需，格式只能为手机号码；
> - content：短信内容；

**（C）邮件属性列表**

> - fromAddress：发件人邮箱地址，必需，格式为邮箱地址；
> - toAddress：收件人邮箱地址，必需，格式为邮箱地址；
> - theme：邮件主题；
> - body：邮件正文；
> - ccAddresses：抄送人邮箱，列表或数组，每一个邮箱地址都需要格式校验；
> - attachments：附件，列表或数组；

除此之外，如果希望保证程序的健壮性，我们可能不得不考虑与其他平台（比如邮箱服务器）之间交互在失败后的重试机制。并且，我们可能需要引入延时机制，这样就能在订单创建时，配置一个在未来某个时刻（比如 5 分钟后）被推送的订单支付超时消息。我们还可以配置一个消息支持取消推送，这样在用户完成支付之后，就可以立马取消该订单的支付超时消息。所以，我们为上面的三种通知方式分别增加如下的属性：

> - cancelable：通知是否可被取消，默认不可取消；
> - delayTimes：延时时间，默认为 0，设置小于 0 时修正为 0；
> - delayTimeUnit：延时时间单位，默认为秒；
> - retryTimes：重试次数，邮件默认重试 1 次，短信默认不重试，站内信默认重试 2 次；

我们暂不考虑如何实现各种各样的消息推送，本章节我们只关心一个问题：如何构建一个通知对象？

## 1.1 重叠构造器
我们可为每个种类的通知类提供所有参数的构造器，对于缺省属性，可采用重叠构造器的方式，提供多个构造器。以邮件通知为例，多个构造器可能如下代码片段所示。
```java
public class Mail {

    // 构造器 1
    public Mail(NoticeStatus state, String fromAddress, String toAddress, String theme,
                String body) {
        this(state, fromAddress, toAddress, theme, body, null, false,
            0, TimeUnit.SECONDS, 1);
    }

    // 此处省略多个构造器

    // 构造器 N
    public Mail(NoticeStatus state, String fromAddress, String toAddress, String theme,
                String body, List<Attachment> attachments, boolean cancelable,
                int delayTimes, TimeUnit delayTimeUnit, int retryTimes) {
        this(state, fromAddress, toAddress, theme, body, attachments, null, cancelable,
            delayTimes, delayTimeUnit, retryTimes);
    }

    // 全参构造器
    public Mail(NoticeStatus state, String fromAddress, String toAddress, String theme,
                String body, List<String> ccAddresses, List<Attachment> attachments, boolean cancelable,
                int delayTimes, TimeUnit delayTimeUnit, int retryTimes) {
        // 校验：例如发件人收件人不能为空，邮箱格式校验等等
    	doValidate();
        // 设置默认值：例如延迟时间默认为0，重试次数默认为1等
        setDefaultVal();
        // 参数修正：比如客户端设置重试次数为-1，修正为0等
        doCorrect();
        // 赋值
        this.state = state;
        this.fromAddress = fromAddress;
        // ...
	}
}
```
如上所示，我们为邮件通知提供了 N 个构造器，每个构造器都有不同的参数列表，可以根据实际的情况选择相应的构造器。为一个类提供多个构造器，就是重叠构造器。肉眼可见，重叠构造器有如下问题：

- **参数过多**：客户端有可能会提供所有的属性列表，此时我们不得不提供全参构造器。上面的邮件案例还仅仅只是演示，实际情景中属性列表比现在还要多。参数越多，就使得构造器的维护越难；
- **构造器过多**：构造器的数量取决于实际需求，理论上客户端除了必填属性外，其他的属性都提供缺省值或者是可选的。想象一下这些属性的可能组合，构造器的数量将越来越无法控制；
- **职责混乱**：对于邮件通知对象来说，更重要的是对于一条邮件通知的表示，但实际情况是，大部分的代码都在构造一个邮件通知对象。这使得原本职责简单的类充斥了大量不必要的职责；

重叠构造器在解决属性过多，并且部分属性提供缺省值，或者部分属性为可选时，并不是很好的选择。会因为构造器的参数过多和存在大量的重叠构造器而暴露出一些亟需解决的问题。

## 1.2 setter
当我们在讨论重叠构造器所展现出来的弊端时，或许你马上就已经想到了解决办法。是的，为需要初始化的属性提供设置方法 —— setter。
```java
public class Mail {

    public void setState(NoticeStatus state) {
        this.state = state;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    // 此处省略多个setter
}
```
setter 确实解决了重叠构造器方式呈现出的一些问题，并且比重叠构造器表现得更加灵活。在宏观上，setter 采取了“化整为零”的思想，每次只关心某个具体的状态，这样便不会在某个方法中出现参数过多的情况，自然也不存在属性组合的说法。并且，setter 允许客户端在任何时刻改变对象的某个内部状态，呈现出来极大的灵活性。尽管如此，setter 还是没有解决职责混乱的问题，类中仍然保留着与对象构建相关的行为（例如赋值前的参数检查等）。除此之外，setter 还带来了一些新的问题。

- **客户端可能得到不完整的对象**：setter 将对象的构建分散成了一系列的方法调用，这使得原本使用一个构造器就能完成的工作被拆分成了多个步骤进行。任何一个步骤的缺失都可能导致客户端拿到一个不完整对象的引用。比如构造邮件通知对象的代码`Mail m = new Mail();  =>  m.setFromAddress("style@hotel.com");  =>  m.setTheme("anything");`得到的邮件通知对象并没有设置收件人的邮箱，这是不被允许的，一件没有指定收件邮箱的邮件是无法发送的。糟糕的是，我们没有好的办法对其进行检查，我们无法保证客户端在使用前一定会完整的走一遍对象的构建步骤；
- **状态易变**：对于一封邮件来说，一旦确定了细节就不会轻易改变，否则其他的行为（比如在邮件初始化时记录的日志）将变得没有意义。而 setter 表现的太过灵活，他允许客户端在任何时刻改变状态，甚至是在邮件已经排队完成，即将发送时。比如一个收件地址初始化为`style@hotel.com`的邮件通知对象在即将发送时被重置为`other@hotel.com`，这是相当危险的。我们更希望能像构造器一样，一旦对象初始化完成，内部状态将无法改变。

# 二、解决方案
构造器和 setter 各有各的优点，也各有各的缺点，此时我们开始构想，如果有一种方式能结合构造器和 setter 的优点，同时又屏蔽掉他们的缺点就好了。

## 2.1 独立的建造器
事实上，我们只需将与对象构建相关的行为独立成一个建造器对象（Builder）就可以同时利用构造器和 setter 的所有优点。一旦将对象的构建行为抽离出去，对象的职责就能更加纯粹，对象负责状态表示，构造器则负责初始化对象。我们为构造器选择 setter 方式，因为我们希望对客户端来说，在初始化对象阶段时能足够灵活；而对象则采用构造器的方式，因为我们希望对象一旦初始化完成，状态将不可随意更改。一个邮件通知对象的构建过程可能如下图所示。
<div align="center">
   <img src="/doc/resource/builder/建造器交互时序图.svg" width="80%"/>
</div>

并且，可以在`build()`中对缓存的所有状态进行检查，如果不满足要求，可以拒绝构造邮件通知对象。这样就能保证对象的完整性。邮件通知对象中并未提供任何改变自身状态的行为，这意味着一旦对象初始化完成，所有的状态是密封的，不可变的。这就解决了 setter 中状态的可变性问题。

> 注意：建造器并不等同于 setter。setter 更注重于单个状态的维护，而建造器的每个建造步骤则可以维护多个内部状态，他允许我们站在更高的维度来构造对象。以一条订单为例，建造器可以将所有与购买用户相关的信息放入一个步骤中；而 setter 则必需将用户ID、用户姓名、联系方式和用户地址的设置分别放入不同的方法中。

至此，我们终于找到了一种较好的邮件通知对象构建方式，而站内信、邮件也都可以采取同样的方式来构建对象。

## 2.2 建造器的抽象
现在我们已经按照同样的方法为所有的通知都定义对应的建造器：短信建造器`ShortMessageBuilder`、邮件建造器`MailBuilder`和站内信建造器`SiteLatterBuilder`。尽管他们负责构造不同的对象，并且每个对象都有不同的属性列表，但是他们却有一定的相似性。比如每一条通知都需要发信人和收信人，在短信里是手机号码，在邮件里是点子邮箱，在站内信里是用户账号；再比如每一条通知都需要信息载体，在邮件里是邮件正文，在短信里是消息内容。我们何不将所有的构建过程都放到抽象中定义？
<div align="center">
   <img src="/doc/resource/builder/建造器的抽象.jpg" width="60%"/>
</div>

如上图所示，我们在所有构造器之上提供了抽象建造器`Builder`，并且定义了所有的构建步骤。例如所有通知都需要的发件者`buildSender(String):void`，所有通知都需要的接收者`buildRecipient(String):void`，所有通知都需要的载体`buildBody(String):void`。

我们同样在抽象建造器中定义了添加抄送人`addCc(String):void`和设置主题`buildTheme(String):void`的行为，但并不是所有类型的通知都需要这些信息，例如短信无法支持消息主题这样的属性，同样也没有抄送人的说法。那我们应该如何处理这些例外情况呢？如果具体建造器不需要某一个建造步骤，我们可以在该建造步骤中什么也不做，表示当前建造器不进行这个步骤；我们也可以在该建造步骤中抛出异常，表示当前建造器并不支持这个步骤。关于这一点，在稍后的内容中我们会继续探讨，此处暂且搁置。

## 2.3 复用建造过程
你或许会感到疑惑，为什么我们在上面要对所有的建造器进行抽象呢？事实上，正是为了建造过程的复用。回想一开始我们提出的四种通知场景，以订单支付超时通知为例，在那里我们并未限定该通知必须是短信通知，亦或者是邮件通知，理论上这三种通知方式都可以被支持。如果我们不对建造过程进行抽象，那么我们就无法复用这个建造过程，换句话说我们必须为每一种通知方式提供独立的建造方法，尽管这些建造过程几乎是完全一致的。

而有了抽象的建造器，一切就不太一样了。我们可以用完全一样的建造过程，来完成不同类型通知的对象创建，如下面的代码片段所示。
```java
public class GenericConstructor {

    private final Builder builder;
    
    public GenericConstructor(Builder builder) {
        this.builder = builder;
    }
    
    /**
     * 订单超时信息
     * @param sender 发送人
     * @param recipient 收件人
     * @return 通知
     */
    public Object orderTimeout(String sender, String recipient) {
        builder.buildSender(sender);
        builder.buildRecipient(recipient);
        builder.buildBody("订单支付已超时，即将被取消！");
        // 允许取消
        builder.buildCancelable();
        // 5分钟超时时间
        builder.buildDelay(5, TimeUnit.MINUTES);
        // 失败重试 3 次
        builder.buildRetryTimes(3);
        return builder.build();
    }
}
```

> 上面的代码片段表示了一个通用的通知构造器，内部维护了一个具体的建造器。当客户端调用`GenericConstructor#orderTimeout()`方法时，将根据建造器`Builder`的类型建造对应类型的通知，如此便能实现对建造过程的复用。

# 三、案例实现
我们已经完整的分析了所有通知的对象构建过程，下面是根据上述分析的案例实现。
## 3.1 案例类图
<div align="center">
   <img src="/doc/resource/builder/案例类图.jpg" width="95%"/>
</div>

案例的类图结构如上所示，类图由以下部分组成。

- **GenericConstructor**：通用的通知构造器。`welcome(String,String):T`表示构造一个欢迎的通知，通知的方式为泛型T，参数分别为发件者和收件者；`orderTimeout(Stirng,String):T`表示构造一个订单超时的通知，参数等同于前者。`logisticsChanged(String,String,String):T`表示物流信息变更通知，参数分别为发件者、收件者和系统重定向 URL，`orderCompleted(String,String,Attachment):T`表示订单完成通知，参数分别为发件者、收件者和票据附件；
- **Builder**：抽象建造器。定义了一系列对象建造的步骤，例如设置发件者信息`buildSender(String):void`、设置收件者信息`buildRecipient(String):void`......设置通知延时时间`buildDelay(int,TimeUnit):void`、设置通知可被提前取消`buildCancelable():void`和设置失败重试次数`buildRetryTimes(int):void`等。提供一个构造通知对象的行为`build():T`，该行为将返回一个新建并完整初始化的通知对象；
- **ShortMessageBuilder、MailBuilder、SiteLatterBuilder**：分别代表短信建造器、邮件建造器和站内信建造器，他们均实现了抽象建造器中定义的一系列建造步骤。如果当前通知并不需要某个建造步骤，该建造步骤的实现中则什么也不做。在建造器的通知对象构造行为`build()`发生之前，建造器将缓存所有客户端设置的状态。当构造行为被调用时，建造器将所有缓存的状态传递给通知类，完成通知对象的初始化；
- **ShortMessage、Mail、SiteLatter**：分别代表短信、邮件和站内信。他们由各自的建造器负责实例化对象，并且在对象的初始化完成之后，再将引用暴露给客户端。

## 3.2 代码附录
<div align="center">
   <img src="/doc/resource/builder/代码附录.png" width="95%"/>
</div>

代码层次及类说明如上所示，更多内容请参考[案例代码](/src/main/java/com/aoligei/creational/builder/message)。客户端示例代码如下
```java
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> Start ---------------------------------------|");
        // 一封欢迎新用户的站内信
        SiteLetter welcome = new GenericConstructor<>(new SiteLatterBuilder())
                .welcome("system", "jack");
        welcome.printSiteMessage();

        // 一条订单超时的短信
        ShortMessage timeout = new GenericConstructor<>(new ShortMessageBuilder())
                .orderTimeout("13135", "18790452415");
        timeout.printShortMessage();

        // 一条物流信息变更的站内信
        SiteLetter logisticsChanged = new GenericConstructor<>(new SiteLatterBuilder())
                .logisticsChanged("system", "tom",
                        "https://on.mall/logistics?id=797e983f-30c0-4ab6-855f-5b24c3f3f543");
        logisticsChanged.printSiteMessage();

        // 一条订单完成的邮件
        Mail mail = new GenericConstructor<>(new MailBuilder())
                .orderCompleted("system@on-mall.com", "67545139@163.com",
                        new Mail.Attachment("a560e36a-31ea.png", 345, null));
        mail.printMail();
    }
}
```
运行结果如下
```text
|==> Start ---------------------------------------|
    站内信：
        状态：已初始化
        发信人账号：system
        收信人账号：jack
        正文：欢迎首次使用系统！
        重定向URL：
        取消发送：不支持
        延时时间：0 (SECONDS)
        失败重试次数：0
    短信：
        状态：已初始化
        发信人号码：13135
        收信人号码：18790452415
        短信内容：订单支付已超时，即将被取消！
        取消发送：支持
        延时时间：5 (MINUTES)
        失败重试次数：3
    站内信：
        状态：已初始化
        发信人账号：system
        收信人账号：tom
        正文：物流信息已更新，访问链接可查看详细信息！
        重定向URL：https://on.mall/logistics?id=797e983f-30c0-4ab6-855f-5b24c3f3f543
        取消发送：不支持
        延时时间：0 (SECONDS)
        失败重试次数：2
    邮件：
        状态：已初始化
        发件人邮箱地址：system@on-mall.com
        收件人邮箱地址：67545139@163.com
        邮件主题：订单已完成
        邮件正文：一笔订单已完成，请查收附件中包含的发票！
        抄送人邮箱地址：
        附件数量：1
        取消发送：不支持
        延时时间：0 (SECONDS)
        失败重试次数：1
```

# 四、建造者模式
## 4.1 意图
> **将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示**。

结合案例对建造者模式的意图解析：

- **将复杂对象的构建与它的表示分离**：对于一个复杂对象，我们不希望将对象的构建过程掺杂在类中，那样会让职责变得混乱。我们更希望将对象的构建过程和对象的表示分离出来；
- **同样的构建过程创建不同的表示**：建造者模式允许我们用同样的构建过程来创建不同的对象表示。正如在上面的案例中，我们使用同样的构建过程`GenericConstructor#welcome(String,String):T`创建出了不同的通知对象。

## 4.2 类图结构
<div align="center">
   <img src="/doc/resource/builder/通用建造者模式类图.jpg" width="50%"/>
</div>

建造者模式的通用类图结构如上所示，他拥有如下的角色列表。

- **Director**： 导向器，构造一个使用`Builder`接口的对象，该类通常用来复用产品的建造过程。正如在案例中，我们预定义的四种通知一样，无论创建的是短信通知对象还是邮件通知对象，他们的建造过程是一致的；
- **Builder**：为创建一个产品对象的各个部件指定抽象接口；
- **ConcreteBuilder**：实现`Builder`的接口以构造和装配该产品的各个部件；
- **Product**：表示被构造的复杂对象；

# 五、使用技巧
## 5.1 产品没有抽象类
值得一提的是在通用的建造者模型中，所有的产品并没有一个抽象类，并且在之前的案例中也是如此。事实上，在案例中我们本可以为所有的产品提供抽象类的，因为他们在某些方面确实有一定的特征，但是为了不产生误导最终还是没有对产品进行抽象。更通常情况下，这些生成的产品他们的相差非常大，所以对他们进行抽象没有必要。	

但如果产品没有抽象类，则会引申出另外的一个问题，在抽象的`Builder`中我们无法确定产品的实际类型。此时我们有如下两种解决办法。

- 不在抽象建造器中定义构建行为`build()`。正如通用的建造者模式类图中所示，抽象的`Builder`中我们并没有定义`build()`行为，而是在具体的建造器`ConcreteBuilder`中添加了`build()`行为，因为具体建造者类明确知道自己即将构建的对象类型；
- 使用泛型的抽象建造器。在前面的案例中，我们就使用了泛型的抽象建造器`Builder<T>`，具体建造器`ConcreteBuilder`在类定义时指定产品类型 T。此时构建行为只需要定义成`build():T`就可以了；

## 5.2 不支持的建造步骤
在前面的案例中，我们提到当一个产品不支持某个建造步骤时，我们可以为该步骤实现缺省的空行为，也可以在该行为中直接抛出异常。当我们为某个步骤实现为什么也不做的空行为时，认为直接忽略掉客户端的当前建造请求；而当我们直接抛出异常时，我们更加注重提示给客户端当前的建造步骤是非法的。这两种方式各有各的出发点，实际遇到时应按需设计。

## 5.3 链式调用
我们可以在每一个建造步骤完成后，立即返回当前建造者对象，这样就能轻松实现这样的链式调用`Mail mail = new MailBuilder().buildSender("xxxx@one.com").buildRecipient("yyyy@one.com").buildBody("nothing").buildRetryTimes(0).build()`。链式调用的可读性更强，代码相对更加简洁，我个人非常喜欢。但相应的，我们需要对建造步骤做出调整，例如应将`buildSender(String):void`修改为`buildSender(String):MailBuilder`方可进行链式调用。如果你仍然不了解链式调用，不用担心，稍后我们将列举一个新的示例，在那个示例中，我们将实现建造器的链式调用。

# 六、退化的建造者
在分析意图时，我们提到建造者模式一共有两个目的，其一是对象构建和表示的分离，其二是同样的过程创建不同的产品。在实际项目中，很多时候我们只需要考虑复杂对象的构建和表示分离就够用了。那么这个时候，我们只需要为产品提供一个建造器类即可。
<div align="center">
   <img src="/doc/resource/builder/退化的建造者模式类图.jpg" width="50%"/>
</div>

> 假如你现在正在开发一款数据采集的系统，该系统负责连接多个物联网设备，并且采集设备上传的数据。想象一下设备该如何连接呢？我们需要指定设备的ip地址，端口号，协议，版本，客户端识别号，账号密码，连接超时时间和读取超时时间等等。如此多的属性配置，正适合使用建造者模式来构建连接配置对象。

针对设备的连接配置类的定义如下所示：
```java
public class ConnectionConfig {

    private final String url;                     // 连接地址
    private final int port;                       // 端口
    private final int protocolMagic;              // 协议魔数
    private final String protocolVersion;         // 协议版本
    private final String clientId;                // 客户端识别号
    private final String username;                // 连接账号
    private final byte[] password;                // 密码
    private final boolean keepAlive;              // 保持连接
    private final byte[] heartbeat;               // 固定心跳包
    private final int maxKeepAliveSecs;           // 连接保持最长空闲时间（秒），超过该时间没有心跳，断开连接
    private final int connectTimeoutSecs;         // 连接超时时间（秒）
    private final int readTimeoutSecs;            // 读取超时时间（秒）

    public ConnectionConfig(ConnectionBuilder builder) {
        // 默认url 127.0.0.1
        this.url = builder.url == null ? "127.0.0.1" : builder.url;
        // 默认端口 13920
        this.port = builder.port == null ? 13920 : builder.port;
        // 默认协议识别号 0xFF2345
        this.protocolMagic = builder.protocolMagic == null ? 0xff2345 : builder.protocolMagic;
        // 默认协议版本 1-0-0
        this.protocolVersion = builder.protocolVersion == null ? "1-0-0" : builder.protocolVersion;
        this.clientId = builder.clientId;
        this.username = builder.username;
        this.password = builder.password.getBytes(StandardCharsets.UTF_8);
        this.keepAlive = builder.keepAlive;
        // 默认心跳包
        this.heartbeat = builder.heartbeat == null ? new byte[]{0xf, 0x3} : builder.heartbeat;
        // 默认最大空闲 5s
        this.maxKeepAliveSecs = Math.max(builder.maxKeepAliveSecs, 5);
        // 默认连接超时 2s
        this.connectTimeoutSecs = Math.max(builder.connectTimeoutSecs, 2);
        // 默认读取超时 1s
        this.readTimeoutSecs = Math.max(builder.readTimeoutSecs, 1);
    }

    public void print() {
        System.out.println("ConnectionConfig {\n" +
                "   ip = " + url + "\n" +
                "   port = " + port + "\n" +
                "   protocolMagic = " + protocolMagic + "\n" +
                "   protocolVersion = " + protocolVersion + "\n" +
                "   clientId = " + clientId + "\n" +
                "   username = " + username + "\n" +
                "   password = " + Arrays.toString(password) + "\n" +
                "   keepAlive = " + keepAlive + "\n" +
                "   heartbeat = " + Arrays.toString(heartbeat) + "\n" +
                "   maxKeepAliveSecs = " + maxKeepAliveSecs + "s\n" +
                "   connectTimeoutSecs = " + connectTimeoutSecs + "s\n" +
                "   readTimeoutSecs = " + readTimeoutSecs + "s\n" +
                '}');
    }

    /**
     * 配置建造器
     */
    public static class ConnectionBuilder {
        private final String url;
        private final Integer port;
        private Integer protocolMagic;
        private String protocolVersion;
        private String clientId;
        private String username;
        private String password;
        private boolean keepAlive;
        private byte[] heartbeat;
        private int maxKeepAliveSecs;
        private int connectTimeoutSecs;
        private int readTimeoutSecs;

        public ConnectionBuilder(String url, int port) {
            this.url = url;
            this.port = port;
        }

        public ConnectionBuilder protocolMagic(int protocolMagic) {
            this.protocolMagic = protocolMagic;
            return this;
        }

        public ConnectionBuilder protocolVersion(String protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        public ConnectionBuilder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public ConnectionBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ConnectionBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ConnectionBuilder keepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        public ConnectionBuilder heartbeat(byte[] heartbeat) {
            this.heartbeat = heartbeat;
            return this;
        }

        public ConnectionBuilder maxKeepAliveSecs(int maxKeepAliveSecs) {
            this.maxKeepAliveSecs = maxKeepAliveSecs;
            return this;
        }

        public ConnectionBuilder connectTimeoutSecs(int connectTimeoutSecs) {
            this.connectTimeoutSecs = connectTimeoutSecs;
            return this;
        }

        public ConnectionBuilder readTimeoutSecs(int readTimeoutSecs) {
            this.readTimeoutSecs = readTimeoutSecs;
            return this;
        }

        public ConnectionConfig build() {
            return new ConnectionConfig(this);
        }
    }
}
```
在该示例中，我们为`ConnectionConfig`提供了静态内部类`ConnectionBuilder`，`ConnectionBuilder`负责构建`ConnectionConfig`对象。针对该示例的客户端如下所示。
```java
public class Client {
    public static void main(String[] args) {
        ConnectionConfig conn = new ConnectionConfig.ConnectionBuilder("localhost", 13920)
                .clientId("048a8a0c41644b53b57e4b612ddabd92")
                .username("root")
                .password("root@!Az").keepAlive(true)
                .heartbeat("hello".getBytes(StandardCharsets.UTF_8))
                .maxKeepAliveSecs(12)
                .connectTimeoutSecs(3)
                .readTimeoutSecs(1)
                .build();
        conn.print();
    }
}
```

> 在前面我们已经介绍过链式调用，在客户端这里我们也实现了链式调用。

该客户端的运行结果如下所示：
```text
ConnectionConfig {
   ip = localhost
   port = 13920
   protocolMagic = 16720709
   protocolVersion = 1-0-0
   clientId = 048a8a0c41644b53b57e4b612ddabd92
   username = root
   password = [114, 111, 111, 116, 64, 33, 65, 122]
   keepAlive = true
   heartbeat = [104, 101, 108, 108, 111]
   maxKeepAliveSecs = 12s
   connectTimeoutSecs = 3s
   readTimeoutSecs = 1s
}
```
这种退化的建造者模式相比完整的建造者模式来说更加简单，应用也更加广泛。我们经常在各种地方都能见到他们的身影，比如`java.util.Calendar`，其内部持有静态内部类`java.util.Calendar.Builder` ，`Builder`中有多个属性，根据多个不同的方法设置属性的值，在`build()`方法中，根据所有属性的值构造不同的`Calendar`对象。

# 七、源码应用举例
建造者模式在源码应用中非常广泛，这里举两个源码案例进行说明。

**（1）java.lang.StringBuilder**

`StringBuilder`就使用了建造者模式，`StringBuilder`作为具体的建造者，继承了抽象的建造者`AbstractStringBuilder`并重写了其中的`append()`、`insert()`、`delete()`、`replace()`、`substring()`、`toString()`等方法。`StringBuilder`负责生产`String`类型的对象，在构造对象的过程中，可反复调用`append()`等方法，来丰富最终的对象。

`StringBuffer`同样继承了`AbstractStringBuilder`，也是用于负责生产`String`类型的对象。他们之间的区别是，`StringBuffer`在`append()`、`insert()`、`delete()`、`replace()`等构造步骤中使用`synchronized`关键字来保证线程安全。

**（2）org.springframework.beans.factory.support.BeanDefinitionBuilder**

spring中的`BeanDefinitionBuilder`同样使用了建造者模式，他负责构建`AbstractBeanDefinition`对象，如下代码片段所示。
```java
public final class BeanDefinitionBuilder {

    // 创建的bean定义对象
    private final AbstractBeanDefinition beanDefinition;

    // 设置此bean定义的父定义名称
    public BeanDefinitionBuilder setParentName(String parentName) {
		this.beanDefinition.setParentName(parentName);
		return this;
	}

    // 设置此bean定义的静态工厂方法的名称
    public BeanDefinitionBuilder setFactoryMethod(String factoryMethod) {
		this.beanDefinition.setFactoryMethodName(factoryMethod);
		return this;
	}

    // 设置此bean定义的非静态工厂方法的名称
    public BeanDefinitionBuilder setFactoryMethodOnBean(String factoryMethod, String factoryBean) {
		this.beanDefinition.setFactoryMethodName(factoryMethod);
		this.beanDefinition.setFactoryBeanName(factoryBean);
		return this;
	}

    // ...
}
```


# 附录
[回到主页](/README.md)&emsp;[消息通知案例代码](/src/main/java/com/aoligei/creational/builder/message)&emsp;[连接配置案例代码](/src/main/java/com/aoligei/creational/builder/conn)
