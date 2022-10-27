## <center> 行为型 - 责任链（Chain Of Responsibility）设计模式
---

本篇将介绍一个有趣，强大但比较复杂的模式，责任链（chain of responsibility）模式。关于责任链模式，可以讨论的内容有很多，但那样会让这篇文章格外长。所以本篇文章讨论的重点还是在于认识并理解责任链模式，对于更加深入的内容本篇将不再提及或者简短带过。按照以往的习惯，让我们仍然从一个例子开始。

# 一、问题引入

> 我们正在构建一款运行于 ATM 机上的自助取款应用，该应用为用户提供了一个界面，在界面中用户可以输入代表任意金额的数字。应用根据用户输入的金额为用户分配几种面额（100 元、50 元和10 元）的组合，以等值的纸币来置换账户上的金额。我们约定用户输入的数字必须是 10 的倍数，因为 ATM 机目前暂不支持低于 10 元面额的纸币。并且，应用总是希望能提供给用户最少的纸币数量。

针对于这个应用，我们应该如何设计呢？或许我们可以采用最原始的方式来实现，如下所示：
```java
public class ATMApplication {
    public static void main(String[] args) {
        while (true) {
            int amount;
            System.out.println("|==> 输入一个需要提取的金额：-------------------------------------|");
            Scanner s = new Scanner(System.in);
            amount = s.nextInt();
            if (amount % 10 > 0) {
                System.out.println("    错误的金额，只支持输入10$的倍数");
            } else {
                // 分配100元的纸币
                if (amount >= 100) {
                    int allocated100Num = amount / 100;
                    System.out.println(MessageFormat.format("    面额【100】：【{0}】张", allocated100Num));
                    amount = amount % 100;
                }
                // 已凑齐金额
                if (amount == 0){
                    continue;
                }
                // 分配50元的纸币
                if (amount >= 50) {
                    int allocated50Num = amount / 50;
                    System.out.println(MessageFormat.format("    面额【50】：【{0}】张", allocated50Num));
                    amount = amount % 50;
                }
                // 已凑齐金额
                if (amount == 0){
                    continue;
                }
                // 分配10元的纸币
                if (amount >= 10) {
                    int allocated10Num = amount / 10;
                    System.out.println(MessageFormat.format("    面额【10】：【{0}】张", allocated10Num));
                    amount = amount % 10;
                }
            }
        }
    }
}
```
尽管这一堆臃肿杂乱的代码在上线后一切表现正常，但它在扩展和维护时，给我们带来了极大的难度。

> - 如果应用需要支持 20 元面额的人民币，我们不得不在原来众多的逻辑分支中增加新的逻辑分支，这简单的一个调整却直接对整个功能产生了影响。并且随着项目的推进，逻辑分支也将变得愈加臃肿，愈加难以扩展和维护；

> - 某些时候，我们不得不将某些逻辑从现有逻辑链路中移除（比如说：当前 ATM 机在某一时刻 50 元面额的纸币使用殆尽，此时，ATM 机器无法再为用户提供 50 元的纸币），但我们发现在应用运行过程中剔除分配 50 元纸币的功能很困难。要是所有的逻辑都以一个一个的组件形式进行组织，那么这个问题将变得极其容易，当不需要某个逻辑时，只需要从现有插件链路中移除对应的组件。

我们迫切的需要一种方式来对其重构，以便使开发人员可以从日益剧增的维护投入中解放出来。

# 二、解决方案

**（1）封装请求对象**

上面提到，系统有可能在某一时刻从逻辑链路中移除一个逻辑（比如不再提供 10 元的纸币），此时对于用户输入的金额，则会出现无法提供服务的情况（比如用户输入 1460 元，此时缺少 1 张 10元）。为了应对这种情况，我们必须先对金额进行分配【`addAllocated()`】，如果能将所有金额完全分配，再为用户出钞【`cashOut()`】，否则，不提供出钞的服务。为了更好的描述系统处理的过程，可以将金额，各个节点的分配情况封装成单独的请求对象。

**（2）链路逻辑组件化**

责任链模式建议我们将一块一块的逻辑（分配 100 元纸币、分配 50 元纸币等）封装成为多个对象，并且将这些对象按照顺序进行组织，成为一个处理请求对象的链条。这样我们就可以将请求投递给链条，请求在链条中按照顺序逐个传递，每一个处理对象可选择对请求处理或者不处理。此时出钞的过程如下图所示：
<div align="center">
   <img src="/res/chain-of-responsibility/出钞过程示意图.jpg" width="90%"/>
</div>

从上图中，我们看出所有的组件（处理器）都有着两个共同的特点：分配对于面额的纸币、连接着后继处理器（尾处理器除外）。为了使所有的组件去差异化，我们应该对组件进行抽象，这样我们就可以使任意一个组件成为某个组件的后继，甚至可以随意调换他们之间的顺序。

# 三、案例实现

## 3.1 案例类图
按照上面的思路，我们对系统进行重构，新的类图结构如下图所示。
<div align="center">
   <img src="/res/chain-of-responsibility/案例类图.jpg" width="100%"/>
</div>

> 在应用启动时，通过`AbstractPaperPasCurrencyAllocator.nextAllocator()`提前构造好链路【`RMB100Allocator -> RMB50Allocator -> RMB10Allocator` 】。当用户发起兑换现金的请求后，请求首先到达 100 元的分配处理器。在该处理器分配完毕后，会根据请求中剩余的金额（`CurrencyRequest.toAllocateAmount`）决定是否继续向后继处理器传递请求，如此往复。直到请求从链路中脱离时，应用开始出钞（`CurrencyRequest.cashOut()`），如果全部金额正常分配，则为用户提供现金，否则告知用户系统无法提供本次服务。

## 3.2 代码附录
<div align="center">
   <img src="/res/chain-of-responsibility/代码附录.png" width="95%"/>
</div>

代码层次及类说明如上所示，更多内容请参考[案例代码](/cases-behavioral/src/main/java/com/patterns/chain_of_responsibility)。ATM 应用示例代码如下
```java
public class ATMApplication {

    private static final AbstractPaperCurrencyAllocator ALLOCATOR  = new RMB100Allocator();;
    static {
        // 【RMB100Allocator -> RMB50Allocator -> RMB10Allocator】
        AbstractPaperCurrencyAllocator rmb50 = new RMB50Allocator();
        AbstractPaperCurrencyAllocator rmb10 = new RMB10Allocator();
        ALLOCATOR.setNextAllocator(rmb50);
        rmb50.setNextAllocator(rmb10);
        // 【RMB100Allocator -> RMB50Allocator】
        // AbstractPaperCurrencyAllocator rmb50 = new RMB50Allocator();
        // ALLOCATOR.setNextAllocator(rmb50);
    }

    public static void main(String[] args) {
        while (true) {
            int amount;
            System.out.println("|==> 输入一个需要提取的金额：-------------------------------------|");
            Scanner s = new Scanner(System.in);
            amount = s.nextInt();
            if (amount % 10 > 0) {
                System.out.println("    错误的金额，只支持输入 10 的倍数");
            } else {
                CurrencyRequest req = new CurrencyRequest(amount);
                ALLOCATOR.allocate(req);
                req.cashOut();
            }
        }
    }
}
```

# 四、责任链模式
## 4.1 意图
> **使多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系。将这些对象连成一条链，并沿着这条链传递该请求，直到有一个对象处理它为止。**

这里结合着案例对责任链模式的意图进行说明：首先，责任链模式使得多个对象（处理器）都得到了处理请求的机会，并且请求的发送者和请求的处理者之间降低了耦合（请求是沿着链路逐个向后传递，请求并不用知道这个链路中包含了哪些具体的处理器）。

> 《Design patterns- Elements of reusable object-oriented software》一书中提到责任链模式在有一个对象处理请求时，请求将不再向后继处理器传递。这里我认为值得商榷，我觉得请求从处理器链中脱离的条件不应是某个处理器处理了请求，而应该是某个处理器阻断了向后传递请求。就像本例中，`RMB100Allocator`在处理完请求后，仍然可以将请求交给后续处理器处理。所以，还是应具体问题具体分析，当请求只需要被其中一个处理器所处理时，可以实现为有一个对象处理就不再向后传递（就像是请求拦截器）；否则，还是应该给后续处理器一些机会。

## 4.2 类图分析

<div align="center">
   <img src="/res/chain-of-responsibility/经典责任链模式类图.jpg" width="60%"/>
</div>

责任链模式的类图结构如上所示，在责任链模式中，有如下的参与角色：

- **Handler**：定义一个处理请求的接口，提供设置后继 Handler（可选）；
- **ConcreteHandler**：处理他所负责的请求，根据需要决定是否向后传递请求；
- **Client**：向链上的处理器提交请求。

# 五、深入理解
## 5.1 特点

**（A）降低了耦合度**

责任链模式使得请求的发起者无需知道是哪一个（或者哪些）处理器对请求进行了处理。并且对于所有的处理器来说，每一个仅需要保持一个后继者的引用，甚至如果整个链路关系不需要在处理器内部进行维护（例如在使用技巧中提到的变种实现），那么这一点耦合度也没有了。

**（B）增强了链路逻辑的灵活性**

这一点很明显，责任链通过类似于插件的形式，让系统表现出了极大的灵活性。我们可以方便的为系统新增处理器，并且不会影响到现有的处理器。也可以通过配置化的形式来调整链路的结构和顺序，还可以在运行时动态的改变链路，例如从链路中移除一个处理器。

**（C）无法保证请求一定被处理**

责任链模式使得一个请求没有明确的执行者，既然没有明确的执行者也可能就没有执行者。换句话说，有些情况下链路中的所有处理器，都不能处理这个请求。

> 我们在用户输入金额时，就检查了输入的数字是否为 10 的倍数，这是为了过滤一些无法处理的请求。如果没有这个检查，部分请求可能无法被处理。例如用户输入 5，任何一个纸币分配器都无法为其服务，导致请求穿透整个链路也没有被处理。

尽管我们无法保证任何一个请求一定会得到处理，但我们至少能给应用一个告警，通过这个告警能让系统发现问题，并且为问题的追踪提供一定的帮助。我们可以在链路的最末端增加一个告警处理器，这个处理器仅仅只是为了提示用户请求穿透了链路也没有被处理。

## 5.2 适用场景

**（A）多个处理器按照顺序处理请求时**

很多时候，对于一个请求，有多个处理者按照顺序对其进行处理，此时我们使用责任链模式将处理者排列成链，使得请求在链上逐个向后传递，给所有的处理者提供一个处理机会。

> 比如一个提交用户信息的请求体：内容包含有用户姓名、身份证号码、电话号码、邮箱等等信息，我们需要对这些信息进行合法性以及存在性验证。此时，我们可以提供用户姓名校验器、身份证号码校验器、电话号码校验器、邮箱校验器，并将他们按照顺序组织并逐个校验请求。

**（B）可插拔的组件**

当你希望在某些时候可以从链路中移除某个处理器时，可以使用责任链模式，使用其可以轻松实现组件的链路配置。

> 考虑上面的案例，当 ATM 中的 10 元纸币已经使用完毕时，我们可以动态的将 10 元纸币的分配器从链路中移除，这样，系统就不再为用户分配 10 元的纸币。这里就不对其进行实现了，如果感兴趣可自行实现。

# 六、扩展
## 6.1 使用技巧

**（A）如有必要，记录请求穿透时的现场**

在上面我们说到：责任链模式无法保证请求一定会被处理，在某些情况下，一个请求会穿透整个链路，却没有一个处理器能对其处理。但同时，我们又希望任意请求都能在链路中至少找到一个处理器对其进行处理，请求不应该穿透链路。此时，告知系统有请求穿透链路的情况就尤为重要。我们可以在链路的末端，放置一个用于为请求兜底的处理器，这个处理器可以不处理请求，仅负责告知系统请求穿透时的现场情况，比如原始请求的所有信息。

## 6.2 另一个变种
在责任链模式的类图分析处，我们提到 Handler 不一定非要提供一个设置后继处理器的接口。我们可以把链路的维护职责独立出来，这也是责任链模式的一个变种实现。这个变种的类图结构如下：

<div align="center">
   <img src="/res/chain-of-responsibility/责任链模式变种类图.jpg" width="80%"/>
</div>

在这个类图结构中，整个链路由 HandlerChain 负责维护，提供了增加处理器，删除处理器等方法。除此之外，HandlerChain 本身也实现了处理器接口，在它的处理方法（handle）中，也负责请求在整个链路中的传递。它内部维护了当前的处理器在整个链路中的索引，在客户端发起一个请求后：

> - 1）HandlerChain 驱动第一个处理器处理（当前 pos = 0，对应链路中的第一个处理器）；
> - 2）在当前处理器处理时，可根据需要决定是否向后传递，如果不需要，则请求跳出链路；
> - 3）处理器调用 HandlerChain 的 handle 方法，HandlerChain 再次驱动下一个处理器（pos 在上一次驱动时已自增）进行处理；
> - 4）如此反复，直到某一个处理器决定不再向后传递，或者当前处理器已经是最后一个处理器（pos >= handlers.size()）。

总的来看，这个变种的好处是：每个处理器不再需要依赖后继处理器，各个处理器的顺序不再取决于谁是谁的后继，而是他们各自在 HandlerChain 中的位置。这个变种比经典责任链模式拥有更加灵活的特性，还解除了处理器之间的依赖。Servlet 中的过滤器（Filter）就是基于这个变种实现的。

# 七、从源码中看责任链模式

**（1）tomcat 中的 Filter**

在 servlet 规范中，约定了可以通过 Filter 在请求进入容器后，执行 Servlet.service() 方法之前，对 web 资源进行过滤，权限鉴别等处理。在 tomcat 启动时，会加载所有的过滤器信息。在 tomcat 收到请求时，再加载整个过滤器的链路，然后请求执行过滤器的链条，等请求从链路中脱离后，才会进入真正的业务接口。工作过程如下图所示：

<div align="center">
   <img src="/res/chain-of-responsibility/Filter工作过程示意图.jpg" width="80%"/>
</div>

在 tomcat 中，每一个 Filter 都是一个处理器，该处理器不仅能处理请求，还能处理响应。如上图，对于 request 来说，链路的结构为 FilterA ->  FilterB -> FilterC，而对于响应来说，链路的结构为  FilterC ->  FilterB -> FilterA。这种双向处理的思想很经典，经常能在各个框架中看到类似的实现。那么，是如何实现的呢？下面将简单分析一下源码。
```java
public interface Filter {
    public default void init(FilterConfig filterConfig) throws ServletException {}
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException;
    public default void destroy() {}
}
```
Filter 为处理器的抽象，提供了三个方法，其中 doFilter() 方法为处理方法，三个参数分别为请求、响应和链路管理器。接下来看一下链路管理器（FilterChain）的代码：
```java
// FilterChain
public interface FilterChain {
    public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException;
}
// ApplicationFilterChain
public final class ApplicationFilterChain implements FilterChain {
    
    // 处理器链路，ApplicationFilterConfig 可以理解为对 Filter 的包装
    private ApplicationFilterConfig[] filters = new ApplicationFilterConfig[0];
    
    // 当前处理器在链路中的索引
    private int pos = 0;
    
    // 调用入口
    @Override
    public void doFilter(ServletRequest request, ServletResponse response)
        throws IOException, ServletException {

        if( Globals.IS_SECURITY_ENABLED ) {
            final ServletRequest req = request;
            final ServletResponse res = response;
            try {
                java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedExceptionAction<Void>() {
                        @Override
                        public Void run()
                            throws ServletException, IOException {
                            // here
                            internalDoFilter(req,res);
                            return null;
                        }
                    }
                );
            } catch( PrivilegedActionException pe) {
                Exception e = pe.getException();
                if (e instanceof ServletException)
                    throw (ServletException) e;
                else if (e instanceof IOException)
                    throw (IOException) e;
                else if (e instanceof RuntimeException)
                    throw (RuntimeException) e;
                else
                    throw new ServletException(e.getMessage(), e);
            }
        } else {
            // here
            internalDoFilter(request,response);
        }
    }
    
    // 开始处理
    private void internalDoFilter(ServletRequest request,
                                  ServletResponse response)
        throws IOException, ServletException {

        // Call the next filter if there is one
        // 不停的驱动下一个过滤器
        if (pos < n) {
            ApplicationFilterConfig filterConfig = filters[pos++];
            try {
                Filter filter = filterConfig.getFilter();

                if (request.isAsyncSupported() && "false".equalsIgnoreCase(
                        filterConfig.getFilterDef().getAsyncSupported())) {
                    request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR, Boolean.FALSE);
                }
                if( Globals.IS_SECURITY_ENABLED ) {
                    final ServletRequest req = request;
                    final ServletResponse res = response;
                    Principal principal =
                        ((HttpServletRequest) req).getUserPrincipal();

                    Object[] args = new Object[]{req, res, this};
                    SecurityUtil.doAsPrivilege ("doFilter", filter, classType, args, principal);
                } else {
                    // 这里驱动了过滤器
                    filter.doFilter(request, response, this);
                }
            } catch (IOException | ServletException | RuntimeException e) {
                throw e;
            } catch (Throwable e) {
                e = ExceptionUtils.unwrapInvocationTargetException(e);
                ExceptionUtils.handleThrowable(e);
                throw new ServletException(sm.getString("filterChain.filter"), e);
            }
            return;
        }

        // We fell off the end of the chain -- call the servlet instance
        // 链路中没有更多过滤器了，开始进入 servlet
        try {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(request);
                lastServicedResponse.set(response);
            }

            if (request.isAsyncSupported() && !servletSupportsAsync) {
                request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR,
                        Boolean.FALSE);
            }
            // Use potentially wrapped request from this point
            if ((request instanceof HttpServletRequest) &&
                    (response instanceof HttpServletResponse) &&
                    Globals.IS_SECURITY_ENABLED ) {
                final ServletRequest req = request;
                final ServletResponse res = response;
                Principal principal =
                    ((HttpServletRequest) req).getUserPrincipal();
                Object[] args = new Object[]{req, res};
                SecurityUtil.doAsPrivilege("service",
                                           servlet,
                                           classTypeUsedInService,
                                           args,
                                           principal);
            } else {
                // 进入servlet 处理实际业务
                servlet.service(request, response);
            }
        } catch (IOException | ServletException | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            e = ExceptionUtils.unwrapInvocationTargetException(e);
            ExceptionUtils.handleThrowable(e);
            throw new ServletException(sm.getString("filterChain.servlet"), e);
        } finally {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(null);
                lastServicedResponse.set(null);
            }
        }
    }
}

```
在链路管理器的实现（ApplicationFilterChain）中，用数组来组织各个处理器的先后顺序，并提供了一个当前处理器在链路中的索引（pos）。主要的代码逻辑是，取出当前的处理器，并且数组下标向后移动，使用处理器进行处理，处理器处理完毕后再次回到这里（不是必须的，要看处理器是否回调了ApplicationFilterChain 的 doFilter() 方法），进入下一个处理器。当链路未在中途断开，并且当前处理器已经是最后一个处理器时，调用 servlet.service(request, response) 进入业务处理逻辑。作为用户，我们可以根据需要定义新的 Filter，通过配置文件或者注入等方式让其生效。

**（2）netty 中的 pipeline**

netty 中的 pipeline 则采用了双向链表的形式来组织所有处理器。netty 在设计上认为，对于入站的数据来说（收到的数据），给链路中的所有处理器都提供一个处理机会，出站同理（这里讨论的是 channel 提供的写出方法，对于 context 提供的并不适用）。在 netty 中，处理器的结构示意图如下：

<div align="center">
   <img src="/res/chain-of-responsibility/netty处理器结构示意图.jpg" width="90%"/>
</div>

netty 中，所有的处理器都继承自 ChannelHandler 接口，由该接口衍生出 ChannelInboundHandler 和 ChannelOutboundHandler，分别是入站数据处理器和出站数据处理器。他们分别为数据的入站和出站服务，当数据入站时，只有链路上的入站处理器会得到处理的机会；出站同理。
```java
public interface ChannelHandler {
    // 处理器被添加到上下文中，并且已经准备好处理事件时的回调
    void handlerAdded(ChannelHandlerContext ctx) throws Exception;
    // 处理器从上下文中移除后的回调
    void handlerRemoved(ChannelHandlerContext ctx) throws Exception;
    // 异常捕获
    @Deprecated
    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
}
```
整个链路在 ChannelPipeline 中负责维护，它提供了往链路中添加处理器的各种方法。
```java
public interface ChannelPipeline extends ChannelInboundInvoker, 
      ChannelOutboundInvoker, Iterable<Entry<String, ChannelHandler>> {
    // 各种往链路中添加处理器的方法
    ChannelPipeline addFirst(String name, ChannelHandler handler);
    ChannelPipeline addFirst(EventExecutorGroup group, String name, ChannelHandler handler);
    ChannelPipeline addLast(String name, ChannelHandler handler);
    ...
}
```
netty 提供了一个默认的 DefaultChannelPipeline 来实现链路管理器。主要相关代码如下：
```java
public class DefaultChannelPipeline implements ChannelPipeline {
    // 头处理器上下文
    final AbstractChannelHandlerContext head;
    // 尾处理器上下文
    final AbstractChannelHandlerContext tail;
    
    protected DefaultChannelPipeline(Channel channel) {
        // 初始化头尾
        tail = new TailContext(this);
        head = new HeadContext(this);
        head.next = tail;
        tail.prev = head;
    }
    
    @Override
    public final ChannelPipeline addLast(String name, ChannelHandler handler){
        // 往尾部添加新的处理器
        return addLast(null, name, handler);
    }
    
    @Override
    public final ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler) {
        final AbstractChannelHandlerContext newCtx;
        synchronized (this) {
            checkMultiplicity(handler);
            // 封装处理器到AbstractChannelHandlerContext上下文
            newCtx = newContext(group, filterName(name, handler), handler);
            // 将上下文添加到链路最后
            addLast0(newCtx);

            ...
        }
        // 回调处理器添加完成
        callHandlerAdded0(newCtx);
        return this;
    }
    
    private void addLast0(AbstractChannelHandlerContext newCtx) {
        AbstractChannelHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
    }
}
```
我们注意到，netty 并不是直接将 ChannelHandler 添加到链路中，而是首先将其封装成 AbstractChannelHandlerContext 的上下文对象，再添加到链路中。看一下 AbstractChannelHandlerContext 的相关代码：
```java
abstract class AbstractChannelHandlerContext implements 
    ChannelHandlerContext, ResourceLeakHint {
    
    volatile AbstractChannelHandlerContext next;
    volatile AbstractChannelHandlerContext prev;
    
    ...
}
```
这么一看，就明了了。AbstractChannelHandlerContext 上下文中维护了前处理器（prev）和后继处理器（next）的引用，每一个处理器的上下文中已经维护了该处理器相对于整个链路的前后关系。这样，当数据进站时，只需从头处理器开始，往后逐个匹配处理器，如果该处理器是入站数据处理器类型，则有机会处理该数据；而当数据出站时，则需从尾处理器开始，往前挨着匹配，如果该处理器是出站数据处理器类型，则可以处理该数据。


# 附录
[回到主页](/README.md)&emsp;[案例代码](/cases-behavioral/src/main/java/com/patterns/chain_of_responsibility)