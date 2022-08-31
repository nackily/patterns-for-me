## <center> 行为型 - 适配器（Adapter）设计模式
---

# 一、问题引入

> 趁着这段时间的 618 活动，我在某宝入手了一台 iphone13 手机。不得不说，现在的手机厂商真是太精了，买了手机都不给配充电头，当时也没怎么在意，想着毕竟目前也是用着 iphone 手机，不是还有一个旧的充电头嘛。到货的时候就傻眼了，新手机只有一根数据线，接入充电头的那一头采用的是 usb-c 接口，而我的旧充电头只能接入 type-a 的排口，数据线没有办法插入旧的充电头。。

无奈准备再买一个专用的充电头回来，结果一看价格，149，这什么充电头，这么贵。。

# 二、解决方案
## 2.1 适配器

转头一想，没有买充电头的肯定不止我一个，有问题就一定有解决办法，果不其然，我翻到了有商家卖下图的转接头。转接头价格相比原装充电头完全可以接受，果断买回来，一头接上手机数据线，一头插上旧的充电头，诶这不就 OK 了嘛。
<div align="center">
   <img src="/doc/resource/adapter/转接头图片.png" width="30%"/>
</div>


事实上，转接头在整个结构中起了适配的作用，而它就是适配器。所谓适配器，正如上例中的转换头一样，一头连接 type-c 类型的手机数据线，另一头连接着 usb-a 类型的充电头，这使得原本因为类型不匹配的两个零件能正常的工作。

## 2.2 适配过程

在充电行为中，有三个角色参与其中，他们分别是充电头、转接头、手机数据线。有时我们还不得不为同一个充电头适配多个数据线，那么就需要多个适配器。此时我们可以为多个适配器提供一个适配器的抽象，在抽象的适配器中定义适配的行为，由各个适配器实现针对特定零件的适配。

> 假如我正好有一台手持游戏机也需要充电，该游戏机的数据线输入端同样不是 type-c 类型的，无法插入充电头，不得已我又买了一个对应的转接头。

现在，各个参与角色之间具有如下的交互关系：
<div align="center">
   <img src="/doc/resource/adapter/适配过程示意图.jpg" width="60%"/>
</div>

在这个交互关系图中，我们拥有两根数据线，两个对应的转接头，但只有一个充电头。以充电头的角度来分析，充电头提供的充电服务在经过不同的转接头后，最终由各个设备数据线接收。转接头并不提供充电服务，也不享受充电服务，由此看来，适配器就像是一个请求传递者，负责连接两个不相容的接口，并将请求从一方传递到另一方。

# 三、案例实现
在前面我们已经分析了案例中转接头在充电过程中的适配过程，其实这个适配过程就是适配器模式的工作过程，上图中提到的参与者也与适配器模式的参与角色一一对应。

## 3.1 案例类图
按照上面的分析，此处已经实现了这个案例，下图为案例的类图结构。
<div align="center">
   <img src="/doc/resource/adapter/案例类图.jpg" width="100%"/>
</div>

对于该类图的说明如下：

- **Plug**：充电头，提供充电服务；
- **AccessUsbCable**：转接头的抽象，提供接入数据线的行为（`access()`）；
- **PhoneUsbCableAdapter、GameConsoleUsbCableAdapter**：分别为手机数据线转接头、游戏机数据线转接头；
- **PhoneUsbCable**：手机数据线，提供接入 usb-c 类型的电源插座；
- **GameConsoleUsbCable**：游戏机数据线，提供接入 usb-b 类型的电源插座；

## 3.2 代码附录

**（1）数据线**

**（1-1）手机数据线**
```java
public class PhoneUsbCable {

    public void accessTypeC (){
        System.out.println("    手机数据线接入 type-c 类型接口");
    }

}
```
**（1-2）游戏机数据线**
```java
public class GameConsoleUsbCable {

    public void accessTypeB (){
        System.out.println("    游戏机数据线接入 type-b 类型接口");
    }

}
```
**（2）转接头抽象**
```java
public interface AccessUsbCable {
    /**
     * 接入数据线
     */
    void access();
}
```
**（3）转接头**

**（3-1）手机数据线转接头**
```java
public class PhoneUsbCableAdapter implements AccessUsbCable {

    private final PhoneUsbCable phoneUsbCable;

    public PhoneUsbCableAdapter(PhoneUsbCable phoneUsbCable) {
        this.phoneUsbCable = phoneUsbCable;
    }

    @Override
    public void access() {
        System.out.println("    手机数据线适配器接入 type-a 类型的接口，接出 usb-c 类型的接口");
        this.phoneUsbCable.accessTypeC();
    }
}
```
**（3-2）游戏机数据线转接头**
```java
public class GameConsoleUsbCableAdapter implements AccessUsbCable {

    private final GameConsoleUsbCable gameConsoleUsbCable;

    public GameConsoleUsbCableAdapter(GameConsoleUsbCable gameConsoleUsbCable) {
        this.gameConsoleUsbCable = gameConsoleUsbCable;
    }

    @Override
    public void access() {
        System.out.println("    游戏机数据线适配器接入 type-a 类型的接口，接出 usb-b 类型的接口");
        this.gameConsoleUsbCable.accessTypeB();
    }
}

```
**（4）充电头**

**（4-1）充电头**
```java
public class Plug {

    public static void main(String[] args) {
        System.out.println("手机充电时接线，该插座接出 usb-a 类型的接口：");
        AccessUsbCable phone = new PhoneUsbCableAdapter(new PhoneUsbCable());
        phone.access();

        System.out.println("游戏机充电时接线，该插座接出 usb-a 类型的接口：");
        AccessUsbCable gameConsole = new GameConsoleUsbCableAdapter(new GameConsoleUsbCable());
        gameConsole.access();
    }

}
```
**（4-2）运行结果**
```text
手机充电时接线，该插座接出 usb-a 类型的接口：
    手机数据线适配器接入 type-a 类型的接口，接出 usb-c 类型的接口
    手机数据线接入 type-c 类型接口
游戏机充电时接线，该插座接出 usb-a 类型的接口：
    游戏机数据线适配器接入 type-a 类型的接口，接出 usb-b 类型的接口
    游戏机数据线接入 type-b 类型接口
```

# 四、适配器模式
## 4.1 意图
> **将一个类的接口转换成客户希望的另外一个接口，适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作**。

适配器模式的意图非常明显，尽管如此，为了帮助理解，我们还是结合着上面的例子对意图进行说明：

- **一个类的接口转换成客户希望的另外一个接口**：在充电时，我的数据线插入充电头的那一端是 type-c 类型的，所以，我希望充电头是 usb-c 类型的；
- **使得原本由于接口不兼容而不能一起工作的那些类可以一起工作**：因为充电头是 usb-a 类型和我的 type-c 类型不兼容，没有办法接入，但因为有了转接头的存在，让他们可以在一起完成充电工作。

## 4.2 类图结构
适配器模式的类图则如下所示：
<div align="center">
   <img src="/doc/resource/adapter/案例类图.jpg" width="60%"/>
</div>

在适配器模式中，包含有以下主要角色，我们可以将各个角色与上面的例子进行类比分析：

- **Client**：使用放 Target 接口的用户。与充电头的作用一致，通过相应的转接头给不同的设备提供充电服务；
- **Target**：定义给 Client 使用的接口。定义一个将其他类型的接线口转换成 type-a 类型的方法，由具体的适配器实现，不同的适配器可针对不同的接线口类型进行适配；
- **Adapter**： 对 Adaptee 的接口与 Target 接口进行适配 。等效于手机数据线转接头、游戏机数据线转接头，负责将其他类型的接口转换成 type-a 类型，接入充电头；
- **Adaptee**：定义一个已经存在的接口，这个接口需要适配。比如手机数据线、游戏机数据线，需要适配器来适配，否则无法接入充电头。

# 五、深入
## 5.1 适用场景
适配器有一个比较独特的使用场景，如果用一个词描述的话，就是“亡羊补牢”。

> 回想一开始的例子，如果我在一开始就知道 iphone13 数据线与现有的充电头无法适配时，我或许会在下单手机的同时就买一个对应的充电头回来，这样就能完全匹配。但事实是，我发现了数据线与现有的充电头无法直连，才出此下策，买了一个转接头回来进行适配。

由此可以看出，由于前期设计上出现问题，导致实际产品在对接时出现偏差的时候我们就可以使用适配器模式来挽救这个局面，这样做的好处是：已经出现偏差的两方产品不需要改动。实际上，出于挽救这一初衷而采用适配器模式的场景有很多。

- **场景Ⅰ** 现有几个类，拥有相似的功能，但是没有统一的规范（没有接口约束，方法名，参数均不一样），可以用适配器模式分别适配这几个类，使调用方可以按照统一的方式进行调用；
- **场景Ⅱ** 我给别人提供了 sdk，在新的版本中我发现将某个 api 的参数类型调整一下，能让整个方法更加高效，但是这样就意味着使用旧版本的用户升级到新版本时，不得不改动调用。此时可以不改 api 的方法签名，只需要将旧的 api 方法委托给新的接口实现。

# 六、从源码中看适配器模式

**（1）**`**java.util.concurrent.Executors**`**类中的静态内部类**`**RunnableAdapter**`

```java
public class Executors {
    
    /**
     * 运行给定任务并返回给定结果的可调用对象
     */
    static final class RunnableAdapter<T> implements Callable<T> {
        final Runnable task;
        final T result;
        RunnableAdapter(Runnable task, T result) {
            this.task = task;
            this.result = result;
        }
        
        public T call() {
            task.run();
            return result;
        }
    }
    
}
```

> 我们看到：在`RunnableAdapter`这个类的定义中，实现了`Callable`接口，内部维护了一个`Runnable`类型的 task ，在实现的`call()`方法中，调用了`Runnable#run()`方法。所以，`RunnableAdapter`类本身就是一个适配器，目的就是将`Runnable`类型的对象包装成一个`Callable`类型的对象。在这段源码中，Target 是`Callable`接口，Adapter 是`RunnableAdapter` ，Adaptee 是`Runable`的实例 task。

这样看来`RunnableAdapter`其实是将`Runnable`包装成`Callable`，这正是适配器模式的特点。那这样做对 jdk 来说有什么实际的意义呢？

要想知道这样封装有什么作用，得看哪里在使用这个类。我们跟踪到`Executors`类中使用了这个类的对象，而正好`Executors`类我们比较熟悉。
```java
public class Executors {
    
    /**
     * 运行给定任务并返回给定结果的可调用对象
     */
    static final class RunnableAdapter<T> implements Callable<T> {
        // ...
    }
    
    /**
     * 返回一个 {@link Callable} 对象，该对象在被调用时运行给定任务并返回给定结果。 
     * 这在将需要 {@code Callable} 的方法应用于其他没有结果的操作时很有用。
     * @param task 要运行的任务
     * @param result 返回的结果
     * @param <T> 结果的类型
     * @return 可调用对象
     * @throws NullPointerException 如果任务为空
     */
    public static <T> Callable<T> callable(Runnable task, T result) {
        if (task == null)
            throw new NullPointerException();
        return new RunnableAdapter<T>(task, result);
    }
    public static Callable<Object> callable(Runnable task) {
        if (task == null)
            throw new NullPointerException();
        return new RunnableAdapter<Object>(task, null);
    }
}
```
`Executors#callable()` 方法的文档注释中提到：`当将需要 Callable 的方法应用于其他无结果的操作时，这个方法会很有用`。

这说了个啥？？既然看不懂，那就再往上找，看看哪里在调用这个方法。然后就跟踪到了`FutureTask`这个类。
```java
public class FutureTask<V> implements RunnableFuture<V> {
    private Callable<V> callable;
    private volatile Thread runner;
    
    public FutureTask(Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
        this.state = NEW;       // ensure visibility of callable
    }
    
    public FutureTask(Runnable runnable, V result) {
        this.callable = Executors.callable(runnable, result);
        this.state = NEW;       // ensure visibility of callable
    }
}
```
看到这里，一下就明白了。`FutureTask`类中只维护了一个`Callable`类型的任务对象，但是`FutureTask`需要支持提交`Runnable`类型的任务。如果不将`Runnable`类型包装成`Callable`类型，就意味着`FutureTask`类还需要再维护一个`Runnable`类型的任务，而`FutureTask`只能维持一个任务，也就是说`Callable`类型和`Runnable`类型必然有一个任务是 null 。这对于`FutureTask`来说，在交给线程执行的时候就很麻烦了，需要找到不为空的那一个任务，且需根据任务的类型进行具体的处理。

梳理一下整个逻辑，得出如下链路调用关系：

- **Runnable**：worker thread --> futureTask.run() --> callable.call() --> task.run()
- **Callable**：worker thread --> futureTask.run() --> callable.call()

综上所述，`RunnableAdapter`类采用适配器模式主要是为了使上层应用只需要统一处理`Callable`类型的接口，以便上层应用只需处理一套逻辑。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/structural/adapter)

