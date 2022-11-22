package cn.com.mustache.mybatis.console;

import cn.com.mustache.mybatis.util.SqlProUtil;
import com.intellij.icons.AllIcons;
import com.intellij.icons.AllIcons.Actions;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;

/**
 * 左侧工具栏
 *
 * @author Steven Han
 */
public class ConsoleActionGroup {

    private static Runnable myFilterAction;

    public static void withFilter(Runnable filter) {
        ConsoleActionGroup.myFilterAction = filter;
    }

    /**
     * 过滤按钮
     */
    public static class FilterAction extends AnAction implements DumbAware {


        public FilterAction() {
            super("Filter", "Filter", AllIcons.General.Filter);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            ConsoleActionGroup.myFilterAction.run();
        }
    }


    /**
     * 开启过滤按钮
     */
    public static class FormatAction extends ToggleAction implements DumbAware {


        public FormatAction() {
            super("Ellipsis", "Ellipsis", AllIcons.General.Ellipsis);
        }

        @Override
        public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
            return SqlProUtil.Ellipsis;
        }

        @Override
        public void setSelected(@NotNull AnActionEvent anActionEvent, boolean b) {
            SqlProUtil.Ellipsis = b;
        }
    }

    /**
     * Sql 展示字面量按钮
     */
    public static class ShowLiteralAction extends ToggleAction implements DumbAware {

        public ShowLiteralAction() {
            super("Literal", "ShowLiteral", Actions.ShowCode);
        }

        @Override
        public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
            return SqlProUtil.ShowLiteral;
        }

        @Override
        public void setSelected(@NotNull AnActionEvent anActionEvent, boolean b) {
            SqlProUtil.ShowLiteral = b;
        }
    }
}
