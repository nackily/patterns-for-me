## <center> 行为型 - 中介者（Mediator）设计模式
---

中介者模式很简单，它描述了当我们遇到多个对象之间存在复杂的依赖关系时，该如何降低这些依赖关系之间的耦合性。用一句时髦的话来形容中介者模式，那就是没有什么问题是加一层解决不了的。

# 一、问题引入
当我们使用面向对象的思维来构建应用模型时，总是免不了要处理各个对象之间的依赖关系。在一个职责分明的系统设计中，依赖是一定存在的，并且职责越是分明，依赖或许越复杂。因为在面向对象设计时，我们总是强调对象行为的单一性，不应把过多的职责赋予单个对象。所以现实世界中用户的一个动作，却需要经过一系列对象进行处理，他们就像是流水线上的工人，彼此协同才能完成工作。

这里并不是在指责这种设计有问题，如果你看过本系列中关于门面模式的介绍，那么你或许对我们描述的场景有一些印象。在那里，我们说道：多个对象之间的较为复杂的依赖关系往往表明系统设计的足够好，因为那意味着不同的行为被拆分不同的对象中负责。让我们以一个例子来说明这种情况。
> 在各个 IDE 中，都有设置编辑器字体的功能，在 Intellij-IDEA 中，字体设置页面如下所示：
<div align="center">
   <img src="/doc/resource/mediator/idea字体设置界面.png" width="60%"/>
</div>

> 在用户选择字体后，对应的编辑器框内文本字体将根据选择的内容发生变化。除此之外，针对每一种字体都提供了默认的排版设置（Typography Settings），所以在字体发生变化时，排版设置可能发生相应的变化。而在设置编辑器内文本内容的字体时，我们还需要知道用户设置的字体大小、行高等等信息。

这个例子稍显复杂，让我们定义一个简化版的实现作为演示案例。该简化版实现中的组件列表如下：
> - **1) 可选字体列表**：当切换字体时，设置提供默认的字体大小、是否加粗属性，并且编辑框内文本切换为所选字体；
> - **2) 字体输入框**：可输入关键字对字体列表进行筛选，筛选时，除可选字体列表外，其他的组件均不可用；
> - **3) 字体大小调整框**：当字体的大小值发生变化时，编辑框内文本字体的大小相应切换；
> - **4) 字体加粗单选框**：切换编辑框内文本字体的加粗样式；
> - **5) 编辑框**：在字体切换时，用于展示当前所选字体的实际效果。当编辑框内没有任何文本时，其他组件均不可用。

整理各个组件之间的交互逻辑后，我们得到各个组件之间的关系图如下：
<div align="center">
   <img src="/doc/resource/mediator/字体选择器组件关系图.jpg" width="70%"/>
</div>

我们看到，各个对象之间复杂的依赖关系错综复杂。更糟糕的是当新的组件加进来时，整个结构将变得更加难以维护。而此时就是引入中介者模式的最佳时机。

# 二、解决方案
中介者模式建议我们用一个中介对象来封装一系列的对象交互，在中介者对象中处理多个对象之间复杂的交互。各个对象之间不直接进行通信，由中介者对象来协调各个对象的工作。引入中介者对象后，各个组件之间的关系图将变成如下所示：
<div align="center">
   <img src="/doc/resource/mediator/字体选择器组件关系图-中介者模式.jpg" width="70%"/>
</div>

可以看出引入中介者对象之后，使得之前对象之间复杂的连接关系变得相当简单。如果要往这个模型中加入一个新的部件，我们只需要让新的部件连接到中介者对象即可。

按照引入中介者对象之后模型，以字体列表中切换字体为例，用户发起的动作的交互过程如下所示：
<div align="center">
   <img src="/doc/resource/mediator/请求时序图.svg" width="100%"/>
</div>

正如我们在之前所描述的那样：中介者对象负责控制和协调各个部件之间的交互，它的存在使得各个对象之间不需要再相互引用。相比来看，中介者模式多了中介者对象，并且各个部件之间不再有显式依赖。

# 三、案例实现
我已经实现了该案例，为了在阅读代码时有一个清晰的结构脉络，这里先介绍一下该案例的类图结构。

## 3.1 案例类图
<div align="center">
   <img src="/doc/resource/mediator/案例类图.jpg" width="100%"/>
</div>

案例的类图结构如上图所示，因篇幅原因，该类图中仅罗列出主要的方法，更详细的方法参考请完整代码。对于类图中类的解释如下：

- **AbstractWidget**：抽象部件，维护了一个 page 对象（中介者），以便子类在需要时可将自身状态的改变通知给中介者，由中介者协调其他的组件响应用户的动作。`setEnable()`和`setDisable()`方法为部件的禁用和启用方法；
- **FontInput**：字体输入框部件。提供了`getCurrentText()`和`setFontName()`方法，分别表示获取输入框内当前的文本、设置输入框内的内容；
- **OptionalList**：字体列表框部件。提供了`selectFontName()`和`filter()`方法，分别为获取当前所选字体和对列表的可选项进行过滤；
- **SizeInput**：字体大小调整框部件。提供了获取字体大小、设置字体大小的方法；
- **BoldInput**：字体加粗单选框部件。提供了获取字体加粗样式、设置字体加粗样式的方法；
- **Editor**：编辑框。提供了`isEmptyText()`和`changeFont()`方法，前者判断当前编辑框内的文本是否为空，后者为改变编辑框内的文本字体；
- **Page**：页面（中介者）。维持了该页面的所有部件的引用，并且提供了一个`widgetChanged()`方法，当页面内的某个组件状态发生变化时，可调用该方法通知自己，在该方法中协调与其他组件之间的交互。例如，`OptionalList.valueChanged()`、`Editor.removeUpdate()`等方法的触发就代表该组件的状态发生了变化。

## 3.2 代码附录

**（1）抽象部件**

```java
public abstract class AbstractWidget implements FocusListener {

    protected Page page;                        // 挂载页面
    protected JComponent root;                  // 当前部件，当部件由多个子部件构成时，该部件为顶层部件
    protected boolean headOfEventSource;        // 事件源头

    public AbstractWidget() {
        buildComponentUI();
    }

    /**
     * 直接交互对象
     * @param page 页面
     */
    protected void setPage(Page page) {
        this.page = page;
    }


    @Override
    public void focusGained(FocusEvent e) {
        // 得到焦点，组件可能为事件的触发源头
        this.headOfEventSource =  true;
    }

    @Override
    public void focusLost(FocusEvent e) {
        // 失去焦点，组件不可能为事件的触发源头
        this.headOfEventSource =  false;
    }

    /**
     * 获取组件
     * @return JComponent
     */
    protected JComponent getRoot() {
        return root;
    }

    /**
     * 创建UI组件
     */
    protected abstract void buildComponentUI();

    /**
     * 设置部件不可用
     */
    protected abstract void setDisable();

    /**
     * 设置部件可用
     */
    protected abstract void setEnable();

}
```
**（2）部件**

**（2-1）字体输入框**
```java
public class FontInput extends AbstractWidget implements DocumentListener {

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JTextField();
        root.addFocusListener(this);

        // 值改变监听器
        ((JTextField) root).getDocument().addDocumentListener(this);
    }

    @Override
    protected void setDisable() {
        root.setEnabled(false);
    }

    @Override
    protected void setEnable() {
        root.setEnabled(true);
    }

    public void setFontName(String fontName) {
        ((JTextField) root).setText(fontName);
    }

    /**
     * 获取当前的内容
     * @return 内容
     */
    public String getCurrentText() {
        return ((JTextField) root).getText();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (headOfEventSource) {
            // 关键字匹配字体
            page.widgetChanged(this);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (headOfEventSource) {
            // 关键字匹配字体
            page.widgetChanged(this);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
```
**（2-2）可选字体列表框**
```java
public class OptionalList extends AbstractWidget implements ListSelectionListener {

    private DefaultListModel<String> model;
    @Override
    @SuppressWarnings(value = {"unchecked"})
    protected void buildComponentUI() {
        // 初始化所有选项
        String[] fonts = allOptional();
        model = new DefaultListModel<>();
        for (String font : fonts) {
            model.addElement(font);
        }

        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JList<>(model);
        root.addFocusListener(this);

        // 不可多选
        ((JList<String>) root).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 注册选择监听器
        ((JList<String>) root).addListSelectionListener(this);
    }

    @Override
    protected void setDisable() {
        root.setEnabled(false);
    }

    @Override
    protected void setEnable() {
        root.setEnabled(true);
    }

    /**
     * 获取当前选择的字体名字
     * @return 字体名
     */
    @SuppressWarnings(value = {"unchecked"})
    protected String selectedFontName(){
        return ((JList<String>) root).getSelectedValue();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (headOfEventSource) {
            // 仅当鼠标释放、并且有选中选项时才处理
            if (null != selectedFontName() && !e.getValueIsAdjusting()) {
                page.widgetChanged(this);
            }
        }
    }

    /**
     * 筛选可选列表
     * @param keyword 关键字
     */
    public void filter(String keyword) {
        List<String> current = currentOptional();
        String[] all = allOptional();
        for (String item : all) {
            // 需要添加到可选列表中
            if (item.contains(keyword) && !current.contains(item)) {
                model.addElement(item);
            }
            // 从可选列表中移除
            if (!item.contains(keyword) && current.contains(item)) {
                model.removeElement(item);
            }
        }
    }

    /**
     * 所有列表
     * @return String[]
     */
    private String[] allOptional() {
        return Arrays.stream(Page.OptionalFont.values())
                .map(Page.OptionalFont::getKey)
                .toArray(String[]::new);
    }

    /**
     * 当前列表
     * @return List<String>
     */
    private List<String> currentOptional() {
        List<String> current = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            current.add(model.get(i));
        }
        return current;
    }
}
```
**（2-3）字体大小输入框**
```java
public class SizeInput extends AbstractWidget implements ChangeListener {

    private JSpinner sizeSpinner;

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JPanel();
        sizeSpinner = new JSpinner(new SpinnerNumberModel(15, 9, 40, 1));
        ((JSpinner.DefaultEditor) sizeSpinner.getEditor()).getTextField().addFocusListener(this);

        // 添加组件并布局
        JLabel label = new JLabel("Size: ");
        root.add(label);root.add(sizeSpinner);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                1, 1, null, 1, 1, 0, 0);
        layout.setConstraints(label, gbc);
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                3, 1, null, 3, 1, 1, 0);
        layout.setConstraints(sizeSpinner, gbc);
        root.setLayout(layout);

        // 字体大小改变监听器
        sizeSpinner.addChangeListener(this);
    }

    @Override
    protected void setDisable() {
        sizeSpinner.setEnabled(false);
    }

    @Override
    protected void setEnable() {
        sizeSpinner.setEnabled(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (headOfEventSource) {
            page.widgetChanged(this);
        }
    }

    public int getFontSize(){
        return (int) sizeSpinner.getValue();
    }

    public void setFontSize(int size) {
        sizeSpinner.setValue(size);
    }
}
```
**（2-4）字体加粗单选框**
```java
public class BoldInput extends AbstractWidget implements ItemListener {

    private JCheckBox boldField;

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        root = new JPanel();
        boldField = new JCheckBox();
        boldField.addFocusListener(this);

        // 添加组件并布局
        JLabel label = new JLabel("Bold: ");
        root.add(label);root.add(boldField);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                1, 1, null, 1, 1, 0, 0);
        layout.setConstraints(label, gbc);
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.WEST,
                3, 1, null, 3, 1, 1, 0);
        layout.setConstraints(boldField, gbc);
        root.setLayout(layout);

        // 选择项改变监听器
        boldField.addItemListener(this);
    }

    @Override
    protected void setDisable() {
        boldField.setEnabled(false);
    }

    @Override
    protected void setEnable() {
        boldField.setEnabled(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (headOfEventSource) {
            page.widgetChanged(this);
        }
    }

    public boolean getSelectState() {
        return boldField.isSelected();
    }

    public void setFontBold(boolean bold) {
        boldField.setSelected(bold);
    }

}
```
**（2-5）编辑框**
```java
public class Editor extends AbstractWidget implements DocumentListener {

    private boolean emptyText = false;
    private JTextArea content;

    @Override
    protected void buildComponentUI() {
        // 指定当前组件的顶层节点，并为合适的组件添加焦点监听
        content = new JTextArea();
        root = new JScrollPane(content);
        content.addFocusListener(this);

        String defaultText = "中文效果：\n" +
                "   这是当前所选字体的呈现效果\n\n" +
                "英文效果：\n" +
                "   this is the rendering of the currently selected font\n\n" +
                "基础样式：\n" +
                "   abcdefghijklmnopqrstuvwxyz\n" +
                "   ABCDEFGHIJKLMNOPQRSTUVWXYZ\n" +
                "   0123456789 (){}[]\n" +
                "   +-*/= .,;:!? #&$%@|^\n\n" +
                "示例段落：\n" +
                "   罗辑艰难地站了起来，在虚弱的颤抖中，他只有扶着墓碑才能站住。他腾出一只手来，整理了一下自己满是泥浆的湿衣服和蓬乱的头发，随后摸索着，从上衣口袋中掏出一个金属管状物，那是一支已经充满电的手枪。\n" +
                "   然后，他面对着东方的晨光，开始了地球文明和三体文明的最后对决。\n\n" +
                "......";
        content.setText(defaultText);
        content.setLineWrap(true);
        content.setLineWrap(true);

        content.getDocument().addDocumentListener(this);
    }

    @Override
    protected void setDisable() {
        content.setEnabled(false);
    }

    @Override
    protected void setEnable() {
        content.setEnabled(true);
    }

    public void changeFont(String fontName, int size, boolean bold) {
        content.setFont(new Font(fontName, bold ? Font.BOLD : Font.PLAIN ,size));
    }

    public boolean isEmptyText() {
        return emptyText;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (headOfEventSource && emptyText) {
            // 当文本区域内容从无到有时，启用其他组件
            String text = content.getText();
            if (!"".equals(text.trim())) {
                emptyText = false;
                page.widgetChanged(this);
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (headOfEventSource) {
            // 当文本区域没有内容时，禁用其他组件
            String text = content.getText();
            if (text == null || "".equals(text.trim())) {
                emptyText = true;
                page.widgetChanged(this);
            }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
```
**（3）中介对象**
```java
public class Page extends JPanel {

    private final OptionalList fontList;                    // 可选的字体列表
    private final FontInput fontInput;                      // 字体输入框
    private final SizeInput sizeInput;                      // 字体大小微调框
    private final BoldInput boldInput;                      // 字体样式输入框
    private final Editor editor;                            // 编辑器框
    public Page(OptionalList fontList, FontInput fontInput, SizeInput sizeInput,
                BoldInput boldInput, Editor displayBox) {
        this.fontList = fontList;
        this.fontInput = fontInput;
        this.sizeInput = sizeInput;
        this.boldInput = boldInput;
        this.editor = displayBox;
        initialLayoutStyle();
        selectFontChanged(OptionalFont.F1);
    }

    /**
     * 部件状态变化
     * @param abstractWidget 部件
     */
    public void widgetChanged(AbstractWidget abstractWidget) {
        if (abstractWidget == fontList) {
            String fontName = fontList.selectedFontName();
            selectFontChanged(OptionalFont.getEnumByKey(fontName));

        } else if (abstractWidget == fontInput) {
            filterFontList();

        } else if (abstractWidget == sizeInput) {
            editor.changeFont(fontInput.getCurrentText(), sizeInput.getFontSize(), boldInput.getSelectState());

        } else if (abstractWidget == boldInput) {
            editor.changeFont(fontInput.getCurrentText(), sizeInput.getFontSize(), boldInput.getSelectState());

        } else if (abstractWidget == editor) {
            editorStateChanged();

        } else {
            throw new UnsupportedOperationException("未注册的组件");
        }
    }


    /**
     * 改变所选字体
     * @param font 字体
     */
    private void selectFontChanged(OptionalFont font) {
        // 启用其他的组件
        sizeInput.setEnable();
        boldInput.setEnable();
        editor.setEnable();

        fontList.filter("");

        // 联动新字体
        fontInput.setFontName(font.key);
        sizeInput.setFontSize(font.defaultSize);
        boldInput.setFontBold(font.defaultBold);
        editor.changeFont(font.key, font.defaultSize, font.defaultBold);
    }

    /**
     * 筛选字体列表
     */
    private void filterFontList() {
        // 禁用其他的组件
        sizeInput.setDisable();
        boldInput.setDisable();
        editor.setDisable();
        // 列表筛选
        String keyword = fontInput.getCurrentText();
        fontList.filter(keyword);
    }

    /**
     * 编辑器改变状态
     */
    private void editorStateChanged() {
        if (editor.isEmptyText()) {
            fontList.setDisable();
            fontInput.setDisable();
            sizeInput.setDisable();
            boldInput.setDisable();
        } else {
            fontList.setEnable();
            fontInput.setEnable();
            sizeInput.setEnable();
            boldInput.setEnable();
        }
    }

    /**
     * 初始化布局
     */
    private void initialLayoutStyle() {
        JPanel placeholder = new JPanel();
        // 组合字体选择器
        JPanel fontComboBox = comboFontBox();
        // 添加所有组件
        add(fontComboBox);add(editor.getRoot());      // 第一二三行
        add(sizeInput.getRoot());                     // 第四行
        add(boldInput.getRoot());                     // 第五行
        add(placeholder);                             // 第六行
        // 组件布局
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        Insets defaultInsets = new Insets(5, 10, 5, 10);
        // FontBox、文本效果框
        setConstraints(gbc, GridBagConstraints.BOTH, GridBagConstraints.WEST,
                1, 3, defaultInsets, 1, 3, 0, 0);
        gbc.ipadx = 50;
        layout.setConstraints(fontComboBox, gbc);
        gbc.ipadx = 0;
        setConstraints(gbc, GridBagConstraints.BOTH, GridBagConstraints.WEST,
                4, 15, defaultInsets, 4, 15, 1, 0);
        layout.setConstraints(editor.getRoot(), gbc);
        // SizeInput
        setConstraints(gbc, GridBagConstraints.BOTH, GridBagConstraints.WEST,
                1, 1, defaultInsets, 1, 1, 0, 3);
        layout.setConstraints(sizeInput.getRoot(), gbc);
        // BoldInput
        setConstraints(gbc, GridBagConstraints.BOTH, GridBagConstraints.WEST,
                1, 1, defaultInsets, 1, 1, 0, 4);
        layout.setConstraints(boldInput.getRoot(), gbc);
        // 占位组件
        setConstraints(gbc, GridBagConstraints.BOTH, GridBagConstraints.WEST,
                1, 10, defaultInsets, 1, 10, 0, 5);
        layout.setConstraints(placeholder, gbc);

        setLayout(layout);
    }

    /**
     * 构造组合字体选择器
     * @return JPanel
     */
    private JPanel comboFontBox() {
        JPanel fontPanel = new JPanel();
        JLabel fontLabel = new JLabel("Font: ");
        fontPanel.add(fontLabel);fontPanel.add(fontInput.getRoot());
        fontPanel.add(fontList.getRoot());
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        Page.setConstraints(gbc, GridBagConstraints.NONE, GridBagConstraints.SOUTHWEST,
                1, 1, null, 1, 1, 0, 0);
        layout.setConstraints(fontLabel, gbc);
        Page.setConstraints(gbc, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST,
                3, 1, null, 3, 1, 1, 0);
        layout.setConstraints(fontInput.getRoot(), gbc);
        Page.setConstraints(gbc, GridBagConstraints.BOTH, GridBagConstraints.WEST,
                3, 2, null, 3, 2, 1, 1);
        layout.setConstraints(fontList.getRoot(), gbc);
        fontPanel.setLayout(layout);
        return fontPanel;
    }


    /**
     * 设置组件参数
     * @param gbc GridBagConstraints
     * @param fill 填充方式
     * @param anchor 停靠方向
     * @param gridWidth 横向占用单元格数
     * @param gridHeight 纵向占用单元格数
     * @param insets 组件之间的间距
     * @param weightX 当窗口放大时，长度如何变化：0不变，1等比放大
     * @param weightY 当窗口放大时，高度如何变化：0不变，1等比放大
     * @param gridx 所处左上角单元格的X坐标
     * @param gridY 所处左上角单元格的Y坐标
     */
    public static void setConstraints(GridBagConstraints gbc, int fill, int anchor, int gridWidth,
                                       int gridHeight, Insets insets, double weightX, double weightY, int gridx, int gridY) {
        gbc.fill = fill;
        gbc.anchor = anchor;
        gbc.gridwidth = gridWidth;
        gbc.gridheight = gridHeight;
        if (null == insets) {
            insets = new Insets(0,0,0,0);
        }
        gbc.insets = insets;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        gbc.gridx = gridx;
        gbc.gridy = gridY;
    }


    /**
     * 可选字体枚举
     *
     * @author coder
     * @date 2022-08-05 20:18:59
     * @since 1.0.0
     */
    public enum OptionalFont {
        F1("宋体", 15, false),                          // 宋体
        F2("微软雅黑", 14, false),                       // 微软雅黑
        F3("仿宋", 16, true),                           // 仿宋
        F4("微软雅黑 Light", 14, true),                  // 微软雅黑 Light
        F5("方正姚体", 14, false),                       // 方正姚体
        F6("Consolas", 15, true);                       // Consolas
        private final String key;
        private final int defaultSize;
        private final boolean defaultBold;
        OptionalFont(String key, int defaultSize, boolean defaultBold) {
            this.key = key;
            this.defaultSize = defaultSize;
            this.defaultBold = defaultBold;
        }
        public String getKey() {
            return key;
        }
        public int getDefaultSize() {
            return defaultSize;
        }
        public boolean isDefaultBold() {
            return defaultBold;
        }
        public static OptionalFont getEnumByKey(String key) {
            for (OptionalFont font : OptionalFont.values()) {
                if (font.key.equals(key)) {
                    return font;
                }
            }
            throw new UnsupportedOperationException("不支持的关键字");
        }
    }
}
```
**（4）客户端**
```java
public class Application {
    public static void main(String[] args) {
        JFrame frame = new JFrame("FontSelector");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        // 添加页面
        OptionalList fontList = new OptionalList();
        FontInput fontInput = new FontInput();
        SizeInput sizeInput = new SizeInput();
        BoldInput boldInput = new BoldInput();
        Editor displayBox = new Editor();
        
        Page page = new Page(fontList, fontInput, sizeInput, boldInput, displayBox);
        fontList.setPage(page);
        fontInput.setPage(page);
        sizeInput.setPage(page);
        boldInput.setPage(page);
        displayBox.setPage(page);

        frame.getContentPane().add(page, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
```
**（5）运行结果**

<div align="center">
   <img src="/doc/resource/mediator/gaming.gif" width="70%"/>
</div>

# 四、中介者模式
## 4.1 意图
> **用一个中介对象来封装一系列的对象交互。中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。**

中介者模式的目的在于解耦对象之间复杂的依赖关系，中介者对象的存在使得各个对象不需要再依赖于多个其他的对象。对象只需要把自身无法完成的工作委托给中介者，由中介者对象负责协调其他的对象进行交互。

## 4.2 类图分析
通常来说，典型的中介者模式的类图结构如下所示：
<div align="center">
   <img src="/doc/resource/mediator/经典中介者类图.jpg" width="60%"/>
</div>

中介者模式的参与者有如下：

- **Colleague**：抽象同事。因为各个部件之间的关系就像同事一样的相互协作，所以通常将部件称呼为同事。所有的同事自身都维护了一个指向中介者的引用；
- **ConcreteColleague**：同事类。在合适的时机（自身无法完成工作时），将工作委托给中介者对象；
- **Mediator**：抽象中介者。定义一个接口用于各同事对象的协作，当只有一个中介者时，可省略该接口；
- **ConcreteMediator**：具体中介者。负责维护所有同事，通过协调各对象实现协作行为。

整个工作流程可简述为：某个同事对象向中介者发起一个请求，中介者协调其他的同事（调用其他同事对象的相关方法）来共同完成协作行为。

# 五、深入理解
## 5.1 特点
**（1）简化了工作模式**

在开篇时，我们说各个组件（同事对象）之间的依赖关系错综复杂，一个组件和其他的组件之间的关系可能是多对多的关系。但是使用了中介者模式之后，这个耦合关系被降低成了一对多（一个中介者 -> 多个组件），这种工作模式使得整个系统的结构更加简单，更容易维护。

**（2）突出关注点**

很多时候，各个对象之间的调用链并不简单，这个调用链可能相当长。如果想要弄清楚系统对于用户的一个动作到底有哪些对象协作完成，我们只能沿着调用链逐个追踪。使用中介者模式则不一样，中介者对象中就已经包含了各个组件在何时开始处理请求，我们可以很轻松的就把注意力转移到各个对象之间的协作上来。所以，使用中介者模式有助于让我们弄清楚一个系统中的对象究竟是如何交互的。

**（3）控制集中化**

中介者对象封装了所有对象之间的交互逻辑，这可能使得中介者对象相当庞大且复杂。所以，使用时应当慎重考虑，如果不能接受中介者对象在日复一日的维护中变成庞然大物，那就不应该使用中介者模式。

## 5.2 使用技巧
**（1）抽象的 Mediator 并不是必须的**

当各 Colleague 仅与一个 Mediator 一起工作时，没有必要定义一 个抽象的 Mediator 类。
## 5.3 扩展
对于中介者模式来说，因为其将控制集中化，可能为系统引入一些庞大的中介对象，这使得我们在实际中使用时必须非常小心。但是其蕴含的思想却值得我们借鉴，比如我们常用的消息中间件（MQ）就与中介者模式的思想不谋而合。消息中间件的作用点之一就是解耦，正如中介者模式一样，消息的生产者并不关心由谁来消费消息，也不负责与消费者进行交互，他仅仅只是将消息发送到消息队列中，由消息对象负责将该消息转发给合适的消费者。并且，消息生产者同时也可以是另一种消息的消费者，正如在案例中描述的那样：当字体输入框中切换字体时，编辑器相应的切换自身内容的字体；而当编辑器内的内容为空时，反过来将导致字体输入框不可用。

# 附录
[回到主页](/README.md)    [案例代码](/src/main/java/com/aoligei/behavioral/mediator)
