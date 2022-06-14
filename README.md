> Once you understand the design patterns and have had an "Aha!" (and not just a "Huh?") experience with them, you won't ever think about object-oriented design in the same way.
## 项目介绍

23 种设计模式之入门宝典（Java版），对《Design patterns- Elements of reusable object-oriented software》一书中提及的 23 种设计模式进行更加浅显的解释，配合着通俗易懂的例子进行说明，旨在降低设计模式入门的难度。

## 适宜人群说明

本仓库适用于 **对设计模式知之甚少或者有一些浅显的认知，对于这类开发者朋友可通过仓库中的例子快速认识设计模式。** 然而，意图通过快餐式消费丰富自我进而重构手头项目的开发者则并不能从该项目中受益，如果你正好属于这一类开发者，就不必在这个仓库中花费过多的时间啦。

## 开始学习

对于每一种设计模式，提供了一篇在线文档以及示例代码。<br>
### 创建型模式

| 序号  | 模式名称 | 在线文档                      | 示例代码                       |
|-----|------------------------|---------------------------|----------------------------|
| 1   | 抽象工厂（Abstract Factory） | 更新中...                    | 更新中...                        |
| 2   | 建造者（Builder）           | [传送门][builder_doc]        | [传送门][builder_code]        |
| 3 | 工厂方法（Factory Method） | [传送门][factory_method_doc] | [传送门][factory_method_code] |
| 4 | 原型（Prototype）| [传送门][prototype_doc]      | [传送门][prototype_code]      |
| 5 | 单例（Singleton）| [传送门][singleton_doc]      | [传送门][singleton_code]      |

### 结构型模式

| 序号  | 模式名称           | 在线文档                 | 示例代码                  |
|-----|----------------|----------------------|-----------------------|
| 1   | 适配器（Adaptor）   | [传送门][adaptor_doc]   | [传送门][adaptor_code]   |
| 2   | 桥接（Bridge）     | 更新中...              | 更新中...               |
| 3   | 组合（Composite）  | 更新中...              | 更新中...               |
| 4   | 装饰器（Decorator） | [传送门][decorator_doc] | [传送门][decorator_code] |
| 5   | 外观（Facade）     | 更新中...              | 更新中...               |
| 6   | 享元（Flyweight）  | 更新中...              | 更新中...               |
| 7   | 代理（Proxy）      | 更新中...              | 更新中...               |

### 行为型模式

| 序号  | 模式名称                         | 在线文档                       | 示例代码                        |
|-----|------------------------------|----------------------------|-----------------------------|
| 1   | 责任链（Chain Of Responsibility） | 更新中...                     | 更新中...                      |
| 2   | 命令（Command）                  | 更新中...                     | 更新中...                      |
| 3   | 解释器（Interpreter）             | 更新中...                     | 更新中...                      |
| 4   | 迭代器（Iterator）                | 更新中...                     | 更新中...                      |
| 5   | 中介者（Mediator）                | 更新中...                     | 更新中...                      |
| 6   | 备忘录（Memento）                 | 更新中...                     | 更新中...                      |
| 7   | 观察者（Observer）                | [传送门][observer_doc]        | [传送门][observer_code]        |
| 8   | 状态（State）                    | 更新中...                     | 更新中...                      |
| 9   | 策略（Strategy）                 | 更新中...                     | 更新中...                      |
| 10  | 模板方法（Template Method）        | [传送门][template_method_doc] | [传送门][template_method_code] |
| 11  | 访问者（Visitor）                 | 更新中...                     | 更新中...                      |

## 毕业建议
### 远离那些试图阻止你的人
事实上，每一种设计模式都并不复杂。只要你使用面向对象的语言，就或多或少与设计模式打过交道，设计模式也没什么神秘的。设计模式并不是只能由高级开发者使用，只要你有面向对象的基础，你一样可以在设计模式中汲取前人的经验。<br>
> ”放弃吧，设计模式没个几万行的代码基础压根学不会的！“<br>
> ”学什么设计模式？没有设计模式老夫照样一把刷...“<br>
> “还不如不学，眼睛会了手不会，用不起来等于没学。”<br>

如果有一天咱听到这样的话，记得远离他。最好是大家都不学，到时候就只有咱自己独步天下呐，岂不美哉？

### 进阶建议

**(1). 学无止境，温故知新**

如果你已经学习了某一种模式，想知道更多关于该设计模式的内容，可以参阅设计模式相关的书籍，例如《Head First 设计模式》等（此处并不是推荐这本书，如有购书需求，请自行斟酌）。书籍中的内容会比本项目更细致，更全面。

多看书，看好书，不管处于哪个阶段，温故知新总是没错的。比如《Design patterns- Elements of reusable object-oriented software》一书，过一段时间再去看，和上次看的感受就可能完全不一样了。

**(2). 不断实践，不断总结**

如果你已经了解了某一种模式，唯一的提升途径就是：**一边实践，一边总结**。尝试在实践中使用这种设计模式，并不断的进行总结，总结引入设计模式后的优缺点、寻找是否还有更好的方案。**注意，如果你的代码隶属于公司，如果没有十足的把握以及迫不得已的需求，不要提交到远程仓库**。

**(3). 厚积薄发，有的放矢**

设计模式的最终目的是为了规划合理的结构和改善既有的代码，为了这样的目标我们借助于设计模式所提供的 23 种套路。但在面对实际需求时，最困难的部分是选择合适的套路（甚至是应不应该引入套路）。有时候我们发现可以套用一个设计模式（或几个模式的组合），又觉得另一个设计模式（或其他模式的组合）同样能套用在此处。而此时，就是你总结经验的最佳时机。
尽管，你会因为选择错误而付出代价，但在下次遇到同样问题的时候，你已经有失败过一次的经验了。不必为选择错误而感到担心，没有人是完美的。

## 写在最后
**(1). 声明** <br>
本项目内容均是原创，每一个字都是从摸鱼或者休息的时候抽出来的，文档中有错误或者不甚详细的地方请谅解。同时，我也是个刚刚起步的学者，在设计模型方面的造诣或许并不高于你，如果你觉得项目中有不合适的地方，欢迎指正。
<br>
**(2). 发现文档及示例代码中有错误或者有疑问** <br>
发现错误可在 issue 中告诉作者，作者将尽快处理；如果有不甚清楚的问题，请在评论中留言。
<br>
**(3). 如何联系作者** <br>
扫描以下二维码，添加作者 QQ。
<br>
<div align="center">
   <img src="https://s2.loli.net/2022/06/13/usw2GdZz7YfCrqW.jpg" width="45%"  />
</div>

---
更新不易，如果觉得该文档帮到了你，请点个star吧~



[builder_doc]:https://www.yuque.com/coderran/pd/dkzsxv
[factory_method_doc]:https://www.yuque.com/coderran/pd/dq7au9
[prototype_doc]:https://www.yuque.com/coderran/pd/eqz0qg
[singleton_doc]:https://www.yuque.com/coderran/pd/dvedfa
[adaptor_doc]:https://www.yuque.com/coderran/pd/zy4og8
[decorator_doc]:https://www.yuque.com/coderran/pd/xgff2o
[observer_doc]:https://www.yuque.com/coderran/pd/gpcb3a
[template_method_doc]:https://www.yuque.com/coderran/pd/gxykap


[builder_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/builder
[factory_method_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/factory_method
[prototype_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/prototype
[singleton_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/creational/singleton
[adaptor_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/structural/adapter
[decorator_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/structural/decorator
[observer_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/behavioral/observer
[template_method_code]:https://gitee.com/ry_always/dp4java/tree/master/src/main/java/com/aoligei/behavioral/template_method