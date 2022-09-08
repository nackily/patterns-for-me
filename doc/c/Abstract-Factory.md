## <center> 创建型 - 抽象工厂（Abstract Factory）设计模式
---

很多时候，我们不应该被一个看起来很复杂的名词或概念所绊倒，因为往往看起来越复杂的东西其本质越简单。就像是抽象工厂模式，他的名字看起来有点令人头大，又是抽象，又是工厂，如果是第一次了解很容易就被劝退了。但事实是，当你真正尝试去了解它时，你会觉得它并没有想象中那么复杂。

# 一、问题引入
为了对抽象工厂模式有一个深刻的印象，我们还是在例子中来正确打开抽象工厂模式。但在此之前，得确保你已经浏览过本系列中的工厂方法模式的文章，因为我们在本章的案例建立在工厂方法模式的基础之上。[可由此处进入工厂方法模式](/doc/c/Factory-Method.md)。

## 1.1 回顾工厂方法

在本系列的工厂方法模式的文章中，我们实现了这样的需求：

> 根据实际需要，将 Java 对象转换成通用的数据交互格式，并写入到磁盘中，支持 json、xml 数据格式。文件存储时，应指定一个 key，以 key 作为存储到磁盘时的名字。

在那篇文章中，我们通过一步步的阐述，使用工厂方法模式为系统进行了建模。得到的整个类图结构如下图所示：
<div align="center">
   <img src="/doc/resource/factory-method/案例类图.png" width="90%"/>
</div>

对该类图结构的概述如下：

- 所有的产品（`Saver`）都遵循了统一的规范，但又表现出不同的行为【重写了`AbstractFormatSaver.convert()`方法，不同的实现类把同一个对象转换成不同的格式】;
- 给每一个具体产品配备了一个具体的工厂，工厂负责构建具体的产品；
- 所有的工厂（`Factory`）都遵循了统一的规范【实现了`FormatSaveFactory.createSaver()`方法】

工厂方法模式使得各个产品相互独立，一个产品的调整不会影响到其他的产品定义以及构建过程（例如，对`XmlSaver`类代码的修改不会影响到`JsonSaver`和`JsonSaveFactory`）。当我们扩展一个新的产品时，只需要在现有结构中增加产品的实现类、产品的工厂实现类，同样不会对现有类中的代码产生影响。

## 1.2 保证产品相关性

在很多时候，我们期望的不只是将对象持久化到磁盘中，在另一些时候，我们还希望把磁盘中的数据加载到内存中进行处理。比如说，对一个内存占用高的系统来说，我们希望将那些暂时不用的对象从内存中释放掉，等真正需要这个对象时，我们再从磁盘中还原。基于这个出发点，我们对需求调整如下：

> 根据实际需要，将 Java 对象转换成通用的数据交互格式，并写入到磁盘中，支持 json、xml 数据格式。文件存储时，应指定一个 key，以 key 作为存储到磁盘时的名字。并且，在需要的时候，将存储到磁盘中的文件加载到内存中，还原成 Java 对象。

在上述需求中，并未对原来的需求进行变更，而是在原来的基础上进行了扩展了新的功能。除了支持将对象持久化到文件外，还要支持将文件中的数据还原成对象。这很简单，因为前半部分我们已经实现了，现在只需要依样画葫芦，照着前半部分的模型复刻后半部分就可以了。系统的完整类图如下所示。
<div align="center">
   <img src="/doc/resource/abstract-factory/案例解决方案一类图.jpg" width="80%"/>
</div>

在这个类图结构中，分为两部分，一部分是 Java 对象写入磁盘，为上半部分深色背景的结构；另一个是磁盘文件还原为 Java 对象，为下半部分浅色背景的结构。同`AbstractFormatSaver`一样，`AbstractFormatLoader`也提供了三个完全与之相反的方法：

- `loadAndResolve(key, type)`：加载文件到内存，并解析为对象；
- `load(obj)`：加载文件为 String 类型的字符串；
- `resolve(content, type)`：解析字符串为对象；

在这一结构下，客户端分别构建一个`JsonSaver`和一个`JsonLoader`即可实现对 json 文件格式的持久化以及还原。如下代码所示：
```java
// 持久化
FormatSaveFactory saveFactory = new JsonSaveFactory();
AbstractFormatSaver saver = saveFactory.createSaver();
saver.convertAndStore("key", toSaveObject);
// 还原
FormatLoadFactory loadFactory = new JsonLoadFactory();
AbstractFormatLoader loader = loadFactory.createLoader();
Object obj = loader.loadAndResolve("key", toSaveObject.getClass());
// ...
```

尽管这能实现需求并能很好的工作，但这里有一个隐藏的问题：_对于同一种格式来说，存储器和加载器应该是成对匹配的_。比如使用`JsonSaver`存储的文档，只能使用`JsonLoader`进行加载并解析；同理，`XmlLoader`也只能正确的加载并解析使用`XmlSaver`存储的文档。

我们无法约束客户端能严格遵循这样的行为准则，如果客户端错误的使用了组合就会导致无法正常的工作（例如，对于 json 格式，客户端错误的使用了`XmlLoader`进行加载解析）。就目前的实现方式而言，我们该如何保证客户端构建的多种类型产品的相关性？

很遗憾，就目前的这种实现方式而言，我们并不能做到。因为存储器（Saver）和加载器（Loader）是互相独立的，我们无法知道客户端在使用时采用了哪一种具体的组合，也就谈不上对组合的相关性进行约束。

# 二、解决方案
其实，我们只需要换一个角度看待这个问题，就能找到解决办法。既然客户端（工厂的使用方）并不知道怎样的搭配才算是正确的组合，但系统的构建者（工厂的开发者）知道。那我们为何不在系统中定义好这些成对的组合呢？

基于这个思路，我们只需要对系统的结构进行一个小的调整：将同一系列的产品的生产合并到一个工厂中实现。这样客户端就能通过一个工厂生产出具有相关性的产品，这些产品为同一系列，可以搭配使用。
<div align="center">
   <img src="/doc/resource/abstract-factory/案例类图.png" width="90%"/>
</div>

如上图所示，为每一个系列的产品提供一个工厂，该工厂即可生产这一系列的产品，区别于工厂方法模式只能生产单个产品。例如，xml 系列的产品，可以由`XmlFactory`生产，包括有`XmlSaver`和`XmlLoader`。客户端在使用时，只需要获取到具体的工厂，即可调用`FormatFactory`提供的生产方法，获取对应的产品。像上面类图中的结构，提供了一个对外的工厂接口，这个接口中定义了创建一系列产品的方法；而每种系列的工厂实现这个接口，负责创建这个系列的产品，这就是抽象工厂模式。

# 三、案例实现
在深入讨论抽象工厂模式之前，我们先对上面的案例进行实现。因为该案例引申自工厂方法模式中使用的案例，所以，部分代码（包括有`AbstractFormatSaver`、`JsonSaver`和`XmlSaver`）直接引用自工厂方法模式的代码。

**（1）格式存储器**

引用自工厂方法模式的案例代码，相关代码的链接已附录在文末。

**（1-1）抽象的数据格式存储器**
```java
public abstract class AbstractFormatSaver {

    /**
     * 文件存储格式
     */
    protected final String fileExtension;
    public AbstractFormatSaver(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * 转换格式并存储对象
     * @param key 键
     * @param obj 原始对象
     */
    public void convertAndStore(String key, Object obj) throws Exception {
        String formatContent = this.convert(obj);
        this.store(key, formatContent);
    }

    /**
     * 格式转换
     * @param obj 原始对象
     * @return 格式化后的字符串
     * @throws Exception Exception
     */
    protected abstract String convert(Object obj) throws Exception;

    /**
     * 内容写入文件
     * @param key 键 - 作为文件名
     * @param content 内容
     * @throws IOException IOException
     */
    protected void store(String key, String content) throws IOException {
        System.out.println("    即将开始写入文件");
        String directory = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        String filename = directory + key + this.fileExtension;
        // 写入文件
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        }
    }
}
```
**（1-2）JSON格式存储器**
```java
public class JsonSaver extends AbstractFormatSaver {

    private final ObjectMapper objectMapper;
    public JsonSaver(ObjectMapper objectMapper) {
        super(".json");
        this.objectMapper = objectMapper;
    }

    @Override
    protected String convert(Object obj) throws Exception {
        System.out.println("    即将开始转换对象为JSON格式");
        String tar = objectMapper.writeValueAsString(obj);
        System.out.println("        转换后内容：" + tar);
        return tar;
    }
}
```
**（1-3）XML格式存储器**
```java
public class XmlSaver extends AbstractFormatSaver {

    public XmlSaver() {
        super(".xml");
    }

    @Override
    protected String convert(Object obj) throws Exception {
        System.out.println("    即将开始转换对象为XML格式");
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 编码
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(obj, writer);
        String tar = writer.toString();
        System.out.println("        转换后内容：" + tar);
        return tar;
    }
}
```

**（2）对象加载器**

**（2-1）抽象的对象加载器**
```java
public abstract class AbstractFormatLoader {

    /**
     * 文件后缀
     */
    protected final String fileExtension;
    public AbstractFormatLoader(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * 加载并解析为对象
     * @param key 键 - 文件名
     * @param type 期待的对象类型
     * @param <T> 返回的类型
     * @return 还原的对象
     * @throws Exception Exception
     */
    public <T> T loadAndResolve(String key, Class<T> type) throws Exception {
        String context = this.load(key);
        return this.resolve(context, type);
    }

    /**
     * 加载文件为字符串
     * @param key 键 - 文件名
     * @return 字符串
     * @throws IOException IOException
     */
    protected String load(String key) throws IOException {
        System.out.println("    即将开始加载文件");
        String directory = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        String filename = directory + key + fileExtension;

        File file = new File(filename);
        try (Reader r = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);) {
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = r.read()) != -1) {
                sb.append((char) ch);
            }
            return sb.toString();
        }
    }

    /**
     * 解析为对象
     * @param content 字符串
     * @param type 期待的对象类型
     * @param <T> 返回的类型
     * @return 还原的对象
     * @throws Exception Exception
     */
    protected abstract <T> T resolve(String content, Class<T> type) throws Exception;
}
```
**（2-2）JSON对象存储器**
```java
public class JsonLoader extends AbstractFormatLoader {

    private final ObjectMapper objectMapper;

    public JsonLoader(ObjectMapper objectMapper) {
        super(".json");
        this.objectMapper = objectMapper;
    }

    @Override
    protected <T> T resolve(String content, Class<T> type) throws Exception {
        System.out.println("    即将开始解析JSON");
        T tar = objectMapper.readValue(content, type);
        System.out.println("        解析后内容：" + tar);
        return tar;
    }
}
```
**（2-3）XML格式存储器**
```java
public class XmlLoader extends AbstractFormatLoader {

    public XmlLoader() {
        super(".xml");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T resolve(String content, Class<T> type) throws Exception {
        System.out.println("    即将开始解析XML");
        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        T tar = (T) unmarshaller.unmarshal(stream);
        System.out.println("        解析后内容：" + tar);
        return tar;
    }
}

```

**（3）产品工厂**

**（3-1）系列产品工厂接口**
```java
public interface FormatFactory {

    /**
     * 生产存储器
     * @return AbstractFormatSaver
     */
    AbstractFormatSaver createSaver();

    /**
     * 生产加载器
     * @return AbstractFormatLoader
     */
    AbstractFormatLoader createLoader();

}

```
**（3-2）JSON系列产品工厂**
```java
public class JsonFactory implements FormatFactory{
    @Override
    public AbstractFormatSaver createSaver() {
        return new JsonSaver(new ObjectMapper());
    }

    @Override
    public AbstractFormatLoader createLoader() {
        return new JsonLoader(new ObjectMapper());
    }
}
```
**（3-3）XML系列产品工厂**
```java
public class XmlFactory implements FormatFactory{
    @Override
    public AbstractFormatSaver createSaver() {
        return new XmlSaver();
    }

    @Override
    public AbstractFormatLoader createLoader() {
        return new XmlLoader();
    }
}
```

**（4）客户端**

**（4-1）Client**
```java
public class Client {
    public static void main(String[] args) throws Exception {
        DTO dto = new DTO();
        dto.setName("tom");
        dto.setAge(60);
        System.out.println("|==> Start ---------------------------------------------------------------|");
        FormatFactory jsonFactory = new JsonFactory();
        AbstractFormatSaver jsonSaver = jsonFactory.createSaver();
        // 转换json并存储
        jsonSaver.convertAndStore("tom_json", dto);
        // 从磁盘加载并解析
        AbstractFormatLoader jsonLoader = jsonFactory.createLoader();
        jsonLoader.loadAndResolve("tom_json", DTO.class);

        FormatFactory xmlFactory = new XmlFactory();
        AbstractFormatSaver xmlSaver = xmlFactory.createSaver();
        // 转换格式并存储
        xmlSaver.convertAndStore("tom_xml", dto);
        // 从磁盘加载并解析
        AbstractFormatLoader xmlLoader = xmlFactory.createLoader();
        xmlLoader.loadAndResolve("tom_xml", DTO.class);
    }
}



// 测试使用的 DTO 对象
@XmlRootElement(name = "object")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTO {
    private String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```
**（4-2）运行结果**
```text
|==> Start ---------------------------------------------------------------|
    即将开始转换对象为JSON格式
    转换后内容：{"name":"tom","age":60}
即将开始写入文件
即将开始加载文件
即将开始解析JSON
    解析后内容：DTO{name='tom', age=60}
即将开始转换对象为XML格式
    转换后内容：<?xml version="1.0" encoding="UTF-8" standalone="yes"?><object><name>tom</name><age>60</age></object>
    即将开始写入文件
    即将开始加载文件
    即将开始解析XML
    解析后内容：DTO{name='tom', age=60}
```
运行生成的文件如下图所示：
<div align="center">
   <img src="/doc/resource/abstract-factory/运行结果.png" width="20%"/>
</div>

# 四、抽象工厂模式
## 4.1 意图
> **提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。**

结合着上面的案例，对于抽象工厂模式的意图解析如下：

- **提供一个创建一系列相关或相互依赖对象的接口**：抽象工厂模式强调的重点是创建一个系列的产品对象，何谓一个系列？就像是 xml 格式的存储器存储的文件，也只能使用 xml 格式的加载器进行解析，这两个产品是互为衬托的，所以他们具有很强的相关性，可以认为是一个系列；
- **无需指定它们具体的类**：回顾在案例实现中的客户端代码，客户端是如何得到产品对象的？例如，对于存储一个对象为 json 格式时，客户端只需要通过产品工厂接口提供的方法（`FormatFactory.createSaver()`）获取，获取的是实际是一个类型为`JsonSaver`的产品对象。但是客户端并未指定这个产品对象的类型，也不知道这个产品对象的实际类型，客户端仅知道这个产品的类型是`AbstractFormatSaver`而已。

## 4.2 类图分析
<div align="center">
   <img src="/doc/resource/abstract-factory/案例类图.png" width="80%"/>
</div>

抽象工厂模式的类图结构如上所示，其有如下的参与者列表：

- **AbstractProduct**：抽象的产品，在抽象工厂模式中分为多个系列的产品（AbstractProductA、AbstractProductB）；
- **ConcreteProduct**：具体的产品，分为同一个系列下的不同产品（ProductA_1、ProductB_1），也可分为不同系列下的同类产品（ProductA_1、ProductA_2）；
- **AbstractFactory**：抽象的工厂，定义创建同类产品对象的接口；
- **ConcreteFactory**：实现创建某个类型中具体产品对象的操作；
- **Client**：仅使用由 Factory 和 Product 类声明的接口。

# 五、深入
## 5.1 适用场景
总的来说，抽象工厂模式适用于需要强调一系列相关的产品对象的设计以便进行联合使用时。更加直白的阐述是：需要从一堆产品中筛选出具有相关性的那一个系列产品，因为客户端需要对这些产品进行组合使用。这里列举两个例子对适用场景进行说明。

> **场景Ⅰ** 当构建一个具有 UI 界面的客户端程序时，我们必不可少的会使用到 菜单、按钮、对话框等等组件。我们希望我的客户端程序跨平台，能支持 Linux、Windows、macOS等操作系统，就像浏览器一样。此时，我们可以使用抽象工厂模式来建模，为每一个平台提供一个工厂，该工厂负责构建与平台相关的组件（菜单、按钮、对话框等）。除此之外，系统还应在启动时根据当前所处平台环境加载与之对应的工厂。这样我们就能保证用户直接从工厂中获取组件，而不需要考虑这个组件是否和当前的平台适配，因为用户从工厂中获取到的组件都是严格匹配当前平台环境的。

> **场景Ⅱ** 很多软件都支持更改主题，比如 idea 就支持 Light、Darcula、High contrast 等主题。在主题切换时，变化的不仅仅是背景颜色，还有字体颜色等等。比如说在切换成 Light 后，主题是白底黑字，当我们使用 Darcula 时，又变成了黑底白字。这个场景也适合使用抽象工厂模式进行建模，因为每一个主题对应着特定背景颜色和特定的字体颜色，这两种颜色不能随意组合，就像是背景颜色是黑色时，字体颜色就不能是黑色。

## 5.2 使用技巧

**（1）尽量将工厂实现为单例**

工厂作为创建具体产品的媒介，往往不需要其他的外部状态，所以，我们可以将每一个具体的工厂实现为单个实例。

**（2）需要时，从配置中加载具体工厂**

有时候客户端只需要使用一个具体的工厂，此时，我们可以在应用程序初始化阶段加载具体的工厂。比如在上面构建 UI 界面的例子中，应用程序在启动时，操作系统就已经确定，此时，对于所有的实现工厂来说，只有与当前操作系统一致的那个工厂才具有实际意义。所以，我们可以在应用程序初始化时就根据当前的操作系统环境加载与之对应的工厂。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/creational/abstract_factory)

