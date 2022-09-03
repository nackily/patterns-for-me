## <center> 结构型 - 享元（Flyweight）设计模式
---

对于每一种设计模式，我们总能从他的命名中找到意义或者特征，通过名字我们便能快速的联想到这个模式。但有一个模式除外，他有着一个让人摸不着头脑的名字 —— 享元模式。即便在此之前你已经阅读过与该模式有关的内容，那么你是否能快速的联想到这个模式的更多细节呢？

# 一、从包装类的缓存池开始

在你参加过大大小小的面试生涯中，是否曾遇到过如下的问题？

> 给出该行代码的运行结果：`System.out.println(Integer.valueOf("90") == Integer.valueOf(90));`

我们知道`Integer#valueOf()`方法返回一个`Integer`类型的对象，那么这道题则是将两个`Integer`类型的对象进行比较。在 java 中，对于基本数据类型 == 比较的是值，而对于对象类型，则比较的是对象的引用地址。似乎我们很快便得出结论：两次`Integer.valueOf()`方法的调用，将产生两个对象，所以这道题应该输出 false。

但遗憾的是，这道题的正确答案是 true。那么，在分析的过程中，我们到底忽略了什么？

```java
public final class Integer extends Number implements Comparable<Integer> {
    
    public static Integer valueOf(String s) throws NumberFormatException {
        return Integer.valueOf(parseInt(s, 10));
    }
    
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
    
    private static class IntegerCache {
        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            // high value may be configured by property
            int h = 127;
            String integerCacheHighPropValue =
                sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    int i = parseInt(integerCacheHighPropValue);
                    i = Math.max(i, 127);
                    // Maximum array size is Integer.MAX_VALUE
                    h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
                } catch( NumberFormatException nfe) {
                    // If the property cannot be parsed into an int, ignore it.
                }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for(int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);

            // range [-128, 127] must be interned (JLS7 5.1.7)
            assert IntegerCache.high >= 127;
        }
    }
}
```

以上源码片段摘自`java.lang.Integer`，在源码中我们看到使用`Integer.valueOf()`方法时，会先对传入的值进行检查。如果该值超过某个范围区间，重新创建一个`Integer`对象，如果在某个区间内，则直接从缓存中获取，并且这个缓存是在类加载的时候初始化的。缓存的最低区间为 -128，而最高区间取决于 `java.lang.Integer.IntegerCache.high` 的配置值，如果配置值大于 127，则最高区间则是配置值；否则，最高区间值为 127。

至此，我们可以得出结论，`Integer.valueOf("90")`和`Integer.valueOf(90)`的值都在 [-128, 127]之间，那么他们对应的 Integer 对象都取自于缓存。他们取的是同一个对象，所以，答案是 true。在所有的包装类中，除了`Float`和`Double`之外，其他的包装类都提供类似的机制。这个机制被称为包装类缓存池，主要目的就在于减少对象的数量，倡导对象复用。

OK，到此为止，我们已经对包装类缓存池的机制进行了完整的分析。那么，这和我们本章所探讨的享元模式有什么关系呢？事实上，`Integer`的缓存池就是享元模式的实践，而`Integer`的实现中就包含有享元模式的影子！

# 二、享元模式
我们在前面已经总结过，`Integer`缓存池机制的主要目的是为了减少对象的创建，提倡对象的复用，这个目的对于享元模式同样适用！让我们来看看享元模式的意图。

## 2.1 意图
> **运用共享技术有效地支持大量细粒度的对象。**

对于该意图的进一步解释如下：

- **运用共享技术**：共享技术就是复用，对于表示同一个状态的对象，我们可以复用同一个对象。比如`Integer.valueOf("90")`和`Integer.valueOf(90)`都是个表示 90 的整数值对象，既然需要的值是同一个，那么就可以重用；
- **支持大量细粒度的对象**：这句话的隐藏细节是如果不采用一些特殊手段，或许系统就无法支持大量的细粒度的对象。比如，我的系统可能在某一个时间需要 1000 个表示值为 90 的 Integer 对象，如果我们为每一个引用创建 1000 个对象，那么在那一时刻系统内存的占用就是对象重用的 1000 倍。

## 2.2 组成部分
在前面，我们已经分析过`Integer`的源码，现在我们参照它来梳理更加宽泛的享元模式的组成部分。

- **共享对象**：可以被重用的对象。比如`Integer`值在[-128, 127]之间的实例；
- **非共享对象**：不能被重用的对象。我们不能强制要求客户只能从缓存池中取对象，而不提供另外的创建机制，因为共享对象并不能满足所有的需求。比如`Integer`，如果范围区间设置过大，那么在初始化时将加载过多的对象占用内存，这是不可取的，因为我们并不会用到那么多的共享对象，所以，考虑常用的就够了；
- **缓存池**：对于部分的元对象实例，可取自缓存池中，而不用重复创建。比如`Integer.IntegerCache`类，既提供了获取缓存的行为，也实现了初始化加载缓存的行为。

## 2.3 类图分析
<div align="center">
   <img src="/doc/resource/flyweight/经典享元模式类图.jpg" width="70%"/>
</div>

享元模式的通用类图如上所示，包含的角色有如下：

- **Flyweight**：共享对象的接口，该接口定义了共享对象可接收一些参数作为外部状态（extrinsicState），并对其进行处理；
- **ConcreteFlyweight**：具体的共享对象，除实现共享接口外，内部还维护了共享对象的内在状态（intrinsicState）；
- **FlyweightFactory**：管理所有的共享对象，通常的处理是：当用户请求一个 flyweight 时，FlyweightFactory 对象提供一个已创建的实例或者创建一个（如果不存在的话）；
- **Client**：维护共享对象的外在状态。

**（1）同`Integer**`类比分析**

- **ConcreteFlyweight**：如`Integer`对象，维护了内部状态（`private final int value`）；
- **FlyweightFactory**：如`Integer#valueOf()`静态方法，内部维护了`IntegerCache#cache[]`，当缓存中包含有所需的共享对象时，可从缓存中获取；
- **Client**：使用 Integer 对象的地方，Client 可从缓存中取共享对象，也可以不使用共享对象（通过 new 关键字实例化），享元模式不应强制客户端只能使用共享对象，当客户端并不希望使用共享对象时，可自行创建非共享对象；

**（2）同`Integer`差异分析**

- **Flyweight**：Integer 并不需要一个接口进行约束，所以，当实现类仅有一个时，Flyweight 抽象可以不必要。

# 三、案例实现
## 3.1 案例分析

> 假设我们现在正在构建一个有线条、椭圆和矩形的随机画板。我们强调：在画板中可能会绘制大量的图形。

在这个例子中，我们需要一个图像的接口（Shape），还需要线条（Line）、椭圆（Oval）和矩形（Rectangle）类。如果我们为每个图像都创建一个独立的对象，无疑内存占用将会很高，所以，我们考虑采用享元模式来复用图像对象。

对于一个图像来说，如果需要呈现在窗口中，那么必不可少的属性至少应该有：

- **形状**：图像一旦被初始化，形状就已确定，且不能被修改，所以形状被划分为内部状态；
- **颜色**：每个图像都可能有不同的颜色表示，并且颜色的取值范围太广，更适合作为外部状态；
- **位置**：每个图像都将出现在不同位置，理论上图像可能出现在画板的任何位置，更适合划分为外部状态；
- **是否填充**：对于矩形和椭圆形，因为他们是带有内部空间并且封闭的图像，那么我们可以对其内部进行填充，所以可以选择他们是否被填充。而是否被填充仅包含两个可能的取值【是/否】，所以此处作为内部状态。

值得一提的是，对于不太熟悉享元模式的使用者来说，最难的其实是状态的划分。如果没有经验，很容易在内部状态和外部状态的抉择中举棋不定。请注意上面我在描述图像属性时，是如何划分状态的。

**为什么形状和是否被填充划分为内部状态？**

> - 当一个图像的类型被确定时，形状也已明确下来，改变图像的形状意味着图像对象的类型将跟着变化，这是不被允许的，所以形状是内部状态；
> - 我将是否填充属性划分为内部状态，是因为它非常有限的取值：是或否。我们可以很轻松的用来构造两个相互对立的对象：内部填充的图像、内部不填充的图像。当然，你也可以认为它是外部状态；

**为什么颜色和位置为什么是外部状态？**

> - 每一个图像被绘制在屏幕上时，都可能有着完全不同的位置（包括图像左上角的坐标、宽度和高度）。如果将这部分作为内部状态，对象共享的可能性就将大大降低。这就相当于，区间[1~5]之间的自然数取值只有 5 种可能，而区间[1~500]之间的自然数取值就有 500 种可能，前者的共享可能性明显高于后者；
> - 和位置属性一样，将颜色作为外部状态的依据也是如此。颜色也有着太丰富的变化空间；

## 3.2 代码附录
**（1）图像接口**
```java
public interface Shape {

    /**
     * 绘制当前图像到屏幕上
     * @param g g
     * @param x 图像的左上角X坐标
     * @param y 图像的左上角Y坐标
     * @param width 图像的宽度
     * @param height 图像的高度
     * @param c 颜色
     */
    void draw(Graphics g, int x, int y, int width, int height, Color c);
}
```
**（2）形状**

**（2-1）线条**
```java
public class Line implements Shape {

    public Line() {
        System.out.println("    创建一个新的线条对象");
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height, Color c) {
        g.setColor(c);
        // 线条的终点坐标为：[x + width, y + height]
        g.drawLine(x, y, x + width, x + height);
    }
}
```
**（2-2）矩形**
```java
public class Rectangle implements Shape{

    private final boolean fill;
    public Rectangle(boolean fill) {
        this.fill = fill;
        System.out.println("    创建一个新的矩形对象");
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height, Color c) {
        g.setColor(c);
        if (this.fill) {
            g.fillRect(x, y, width, height);
        } else {
            g.drawRect(x, y, width, height);
        }
    }
}
```
**（2-3）椭圆形**
```java
public class Oval implements Shape{

    private final boolean fill;
    public Oval(boolean fill) {
        this.fill = fill;
        System.out.println("    创建一个新的椭圆形对象");
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height, Color c) {
        g.setColor(c);
        if (this.fill) {
            g.fillOval(x, y, width, height);
        } else {
            g.drawOval(x, y, width, height);
        }
    }
}
```
**（3）共享对象工厂**
```java
public class ShapeFactory {

    /**
     * 缓存
     */
    private static final HashMap<SupportedShape, Shape> CACHES = new HashMap<>();

    /**
     * 获取图像对象
     * @param s 图像类型
     * @return 图像对象
     */
    public static Shape getShape(SupportedShape s) {
        if (CACHES.containsKey(s)) {
            return CACHES.get(s);
        }
        Shape shape = null;
        switch (s) {
            case LINE:
                shape = new Line();
                break;
            case RECT:
                shape = new Rectangle(false);
                break;
            case RECT_FILL:
                shape = new Rectangle(true);
                break;
            case OVAL:
                shape = new Oval(false);
                break;
            case OVAL_FILL:
                shape = new Oval(true);
                break;
            default:
                throw new RuntimeException("不支持的图像类型");
        }
        CACHES.put(s, shape);
        return shape;
    }


    public static enum SupportedShape {
        LINE,                       // 线条
        RECT,                       // 矩形
        RECT_FILL,                  // 填充矩形
        OVAL,                       // 椭圆形
        OVAL_FILL;                  // 填充椭圆形
    }
}
```
**（4）客户端**

**（4-1）Client**
```java
public class Application extends JFrame implements ActionListener {

    private final Color[] supportedColors = new Color[] {Color.BLUE, Color.RED, Color.BLACK, Color.MAGENTA};
    private final JPanel panel = new JPanel();
    public Application(int width, int height) {
        super.setSize(width, height);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 刷新按钮
        JButton button = new JButton("once again");
        button.addActionListener(this);
        // 组件布局
        Container container = super.getContentPane();
        container.add(panel, BorderLayout.CENTER);
        container.add(button, BorderLayout.SOUTH);
    }

    /**
     * 获取一个随机的图像类型
     * @return 图像类型
     */
    private ShapeFactory.SupportedShape getRandomShape() {
        ShapeFactory.SupportedShape[] allSupported = ShapeFactory.SupportedShape.values();
        return allSupported[(int) (Math.random() * allSupported.length)];
    }

    /**
     * 获取一个随机的左上角X坐标
     * @return x
     */
    private int getRandomX() {
        return (int) (Math.random() * super.getSize().width);
    }

    /**
     * 获取一个随机的左上角Y坐标
     * @return y
     */
    private int getRandomY() {
        return (int) (Math.random() * super.getSize().height);
    }

    /**
     * 获取一个随机的图像宽度
     * @return 宽度，范围在0~画布宽度的十分之一
     */
    private int getRandomWidth() {
        return (int) (Math.random() * (super.getSize().width / 10));
    }

    /**
     * 获取一个随机的图像高度
     * @return 高度，范围在0~画布高度的十分之一
     */
    private int getRandomHeight() {
        return (int) (Math.random() * (super.getSize().height / 10));
    }

    /**
     * 获取一个随机的颜色
     * @return 颜色
     */
    private Color getRandomColor() {
        int index = (int) (Math.random() * supportedColors.length);
        return supportedColors[index];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Graphics graphics = panel.getGraphics();
        for (int i = 0; i < 100; i++) {
            // 随机获取一个图像对象
            ShapeFactory.SupportedShape supportedShape = this.getRandomShape();
            Shape shape = ShapeFactory.getShape(supportedShape);
            shape.draw(graphics, getRandomX(), getRandomY(), getRandomWidth(), getRandomHeight(), getRandomColor());
        }
    }



    public static void main(String[] args) {
        System.out.println("|==> Start -------------------------------------------------|");
        Application app = new Application(800, 650);
        app.setVisible(true);
    }
}
```
**（4-2）运行结果**
```text
|==> Start -------------------------------------------------|
    创建一个新的椭圆形对象
    创建一个新的矩形对象
    创建一个新的椭圆形对象
    创建一个新的矩形对象
    创建一个新的线条对象
```
运行时效果截图如下：
<div align="center">
   <img src="/doc/resource/flyweight/运行结果截图.png" width="60%"/>
</div>

在这个例子中，我们仅仅用了 5 个共享的对象就在屏幕上绘制了大量随机图像，这就是享元模式的魅力。

# 四、深入
## 4.1 透过现象看本质
在前面，我们已经对享元模式进行了比较全面的剖析。总结来看，享元模式是利用了池化的思想，来实现对象的共享。但实际上，池化并不是享元模式的所有，享元模式还包含了另一层隐含的特性：使用享元模式往往能使得对象更加轻量化（细粒度化）。以前面的例子来说，描述一个图像的状态有：形状、颜色、位置和是否被填充。如果我们不使用享元模式，那么每一个图像都必须维护这些属性；但在前面的实现中，我们将图像的状态分为了内部状态和外部状态，图像本身不再维护颜色、位置等状态，这就使得图像对象相对于原来占用更小，更加轻量（仅需维护形状和是否被填充两个状态）。

## 4.2 使用场景

**（1）对象复用**

当系统内存在大量表示相同状态的对象时，可用享元模式来进行对象的重用。

**（2）优化大量对象**

当系统内存在大量的对象，且部分对象在某些状态上表现一致，可将对象按照某个角度拆分成多种类型的对象，将容易变化的状态提取到对象外部，保留下来的就是可被共享的状态。

# 五、使用技巧

**（1）选择合适的共享对象维护机制**

在`Integer`中，共享对象的加载机制是在类加载时进行的。除此之外，我们还可以采用懒加载的机制，在`FlyweightFactory#getFlyweight()`方法中判断，如果当前共享对象不存在，则创建一个新对象并放入缓存池，否则从缓存中获取。

**（2）内部状态和外部状态**

在前面，我们已经通过案例来说明内部状态和外部状态应该如何科学地划分，这一点相当重要。总结来说，我们使用享元模式的目的是为了减少资源的消耗，也是为了只用少量的几个对象来表达这些可被共享的状态，这个过程必须要做的就是剔除掉那些容易变化的状态。如果我们想要在对象中穷尽这些易变的状态，就必须创建更多的对象来表示，这就与我们的初衷相悖，自然也就无法实现对象复用。所以我们在对状态进行分类时，有一个常用的原则：不变的是内部状态，易变的通常是外部状态。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/structural/flyweight)
