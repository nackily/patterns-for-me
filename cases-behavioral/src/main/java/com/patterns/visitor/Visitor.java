package com.patterns.visitor;

import com.patterns.visitor.resource.Button;
import com.patterns.visitor.resource.Catalog;
import com.patterns.visitor.resource.Menu;

/**
 * 访问者接口
 *
 * @author coder
 * @date 2022-08-26 10:51:18
 * @since 1.0.0
 */
public interface Visitor {

    /**
     * 访问目录
     * @param c 目录
     * @return String
     */
    String visitCatalog(Catalog c);

    /**
     * 访问菜单
     * @param m 菜单
     * @return String
     */
    String visitMenu(Menu m);

    /**
     * 访问按钮
     * @param b 按钮
     * @return String
     */
    String visitButton(Button b);

}
