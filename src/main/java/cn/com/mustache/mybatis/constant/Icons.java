package cn.com.mustache.mybatis.constant;

import javax.swing.*;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.PlatformIcons;

/**
 * @author Steven Han
 */
public interface Icons {

    Icon MYBATIS_LOGO = IconLoader.getIcon("/javaee/persistenceId.png", Icons.class);

    Icon PARAM_COMPLETION_ICON = PlatformIcons.PARAMETER_ICON;

    Icon SPRING_INJECTION_ICON = IconLoader.getIcon("/images/injection.svg", Icons.class);

    Icon MYBATIS_ICON = IconLoader.getIcon("/images/logo.svg", Icons.class);

    Icon MAPPER_LINE_MARKER_ICON = IconLoader.getIcon("/images/mapper_method.svg", Icons.class);

    Icon STATEMENT_LINE_MARKER_ICON = IconLoader.getIcon("/images/statement.svg", Icons.class);
}