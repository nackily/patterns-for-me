## <center> 行为型 - 模板方法（Template Method）设计模式
---

模板方法模式是一个相对简单，并且容易理解的模式。我们将通过一个例子来介绍模板方法模式。

# 一、问题引入
我的身份证快要到期了，正好前两年在这个城市买了房，户口也一直没有迁移过来，所以前几天我就去了一趟公安局迁移户口，并且用新的户口办了一张新的身份证。 往城市里迁移户口是需要具备一些条件的，比如说大学本科学历入户、投靠亲属入户、住房入户等等。只有满足了这些条件中的一种才具备往城市迁移户口的资格。按照我们多年面向对象的经验来说，我们会设计 学历入户类、投靠亲属类、住房入户类等。

另外公安局办理身份证需要一些流程，大致分为：准备材料，包括填写入户申请表等、材料审核、业务办理、原始户口迁出、迁入新户口这样一些流程。好了，这下我们就给每个类创建具体的流程，包括上面所有的步骤。

然后我们发现：有些步骤与当前是什么入户类型并无关系，也就是说这些步骤都是以同样的代码出现在每个类中。因为每种类型都要经历同样的流程步骤，区别在于有些阶段准备的材料可能不一样。比如说，学历入户类型至少需要准备学历证书，投靠亲属类型需要准备亲属的户口簿、身份证，而住房入户则需要提供房产证等。除此之外，如业务办理、原始户口迁出、迁入新户口这些步骤都是一致的。那么，我们该如何有效的解决步骤中代码重复的问题呢？

# 二、解决方案
> **封装不变部分，扩展可变部分。**

模板方法模式建议我们可以把所有的步骤及先后顺序声明在一个高层类（超类）中，在高层类中定义好流程的基本算法骨架，针对某些与子类无关的步骤提供缺省的实现。对于那些与子类相关的步骤，我们将其定义为抽象方法，让各个类型自己提供差异化的实现。按照这个的原则，我们对该例子建模如下：
<div align="center">
   <img src="/doc/resource/template-method/案例类图.png" width="60%"/>
</div>

对于该类图的类说明如下：

- `**AbstractDomicile**`：抽象的户籍地。定义了迁移户口（`migrate()`）的行为，该方法就是模板方法，在该方法中定义了整个流程的基本骨架，包括准备基本材料（`prepareBasicMaterials()`）、准备附加材料（`additionalMaterials()`）、办理户口迁移（`doBusiness()`）、发放新的户口簿（`grantCertificate()`）等行为。除此之外，`doCheck()`一个钩子方法，表示对部分材料进行审查（比如学历入户方式，需要提供的学历证明不低于大学本科，而其他类型的入户方式则没有这个流程）；
- `**RelativesEntryDomicile**`：亲属关系入户；
- `**HouseEntryDomicile**`：房产入户；
- `**EducationEntryDomicile**`：学历入户；

# 三、案例代码
**（1）模板类**
```java
public abstract class AbstractDomicile {
    private final String username;
    public AbstractDomicile(String username) {
        this.username = username;
    }

    /**
     * 迁移户口
     */
    public final void migrate () {
        System.out.println(MessageFormat.format("||--> migrate domicile for {0} ------------------------------------|", this.username));
        this.prepareBasicMaterials();
        this.additionalMaterials();
        this.doCheck();
        this.doBusiness();
        this.grantCertificate();
    }

    /**
     * 准备基本材料
     */
    private void prepareBasicMaterials(){
        System.out.println("    应准备好当前的身份证、原始户口簿、入户申请表");
    }

    /**
     * 准备附加材料
     */
    protected abstract void additionalMaterials();

    /**
     * 钩子方法，有些落户方式需要检查
     */
    protected void doCheck(){}

    /**
     * 办理业务
     */
    private void doBusiness(){
        System.out.println("    提交资料，由工作人员审核及办理");
        System.out.println("        户口已迁出");
        System.out.println("        已迁入新户口");
    }

    /**
     * 拿证
     */
    private void grantCertificate(){
        System.out.println("    发放新户口簿");
    }
}
```
**（2）具体实现类**

**（2-1）学历入户类**
```java
public class EducationEntryDomicile extends AbstractDomicile {
    public EducationEntryDomicile(String username) {
        super(username);
    }

    @Override
    protected void additionalMaterials() {
        System.out.println("    还应准备好：学历证书、学位证书");
    }

    @Override
    protected void doCheck() {
        System.out.println("    查验证书是否有效，并且学历至少要求为大学本科学历");
    }
}
```
**（2-2）住房落户类**
```java
public class HouseEntryDomicile extends AbstractDomicile {
    public HouseEntryDomicile(String username) {
        super(username);
    }

    @Override
    protected void additionalMaterials() {
        System.out.println("    还应准备好：房产证书");
    }
}
```
**（2-3）投靠亲属入户类**
```java
public class RelativesEntryDomicile extends AbstractDomicile {
    public RelativesEntryDomicile(String username) {
        super(username);
    }

    @Override
    protected void additionalMaterials() {
        System.out.println("    还应准备好：亲属的身份证、户口簿");
    }
}
```
**（3）客户端**

**（3-1）Client**
```java
public class Client {
    public static void main(String[] args) {
        AbstractDomicile domicile4Tom = new EducationEntryDomicile("Tom");
        domicile4Tom.migrate();
        AbstractDomicile domicile4Jack = new HouseEntryDomicile("Jack");
        domicile4Jack.migrate();
        AbstractDomicile domicile4Lisa = new RelativesEntryDomicile("Lisa");
        domicile4Lisa.migrate();
    }
}
```
**（3-2）运行结果**
```text
||--> migrate domicile for Tom ------------------------------------|
    应准备好当前的身份证、原始户口簿、入户申请表
    还应准备好：学历证书、学位证书
    查验证书是否有效，并且学历至少要求为大学本科学历
    提交资料，由工作人员审核及办理
        户口已迁出
        已迁入新户口
    发放新户口簿
||--> migrate domicile for Jack ------------------------------------|
    应准备好当前的身份证、原始户口簿、入户申请表
    还应准备好：房产证书
    提交资料，由工作人员审核及办理
        户口已迁出
        已迁入新户口
    发放新户口簿
||--> migrate domicile for Lisa ------------------------------------|
    应准备好当前的身份证、原始户口簿、入户申请表
    还应准备好：亲属的身份证、户口簿
    提交资料，由工作人员审核及办理
        户口已迁出
        已迁入新户口
    发放新户口簿
```

# 四、模板方法模式
## 4.1 意图
> **定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法模式使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。**

结合上述案例，我们再来理解模板方法模式的意图：

- **定义一个操作中的算法的骨架**：算法相当于整个迁移户口的工作流程，算法的骨架就是指一连串操作的调用链。就像是迁移户口需要先准备材料、然后材料审核、紧接着业务办理、最后再迁出原始户口、迁入新户口，将这一连串的操作按照先后顺序排列进行就是定义算法的骨架；
- **将一些步骤延迟到子类中**：比如上面的准备材料阶段，这部分实现在子类中，也就是扩展可变的部分；
- **使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤**：算法的基本机构（有哪些步骤、先后顺序）是在超类中定义的，子类无需改变，子类关心的是实现整个算法中特定的步骤（准备材料）。

## 4.2 通用类图
模板方法模式的通用类图结构如下所示：
<div align="center">
   <img src="/doc/resource/template-method/经典模板方法模式类图.jpg" width="30%"/>
</div>

模板方法模式是代码复用的一种极有效的手段，通过复用一些公共的代码，使得公用的部分变得更容易管理。一般，我们将定义算法的基本骨架的这个方法叫做**模板方法**（`AbstractClass#templateMethod()`），将抽象的方法称为**原语操作**（`AbstractClass#abstractMethod1()`、`AbstractClass#abstractMethod2()`）。

## 4.3 使用小技巧

**（1）将模板方法声明为 final**

模板方法定义了一个算法的骨架，这个骨架包含了相对固定的一些操作，不应由子类重写。所以，如果不希望你的模板方法被子类改变，可以将模板方法声明为 final，这样子类就没有办法改写这个模板方法所定义的算法。

**（2）利用好钩子方法**

事实上，在模板方法中还有一类更为出名的方法：`钩子方法`。钩子方法描述了这样的一种思想：超类中提供一个空的（或者默认的缺省行为）方法，当子类认为有必要替换这一行为的时候，可以重写这个钩子方法。钩子方法和原语操作有一定的相似性，例如他们都提供了给子类扩展的手段，但他们并不能划等号。对于原语操作每个子类都应该提供各自的实现，而钩子方法则是根据需要决定是否应该重写。上面例子中的 doCheck() 方法就是一个典型的钩子方法，当需要时，可重写钩子方法，以便在特定的步骤中插入特定的实现。

# 五、从源码中看模板方法模式
在源码经常都能见到模板方法的影子，这里举两个例子。

**（1）在 jdk 中的应用**

```java
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    // 省略其他代码
    public boolean add(E e) {
        add(size(), e);
        return true;
    }
    
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }
}
```
在 add(E e) 中定义了添加元素调用的算法，而 add(int index, E element) 是一个应由子类实现的方法。

**（2）在 Mybatis 中的应用**

```java
public abstract class BaseExecutor implements Executor {
    // 省略其他代码
    public List<BatchResult> flushStatements(boolean isRollBack) throws SQLException {
        if (this.closed) {
            throw new ExecutorException("Executor was closed.");
        } else {
            return this.doFlushStatements(isRollBack);
        }
    }
    
    protected abstract List<BatchResult> doFlushStatements(boolean var1) throws SQLException;
    
}
```
在 BaseExecutor 中，刷新语句的方法`flushStatements()`定义了算法，是模板方法；在该方法中，调用了抽象方法`doFlushStatements()`，让真正的刷新语句操作延迟到子类执行，是一个标准的模板方法模式的应用。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/behavioral/template_method)
