package com.aoligei.behavioral.mediator;

import com.aoligei.behavioral.mediator.widget.*;

import javax.swing.*;
import java.awt.*;

/**
 * 页面
 *
 * @author coder
 * @date 2022-08-03 16:18:59
 * @since 1.0.0
 */
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
