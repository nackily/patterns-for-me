> Once you understand the design patterns and have had an "Aha!" (and not just a "Huh?") experience with them, you won't ever think about object-oriented design in the same way.
> 
> 一旦你理解了设计模式，并且有了一种“妙啊！”的体验（相较于之前的“为啥？”），此时你已打开一扇全新的面向对象设计思维方式的大门。
## 项目介绍

设计模式之入门宝典（Java版），包含《Design patterns- Elements of reusable object-oriented software》一书中提及的 23 种设计模式，结合真实的生活场景，对每一种设计模式进行剖析。


## 开始学习

对于每一种设计模式，提供一篇在线文档及文档中的示例代码。除此之外，对于那些容易混淆的模式，另外提供单独的文档对他们对比、区分及总结。<br>

## 【S】结构型模式

### 【S-1】适配器-Adaptor
> **流行指数**：★★★★★
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：转接头、不同国家的标准电压不一样
> <br>**在线文档**：将一个类的接口转换成客户希望的另外一个接口，适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作...[**查看更多**][adaptor_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/structural/adapter)跳转。
> <br>

### 【S-2】桥-Bridge
> **流行指数**：★★☆☆☆
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：拱桥形状、解耦多个维度
> <br>**在线文档**：当需要从多个维度对一个对象进行扩展时，我们可以使用桥模式来让各个维度分离，进而实现各自独立的变化...[**查看更多**][bridge_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/structural/bridge)跳转。
> <br>

### 【S-3】组合-Composite
> **流行指数**：★★★★☆
> <br>**难度等级**：★★☆☆☆
> <br>**助记关键字**：树形结构
> <br>**在线文档**：如果一个应用的核心模型是树形结构，那么我们就能用组合模式来表示它，组合模式就是为树形结构量身定制的...[**查看更多**][composite_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/structural/composite)跳转。
> <br>

### 【S-4】装饰器-Decorator
> **流行指数**：★★★★★
> <br>**难度等级**：★★★★☆
> <br>**助记关键字**：俄罗斯套娃
> <br>**在线文档**：在不改变原有对象结构的基础情况下，动态地给该对象增加一些额外功能的职责...[**查看更多**][decorator_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/structural/decorator)跳转。
> <br>

### 【S-5】门面-Facade
> **流行指数**：★★★☆☆
> <br>**难度等级**：★☆☆☆☆
> <br>**助记关键字**：景区检票口、银行业务接待员
> <br>**在线文档**：为子系统中的一组接口提供一个一致的界面，门面模式定义了一个高层接口，这个接口使得这一子系统更加容易使用...[**查看更多**][facade_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/structural/facade)跳转。
> <br>

### 【S-6】享元-Flyweight
> **流行指数**：★★☆☆☆
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：对象共享
> <br>**在线文档**：运用共享技术有效地支持大量细粒度的对象...[**查看更多**][flyweight_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/structural/flyweight)跳转。
> <br>

### 【S-7】代理-Proxy
> **流行指数**：★★★★★
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：全局唯一、工具类
> <br>**在线文档**：代理模式为其他对象提供一种代理以控制对这个对象的访问...[**查看更多**][proxy_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/structural/proxy)跳转。
> <br>


## 【B】行为型模式

### 【B-1】责任链-Chain Of Responsibility
> **流行指数**：★★☆☆☆
> <br>**难度等级**：★★★★★
> <br>**助记关键字**：过滤器
> <br>**在线文档**：使多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系。将这些对象连成一条链，并沿着这条链传递该请求，直到有一个对象处理它为止...[**查看更多**][chain_of_responsibility_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/chain_of_responsibility)跳转。
> <br>

### 【B-2】命令-Command
> **流行指数**：★★★☆☆
> <br>**难度等级**：★★★★★
> <br>**助记关键字**：封装请求
> <br>**在线文档**：将一个请求封装为一个对象，从而使你可用不同的请求对客户进行参数化；对请求排队或记录请求日志，以及支持可撤消的操作...[**查看更多**][command_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/command)跳转。
> <br>

### 【B-3】解释器-Interpreter
> **流行指数**：★☆☆☆☆
> <br>**难度等级**：★★★★★
> <br>**助记关键字**：谷歌翻译
> <br>**在线文档**：给定一种语言， 定义它的文法的一种表示  ，以及一个解释器，这个解释器使用这个表示来解释该语言中的句子...[**查看更多**][interpreter_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/interpreter)跳转。
> <br>

### 【B-4】迭代器-Iterator
> **流行指数**：★★★★★
> <br>**难度等级**：★★☆☆☆
> <br>**助记关键字**：遍历
> <br>**在线文档**：提供一种方法顺序访问一个聚合对象中各个元素，而又不需暴露该对象的内部表示...[**查看更多**](/doc/behavioral/Iterator.md)
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/iterator)跳转。
> <br>

### 【B-5】中介者-Mediator
> **流行指数**：★☆☆☆☆
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：中介、消息队列
> <br>**在线文档**：用一个中介对象来封装一系列的对象交互。中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互...[**查看更多**](/doc/behavioral/Mediator.md)
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/mediator)跳转。
> <br>

### 【B-6】备忘录-Memento
> **流行指数**：★★☆☆☆
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：撤销、回滚
> <br>**在线文档**：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样以后就可以将该对象恢复到原先保存的状态...[**查看更多**][memento_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/memento)跳转。
> <br>

### 【B-7】观察者-Observer
> **流行指数**：★★★★★
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：你不必找我，我来找你
> <br>**在线文档**：定义对象间的一种一对多的依赖关系 ,当一个对象的状态发生改变时,所有依赖于它的对象都得到通知并被自动更新...[**查看更多**][observer_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/observer)跳转。
> <br>

### 【B-8】状态-State
> **流行指数**：★★★☆☆
> <br>**难度等级**：★☆☆☆☆
> <br>**助记关键字**：上坡加速，下坡减速
> <br>**在线文档**：允许一个对象在其内部状态改变时改变它的行为。对象看起来似乎修改了它的类...[**查看更多**][state_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/state)跳转。
> <br>

### 【B-9】策略-Strategy
> **流行指数**：★★★★★
> <br>**难度等级**：★☆☆☆☆
> <br>**助记关键字**：公交/地铁/共享单车都能回家
> <br>**在线文档**：定义一系列的算法，把它们一个个封装起来，并且使它们可相互替换。本模式使得算法可独立于使用它的客户而变化...[**查看更多**][strategy_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/strategy)跳转。
> <br>

### 【B-10】模板方法-Template Method
> **流行指数**：★★★★★
> <br>**难度等级**：★☆☆☆☆
> <br>**助记关键字**：封装公用部分，扩展变化部分
> <br>**在线文档**：定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤...[**查看更多**][template_method_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/template_method)跳转。
> <br>

### 【B-11】访问者-Visitor
> **流行指数**：★☆☆☆☆
> <br>**难度等级**：★★★★★
> <br>**在线文档**：表示一个作用于某对象结构中的各元素的操作，它使你可以在不改变各元素的类的前提下定义作用于这些元素的新操作...[**查看更多**](/doc/behavioral/Visitor.md)
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/behavioral/visitor)跳转。
> <br>

## 【C】创建型模式

### 【C-1】单例-Singleton
> **流行指数**：★★★★★
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：全局唯一、工具类
> <br>**在线文档**：单例模式应该是所有设计模式中结构最简单的一个了，同时它也是面试中被考的最多的设计模式...[**查看更多**][singleton_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/creational/singleton)跳转。
> <br>

### 【C-2】原型-Prototype
> **流行指数**：★★★★☆
> <br>**难度等级**：★★☆☆☆
> <br>**助记关键字**：细胞分裂
> <br>**在线文档**：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象...[**查看更多**][prototype_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/creational/prototype)跳转。
> <br>

### 【C-3】建造者-Builder
> **流行指数**：★★★★★
> <br>**难度等级**：★★☆☆☆
> <br>**助记关键字**：外卖套餐，车间流水线
> <br>**在线文档**：建造者模式我们都接触过，在开发中，经常见到 XXXBuilder 这样的类，通常以这种方式命名的类就使用了建造者模式...[**查看更多**][builder_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/creational/builder)跳转。
> <br>

### 【C-4】工厂方法-Factory Method
> **流行指数**：★★★★★
> <br>**难度等级**：★★★★☆
> <br>**助记关键字**：煤厂产煤，鞋厂造鞋
> <br>**在线文档**：我们都知道设计模式实际上是一些指导思想，这些指导思想是由前人总结和提炼出来的，主要目的是为了解决在代码设计和维护时暴露出来的问题。这些问题往往围绕着耦合性、扩展性等展开...[**查看更多**][factory_method_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/creational/factory_method)跳转。
> <br>

### 【C-5】抽象工厂-Abstract Method
> **流行指数**：★★☆☆☆
> <br>**难度等级**：★★★☆☆
> <br>**助记关键字**：主题切换
> <br>**在线文档**：很多时候，我们不应该被一个看起来很复杂的名词或概念所绊倒，因为往往看起来越复杂的东西其本质越简单。就像是抽象工厂模式...[**查看更多**][abstract_factory_doc]
> <br>**代码目录**：[**点击**](/src/main/java/com/aoligei/creational/abstract_factory)跳转。
> <br>

## 模式对比

**【1】组合模式 VS 装饰器模式**
<br>
> 正在努力更新中...
<br>

**【2】中介模式 VS 门面模式**
<br>
> 正在努力更新中...
<br>

## 进阶建议

**（1）远离那些试图阻止你的人**

只要你有足够的面向对象基础，每一种设计模式都并不复杂，设计模式并没有很神秘。<br>
> ”放弃吧，设计模式没个几万行的代码基础压根学不会的！“<br>
> ”学什么设计模式？没有设计模式老夫照样一把刷...“<br>
> “还不如不学，眼睛会了手不会，不能应用等于没学~”<br>

不管学什么，第一条要义永远是远离那些试图劝阻你的人！如果有一天听到这样的劝阻，不用尝试着和他理论，因为那没有任何意义。

**（2）学无止境，温故知新**

如果你已经学习过某一种模式，想知道更多关于该模式的内容，可以参阅设计模式相关的书籍，例如《Head First 设计模式》等（此处并不是推荐这本书，如有购书需求，请自行斟酌）。我相信书籍中的内容会比本项目更细致，更全面。<br>
多看书，看好书。不管处于哪个阶段，温故知新总是没错的。就比如我自己吧，每过一段时间去看《Design patterns- Elements of reusable object-oriented software》一书，都很上次看有完全不一样的感受。

**（3）不断实践，不断总结**

对于大多数设计模式来说，唯一的提升途径就是：一边实践，一边总结。尝试在实践中使用设计模式，并不断的进行总结，总结引入设计模式后的优缺点、寻找是否还有更好的方案。（**友情提示，如果你的项目隶属于公司，如果没有十足的把握和必要性，不要提交任何修改到远程仓库**）

**（4）不必为选择错误而感到担心**

设计模式的最终目的是为了规划合理的结构和改善既有的代码，为了这样的目标我们借助于设计模式所提供的一些套路。但在面对实际需求时，最困难的部分是选择合适的模式（甚至是应不应该引入设计模式）。有时候我们发现可以套用一个设计模式（或几个模式的组合），又觉得另一个设计模式（或其他模式的组合）同样能套用在此处。而此时，就是你总结经验的最佳时机。
或许你会因为选择错误而付出代价，但在下次遇到类似问题的时候，你已经有失败过一次的经验了。不必为选择错误而感到担心，没有人是完美的，选错了大不了重来一次。

## 写在最后
**（1）原创声明** <br>
本项目内容均是原创，每一个字、每一行代码都是从摸鱼或休息挤出来时间敲上去的，文档中有错误或者不详尽的地方请谅解。毕竟，我也只是个刚起步的初学者，在设计模式方面的造诣或许并不高于你，如果你觉得项目中有不合适的地方，欢迎指正。
<br>
**（2）发现错误** <br>
发现错误可在 issue 中告诉作者，我将尽快处理；如果有问题或者疑惑的地方，欢迎在评论中留言讨论。
<br>
**（3）联系作者** <br>
如果有需要，扫描以下二维码，添加作者 QQ。
<br>
<div align="center">
   <img src="https://s2.loli.net/2022/06/13/usw2GdZz7YfCrqW.jpg" width="35%"  />
</div>

---
更新不易，如果觉得该文档帮到了你，请点个 star 吧~


[builder_doc]:https://www.yuque.com/coderran/pd/dkzsxv
[factory_method_doc]:https://www.yuque.com/coderran/pd/dq7au9
[abstract_factory_doc]:https://www.yuque.com/coderran/pd/ikqm88
[prototype_doc]:https://www.yuque.com/coderran/pd/eqz0qg
[singleton_doc]:https://www.yuque.com/coderran/pd/dvedfa
[adaptor_doc]:https://www.yuque.com/coderran/pd/zy4og8
[decorator_doc]:https://www.yuque.com/coderran/pd/xgff2o
[observer_doc]:https://www.yuque.com/coderran/pd/gpcb3a
[template_method_doc]:https://www.yuque.com/coderran/pd/gxykap
[strategy_doc]:https://www.yuque.com/coderran/pd/mgbgzd
[state_doc]:https://www.yuque.com/coderran/pd/pmkced
[facade_doc]:https://www.yuque.com/coderran/pd/odmvwb
[proxy_doc]:https://www.yuque.com/coderran/pd/ulzd3k
[command_doc]:https://www.yuque.com/coderran/pd/xn73iv
[memento_doc]:https://www.yuque.com/coderran/pd/gm1ox9
[composite_doc]:https://www.yuque.com/coderran/pd/crhmfb
[flyweight_doc]:https://www.yuque.com/coderran/pd/ytwx7z
[bridge_doc]:https://www.yuque.com/coderran/pd/fbospp
[interpreter_doc]:https://www.yuque.com/coderran/pd/tit80e