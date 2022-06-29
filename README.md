> Once you understand the design patterns and have had an "Aha!" (and not just a "Huh?") experience with them, you won't ever think about object-oriented design in the same way.
---
## 项目介绍

设计模式之入门宝典（Java版），包含《Design patterns- Elements of reusable object-oriented software》一书中提及的 23 种设计模式，结合真实的生活场景，对每一种设计模式进行讲解。

## 适宜人群

本项目适用于以下开发者：
+ 没了解过设计模式，但熟悉面向对象开发；
+ 理解某个模式的目的，但在实际开发中不知道如何打开；
+ 正打算学习设计模式或已经在其他地方学过设计模式；

## 开始学习

### 结构型模式

| 序号  | 模式名称           | 在线文档                 | 示例代码                  |
|-----|----------------|----------------------|-----------------------|
| 1   | 适配器（Adaptor）   | [传送门][adaptor_doc]   | [传送门][adaptor_code]   |
| 2   | 桥接（Bridge）     | 更新中...               | 更新中...                |
| 3   | 组合（Composite）  | 更新中...               | 更新中...                |
| 4   | 装饰器（Decorator） | [传送门][decorator_doc] | [传送门][decorator_code] |
| 5   | 门面（Facade）     | [传送门][facade_doc]    | [传送门][facade_code]    |
| 6   | 享元（Flyweight）  | 更新中...               | 更新中...                |
| 7   | 代理（Proxy）      | [传送门][proxy_doc]     | [传送门][proxy_code]     |

### 行为型模式

| 序号  | 模式名称                         | 在线文档                       | 示例代码                        |
|-----|------------------------------|----------------------------|-----------------------------|
| 1   | 责任链（Chain Of Responsibility） | 更新中...                     | 更新中...                      |
| 2   | 命令（Command）                  | [传送门][command_doc]         | [传送门][command_code]         |
| 3   | 解释器（Interpreter）             | 更新中...                     | 更新中...                      |
| 4   | 迭代器（Iterator）                | 更新中...                     | 更新中...                      |
| 5   | 中介者（Mediator）                | 更新中...                     | 更新中...                      |
| 6   | 备忘录（Memento）                 | 更新中...                     | 更新中...                      |
| 7   | 观察者（Observer）                | [传送门][observer_doc]        | [传送门][observer_code]        |
| 8   | 状态（State）                    | 更新中...                     | 更新中...                      |
| 9   | 策略（Strategy）                 | [传送门][strategy_doc]        | [传送门][strategy_code]        |
| 10  | 模板方法（Template Method）        | [传送门][template_method_doc] | [传送门][template_method_code] |
| 11  | 访问者（Visitor）                 | 更新中...                     | 更新中...                      |


对于每一种设计模式，提供一篇在线文档及文档中的示例代码。对于容易混淆的模式，除此之外提供单独的在线文档对他们对比、区分及总结。<br>
### 创建型模式

#### （C-1）Singleton - 单例模式
<br>**流行指数**：★★★★★
<br>**难度等级**：★★★☆☆
<br>**助记关键字**：全局唯一、工具类
<br>**在线文档**：单例模式应该是最简单的设计模式了，这个设计模式应该是面试中最被的最多的设计模式了。如果现在你仍不能随手写出两种单例实现，那么你该多努努力了...[查看更多][singleton_doc]
<br>**代码目录**：[点击][singleton_code]跳转。

#### （C-2）Prototype - 原型模式
<br>**流行指数**：★★★★☆
<br>**难度等级**：★★☆☆☆
<br>**助记关键字**：细胞分裂
<br>**在线文档**：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象...[查看更多][prototype_doc]
<br>**代码目录**：[点击][prototype_code]跳转。

#### （C-3）Builder - 建造者模式
<br>**流行指数**：★★★★★
<br>**难度等级**：★★☆☆☆
<br>**助记关键字**：外卖套餐，车间流水线
<br>**在线文档**：建造者模式我们都接触过，在开发中，经常见到 XXXBuilder 这样的类，通常以这种方式命名的类就使用了建造者模式...[查看更多][builder_doc]
<br>**代码目录**：[点击][builder_code]跳转。

#### （C-4）Factory Method - 工厂方法模式
<br>**流行指数**：★★★★★
<br>**难度等级**：★★★★☆
<br>**助记关键字**：煤厂产煤，鞋厂造鞋
<br>**在线文档**：我们都知道设计模式实际上是一些指导思想，这些指导思想是由前人总结和提炼出来的，主要目的是为了解决在代码设计和维护时暴露出来的问题。这些问题往往围绕着耦合性、扩展性等展开...[查看更多][factory_method_doc]
<br>**代码目录**：[点击][factory_method_code]跳转。

#### （C-5）Abstract Method - 抽象工厂模式
正在努力更新中...

### 模式对比

####（1）结构四姐妹
正在努力更新中...

## 进阶建议

**(1). 远离那些试图阻止你的人**

只要你有足够的面向对象基础，每一种设计模式都并不复杂，设计模式并没有很神秘。<br>
> ”放弃吧，设计模式没个几万行的代码基础压根学不会的！“<br>
> ”学什么设计模式？没有设计模式老夫照样一把刷...“<br>
> “还不如不学，眼睛会了手不会，不能应用等于没学~”<br>

不管学什么，第一条要义永远是远离那些试图劝阻你的人！如果有一天咱听到这样的话，记得微笑着敷衍吧。

**(2). 学无止境，温故知新**

如果你已经学习过某一种模式，想知道更多关于该模式的内容，可以参阅设计模式相关的书籍，例如《Head First 设计模式》等（此处并不是推荐这本书，如有购书需求，请自行斟酌）。我相信书籍中的内容会比本项目更细致，更全面。<br>
多看书，看好书。不管处于哪个阶段，温故知新总是没错的。就比如我自己吧，每过一段时间去看《Design patterns- Elements of reusable object-oriented software》一书，都很上次看有完全不一样的感受。

**(3). 不断实践，不断总结**

对于大多数设计模式来说，唯一的提升途径就是：**一边实践，一边总结**。尝试在实践中使用设计模式，并不断的进行总结，总结引入设计模式后的优缺点、寻找是否还有更好的方案。（**友情提示，如果你的项目隶属于公司，如果没有十足的把握和必要性，不要提交任何修改到远程仓库**）

**(3). 不必为选择错误而感到担心**

设计模式的最终目的是为了规划合理的结构和改善既有的代码，为了这样的目标我们借助于设计模式所提供的一些套路。但在面对实际需求时，最困难的部分是选择合适的模式（甚至是应不应该引入设计模式）。有时候我们发现可以套用一个设计模式（或几个模式的组合），又觉得另一个设计模式（或其他模式的组合）同样能套用在此处。而此时，就是你总结经验的最佳时机。
或许你会因为选择错误而付出代价，但在下次遇到类似问题的时候，你已经有失败过一次的经验了。不必为选择错误而感到担心，没有人是完美的，选错了大不了重来一次。

## 写在最后
**(1). 原创声明** <br>
本项目内容均是原创，每一个字、每一行代码都是从摸鱼或休息挤出来时间敲上去的，文档中有错误或者不详尽的地方请谅解。毕竟，我也只是个刚起步的初学者，在设计模式方面的造诣或许并不高于你，如果你觉得项目中有不合适的地方，欢迎指正。
<br>
**(2). 发现错误** <br>
发现错误可在 issue 中告诉作者，我将尽快处理；如果有问题或者疑惑的地方，欢迎在评论中留言讨论。
<br>
**(3). 联系作者** <br>
如果有需要，扫描以下二维码，添加作者 QQ。
<br>
<div align="center">
   <img src="https://s2.loli.net/2022/06/13/usw2GdZz7YfCrqW.jpg" width="35%"  />
</div>

---
更新不易，如果觉得该文档帮到了你，请点个 star 吧~


[builder_doc]:https://www.yuque.com/coderran/pd/dkzsxv
[factory_method_doc]:https://www.yuque.com/coderran/pd/dq7au9
[prototype_doc]:https://www.yuque.com/coderran/pd/eqz0qg
[singleton_doc]:https://www.yuque.com/coderran/pd/dvedfa
[adaptor_doc]:https://www.yuque.com/coderran/pd/zy4og8
[decorator_doc]:https://www.yuque.com/coderran/pd/xgff2o
[observer_doc]:https://www.yuque.com/coderran/pd/gpcb3a
[template_method_doc]:https://www.yuque.com/coderran/pd/gxykap
[strategy_doc]:https://www.yuque.com/coderran/pd/mgbgzd
[facade_doc]:https://www.yuque.com/coderran/pd/odmvwb
[proxy_doc]:https://www.yuque.com/coderran/pd/ulzd3k
[command_doc]:https://www.yuque.com/coderran/pd/xn73iv


[builder_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/builder
[factory_method_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/factory_method
[prototype_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/prototype
[singleton_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/singleton
[adaptor_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/structural/adapter
[decorator_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/structural/decorator
[observer_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/behavioral/observer
[template_method_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/behavioral/template_method
[strategy_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/behavioral/strategy
[facade_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/structural/facade
[proxy_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/structural/proxy
[command_code]:https://gitee.com/ry_always/DesignPatterns/tree/master/src/main/java/com/aoligei/behavioral/command