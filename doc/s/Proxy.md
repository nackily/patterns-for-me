## <center> 结构型 - 代理（Proxy）设计模式
---

如果你是 Spring 的开发者，那么你一定听过 AOP 的鼎鼎大名，而代理模式正好是 AOP 的核心。本文我们就来谈一谈代理模式。请注意，在本文中我们不会讨论 AOP 相关的内容，那不是我们本文关注的重点。我们的重点是：什么是代理模式，如何使用它？

# 一、理解代理
“代理”一词广泛出现在我们的日常生活中，即便你和一个其他行业的人谈起代理，你们也能将话题进行下去。这说明，代理模式很有可能是来源于生活，并提炼自生活中鲜活的例子。

## 1.1 关于代理
很多影视剧中，经常会出现代理律师这样的角色。这个代理律师其实就是广义代理的典型例子，这里我们简单梳理一下代理律师、被代理人和第三人之间的关系：

1. 代理律师与被代理人：代理律师可以帮助被代理人处理日常事务以及解决法律纠纷，但是代理律师在和第三人的所有行为中，使用的的是被代理人的名义，其法律后果直接由被代理人承担，代理人在代理权限范围内实施代理行为 ；
1. 代理律师与第三人：被代理人和第三人的事务处理都由代理律师出面进行，处理事务时，被代理人可以不用和第三人见面 ；

通过上述的关系描述，我们可以总结出代理的几个特征，它们分别是：

- ①. 参与者有代理者、被代理者、第三人；
- ②. 代理者做出的行为代表着被代理人，代理过程中的行为权限由被代理人提供；
- ③. 代理者并不总能代替被代理人做所有决定，有权限范围的限制，超出范围外的行为无法代理（只能代理在代理协议范围内的行为）；
- ④. 第三人和代理者不直接发生工作交集；

## 1.2 代理的关系
通过上面的梳理，我们得到如下的角色和他们之间的关系图：
<div align="center">
   <img src="/doc/resource/proxy/代理关系示意图.jpg" width="50%"/>
</div>

总结来说，代理主要包括 4 个角色，他们分别是：代理协议、代理人、被代理人和第三人，代理人和被代理人通过签订代理协议的方式来约定代理的权限范围，这样一来，被代理人和第三人之间的法律事务就可以由代理人出现解决，我们认为被代理人将自身相关的法律权益和责任委托给代理人了。

## 1.3 为何要介绍代理
你可能感到疑惑：为什么在开始介绍代理模式之前要浪费篇幅，阐述这么一段看起来没什么用的内容？

> 我觉得正好相反，我们提到上面的内容很有用。就像我们常说的：艺术来源于生活，却高于生活。我认为设计模式也是同样的，他引用了生活中鲜活的模板来解决面向对象开发中出现的一些问题。我们通过上面的例子能够在后续的内容中引入代理模式，通过他们直接的类比，对代理模式的理解也更容易。
> 并且，设计模式在于意，不在于形，通过例子我们能更加把握住核心的指导思想，这样我们就能将设计模式的使用范围扩大到其他的领域中去，而不仅仅只是限制于某一个特定的业务场景中。

# 二、代理模式
在上面我们已经通过代理律师的工作模式对生活中的代理进行了阐述，接下来我们将直接进入代理模式的内容。

## 2.1 模式意图
> **为其他对象提供一种代理以控制对这个对象的访问。**

代理模式的定义相当简洁高效，只有短短一句话来阐述该模式，但这个高度概况的表达方式或许会让我们有些难以理解。我们换一种更加通俗的方式来表达：为第三人（其他对象）提供一种代理（代理人）以控制对这个对象（被代理人）的访问（事务处理）。所以，这句话表达的意思等同于：给被代理人找一个代理人，第三人和被代理人之间的事务处理通过代理人委托执行。

## 2.2 类图分析
<div align="center">
   <img src="/doc/resource/proxy/代理模式类图.jpg" width="70%"/>
</div>

在类图结构中，共有 4 种角色，对各个角色的分析如下：

- **Proxy**：代理方。相当于上述例子中的代理律师的角色；
- **Subject**：定义代理方和被代理方的共用接口，这样一来，任何使用 RealSubject 的地方都可以替换成Proxy。相当于上述例子中的代理协议，代理双方共同遵守，这样双方就拥有了一样的行为；
- **RealSubject**：被代理方。相当于上述例子中的被代理人；
- **Client**：任何需要使用 Proxy 的地方。相当于上述例子中的第三人。

> 在上面的类图中，Proxy 不仅是 Subject 的实现，还保存了一个 Subject 的引用。从目的上来说，前一点是为了保证 Proxy 遵循着和 RealSubject 同样的行为规范，借此实现可以使用 RealSubject 的地方也同样能替换成 Proxy；而后一点是为了转达客户端的请求，Proxy 在接收到 Client 的请求后，会在合适的时机向 RealSubject 转发这个请求。

# 三、适用场景
在《Design patterns- Elements of reusable object-oriented software》一书中，将代理模式的适用场景分为了 4 类，他们分别是：远程代理（Remote Proxy）、虚拟代理（Virtual Proxy）、保护代理（Protection Proxy）以及智能引用（Smart Reference）。本文借鉴了该分类方式，并在这个分类的基础上根据实际场景重新整合后得到如下的适用场景列表。

## 3.1 延迟对象的初始化
延迟对象初始化实际上描述了这样的一种需求场景：对于一个初始化开销很大的对象，我们发现该对象初始化后的较长一段时间内，我们都还没有使用该对象。此时，我们希望该对象的初始化过程能推迟到真正使用的时候。

> 比如，对于一个图像编辑器系统来说，提供了加载图片、绘制图像、修改图像以及保存图片的功能。加载图片可以将图片从磁盘或者网络上加载到内存中，绘制图像则是将内存中的图像对象绘制到屏幕上。图片加载这个操作的开销可能很大，这个开销包括内存的开销以及时间的开销，比如从网络上加载一副空间占用很大的图片。我们有一个实现了各种操作的图像类，但这个图像类会在图像初始化时就加载图像，这会导致我们只要初始化图像的对象，就可能发生卡顿。但对于一个友好的图像编辑器来说，打开文档的速度必须快速，此时，我们就可以用代理解决该问题。
> 具体做法是，为图像对象提供一个代理对象，在真正需要展示图像的时候才去加载图像（代理类的绘制方法中才去初始化真实的图片对象），这样就可以避免在初始化图像对象时就不得不加载图像的尴尬境地。

该案例的实现代码如下所示：

**图像接口**
```java
public interface Graphic {

    /**
     * 绘制图像到屏幕
     */
    void draw();

    /**
     * 图像宽度
     * @return 宽度
     */
    double getWidth();

    /**
     * 图像高度
     * @return 高度
     */
    double getHeight();

    /**
     * 存储图片
     */
    void store();

}
```
**图片类**
```java
public class Image implements Graphic {

    private final String fileName;

    /**
     * 图片宽度
     */
    private double width;

    /**
     * 图片高度
     */
    private double height;

    public Image(String fileName) {
        this.fileName = fileName;
        this.loadImage(fileName);
    }

    private void loadImage(String fileName) {
        System.out.println("    开始加载图片");
        // 模拟加载图片
        width = Math.random() * (50) + 51;
        height = Math.random() * (50) + 51;
    }

    @Override
    public void draw() {
        System.out.println(MessageFormat.format("    已绘制图片[{0}]", this.fileName));
    }

    @Override
    public double getWidth() {
        System.out.println(MessageFormat.format("        图片宽度为 {0}", this.width));
        return this.width;
    }

    @Override
    public double getHeight() {
        System.out.println(MessageFormat.format("        图片高度为 {0}", this.height));
        return this.height;
    }

    @Override
    public void store() {
        System.out.println(MessageFormat.format("    已存储图片[{0}]", this.fileName));
    }
}

```
**图片代理类**
```java
public class ImageProxy implements Graphic {
    
    private Image image;
    
    private final String fileName;
    
    public ImageProxy(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public void draw() {
        if (image == null) {
            this.image = new Image(this.fileName);
        }
        this.image.draw();
    }
    
    @Override
    public double getWidth() {
        if (image == null) {
            System.out.println("        图片宽度为50，当前未加载图片，使用默认图像");
            return 50;
        } else {
            return image.getWidth();
        }
    }
    
    @Override
    public double getHeight() {
        if (image == null) {
            System.out.println("        图片高度为50，当前未加载图片，使用默认图像");
            return 50;
        } else {
            return image.getHeight();
        }
    }
    
    @Override
    public void store() {
        if (image != null) {
            this.image.store();
        }
    }
}
```
**使用客户端**
```java
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> 打开文档【/res/a.png】---------------------------------------------|");
        Graphic image = new ImageProxy("/res/a.png");
        System.out.println("    获取图片宽度：");
        image.getWidth();
        // 对比使用代理和不使用代理两种情况，可以发现使用代理之后将图像加载的操作
        // 从 初始化对象时 延迟到 绘制图像时
        image.draw();
        System.out.println("    获取图片高度：");
        image.getHeight();
    }
}

```
**运行结果**
```text
|==> 打开文档【/res/a.png】---------------------------------------------|
    获取图片宽度：
        图片宽度为50，当前未加载图片，使用默认图像
    开始加载图片
    已绘制图片[/res/a.png]
    获取图片高度：
        图片高度为 79.385
```

> 我们在代理类维持一个默认的图像（50*50），当图片加载没有完成时，使用代理类中的默认图像作为与用户的友好交互，可以等图片加载完成后重新绘制到屏幕上。

如果你曾用过 Mybatis 或者 Hibernate 等框架，那你一定知道他们提供的延迟加载的功能，而几乎所有的延迟加载的底层实现原理都和代理模式离不开关系。延迟加载的实现机制是给目标对象创建代理对象，当我们调用对象的 get 方法时（比如 user.getRole()），代理对象会先对这个 role 属性进行检查，如果为 null 则查询数据库并将结果 set 进目标对象，并返回查询结果。这样就实现了在的确需要的时机（需要使用属性对象）才去获取需要的数据（查询子表并设置对应的属性对象）。

## 3.2 对访问进行控制
保护代理所描述的场景是：只有满足某些条件时，才将当前请求转达给被代理对象。这些条件可以是客户端身份的凭据必须得到认证、也可以是合法性满足要求，条件的定义取决于需求。举例来说：

> 1. 我为系统中的某个接口设置了白名单，只有当前用户在白名单内才允许访问，可以在代理对象中对客户端的请求进行判断，只有当前用户在白名单列表中时，才将请求转发到被代理对象中；
> 1. 被代理接口希望客户端给我传递的参数是完整并且合法的，此时可以在代理对象中对参数进行验证，当参数完整并且合法时，再将参数传递给被代理对象；

## 3.3 调用远程接口
当我们需要通过远程接口获取数据时，可以使用代理进行封装，将网络相关的复杂细节包装在代理类中实现。

> 这实际上已经超出了代理模式所表述的场景了，因为代理方和被代理方已经不属于同一个 JVM实例 的管辖范围了。在本地服务调用远程服务接口时，本地服务的代理对象代理的是远程服务（另一个 JVM实例）上的对象。举例来说，远程服务上有一个接口 Api，该接口会返回一些数据 Data；在本地服务上，我们也提供了一个拥有 Api 方法的代理对象，在该方法中通过网络转发到远程服务的 Api 方法上执行，并将结果返回到本地的代理对象。

<div align="center">
   <img src="/doc/resource/proxy/远程代理交互过程.svg" width="50%"/>
</div>

远程代理的时序图如上所示，代理层作为客户端和远程接口之间的桥梁，为客户端隐藏了复杂的网络交互细节。这种思想在 web 开发中被广泛应用，比如现今的`HTTP远程调用`和`RPC远程调用`技术，使得开发者可以像使用本地方法一样调用远端接口。

## 3.4 缓存请求的结果
代理可以对于重复请求所对应的结果进行缓存，对于相同的请求来说，第一次获取结果并放入缓存中，之后的请求可以从缓存中直接获取，而不必再次向被代理对象转发请求。在获取结果的过程开销很大时（比如请求的数据来源于传统数据库，获取结果意味着与数据库交互），代理对于系统的提升是质的飞越。 缓存代理的时序图如下所示
<div align="center">
   <img src="/doc/resource/proxy/缓存代理时序图.svg" width="40%"/>
</div>

## 3.5 真实对象的引用计数
可通过代理实现对真实对象（或者其属性）的引用进行计数，这样代理对象就拥有了真实对象的引用客户端列表。我们可以时不时的遍历各个客户端，检查他们的运行状况，当客户端已不再使用时，将其从列表中移除。如果引用列表为空，则可以销毁这个真实对象，释放资源。在 Java 中，堆内存的回收机制开发者无法进行管理，但在使用直接内存等情况时，可以借助于代理来实现资源的释放。

## 3.6 日志记录代理
可在向被代理对象传递请求的前后记录日志，这样就可以实现请求历史记录的监控。

# 四、浅聊 Spring 动态代理
既然谈到了代理模式，又是以 Java 语言讲解的，就不得不聊一下动态代理了。在我们所熟悉的 spring 框架中，支持两种动态代理的实现方式，分别是 **jdk 动态代理** 和 **cglib 动态代理**。事实上，动态代理并不止这两种实现，除此之外还有 javaassist 等，有兴趣的朋友自行查阅资料吧。

## 4.1 什么是动态代理

> 在上面的代理模式介绍中，我们不难发现，如果想要使用代理模式，则要求每一个被代理的对象，都需要匹配一个代理的对象。这样就有一个问题，如果我有 10 个需要代理的类，那就意味着我需要再写 10 个一一对应的代理类。

而动态代理就是为了解决这个问题的，动态代理就是一种在程序运行时，动态的创建目标对象的代理对象，并对目标对象的方法提供了可供增强的切入点。在使用动态代理后，我们不需要再自己创建代理对象，甚至不需要再编写代理类了。

## 4.2 两种动态代理
简单了解一下他们各自的实现方式：

> - jdk 的动态代理机制是基于反射的，在运行期间由 JVM 帮我们生成代理类和实例化代理对象；
> - cglib 的动态代理是基于 ASM 框架提供的字节码修改技术，动态为被代理类生成一个代理类；

那么，既然 spring 已经支持了 jdk 的动态代理，为什么还要引入 cglib 的动态代理呢？

因为 jdk 的动态代理有一个缺点——只能代理接口（Interface）。如果目标类没有实现接口，那么就无法使用 jdk 的动态代理。而 cglib 则可以支持类的动态代理，这意味着使用 cglib 即便目标类没有接口，同样可以代理类中的目标方法。下图是两种代理实现的类图对比。
<div align="center">
   <img src="/doc/resource/proxy/两种动态代理对比.jpg" width="90%"/>
</div>

## 4.3 动态代理示例
这里，我们以 jdk 动态代理为例，演示如何使用 jdk 的动态代理，cglib 的动态代理有兴趣的同学可自行查阅资料。更多关于动态代理的实现原理我已整理并放在代码注释中。

**接口**
```java
public interface AnyService {
    /**
     * 目标方法_0
     */
    void targetFunc0();

    /**
     * 目标方法_1
     * @param num any number
     * @return anything
     */
    String targetFunc1(int num);
}
```
**被代理类**
```java
public class AnyServiceImpl implements AnyService {
    @Override
    public void targetFunc0() {
        System.out.println("    =>> 执行目标方法");
    }

    @Override
    public String targetFunc1(int num) {
        return "Anything";
    }
}
```
**调用处理程序**
```java
public class ProxyInvocationHandler implements InvocationHandler {

    /**
     * 被代理对象
     */
    private Object target;

    /**
     * 设置目标对象
     * @param target 目标对象
     */
    protected void setTarget(Object target){
        this.target = target;
    }

    /**
     * 获取代理对象
     * @return 代理对象
     */
    protected Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=>> 代理对象的Java类型：" + proxy.getClass().getSimpleName());
        System.out.println("=>> 调用的方法名：" + method.getName());
        System.out.println("=>> 调用方法的参数：" + Arrays.toString(args));

        System.out.println("    =>> 执行目标方法之前");
        Object res = method.invoke(target, args);
        System.out.println("    =>> 返回：" + res);
        System.out.println("    =>> 执行目标方法之后");
        return res;
    }
}
```
**客户端**
```java
public class Client {
    public static void main(String[] args) {
        // 在 jdk 提供的动态代理中，有两个重要的类，一个是 Proxy，一个是 InvocationHandler。
        // Proxy 提供了一个静态方法 newProxyInstance 来生成代理对象，三个参数分别是：
        //     类加载器：决定哪个类加载器来加载代理类；
        //     接口列表：代理类要实现的接口列表，这个参数决定代理类生成后有哪些方法；
        //     调用处理程序：在调用代理对象的目标方法时的处理逻辑。
        // InvocationHandler 提供了一个 invoke 方法，该方法的逻辑会被附加到代理对象的目标方法中，三个参数分别是：
        //     代理对象：生成的代理对象；
        //     方法：客户端调用的方法（代理的方法可能不止一个）；
        //     调用参数：调用代理对象时，传入的参数。
        System.out.println("|==> Start -----------------------------------------------------|");
        // 实例调用处理程序
        ProxyInvocationHandler handler = new ProxyInvocationHandler();
        // 指定目标对象
        handler.setTarget(new AnyServiceImpl());
        // 生成代理对象
        AnyService proxy = (AnyService) handler.getProxy();
        // 调用目标方法
        proxy.targetFunc1(3);
    }
}
```
**运行结果**
```text
|==> Start -----------------------------------------------------|
=>> 代理对象的Java类型：$Proxy0
=>> 调用的方法名：targetFunc1
=>> 调用方法的参数：[3]
    =>> 执行目标方法之前
    =>> 返回：Anything
    =>> 执行目标方法之后
```


# 附录
[回到主页](/README.md)    [延迟对象初始化案例](/src/main/java/com/aoligei/structural/proxy/virtual_proxy)    [JDK动态代理示例](/src/main/java/com/aoligei/structural/proxy/jdk_proxy)