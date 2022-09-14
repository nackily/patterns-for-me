package com.aoligei.behavioral.interpreter;

import com.aoligei.behavioral.interpreter.expression.AbstractSymbolExpression;
import com.aoligei.behavioral.interpreter.expression.PermissionExpression;
import com.aoligei.behavioral.interpreter.expression.PermissionType;
import com.aoligei.behavioral.interpreter.expression.symbol.AndExpression;
import com.aoligei.behavioral.interpreter.expression.symbol.OrExpression;

import java.text.MessageFormat;

/**
 * Client
 *
 * @author coder
 * @date 2022-07-27 17:00:32
 * @since 1.0.0
 */
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
