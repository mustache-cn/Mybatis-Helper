package cn.com.mustache.mybatis.util;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;

/**
 * 打印简单工具类
 *
 * @author Steven Han
 */
public class PrintlnUtil {

    /**
     * 多项目控制台独立性
     */
    public static Map<String, ConsoleView> consoleViewMap = new ConcurrentHashMap<>();


    public static void setConsoleView(Project project, ConsoleView consoleView) {
        consoleViewMap.put(project.getBasePath(), consoleView);
    }

    /**
     * 输出语句
     *
     * @param project                项目
     * @param rowLine                行数据
     * @param consoleViewContentType 输出颜色
     */
    public static void println(Project project, String rowLine, ConsoleViewContentType consoleViewContentType) {
        println(project, rowLine, consoleViewContentType, false);
    }

    /**
     * 输出语句
     *
     * @param project                项目
     * @param rowLine                行数据
     * @param consoleViewContentType 输出颜色
     */
    public static void println(Project project, String rowLine, ConsoleViewContentType consoleViewContentType, boolean line) {
        println(project, rowLine, consoleViewContentType, line, true);
    }

    /**
     * 输出语句
     *
     * @param project                项目
     * @param rowLine                行数据
     * @param consoleViewContentType 输出颜色
     */
    public static void println(Project project, String rowLine, ConsoleViewContentType consoleViewContentType, boolean line, boolean lineBreak) {
        ConsoleView consoleView = consoleViewMap.get(project.getBasePath());
        if (consoleView != null) {
            if (lineBreak) {
                consoleView.print(rowLine + "\n", consoleViewContentType);
            } else {
                consoleView.print(rowLine, consoleViewContentType);
            }
            if (line) {
                consoleView.print(KeyNameUtil.LINE, ConsoleViewContentType.USER_INPUT);
            }
        }
    }


    /**
     * SQL 输出语句
     *
     * @param rowLine 行数据
     */
    public static void printlnSqlType(Project project, String rowLine) {
        final String sqlType = SqlProUtil.getSqlType(rowLine);
        switch (sqlType) {
            case "insert":
            case "update":
                println(project, rowLine, ConsoleViewContentType.SYSTEM_OUTPUT, true);
                break;
            case "delete":
                println(project,
                        rowLine,
                        new ConsoleViewContentType("styleName", new TextAttributes(Color.RED, null, null, null, Font.PLAIN)),
                        true);
                break;
            default:
                println(project, rowLine, ConsoleViewContentType.ERROR_OUTPUT, true);
        }
    }
}
