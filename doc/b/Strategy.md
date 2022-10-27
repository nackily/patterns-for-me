## <center> 行为型 - 策略（Strategy）设计模式
---

即便我们不知道什么是策略模式，但总能在工作中听见这个名词。策略模式与单例模式和工厂方法模式一起，组成了最被开发者熟知的模式天团。和以往一样，我们仍然从案例开始讨论策略模式。

# 一、问题引入

> 假设你的团队开发了一款数据分析平台，平台针对数据提供了丰富多样的分析报表，并且提供了报表导出的功能。需求总是不断发展着的，有天产品组收到用户提出的建议：期望支持导出报表压缩包的功能。用户还附带了这条建议的原因：系统针对某些业务数据的导出，设置了记录数的限制，比如单个文件不超过 5w 条记录。所以用户期望提供导出压缩包的功能，这样就能一次导出所有的数据，这些数据可能分布在压缩包内的不同文件中。

这个需求很容易实现，同时也为用户提供了更加良好的体验，没有该功能之前，用户只能一批一批数据导出，这个功能能让用户从繁琐重复的导出工作中解放出来。你在现有的功能基础上，增加了多个文档的压缩包导出功能，仅仅是将原来的多个文档压缩成 ZIP 格式并且提供用户下载即可。

不久后，用户又希望增加 TAR 和 JRA 格式的支持。因为对用户来说，ZIP 格式并不能满足所有的实际工作需求。此时，你的导出功能代码很可能如下所示。

```java
public OutputStream compressFiles (String type, List<File> files) {
    if (type.equals("zip")) {
        // zip格式
        return compressZip(files);
    } else if (type.equals("jar")) {
        // jar格式
        return compressRar(files);
    } else if (type.equals("tar")) {
        // tar格式
        return compressTar(files);
    } else {
        // 其他格式...
    }
}
```

此时，你开始意识到在不久的将来，这个类很可能会出现以下的问题。

**（1）压缩包相关代码越来越庞大**

随着压缩包格式的支持，该方法也将越来越庞大，条件分支也将越来越多。更糟糕的是，导出为压缩包与对象的表示无关，更多的格式支持也就意味着与对象操作相关的代码将远远多于对象表示相关的代码。这对于该类之后的维护是很难接收的，因为这个类里面存在太多与自身无关的行为，这就与我们常说的最少职责原则相悖。

**（2）牵一发动全身**

另一个问题是，每一次对这个类的修改都有可能对这个类所有的行为产生影响，这意味着在支持新的压缩包格式时，不得不对之前已有的压缩包格式导出进行回归测试。在仅有少量几个压缩包时，这个问题我们还能忍受，但是当支持的格式越来越多时，每一次的改动都将给测试人员带来不小的工作量。

# 二、解决方案

如果你仔细思考一下就会发现，上面的两个问题是由于同一个原因引起的：对象中包含了太多的行为。正是因为存在过多的行为才会导致类的代码越来越庞杂，比如既然实现压缩成 ZIP 格式，又要实现压缩成 TAR 格式；也正是因为包含了过多的行为，才会导致对其中任何一个行为做出的修改都可能会影响到其他的行为。所以，我们迫切的需要一种方式来将条件分支中的逻辑进行解耦，只有当对象的职责尽量单一时，我们才能保证各个对象具有更高的伸缩性。

事实上，这在面向对象中是很简单的，我们只需要给每一种压缩包格式都提供一个独立的压缩处理器对象。如此就可将多个行为分散到了不同的对象中，之后对于任何压缩处理器的修改都将在单独的对象中进行，不会影响到其他的压缩处理器。并且，我们让所有的压缩处理器都实现一个抽象的处理器`CompressStrategy`，在抽象处理器仅定义一个压缩的行为`compress()`，由各个压缩处理器负责提供响应压缩格式的实现。这样一来，他们就具有了统一的行为声明，就可以从外面实现一致的调用。如下图所示。
<div align="center">
   <img src="/res/strategy/压缩处理器结构.jpg" width="50%"/>
</div>

这正是策略模式的原型，策略模式建议我们定义一系列的算法，并将他们一个一个的封装起来。对于该例来说，不同的导出格式就对应了不同的导出算法，比如上图中的`ZipCompressor`，由它可以实现压缩成 ZIP 格式，所以它就是一个算法的封装。同时，所有算法的封装都实现自同一个抽象的行为定义`CompressStrategy#compress()`，因此他们具有同样的行为，所以他们之间是可以相互替换的。

除此之外，策略模式还建议我们在整个层次之上再抽取一个上下文的对象。在对象中，可以封装一些其他的操作，比如负责准备算法行为所需的参数，或者记录一些日志信息等等。也可以仅仅只是转发请求到压缩处理器上，尽管看起来多此一举，但实际上该上下文对象承担着屏蔽底层变动的风险。这一点在后续我们将继续阐述，此处不再过多引申。

# 三、案例实现
## 3.1 案例类图

按照上面的论述，我们实现的案例的类图结构如下所示。
<div align="center">
   <img src="/res/strategy/案例类图.png" width="90%"/>
</div>

在该类图结构中：

- `CompressStrategy`是压缩策略的抽象，定义了共用的压缩行为：`compress(Collection<CompressEntry>,OutputStream)`，其参数分别为待压缩的文档集合和输出流对象。`CompressEntry`为元数据，`CompressEntry#content`为文档对应的字节数组，`CompressEntry#relativePath`为文档相较于压缩包根目录的路径；
- `TarCompressor`、`JarCompressor`、`ZipCompressor`皆是压缩处理器，负责将待压缩的文档集合压缩成对应的格式并写入输出流中；
- `CompressContext`：策略上下文，内部维护一个压缩处理器的引用，并在合适的时候向压缩处理器发起压缩文档集合的请求。`CompressContext#compressFile(Path,Path[],Path)`表示将磁盘文件压缩后写入到磁盘中，参数一代表需要压缩的文件或文件夹路径，参数二代表集合中不参与本次压缩的文件或文件夹路径，参数三表示输出文件路径。`CompressContext#compressFile(Path,Path)`同上，表示路径下的文件全部参与压缩。`CompressContext#compressClasses(Class<?>[],Path)`表示将多个JAVA类压缩后写入到磁盘中，参数一代表需要压缩的`Class`，参数二表示输出文件路径。

## 3.2 代码附录
<div align="center">
   <img src="/res/strategy/代码附录.png" width="95%"/>
</div>

代码层次及类说明如上所示，更多内容请参考[案例代码](/cases-behavioral/src/main/java/com/patterns/strategy)。客户端示例代码如下
```java
public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("|==> Start -------------------------------------------------------|");
        // 压缩磁盘文件为zip格式压缩包
        CompressContext context = new CompressContext(new ZipCompressor());
        context.compressFile(Paths.get("opt", "test", "funny"), Paths.get("opt", "test", "output.zip"));

        // 压缩磁盘文件为tar格式压缩包
        context = new CompressContext(new TarCompressor());
        context.compressFile(Paths.get("opt", "test", "one"), Paths.get("opt", "test", "output.tar"));

        // 压缩class为jar包
        context = new CompressContext(new JarCompressor());
        Class<?>[] toPackageClasses = { JarCompressor.class, CompressStrategy.class, Client.class };
        context.compressClasses(toPackageClasses, Paths.get("opt", "test", "output.jar"));
    }

}
```
运行结果如下
```text
|==> Start -------------------------------------------------------|
    :::: 待压缩的文件【opt/test/funny/文档.docx】
    :::: 待压缩的文件【opt/test/funny/message/ll.xlsx】
    :::: 待压缩的文件【opt/test/funny/message/ttt/新建文本文档.txt】
    ==>> 开始压缩[.zip]文件...
    <<== 压缩[.zip]文件完成...
    :::: 待压缩的文件【opt/test/one/文档.docx】
    ==>> 开始压缩[.tar]文件...
    <<== 压缩[.tar]文件完成...
    :::: 待压缩的类【com.aoligei.behavioral.strategy.compressor.JarCompressor】
    :::: 待压缩的类【com.aoligei.behavioral.strategy.CompressStrategy】
    :::: 待压缩的类【com.aoligei.behavioral.strategy.Client】
    ==>> 开始压缩[.jar]文件...
    <<== 压缩[.jar]文件完成...
```

# 四、策略模式
## 4.1 意图
策略模式的意图如下：

> **定义一系列的算法，把它们一个个封装起来，并且使它们可相互替换。本模式使得算法可独立于使用它的客户而变化。**

以下是结合上述案例，对该意图的解析。

- **定义一系列算法，把它们一个个封装起来**：算法就是策略类的内部处理逻辑，压缩到不同的压缩文件格式就属于不同的算法。一个一个封装起来指的是一个把原本在一个类中的多个算法逻辑拆分到不同的类中。简而言之就是将不同的算法逻辑拆分到不同的类中进行实现；
- **并且使它们可相互替换**：同一种类型的算法之间可相互替换，比如`JarCompressor#compress`和`TarCompressor#compress`二者可以互相替换，因为他们的行为实现自同一个接口；
- **使得算法可独立于使用它的客户而变化**：这句描述的是，算法逻辑的变化不会造成客户端代码的改变，比如改变`JarCompressor#compress`的方法代码就意味着算法的逻辑已经发生了变化，但我们并不需要修改使用处的代码。

## 4.2 通用结构分析
<div align="center">
   <img src="/res/strategy/经典策略模式类图.jpg" width="60%"/>
</div>

策略模式的类图通常如上所示，其主要有以下三个角色。

- **Context**：上下文，维护了一个`Strategy`对象的引用，在`contextInterface()`方法中调用`strategy`对象的`algorithmInterface()`方法；
- **Strategy**：定义所有支持的算法的公共接口；
- **ConcreteStrategy**：具体的策略。

# 五、深入
## 5.1 特点

**（1）为什么客户端需要与 context 直接交互？**

在策略模式中，Client 的请求是通过 Context 进行转发的，客户端的请求最终都被转发到具体策略类中。这里加一层 context 的好处主要有两个。

- **对外提供统一入口**：对客户端提供统一的入口，可以避免客户端与具体的策略类直接进行交互。这样一来，客户端就和策略类隔离开来，如果策略类的行为定义发生变化（例如，`Strategy#algorithmInterface()`方法改了名称），只需要变动`Context`内部即可，对客户端来说，依旧调用`Context#contextInterface()`方法不作任何修改；
- **封装可能存在的变化**：例如现在需要在执行`Strategy#algorithmInterface()`方法之前记录日志，如果没有`Context`，那么就只能在每一个调用的地方写日志打印的代码，而现在只需要在`ContextcontextInterface()`方法中`Strategy#algorithmInterface()`调用之前记录日志即可。

在案例中，我们提供了`CompressContext`类，也是为了封装可能存在的变化。比如说`CompressContext#compressClasses()`方法，在该方法调用压缩方法`CompressStrategy#compress()`之前，将相关类转换成`List<CompressEntry>`对象，并且准备一个`OutputStream`对象，用于接收压缩后的内容。

**（2）客户端必须了解不同的策略**

策略模式有一个潜在的特点，那就是客户必须对所有的策略类都相当了解，并能根据实际的需求选择一个合适的策略对象作为`Context`的上下文。其实只要仔细思考一下就不难发现，这是由于把条件处理的职责转嫁给客户端造成的。

> 在上面的案例中，我们似乎解决了本文开头的一连串条件分支。我们为所有的策略对象提供了同样的行为定义，以此来消除不同条件分支之间的差异。对于策略模式内部来说，确实不用处理不同的参数带来的不同行为，但无意间却把这个决策交给了客户端。这种甩锅行为确实让模式的结构内部耦合更低，但是如果客户端也不知道具体该创建哪种策略类，还是无法避免条件分支。职责转移并不是策略模式独有的，回想一下，工厂方法模式在这个问题上是不是和策略模式如出一辙？

如果以后谁再告诉你：我用某某设计模式干掉了项目中的 if-else ，那你可要小心了，你可能正在掉进他们的逻辑陷阱中。这个世界没有免费的午餐，有些事，注定需要有人兜底的。

# 六、从源码中看策略模式

**（1）在`java.util.Arrays`类中，通过`Comparator`类型的排序规则，对数组进行排序**

这里的`Comparator`就是抽象的策略，定义了排序的算法，由子类自行实现排序逻辑。
```java
@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```
`java.util.Arrays`就是上下文`Context`，和标准策略模式中不同的是，在`Arrays`的实现中，并未将`Comparator`作为对象的成员属性，而是在需要排序的时候以方法参数的形式传入。
```java
public class Arrays {
    public static <T> void sort(T[] a, Comparator<? super T> c) {
        if (c == null) {
            sort(a);
        } else {
            if (LegacyMergeSort.userRequested)
                legacyMergeSort(a, c);
            else
                TimSort.sort(a, 0, a.length, c, null, 0, 0);
        }
    }
    
    private static <T> void legacyMergeSort(T[] a, Comparator<? super T> c) {
        T[] aux = a.clone();
        if (c==null)
            mergeSort(aux, a, 0, a.length, 0);
        else
            mergeSort(aux, a, 0, a.length, 0, c);
    }
    
    private static void mergeSort(Object[] src,
                                  Object[] dest,
                                  int low, int high, int off,
                                  Comparator c) {
        int length = high - low;

        // Insertion sort on smallest arrays
        if (length < INSERTIONSORT_THRESHOLD) {
            for (int i=low; i<high; i++)
                for (int j=i; j>low && c.compare(dest[j-1], dest[j])>0; j--)
                    swap(dest, j, j-1);
            return;
        }

        // Recursively sort halves of dest into src
        int destLow  = low;
        int destHigh = high;
        low  += off;
        high += off;
        int mid = (low + high) >>> 1;
        mergeSort(dest, src, low, mid, -off, c);
        mergeSort(dest, src, mid, high, -off, c);

        // If list is already sorted, just copy from src to dest.  This is an
        // optimization that results in faster sorts for nearly ordered lists.
        if (c.compare(src[mid-1], src[mid]) <= 0) {
           System.arraycopy(src, low, dest, destLow, length);
           return;
        }

        // Merge sorted halves (now in src) into dest
        for(int i = destLow, p = low, q = mid; i < destHigh; i++) {
            // 在此处调用了 compare 方法
            if (q >= high || p < mid && c.compare(src[p], src[q]) <= 0)
                dest[i] = src[p++];
            else
                dest[i] = src[q++];
        }
    }
}
```

**（2）在Mybatis中，通过`Executor`定义的各种方法，对数据库进行操作**

在 Mybatis 中，`Executor`就是抽象的策略接口，该类中定义了各种对数据库操作的方法，这里以`Executor#query()`方法为例：
```java
public interface Executor {
      <E> List<E> query(MappedStatement ms, 
                        Object parameter, 
                        RowBounds rowBounds, 
                        ResultHandler resultHandler) throws SQLException;
}
```
`DefaultSqlSession`就是策略上下文，内部维护了一个`Executor`对象，在对外部暴露的接口中调用了其实现的`query()`方法，查询数据。
```java
public class DefaultSqlSession implements SqlSession {
    // Strategy
    private Executor executor;
    @Deprecated
    public DefaultSqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
      this(configuration, executor);
    }
    
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        try {
           MappedStatement ms = configuration.getMappedStatement(statement);
           // 这里调用了 querey() 方法
           List<E> result = executor.query(ms, wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER);
           return result;
        } catch (Exception e) {
           throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
        } finally {
           ErrorContext.instance().reset();
        }
    }
}
```

# 附录
[回到主页](/README.md)&emsp;[案例代码](/cases-behavioral/src/main/java/com/patterns/strategy)


