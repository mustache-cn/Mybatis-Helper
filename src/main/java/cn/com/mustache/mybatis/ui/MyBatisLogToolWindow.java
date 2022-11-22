package cn.com.mustache.mybatis.ui;

import javax.swing.*;

import cn.com.mustache.mybatis.console.ConsolePanel;
import cn.com.mustache.mybatis.constant.Icons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * 标签窗口实现
 *
 * @author Steven Han
 */
public class MyBatisLogToolWindow implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ConsolePanel consolePanel = new ConsolePanel();
        final JComponent jComponent = consolePanel.getConsolePanel(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(jComponent, "", false);
        toolWindow.setIcon(Icons.MYBATIS_ICON);
        toolWindow.getContentManager().addContent(content);
    }
}
