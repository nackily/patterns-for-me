## <center> 行为型 - 解释器（Interpreter）设计模式
---

在所有的设计模式中，有一个模式曾经让我感到非常困惑，它就是解释器模式。老实说，解释器模式的类图结构并不复杂，但在《Design patterns- Elements of reusable object-oriented software》一书中对其隐晦深沉的描述却一度让我破防。所以，在本篇中，我决心抛开那些深奥难懂的理论知识，因为我们的最终目的仅是了解什么是解释器模式。

# 一、案例引入
为了让这个模式理解起来更简单，也为了不让这个过程变得枯燥，我仍然通过一个案例来说明解释器模式，这个案例在带有权限控制的后台管理系统中很常见。以下是关于这个案例的业务背景。

> 在 java-web 开发中，很多时候我们并不能将一个后台接口直接暴露给所有登录的用户。我们希望保证某些后台接口的安全，只对拥有权限的请求开放接口，并且拦截掉那些不具备权限的请求。此时，我们可以借助 spring aop 提供的特性，以自定义注解形式标注这个权限范围的限定，在请求进入接口前，检查权限。比如，对于罗列所有系统用户的列表接口来说，访问他的权限范围为：当前用户拥有管理员的角色。接口定义如下图所示：

<div align="center">
   <img src="/doc/resource/interpreter/权限校验的接口示例.png" width="60%"/>
</div>

这是可行的，我们只需要在请求进入这个接口之前，检查当前登录的用户，如果用户不具备管理员的角色，拦截掉这个请求即可。但真实情况是，系统中对于权限的划分，远比我们想象的复杂。我们发现，某些特定的接口在权限要求时往往呈现多样性的态势。

> 例如：系统提供了导出用户列表的功能，并且支持两种格式的导出，分别为 .xlsx 格式和 .docx 格式，这两个导出功能对应了页面中的两个按钮。并且，这两个按钮不是对所有用户都可见，只有当前用户具备按钮的权限时才能看见对应的按钮。由于这两个功能只是文档的格式不同，所以我们在后台仅提供了一个接口。所以，对于这个接口的权限控制应该是：用户拥有导出 xlsx 格式文档的按钮权限，或者用户拥有导出 docx 格式文档的按钮权限；除此之外，还允许拥有管理员角色的用户访问。

或许到此时我们才意识到这个问题的复杂性：我们不仅要面对各种类型的权限条件，而且还要正确处理各个条件之间组织关系。一方面来说，对于权限的控制不仅仅局限在角色这个范围内了，我们还需要考虑用户是否拥有某个页面元素（如菜单/按钮等）的访问权限；另一方面来说，我们应该正确的组织各个条件，比如表示同时满足两个条件的 and 关系，再比如表示满足其中一个条件即可的 or 关系等等。

> 1. 我们约定以` & `符号表示两个条件的 and 关系，以`|`符号表示两个条件的 or 关系；
> 2. 为每一个条件增加一个标签，依据这个标签可以对条件的类型进行区分。例如使用`R:`前缀表示角色权限（例如`R:admin`表示用户需具备管理员角色），使用`E:`前缀表示页面元素权限（例如：`E:system.user.export-to-xlsx`要求用户拥有系统管理菜单 -> 用户列表页面 -> 导出为 Excel 文档按钮的权限）；
> 3. 对于符号来说，我们还应考虑其在组织时的优先级，例如 `(c1 & c2) | (c3 & c4)` 表示要么同时满足 c1 和 c2，要么则同时满足 c3 和 c4。

按照这些规则，我们对于导出用户列表这个后台接口的权限范围表达式可以定义为：`(E:system.user.export-to-xlsx | E:system.user.export-to-docx) | R:admin`。此时，导出用户列表的接口定义如下所示：
<div align="center">
   <img src="/doc/resource/interpreter/复杂的权限校验示例.png" width="80%"/>
</div>

那么，我们该如何解析这个规则表达式？并且如何对一个规则表达式进行求解呢？

# 二、解决方案
解释器模式建议将这类问题看作是一个简单语言中的句子，我们可以构建一个解释器系统，解释器系统通过解释这些语句来处理问题。这个比喻不好理解，拆开来说：

> 在前面，我们构造了一些表示权限的表达式，他们的用途是为了标注访问一个接口应具备的权限。就像是人与人之间的沟通，我们通过语言交流的方式也是为了传递出自己的想法，所以从这一点上来看，这种标记权限的方式可以称呼为一个简单的语言系统。自然，标记不同的接口使用不同的权限表达式，就像我们在不同的场合下说出不同的语句，一个具体的权限表达式就等同于一个现实生活中的语句。
> 我们在构建这种语言时，定义了一些规则（比如如何表示 and / or 关系等），这些规则就是这种语言的语法。只要一个句子不是病句（严格遵循语法规则），我们就可以借助于查阅词典等方式，还原这个语句的语意。在这个阐述中，解释器模式就扮演着语句和语意之间的桥梁，负责对句子进行解释执行。

回顾我们定义的语法规则，其中包含了两类关于权限的表述：一类是某个具体的权限表达式（比如具备某个角色）；另一类是承上启下的组织关系（比如并且关系 ），这些组织关系由一些符号代替，这里就称呼其为符号表达式。当符号表达式在组织多个权限表达式之间的关系时，只需要先计算两个邻近的权限表达式结果，将这个结果作为一个新的权限表达式和其他的权限表达式进行组织，如此反复即可。而优先级则确定了哪些符号表达式先执行，优先级规定了求解顺序。所以，只要有了这两类表述，并且知晓各个符号表达式的优先级，就能对整个表达式进行解释求值。求值过程大致如下图所示：
<div align="center">
   <img src="/doc/resource/interpreter/案例执行流程.png" width="70%"/>
</div>

不管是权限表达式，还是符号表达式，都具有完全一致的行为：得到一个 boolean 类型的值，从业务意义上来说，表示当前用户是否具有某个（些）权限。解释器模式建议我们对两种表达式类型进行行为统一，提供更高层次的抽象，这个抽象定义了解释表达式（片段）自身的行为。综上所述，类图结构设计如下：

<div align="center">
   <img src="/doc/resource/interpreter/案例类图.png" width="90%"/>
</div>

> 如上类图所示，`Expression` 类为所有表达式类型的抽象，提供了鉴权的行为：`authenticate(String) boolean` ，参数为当前请求的用户 key。`PermissionExpression` 类为权限表达式，`type` 表示鉴权类型（如角色鉴权/页面元素鉴权），`auth` 表示权限 key（比如角色 admin、页面元素 system.user.export-to-docx等）。`AbstractSymbolExpression` 类为符号表达式，持有符号的前一个表达式对象和后一个表达式对象，分为 and 及 or 关系表达式。

如此一来，求解表达式就只需构造一个由各种表达式构建的解释器树即可。比如表达式`(E:system.user.export-to-xlsx | E:system.user.export-to-docx) | R:admin`，对应的解释器树如下所示：
<div align="center">
   <img src="/doc/resource/interpreter/案例的解释器树.jpg" width="70%"/>
</div>

# 三、案例实现
在前面我们已经讨论过案例的解决方案，并且对其解决问题的思路进行了详细的说明。这个案例实现代码如下。
**（1）表达式**

**（1-1）表达式接口**
```java
public interface Expression {

    /**
     * 鉴权
     * @param userKey 用户
     * @return 是否拥有权限
     */
    boolean authenticate(String userKey);
}
```
**（1-2）权限表达式**
```java
public class PermissionExpression implements Expression {

    /**
     * 权限值
     */
    private final String auth;

    /**
     * 类型
     */
    private final PermissionType type;

    public PermissionExpression(String auth, PermissionType type) {
        this.auth = auth;
        this.type = type;
    }

    @Override
    public boolean authenticate(String userKey) {
        switch (type) {
            case ROLE:
                List<String> roles = Simulation.getUserConfig(userKey).getRoles();
                return roles.contains(auth);
            case ELEMENT:
                List<String> elements = Simulation.getUserConfig(userKey).getElements();
                return elements.contains(auth);
            default:
                throw new RuntimeException("未知类型");
        }
    }
}
```
**（1-3）鉴权类型枚举**
```java
public enum PermissionType {

    /**
     * 角色鉴权
     */
    ROLE("R:"),

    /**
     * 页面元素鉴权
     */
    ELEMENT("E:");

    final String key;

    PermissionType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public PermissionType fromKey(String key) {
        return Arrays.stream(PermissionType.values())
                .filter(item -> item.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }
}
```

**（2）符号表达式**

**（2-1）抽象的符号表达式**
```java
public abstract class AbstractSymbolExpression implements Expression {

    /**
     * 符号前面的权限表达式
     */
    protected final Expression prev;

    /**
     * 符号后面的权限表达式
     */
    protected final Expression next;

    public AbstractSymbolExpression(Expression prev, Expression next) {
        this.prev = prev;
        this.next = next;
    }

    /**
     * 多个权限表达式鉴权
     * @param userKey 用户
     * @return 是否拥有权限
     */
    @Override
    public abstract boolean authenticate(String userKey);
}
```
**（2-2）'且'表达式**
```java
public class AndExpression extends AbstractSymbolExpression {

    public AndExpression(Expression prev, Expression next) {
        super(prev, next);
    }

    @Override
    public boolean authenticate(String userKey) {
        return super.prev.authenticate(userKey)
                && super.next.authenticate(userKey);
    }
}
```
**（2-3）'或'表达式**
```java
public class OrExpression extends AbstractSymbolExpression {

    public OrExpression(Expression prev, Expression next) {
        super(prev, next);
    }

    @Override
    public boolean authenticate(String userKey) {
        return super.prev.authenticate(userKey)
                || super.next.authenticate(userKey);
    }
}
```

**（3）模拟用户权限配置**
```java
public class Simulation {

    private final static List<UserPermissionConfig> CONFIGS = new ArrayList<>();
    static {
        CONFIGS.add(new UserPermissionConfig("tom",
                Arrays.asList("warehouse-manager", "salesperson"),
                Arrays.asList("system.user.query", "system.user.export-to-xlsx", "system.user.export-to-docx")));
        CONFIGS.add(new UserPermissionConfig("lisa",
                Collections.singletonList("warehouse-manager"),
                Arrays.asList("system.user.query", "sys.user.detail")));
        CONFIGS.add(new UserPermissionConfig("jack",
                Arrays.asList("salesperson", "admin"),
                Collections.emptyList()));
    }

    public static UserPermissionConfig getUserConfig(String userKey) {
        return CONFIGS.stream()
                .filter(item -> item.getKey().equals(userKey))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未知用户"));
    }

    /**
     * 用户权限配置
     */
    public static class UserPermissionConfig {
        private final String key;                                         // 用户key
        private final List<String> roles;                                 // 用户拥有的角色
        private final List<String> elements;                              // 用户拥有的资源
        public UserPermissionConfig(String key, List<String> roles, List<String> elements) {
            this.key = key;
            this.roles = roles;
            this.elements = elements;
        }
        public String getKey() {
            return key;
        }
        public List<String> getRoles() {
            return roles;
        }
        public List<String> getElements() {
            return elements;
        }
    }
}
```

**（4）客户端**

**（4-1）Client**
```java
public class Client {
    public static void main(String[] args) {
        System.out.println("|==> Start-------------------------------------------------------------------------------|");
        System.out.println("    访问接口[api1]所需权限：(E:system.user.export-to-xlsx | E:system.user.export-to-docx) | R:admin");

        Expression expression1 = buildExpressionForApi1();
        String currentUerKey = "lisa";
        boolean result1 = expression1.authenticate(currentUerKey);
        System.out.println(MessageFormat.format("       用户[{0}]访问该接口时鉴权[{1}]",
                currentUerKey,
                result1 ? "成功" : "失败"));

        currentUerKey = "jack";
        boolean result2 = expression1.authenticate(currentUerKey);
        System.out.println(MessageFormat.format("       用户[{0}]访问该接口时鉴权[{1}]",
                currentUerKey,
                result2 ? "成功" : "失败"));

        System.out.println("    访问接口[api2]所需权限：E:sys.user.detail & R:warehouse-manager");

        Expression expression2 = buildExpressionForApi2();
        currentUerKey = "lisa";
        boolean result3 = expression2.authenticate(currentUerKey);
        System.out.println(MessageFormat.format("       用户[{0}]访问该接口时鉴权[{1}]",
                currentUerKey,
                result3 ? "成功" : "失败"));

        currentUerKey = "tom";
        boolean result4 = expression2.authenticate(currentUerKey);
        System.out.println(MessageFormat.format("       用户[{0}]访问该接口时鉴权[{1}]",
                currentUerKey,
                result4 ? "成功" : "失败"));
    }

    public static Expression buildExpressionForApi1() {
        // 权限表达式
        Expression exportToXlsx = new PermissionExpression("system.user.export-to-xlsx", PermissionType.ELEMENT);
        Expression exportToWord = new PermissionExpression("system.user.export-to-docx", PermissionType.ELEMENT);
        Expression adminRole = new PermissionExpression("admin", PermissionType.ROLE);
        // 符号表达式
        AbstractSymbolExpression xlsxOrWord = new OrExpression(exportToXlsx, exportToWord);
        return new OrExpression(xlsxOrWord, adminRole);
    }

    public static Expression buildExpressionForApi2() {
        // 权限表达式
        Expression detail = new PermissionExpression("sys.user.detail", PermissionType.ELEMENT);
        Expression manager = new PermissionExpression("warehouse-manager", PermissionType.ROLE);
        // 符号表达式
        return new AndExpression(detail, manager);
    }
}
```
**（4-1）运行结果**
```text
|==> Start-------------------------------------------------------------------------------|
    访问接口[api1]所需权限：(E:system.user.export-to-xlsx | E:system.user.export-to-docx) | R:admin
       用户[lisa]访问该接口时鉴权[失败]
       用户[jack]访问该接口时鉴权[成功]
    访问接口[api2]所需权限：E:sys.user.detail & R:warehouse-manager
       用户[lisa]访问该接口时鉴权[成功]
       用户[tom]访问该接口时鉴权[失败]
```

# 四、解释器模式
## 4.1 意图
> **给定一种语言， 定义它的文法的一种表示  ，以及一个解释器，这个解释器使用这个表示来解释该语言中的句子。**

对于解释器模式的意图描述可以说是相当抽象了，我们尝试用上面的例子对其对照剖析：

- **给定一种语言**：就像前面说的，我们创造了一种语言，这种语言用来描述用户在访问后台接口时应具备的权限；
- **文法的一种表示**：指实际的表达式，比如 `E:system.user.query & R:admin`，再比如 `R:manager | R:seller`都是文法的表示；
- **一个解释器**：指的是各种类型表达式对象的组合以及嵌套；
- **解释器使用这个表示来解释该语言的句子**：解释器将一个完整的句子分成各个小部分，逐个进行解释处理，最终完成一个完整句子的解释。

## 4.2 通用类图分析
<div align="center">
   <img src="/doc/resource/interpreter/经典解释器模式类图.jpg" width="60%"/>
</div>

解释器模式的通用类图结构如上图所示，在解释器模式中主要有如下参与者角色：

- **Expression**：表达式。声明一个抽象的解释操作；  
- **TerminalExpression**：终结符表达式。终结符代表不能继续向下递归的表达式，比如权限表达式，本身即代表了一个确定的布尔值，无法继续细化；
- **NonterminalExpression**：非终结符表达式。非终结符相当于终结符而言，代表需要继续向下递归求解的表达式（比如 and 关系表达式，并不能直接求解，需要先对前后的表达式分别求解）；
- **Client**：负责构建（或被给定）一个特定语句的抽象语法树，该抽象语法树由** **TerminalExpression 和 NonterminalExpression 的实例嵌套组合而成。

## 4.3 特点
**（1）易于扩展**

解释器模式使用类来表示规则，新增一个规则只需要新增一个表达式类即可。比如需要一个连接两个权限表达式，组织方式为异或关系时，只需要新增一个 XorExpression 类，并在合适的地方使用他即可。

**（2）复杂的语言难以维护**

如果一种语言极其负责，规则数不胜数，那么我们解释这门语言时就不得不提供多个类。这无疑加大了维护的挑战，此时，放弃解释器模式是一种明智的选择。如果有需要，语法分析器或者编译器生成器应该更加适合这类场景。

## 4.4 使用技巧
**（1）创建抽象语法树**

在解释器模式中，我们并未提及如何构建一个抽象的语法树，这意味着解释器模式并不负责语法分析。抽象语法树可以用数据库表驱动的方式来生成，也可以采用语法分析程序（比如递归下降法）构建，甚至，可以由客户端直接构建（例如，`Client.buildExpressionForApi1()` 方法）。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/behavioral/interpreter)

