## 这个仓库是干啥的？

23 种设计模式之入门宝典（Java版），对《Design patterns- Elements of reusable object-oriented software》一书中提及的 23 种设计模式进行更加浅显的解释，配合着通俗易懂的例子进行说明，旨在降低设计模式入门的难度。

## 适宜人群说明

本仓库适合对设计模式知之甚少或者有一些浅显的认知，对于这类开发者朋友可通过仓库中的例子快速认识设计模式。然而，对于想在设计模式方面进行深入的探讨，以及想通过快餐式消费一些知识进而重构手头项目的开发者并不适用，如果您正好属于这一类开发者，就不必在这个仓库中花费过多的时间啦。

## 如何开始？

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

| 序号  | 模式名称     | 在线文档                 | 示例代码                  |
|-----|--------------|----------------------|-----------------------|
| 1   | 适配器（Adaptor）   | [传送门][adaptor_doc]   | [传送门][adaptor_code]   |
| 2   | 桥接（Bridge）     | 更新中...              | 更新中...               |
| 3 | 组合（Composite）   | 更新中...              | 更新中...               |
| 4 | 装饰器（Decorator）  | [传送门][decorator_doc] | [传送门][decorator_code] |
| 5 | 外观（Facade）      | 更新中...              | 更新中...               |
| 6 | 享元（Flyweight） | 更新中...              | 更新中...               |

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

## 有问题咋办？

**(1). 发现文档及示例代码中有错误或者有疑问** <br>
发现错误可在文档中评论，或者在 issue 中告诉作者，作者将尽快处理；如果有不甚清楚的问题，请在评论中留言。
<br>
**(2). 如何联系作者** <br>
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