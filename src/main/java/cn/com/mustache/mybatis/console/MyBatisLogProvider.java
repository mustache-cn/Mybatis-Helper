package cn.com.mustache.mybatis.console;

import cn.com.mustache.mybatis.util.MyBatisLogFilter;
import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Console 输出监控
 *
 * @author Steven Han
 */
public class MyBatisLogProvider implements ConsoleFilterProvider {
    @NotNull
    @Override
    public Filter[] getDefaultFilters(@NotNull Project project) {
        Filter filter = new MyBatisLogFilter(project);
        return new Filter[]{filter};
    }
}
