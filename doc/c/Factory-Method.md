## <center> 行为型 - 工厂方法（Factory Method）设计模式
---

我们都知道设计模式实际上是一些指导思想，这些指导思想是由前人总结和提炼出来的，主要目的是为了解决在代码设计和维护时暴露出来的问题。这些问题往往围绕着耦合性、扩展性等展开。可以说，一种设计模式的诞生就是为了解决一类特定的问题。正如本篇文章所讨论的工厂方法模式，就是为了解决实际问题而存在。下面将通过一个例子来介绍工厂方法模式是如何诞生的。

# 一、问题引入
有如下需求：

> 根据实际需要，将 Java 对象转换成通用的数据交互格式，并写入到磁盘中，支持 json、xml 数据格式。文件存储时，指定一个 key，以 key 作为存储到磁盘时的名字。

## 1.1 抽象产品

根据面向对象设计原则，我们很容易想到：

1. 给每一种数据交互格式提供一个数据格式存储类，用抽象的存储器来约束规范所有的转换器实现；
1. 在抽象的转换器中提供一个模板方法，该模板方法中主要包含两个步骤：①-转换格式（转换格式由具体的转换器负责实现），②-存储数据到磁盘（存储数据到磁盘为通用的功能，可在抽象类中定义默认的实现，具体的转换器可根据实际决定是否重写）。

类图如下所示：
<div align="center">
   <img src="/doc/resource/factory-method/案例之存储器结构.png" width="40%"/>
</div>

将存储器看作是产品，那么客户端在需要时根据需求创建具体的产品。例如将对象转换为json格式并存储在磁盘的代码如下所示：
```java
public class Client {
    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 23);
        AbstractFormatSaver formatSaver = new JsonSaver(new ObjectMapper());
        formatSaver.convertAndStore("key", map);
    }
}
```

至此，我们已经实现了各个存储器之间的相互隔离，且用[模板方法模式](/doc/b/Template-Method.md)来封装各个存储器之间相同的部分（文件存储），扩展不同的部分（数据格式的转换）。我们通过抽象存储过程，为所有的存储器定义了统一的行为方法，由不同的存储器决定不同的行为。但同时我们也注意到了以下两个问题：

> - 客户端在使用`JsonSaver`时，还创建了一个对客户端来说并不关心的依赖对象（`com.fasterxml.jackson.databind.ObjectMapper`）；
> - 如果需要格式转换的地方不止一处，那就必然要在各个需要的地方都写上创建`AbstractFormatSaver`的代码。

有些时候，上面的两个问题将变得不能忍受。如果客户端对某些依赖（`com.fasterxml.jackson.databind.ObjectMapper`）一无所知时，客户端不得不去了解并且正确构造这些依赖的对象，这些细节本来对客户端来说是可以避免的。如果客户端在大量的地方都使用了同样的方式构造了同样的产品（例如： json 格式存储器），未来如果产品的构建方式一旦发生修改，或许我们不得不面临修改所有使用的地方。

## 1.2 屏蔽产品构建细节
为了解决这两个问题，我们可以引入静态工厂来将具体存储器创建的细节进行封装。既然客户端不关心产品的创建细节，也不在乎产品的组成部分有哪些，那么我们就把产品的构建过程独立出来。用一个类方法单独处理产品的创建，根据参数决定创建产品的种类。如下代码所示：

```java
public class SimpleSaverFactory {
    public static AbstractFormatSaver createFactory(String type) {
        switch (type) {
            case "json":
                return new JsonSaver(new ObjectMapper());
            case "xml":
                return new XmlSaver();
            default:
                throw new RuntimeException("不支持的类型");
        }
    }
}
```

因为这个类专用于生产具体的产品，所以可形象的称呼为工厂；通常情况下，这个方法是静态方法，所以称呼为静态工厂。在静态工厂中，我们根据参数的值决定生产的产品的类型。下面的代码则演示了如何使用静态工厂。
```java
public class Client {
    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 23);
        
        // AbstractFormatSaver formatSaver = new JsonSaver(new ObjectMapper());
        AbstractFormatSaver formatSaver = SimpleSaverFactory.createFactory("json");
        formatSaver.convertAndStore("key", map);
    }
}
```

自此，客户端可根据静态方法生产具体的产品，只需要给定相应的参数，屏蔽了产品生产的细节。这样看起来已经足够优雅了，但在代码服役了一段时间后，我们接到了这样的一个需求：

> 现在系统需要增加一个 csv 数据格式的实现，且将来系统会提供更多数据格式的支持。

按照上面的结构，我们需要增加一个`AbstractFormatSaver`的实现类，实现将对象的格式转换为 csv 格式。并在静态工厂中增加一个条件分支，在条件分支出处理 csv 格式存储器的构建。

我们在添加一种数据格式的支持（产品）时，不仅要添加产品实现类，而且要在静态方法中增加一个新的条件分支。添加新的产品实现类是新建一个类，但是在静态工厂中增加一个新的条件分支势必有一定可能影响到现有的逻辑，这是我们不希望的。那有没有办法不用修改现有类的代码呢？

## 1.3 改变产品生产模式
要解决上面的问题，先得搞清楚一个问题：静态工厂为什么添加一个新的产品就必须要改变静态方法？

> 因为产品的生产都是在工厂实现的，而工厂只有一个，这就导致这个工厂就像是集散中心，所有的产品不管来自于哪里，都得经过这个集散中心的周转，正是因为这，才导致我们添加一个新产品类型的同时不得不在静态方法中为这个新产品适配一个新的生产方式。

明白了这一点，我们就知道如何调整来达到我们的目的了。添加新产品类型和为新产品配置一个新的生产方式是必然的，因为产品的生产方式和产品总是相关联的。我们只需要稍微改变一下 —— 把产品由集中创建的模式改为为每个产品配置一个工厂，耦合自然也就解开了。

# 二、解决方案
按照我们上面分析的思路，为每一个产品单独配置一个工厂，这样客户端就不需要再通过集中生产的方式来生产产品，这就是工厂方法模式。在工厂方法模式中，客户端在添加新的产品种类时，我们只需要告诉其所需新产品对应的工厂名字就可以生产新的产品对象。

在上面的讨论中，我们已经为每个产品提供了一个工厂，而所有的工厂都是在干同样的事情——生产产品，所以，可以用一个抽象的工厂来约束所有的工厂实现。

## 2.1 案例类图
按着上面的分析进行实现，我们得到如下的类图结构：
<div align="center">
   <img src="/doc/resource/factory-method/案例类图.png" width="90%"/>
</div>

在工厂接口（`FormatSaveFactory`）中，定义了一个创建产品的行为（`createSaver()`）。对于每一种具体的产品（例如`JsonSaver`），提供一个与之对应的工厂（例如`JsonSaveFactory`）。在这样的结构中，新增一种产品类型，我们只需要新增一个产品实现类，和一个匹配的产品工厂即可。无需改动现有的类。

> 例如新增 csv 数据格式的支持，我们需要新增一个产品的实现类（`CsvSaver`），并为该产品配备一个工厂（`CsvSaveFactory`）即可。客户端在需要新产品时，只需要`AbstractFormatSaver csvSaver = new CsvSaveFactory().createSaver();`即可生产出具体产品。

## 2.2 回顾
我们从最开始的需求着手，一步一步的引入了工厂方法模式来解决实际问题。在此期间，我们共经历了三个阶段：

- **第一阶段 -> 第二阶段**：通过封装产品生产的过程，来对客户端屏蔽产品生产时的细节，这让整个设计对客户端更加友好，毕竟客户端并不关心工厂是如何生产产品的，客户端只需要获取一个产品；
- **第二阶段 -> 第三阶段**：通过改变产品的生产模式进行解耦，将原来集中生产模式改变为一个产品配备一个工厂，一个工厂只能生产一种产品，客户端根据实际需要选择工厂就能得到产品，这个演变过程主要是为了得到更好的扩展性，保证在新增产品种类时，不影响到现有产品的生产过程。

# 三、案例实现
接下来，我们就实现一下上面的解决方案。

**（1）抽象产品**
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
**（2）具体产品**

**（2-1）JSON格式存储器**
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
**（2-2）XML格式存储器**
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
**（3）抽象工厂**
```java
public interface FormatSaveFactory {

    /**
     * 生产存储器
     * @return AbstractFormatSaver
     */
    AbstractFormatSaver createSaver();
}
```
**（4）具体工厂**

**（4-1）JSON格式存储器工厂**
```java
public class JsonSaveFactory implements FormatSaveFactory {

    @Override
    public AbstractFormatSaver createSaver() {
        return new JsonSaver(new ObjectMapper());
    }
}
```
**（4-2）XML格式存储器工厂**
```java
public class XmlSaveFactory implements FormatSaveFactory {

    @Override
    public AbstractFormatSaver createSaver() {
        return new XmlSaver();
    }
}
```
**（5）客户端**

**（5-1）Client**
```java
public class Client {
    public static void main(String[] args) throws Exception {
        DTO dto = new DTO();
        dto.setName("Jack");
        dto.setAge(23);
        System.out.println("|==> Start ---------------------------------------------------------------|");
        AbstractFormatSaver jsonSaver = new JsonSaveFactory().createSaver();
        jsonSaver.convertAndStore("Jack_json", dto);

        AbstractFormatSaver xmlSaver = new XmlSaveFactory().createSaver();
        xmlSaver.convertAndStore("Jack_xml", dto);
    }
}



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
**（5-2）运行结果**
```text
|==> Start ---------------------------------------------------------------|
    即将开始转换对象为JSON格式
        转换后内容：{"name":"Jack","age":23}
    即将开始写入文件
    即将开始转换对象为XML格式
        转换后内容：<?xml version="1.0" encoding="UTF-8" standalone="yes"?><object><name>Jack</name><age>23</age></object>
    即将开始写入文件
```
生成的文件如下图所示：
<div align="center">
   <img src="/doc/resource/factory-method/运行结果.png" width="20%"/>
</div>

# 四、工厂方法模式
## 4.1 意图

> **定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类。**

对于该意图的解析如下：

- **定义一个用于创建对象的接口**：工厂接口中的生产产品的方法；
- **让子类决定实例化哪一个类**：把产品的生产过程交给具体的工厂实现，各个具体的工厂生产具体的产品。

## 4.2 类图结构
<div align="center">
   <img src="/doc/resource/factory-method/经典工厂方法模式类图.jpg" width="50%"/>
</div>

工厂方法模式的类图结构如上图所示。在工厂方法模式中，有如下的角色列表：

- **Product**：抽象产品，定义工厂方法所创建的对象的接口；
- **ConcreteProduct**：具体产品，实现 Product 接口；
- **Creator**：声明工厂方法，该方法返回一个 Product 类型的对象（ Creator 也可以定义一个工厂方法的缺省实现，它返回一个默认的 ConcreteProduct 对象）；
- **ConcreteCreator**：重定义工厂方法以返回一个具体的 ConcreteProduct 实例。

# 五、从源码中看工厂方法模式

**（1）在 jdk 中，`java.util.Calendar`使用静态工厂来生产实例**

```java
    private static Calendar createCalendar(TimeZone zone,Locale aLocale){
        CalendarProvider provider =
            LocaleProviderAdapter.getAdapter(CalendarProvider.class, aLocale)
                                 .getCalendarProvider();
        if (provider != null) {
            try {
                // 1 ----------------------------------
                return provider.getInstance(zone, aLocale);
            } catch (IllegalArgumentException iae) {
            }
        }

        Calendar cal = null;

        if (aLocale.hasExtensions()) {
            String caltype = aLocale.getUnicodeLocaleType("ca");
            if (caltype != null) {
                switch (caltype) {
                case "buddhist":
                    // 2 ----------------------------------
                    cal = new BuddhistCalendar(zone, aLocale);
                    break;
                case "japanese":
                    // 3 ----------------------------------
                    cal = new JapaneseImperialCalendar(zone, aLocale);
                    break;
                case "gregory":
                    // 4 ----------------------------------
                    cal = new GregorianCalendar(zone, aLocale);
                    break;
                }
            }
        }
        if (cal == null) {
            if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
                // 5  ----------------------------------
                cal = new BuddhistCalendar(zone, aLocale);
            } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja"
                       && aLocale.getCountry() == "JP") {
                // 6 ----------------------------------
                cal = new JapaneseImperialCalendar(zone, aLocale);
            } else {
                // 7  ----------------------------------
                cal = new GregorianCalendar(zone, aLocale);
            }
        }
        return cal;
    }
```

**（2）在 spring 中，`org.springframework.beans.factory.FactoryBean`使用工厂方法`getObject()`来生产对象实例**

```java
public interface FactoryBean<T> {
    /**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）......
	 * @return an instance of the bean (can be {@code null})
	 * @throws Exception in case of creation errors
	 * @see FactoryBeanNotInitializedException
	 */
	@Nullable
	T getObject() throws Exception;
    
    @Nullable
	Class<?> getObjectType();
    
    default boolean isSingleton() {
		return true;
	}
}
```

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/creational/factory_method)
